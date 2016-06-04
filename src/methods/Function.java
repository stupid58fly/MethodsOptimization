package methods;

public class Function {
    private static Double a;
    
    /**
     * Возвращение параметра функции.
     * @return параметра "а".
     */
    public static Double getParameter() {
        return a;
    }
    
    /**
     * Задание параметра функции.
     * @param p параметр "а".
     */
    public static void setParameter(final Double p) {
        a = p;
    }
    
    /**
     * Вычисляет значение функции с заданными параметрами: вектор координаты и параметр "а".
     * @param p вектор координат.
     * @return значение функции с заданными параметрами.
     */
    public static Double getFunctionValue(final Point p) {
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
    public static double getAlpha(final Point p, final Point grad) {
        double x1 = p.getX1();
        double x2 = p.getX2();
        double g1 = grad.getX1();
        double g2 = grad.getX2();
        
        double []pol = new double[4];
        pol[0] = 4 * Math.pow(g1, 4.0);
        pol[1] = 6 * Math.pow(g1, 2.0) * (g2 - 2 * x1*g1);
        pol[2] = 2 * (Math.pow(2 * x1*g1 - g2, 2) + g1*g1 * (2 * x1*x1 - 2 * x2 + a));
        pol[3] = 2*((x1*x1 - x2)*(g2 - 2 * x1*g1)-
                a * g1 * (x1 - 1));
        double max_pol_k = pol[0];
        for (int i = 1; i < pol.length; i++)
            if (max_pol_k < pol[i]) max_pol_k = pol[i];
        
        if (max_pol_k != 0) {
            for (int i = 0; i < pol.length; i++)
                pol[i] /= max_pol_k;
        }
        
        double x = 0;
        double x_old;
        double pow_x = 1;
        
        do {
            x_old = x;
            double f = pol[0]*Math.pow(x, 3) + pol[1]*Math.pow(x, 2) + pol[2]*x + pol[3];
            double df = 3*pol[0]*Math.pow(x, 2) + 2*pol[1]*x + pol[2];
            if (df != 0) {
                x -= f/df;
            } else {
                x += x_old;
                continue;
            }
            pow_x = 1;
            double tmp = Math.min(Math.abs(x), Math.abs(x_old));
            
            while(tmp > pow_x) pow_x *= 10;
            while(tmp < pow_x) pow_x /= 10;
        } while(Math.abs(x - x_old) > pow_x*1e-14);
//        System.out.println("1: " + x);
        
        double n_pol[] = new double[3];
        
        n_pol[0] = pol[0];
        for (int i = 1; i < 3; i++) {
            n_pol[i] = x*n_pol[i-1] + pol[i];
        }
        
        double D = Math.pow(n_pol[1], 2) - 4*n_pol[0]*n_pol[2];
        
        if (D <= 0) return x;
        
        double x_ = (-n_pol[1] + Math.sqrt(D))/(2*n_pol[0]);
//        System.out.println("2: " + x_);
        
        Point px = new Point(Point.minus(p, Point.multiplication(grad, x)));
        Point px_ = new Point(Point.minus(p, Point.multiplication(grad, x_)));
        if (getFunctionValue(px) > getFunctionValue(px_)) {
            px = px_;
            x = x_;
        }
        
        x_ = (-n_pol[1] - Math.sqrt(D))/(2*n_pol[0]);
//        System.out.println("3: " + x_);
        
        px_ = new Point(Point.minus(p, Point.multiplication(grad, x_)));
        if (getFunctionValue(px) > getFunctionValue(px_)) {
            x = x_;
        }
        
//        System.out.println("r: " + x);
        System.out.println();
        return x;
    }
    
    /**
     * Вычисление градиента функции в точке p.
     * @param p вектор координат.
     * @return значение градиента. В х1 - частная производная по х1, в х2 - частная производная по х2.
     */
    public static Point gradient(final Point p) {
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
    public static Matrix2x2 gradient2(final Point p) {
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
    public static Point getResult() {
        return new Point(1.0, 1.0);
    }
}
