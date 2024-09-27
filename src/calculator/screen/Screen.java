package calculator.screen;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;

public abstract class Screen implements ActionListener {
    private final JFrame frame = new JFrame();

    public Screen() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500, 500);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setTitle(this.windowTitle());
    }

    protected JButton ofButton(JButton button, int[] bounds, boolean actionListener) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        if (actionListener) {
            button.addActionListener(this);
        }
        this.frame.add(button);
        return button;
    }

    protected void ofButton(JButton button, int[] bounds, boolean actionListener, boolean focusable) {
        button.setFocusable(false);
        ofButton(button, bounds, actionListener);
    }

    protected void ofDropdown(JComboBox<?> button, int[] bounds, boolean actionListener) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        if (actionListener) {
            button.addActionListener(this);
        }
        this.frame.add(button);
    }

    protected void ofTextComponent(JTextComponent button, int[] bounds) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        this.frame.add(button);
    }

    protected void ofTextComponent(JTextComponent button, int[] bounds, boolean focusable) {
        button.setFocusable(focusable);
        ofTextComponent(button, bounds);
    }

    protected void ofTextLabel(JLabel label, int[] bounds) {
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        this.frame.add(label);
    }

    protected int[] createBounds(int x, int y, int width, int height) {
        return new int[]{x, y, width, height};
    }

    public JFrame getFrame() {
        return this.frame;
    }

    protected abstract String windowTitle();
}