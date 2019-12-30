package Value;

import java.util.Arrays;

public class RealNumbers extends Field {
    public static Field ZERO = new RealNumbers(0);
    public static Field ONE = new RealNumbers(1);

    public RealNumbers(double value) {
        myValue = value;
    }

    @Override
    public RealNumbers[] getNullRow(int lengthOfRow) {
        RealNumbers[] nullRow = new RealNumbers[lengthOfRow];
        Arrays.setAll(nullRow, v -> new RealNumbers(0));
        return nullRow;
    }

    @Override
    public double getValue() {
        return myValue;
    }

    @Override
    public void setValue(double newValue) {
        this.myValue = newValue;
    }

    @Override
    public void divide(Field divisor) throws ArithmeticException {
        double div = divisor.getValue();
        if (div == 0) {
            throw new ArithmeticException("Division by Zero");
        }
        this.setValue(this.getValue() / div);
    }

    @Override
    public void multiply(Field factor) {
        this.setValue(this.getValue() * factor.getValue());
    }

    @Override
    public void add(Field summand) {
        this.setValue(this.getValue() + summand.getValue());
    }

    @Override
    public void subtract(Field subtrahend) {
        this.setValue(this.getValue() - subtrahend.getValue());
    }

    @Override
    public String toString() {
        return myValue.toString();
    }
}
