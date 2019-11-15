package com.mycompany.ri;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author fdr
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        System.out.println("Test cosine similarity <<<<<>>>>>");
        // Document testd = null;
        // double test = Document.cosineSimilarity();

        // System.out.println(test);


        List<String> stopWords = new ArrayList<>();
        File file = new File("./stopWords");
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r"); //delimitor is one or more spaces
        while (input.hasNext()) {
            stopWords.add(input.next());
        }


        Indexation.indexFile("./file", stopWords);


    }

}
