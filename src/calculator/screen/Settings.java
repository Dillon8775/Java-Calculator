package calculator.screen;

import calculator.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class Settings extends calculator.screen.Screen {
    private final JLabel settingsTitle = new JLabel("Settings");
    private final JLabel roundNumbersLabel = new JLabel("Round Numbers");
    private final JComboBox<String> roundNumbersOptionLabel = new JComboBox<>(new String[]{"true", "false"});
    private final JButton exit = new JButton("Go Back and Save");

    public Settings() {
        this.ofTextLabel(this.settingsTitle, createBounds(190, 40, 200, 40));
        this.ofTextLabel(this.roundNumbersLabel, createBounds(140, 100, 140, 20));
        this.ofDropdown(this.roundNumbersOptionLabel, createBounds(250, 100, 100, 20), true);

        this.settingsTitle.setFont(new Font("Bold", Font.BOLD, 24));
        this.roundNumbersLabel.setFont(new Font("Bold", Font.BOLD, 12));
        this.roundNumbersOptionLabel.setSelectedItem(Main.roundNumbers ? "true" : "false");

        this.ofButton(this.exit, createBounds(140, 300, 200, 40), true, false);
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == this.roundNumbersOptionLabel) {
            try {
                FileWriter writer = new FileWriter(Main.file);
                if (this.roundNumbersOptionLabel.getSelectedItem().equals("true")) {
                    writer.write("roundNumbers=true");
                    Main.roundNumbers = true;
                } else {
                    writer.write("roundNumbers=false");
                    Main.roundNumbers = false;
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred!");
                e.printStackTrace();
            }
        }
        if (a.getSource() == this.exit) {
            new calculator.screen.Calculator();
            this.getFrame().dispose();
        }
    }

    @Override
    protected String windowTitle() {
        return "Calculator Settings";
    }
}
