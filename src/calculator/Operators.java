package calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * All operators that are valid and can be used in the calculator.
 */
public enum Operators {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MOD("%"),
    SQUARE_ROOT("√"),
    CUBED_ROOT("∛"),
    CIRCLE_AREA("πr2"),
    SIN("sin¹"),
    INV_SIN("sin⁻¹"),
    COS("cos¹"),
    INV_COS("cos⁻¹"),
    TAN("tan¹"),
    INV_TAN("tan⁻¹"),
    ANY("ANY");

    private final String operator;

    Operators(String operator) {
        this.operator = operator;
    }

    /**
     * @return an {@link ArrayList} of all operators.
     */
    public static List<String> getOperators() {
        List<String> operators = new ArrayList<>();
        for (Operators operator : values()) {
            operators.add(operator.asString());
        }
        return operators;
    }

    /**
     * @return an operator as a string.
     */
    public String asString() {
        return this.operator;
    }

    /**
     * @return an {@link ArrayList} of {@code "two-way operators"}, which indicates that you need two different numbers to perform an operation.
     */
    public static List<String> twoWayOperators() {
        return List.of(
                Operators.PLUS.asString(),
                Operators.MINUS.asString(),
                Operators.MULTIPLY.asString(),
                Operators.DIVIDE.asString(),
                Operators.MOD.asString()
        );
    }

    /**
     * @return an {@link ArrayList} of {@code "one-way operators"}, which mean you only need 1 value to perform an operation.
     */
    public static List<String> oneWayOperators() {
        return List.of(
                Operators.SQUARE_ROOT.asString(),
                Operators.CUBED_ROOT.asString(),
                Operators.CIRCLE_AREA.asString(),
                Operators.SIN.asString(),
                Operators.COS.asString(),
                Operators.TAN.asString(),
                Operators.INV_SIN.asString(),
                Operators.INV_COS.asString(),
                Operators.INV_TAN.asString(),
                Operators.ANY.asString()
        );
    }
}