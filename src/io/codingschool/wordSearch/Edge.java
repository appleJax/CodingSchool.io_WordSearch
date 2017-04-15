package io.codingschool.wordSearch;

public class Edge {

    public GraphNode destination;
    public int weight;

    public Edge(GraphNode node, int weight) {
        this.destination = node;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "(" + destination + "," + weight + ")";
    }
}
