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
            List<GraphNode> visited = new LinkedList<GraphNode>();
            visited.add(node);

            for (Edge edge : graph.getNeighbors(node))
                searchNext(graph, edge.destination, visited, dict);
        }
    }

    private static void searchNext(Graph graph, GraphNode node, List<GraphNode> visited, List<String> wordList) {
        List<String> filteredWords = new LinkedList<String>(wordList);
        List<GraphNode> history = new LinkedList<GraphNode>(visited);
        history.add(node);

        if (history.size() > 2) {
            String word = "";

            for (GraphNode letter : history)
                word += letter.toString();

            // required for lambda (cannot use a non-final local variable)
            final String fullWord = word;

            // filter the dictionary to only include words that start with
            // the current word
            filteredWords = wordList.stream()
                    .filter(str -> str.startsWith(fullWord))
                    .collect(Collectors.toList());

            // if there are no words in the dictionary that start with the
            // current word, stop looking
            if (filteredWords.size() < 1)
                return;

            if (filteredWords.get(0).equals(word))
                System.out.println(word);
        }

        for (Edge edge : graph.getNeighbors(node)) {
            GraphNode nextNode = edge.destination;

            if (!history.contains(nextNode))
                searchNext(graph, nextNode, history, filteredWords);
        }

    }

    private static String[][] searchScaffold = new String[][] {
            { "A", "C", "D", "W", "B", "D", "S" },
            { "G", "D", "N", "O", "D", "N", "S" },
            { "T", "D", "H", "R", "H", "N", "S" },
            { "T", "H", "E", "D", "T", "X", "B" },
            { "Z", "C", "E", "O", "A", "R", "J" },
            { "U", "C", "N", "R", "Y", "D", "S" }
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
