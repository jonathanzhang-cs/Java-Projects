public class Cylinder extends Circle{
    protected double z = 0.0, height; // height in the z direction
    
    public Cylinder(){
        super();
        height = 1.0;
    }
    
    public Cylinder(double x, double y, double radius, double height){
        super(radius, x, y);
        this.height = height;
    }

    public double getHeight(){
        return height;
    }
  
    public void setHeight(double height){
        this.height = height;
    }
    
    @Override
    public double getArea(){
        return ((2 * super.getArea()) + (super.getCircumference() * height));
    }

    public double getVolume(){
        return (super.getArea() * height);
    }
}
