package Value;

import java.util.ArrayList;
import java.util.List;

public class MyValue {

    private List<Double> myValue;

    public MyValue(double value) {
        myValue = new ArrayList<>();
        myValue.add(value);
    }

    public double getValue() {
        return myValue.get(0);
    }

    public void setValue(double value) {
        this.myValue.set(0, value);
    }

    public MyValue div(MyValue divisor) throws ArithmeticException {
        double div = divisor.getValue();
        if (div == 0) {
            throw new ArithmeticException("Division by Zero");
        }
        this.setValue(this.getValue() / div);
        return this;
    }

    public MyValue sub(MyValue value) {
        this.setValue(this.getValue() - value.getValue());
        return this;
    }

    @Override
    public String toString() {
        return myValue.toString() ;
    }

    public MyValue copy(){
        return new MyValue(this.getValue());
    }
}
