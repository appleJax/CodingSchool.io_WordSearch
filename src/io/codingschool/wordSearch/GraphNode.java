package io.codingschool.wordSearch;

public class GraphNode {

    public String name;

    public GraphNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
