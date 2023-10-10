package project_2;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DijkstraAlgo {
    // Estimated lengths of shortest paths from source node to all vertices
    private static int[] d;
    // Predecessors for each vertex
    private static int[] pi;
    // Keep track of visited vertices (in place of S[])
    private static boolean[] visited;
    // Priority queue holds edges
    private static PriorityQueue<Edge> pq;

    public DijkstraAlgo(int V) {
        d = new int[V];
        pi = new int[V];
        visited = new boolean[V];
        // Priority queue of max. size V
        pq = new PriorityQueue<>(V, new Comparator<Edge>() {
            @Override
            // Defining new basis for comparison using Comparator
            public int compare(Edge e1, Edge e2) {
                if (e1.weight > e2.weight) {
                    return 1;
                }
                else if (e1.weight < e2.weight) {
                    return -1;
                }
                // e1.weight == e2.weight
                return 0;
            }
        });
    }

    private static void initialisation(int V, int src) {
        // Initialise arrays
        for (int i = 0; i < V; i++) {
            d[i] = Integer.MAX_VALUE;
            pi[i] = -1;
            visited[i] = false;
        }
        // Set dist. of source node = 0
        d[src] = 0;
    }
    // Returns index of neighbour with the shortest path
    // Returns -1 if no neighbour found
    private static int findNextNode(int V) {
        int minCost = Integer.MAX_VALUE;
        int minIdx = -1;
        for (int i = 0; i < V; i++) {
            if (!visited[i] && d[i] < minCost) {
                minCost = d[i];
                minIdx = i;
            }
        }
        return minIdx;
    }
    // Use pi[] to keep track of shortest paths
    private static void printPath(int src, int dest) {
        if (src == dest) {
            System.out.printf("%d ", src);
            return;
        }
        printPath(src, pi[dest]);
        System.out.printf("%d ", dest);
    }

    private static void printResult(int V, int src) {
        for (int i = 0; i < V; i++) {
            if (d[i] == Integer.MAX_VALUE) {
                System.out.printf("No path exists from %d to %d%n", src, i);
            }
            else {
                System.out.printf("Lowest cost edge from %d to %d is %d%n", src, i, d[i]);
                System.out.print("Path: ");
                printPath(src, i);
                System.out.println();
            }
        }
    }

    // Drivers
    public void dijkstraArray(int V, int src, int[][] adjMatrix, boolean showResults) {
        initialisation(V, src);
        int u;
        while ((u = findNextNode(V)) != -1) {
            // Mark vertex u as visited
            visited[u] = true;
            for (int v = 0; v < V; v++) {
                /*
                 * Check that: vertex v has not been visited
                 *           & edge(u,v) exists
                 *           & shortest path from src to u exists
                 *           & cost of new path < cost of shortest path recorded
                 */
                if (!visited[v] && adjMatrix[u][v] != 0 && d[u] != Integer.MAX_VALUE && d[u] + adjMatrix[u][v] < d[v]) {
                    // Update new lowest cost
                    d[v] = d[u] + adjMatrix[u][v];
                    // Update predecessor
                    pi[v] = u;
                }
            }
        }

        // Show results of Dijkstra
        if (showResults) {
            printResult(V, src);
        }
    }
    public void dijkstraPQ(int V, int src, LinkedList<Edge>[] adjList, boolean showResults) {
        initialisation(V, src);
        // Adding first vertex (src) to pq
        pq.add(new Edge(src, src, 0));
        while (!pq.isEmpty()) {
            // Remove min. cost edge from pq
            Edge minEdge = pq.poll();
            int u = minEdge.dest;
            
            // Check if u has been visited
            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            // Iterate through every edge in adjList[u]
            for (Edge e : adjList[u]) {
                int v = e.dest;
                /*
                 * Check that: vertex v has not been visited
                 *           & shortest path from src to u exists
                 *           & cost of new path < cost of shortest path recorded
                 * No need to check if edge[u][v] exists since it must exist to be stored in adjList[u]
                 */
                if (!visited[v] && d[u] != Integer.MAX_VALUE && d[u] + e.weight < d[v]) {
                    // Update new lowest cost
                    d[v] = d[u] + e.weight;
                    // Update predecessor
                    pi[v] = u;
                    // Insert edge(u,v) into pq according to its d[v]
                    pq.add(new Edge(u, v, d[v]));
                }
            }
        }

        // Show results of Dijkstra
        if (showResults) {
            printResult(V, src);
        }
    }
}
