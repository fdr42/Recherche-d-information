/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri;

import com.mycompany.ri.Models.Document;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author fdr
 */
public class Indexation {

    public static void indexFile(String path, List<String> stopWords) throws FileNotFoundException {
        List<Document> listeDoc = new ArrayList<>();
        Document currentDoc = null;
        File file = new File("C:\\Users\\madji\\IdeaProjects\\Recherche-d-information\\RI\\src\\main\\java\\file");
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r|\'");

        int i = 0;
        while (input.hasNext()) {
            String string = input.next().toLowerCase();
            if (string.contains("<doc><docno>")) {//Nouveau document
                string = string.replace("<doc><docno>", "");
                string = string.replace("</docno>", "");
                currentDoc = new Document(string);//On crée le document avec son ID
            } else if (string.equals("</doc>")) {//Fin de doc: on l'ajoute a la liste
                listeDoc.add(currentDoc);
                //  System.out.println("NOUVEAU DOC / " + currentDoc.id + " numero :  "+i+" taille :  "+currentDoc.index.size());
                i++;
                // System.out.println(currentDoc.index);
                // for (Map.Entry<String, Integer> entry : currentDoc.index.entrySet()) {
                //     System.out.println(">>>"+entry.getKey() + "/" + currentDoc.getTermFrequecy(entry.getKey()));
                // }

            } else if (!stopWords.contains(string) && string.length() > 1) {//Ajout des mots a l'index
                currentDoc.index.put(string, currentDoc.getTf(string) + 1);
            }
        }

        System.out.println("TAILLE LISTE DOC >>> " + listeDoc.size());
        //on va construire le run :


        List<String[]> listeQ = new ArrayList<>();
        String query = "olive oil health benefit";
        String[] queryList = query.split(" ");
        listeQ.add(queryList);

        String query1 = "notting hill film actors";
        String[] queryList1 = query1.split(" ");
        listeQ.add(queryList1);

        String query2 = "probabilistic models in information retrieval";
        String[] queryList2 = query2.split(" ");
        listeQ.add(queryList2);

        String query3 = "web link network analysis";
        String[] queryList3 = query3.split(" ");
        listeQ.add(queryList3);

        String query4 = "web ranking scoring algorithm";
        String[] queryList4 = query4.split(" ");
        listeQ.add(queryList4);

        String query5 = "supervised machine learning algorithm";
        String[] queryList5 = query5.split(" ");
        listeQ.add(queryList5);

        String query6 = "operating system +mutual +exclusion";
        String[] queryList6 = query6.split(" ");
        listeQ.add(queryList6);


        List<String> listeNumQ = new ArrayList<>();
        listeNumQ.add("2009011");
        listeNumQ.add("2009036");
        listeNumQ.add("2009067");
        listeNumQ.add("2009073");
        listeNumQ.add("2009074");
        listeNumQ.add("2009078");
        listeNumQ.add("2009085");


        Map<String, Double> mapFinale = new HashMap<String, Double>();


        String tag = "<>";
        int compteur = 0;
        int compteurNonNul = 0;
        //pour chaque doc
        for (int q = 0; q < listeQ.size(); q++) {
            for (int compteurDoc = 0; compteurDoc < listeDoc.size(); compteurDoc++) {
                //pour chaque query
                compteur++;
                double cosSim = Document.cosineSimilarity(listeDoc.get(compteurDoc), listeQ.get(q));
                if (cosSim != 0.0) {
                    compteurNonNul++;
                }
                mapFinale.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " TEAM /article[1]", cosSim);
                System.out.println(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " TEAM /article[1]");
            }
        }

        Map<String, Double> mapTriee = new HashMap<String, Double>();

        mapTriee =  MapUtil.sortByValue(mapFinale);
        PrintWriter out = null;
        PrintWriter out2 = null;
        PrintWriter out3 = null;
        PrintWriter out4 = null;
        PrintWriter out5 = null;

        int co=1500;
        int co1=1500;
        int co2=1500;
        int co3=1500;
        int co4=1500;


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

            /*
            FileWriter runs5 = new FileWriter("MadjidPierreBunyaminFrançois_01_05_LTN_articles.txt");
            BufferedWriter bw5 = new BufferedWriter(runs5);

            FileWriter runs4 = new FileWriter("MadjidPierreBunyaminFrançois_01_04_LTN_articles.txt");
            BufferedWriter bw4 = new BufferedWriter(runs4);

            FileWriter runs3 = new FileWriter("MadjidPierreBunyaminFrançois_01_03_LTN_articles.txt");
            BufferedWriter bw3 = new BufferedWriter(runs3);

            FileWriter runs2 = new FileWriter("MadjidPierreBunyaminFrançois_01_02_LTN_articles.txt");
            BufferedWriter bw2 = new BufferedWriter(runs2);

            FileWriter runs1 = new FileWriter("MadjidPierreBunyaminFrançois_01_01_LTN_articles.txt");
            BufferedWriter bw1 = new BufferedWriter(runs1);
            */

        System.out.println("Apres le tri");
        compteur = 0;

        for (Map.Entry<String, Double> entry : mapTriee.entrySet()) {
            if (compteur < mapTriee.size() - 4*1500 && compteur >= mapTriee.size() - 5*1500) {
                //bw5.write(entry.getKey()+""+"\n");

                out5.println(entry.getKey().replaceAll("<>", String.valueOf(co)));
                co--;
                //runs 5
            }else if(compteur < mapTriee.size() - 3*1500 && compteur >= mapTriee.size() - 4*1500) {
                //runs 4
                //bw4.write(entry.getKey()+""+"\n");
                out4.println(entry.getKey().replaceAll("<>", String.valueOf(co1)));
                co1--;
            }else if(compteur < mapTriee.size() - 2*1500 && compteur >= mapTriee.size() - 3*1500) {
                //runs 3
               // bw3.write(entry.getKey()+""+"\n");
                out3.println(entry.getKey().replaceAll("<>", String.valueOf(co2)));
                co2--;
            }else if(compteur < mapTriee.size() - 1500 && compteur >= mapTriee.size() - 2*1500) {
                //runs 2
                //bw2.write(entry.getKey()+""+"\n");
                out2.println(entry.getKey().replaceAll("<>", String.valueOf(co3)));
                co3--;
            }else if(compteur >= mapTriee.size() - 1500){
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

        /*

        68000

        66500

        65000



         */

        TreeMap<String, Double> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(mapTriee);



        /*
        System.out.println("renversement");
        compteur=0;
        Iterator it = MapUtil.valueIterator(treeMap);
        while(it.hasNext()) {
            System.out.println(it.next());
            compteur++;
        }
*/


        /*

        Il faut donc ordonner toutes ces ligne selon ordre decroissant du score de cos similarite
        et mettre dans 5 fichier different (1500 lignes par fichiers)

        a confirmer je sais pas si c'est juste

        cordialement


         */
    }
}