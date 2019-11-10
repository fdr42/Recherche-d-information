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
 * @author fdr + madjid
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

        //map pour chaque requete
        Map<String, Double> mapQuery1 = new HashMap<String, Double>();
        Map<String, Double> mapQuery2 = new HashMap<String, Double>();
        Map<String, Double> mapQuery3 = new HashMap<String, Double>();
        Map<String, Double> mapQuery4 = new HashMap<String, Double>();
        Map<String, Double> mapQuery5 = new HashMap<String, Double>();
        Map<String, Double> mapQuery6 = new HashMap<String, Double>();
        Map<String, Double> mapQuery7 = new HashMap<String, Double>();

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
                mapFinale.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                System.out.println(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]");
                switch (q) {
                    case 0:
                        mapQuery1.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 1:
                        mapQuery2.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 2:
                        mapQuery3.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 3:
                        mapQuery4.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 4:
                        mapQuery5.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 5:
                        mapQuery6.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                    case 6:
                        mapQuery7.put(listeNumQ.get(q) + " Q0 " + listeDoc.get(compteurDoc).id + " " + tag + " " + cosSim + " MadjidPierreBunyaminFrançois /article[1]", cosSim);
                        break;
                }

            }

            /*


            Mettre dans le runs par requete ici
            Trier par score
             */
        }

        Map<String, Double> mapTriee = new HashMap<String, Double>();
        Map<String, Double> mapTriee1 = new HashMap<String, Double>();
        Map<String, Double> mapTriee2 = new HashMap<String, Double>();
        Map<String, Double> mapTriee3 = new HashMap<String, Double>();
        Map<String, Double> mapTriee4 = new HashMap<String, Double>();
        Map<String, Double> mapTriee5 = new HashMap<String, Double>();
        Map<String, Double> mapTriee6 = new HashMap<String, Double>();
        Map<String, Double> mapTriee7 = new HashMap<String, Double>();


        /*

        System.out.println("===============================================");
        System.out.println("Taille mapQuery1 1 : "+mapQuery1.size());
        System.out.println("Taille mapQuery1 2 : "+mapQuery2.size());
        System.out.println("Taille mapQuery1 3 : "+mapQuery3.size());
        System.out.println("Taille mapQuery1 4 : "+mapQuery4.size());
        System.out.println("Taille mapQuery1 5 : "+mapQuery5.size());
        System.out.println("Taille mapQuery1 6 : "+mapQuery6.size());
        System.out.println("Taille mapQuery1 7 : "+mapQuery7.size());

*/
        mapTriee = MapUtil.sortByValue(mapFinale);


        mapTriee1 =  MapUtil.sortByValue(mapQuery1);
        mapTriee2 =  MapUtil.sortByValue(mapQuery2);
        mapTriee3 =  MapUtil.sortByValue(mapQuery3);
        mapTriee4 =  MapUtil.sortByValue(mapQuery4);
        mapTriee5 =  MapUtil.sortByValue(mapQuery5);
        mapTriee6 =  MapUtil.sortByValue(mapQuery6);
        mapTriee7 =  MapUtil.sortByValue(mapQuery7);


        /*



        int limit = 0;
        for (Map.Entry<String, Double> entry : mapQuery1.entrySet()) {
            if(limit<100){
                System.out.println(">>> "+entry.getKey());
                limit++;
            }
        }
        limit=0;
        System.out.println("----------------");
        for (Map.Entry<String, Double> entry : mapTriee1.entrySet()) {
            if(limit<100){
                System.out.println(">>> "+entry.getKey());
                limit++;
            }
        }

*/

        //on met les hashmap dans des arralist pour reverse facilement

        int zero = 0;
        List<String> queryListTriee1 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee1.entrySet()) {
            queryListTriee1.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }
        }

        int un = zero;

        System.out.println(queryListTriee1);
        Collections.reverse(queryListTriee1);
        System.out.println(queryListTriee1);
        System.out.println("Il y a "+zero+" zeros dans cette liste");

        zero = 0;

        List<String> queryListTriee2 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee2.entrySet()) {
            queryListTriee2.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }
        }


        int deux = zero;


        System.out.println(queryListTriee2);
        Collections.reverse(queryListTriee2);
        System.out.println(queryListTriee2);
        System.out.println("Il y a "+zero+" zeros dans cette liste");

        zero = 0;

        List<String> queryListTriee3 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee3.entrySet()) {
            queryListTriee3.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }
        }


        int trois = zero;



        System.out.println(queryListTriee3);
        Collections.reverse(queryListTriee3);
        System.out.println(queryListTriee3);
        System.out.println("Il y a "+zero+" zeros dans cette liste");



        zero = 0;

        List<String> queryListTriee4 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee4.entrySet()) {
            queryListTriee4.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }

        }



        int quatre = zero;

        System.out.println(queryListTriee4);
        Collections.reverse(queryListTriee4);
        System.out.println(queryListTriee4);
        System.out.println("Il y a "+zero+" zeros dans cette liste");




        zero = 0;

        List<String> queryListTriee5 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee5.entrySet()) {
            queryListTriee5.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }
        }

        int cinq=zero;

        System.out.println(queryListTriee5);
        Collections.reverse(queryListTriee5);
        System.out.println(queryListTriee5);
        System.out.println("Il y a "+zero+" zeros dans cette liste");



        zero = 0;

        List<String> queryListTriee6 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee6.entrySet()) {
            queryListTriee6.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }
        }


        int six = zero;

        System.out.println(queryListTriee6);
        Collections.reverse(queryListTriee6);
        System.out.println(queryListTriee6);
        System.out.println("Il y a "+zero+" zeros dans cette liste");



        zero = 0;

        List<String> queryListTriee7 = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : mapTriee7.entrySet()) {
            queryListTriee7.add(entry.getKey());
            if(entry.getValue().equals(0.0)){
                zero++;
            }
        }


        int sept = zero;


        //System.out.println(queryListTriee7);
        Collections.reverse(queryListTriee7);
        //System.out.println(queryListTriee7);
        //System.out.println("Il y a "+zero+" zeros dans cette liste");


       // System.out.println(" "+un+" - "+ deux+"  -  "+trois+"  - "+quatre+" - "+cinq+" - "+six+" - "+ sept);

        PrintWriter testOut = null;
        PrintWriter run2 = null;
        PrintWriter run3 = null;
        PrintWriter run4 = null;
        PrintWriter run5 = null;



        try{
            testOut = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_01_LTN_articles.txt"))));
            run2 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_02_LTN_articles.txt"))));
            run3 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_03_LTN_articles.txt"))));
            run4 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_04_LTN_articles.txt"))));
            run5 = new PrintWriter(new OutputStreamWriter(
                    new BufferedOutputStream(new FileOutputStream("MadjidPierreBunyaminFrançois_03_05_LTN_articles.txt"))));
        } catch (Exception e1) {
        e1.printStackTrace();
        System.out.println(e1);
        }

        for(int run=0; run<5; run++){
            switch (run){
                case 1:
                for(int compt=0;compt<1500;compt++){
                    testOut.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                for(int compt=0;compt<1500;compt++){
                     testOut.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                for(int compt=0;compt<1500;compt++){
                    testOut.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                for(int compt=0;compt<1500;compt++){
                    testOut.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                for(int compt=0;compt<1500;compt++){
                    testOut.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                for(int compt=0;compt<1500;compt++){
                    testOut.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                for(int compt=0;compt<1500;compt++){
                    testOut.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt+1)));
                }
                break;
                case 2:
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    for(int compt=1500;compt<3000;compt++){
                        run2.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt+1-1500)));
                    }
                    break;


                case 3:
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    for(int compt=3000;compt<4500;compt++){
                        run3.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt+1-3000)));
                    }
                    break;




                case 4:
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee1.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee2.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee3.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee4.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee5.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee6.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    for(int compt=4500;compt<6000;compt++){
                        run4.println(queryListTriee7.get(compt).replaceAll("<>", String.valueOf(compt+1-4500)));
                    }
                    break;


            }

        }

        testOut.close();
        run2.close();
        run3.close();
        run4.close();
        run5.close();




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

        TreeMap<String, Double> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(mapTriee);

    }
}