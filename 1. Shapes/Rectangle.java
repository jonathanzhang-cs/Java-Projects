public class Rectangle {
    private double L, H, x, y; // length, height, and coordinates of the bottom left corner of rectangle

    public Rectangle(){
        L = H = 1.0;
        x = y = 0.0;
    }

    public Rectangle(double L, double H, double x, double y){
        this.L = L;
        this.H = H;
        this.x = x;
        this.y = y;
    }

    public double getLength(){
        return L;
    }

    public double getHeight(){
        return H;
    }

    public void setLength(double L){
        this.L = L;
    }

    public void setHeight(double H){
        this.H = H;
    }

    public double perimeter(){
        return (2 * (H + L));
    }

    public double area(){
        return (H * L);
    }

    @Override
    public boolean equals (Object rectangleB){
        Rectangle compare = (Rectangle) rectangleB;
        return area() == compare.area();
    }
}
