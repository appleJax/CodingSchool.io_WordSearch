package io.codingschool.wordSearch;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class searchBranch extends Thread {

    private Graph graph;
    private GraphNode node;
    private List<GraphNode> visited;
    private List<String> searchFor;

    public searchBranch(Graph graph, GraphNode node, List<GraphNode> visited, List<String> wordList) {
       this.graph = graph;
       this.node = node;
       this.visited = visited;
       this.searchFor = wordList;
    }

    @Override
    public void run() {

        for (Edge edge : graph.getNeighbors(node)) {

            if (!visited.contains(edge.destination)) {

                List<GraphNode> history = new LinkedList<GraphNode>(visited);
                GraphNode nextNode = edge.destination;
                history.add(nextNode);

                String word = "";

                for (GraphNode letter : history)
                    word += letter.toString();

                // required for lambda (cannot use a non-final local variable)
                final String fullWord = word;

                // filter the dictionary to only include words that start with
                // the current word
                List<String> filteredWords = searchFor.stream()
                        .filter(str -> str.startsWith(fullWord))
                        .collect(Collectors.toList());

                // if there are no words in the dictionary that start with the
                // current word, stop looking
                if (filteredWords.size() < 1)
                    continue;

                if (filteredWords.get(0).equals(word))
                    System.out.println(word);

                (new searchBranch(graph, nextNode, history, filteredWords)).start();
            }
        }

        return;

    }

}
