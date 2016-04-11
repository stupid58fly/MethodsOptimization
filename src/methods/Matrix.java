package methods;

import java.util.Objects;


public class Matrix {
    private Integer n;
    private Double [][]matrix;
    
    public Matrix(final Integer n) {
        this.n = n;
        matrix = new Double[n][n];
    }
    
    public Matrix(final Matrix m) {
        this.n = m.n;
        this.matrix = m.matrix.clone();
    }
    
    /**
     * Транспонирование матрицы
     */
    public void t() {
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++) {
                double tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }   
    }
    
    /**
     * Умножение матриц this на m
     * @param m матрица, на котороую умнажают
     * @return матрица произведения. null если размеры матриц не совпадают.
     */
    public Matrix mul(Matrix m) {
        if (!Objects.equals(n, m.n)) return null;
        Matrix ret = new Matrix(n);
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                for(int k = 0; k < n; k++)
                    ret.matrix[i][j] += matrix[i][k]*m.matrix[k][j];
        return ret;
    }
}
