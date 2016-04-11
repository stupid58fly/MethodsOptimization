package methods;

import java.util.Objects;

public class Point {
    private Double x1;
    private Double x2;
    
    public Point() {
        x1 = 0.0;
        x2 = 0.0;
    }
    
    public Point(final Double x1, final Double x2) {
        this.x1 = x1;
        this.x2 = x2;
    }
    
    public Point(final Point p) {
        this.x1 = p.x1;
        this.x2 = p.x2;
    }
    
    public boolean equals(final Point p) {
        return Objects.equals(p.x1, x1) && Objects.equals(p.x2, x2);
    }
    
    @Override
    public String toString() {
        return "(" + x1 + "; " + x2 + ")";
    }
    
    public void setX1(final Double x1) {
        this.x1 = x1;
    }
    
    public void setX2(final Double x2) {
        this.x2 = x2;
    }
    
    public Double getX1() {
        return x1;
    }
    
    public Double getX2() {
        return x2;
    }
}
