package com.fanyuwen.util;

import java.util.*;

/**
 * @author fanyuwen
 * 基于双向链表实现的链式创建List和Map的API
 */
public class CollectionUtil {

    private CollectionUtil() {
        throw new IllegalStateException("非法创建对象" + CollectionUtil.class + ",该类不能被创建.");
    }

    public static <K, V> MapBuilder<K, V> ofMap(K key, V value) {
        return new MapBuilder<>(null, key, value);
    }

    public static <T> ListBuilder<T> ofList(T value) {
        return new ListBuilder<>(null, value);
    }

    public static class ListBuilder<T> extends AbstractBuilder<List<T>> {
        private T value;
        private ListBuilder<T> buildLink;
        private ListBuilder<T> preBuildLink;

        ListBuilder(ListBuilder<T> listBuilder, T value) {
            Objects.requireNonNull(value);
            if (listBuilder != null)
                listBuilder.buildLink = this;
            this.preBuildLink = listBuilder;
            this.value = value;
        }

        public ListBuilder<T> add(T value) {
            return new ListBuilder<>(this, value);
        }

        public ListBuilder<T> add(T value, T... values) {
            ListBuilder<T> listBuilder = add(value);
            for (T t : values)
                listBuilder = listBuilder.add(t);
            return listBuilder;
        }

        public T getValue() {
            return value;
        }

        @Override
        public ListBuilder<T> getBuildLink() {
            return buildLink;
        }

        @Override
        public ListBuilder<T> getPreBuildLink() {
            return preBuildLink;
        }

        @Override
        public ListBuilder<T> revert() {
            return (ListBuilder<T>) super.revert();
        }

        @Override
        public List<T> build() {
            return build(ArrayList::new);
        }

        @Override
        public List<T> build(Generate<List<T>> generate) {
            List<T> list = generate.generate();
            ListBuilder<T> listBuilder = revert();
            while (listBuilder != null) {
                list.add(listBuilder.getValue());
                listBuilder = listBuilder.getBuildLink();
            }
            return list;
        }

    }

    public static class MapBuilder<K, V> extends AbstractBuilder<Map<K, V>> {
        private K key;
        private V value;

        private MapBuilder<K, V> buildLink;
        private MapBuilder<K, V> preBuildLink;

        MapBuilder(MapBuilder<K, V> buildLink, K key, V value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            if (buildLink != null)
                buildLink.buildLink = this;
            this.preBuildLink = buildLink;
            this.key = key;
            this.value = value;
        }

        public MapBuilder<K, V> add(K key, V value) {
            return new MapBuilder<>(this, key, value);
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public MapBuilder<K, V> getBuildLink() {
            return buildLink;
        }

        @Override
        public Builder<Map<K, V>> getPreBuildLink() {
            return preBuildLink;
        }

        @Override
        public MapBuilder<K, V> revert() {
            return (MapBuilder<K, V>) super.revert();
        }

        @Override
        public Map<K, V> build() {
            return build(HashMap::new);
        }

        @Override
        public Map<K, V> build(Generate<Map<K, V>> generate) {
            Map<K, V> map = generate.generate();
            MapBuilder<K, V> builder = revert();
            while (builder != null) {
                map.put(builder.getKey(), builder.getValue());
                builder = builder.getBuildLink();
            }
            return map;
        }
    }

    private interface Builder<T> {
        T build();

        T build(Generate<T> generate);

        Builder<T> getBuildLink();

        Builder<T> getPreBuildLink();

        Builder<T> revert();

        interface Generate<T> {
            T generate();
        }
    }

    private static abstract class AbstractBuilder<T> implements Builder<T> {
        @Override
        public Builder<T> revert() {
            Builder<T> preBuilder = this;
            while (preBuilder.getPreBuildLink() != null)
                preBuilder = preBuilder.getPreBuildLink();
            return preBuilder;
        }
    }

}