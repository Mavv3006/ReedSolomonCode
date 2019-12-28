package Value;

import java.util.Arrays;

public class MyValue {
    public static MyValue ZERO = new MyValue(0);
    public static MyValue ONE = new MyValue(1);
    private Double myValue;

    public MyValue(double value) {
        myValue = value;
    }

    public MyValue() {
        myValue = 0.0;
    }

    public static MyValue multiply(MyValue value1, MyValue value2) {
        return new MyValue(value1.getValue() * value2.getValue());
    }

    public static MyValue[] getNullRow(int lengthOfRow) {
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

    public double getValue() {
        return myValue;
    }

    public void setValue(double newValue) {
        this.myValue = newValue;
    }

    public void divideBy(MyValue divisor) throws ArithmeticException {
        double div = divisor.getValue();
        if (div == 0) {
            throw new ArithmeticException("Division by Zero");
        }
        this.setValue(this.getValue() / div);
    }

    public void add(MyValue value) {
        this.setValue(this.getValue() + value.getValue());
    }

    public void subtract(MyValue value) {
        this.setValue(this.getValue() - value.getValue());
    }

    @Override
    public String toString() {
        return myValue.toString();
    }

    public MyValue copy() {
        return new MyValue(this.getValue());
    }
}
