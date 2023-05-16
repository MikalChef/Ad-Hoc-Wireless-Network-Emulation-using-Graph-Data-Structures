//This class is complete. Don't modify it
import java.util.*;
public class AdjacencyListHead {
    private NetNode node;
    private LinkedList<Adjacent> adjacencyList;
    
    public AdjacencyListHead(NetNode node){
        this.node=node;
        this.adjacencyList=new LinkedList<Adjacent>();
    }
    public AdjacencyListHead(NetNode node,LinkedList<Adjacent> adjacencyList){
        this.node=node;
        this.adjacencyList=adjacencyList;
    }
    public void setNetNode(NetNode node){
        this.node=node;
    }
    public void setAdjacencyList(LinkedList<Adjacent> adjacencyList){
        this.adjacencyList=adjacencyList;
    }
    public NetNode getNetNode(){
        return node;
    }
    public LinkedList<Adjacent> getAdjacencyList(){
        return adjacencyList;
    }
    
    
}
