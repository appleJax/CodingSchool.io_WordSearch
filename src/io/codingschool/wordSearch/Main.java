package io.codingschool.wordSearch;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static List<String> dict = new Dictionary().wordList;

    public static void main(String[] args) {

        Graph wordSearch = new Graph("Word Search");

        // Create a 2D array of graph nodes from a 2D array of letters
        GraphNode[][] graphNodes = makeNodes(searchScaffold);

        // Add all nodes to graph
        for (int i = 0; i < graphNodes.length; i++)
            for (int j = 0; j < graphNodes[i].length; j++)
                wordSearch.addNode(graphNodes[i][j]);

        // Add all edges to graph
        for (int i = 0; i < graphNodes.length; i++)
            for (int j = 0; j < graphNodes[i].length; j++) {
                GraphNode currentNode = graphNodes[i][j];

                if (i > 0)
                    wordSearch.addEdge(currentNode, graphNodes[i - 1][j]);
                if (j < graphNodes[i].length - 1)
                    wordSearch.addEdge(currentNode, graphNodes[i][j + 1]);
                if (i < graphNodes.length - 1)
                    wordSearch.addEdge(currentNode, graphNodes[i + 1][j]);
                if (j > 0)
                    wordSearch.addEdge(currentNode, graphNodes[i][j - 1]);
            }

        System.out.println("Word Search Puzzle:");
        System.out.println();

        for (int i = 0; i < searchScaffold.length; i++) {
            for (int j = 0; j < searchScaffold[0].length; j++)
                System.out.print(searchScaffold[i][j] + " ");

            System.out.println();
        }

        // Word Search!!!
        findWords(wordSearch);
        System.out.println("Finished!");

    } // end main

    // DFS
    private static void findWords(Graph graph) {
        System.out.println();
        System.out.println("Searching...");

        Set<GraphNode> nodes = graph.getNodes();

        for (GraphNode node : nodes) {
            // Start a new thread for each branch
            for (Edge edge : graph.getNeighbors(node)) {
                List<GraphNode> visited = new LinkedList<GraphNode>();

                GraphNode nextNode = edge.destination;
                visited.add(node);
                visited.add(nextNode);

                String wordStem = node.toString() + nextNode.toString();

                List<String> searchFor = dict.stream()
                        .filter(str -> str.startsWith(wordStem))
                        .collect(Collectors.toList());

                (new searchBranch(graph, nextNode, visited, searchFor)).start();
            }
        }
    }

    private static String[][] searchScaffold = new String[][] {
            { "A", "C", "D", "W", "B", "D", "S", "E" },
            { "G", "D", "N", "O", "D", "N", "S", "L" },
            { "T", "D", "H", "R", "H", "N", "S", "N" },
            { "T", "H", "E", "D", "T", "X", "B", "E" },
            { "Z", "C", "E", "O", "A", "R", "J", "I" },
            { "U", "Z", "N", "R", "Y", "D", "S", "G" },
            { "D", "O", "O", "H", "R", "O", "B", "H" },
            { "J", "U", "N", "K", "A", "P", "M", "I" }
    };

    private static GraphNode[][] makeNodes(String[][] nodes) {
        int height = nodes.length;
        int width = nodes[0].length;

        GraphNode[][] graphNodes = new GraphNode[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                graphNodes[i][j] = new GraphNode(nodes[i][j]);

        return graphNodes;
    }
}
