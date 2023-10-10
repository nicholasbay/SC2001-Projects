package project_2;

import java.util.Scanner;

public class Tests {
    static final int MIN_NO_OF_VERTICES = 10;
    static final int RUN_COUNT_PER_GRAPH = 10;
    static final double MIN_DENSITY = 0.01;
    static final double MAX_DENSITY = 1;

    public enum GraphDensity {
        SPARSE,
        NEUTRAL,
        DENSE
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        
        do {
            System.out.println("\n1. testPrintGraph()");
            System.out.println("2. testDijkstraArray()");
            System.out.println("3. testDijkstraPQ()");
            System.out.println("4. testCreateRandGraph()");
            System.out.println("5. empiricalTest()");
            System.out.println("6. empiricalTestFixedV()");
            System.out.println("7. Exit\n");
            System.out.println("Enter choice:");
            choice = Integer.parseInt(scan.nextLine());
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
                    int maxV = Integer.parseInt(scan.nextLine());
                    
                    System.out.println("\nSPARSE graph (edge density = 0.25)");
                    System.out.println("NEUTRAL graph (edge density = 0.5)");
                    System.out.println("DENSE graph (edge density = 0.75)\n");
                    System.out.println("Choose density of graph to be generated (SPARSE / NEUTRAL / DENSE):");
                    GraphDensity density = GraphDensity.valueOf(scan.nextLine().toUpperCase());
                    
                    empiricalTest(maxV, density);
                    break;
                case 6:
                    System.out.println("Enter number of vertices:");
                    int V = Integer.parseInt(scan.nextLine());
                    System.out.println("Enter graph density interval (btw. 0 & 1 exclusive):");
                    double interval = scan.nextDouble();
                    scan.nextLine();
                    empiricalTestFixedV(V, interval);
                    break;
                case 7:
                    scan.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
                    break;
            }
        } while (choice != 7);
    }

    private static Graph sampleGraph() {
        Graph g = new Graph(6, false, 0);
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
        // Need to display results to ascertain correctness of implementation
        dAlgo.dijkstraArray(g.V, 0, g.adjMatrix, true);
    }

    private static void testDijkstraPQ() {
        System.out.println("testDijkstraPQ()");
        Graph g = sampleGraph();
        DijkstraAlgo dAlgo = new DijkstraAlgo(g.V);
        // Need to display results to ascertain correctness of implementation
        dAlgo.dijkstraPQ(g.V, 0, g.adjList, true);
    }

    private static void testCreateRandGraph(int V) {
        // Generate random graph of edge density 0.5
        Graph g = new Graph(V, true, 0.5);
        g.printGraph();
    }
    
    // Vary V, keeping edge density of every random graph generated at specified level
    private static void empiricalTest(int maxV, GraphDensity density) throws Exception {
        int vertexCount = MIN_NO_OF_VERTICES;
        // Number of graphs that will be created
        int graphCount = maxV - vertexCount + 1;
        
        String[] vertexArr = new String[graphCount];
        String[] edgeArr = new String[graphCount];
        String[] partAResults = new String[graphCount];
        String[] partBResults = new String[graphCount];

        System.out.println(String.format("empiricalTest() for %s graph%n", density));
        for (int i = 0; i < graphCount; i++) {
            System.out.println("Creating graph " + (i+1) + " with " + vertexCount + " vertices...");
            Graph g;

            // Generate random graph based on required edge density
            switch (density) {
                case DENSE:
                    g = new Graph(vertexCount++, true, 0.75);
                    break;
                case NEUTRAL:
                    g = new Graph(vertexCount++, true, 0.5);
                    break;
                case SPARSE:
                    g = new Graph(vertexCount++, true, 0.25);
                    break;
                default:
                    g = new Graph(vertexCount++, true, 0.5);
                    break;
            }
            
            // Store specifications of this particular graph
            vertexArr[i] = g.V + "";
            edgeArr[i] = g.E + "";
            // Keep track of total empirical runtime for 10 runs
            long partATotal = 0;
            long partBTotal = 0;

            // Run Dijkstra's 10x, then take avg. runtime
            for (int j = 0; j < RUN_COUNT_PER_GRAPH; j++) {
                DijkstraAlgo dAlgo = new DijkstraAlgo(g.V);
                
                long partAStart = System.nanoTime();
                // Perform Dijkstra's on adjMatrix using Array
                // Do not show results as that will affect runtimes
                dAlgo.dijkstraArray(g.V, 0, g.adjMatrix, false);
                long partAEnd = System.nanoTime();
                partATotal += (partAEnd - partAStart);

                long partBStart = System.nanoTime();
                // Perform Dijkstra's on adjList using PQ
                // Do not show results as that will affect runtimes
                dAlgo.dijkstraPQ(g.V, 0, g.adjList, false);
                long partBEnd = System.nanoTime();
                partBTotal += (partBEnd - partBStart);
            }

            // Store avg. runtime per iteration of Dijkstra's
            partAResults[i] = (partATotal / RUN_COUNT_PER_GRAPH) + "";
            partBResults[i] = (partBTotal / RUN_COUNT_PER_GRAPH) + "";
        }

        // Combine String arrays for writing to CSV
        String[][] results = {vertexArr, edgeArr, partAResults, partBResults};
        String[] headers = {"V", "E", "a) runtime", "b) runtime"};
        String filename = String.format("results_%s.csv", density);
        WriteToCSV.writeFile("project2/" + filename, headers, results);

        // Completion message
        System.out.println("\nCheck '" + filename + "' for updated results");
    }
    // Vary edge density from 0.01 to 1 at intervals of 0.01
    private static void empiricalTestFixedV(int V, double interval) throws Exception {
        // Error checking
        if (interval == 0 | interval == 1) {
            System.out.println("Interval must be a float between 0 and 1 exclusive!");
            return;
        }

        // Number of graphs that will be created
        int graphCount = (int)((MAX_DENSITY - MIN_DENSITY) / interval) + 1;
        // Initialise edge density to 0.01, then increment in intervals of 0.01
        double edgeDensity = MIN_DENSITY;
        
        String[] edgeDensityArr = new String[graphCount];
        String[] partAResults = new String[graphCount];
        String[] partBResults = new String[graphCount];

        System.out.println(String.format("empiricalTestFixedV() for V = %d%n", V));
        for (int i = 0; i < graphCount; i++) {
            System.out.println(String.format("Creating graph %d of edge density %.2f...", i+1, edgeDensity));
            // Generate random graph
            Graph g = new Graph(V, true, edgeDensity);
            // Store specifications of this particular graph
            edgeDensityArr[i] = edgeDensity + "";
            // Keep track of total empirical runtime for 10 runs
            long partATotal = 0;
            long partBTotal = 0;

            // Run Dijkstra's 10x, then take avg. runtime
            for (int j = 0; j < RUN_COUNT_PER_GRAPH; j++) {
                DijkstraAlgo dAlgo = new DijkstraAlgo(g.V);
                
                long partAStart = System.nanoTime();
                // Perform Dijkstra's on adjMatrix using Array
                // Do not show results as that will affect runtimes
                dAlgo.dijkstraArray(g.V, 0, g.adjMatrix, false);
                long partAEnd = System.nanoTime();
                partATotal += (partAEnd - partAStart);

                long partBStart = System.nanoTime();
                // Perform Dijkstra's on adjList using PQ
                // Do not show results as that will affect runtimes
                dAlgo.dijkstraPQ(g.V, 0, g.adjList, false);
                long partBEnd = System.nanoTime();
                partBTotal += (partBEnd - partBStart);
            }

            // Store avg. runtime per iteration of Dijkstra's
            partAResults[i] = (partATotal / RUN_COUNT_PER_GRAPH) + "";
            partBResults[i] = (partBTotal / RUN_COUNT_PER_GRAPH) + "";

            // Increment edgeDensity
            edgeDensity += interval;
        }

        // Combine String arrays for writing to CSV
        String[][] results = {edgeDensityArr, partAResults, partBResults};
        String[] headers = {"Edge Density", "a) runtime", "b) runtime"};
        String filename = String.format("results_fixedV_%d.csv", V);
        WriteToCSV.writeFile("project_2/" + filename, headers, results);

        // Completion message
        System.out.println("\nCheck '" + filename + "' for updated results");
    }
}
