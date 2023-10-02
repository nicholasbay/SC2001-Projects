package project_2;

import java.util.Scanner;

public class Tests {
    static final int MIN_NO_OF_VERTICES = 10;
    static final int RUN_COUNT_PER_GRAPH = 10;

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. testPrintGraph()");
            System.out.println("2. testDijkstraArray()");
            System.out.println("3. testDijkstraPQ()");
            System.out.println("4. testCreateRandGraph()");
            System.out.println("5. empiricalTest()");
            System.out.println("6. Exit\n");
            System.out.println("Enter choice:");
            int choice = Integer.parseInt(scan.nextLine());
            switch (choice) {
                case 1:
                    testPrintGraph();
                    break;
                case 2:
                    testDijkstraArray();
                    break;
                case 3:
                    testDijkstraPQ();
                    break;
                case 4:
                    System.out.println("Enter number of vertices:");
                    testCreateRandGraph(Integer.parseInt(scan.nextLine()));
                    break;
                case 5:
                    System.out.println("Enter max. number of vertices:");
                    empiricalTest(Integer.parseInt(scan.nextLine()));
                    break;
                case 6:
                    scan.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
                    break;
            }
        }
    }

    private static Graph sampleGraph() {
        Graph g = new Graph(6, false);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 5);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, 2);
        g.addEdge(4, 0, 4);
        g.addEdge(4, 1, 4);
        g.addEdge(4, 5, 6);
        return g;
    }

    private static void testPrintGraph() {
        System.out.println("testPrintGraph()");
        Graph g = sampleGraph();
        g.printGraph();
    }

    private static void testDijkstraArray() {
        System.out.println("testDijkstraArray()");
        Graph g = sampleGraph();
        DijkstraAlgo dAlgo = new DijkstraAlgo(g.V);
        dAlgo.dijkstraArray(g.V, 0, g.adjMatrix);
    }

    private static void testDijkstraPQ() {
        System.out.println("testDijkstraPQ()");
        Graph g = sampleGraph();
        DijkstraAlgo dAlgo = new DijkstraAlgo(g.V);
        dAlgo.dijkstraPQ(g.V, 0, g.adjList);
    }

    private static void testCreateRandGraph(int V) {
        Graph g = new Graph(V, true);
        g.printGraph();
    }
    
    private static void empiricalTest(int maxV) throws Exception {
        int vertexCount = MIN_NO_OF_VERTICES;
        // Number of graphs that will be created
        int graphCount = maxV - vertexCount + 1;
        
        String[] vertexArr = new String[graphCount];
        String[] edgeArr = new String[graphCount];
        String[] partAResults = new String[graphCount];
        String[] partBResults = new String[graphCount];

        System.out.println("empiricalTest()");
        for (int i = 0; i < graphCount; i++) {
            System.out.println("Creating graph " + i + " with " + vertexCount + " vertices");
            Graph g = new Graph(vertexCount++, true);
            
            // Store specifications of this particular graph
            vertexArr[i] = g.V + "";
            edgeArr[i] = g.E + "";
            // Keep track of total empirical runtime for 10 runs
            long partATotal = 0;
            long partBTotal= 0;

            // Run Dijkstra's 10x, then take avg. runtime
            for (int j = 0; j < RUN_COUNT_PER_GRAPH; j++) {
                DijkstraAlgo dAlgo = new DijkstraAlgo(g.V);
                
                long partAStart = System.nanoTime();
                // Perform Dijkstra's on adjMatrix using Array
                dAlgo.dijkstraArray(g.V, 0, g.adjMatrix);
                long partAEnd = System.nanoTime();
                partATotal += (partAEnd - partAStart);

                long partBStart = System.nanoTime();
                // Perform Dijkstra's on adjList using PQ
                dAlgo.dijkstraPQ(g.V, 0, g.adjList);
                long partBEnd = System.nanoTime();
                partBTotal += (partBEnd - partBStart);
            }

            // Store avg runtime per iteration of Dijkstra's
            partAResults[i] = (partATotal / RUN_COUNT_PER_GRAPH) + "";
            partBResults[i] = (partBTotal / RUN_COUNT_PER_GRAPH) + "";
        }

        // Combine String arrays for writing to CSV
        String[][] results = {vertexArr, edgeArr, partAResults, partBResults};
        String[] headers = {"Vertex count", "Edge count", "a) avg. runtime", "b) avg. runtime"};
        WriteToCSV.writeFile("project_2/results.csv", headers, results);

        // Completion message
        System.out.println("\nCheck 'results.csv' for updated results");
    }
}
