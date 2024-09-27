package calculator.frame;

import calculator.Operators;

import javax.swing.*;
import java.util.List;

public class OperatorJCombo extends JComboBox<String> {

    public OperatorJCombo() {
        super(Operators.getOperators().toArray(new String[0]));
    }

    public boolean isSelectedOf(List<String> list) {
        for (String operator : list) {
            if (this.getSelectedItem().equals(operator)) {
                return true;
            }
        }
        return false;
    }
}