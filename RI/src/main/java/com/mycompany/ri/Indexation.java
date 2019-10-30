/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri;

import com.mycompany.ri.Models.Document;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author fdr
 */
public class Indexation {

    public static void indexFile(String path, List<String> stopWords) throws FileNotFoundException {
        List<Document> listeDoc = new ArrayList<>();
        Document currentDoc = null;
        File file = new File("./file");
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r|\'"); 

        while (input.hasNext()) {
            String string = input.next().toLowerCase();

            if (string.contains("<doc><docno>")) {//Nouveau document

                string = string.replace("<doc><docno>", "");
                string = string.replace("</docno>", "");
                currentDoc = new Document(string);//On crée le document avec son ID
            } else if (string.equals("</doc>")) {//Fin de doc: on l'ajoute a la liste
                listeDoc.add(currentDoc);
                 System.out.println(currentDoc.index);
            } else if (!stopWords.contains(string) && string.length() > 1) {//Ajout des mots a l'index

                currentDoc.index.put(string, currentDoc.getTf(string) + 1);

            }
        }
       
    }
}