package io.codingschool.wordSearch;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class searchBranch extends Thread {

    private Graph graph;
    private GraphNode node;
    private List<GraphNode> history;
    private List<String> filteredWords;

    public searchBranch(Graph graph, GraphNode node, List<GraphNode> visited, List<String> wordList) {
       this.graph = graph;
       this.node = node;
       this.history = new LinkedList<GraphNode>(visited);
       this.filteredWords = new LinkedList<String>(wordList);
    }

    @Override
    public void run() {

        history.add(node);

        if (history.size() > 2) {
            String word = "";

            for (GraphNode letter : history)
                word += letter.toString();

            // required for lambda (cannot use a non-final local variable)
            final String fullWord = word;

            // filter the dictionary to only include words that start with
            // the current word
            filteredWords = filteredWords.stream()
                    .filter(str -> str.startsWith(fullWord))
                    .collect(Collectors.toList());

            // if there are no words in the dictionary that start with the
            // current word, stop looking
            if (filteredWords.size() < 1)
                return;

            if (filteredWords.get(0).equals(word))
                System.out.println(word);

            if (filteredWords.size() == 1)
                return;
        }

        for (Edge edge : graph.getNeighbors(node)) {
            GraphNode nextNode = edge.destination;

            if (!history.contains(nextNode))
                (new searchBranch(graph, nextNode, history, filteredWords)).start();
        }

    }

}
