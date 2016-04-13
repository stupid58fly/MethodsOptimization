package methods;

public class Matrix2x2 {
    private final Integer n;
    private final double [][]matrix;
    
    public Matrix2x2() {
        this.n = 2;
        matrix = new double[n][n];
    }
    
    public Matrix2x2(final Matrix2x2 m) {
        this.n = 2;
        this.matrix = m.matrix.clone();
    }
    
    /**
     * Сравнение матриц.
     * @param m матрица для сравнения.
     * @return true если равны, иначе false.
     */
    public boolean equals(final Matrix2x2 m) {
        return matrix[0][0] == m.matrix[0][0] && 
               matrix[0][1] == m.matrix[0][1] && 
               matrix[1][0] == m.matrix[1][0] && 
               matrix[1][1] == m.matrix[1][1];
    }
    
    /**
     * Транспонирование матрицы
     */
    public void t() {
        double tmp = matrix[0][1];
        matrix[0][1] = matrix[1][0];
        matrix[1][0] = tmp;
    }
    
    /**
     * Умножение матриц this на m
     * @param m матрица, на котороую умнажают
     * @return матрица произведения.
     */
    public Matrix2x2 multiplication(final Matrix2x2 m) {
        Matrix2x2 ret = new Matrix2x2();
        ret.matrix[0][0] = matrix[0][0]*m.matrix[0][0] + matrix[0][1]*m.matrix[1][0];
        ret.matrix[0][1] = matrix[0][0]*m.matrix[1][0] + matrix[0][1]*m.matrix[1][1];
        ret.matrix[1][0] = matrix[1][0]*m.matrix[0][0] + matrix[1][1]*m.matrix[1][0];
        ret.matrix[1][1] = matrix[1][0]*m.matrix[1][0] + matrix[1][1]*m.matrix[1][1];
        return ret;
    }
    
    /**
     * Умножение матрицы на число.
     * @param num числ, на которое умножается матрица.
     */
    public void multiplication(final Double num) {
        matrix[0][0] *= num;
        matrix[0][1] *= num;
        matrix[1][0] *= num;
        matrix[1][1] *= num;
    }
    
    /**
     * Определитель матрицы.
     * @return определитель.
     */
    public double det() {
        return matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
    }
    
    /**
     * Возврат матрицы.
     * @return массив, содержащий матрицу.
     */
    public double[][] getMatrix() {
        return matrix.clone();
    }
    
    /**
     * Замена элемента матрицы с координатами i и j на element.
     * @param i номер строки.
     * @param j номер столбца.
     * @param element задаваемый элемент.
     * @throws ArrayIndexOutOfBoundsException если i или j выходят за рамки массива.
     */
    public void setMatrixElement(final Integer i, final Integer j, final Double element) /*throws ArrayIndexOutOfBoundsException*/{
        //if (i >= n || j >= n) throw new ArrayIndexOutOfBoundsException();
        matrix[i][j] = element;
    }
    
    /**
     * Инвертирование матрицы.
     */
    public void inverce() {
        double det = det();
        double tmp = matrix[0][0];
        matrix[0][0] = matrix[1][1] / det;
        matrix[1][1] = tmp / det;
        matrix[0][1] /= -det;
        matrix[1][0] /= -det;
    }
}
