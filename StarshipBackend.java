import java.io.FileNotFoundException;
import java.util.List;

/**
 * Back end engine for starship application that facilitates delivery routes for
 * starship delivery and finds the shortest routes.
 */
public class StarshipBackend implements GraphADT{


    private StarshipGraph<Object> graph = new StarshipGraph<>();
    public void load(String csvFilePath) throws FileNotFoundException {
        StarshipLoader loadGraph = new StarshipLoader();
        this.graph = loadGraph.loadFile(csvFilePath);
    }

    /**
     * Inserts a vertex of data
     *
     * @param data the data item stored in the new vertex
     * @return true if the data can be inserted as a new vertex, false if it is already in the graph
     */
    @Override
    public boolean insertVertex (Object data) { return graph.insertVertex(data); }

    /**
     * Finds a vertex of data and removes it from the graph
     *
     * @param data the data item stored in the vertex to remove
     * @return true if a vertex with *data* has been removed, false if it was not in the graph
     */
    @Override
    public boolean removeVertex (Object data) {
        return graph.removeVertex(data);
    }

    /**
     * Insert a new directed path with a positive distance into the graph.
     *
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight for the edge (has to be a positive integer)
     * @return true if the edge could be inserted or its weight updated, false if the edge with the same weight was already in the graph with the graph
     */
    @Override
    public boolean insertEdge (Object source, Object target, int weight) {
        if (this.getEdgeCountVertex(source) >= 3) {
            throw new IllegalArgumentException("This vertex already has 3 leaving edges");
        }
        return graph.insertEdge(source, target, weight);
    }

    /**
     * Remove a path from the graph.
     *
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge could be removed, false if it was not in the graph
     */
    @Override
    public boolean removeEdge (Object source, Object target) { return graph.removeEdge(source, target); }

    /**
     * Check if the graph contains a vertex with data item *data*.
     *
     * @param data the data item to check for
     * @return true if data item is stored in a vertex of the graph, false otherwise
     */
    @Override
    public boolean containsVertex (Object data) { return graph.containsVertex(data); }

    /**
     * Check if edge is in the graph.
     *
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if data item is stored in a vertex of the graph, false otherwise
     */
    @Override
    public boolean containsEdge (Object source, Object target) { return graph.containsEdge(source, target); }

    /**
     * Return the weight of an edge.
     *
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return the weight of the edge (0 or positive integer)
     */
    @Override
    public int getWeight (Object source, Object target) { return graph.getWeight(source, target); }

    /**
     * Returns the shortest path between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     *
     * @param start the data item in the starting vertex for the path
     * @param end   the data item in the destination vertex for the path
     * @return list of data item in vertices in order on the shortest path between vertex
     * with data item start and vertex with data item end, including both start and end
     */
    @Override
    public List shortestPath (Object start, Object end) {
        return graph.shortestPath(start, end);
    }

    /**
     * Returns the cost of the path (sum over edge weights) between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     *
     * @param start the data item in the starting vertex for the path
     * @param end   the data item in the destination vertex for the path
     * @return the cost of the shortest path between vertex with data item start
     * and vertex with data item end, including all edges between start and end
     */
    @Override
    public int getPathCost (Object start, Object end) {
        return graph.getPathCost(start, end);
    }

    /**
     * Check if the graph is empty (does not contain any vertices or edges).
     *
     * @return true if the graph does not contain any vertices or edges, false otherwise
     */
    @Override
    public boolean isEmpty () {
        return graph.isEmpty();
    }

    /**
     * Return the number of edges in the graph.
     *
     * @return the number of edges in the graph
     */
    @Override
    public int getEdgeCount () {
        return graph.getEdgeCount();
    }

    /**
     * Return the number of vertices in the graph
     *
     * @return the number of vertices in the graph
     */
    @Override
    public int getVertexCount () {
        return graph.getVertexCount();
    }

    /**
     * returns number of edges leaving a certain vertex
     *
     * @param source the data item contained in the source vertex for the edge
     * @return the number of edges leaving
     */
    public int getEdgeCountVertex(Object source) { return graph.vertices.get(source).edgesLeaving.size(); }
}

