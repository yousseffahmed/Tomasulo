
public class ALU {

    public int add(int operand1, int operand2) {
        return operand1 + operand2;
    }

    public int subtract(int operand1, int operand2) {
        return operand1 - operand2;
    }

    public int multiply(int operand1, int operand2) {
        return operand1 * operand2;
    }

    public int divide(int operand1, int operand2) {
        if (operand2 != 0) {
            return operand1 / operand2;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
    
    public double addFP(double operand1, double operand2) {
        return operand1 + operand2;
    }

    public double subtractFP(double operand1, double operand2) {
        return operand1 - operand2;
    }

    public double multiplyFP(double operand1, double operand2) {
        return operand1 * operand2;
    }

    public double divideFP(double operand1, double operand2) {
        if (operand2 != 0) {
            return operand1 / operand2;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
}
