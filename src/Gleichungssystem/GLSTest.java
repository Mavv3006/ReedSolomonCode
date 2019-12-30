package Gleichungssystem;

import Value.RealNumbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

class GLSTest {

    GLS twoByTwo;
    GLS threeByThree;

    @BeforeEach
    void setUp() {
        RealNumbers[][] myValueArray;

        myValueArray = new RealNumbers[][]{
                {new RealNumbers(4), new RealNumbers(2), new RealNumbers(5)},
                {new RealNumbers(3), new RealNumbers(0), new RealNumbers(7)}
        };
        twoByTwo = new GLS(myValueArray);

        myValueArray = new RealNumbers[][]{
                {new RealNumbers(3), new RealNumbers(3), new RealNumbers(3), new RealNumbers(3)},
                {new RealNumbers(4), new RealNumbers(4), new RealNumbers(4), new RealNumbers(4)},
                {new RealNumbers(5), new RealNumbers(5), new RealNumbers(5), new RealNumbers(5)},
        };
        threeByThree = new GLS(myValueArray);
    }

    @Test
    void upperTriangularMatrix() {
        try {
            twoByTwo.setCurrentVariableIndex(0);
            twoByTwo.upperTriangularMatrix();
        } catch (UnsolvableGLSException e) {
            e.printStackTrace();
            fail();
        }
        assert twoByTwo.getGlsToSolve()[0][0].equals(RealNumbers.ONE) : true;
        assert twoByTwo.getGlsToSolve()[1][1].equals(RealNumbers.ONE) : true;
    }

    @Test
    void checkGLS_lengthOfRow() {
        assert twoByTwo.allRowsHaveTheSameLength() : true;
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
        RealNumbers[][] list = new RealNumbers[][]{
                {new RealNumbers(1), new RealNumbers(1), new RealNumbers(7)},
                {new RealNumbers(3), new RealNumbers(5), new RealNumbers(12)},
                {new RealNumbers(9), new RealNumbers(15), new RealNumbers(36)},
                {new RealNumbers(6), new RealNumbers(10), new RealNumbers(24)}
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