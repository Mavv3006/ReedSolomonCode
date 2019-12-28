package Value;

public interface Field {

    Field[] getNullRow(int lengthOfRow);

    double getValue();

    void setValue(double newValue);

    Field copy();

    void subtract(Field subtrahend);

    void add(Field summand);

    void divide(Field divisor);

    void multiply(Field factor);
}
