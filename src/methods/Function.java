package methods;

public class Function {
    private Double parametr;
    
    /**
     * Конструктор без задания параметра. Параметр по-умолчанию равен 0.
     */
    public Function() {
        parametr = 0.0;
    }
    
    /**
     * Конструктор с заданием параметра а.
     * @param p параметр функции.
     */
    public Function(Double p) {
        parametr = p;
    }
    
    /**
     * Возвращение параметра функции.
     * @return параметра "а".
     */
    public Double getParameter() {
        return parametr;
    }
    
    /**
     * Задание параметра функции.
     * @param p параметр "а".
     */
    public void setParameter(final Double p) {
        parametr = p;
    }
    
    /**
     * Вычисляет значение функции с заданными параметрами: вектор координаты и параметр "а".
     * @param p вектор координат.
     * @return значение функции с заданными параметрами.
     */
    public Double getFunctionValue(Point p) {
        return Math.pow((p.getX2() + Math.pow(p.getX1(), 2)), 2) + parametr*Math.pow((p.getX1() -1), 2);
    }
    
    public Point gradient(Point p) {
        return new Point(4*p.getX1()*(Math.pow(p.getX1(), 2) - p.getX2()) - 2*parametr*(p.getX1() - 1), 
                   2*(p.getX1() - Math.pow(p.getX2(), 2)));
    }
}
