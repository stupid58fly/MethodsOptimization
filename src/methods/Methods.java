package methods;

public class Methods {
    private final Function func;
    
    public Methods() {
        func = new Function();
    }
    
    public Methods(final Double param) {
        func = new Function(param);
    }
    
    /**
     * Возврат параметра фанкции.
     * @return параметр функции.
     */
    public Double getParameter() {
        return func.getParameter();
    }
    
    /**
     * Изменение значения параметра функции.
     * @param param новый параметр функции.
     */
    public void setParameter(final Double param) {
        func.setParameter(param);
    }
    
    /**
     * Метод наискорейшего спуска.
     * @param p координаты точки.
     * @return координаты следующей точки.
     */
    public Point steepestDescentMethod(final Point p) {
        
        return null;
    }
    
    /**
     * Градиентный метод с постоянным шагом.
     * @param p координаты точки.
     * @param step длина шага. Параметр alpha_k.
     * @return координаты следующей точки.
     */
    public Point gradientMethodWithConstantPitch(final Point p, final Double step) {
        return Point.minus(p, Point.multiplication(func.gradient(p), step));
    }
}
