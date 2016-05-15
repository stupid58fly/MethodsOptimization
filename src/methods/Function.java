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
     * @param grad вектор, стоящий при alpha в формуле пересчёта.
     * @return значение alpha для заданой точки для метода наискорейшего спуска.
     */
    protected Double getAlpha(final Point p, final Point grad) {
        double x1 = p.getX1();
        double x2 = p.getX2();
        double g1 = grad.getX1();
        double g2 = grad.getX2();
        
        double k3 = 4 * Math.pow(g1, 4.0);
        double k2 = 6 * Math.pow(g1, 2.0) * (g2 - 2 * x1*g1);
        double k1 = 2 * (Math.pow(2 * x1*g1 - g2, 2) + g1*g1 * (2 * x1*x1 - 2 * x2 + a));
        double k0 = 2*((x1*x1 - x2)*(g2 - 2 * x1*g1)-
                a * g1 * (x1 - 1));

        double x_old;
        double x_new = 0;
        
        do {
            x_old = x_new;
            x_new = x_old - (k3*Math.pow(x_old, 3)+k2*Math.pow(x_old, 2)+k1*x_old+k0)/(3*k3*Math.pow(x_old, 2)+2*k2*x_old+k1);
        } while (Math.abs(x_new - x_old) > 1e-15);
                
        return x_old;
    }
    
    /**
     * Вычисление градиента функции в точке p.
     * @param p вектор координат.
     * @return значение градиента. В х1 - частная производная по х1, в х2 - частная производная по х2.
     */
    public Point gradient(final Point p) {
        double x1 = p.getX1();
        double x2 = p.getX2();
        Point ret = new Point(4 * x1 * (x1*x1 - x2) + 2 * a * (x1 - 1), 
                   2*(x2 - x1*x1));
        return ret;
    }
    
    /**
     * Вычисление второго градиента в точке с координатами p. grad(grad(f)).
     * @param p вектор координат.
     * @return значение второго градиента.
     */
    public Matrix2x2 gradient2(final Point p) {
        Matrix2x2 m = new Matrix2x2();
        double a11 = 12.0*Math.pow(p.getX1(), 2.0) - 4.0*p.getX2() + 2.0*a;
        double a12 = -4.0*p.getX1();
        double a22 = 2.0;
        
        m.setMatrixElement(0, 0, a11);
        m.setMatrixElement(0, 1, a12);
        m.setMatrixElement(1, 0, a12);
        m.setMatrixElement(1, 1, a22);
        return m;
    }
    
    /**
     * @return минимум знначения фкнкции.
     */
    public Point getResult() {
        return new Point(1.0, 1.0);
    }
}
