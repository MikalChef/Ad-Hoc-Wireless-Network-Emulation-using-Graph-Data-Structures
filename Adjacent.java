//This class is complete. Don't modify it
public class Adjacent {
  private NetNode neighbor;
    private double weight; 
    public Adjacent(NetNode neighbor,double weight){
        this.neighbor=neighbor;
        this.weight=weight;
    }
    public void setNeighbor(NetNode neighbor){
       this.neighbor=neighbor; 
    }
    public void setWeight(double weight){
        this.weight=weight;
    }
     public NetNode getNeighbor(){
       return neighbor; 
    }
    public double getWeight(){
        return weight;
    }  
} 
