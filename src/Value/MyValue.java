package Value;

import java.util.Arrays;

public class MyValue implements Field {
    public static MyValue ZERO = new MyValue(0);
    public static MyValue ONE = new MyValue(1);
    private Double myValue;

    public MyValue(double value) {
        myValue = value;
    }

    public MyValue() {
        myValue = 0.0;
    }

    @Override
    public MyValue[] getNullRow(int lengthOfRow) {
        MyValue[] nullRow = new MyValue[lengthOfRow];
        Arrays.setAll(nullRow, v -> new MyValue());
        return nullRow;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyValue) {
            return ((MyValue) obj).getValue() == this.getValue();
        } else return false;
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

    @Override
    public MyValue copy() {
        return new MyValue(this.getValue());
    }
}
