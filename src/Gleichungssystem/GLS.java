package Gleichungssystem;

import Value.MyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GLS {
    private MyValue[][] glsToSolve;
    private MyValue[] result;

    public GLS(MyValue[][] glsToSolve) {
        this.glsToSolve = glsToSolve;
    }

    public int rowCount() {
        return glsToSolve.length;
    }

    public int columnCount() throws Exception {
        if (checkGLS_lengthOfRow()) {
            return glsToSolve[0].length;
        } else {
            throw new Exception("All rows have to be the same length");
        }
    }

    public void solve() {
        int startIndex = 0;
        upperTriangularMatrix(startIndex);
        resolveMatrix();
    }

    private void resolveMatrix() {
        // zeilenanzahl + 1 == spaltenanzahl => es kann eine Möglichkeit geben
        // zeilenanzahl + 1 < spaltenzahl => es gibt ein F.S. als Lösung
        // zeilenanzahl + 1 > spaltenzahl => es kann eine Möglichkeit geben, wenn es zeilenanzahl - 1 Nullzeilen gibt
        // => letzen beiden Möglichkeiten lasse ich erstmal außen vor
        int columnAmount = glsToSolve[0].length;
        int resultColumnIndex = glsToSolve[0].length - 1;
        if (glsToSolve.length + 1 == glsToSolve[0].length) {
            MyValue[] temporaryResultList = new MyValue[columnAmount - 1];
            for (int rowCount = glsToSolve.length - 1; rowCount >= 0; rowCount--) {
                // TODO: solve this issue
            }
            result = temporaryResultList;
        }
    }

    private void reduceFirstColumnToOne(int currentIndex) {
        for (MyValue[] activeRow : glsToSolve) {
            MyValue valueInTheFirstColumn = activeRow[currentIndex].copy();
            if (valueInTheFirstColumn.equals(MyValue.ZERO) || valueInTheFirstColumn.equals(MyValue.ONE)) continue;

            for (int columnCount = currentIndex; columnCount < activeRow.length; columnCount++) {
                try {
                    activeRow[columnCount].divideBy(valueInTheFirstColumn);
                } catch (ArithmeticException ignored) {
                }
            }
        }
    }

    public MyValue[][] getGlsToSolve() {
        return glsToSolve;
    }

    public void upperTriangularMatrix(int currentIndex) {
        if (currentIndex + 1 == glsToSolve.length || currentIndex + 1 == glsToSolve[0].length) return;

        reduceFirstColumnToOne(currentIndex);
        MyValue[] firstRow = glsToSolve[currentIndex];

        for (int rowCount = currentIndex + 1; rowCount < glsToSolve.length; rowCount++) {
            MyValue[] otherRow = glsToSolve[rowCount];
            if (otherRow[currentIndex].equals(MyValue.ZERO)) continue;
            for (int columnCount = currentIndex; columnCount < firstRow.length; columnCount++) {
                otherRow[columnCount].subtract(firstRow[columnCount]);
            }
        }

        upperTriangularMatrix(currentIndex + 1);
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
            int rowLength = this.columnCount();
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
        for (int i = 0, glsSize = glsToSolve.length; i < glsSize; i++) {
            length.add(i, glsToSolve[i].length);
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
            MyValue[] nullRow = new MyValue[this.columnCount()];
            for (int i = 0; i < this.columnCount(); i++) {
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
