package calculator;

import calculator.screen.Calculator;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static boolean bl = false;
    public static boolean roundNumbers = true;
    public static final String file = "options.txt";
    private static final List<JTextField> inputs = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Starting calculator app...");

        openCalculatorScreen();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                roundNumbers = line.equals("roundNumbers=true");
            }
            reader.close();
        } catch (FileNotFoundException f) {
            createOptionsFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully started calculator app!");
    }

    private static void createOptionsFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("roundNumbers=true");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openCalculatorScreen() {
        Calculator launchPage = new Calculator();
        inputs.add(launchPage.getMainInput());
        inputs.add(launchPage.getSecondaryInput());

        for (JTextComponent field : inputs) {
            field.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (!bl) {
                        try {
                            Double.parseDouble(field.getText() + e.getKeyChar());
                        } catch (NumberFormatException n) {
                            e.consume();
                        }
                    } else {
                        if (Character.isLetter(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                }
            });
        }
    }
}