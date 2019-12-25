package Gleichungssystem;

import Value.MyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GLS {
    private MyValue[][] gls;
    private MyValue[] result;

    public GLS(MyValue[][] gls) {
        this.gls = gls;
    }

    public int rowCount() {
        return gls.length;
    }

    public int rowLength() throws Exception {
        if (checkGLS_lengthOfRow()) {
            return gls[0].length;
        } else {
            throw new Exception("All rows have to be the same length");
        }
    }

    public boolean solve() {
//        if (!checkGLS()) return false;

        // Turn first values of all rows to 1
        return true;
    }

    /**
     * Reduces the Values in the first Column to One and divides
     * all other Values through the old Value in Columnn One
     *
     * @return {@code MyValue[][]}
     */
    private MyValue[][] reduceToOne(MyValue[][] gls) {
        for (MyValue[] row : gls) {
            int i = 0;
            if (i < row.length - 1) {
                do {
                    try {
                        MyValue value = row[i].copy();
                        for (int j = 0; j < row.length; j++) {
                            row[j] = row[j].div(value);
                        }
                        break;
                    } catch (ArithmeticException ignored) {
                    }
                    i++;
                } while (i < row.length - 1);
            }
        }
        return gls;
    }

    public boolean zeilenStufenForm(int startIndex, MyValue[][] gls) {
        MyValue[][] reduced_gls = reduceToOne(gls);
        MyValue[] row1 = gls[startIndex];

        for (int i = startIndex + 1; i < gls.length; i++) {
            MyValue[] other_row = gls[i];
            // Subtract from other row the first one
            for (int j = 0; j < row1.length; j++) {
                other_row[j].sub(row1[j]);
            }
        }
        return true;
    }

    public MyValue[] getResult() {
        return result;
    }

    /**
     * Checks whether a List of Lists of Numbers are a valid GLS:<br>
     * - all rows have the same length<br>
     * - {@code rowCount + 1 = rowLength}
     */
    public boolean checkGLS() {
        try {
            int rowCount = this.rowCount();
            int rowLength = this.rowLength();
            return rowCount + 1 == rowLength;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check the length of the rows -> all of them must have the same length
     *
     * @return {@code true} if all the rows have the same length
     */
    public boolean checkGLS_lengthOfRow() {
        List<Integer> length = new ArrayList<>();
        for (int i = 0, glsSize = gls.length; i < glsSize; i++) {
            length.add(i, gls[i].length);
        }
        Integer firstElement = length.get(0);
        for (int i = 0; i < length.size(); i++) {
            Integer e = length.get(i);
            e -= firstElement;
            length.remove(i);
            length.add(i, e);
        }
        for (Integer e : length) {
            if (e != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the GLS whether at least one row contains only {@code 0}-s
     *
     * @return {@code true} if none of the rows contain a row of {@code 0}-s
     */
    public boolean checkGLS_forNullRow() {
        try {
            MyValue[] nullRow = new MyValue[this.rowLength()];
            for (int i = 0; i < this.rowLength(); i++) {
                nullRow[i] = new MyValue(0);
            }
            System.out.println("nullRow: " + Arrays.toString(nullRow));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
