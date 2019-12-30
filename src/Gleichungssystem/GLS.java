package Gleichungssystem;

import Value.Field;
import Value.RealNumbers;

import java.util.Arrays;

public class GLS {
    private final boolean isCorrectGLS;
    private Field[][] glsToSolve;
    private Field[] result;
    private boolean isSolved;
    private int currentVariableIndex = 0;

    public GLS(Field[][] glsToSolve) {
        this.glsToSolve = glsToSolve;
        isCorrectGLS = allRowsHaveTheSameLength();
        isSolved = false;
    }

    void setCurrentVariableIndex(int currentVariableIndex) {
        this.currentVariableIndex = currentVariableIndex;
    }

//    public static Field[] solve(Field[][] glsToSolve) throws UnsolvableGLSException, UnsolvedGLSException {
//        GLS gls = new GLS(glsToSolve);
//        gls.solve();
//        return gls.getResult();
//    }

    Field[][] getGlsToSolve() {
        return glsToSolve;
    }

    /**
     * @throws UnsolvedGLSException Iff the GLS isn't solved
     */
    public Field[] getResult() throws UnsolvedGLSException {
        if (isSolved) return result;
        throw new UnsolvedGLSException();
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

        Field[] nullRow = RealNumbers.ZERO.getNullRow(this.columnCount());

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
        upperTriangularMatrix();
        resolveMatrix();
        if (result.length + nullRowCount() < rowCount()) {
            throw new UnsolvableGLSException();
        } else {
            isSolved = true;
        }
    }

    private Field[] getNullField(int lengthOfField) {
        Field[] value = new Field[lengthOfField];
        Arrays.setAll(value, v -> RealNumbers.ZERO);
        return value;
    }

    void resolveMatrix() throws UnsolvableGLSException {
        final int totalVariableAmount = totalVariableAmount();
        result = getNullField(totalVariableAmount);
        result[totalVariableAmount - 1] = glsToSolve[totalVariableAmount - 1][columnCount() - 1];
        resolveRecursively(totalVariableAmount - 1);
    }

    void resolveRecursively(int currentVariableIndex) throws UnsolvableGLSException {
        if (currentVariableIndex == 0) return;
        Field recursiveValue = RealNumbers.ZERO;
        final int totalVariableAmount = this.totalVariableAmount();
        for (int i = 0; i < totalVariableAmount - currentVariableIndex; i++) {
            Value.Field nextVariableValue = result[currentVariableIndex + i].clone();
            nextVariableValue.multiply(glsToSolve[currentVariableIndex - 1][currentVariableIndex + i]);
            recursiveValue.add(nextVariableValue);
        }
        Value.Field nextValue = glsToSolve[currentVariableIndex - 1][columnCount() - 1].clone();
        nextValue.subtract(recursiveValue);
        result[currentVariableIndex - 1] = nextValue;
        resolveRecursively(currentVariableIndex - 1);
    }

    void upperTriangularMatrix() throws UnsolvableGLSException {
        reduceFirstColumnToOne();
        if (isValidVariableIndex()) {
            glsToUppterTriangularMatrix();
            currentVariableIndex++;
            upperTriangularMatrix();
        }
    }

    private boolean isValidVariableIndex() throws UnsolvableGLSException {
        return currentVariableIndex + 1 != rowCount() && currentVariableIndex + 1 != columnCount();
    }

    void reduceFirstColumnToOne() {
        for (int currentRowIndex = currentVariableIndex; currentRowIndex < rowCount(); currentRowIndex++) {
            divideRowByFirstValueOfTheRow(glsToSolve[currentRowIndex]);
        }
    }

    private void divideRowByFirstValueOfTheRow(Field[] activeRow1) {
        Field valueInTheFirstColumn = activeRow1[currentVariableIndex].clone();
        if (isFirstValueOfRowZeroOrOne(valueInTheFirstColumn)) {
            return;
        }

        for (int columnCount = currentVariableIndex; columnCount < activeRow1.length; columnCount++) {
            try {
                activeRow1[columnCount].divide(valueInTheFirstColumn);
            } catch (ArithmeticException ignored) {
                // will never happen
            }
        }
    }

    private boolean isFirstValueOfRowZeroOrOne(Field valueInTheFirstColumn) {
        return valueInTheFirstColumn.equals(RealNumbers.ZERO) || valueInTheFirstColumn.equals(RealNumbers.ONE);
    }

    private void glsToUppterTriangularMatrix() {
        for (int rowCount = currentVariableIndex + 1; rowCount < glsToSolve.length; rowCount++) {
            subtractFirstRowFromOtherRow(glsToSolve[rowCount]);
        }
    }

    private void subtractFirstRowFromOtherRow(Field[] otherRow) {
        Field[] firstRow = glsToSolve[currentVariableIndex];
        if (!otherRow[currentVariableIndex].equals(RealNumbers.ZERO)) {
            for (int columnCount = currentVariableIndex; columnCount < firstRow.length; columnCount++) {
                otherRow[columnCount].subtract(firstRow[columnCount]);
            }
        }
    }

    /**
     * Check the length of the rows -> all of them must have the same length
     *
     * @return {@code true} if all the rows have the same length
     */
    boolean allRowsHaveTheSameLength() {
        for (int columnIndex = 1; columnIndex < rowCount(); columnIndex++) {
            if (glsToSolve[columnIndex].length != glsToSolve[0].length) {
                return false;
            }
        }
        return true;
    }
}
