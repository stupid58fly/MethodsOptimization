package methods;

public class Methods extends Function{
    private double step;
    private double h;
    private Matrix2x2 H;
    private double cos_k;
    
    public Methods() {
        cos_k = 0;
        step = 0;
        h = 1;
        H = new Matrix2x2();
    }
    
    public Methods(final Double param) {
        super(param);
        cos_k = 0;
        step = 0;
        h = 1;
        H = new Matrix2x2();
    }
    
    /**
     * Метод наискорейшего спуска.
     * @param p координаты точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodOfSteepestDescent(final Point p) {
        return Point.minus(p, Point.multiplication(gradient(p), getAlpha(p, gradient(p))));
    }
    
    /**
     * Градиентный метод с постоянным шагом.
     * @param p координаты точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodPitchwise(final Point p) {
        Point grad = gradient(p);
        grad.multiplication(step);
        return Point.minus(p, grad);
    }
    
    /**
     * Градиентный метод с дроблением шага.
     * @param p координаты точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodStepCrushing(final Point p) {
        Point newPoint;
        Point grad =  gradient(p);
        grad.multiplication(1.0/grad.norma());
        do {
            newPoint = Point.minus(p, Point.multiplication(grad, step));
            if(getFunctionValue(p) < getFunctionValue(newPoint))
                step *= 0.5;
            else break;
        } while(true);   
        return newPoint;  
    }
    
    /**
     * Градиентный метод с убыванием длины шага как 1/k.
     * @param p координаты точки для спуска.
     * @param k номер шага.
     * @return координаты следующей точки.
     */
    public Point methodToDecreaseTheLengthOfThePitch(final Point p, final long k) {
        Point grad = gradient(p);
        grad.multiplication(step/grad.norma()/k);
        return Point.minus(p, grad);
    }
    
    /**
     * Градиентный метод оврагов.
     * @param p1 координаты первой точки для спуска.
     * @param p2 координаты второй точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodOfRavines(final Point p1, final Point p2) {
        Point ret = Point.minus(p1, p2);
        ret.multiplication(1.0/ret.norma());
        
        //пересчёт коэфициента h
        double cos_k1 = Point.multiplication(ret, ret);
        h = h * Math.pow(1.5, cos_k1 - cos_k);
        cos_k = cos_k1;
        
        double k = Math.signum(getFunctionValue(p2) - getFunctionValue(p1));
        
        ret.multiplication(h*k);
        ret.plus(p1);
        return methodOfSteepestDescent(ret);
    }
    
    /**
     * Метод Ньютона.
     * @param p координаты точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodNewtons(final Point p) {
        Matrix2x2 grad2 = gradient2(p);
        grad2.inverce();
        return Point.minus(p, grad2.multiplicationR(gradient(p)));
    }
    
    /**
     * Метод Давидона-Флетчера-Пауэла.
     * @param p координаты точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodDFP(final Point p) {
        Point grad = H.multiplicationR(gradient(p));
        double gamma = getAlpha(p, grad);
        
        grad.multiplication(gamma);
        Point ret = Point.minus(p, grad);
        
        Point yk = gradient(ret);
        yk.minus(gradient(p));
        
        Point pk = grad;
        pk.multiplication(-1.0);
        
        Point Hyk = H.multiplicationR(yk);
        
        Matrix2x2 A = new Matrix2x2(Hyk, Hyk);
        A.multiplication(-1.0/Hyk.multiplication(yk));
        
        Matrix2x2 B = new Matrix2x2(pk, pk);
        B.multiplication(gamma/pk.multiplication(yk));
        
        H = H.plus(A).plus(B);
        
        return ret;
    }
    
    /**
     * Метод Бройдена-Флетчера-Шанно.
     * @param p координаты точки для спуска.
     * @return координаты следующей точки.
     */
    public Point methodBFS(final Point p) {
        Point grad = H.multiplicationR(gradient(p));
        Point pk = new Point(grad);
        pk.multiplication(-1.0);
        double gamma = getAlpha(p, grad);
        System.out.println(gamma);
        
        grad.multiplication(gamma);
        Point ret = Point.minus(p, grad);
        
        Point yk = gradient(ret);
        yk.minus(gradient(p));
        
        Point Hyk = H.multiplicationR(yk);
        double ro = Hyk.multiplication(yk);
        ro /= yk.multiplication(pk);
        ro += gamma;
        
        Matrix2x2 a = new Matrix2x2(pk, pk);
        a.multiplication(ro);
        
        a.minus(new Matrix2x2(pk, yk).multiplication(H));
        a.minus(H.multiplication(new Matrix2x2(yk, pk)));
        a.multiplication(1.0/yk.multiplication(pk));
        
        H = H.plus(a);
        
//        System.out.println(H);
//        System.out.println();
        
        return ret;
    }

    /**
     * @return параметр метода.
     */
    public double getStep() {
        return step;
    }

    /**
     * @param step параметр метода для задания.
     */
    public void setStep(final double step) {
        this.step = step;
    }
    
    public void clear() {
        cos_k = 0;
        step = 0;
        h = 1;
        H.setMatrixElement(0, 0, 1);
        H.setMatrixElement(1, 0, 0);
        H.setMatrixElement(0, 1, 0);
        H.setMatrixElement(1, 1, 1);
        //H = gradient2(new Point(10.0, 10.0));
    }
}
