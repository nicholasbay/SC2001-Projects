package project_2;

import java.util.LinkedList;
import java.util.Random;

public class Graph {
    static final int MAX_WEIGHT = 10;
    // Instance variables
    int V;
    int E;
    LinkedList<Edge>[] adjList;
    int[][] adjMatrix;
    
    // Constructors
    public Graph(int V, boolean randomise) {
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
        // Max. edges for directed graph = V * (V - 1), excluding self loops
        int maxE = V * (V - 1);
        int randEdgeCount = rand.nextInt(maxE - V + 1) + 1;
        for (int i = 0; i < randEdgeCount; i++) {
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
