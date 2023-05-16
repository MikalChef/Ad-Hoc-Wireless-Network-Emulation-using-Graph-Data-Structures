//This class is complete. Don't modify it
public class NetNode {
    private int id;
    private String name;
    private double x_coordinate;
    private double y_coordinate;
    public NetNode(int id,String name,double x_coordinate,double y_coordinate){
        this.id=id;
        this.name=name;
        this.x_coordinate=x_coordinate;
        this.y_coordinate=y_coordinate;  
    }
    
    public void setId(int id){
        this.id=id;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setX_coordinate(double x_coordinate){
        this.x_coordinate=x_coordinate;
    }
     public void setY_coordinate(double y_coordinate){
        this.y_coordinate=y_coordinate;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    public double getX_coordinate(){
        return x_coordinate;
    }
     public double getY_coordinate(){
        return y_coordinate;
    }
 }
