package methods;

public class Methods {
    private final Function func;
    private final java.util.List<String> names;
    
    public Methods() {
        func = new Function();
        names = new java.util.ArrayList<>();
        names.add("Метод наискорейшего спуска");
        names.add("Метод с постоянным шагом");
        names.add("Метод с дроблением шага");
        names.add("Метод с убыванием длины шага как 1/k");
        names.add("Метод оврагов");
        names.add("Метод Ньютона");
        names.add("Метод Давидона-Флетчера-Пауэла");
        names.add("Метод Бройдена-Флетчера-Шанно");
    }
    
    public Methods(final Double param) {
        func = new Function(param);
        names = new java.util.ArrayList<>();
        names.add("Метод наискорейшего спуска");
        names.add("Метод с постоянным шагом");
        names.add("Метод с дроблением шага");
        names.add("Метод с убыванием длины шага как 1/k");
        names.add("Метод оврагов");
        names.add("Метод Ньютона");
        names.add("Метод Давидона-Флетчера-Пауэла");
        names.add("Метод Бройдена-Флетчера-Шанно");
    }
    
    /**
     * @return имена методов оптимизации.
     */
    public final java.util.List<String> names() {
        return names;
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
        return Point.minus(p, Point.multiplication(func.gradient(p), func.getAlpha(p)));
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
