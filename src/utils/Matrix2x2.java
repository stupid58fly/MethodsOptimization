package utils;

import utils.Point;

public class Matrix2x2 {
    private final int n;
    private final double [][]matrix;
    
    public Matrix2x2() {
        this.n = 2;
        this.matrix = new double[n][n];
        matrix[0][0] = 1;
        matrix[1][1] = 1;
    }
    
    public Matrix2x2(final Matrix2x2 m) {
        this.n = 2;
        this.matrix = m.matrix.clone();
    }
    
    public Matrix2x2(final Point p1, final Point p2) {
        this.n = 2;
        this.matrix = new double[n][n];
        matrix[0][0] = p1.getX1()*p2.getX1();
        matrix[0][1] = p1.getX1()*p2.getX2();
        matrix[1][0] = p1.getX2()*p2.getX1();
        matrix[1][1] = p1.getX2()*p2.getX2();
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
     * Сложение матриц this и m.
     * @param m слогаемое.
     * @return сумма.
     */
    public Matrix2x2 plus(final Matrix2x2 m) {
        Matrix2x2 ret = new Matrix2x2();
        ret.matrix[0][0] = matrix[0][0] + m.matrix[0][0];
        ret.matrix[0][1] = matrix[0][1] + m.matrix[0][1];
        ret.matrix[1][0] = matrix[1][0] + m.matrix[1][0];
        ret.matrix[1][1] = matrix[1][1] + m.matrix[1][1];
        return ret;
    }
    
    /**
     * Вычитание матриц this и m.
     * @param m вычитаемое.
     * @return разность.
     */
    public Matrix2x2 minus(final Matrix2x2 m) {
        Matrix2x2 ret = new Matrix2x2();
        ret.matrix[0][0] = matrix[0][0] - m.matrix[0][0];
        ret.matrix[0][1] = matrix[0][1] - m.matrix[0][1];
        ret.matrix[1][0] = matrix[1][0] - m.matrix[1][0];
        ret.matrix[1][1] = matrix[1][1] - m.matrix[1][1];
        return ret;
    }
    
    /**
     * Умножение матриц this на m
     * @param m матрица, на котороую умнажают
     * @return матрица произведения.
     */
    public Matrix2x2 multiplication(final Matrix2x2 m) {
        Matrix2x2 ret = new Matrix2x2();
        ret.matrix[0][0] = matrix[0][0]*m.matrix[0][0] + matrix[0][1]*m.matrix[1][0];
        ret.matrix[0][1] = matrix[0][0]*m.matrix[0][1] + matrix[0][1]*m.matrix[1][1];
        ret.matrix[1][0] = matrix[1][0]*m.matrix[0][0] + matrix[1][1]*m.matrix[1][0];
        ret.matrix[1][1] = matrix[1][0]*m.matrix[0][1] + matrix[1][1]*m.matrix[1][1];
        return ret;
    }
    
    /**
     * Умножение матрицы на вектор-строку
     * @param p вектор, на который умножаем
     * @return результирующий вектор-строка
     */
    public Point multiplicationL(Point p) {
        return new Point(p.getX1()*matrix[0][0] + p.getX2()*matrix[1][0],
                         p.getX1()*matrix[0][1] + p.getX2()*matrix[1][1]);
    }
    
    /**
     * Умножение матрицы на вектор-столбец
     * @param p вектор, на который умножаем
     * @return результирующий вектор-столбец
     */
    public Point multiplicationR(Point p) {
        return new Point(p.getX1()*matrix[0][0] + p.getX2()*matrix[0][1],
                         p.getX1()*matrix[1][0] + p.getX2()*matrix[1][1]);
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
     */
    public void setMatrixElement(final int i, final int j, final double element) {
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
    
    @Override
    public String toString() {
        return matrix[0][0] + "\t" + matrix[0][1] + "\n" +
                matrix[1][0] + "\t" + matrix[1][1];
    }
}
