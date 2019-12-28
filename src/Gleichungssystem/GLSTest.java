package Gleichungssystem;

import Value.MyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class GLSTest {

    GLS gls1;
    GLS gls2;
    MyValue[][] list;

    @BeforeEach
    void setUp() {
        list = new MyValue[][]{
                {new MyValue(4), new MyValue(2), new MyValue(5)},
                {new MyValue(3), new MyValue(0), new MyValue(7)}
        };
        gls1 = new GLS(list);

        list = new MyValue[][]{
                {new MyValue(3), new MyValue(3), new MyValue(3), new MyValue(3)},
                {new MyValue(4), new MyValue(4), new MyValue(4), new MyValue(4)},
                {new MyValue(5), new MyValue(5), new MyValue(5), new MyValue(5)},
        };
        gls2 = new GLS(list);

    }

    @Test
    void upperTriangularMatrix() {
        gls1.upperTriangularMatrix(0);
        assert gls1.getGlsToSolve()[0][0].equals(MyValue.ONE) : true;
        assert gls1.getGlsToSolve()[1][1].equals(MyValue.ONE) : true;
    }

    @Test
    void checkGLS_lengthOfRow() {
        assert gls1.checkGLS_lengthOfRow() : true;
    }

    @Test
    void isSolvable() {
        GLS testGLS = new GLS(new MyValue[][]{
                {new MyValue(2), new MyValue(4), new MyValue(6)},
                {new MyValue(4)}
        });
        assert !testGLS.isSolvable() : true;
        assert gls1.isSolvable() : true;
    }

    @Test
    void solve() {
        try {
            gls1.solve();
            System.out.println("GLS: " + gls1);
            System.out.println("Result: " + Arrays.toString(gls1.getResult()));
            System.out.println();
            gls2.solve();
            System.out.println("GLS: " + gls2);
            System.out.println("Result: " + Arrays.toString(gls2.getResult()));

        } catch (UnsolvableGLSException /*| NoDistinctGLSSolutionException*/ e) {
            e.printStackTrace();
        }
    }
}