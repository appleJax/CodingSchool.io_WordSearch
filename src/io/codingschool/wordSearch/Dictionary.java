package io.codingschool.wordSearch;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

public class Dictionary {

    private List<String> dictionary;

    public Dictionary() {
        dictionary = new LinkedList<String>();

        File file = new File("./dictionary.txt");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedReader scan = null;

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            scan = new BufferedReader(new InputStreamReader(fis));

            // scan.ready() returns false if the file does not have more lines.
            while (scan.ready()) {

                // this statement reads the line from the file and print it to
                // the console.
                String word = scan.readLine().toUpperCase();

                if (word.length() > 2 && !word.equals("THE"))
                    dictionary.add(word);
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isWord(String word) {
        return dictionary.contains(word);
    }
}
