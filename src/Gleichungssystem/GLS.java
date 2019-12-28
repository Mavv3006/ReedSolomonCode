package Gleichungssystem;

import Value.MyValue;

import java.util.Arrays;

public class GLS {
    private final boolean isCorrectGLS;
    private MyValue[][] glsToSolve;
    private MyValue[] result;

    public GLS(MyValue[][] glsToSolve) {
        this.glsToSolve = glsToSolve;
        isCorrectGLS = checkGLS_lengthOfRow();
    }

    @Override
    public String toString() {
        return Arrays.deepToString(glsToSolve);
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
     * Counts how many rows in the GLS have only {@code 0}-s in it
     *
     * @return The amount of rows which contains only {@code 0}-s
     * @throws UnsolvableGLSException Iff the GLS is not correct. Which means that all rows don't have the same length
     */
    private int nullRowCount() throws UnsolvableGLSException {
        int nullRowCounter = 0;
        MyValue[] nullRow = MyValue.getNullRow(this.columnCount());

        for (int i = 0; i < rowCount(); i++) {
            if (Arrays.equals(glsToSolve[i], nullRow)) {
                nullRowCounter++;
            }
        }
        return nullRowCounter;
    }

    /**
     * If this method returns without any Exception than the GLS has one unique solution
     *
     * @throws UnsolvableGLSException If the GLS does not have a solution
     */
    public void solve() throws UnsolvableGLSException {
        int startIndex = 0;
        upperTriangularMatrix(startIndex);
        resolveMatrix();
        if (result.length + nullRowCount() < rowCount()) {
            throw new UnsolvableGLSException();
        }
    }

    void resolveMatrix() throws UnsolvableGLSException {
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
        if (isCorrectGLS) return result;
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
}
