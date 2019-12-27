package Gleichungssystem;

import Value.MyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GLSTest {

    GLS gls;
    MyValue[][] list;

    @BeforeEach
    void setUp() {
        list = new MyValue[][]{
                {new MyValue(4), new MyValue(2), new MyValue(5)},
                {new MyValue(3), new MyValue(0), new MyValue(7)}
        };

        gls = new GLS(list);
    }

    @Test
    void upperTriangularMatrix() {
        gls.upperTriangularMatrix(0);
        assert gls.getGlsToSolve()[0][0].equals(MyValue.ONE) : true;
        assert gls.getGlsToSolve()[1][1].equals(MyValue.ONE) : true;
    }

    @Test
    void checkGLS_lengthOfRow() {
        assert gls.checkGLS_lengthOfRow() : true;
    }

    @Test
    void isSolvable() {
        GLS testGLS = new GLS(new MyValue[][]{
                {new MyValue(2), new MyValue(4), new MyValue(6)},
                {new MyValue(4)}
        });
        assert !testGLS.isSolvable() : true;
        assert gls.isSolvable() : true;
    }

    @Test
    void checkGLS_forNullRow() {
        assert gls.checkGLS_forNullRow() : true;
    }

    @Test
    void solve() {
        // TODO write test
    }
}