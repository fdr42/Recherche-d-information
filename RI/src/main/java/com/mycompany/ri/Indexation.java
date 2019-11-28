/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri;

import com.mycompany.ri.Models.Document;

import java.io.*;
import java.util.*;

/**
 * @author fdr + madjid
 */
public class Indexation {
    /* Old
    public static String stemming(String input) {
        if (input.endsWith("ed")) {
            input = input.replaceAll("ed$", "");
        } else if (input.endsWith("ing")) {
            input = input.replaceAll("ing$", "");
        } else if (input.endsWith("ly")) {
            input = input.replaceAll("ly$", "");
        } else if (input.endsWith("sses")) {
            input = input.replaceAll("sses$", "ss");
        } else if (input.endsWith("ss")) {

        } else if (input.endsWith("s")) {
            input = input.replaceAll("s$", "");
        }
        return input;
    }*/

    public static String[] listQuery(String query){
        String[] queryList = query.split(" ");
        List<String> stemmedList;
        stemmedList=new ArrayList<>();
        for(String s : queryList) {
            stemmedList.add(PorterStemmer.stemWord(s));
        }
       return stemmedList.toArray(new String[0]);
    }
    public static List<String> queryListTriee(Map<String, Double> maptriee) {
        List<String> queryListTriee = new ArrayList<>();
        int zero=0;
        for (Map.Entry<String, Double> entry : maptriee.entrySet()) {
            queryListTriee.add(entry.getKey());
            if (entry.getValue().equals(0.0)) {
                zero++;
            }
        }
       // System.out.println(queryListTriee);
        //System.out.println("Il y a " + zero + " zeros dans cette liste");
        return queryListTriee;
    }
    public static void indexFile(String path, List<String> stopWords) throws FileNotFoundException {
        List<Document> listeDoc = new ArrayList<>();
        Document currentDoc = null;
        File file = new File(path);
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r|\'|\"|[\\\\.,()=-]");

        double i = 0;
        int j=0;
         Map<String, Integer> index=new HashMap<>();
        while (input.hasNext()) {
            double percent=(i / 19198619) * 100;
           if(percent>j) {
                for(int k=0;k<50;k++)
                     System.out.print("\b\b\b");
                System.out.print((int)percent+"% [");
                for(int k=0;k<j/2;k++)
                    System.out.print("=");
                for(int k=j/2;k<100/2;k++)
                    System.out.print(" ");
                System.out.print("]");
                j++;
            }

            i++;
            String string = input.next().toLowerCase();
            if (string.contains("<doc><docno>")) {//Nouveau document
                string = string.replace("<doc><docno>", "");
                string = string.replace("</docno>", "");

                currentDoc = new Document(string);//On crée le document avec son ID
            } else if (string.equals("</doc>")) {//Fin de doc: on l'ajoute a la liste
                listeDoc.add(currentDoc);
               i++;
              //  System.out.println(currentDoc.index.size());
               // System.out.println(currentDoc.index);

            } else if (!stopWords.contains(string)
                    && PorterStemmer.stemWord(string).length() > 1
                    && !string.matches(".*\\d.*")
                    && !string.contains("/")) {//Ajout des mots a l'index
                string=PorterStemmer.stemWord(string);
                currentDoc.index.put(string, currentDoc.getTf(string) + 1);
                currentDoc.totalWords++;
                index.put(string, currentDoc.getTf(string) + 1);
            }

        }
        System.out.println("Total unique = "+index.size());
        System.out.println("<<<<<<<<<<<<<<<<"+i);

        System.out.println("TAILLE LISTE DOC >>> " + listeDoc.size());
        //on va construire le run :
Main.choice();
System.out.println("C'est parti");
        List<String[]> listeQ = new ArrayList<>();
        listeQ.add(listQuery("olive oil health benefit"));
        listeQ.add(listQuery("notting hill film actors"));
        listeQ.add(listQuery("probabilistic models in information retrieval"));
        listeQ.add(listQuery("web link network analysis"));
        listeQ.add(listQuery("web ranking scoring algorithm"));
        listeQ.add(listQuery("supervised machine learning algorithm"));
        listeQ.add(listQuery("operating system +mutual +exclusion"));

        List<String> listeNumQ = new ArrayList<>();
        listeNumQ.add("2009011");
        listeNumQ.add("2009036");
        listeNumQ.add("2009067");
        listeNumQ.add("2009073");
        listeNumQ.add("2009074");
        listeNumQ.add("2009078");
        listeNumQ.add("2009085");


        Map<String, Double> mapFinale = new HashMap<>();

        //map pour chaque requete
        Map<String, Double> mapQuery1 = new HashMap<>();
        Map<String, Double> mapQuery2 = new HashMap<>();
        Map<String, Double> mapQuery3 = new HashMap<>();
        Map<String, Double> mapQuery4 = new HashMap<>();
        Map<String, Double> mapQuery5 = new HashMap<>();
        Map<String, Double> mapQuery6 = new HashMap<>();
        Map<String, Double> mapQuery7 = new HashMap<>();

        String tag = "<>";
        int compteur = 0;
        //pour chaque doc
        boolean passed = false;
        for (int q = 0; q < listeQ.size(); q++) {
            for (Document doc : listeDoc) {
                //pour chaque query
                compteur++;

                double cosSim;
                if(Main.choix.equals("ATN")){
                     cosSim = Document.atn(doc, listeQ.get(q),listeDoc);
                }else if(Main.choix.equals("BM25")){
                     cosSim = doc.okapi(listeQ.get(q),listeDoc);
                }else if (Main.choix.equals("LTN")){
                     cosSim = Document.cosineSimilarity(doc, listeQ.get(q),listeDoc);
                }else {
                    if(!passed){
                        System.out.println("Erreur, cette fonction n'est pas connue \n " +
                                "Par défaut, on va prendre LTN \n" +
                                "Cordialement");
                        passed = true;
                    }
                     cosSim = Document.cosineSimilarity(doc, listeQ.get(q),listeDoc);
                }


                mapFinale.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                //System.out.println(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]");
                switch (q) {
                    case 0:
                        mapQuery1.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 1:
                        mapQuery2.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 2:
                        mapQuery3.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 3:
                        mapQuery4.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 4:
                        mapQuery5.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 5:
                        mapQuery6.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 6:
                        mapQuery7.put(listeNumQ.get(q) + " Q0 " + doc.id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                }

            }

            /*


            Mettre dans le runs par requete ici
            Trier par score
             */
        }

        Map<String, Double> mapTriee,mapTriee1,mapTriee2,mapTriee3,mapTriee4,mapTriee5,mapTriee6,mapTriee7 = new HashMap<>();


        mapTriee = MapUtil.sortByValue(mapFinale);


        mapTriee1 = MapUtil.sortByValue(mapQuery1);
        mapTriee2 = MapUtil.sortByValue(mapQuery2);
        mapTriee3 = MapUtil.sortByValue(mapQuery3);
        mapTriee4 = MapUtil.sortByValue(mapQuery4);
        mapTriee5 = MapUtil.sortByValue(mapQuery5);
        mapTriee6 = MapUtil.sortByValue(mapQuery6);
        mapTriee7 = MapUtil.sortByValue(mapQuery7);


            //on met les hashmap dans des arralist pour reverse facilement


        List<String> queryListTriee1 =queryListTriee(mapTriee1) ;
        Collections.reverse(queryListTriee1);

        List<String> queryListTriee2 = queryListTriee(mapTriee2);
        Collections.reverse(queryListTriee2);

        List<String> queryListTriee3 = queryListTriee(mapTriee3);
        Collections.reverse(queryListTriee3);

        List<String> queryListTriee4 = queryListTriee(mapTriee4);
        Collections.reverse(queryListTriee4);

        List<String> queryListTriee5 = queryListTriee(mapTriee5);
        Collections.reverse(queryListTriee5);

        List<String> queryListTriee6 = queryListTriee(mapTriee6);
        Collections.reverse(queryListTriee6);

        List<String> queryListTriee7 = queryListTriee(mapTriee7);
        Collections.reverse(queryListTriee7);

        PrintWriter testOut = null;
        PrintWriter run2 = null;
        PrintWriter run3 = null;
        PrintWriter run4 = null;
        PrintWriter run5 = null;


        try {
            testOut = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_01_"+Main.choix+"_articles_k"+Main.k1+"b"+Main.b+".txt"))));
            run2 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_02_"+Main.choix+"_articles_k"+Main.k1+"b"+Main.b+".txt"))));
            run3 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_03_"+Main.choix+"_articles_k"+Main.k1+"b"+Main.b+".txt"))));
            run4 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_04_"+Main.choix+"_articles_k"+Main.k1+"b"+Main.b+".txt"))));
            run5 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_05_"+Main.choix+"_articles_k"+Main.k1+"b"+Main.b+".txt"))));
        } catch (Exception e1) {
            e1.printStackTrace();
            System.out.println(e1);
        }

        for (int run = 0; run < 5; run++) {
            switch (run) {
                case 1:
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    for (int compt = 0; compt < 1500; compt++) {
                        testOut.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt + 1)));
                    }
                    break;
                case 2:
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    for (int compt = 1500; compt < 3000; compt++) {
                        run2.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 1500)));
                    }
                    break;


                case 3:
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    for (int compt = 3000; compt < 4500; compt++) {
                        run3.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 3000)));
                    }
                    break;


                case 4:
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    for (int compt = 4500; compt < 6000; compt++) {
                        run4.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt + 1 - 4500)));
                    }
                    break;


            }

        }

        testOut.close();
        run2.close();
        run3.close();
        run4.close();
        run5.close();


        PrintWriter out;
        PrintWriter out2;
        PrintWriter out3;
        PrintWriter out4;
        PrintWriter out5;

        int co = 1500;
        int co1 = 1500;
        int co2 = 1500;
        int co3 = 1500;
        int co4 = 1500;



        /*
        try {
            out = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_01_01_LTN_articles.txt"))));

            out2 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_01_02_LTN_articles.txt"))));

            out3 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_01_03_LTN_articles.txt"))));

            out4 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_01_04_LTN_articles.txt"))));

            out5 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_01_05_LTN_articles.txt"))));

           // System.out.println("Apres le tri");
            compteur = 0;

            for (Map.Entry<String, Double> entry : mapTriee.entrySet()) {
                if (compteur < mapTriee.size() - 4 * 1500 && compteur >= mapTriee.size() - 5 * 1500) {
                    //bw5.write(entry.getKey()+""+"\n");

                    out5.println(entry.getKey().replaceAll("<>", String.valueOf(co)));
                    co--;
                    //runs 5
                } else if (compteur < mapTriee.size() - 3 * 1500 && compteur >= mapTriee.size() - 4 * 1500) {
                    //runs 4
                    //bw4.write(entry.getKey()+""+"\n");
                    out4.println(entry.getKey().replaceAll("<>", String.valueOf(co1)));
                    co1--;
                } else if (compteur < mapTriee.size() - 2 * 1500 && compteur >= mapTriee.size() - 3 * 1500) {
                    //runs 3
                    // bw3.write(entry.getKey()+""+"\n");
                    out3.println(entry.getKey().replaceAll("<>", String.valueOf(co2)));
                    co2--;
                } else if (compteur < mapTriee.size() - 1500 && compteur >= mapTriee.size() - 2 * 1500) {
                    //runs 2
                    //bw2.write(entry.getKey()+""+"\n");
                    out2.println(entry.getKey().replaceAll("<>", String.valueOf(co3)));
                    co3--;
                } else if (compteur >= mapTriee.size() - 1500) {
                    //runs 1
                    // bw1.write(entry.getKey()+""+"\n");
                    //System.out.println(entry.getKey());
                    out.println(entry.getKey().replaceAll("<>", String.valueOf(co4)));
                    co4--;
                }

                compteur++;
            }


            out.close();
            out2.close();
            out3.close();
            out5.close();
            out4.close();
        } catch (Exception e1) {
            e1.printStackTrace();
            System.out.println(e1);
        }

        */

        TreeMap<String, Double> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(mapTriee);

    }
}