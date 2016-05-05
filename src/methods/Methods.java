package methods;

public class Methods {
    private final Function func;
    private double step;
    private Point p1;
    private Point p2;
    
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
     * Вычисляет значение функции с заданными параметрами: вектор координаты и параметр "а".
     * @param p вектор координат.
     * @return значение функции с заданными параметрами.
     */
    public double getFunctionValue(final Point p) {
        return func.getFunctionValue(p);
    }
    
    /**
     * Вычисление градиента функции в точке p.
     * @param p вектор координат.
     * @return значение градиента. В х1 - частная производная по х1, в х2 - частная производная по х2.
     */
    public Point getGradient(final Point p) {
        return func.gradient(p);
    }
    
    /**
     * @return шаг градиентного метода. alpha_k
     */
    public double getStep() {
        return step;
    }

    /**
     * @param step шаг градиентного метода для задания. alpha_k
     */
    public void setStep(double step) {
        this.step = step;
    }

    /**
     * @return координаты точки.
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @param p1 координаты точки для задания.
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * @return координаты точки для двушагового метода.
     */
    public Point getP2() {
        return p2;
    }

    /**
     * @param p2 координаты точки для двушагового метода для задания.
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }
    
    /**
     * Метод наискорейшего спуска.
     * @return координаты следующей точки.
     */
    public Point methodOfSteepestDescent() {
        //throw new UnsupportedOperationException("Метод наискорейшего спуска");

        return Point.minus(p1, Point.multiplication(func.gradient(p1), func.getAlpha(p1)));
    }
    
    /**
     * Градиентный метод с постоянным шагом.
     * @return координаты следующей точки.
     */
    public Point methodPitchwise() {
        Point g = func.gradient(p1);
        return Point.minus(p1, Point.multiplication(func.gradient(p1), step));
    }
    
    /**
     * Градиентный метод с дроблением шага.
     * @return координаты следующей точки.
     */
    public Point methodStepCrushing() {
        Point newPoint;
        do {
            newPoint = Point.minus(p1, Point.multiplication(func.gradient(p1), step));
            if(func.getFunctionValue(p1) < func.getFunctionValue(newPoint))
                step *= 0.5;
            else break;
        } while(true);   
        return newPoint;  
    }
    
    /**
     * Градиентный метод с убыванием длины шага как 1/k.
     * @param k номер шага.
     * @return координаты следующей точки.
     */
    public Point methodToDecreaseTheLengthOfThePitch(long k) {
        //throw new UnsupportedOperationException("Градиентный метод с убыванием длины шага как 1/k");
        return Point.minus(p1, Point.multiplication(func.gradient(p1), step/k));
        //return Point.minus(p, Point.multiplication(func.gradient(p), step));
    }
    
    /**
     * Градиентный метод оврагов.
     * @return координаты следующей точки.
     */
    public Point methodOfRavines() {
        throw new UnsupportedOperationException("Градиентный метод оврагов");
        //return Point.minus(p, Point.multiplication(func.gradient(p), step));
    }
    
    /**
     * Метод Ньютона.
     * @return координаты следующей точки.
     */
    public Point methodNewtons() {
        throw new UnsupportedOperationException("Метод Ньютона");
        //return Point.minus(p, Point.multiplication(func.gradient(p), step));
    }
    
    /**
     * Метод Давидона-Флетчера-Пауэла.
     * @return координаты следующей точки.
     */
    public Point methodDFP() {
        throw new UnsupportedOperationException("Метод Давидона-Флетчера-Пауэла");
        //return Point.minus(p, Point.multiplication(func.gradient(p), step));
    }
    
    /**
     * Метод Бройдена-Флетчера-Шанно.
     * @return координаты следующей точки.
     */
    public Point methodBFS() {
        throw new UnsupportedOperationException("Метод Бройдена-Флетчера-Шанно");
        //return Point.minus(p, Point.multiplication(func.gradient(p), step));
    }
}
