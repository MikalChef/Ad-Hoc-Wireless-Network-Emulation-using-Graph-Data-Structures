
import java.util.*;



public class Network {
    private NetGraph networkGraph;



    public Network() {
        //Default constructor creates an Network consisting of 1000 nodes located in an area of 200x200
        Network sampleNetwork = new Network(1000, 200);
    }

    /**
     * Constructs a new Network object with the specified number of nodes and side length of the two-dimensional area
     * in which the nodes will be placed. The constructor initializes the networkGraph instance variable with an
     * ArrayList of AdjacencyListHead objects, each representing a node in the network.
     * @param numofNodes the number of nodes to create in the network.
     * @param side the side length of the square area in which the nodes will be placed.
     */

    public Network(int numofNodes, double side) {
        ArrayList<AdjacencyListHead> nodesList = new ArrayList<AdjacencyListHead>();
        for (int i = 0; i < numofNodes; i++) {
            double x_coordinate = side * Math.random();
            double y_coordinate = side * Math.random();
            int id = i;
            String name = "node " + i;
            NetNode node = new NetNode(id, name, x_coordinate, y_coordinate);
            nodesList.add(new AdjacencyListHead(node));

        }
        networkGraph = new NetGraph(nodesList);

        //Iterate over all the node pairs in the graph and connect the nodes with a distance <=20root2  with links
        for (int i = 0; i < networkGraph.getNumNodes(); i++) {
            NetNode node1 = networkGraph.nodeFromIndex(i);
            for (int j = i + 1; j < networkGraph.getNumNodes(); j++) {
                NetNode node2 = networkGraph.nodeFromIndex(j);
                double distance = euclideanDistance(node1, node2);
                if (distance <= 20 * Math.sqrt(2)) {
                    networkGraph.addLink(node1, node2, distance);
                }
            }
        }
    }

    /**
     * Calculates the Euclidean distance between two nodes in a two-dimensional space.
     * @param node1 the first node.
     * @param node2 the second node.
     * @return the Euclidean distance between the two nodes.
     */
    double euclideanDistance(NetNode node1, NetNode node2) {
        //Implement this method.
        //returns the Euclidean distance between two nodes node1 and node2.
        double distance = Math.sqrt(Math.pow(node1.getX_coordinate() - node2.getX_coordinate(), 2)
                + Math.pow(node1.getY_coordinate() - node2.getY_coordinate(), 2));
        return distance;
    }

    /**
     * Calculates the minimum spanning tree of the network using Prim's algorithm.
     * @return A 2D array representing the minimum spanning tree of the network.
     */
    public int[][] minSpanningTree() {
        //the running time should be <= O(n*n) where n is the number of vertices in the graph

        // Get the number of nodes in the network
        int numNodes = networkGraph.getNumNodes();
        // Create a 2D array to store the minimum spanning tree
        int[][] spanningTree = new int[numNodes][numNodes];

        //keep track of visited nodes, distances, and parents
        boolean[] visited = new boolean[numNodes];
        double[] distance = new double[numNodes];

        Arrays.fill(distance, Double.MAX_VALUE);

        distance[0] = 0;
        int[] parent = new int[numNodes];
        parent[0] = -1;

        // loop until all nodes have been visited
        for (int i = 0; i < numNodes; i++) {
            // find the unvisited node with the smallest distance
            int minNode = -1;
            double minDist = Double.POSITIVE_INFINITY;
            for (int j = 0; j < numNodes; j++) {
                if (!visited[j] && distance[j] < minDist) {
                    minNode = j;
                    minDist = distance[j];
                }
            }

            visited[minNode] = true;

            // update the distances and parents of its neighbors
            for (Adjacent adjacent :
                    networkGraph.getAdjacents(networkGraph.nodeFromIndex(minNode))) {
                NetNode neighbor = adjacent.getNeighbor();
                int neighborIndex = neighbor.getId();
                if (!visited[neighborIndex] && adjacent.getWeight() < distance[neighborIndex]) {
                    parent[neighborIndex] = minNode;
                    distance[neighborIndex] = adjacent.getWeight();
                }
            }
        }

        // fill the MST matrix using the parent array
        for (int i = 1; i < numNodes; i++) {
            spanningTree[parent[i]][i] = 1;
            spanningTree[i][parent[i]] = 1;
        }

        return spanningTree;
    }


    /**
     * Computes the shortest path between two nodes in a network graph using Dijkstra's algorithm.
     * @param node1 the starting node of the shortest path.
     * @param node2 the destination node of the shortest path.
     * @return an ArrayList of NetNode objects representing the shortest path from node1 to node2.
     */

    public ArrayList<NetNode> getShortestPath(NetNode node1, NetNode node2) {
        //the running time complexity is O(V lg V) where V is the number of vertices in the graph

        //Create a priority queue to store NetNodeDistance objects in ascending order of distance.
        PriorityQueue<NetNodeDistance> priorityQueue = new PriorityQueue<>();
        //Create a map to store the shortest known distance from node1 to each node in the graph.
        Map<NetNode, Double> distance = new HashMap<>();
        // Create a map to store the previous node on the shortest known path to each node in the graph
        Map<NetNode, NetNode> previous = new HashMap<>();
        // a set to store the nodes that have already been visited.
        Set<NetNode> visited = new HashSet<>();

        for (int i = 0; i < networkGraph.getNumNodes(); i++) {
            distance.put(networkGraph.nodeFromIndex(i), Double.MAX_VALUE);
            previous.put(networkGraph.nodeFromIndex(i), null);
        }

        distance.put(node1, 0.0);
        priorityQueue.offer(new NetNodeDistance(node1, 0));

        while (!priorityQueue.isEmpty()) {
            NetNodeDistance current = priorityQueue.poll();
            NetNode currentNode = current.node;

            if (currentNode == node2) {
                ArrayList<NetNode> path = new ArrayList<>();
                NetNode node = node2;
                while (node != null) {
                    path.add(0, node);
                    node = previous.get(node);
                }
                return path;
            }

            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            for (Adjacent adjacent : networkGraph.getAdjacents(currentNode)) {
                NetNode node = adjacent.getNeighbor();
                double weight = adjacent.getWeight();

                if (distance.get(currentNode) + weight < distance.get(node)) {
                    distance.put(node, distance.get(currentNode) + weight);
                    previous.put(currentNode, node);
                    priorityQueue.offer(new NetNodeDistance(node, distance.get(node)));
                }
            }

        }

        return new ArrayList<>();
    }


    // node class for priority queue
    private class NetNodeDistance implements Comparable<NetNodeDistance> {
        NetNode node;
        double distance;

        NetNodeDistance(NetNode node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        public int compareTo(NetNodeDistance other) {
            return Double.compare(distance, other.distance);
        }
    }

}
