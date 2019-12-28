package Gleichungssystem;

import Value.MyValue;

import java.util.Arrays;

public class GLS {
    private final boolean isCorrectGLS;
    private MyValue[][] glsToSolve;
    private MyValue[] result;
    private boolean isSolvable;

    public GLS(MyValue[][] glsToSolve) {
        this.glsToSolve = glsToSolve;
        isCorrectGLS = checkGLS_lengthOfRow();
        isSolvable = isCorrectGLS;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(glsToSolve);
    }

    /**
     * @return true if and only if every row in the GLS has the same amount of columns. Which means every row has the same length
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * @return The amount of rows in the GLS
     */
    int rowCount() {
        return glsToSolve.length;
    }

    /**
     * @return The amount of variables in the GLS
     * @throws UnsolvableGLSException If the GLS is not correct. Which means that all rows don't have the same length
     */
    int totalVariableAmount() throws UnsolvableGLSException {
        return columnCount() - 1;
    }

    /**
     * @return The amount of columns in the GLS
     * @throws UnsolvableGLSException If the GLS is not correct. Which means that all rows don't have the same length
     */
    int columnCount() throws UnsolvableGLSException {
        if (isCorrectGLS) {
            return glsToSolve[0].length;
        } else {
            throw new UnsolvableGLSException("All rows have to be the same length");
        }
    }

    /**
     * If this method returns without any Exception than the GLS has one unique solution
     *
     * @throws UnsolvableGLSException If the GLS does not have a solution
     */
    /* @throws NoDistinctGLSSolutionException If the GLS has more than one solution
     */
    public void solve() throws UnsolvableGLSException /*, NoDistinctGLSSolutionException*/ {
        int startIndex = 0;
        upperTriangularMatrix(startIndex);
        resolveMatrix();
    }

    void resolveMatrix() throws UnsolvableGLSException {
        // zeilenanzahl + 1 == spaltenanzahl => es kann eine Möglichkeit geben
        // zeilenanzahl + 1 < spaltenzahl => es gibt ein F.S. als Lösung
        // zeilenanzahl + 1 > spaltenzahl => es kann eine Möglichkeit geben, wenn es zeilenanzahl - 1 Nullzeilen gibt
        // => letzen beiden Möglichkeiten lasse ich erstmal außen vor
        final int totalVariableAmount = totalVariableAmount();
        result = new MyValue[totalVariableAmount];
        Arrays.setAll(result, v -> new MyValue());
        result[totalVariableAmount - 1] = glsToSolve[totalVariableAmount - 1][columnCount() - 1];
        resolveRecursively(totalVariableAmount - 1);
    }

    void resolveRecursively(int currentVariableIndex) throws UnsolvableGLSException {
        if (currentVariableIndex == 0) return;
        MyValue recursiveValue = new MyValue();
        final int totalVariableAmount = this.totalVariableAmount();
        for (int i = 0; i < totalVariableAmount - currentVariableIndex; i++) {
            MyValue nextVariableValue = result[currentVariableIndex + i];
            MyValue currentRecursiveValue = MyValue.multiply(glsToSolve[currentVariableIndex - 1][currentVariableIndex + i], nextVariableValue);
            recursiveValue.add(currentRecursiveValue);
        }
        MyValue nextValue = glsToSolve[currentVariableIndex - 1][columnCount() - 1].copy();
        nextValue.subtract(recursiveValue);
        result[currentVariableIndex - 1] = nextValue;
        resolveRecursively(currentVariableIndex - 1);
    }

    void reduceFirstColumnToOne(int currentIndex) {
        for (int i = currentIndex; i < glsToSolve.length; i++) {
            MyValue[] activeRow = glsToSolve[i];
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

    MyValue[][] getGlsToSolve() {
        return glsToSolve;
    }

    void upperTriangularMatrix(int currentIndex) {
        reduceFirstColumnToOne(currentIndex);

        if (currentIndex + 1 == glsToSolve.length || currentIndex + 1 == glsToSolve[0].length) return;

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
        if (isSolvable) return result;
        else return new MyValue[]{};
    }

    /**
     * Check the length of the rows -> all of them must have the same length
     *
     * @return {@code true} if all the rows have the same length
     */
    boolean checkGLS_lengthOfRow() {
        int lengthOfFirstRow = glsToSolve[0].length;
        int columnIndex = 1;
        do {
            if (glsToSolve[columnIndex].length != lengthOfFirstRow) {
                return false;
            }
            columnIndex++;
        } while (columnIndex < glsToSolve.length);
        return true;
    }

//    /**
//     * Checks the GLS whether at least one row contains only {@code 0}-s
//     *
//     * @return {@code true} if none of the rows contain a row of {@code 0}-s
//     */
//    boolean checkGLS_forNullRow() {
//        try {
//            MyValue[] nullRow = new MyValue[this.columnCount()];
//            for (int i = 0; i < this.columnCount(); i++) {
//                nullRow[i] = new MyValue();
//            }
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
