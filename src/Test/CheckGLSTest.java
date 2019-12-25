package Test;

import Gleichungssystem.GLS;
import Value.MyValue;

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
    void testZeilenStufenForm() {
        assert gls.zeilenStufenForm(0, list) : true;
    }
}