/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

/**
 *
 * @author ilyab
 */
public enum NameMethods implements Method{
        MethodOfSteepestDescent {
            private final String name = "Метод наискорейшего спуска"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Point grad = Function.gradient(p1);
                p1 = Point.minus(p1, Point.multiplication(grad, Function.getAlpha(p1, grad)));
            }
        },  
        MethodPitchwise {
            private final String name = "Метод с постоянным шагом"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Point grad = Function.gradient(p1);
                grad.multiplication(1.0/grad.norma());
                p1 = Point.minus(p1, grad);
            }
        },
        MethodStepCrushing {
            private final String name = "Метод с дроблением шага"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Point newPoint;
                Point grad =  Function.gradient(p1);
                
                do {
                    newPoint = Point.minus(p1, Point.multiplication(grad, step));
                    if(Function.getFunctionValue(newPoint) <= Function.getFunctionValue(p1))
                        step *= 2;
                    else break;
                } while(true);
                                
                do {
                    newPoint = Point.minus(p1, Point.multiplication(grad, step));
                    if(Function.getFunctionValue(newPoint) >= Function.getFunctionValue(p1))
                        step *= 0.5;
                    else break;
                } while(true);
                
                p1 =  newPoint;
            }
        },
        MethodToDecreaseTheLengthOfThePitch {
            private final String name = "Метод с убыванием длины шага как 1/k"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Point grad = Function.gradient(p1);
                grad.multiplication(step/grad.norma()/cStep);
                p1 =  Point.minus(p1, grad);
            }
        },
        MethodOfRavines {
            private final String name = "Метод оврагов"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                if (p2 == null) {
                    Point tmp = new Point(p1);
                    MethodOfSteepestDescent.step();
                    p2 = p1;
                    p1 = tmp;
                    p1.setX1(p1.getX1() - 1);
                    p1.setX2(p1.getX2() + 1);
                    MethodOfSteepestDescent.step();
                }
                
                double f1 = Function.getFunctionValue(p1);
                double f2 = Function.getFunctionValue(p2);
                
                if (f1 > f2) {
                    Point tmp = p1;
                    p1 = p2;
                    p2 = tmp;
                }
                
                Point ret;
                ret = Point.minus(p1, p2);
                ret.multiplication(step/ret.norma());
                
                p2 = new Point(p1);
              
                p1.plus(ret);
                MethodOfSteepestDescent.step();
                
                //throw new UnsupportedOperationException("Метод оврагов");
            }
        },
        MethodNewtons {
            private final String name = "Метод Ньютона"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Matrix2x2 grad2 = Function.gradient2(p1);
                grad2.inverce();
                p1 = Point.minus(p1, grad2.multiplicationR(Function.gradient(p1)));
            }
        },
        MethodDFP {
            private final String name = "Метод Давидона-Флетчера-Пауэла"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Point grad = H.multiplicationR(Function.gradient(p1));
                double gamma = Function.getAlpha(p1, grad);

                grad.multiplication(gamma);
                Point ret = Point.minus(p1, grad);

                Point yk = Function.gradient(ret);
                yk.minus(Function.gradient(p1));

                Point pk = grad;
                pk.multiplication(-1.0);

                Point Hyk = H.multiplicationR(yk);

                Matrix2x2 A = new Matrix2x2(Hyk, Hyk);
                A.multiplication(-1.0/Hyk.multiplication(yk));

                Matrix2x2 B = new Matrix2x2(pk, pk);
                B.multiplication(gamma/pk.multiplication(yk));

                H = H.plus(A).plus(B);

                p1 = ret;
            }
        },
        MethodBFS {
            private final String name = "Метод Бройдена-Флетчера-Шанно"; 
            @Override
            public String toString() {
                return name;
            }
            
            @Override
            public void step() {
                Point grad = H.multiplicationR(Function.gradient(p1));
                Point pk = new Point(grad);
                pk.multiplication(-1.0);
                double gamma = Function.getAlpha(p1, grad);

                grad.multiplication(gamma);
                Point ret = Point.minus(p1, grad);

                Point yk = Function.gradient(ret);
                yk.minus(Function.gradient(p1));

                Point Hyk = H.multiplicationR(yk);
                double ro = Hyk.multiplication(yk);
                ro /= yk.multiplication(pk);
                ro += gamma;

                Matrix2x2 a = new Matrix2x2(pk, pk);
                a.multiplication(ro);

                Matrix2x2 b = new Matrix2x2(pk, yk).multiplication(H);

                Matrix2x2 c = H.multiplication(new Matrix2x2(yk, pk));

                a = a.minus(b);
                a = a.minus(c);
                a.multiplication(1.0/yk.multiplication(pk));

                H = H.plus(a);

                p1 = ret;
            }
        };
        
        public static void clear() {
            p1 = null;
            p2 = null;
            p1_ = null;
            p2_= null;
            H = new Matrix2x2();
            cStep = 0;
            step = 0;
        }

        public static Point p1;                     // Точка для спуска
        public static Point p2;                     // Точка для спуска овражным методом
        public static Point p1_;                    // Точка для спуска овражным методом (с штрихом)
        public static Point p2_;                    // Точка для спуска овражным методом (с штрихом)
        public static Matrix2x2 H;                  // Матрица приближений для квазиньютоновых методов
        public static int cStep;                    // Номер шага для метода с убыванием длины шага
        public static double step;                  // Начальный щаг
        private static double h;                    //начальное приближение hk для овражного алгоритма
    }
