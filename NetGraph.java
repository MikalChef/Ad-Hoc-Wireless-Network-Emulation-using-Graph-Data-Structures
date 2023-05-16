
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This class represents a graph containing a list of nodes and their adjacent nodes with respective weights.
 */
public class NetGraph {

    /**
     * The list of Nodes in the graph.
     */

    private ArrayList<AdjacencyListHead> nodesList;

    /**
     * Constructs a new NetGraph object with the given list of nodes.
     *
     * @param nodesList the list of nodes in the graph.
     */

    public NetGraph(ArrayList<AdjacencyListHead> nodesList) {
        this.nodesList = nodesList;
        //This method is complete
    }

    /**
     * Returns the list of nodes in the graph.
     *
     * @return the list of nodes in the graph.
     */

    public ArrayList<AdjacencyListHead> getNodesList() {


        return nodesList;
        //This method is complete
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return the number of nodes in the graph.
     */

    public int getNumNodes() {

        //Implement this method
        //Should return the number of nodes in the Graph

        return nodesList.size();

    }

    /**
     * Returns the number of edges in the undirected graph.
     *
     * @return the number of edges.
     */

    public int getNumLinks() {
        //Implement this method
        //returns the number of edges in the graph. Remember this is an undirected graph
        int count = 0;
        for (AdjacencyListHead nodes : nodesList) {
            count += nodes.getAdjacencyList().size();

        }
        return count / 2;
    }

    /**
     * Adds a new node to the graph.
     *
     * @param id           id of the new node.
     * @param name         name of the new node.
     * @param x_coordinate x coordinate of the new node.
     * @param y_coordinate y coordinate of the new node.
     */

    public void insertNetNode(int id, String name, double x_coordinate, double y_coordinate) {

        //Implement this method
        //Adds a new node to the graph. The node is represented by the NetNode class having id, name, x_coordinate, and y_coordinatte instance variables
        //You should check if the nodes already exists in the graph. If this is the case throw an IllegalArgumentException

        NetNode netNode = new NetNode(id, name, x_coordinate, y_coordinate);
        AdjacencyListHead adjacencyListHead = new AdjacencyListHead(netNode);
        if (nodesList.contains(adjacencyListHead)) {
            throw new IllegalArgumentException();
        }
        nodesList.add(adjacencyListHead);
    }

    /**
     * Adds a link in the graph between two nodes node1 and node2 with the given weight.
     *
     * @param node1  the first NetNode to be connected.
     * @param node2  the second NetNode to be connected.
     * @param weight weight of the link to be added.
     * @throws IllegalArgumentException if node1 or node2 is not found in the graph or if they are null.
     */

    public void addLink(NetNode node1, NetNode node2, double weight) {
        //Implement this method
        //adds a link in the graph between two  nodes node1 and node2.
        // You should check if the nodes exist in the graph and that they are not null or else you should raise an IllegalArgumentException

        boolean foundNode1 = false;
        boolean foundNode2 = false;


        for (AdjacencyListHead nodes : nodesList) {
            if (nodes.getNetNode() == node1) {
                foundNode1 = true;
                Adjacent adjacent = new Adjacent(node2, weight);
                if (!nodes.getAdjacencyList().contains(adjacent)) {

                    nodes.getAdjacencyList().add(adjacent);

                }


            }

            if (nodes.getNetNode() == node2) {
                foundNode2 = true;
                Adjacent adjacent = new Adjacent(node1, weight);

                if (!nodes.getAdjacencyList().contains(adjacent)) {

                    nodes.getAdjacencyList().add(adjacent);
                }


            }
        }

        if (!foundNode1 || !foundNode2) {
            throw new IllegalArgumentException();
        }


    }

    /**
     * Deletes a particular node from the NetGraph and all edges containing it from the different adjacency lists.
     *
     * @param node the NetNode to be deleted from the graph.
     * @throws IllegalArgumentException if the node does not exist in the graph or if it is null.
     */
    public void deleteNetNode(NetNode node) {
        //Implement this method
        //deletes a particular  node from the NetGraph. Remember to delete all edges containing it from the different adjacency lists
        //You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
        AdjacencyListHead listNode = new AdjacencyListHead(node);

        for (AdjacencyListHead nodes : nodesList) {
            if (nodes.getNetNode() == node) {
                nodesList.remove(listNode);
            }
            nodes.getAdjacencyList().removeIf(adjacent -> adjacent.getNeighbor() == node);

        }
        throw new IllegalArgumentException();
    }

    /**
     * Deletes a link between two nodes in the NetGraph.
     *
     * @param node1 the first node.
     * @param node2 the second node.
     * @throws IllegalArgumentException if either node is null or does not exist in the graph.
     */

    public void removeLink(NetNode node1, NetNode node2) {
        //Implement this method
        //deletes a link between two  nodes in the NetGraph.
        //You should check if the nodes exist in the graph and that they are not null or else you should raise an IllegalArgumentException.

        //flags to check if node1 and node2 are found in the graph.
        boolean foundNode1 = false;
        boolean foundNode2 = false;

        //Loop through all the nodes in the graph to find node1 and node2.
        for (AdjacencyListHead nodes : nodesList) {
            if (nodes.getNetNode() == node1) {
                foundNode1 = true;

                //Get the adjacency list of node1 and find the adjacent node that is equal to node2.
                LinkedList<Adjacent> adjacents = nodes.getAdjacencyList();
                Adjacent neighbor = adjacents
                        .stream()
                        .filter(adjacent -> adjacent.getNeighbor() == node2)
                        .findFirst()
                        .orElse(null);

                // If the adjacent node is found, remove it from the adjacency list of node1.
                if (neighbor != null) {
                    adjacents.remove(neighbor);
                }

            }

            if (nodes.getNetNode() == node2) {
                foundNode2 = true;

                //Get the adjacency list of node2 and find the adjacent node that is equal to node1.
                LinkedList<Adjacent> adjacents = nodes.getAdjacencyList();

                Adjacent neighbor = adjacents
                        .stream()
                        .filter(adjacent -> adjacent.getNeighbor() == node1)
                        .findFirst()
                        .orElse(null);

                // If the adjacent node is found, remove it from the adjacency list of node2.
                if (neighbor != null) {
                    adjacents.remove(neighbor);
                }
            }
        }

        // If either node1 or node2 is not found in the graph, raise an IllegalArgumentException.
        if (!foundNode1 || !foundNode2) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a LinkedList containing the Adjacent Objects representing the neighbors of a particular node and the weights of the link.
     *
     * @param node the node whose neighbors and link weights are to be returned.
     * @return LinkedList of Adjacent objects.
     * @throws IllegalArgumentException if the specified node does not exist in the graph.
     */
    public LinkedList<Adjacent> getAdjacents(NetNode node) {


        //Implement this method
        //returns a LinkedList containing the Adjacent Objects representing the neighbors of a particular node and the weights of the link
        for (AdjacencyListHead nodes : nodesList) {
            if (nodes.getNetNode() == node) {
                return nodes.getAdjacencyList();
            }

        }
        throw new IllegalArgumentException();


    }

    /**
     * Returns the index in the nodesList ArrayList of a particular node.
     *
     * @param node the NetNode whose index in the nodesList ArrayList is to be returned.
     * @return the index of the given node in the nodesList ArrayList.
     * @throws IllegalArgumentException if the node is not found in the graph or is null.
     */

    int getNodeIndex(NetNode node) {
        //Implement this method
        //returns the index in the nodesList ArrayList of a particular node.
        //You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
        int index = -1;

        for (int i = 0; i < nodesList.size(); i++) {
            if (nodesList.get(i).getNetNode() == node) {
                index = i;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        }
        return index;
    }

    /**
     * Returns the degree(the number of its adjacent nodes) of a given node in the NetGraph.
     *
     * @param node the node whose degree will be returned
     * @return the number of adjacent nodes of the given node.
     * @throws IllegalArgumentException if the given node does not exist in the graph or is null.
     */

    public int degree(NetNode node) {
        //Implement this method
        //returns the number of adjacent nodes of a particular  node
        ////You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
        for (AdjacencyListHead nodes : nodesList) {
            if (nodes.getNetNode() == node) {
                return nodes.getAdjacencyList().size();
            }

        }
        throw new IllegalArgumentException();

    }

    /**
     * Returns the maximum number of adjacent nodes connected to any node in the graph.
     *
     * @return the max degree of the graph.
     */
    public int getGraphMaxDegree() {
        //Implement this method
        //returns the maximum number of adjacent nodes connected to a particular node
        int max = 0;
        for (AdjacencyListHead nodes : nodesList) {
            if (nodes.getAdjacencyList().size() > max) {
                max = nodes.getAdjacencyList().size();
            }
        }


        return max;
    }

    /**
     * Returns a NetNode object from the index of the node in the nodesList ArrayList.
     *
     * @param index the index of the node in the nodesList ArrayList.
     * @return the NetNode object at the specified index, or null if the index is out of bounds.
     */

    public NetNode nodeFromIndex(int index) {
        //Implement this method
        //returns an NetNode object from the index of the node in nodesList ArrayList

        for (int i = 0; i < nodesList.size(); i++) {
            if (index == i) {
                return nodesList.get(i).getNetNode();
            }
        }
        return null;
    }


    /**
     * Returns a String representation of the network graph in the adjacency list format.
     *
     * @return String representation of the network graph in adjacency list format.
     */
    public String printGraph() {
        //Implement this method
        //returns a String representation of the network graph in the adjacency list format: e.g.
        /*
            A: {(B,3), (D,2), (E,0.5)}
            B: {(A,3),(E,2)}
            C:{}
            D:{(A,2)}
            E:{(A,0.5),(B,2)}
        */

        StringBuilder stringBuilder = new StringBuilder();

        for (AdjacencyListHead nodes : nodesList) {
            stringBuilder.append(nodes.getNetNode().getName());
            stringBuilder.append(": {");
            int count = 0;
            List<Adjacent> adjacents = nodes.getAdjacencyList();
            for (int i = 0; i < adjacents.size(); i++) {
                stringBuilder.append("(");
                Adjacent adjacent = adjacents.get(i);
                stringBuilder.append(adjacent.getNeighbor().getName());
                stringBuilder.append(",");
                stringBuilder.append(adjacent.getWeight());
                stringBuilder.append(")");
                if (count < adjacents.size() - 1) {
                    stringBuilder.append(", ");
                }
                count++;
            }
            stringBuilder.append("}");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
