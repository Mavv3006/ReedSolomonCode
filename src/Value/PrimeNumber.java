package Value;

public class PrimeNumber extends Field {

    private int primeBasis;

    PrimeNumber(double newValue, int primeBasis) {
        myValue = newValue;
    }

    @Override
    public Field[] getNullRow(int lengthOfRow) {
        return new Field[0];
    }

    @Override
    public double getValue() {
        return myValue;
    }

    @Override
    public void setValue(double newValue) {

    }

    @Override
    public void subtract(Field subtrahend) {

    }

    @Override
    public void add(Field summand) {

    }

    @Override
    public void divide(Field divisor) {

    }

    @Override
    public void multiply(Field factor) {

    }
}
