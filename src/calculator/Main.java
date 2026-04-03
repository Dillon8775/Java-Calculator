package calculator;

import calculator.screen.Calculator;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dillon S.
 * <p>4/2/26
 * <p>An advanced calculator in Java, which I have spent a long time working on.
 * <p>GitHub Repository: <a href="https://github.com/Dillon8775/Java-Calculator">...</a></p>
 */
public class Main {
    public static boolean bl = false;
    public static boolean roundNumbers = true;
    public static final String file = "options.txt";
    private static final List<JTextField> inputs = new ArrayList<>();

    /**
     * Main method. Constructs and renders the calculator.
     */
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

    /**
     * Creates the options file for the calculator, indicating if the user wants to round numbers.
     */
    private static void createOptionsFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("roundNumbers=true");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the calculator screen, and adds listeners for each field.
     */
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