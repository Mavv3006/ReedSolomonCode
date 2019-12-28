package Gleichungssystem;

import Value.MyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

class GLSTest {

    GLS twoByTwo;
    GLS threeByThree;

    @BeforeEach
    void setUp() {
        MyValue[][] myValueArray;

        myValueArray = new MyValue[][]{
                {new MyValue(4), new MyValue(2), new MyValue(5)},
                {new MyValue(3), new MyValue(0), new MyValue(7)}
        };
        twoByTwo = new GLS(myValueArray);

        myValueArray = new MyValue[][]{
                {new MyValue(3), new MyValue(3), new MyValue(3), new MyValue(3)},
                {new MyValue(4), new MyValue(4), new MyValue(4), new MyValue(4)},
                {new MyValue(5), new MyValue(5), new MyValue(5), new MyValue(5)},
        };
        threeByThree = new GLS(myValueArray);
    }

    @Test
    void upperTriangularMatrix() {
        twoByTwo.upperTriangularMatrix(0);
        assert twoByTwo.getGlsToSolve()[0][0].equals(MyValue.ONE) : true;
        assert twoByTwo.getGlsToSolve()[1][1].equals(MyValue.ONE) : true;
    }

    @Test
    void checkGLS_lengthOfRow() {
        assert twoByTwo.checkGLS_lengthOfRow() : true;
    }

    @Test
    void solve() {
        try {
            twoByTwo.solve();
            System.out.println("GLS: " + twoByTwo);
            System.out.println("Result: " + Arrays.toString(twoByTwo.getResult()));
            System.out.println();
            threeByThree.solve();
            System.out.println("GLS: " + threeByThree);
            System.out.println("Result: " + Arrays.toString(threeByThree.getResult()));

        } catch (UnsolvableGLSException | UnsolvedGLSException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testOtherMatrixDimensions() {
        MyValue[][] list = new MyValue[][]{
                {new MyValue(1), new MyValue(1), new MyValue(7)},
                {new MyValue(3), new MyValue(5), new MyValue(12)},
                {new MyValue(9), new MyValue(15), new MyValue(36)},
                {new MyValue(6), new MyValue(10), new MyValue(24)}
        };

        GLS gls = new GLS(list);

        try {
            gls.solve();
            assert gls.getResult()[0].getValue() == 11.5 : true;
            assert gls.getResult().length == 2 : true;
        } catch (UnsolvableGLSException | UnsolvedGLSException e) {
            e.printStackTrace();
            fail();
        }
    }
}