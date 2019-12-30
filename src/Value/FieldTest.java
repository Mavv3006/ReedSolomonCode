package Value;

import org.junit.jupiter.api.Test;

class FieldTest {

    @Test
    void testEquals() {
        Field realNumber = new RealNumbers(0);
        Field primeNumber = new PrimeNumber(0, 0);

        assert realNumber.equals(RealNumbers.ZERO) : true;
        assert primeNumber.equals(RealNumbers.ZERO) : true;
    }
}