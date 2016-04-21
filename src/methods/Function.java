package methods;

public class Function {
    private Double parameter;
    
    /**
     * Конструктор без задания параметра. Параметр по-умолчанию равен 0.
     */
    public Function() {
        parameter = 0.0;
    }
    
    /**
     * Конструктор с заданием параметра а.
     * @param p параметр функции.
     */
    public Function(final Double p) {
        parameter = p;
    }
    
    /**
     * Возвращение параметра функции.
     * @return параметра "а".
     */
    public Double getParameter() {
        return parameter;
    }
    
    /**
     * Задание параметра функции.
     * @param p параметр "а".
     */
    public void setParameter(final Double p) {
        parameter = p;
    }
    
    /**
     * Вычисляет значение функции с заданными параметрами: вектор координаты и параметр "а".
     * @param p вектор координат.
     * @return значение функции с заданными параметрами.
     */
    public Double getFunctionValue(final Point p) {
        return Math.pow((p.getX2() + Math.pow(p.getX1(), 2)), 2) + parameter*Math.pow((p.getX1() -1), 2);
    }
    
    /**
     *
     * @param p точка, для которой ищется alpha.
     * @return значение alpha для заданой точки для метода наискорейшего спуска.
     */
    public Double getAlpha(final Point p) {
        Point grad = gradient(p);
        if (0.0 == grad.getX2()) return 0.0;
        return (p.getX1() - p.getX2()*p.getX2())/grad.getX2();
    }
    
    /**
     * Вычисление градиента функции в точке p.
     * @param p вектор координат.
     * @return значение градиента. В х1 - частная производная по х1, в х2 - частная производная по х2.
     */
    public Point gradient(final Point p) {
        return new Point(4*p.getX1()*(Math.pow(p.getX1(), 2) - p.getX2()) - 2*parameter*(p.getX1() - 1), 
                   2*(p.getX1() - Math.pow(p.getX2(), 2)));
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
