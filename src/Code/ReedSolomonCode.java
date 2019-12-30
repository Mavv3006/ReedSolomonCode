package Code;

import Value.Field;

public class ReedSolomonCode extends MyCode {
    private Field[][] paritaetsPruefmatrix;
    private Field[][] erzeugerMatrix;
    private Field[] message;
    private Field[] syndroms;

    public ReedSolomonCode(Field[] matrixBasis) {
        this.paritaetsPruefmatrix = calculateParitaetsPruefmatrix(matrixBasis);
        this.erzeugerMatrix = calculateErzeugermatrix(matrixBasis);
    }

    public Field[] encode(Field[] messageToEncode) {
        this.message = messageToEncode;

        return new Field[]{};
    }

    public Field[] decode(Field[] messageToDecode) {
        this.message = messageToDecode;
        return new Field[]{};
    }

    Field[][] calculateParitaetsPruefmatrix(Field[] matrixBasis) {
        return new Field[][]{};
    }

    Field[][] calculateErzeugermatrix(Field[] matrixBasis) {
        return new Field[][]{};
    }

    void calculateSyndroms() {
        int n = getN();
        int k = getK();
        this.syndroms = new Field[n - k];
//        for (int l = 0; l <= n - k - 1; l++) {
//
//            for (int i = 1; i < n; i++) {
//
//            }
//        }
    }

    void correctMessage() {
    }
}
