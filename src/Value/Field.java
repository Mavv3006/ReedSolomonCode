package Value;

import java.util.Arrays;

public abstract class Field implements Cloneable {
    Double myValue;

    public Field[] getNullRow(int lengthOfRow) {
        Field[] fields = new Field[lengthOfRow];
        Arrays.setAll(fields, v -> {
            Field value = this.clone();
            value.setValue(0);
            return value;
        });
        return fields;
    }

    public abstract double getValue();

    public abstract void setValue(double newValue);

    public Field clone() {
        Field field = null;
        try {
            field = (Field) super.clone();
        } catch (CloneNotSupportedException ignored) {
            // will never happen
        }
        return field;
    }

    public abstract void subtract(Field subtrahend);

    public abstract void add(Field summand);

    public abstract void divide(Field divisor);

    public abstract void multiply(Field factor);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field) {
            return ((Field) obj).getValue() == this.getValue();
        } else return false;
    }
}
