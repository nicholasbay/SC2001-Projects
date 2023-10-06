package project_2;

import java.util.LinkedList;
import java.util.Random;

public class Graph {
    static final int MAX_WEIGHT = 1000000;
    // Instance variables
    int V;
    int E;
    LinkedList<Edge>[] adjList;
    int[][] adjMatrix;
    
    // Constructors
    // For non-random graph, density = 0
    public Graph(int V, boolean randomise, double density) {
        this.V = V;
        // Create new adjacency list of size V
        adjList = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            // Every node in adjList leads to another linked list
            adjList[i] = new LinkedList<Edge>();
        }
        // Create new adjacency matrix of size V x V
        adjMatrix = new int[V][V];
        
        // No need to create random graph
        if (!randomise) {
            return;
        }
        
        // To create random graph
        Random rand = new Random();
        // Generates number of edges based on required density of graph
        // Max. edges in a directed graph = V * (V - 1)
        int edgeCount = (int)(density * V * (V - 1));
        for (int i = 0; i < edgeCount; i++) {
            int src = rand.nextInt(V);
            int dest = rand.nextInt(V);
            // Ensure no self loops & no existing edge
            if (src != dest & !existEdge(src, dest)) {
                int weight = rand.nextInt(MAX_WEIGHT) + 1;
                addEdge(src, dest, weight);
            }
            // Try again
            else {
                i--;
            }
        }
    }

    public void addEdge(int src, int dest, int weight) {
        Edge edge = new Edge(src, dest, weight);
        adjList[src].addFirst(edge);
        adjMatrix[src][dest] = weight;
        E++;
    }

    public boolean existEdge(int src, int dest) {
        return adjMatrix[src][dest] != 0;
    }

    public void printGraph() {
        for (int i = 0; i < V; i++) {
            LinkedList<Edge> list = adjList[i];
            for (int j = 0; j < list.size(); j++) {
                System.out.printf("Vertex %d is connected to %d with edge of weight %d%n",
                                  i, list.get(j).dest, list.get(j).weight);
            }
        }
    }
}
