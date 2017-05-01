package com.fanyuwen.dynamicCompile.button;

import javax.swing.*;
import java.util.function.Function;

/**
 * Created by fanyuwen on 2017/4/30.
 */
public abstract class ButtonFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;

    protected JPanel panel;
    protected JButton yellowButton;
    protected JButton blueButton;
    protected JButton redButton;

    protected abstract void addEventHandlers();

    public ButtonFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        panel = new JPanel();
        add(panel);

        yellowButton = stringJButtonFunction.apply("Yellow");
        blueButton = stringJButtonFunction.apply("Blue");
        redButton = stringJButtonFunction.apply("Red");

        panel.add(yellowButton);
        panel.add(blueButton);
        panel.add(redButton);
        addEventHandlers();
    }

    private Function<String, JButton> stringJButtonFunction = JButton::new;
}