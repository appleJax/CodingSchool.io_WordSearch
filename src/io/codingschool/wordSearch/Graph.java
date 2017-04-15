package io.codingschool.wordSearch;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.Set;

public class Graph {

    public String name;
    private Map<GraphNode, List<Edge>> adjacencyMap;

    public Graph(String name) {
        this.name = name;
        adjacencyMap = new HashMap<GraphNode, List<Edge>>();
    }

    public Set<GraphNode> getNodes() {
        return adjacencyMap.keySet();
    }

    public void addNode(GraphNode node) {
        adjacencyMap.put(node, new LinkedList<Edge>());
    }

    public void removeNode(GraphNode node) {
        adjacencyMap.remove(node);

        Set<GraphNode> vertices = adjacencyMap.keySet();

        for (GraphNode currentNode : vertices) {
            List<Edge> deadEdges = new LinkedList<Edge>();
            List<Edge> neighbors = adjacencyMap.get(currentNode);

            for (Edge edge : neighbors)
                if (edge.destination == node)
                    deadEdges.add(edge);

            for (Edge edge : deadEdges)
                removeEdge(currentNode, node, edge.weight);
        }

    }

    public List<Edge> getNeighbors(GraphNode node) {
        return adjacencyMap.get(node);
    }

    // Unweighted
    public void addEdge(GraphNode origin, GraphNode dest) {
        if (adjacencyMap.containsKey(origin))
            adjacencyMap.get(origin).add(0, new Edge(dest, 1));
    }

    // Weighted
    public void addEdge(GraphNode origin, GraphNode dest, int weight) {
        if (adjacencyMap.containsKey(origin) && adjacencyMap.containsKey(dest))
            adjacencyMap.get(origin).add(0, new Edge(dest, weight));
    }

    public void removeEdge(GraphNode origin, GraphNode dest, int weight) {
        adjacencyMap.get(origin).removeIf(new Predicate<Edge>() {
            @Override
            public boolean test(Edge edge) {
                return edge.destination == dest && edge.weight == weight;
            }
        });
    }

    @Override
    public String toString() {
        String result = name + ": \n";

        for (Map.Entry<GraphNode, List<Edge>> vertex : adjacencyMap.entrySet())
            result += vertex.getKey() + " -> " + vertex.getValue() + "\n";

        return result;
    }
}
