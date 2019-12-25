package Test;

import Gleichungssystem.GLS;
import Value.MyValue;

import java.util.Arrays;

class CheckGLSTest {
    GLS gls;
    MyValue[][] list;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        list = new MyValue[][]{
                {new MyValue(3), new MyValue(3), new MyValue(3), new MyValue(3)},
                {new MyValue(4), new MyValue(4), new MyValue(4), new MyValue(4)},
                {new MyValue(5), new MyValue(5), new MyValue(5), new MyValue(5)},
        };

        gls = new GLS(list);
    }

    @org.junit.jupiter.api.Test
    void testCheckLengthOfRows() {
        assert gls.checkGLS_lengthOfRow() : true;
    }

    @org.junit.jupiter.api.Test
    void testCheckGLS() {
        assert gls.checkGLS() : true;
    }

    @org.junit.jupiter.api.Test
    void testCheckForNullRow() {
        assert gls.checkGLS_forNullRow() : true;
    }

    @org.junit.jupiter.api.Test
    void testUpperTriangularMatrix() {
        gls.upperTriangularMatrix(0);
        assert gls.getGlsToSolve()[0][list.length - 1].equals(MyValue.ONE) : true;
        assert gls.getGlsToSolve()[2][2].equals(MyValue.ZERO) : true;
    }

    @org.junit.jupiter.api.Test
    void testNoResult() {
        gls.solve();
        System.out.println(Arrays.toString(gls.getResult()));
        assert gls.getResult() == null : true;
    }
}