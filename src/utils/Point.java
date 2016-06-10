package utils;

import java.util.Objects;

public class Point {
    private Double x1;
    private Double x2;
    
    /**
     * Конструктор. Инициализирует поля "x1" и "x2" значениями 0.0.
     */
    public Point() {
        x1 = 0.0;
        x2 = 0.0;
    }
    
    /**
     * Конструктор. Инициализирует поля "x1" и "x2" значениями x1 и x2 соответственно.
     * @param x1 значение координаты x1.
     * @param x2 значение координаты x2.
     */
    public Point(final Double x1, final Double x2) {
        this.x1 = x1;
        this.x2 = x2;
    }
    
    /**
     * Конструктор копирования.
     * @param p праобраз для копии.
     */
    public Point(final Point p) {
        this.x1 = p.x1;
        this.x2 = p.x2;
    }
    
    /**
     * Сравнение векторов.
     * @param p вектор для сравнения
     * @return если координаты обоих векторов попарно совпадают, то true, иначе false.
     */
    @Override
    public boolean equals(final Object p) {
        Point newP = (Point)p;
        return Objects.equals(newP.x1, x1) && Objects.equals(newP.x2, x2);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.x1);
        hash = 89 * hash + Objects.hashCode(this.x2);
        return hash;
    }
    
    /**
     * Сравнение векторов.
     * @param p1 первый вектор.
     * @param p2 второй вектор.
     * @return если координаты обоих векторов попарно совпадают, то true, иначе false.
     */
    public static boolean equals(final Point p1, final Point p2) {
        return Objects.equals(p1.x1, p2.x1) && Objects.equals(p1.x2, p2.x2);
    }
    
    @Override
    public String toString() {
        return "(" + x1 + "; " + x2 + ")";
    }
    
    /**
     * Изменение значения координаты "x1".
     * @param x1 новое значение координаты.
     */
    public void setX1(final Double x1) {
        this.x1 = x1;
    }
    
    /**
     * Изменение значения координаты "x2".
     * @param x2 новое значение координаты.
     */
    public void setX2(final Double x2) {
        this.x2 = x2;
    }
    
    /**
     * Возврат значения координаты "x1".
     * @return значение координаты "x1".
     */
    public Double getX1() {
        return x1;
    }
    
    /**
     * Возврат значения координаты "x2".
     * @return значение координаты "x2".
     */
    public Double getX2() {
        return x2;
    }
    
    /**
     * Вычитание векторов с записью результата в уменьшаемое. this -= p.
     * @param p вычитаемое.
     */
    public void minus(final Point p) {
        x1 -= p.x1;
        x2 -= p.x2;
    }
    
    /**
     * Вычитание векторов. p1 - p2.
     * @param p1 уменьшаемое.
     * @param p2 вычитаемое.
     * @return значение разности p1 и p2.
     */
    public static Point minus(final Point p1, final Point p2) {
        return new Point(p1.x1 - p2.x1, p1.x2 - p2.x2);
    }
    
    /**
     * Сложение векторов с записью результата в первое слогаемое. this += p.
     * @param p второе слогаемое.
     */
    public void plus(final Point p) {
        x1 += p.x1;
        x2 += p.x2;
    }
    
    /**
     * Сложение векторов. p1 + p2
     * @param p1 первое слогаемое.
     * @param p2 второе слогаемое.
     * @return сумма p1 и p2.
     */
    public static Point plus(final Point p1, final Point p2) {
        return new Point(p1.x1 + p2.x1, p1.x2 + p2.x2);
    }
    
    /**
     * Подсчет квадратичной нормы вектора (евклидовой нормы).
     * @return норма вектора.
     */
    public Double norma() {
        return Math.sqrt(Math.pow(x1, 2) + Math.pow(x2, 2));
    }
    
    /**
     * Скалярное произведение векторов.
     * @param p второй множитель.
     * @return скалярное произведение.
     */
    public Double multiplication(final Point p) {
        return x1 * p.x1 + x2 * p.x2;
    }
    
    /**
     * Скалярное произведение векторов p1 и p2.
     * @param p1 первый множитель.
     * @param p2 второй множитель.
     * @return скалярное произведение.
     */
    public static Double multiplication(final Point p1, final Point p2) {
        return p1.x1 * p2.x1 + p1.x2 * p2.x2;
    }
    
    /**
     * Произведение вектора на число с изменением вектора.
     * @param mul множитель для вектора.
     */
    public void multiplication(final Double mul) {
       x1 *= mul;
       x2*= mul;
    }
    
    /**
     * Произведение вектора на число.
     * @param p вектор-сомножитель.
     * @param mul множитель для вектора.
     * @return произведение вектора на число.
     */
    public static Point multiplication(final Point p, final Double mul) {
       return new Point(p.x1 * mul, p.x2 * mul);
    }
}
