package calculator.screen;

import calculator.Main;
import calculator.Operators;
import calculator.frame.OperatorJCombo;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator extends Screen {
    private final JLabel calculatorLabel = new JLabel("Calculator");
    private final JTextField mainInput = new JTextField();
    private final JTextField secondaryInput = new JTextField();
    private final JTextField resultBox = new JTextField("Answer will appear here.");
    private final OperatorJCombo operators = new OperatorJCombo();
    private final JButton calculateButton = new JButton("Calculate");
    private final JButton settingsButton = new JButton();
    private final JButton exitButton = new JButton("Exit");
    private final List<JButton> buttons = new ArrayList<>();

    public Calculator() {
        this.setDefaultLayout();

        this.ofTextLabel(this.calculatorLabel, createBounds(190, 20, 200, 40));
        this.ofTextComponent(this.mainInput, createBounds(75, 75, 100, 20));
        this.ofDropdown(this.operators, createBounds(205, 75, 75, 20), true);
        this.ofTextComponent(this.secondaryInput, createBounds(305, 75, 100, 20));
        this.ofTextComponent(this.resultBox, createBounds(140, 350, 200, 40), false);
        this.ofButton(this.calculateButton, createBounds(140, 225, 200, 40), true, false);
        this.ofButton(this.settingsButton, createBounds(450, 425, 20, 20), true, false);
        this.ofButton(this.exitButton, createBounds(140, 275, 200, 40), true, false);

        this.calculatorLabel.setFont(new Font("Bold", Font.BOLD, 18));

        int x = 320;
        int y = 200;
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                y += 40;
                x = 320;
            }
            JButton button = this.ofButton(new JButton(String.valueOf(i + 1)), createBounds(x, y, 40, 40), true);
            x += 40;
            this.buttons.add(button);
            button.setVisible(false);
        }

        x = 320;
        y = 320;
        for (int i = 0; i < 10; i++) {
            if (i >= 7) {
                y -= 20;
            }
            JButton button = this.ofButton(new JButton(buttonString(i)), createBounds(x, y, 20, 20), true);
            this.buttons.add(button);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setVisible(false);
            if (!(i >= 6)) {
                x += 20;
            }
        }

        this.getFrame().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == this.calculateButton) {
            Double answer = null;
            try {
                Map<String, Double> values = ofMap(this.mainInput.getText(), this.secondaryInput.getText());
                answer = cal(values, this.operators);
            } catch (NumberFormatException e) {
                try {
                    answer = calOther(this.operators);
                } catch (IndexOutOfBoundsException | NumberFormatException i) {
                    System.out.println("Missing number!");
                }
            }

            if (answer != null) {
                if (Main.roundNumbers) {
                    answer = Math.round(answer * 100.0) / 100.0D;
                }
                this.resultBox.setText(String.valueOf(answer));
                System.out.println(answer);
            } else {
                this.resultBox.setText("Invalid/missing number.");
            }
        }

        if (a.getSource() == this.operators) {
            if (this.operators.isSelectedOf(Operators.twoWayOperators())) {
                this.setDefaultLayout();
                Main.bl = false;
            }
            if (this.operators.isSelectedOf(Operators.oneWayOperators())) {
                if (this.operators.getSelectedItem().equals(Operators.ANY.asString())) {
                    this.mainInput.setFocusable(false);
                    this.calculateButton.setBounds(75, 225, 200, 40);
                    this.exitButton.setBounds(75, 275, 200, 40);
                    this.resultBox.setBounds(75, 350, 200, 40);
                    visible(true);
                    Main.bl = false;
                } else {
                    setDefaultLayout();
                    visible(false);
                }
                this.secondaryInput.setText(null);
                this.secondaryInput.setVisible(false);
                this.operators.setBounds(290, this.operators.getY(), this.operators.getWidth(), this.operators.getHeight());
                this.mainInput.setBounds(150, this.operators.getY(), 120, this.operators.getHeight());
            }
            visible(this.operators.getSelectedItem().equals(Operators.ANY.asString()));
            this.getFrame().repaint();
        }

        if (a.getSource() == this.settingsButton) {
            new Settings();
            this.getFrame().dispose();
        }

        for (JButton button : this.buttons) {
            if (a.getSource() == button) {
                if (button.getText().equals("C")) {
                    this.mainInput.setText("");
                } else if (button.getText().equals("<-")) {
                    this.mainInput.setText(this.mainInput.getText().substring(0, this.mainInput.getText().length() - 1));
                } else {
                    this.mainInput.setText(this.mainInput.getText() + button.getText());
                }
                this.buttons.get(18).setEnabled(!this.mainInput.getText().isEmpty());
            }
        }

        if (a.getSource() == this.exitButton) {
            System.out.println("Stopping!");
            this.getFrame().dispose();
        }
    }

    private void setDefaultLayout() {
        this.mainInput.setBounds(75, 75, 100, 20);
        this.mainInput.setFocusable(true);
        this.secondaryInput.setVisible(true);
        this.secondaryInput.setBounds(305, 75, 100, 20);
        this.operators.setBounds(215, 75, 50, 20);
        this.operators.setLayout(new FlowLayout());
        this.calculateButton.setBounds(140, 225, 200, 40);
        this.exitButton.setBounds(140, 275, 200, 40);
        this.settingsButton.setBounds(450, 425, 20, 20);
        this.resultBox.setBounds(140, 350, 200, 40);
    }

    private Map<String, Double> ofMap(String firstValue, String secondValue) {
        Map<String, Double> map = new HashMap<>();
        map.put(firstValue, Double.parseDouble(firstValue));
        map.put(secondValue, Double.parseDouble(secondValue));
        return map;
    }

    private Double cal(Map<String, Double> map, JComboBox<String> operators) {
        Double answer = null;
        if (operators.getSelectedItem().equals("+")) {
            answer = map.get(this.mainInput.getText()) + map.get(this.secondaryInput.getText());
        }

        if (operators.getSelectedItem().equals("-")) {
            answer = map.get(this.mainInput.getText()) - map.get(this.secondaryInput.getText());
        }

        if (operators.getSelectedItem().equals("*")) {
            answer = map.get(this.mainInput.getText()) * map.get(this.secondaryInput.getText());
        }

        if (operators.getSelectedItem().equals("/")) {
            answer = map.get(this.mainInput.getText()) / map.get(this.secondaryInput.getText());
        }

        if (operators.getSelectedItem().equals("%")) {
            answer = map.get(this.mainInput.getText()) % map.get(this.secondaryInput.getText());
        }
        return answer;
    }

    private Double calOther(JComboBox<String> operators) throws NumberFormatException {
        Double answer = null;
        double radians = Math.toRadians(Double.parseDouble(this.mainInput.getText()));
        double degrees = Math.toDegrees(Double.parseDouble(this.mainInput.getText()));
        if (operators.getSelectedItem().equals(Operators.SQUARE_ROOT.asString())) {
            answer = Math.sqrt(Double.parseDouble(this.mainInput.getText()));
        }

        if (operators.getSelectedItem().equals(Operators.CUBED_ROOT.asString())) {
            answer = Math.cbrt(Double.parseDouble(this.mainInput.getText()));
        }

        if (operators.getSelectedItem().equals(Operators.CIRCLE_AREA.asString())) {
            answer = Math.PI * Math.pow(Double.parseDouble(this.mainInput.getText()), 2);
        }

        if (operators.getSelectedItem().equals(Operators.SIN.asString())) {
            answer = Math.sin(radians);
        }

        if (operators.getSelectedItem().equals(Operators.COS.asString())) {
            answer = Math.cos(radians);
        }

        if (operators.getSelectedItem().equals(Operators.TAN.asString())) {
            answer = Math.tan(radians);
        }

        if (operators.getSelectedItem().equals(Operators.INV_SIN.asString())) {
            answer = Math.asin(degrees);
        }

        if (operators.getSelectedItem().equals(Operators.INV_COS.asString())) {
            answer = Math.acos(degrees);
        }

        if (operators.getSelectedItem().equals(Operators.INV_TAN.asString())) {
            answer = Math.atan(degrees);
        }

        if (operators.getSelectedItem().equals(Operators.ANY.asString())) {
            answer = parseEquation(this.mainInput);
        }
        return answer;
    }

    private Double parseEquation(JTextComponent input) throws IndexOutOfBoundsException {
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        for (char character : input.getText().toCharArray()) {
            if (Character.isDigit(character)) {
                double numericalValue = Character.getNumericValue(character);
                numbers.add(numericalValue);
            } else {
                operators.add(character);
            }
        }

        Double x = null;
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '%' || operators.get(i).equals('*') || operators.get(i).equals('/')) {
                switch (operators.get(i)) {
                    case '*' -> x = numbers.get(i) * numbers.get(i + 1);
                    case '/' -> x = numbers.get(i) / numbers.get(i + 1);
                    case '%' -> x = numbers.get(i) % numbers.get(i + 1);
                }
                numbers.set(i, x);
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        x = numbers.getFirst();
        for (int i = 0; i < operators.size(); i++) {
            switch (operators.get(i)) {
                case '+' -> x += numbers.get(i + 1);
                case '-' -> x -= numbers.get(i + 1);
            }
        }
        return x;
    }

    private void visible(boolean visible) {
        for (JButton button : this.buttons) {
            button.setVisible(visible);
        }
        Main.bl = visible;
    }

    private String buttonString(int i) {
        switch (i) {
            case 0 -> {
                return Operators.PLUS.asString();
            }
            case 1 -> {
                return Operators.MINUS.asString();
            }
            case 2 -> {
                return Operators.MULTIPLY.asString();
            }
            case 3 -> {
                return Operators.DIVIDE.asString();
            }
            case 4 -> {
                return Operators.MOD.asString();
            }
            case 5 -> {
                return "0";
            }
            case 6 -> {
                return "C";
            }
            case 7 -> {
                return ")";
            }
            case 8 -> {
                return "(";
            }
            case 9 -> {
                return "<-";
            }
        }
        return null;
    }

    public JTextField getMainInput() {
        return this.mainInput;
    }

    public JTextField getSecondaryInput() {
        return this.secondaryInput;
    }

    @Override
    protected String windowTitle() {
        return "Calculator";
    }
}