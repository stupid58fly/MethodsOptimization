package methods;

public class Function {
    private Double a;
    
    /**
     * Конструктор без задания параметра. Параметр по-умолчанию равен 0.
     */
    public Function() {
        a = 0.0;
    }
    
    /**
     * Конструктор с заданием параметра а.
     * @param p параметр функции.
     */
    public Function(final Double p) {
        a = p;
    }
    
    /**
     * Возвращение параметра функции.
     * @return параметра "а".
     */
    public Double getParameter() {
        return a;
    }
    
    /**
     * Задание параметра функции.
     * @param p параметр "а".
     */
    public void setParameter(final Double p) {
        a = p;
    }
    
    /**
     * Вычисляет значение функции с заданными параметрами: вектор координаты и параметр "а".
     * @param p вектор координат.
     * @return значение функции с заданными параметрами.
     */
    public Double getFunctionValue(final Point p) {
        double x1 = p.getX1();
        double x2 = p.getX2();
        return Math.pow((x2 - x1*x1), 2) + a*Math.pow((x1 - 1), 2);
    }
    
    /**
     *
     * @param p точка, для которой ищется alpha.
     * @return значение alpha для заданой точки для метода наискорейшего спуска.
     */
    public Double getAlpha(final Point p) {
        double x1 = p.getX1();
        double x2 = p.getX2();
        Point grad = gradient(p);
        double g1 = grad.getX1();
        double g2 = grad.getX2();
        
        double k3 = 4 * g1;
        double k2 = 6 * g1 * (g2 - 2 * x1*x1);
        double k1 = 2 * (Math.pow(2 * x1*x1 - g2, 2) + g1*g1 * (2 * x1*x1 - 2 * x2 + a));
        double k0 = 2*((x1*x1 - x2)*(x2 - 2 * x1*x1)-
                a * x1 * (x1 - 1));
        
        if (k3 != 0) {
            //вписать длинную формулу от 3 степени
            throw new UnsupportedOperationException("Вычисление alpha_k для наискорейшего спуска производной 3-й степени не поддерживается");
        }
        if (k2 != 0) {
            return (Math.sqrt(k1*k1-4*k0*k2) - k1)/(2*k2);
        }
        if (k1 != 0) {
            return -k0/k1;
        }
        return 0.0;
    }
    
    /**
     * Вычисление градиента функции в точке p.
     * @param p вектор координат.
     * @return значение градиента. В х1 - частная производная по х1, в х2 - частная производная по х2.
     */
    public Point gradient(final Point p) {
        double x1 = p.getX1();
        double x2 = p.getX2();
        return new Point(4 * x1 * (x1*x1 - x2) + 2 * a * (x1 - 1), 
                   2*(x2 - x1*x1));
    }
    
    /**
     * Вычисление второго градиента в точке с координатами p. grad(grad(f)).
     * @param p вектор координат.
     * @return значение второго градиента.
     */
    public Matrix2x2 gradient2(final Point p) {
        Matrix2x2 m = new Matrix2x2();
        m.setMatrixElement(0, 0, 12*Math.pow(p.getX1(), 2) - 4*p.getX2());
        m.setMatrixElement(0, 1, -4*p.getX1());
        m.setMatrixElement(1, 0, -4*p.getX1());
        m.setMatrixElement(1, 1, 2.0);
        return m;
    }
}
