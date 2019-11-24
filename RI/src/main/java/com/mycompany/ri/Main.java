package com.mycompany.ri;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author fdr
 */
public class Main {

    public static String choix;
    public static double b;
    public static  double k1;

    public static void main(String[] args) throws FileNotFoundException {



        // Document testd = null;
        // double test = Document.cosineSimilarity();
        // System.out.println(test);

        Scanner sc = new Scanner(System.in);
        System.out.println("Bien le bonjour ! \n" +
                "Plusieurs choix :\n" +
                "LTN \n" +
                "BM25 \n" +
                "ATN \n" +
                "Votre choix : ");
        choix = sc.nextLine();
        System.out.println("Vous avez choisi : " + choix);
        if(choix.equals("BM25")){
            System.out.println("Paramètre b :  [0,3 - 0,9] virgule, pas point");
            //sc.nextLine();
            b = sc.nextDouble();
            System.out.println("Paramètre k1 : [1,2 - 2,0] virgule, pas point");
            //sc.nextLine();
            k1 = sc.nextDouble();

            System.out.println("Lancement de BM25 avec : \n" +
                    "b = "+b+"\n" +
                    "k1 = "+k1);
        }

        long startTime = System.nanoTime();
        List<String> stopWords = new ArrayList<>();
        //File file = new File("./stopWords");
        File file = new File("C:\\Users\\madji\\IdeaProjects\\Recherche-d-information\\RI\\src\\main\\java\\com\\mycompany\\ri\\stopWords");
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r"); //delimitor is one or more spaces
        while (input.hasNext()) {
            stopWords.add(input.next());
        }


        Indexation.indexFile("C:\\Users\\madji\\IdeaProjects\\Recherche-d-information\\RI\\src\\main\\java\\com\\mycompany\\ri\\file", stopWords);
        //Indexation.indexFile("./file", stopWords);


        long endTime = System.nanoTime();

        long durationInNano = (endTime - startTime);

        long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);

        System.out.println("Temps total d'éxécution : "+durationInMillis);
    }

}
