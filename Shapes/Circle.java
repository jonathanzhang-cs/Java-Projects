public class Circle {
    protected double radius;

    // circle center coordinates
    protected double x, y;

    public Circle(){
        radius = 1.0;
        x = y = 0.0;
    }

    public Circle(double r) {
        radius = r;
        x = y = 0.0;
    }

    public Circle(double r, double x, double y) {
        radius = r;
        this.x = x;
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public double[] getCenter() {
        double[] c = {x, y};
        return c;
    }

    public double getArea() {
        return radius * radius * Math.PI;
    }

    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    public boolean isBiggerThan(Circle circleB) {
        return this.getArea() > circleB.getArea();
    }

    public boolean containsPoint(double x, double y) {
        // return Distance of input from center <= radius
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2)) <= radius; 
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    public void setCenter(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "This circle is centered at point (" + this.x + ", " + this.y + ") with radius " + this.radius;
    }

    @Override
    public boolean equals(Object circleB){
        Circle compare = (Circle)circleB;
        return getArea() == compare.getArea();
    }
}
