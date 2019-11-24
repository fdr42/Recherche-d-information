/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri.Models;

import javax.print.Doc;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoubleConsumer;

/**
 * @author fdr
 */
public class Document {
    public String id;
    public Map<String, Integer> index;

    public Document(String id) {
        this.id = id;
        this.index = new HashMap<>();
    }

    public int getTf(String word) {
        if (index.get(word) != null) {
            return index.get(word);
        }
        return 0;
    }

    /**
     * @param word
     * @return TF of the word
     */
    public double getTermFrequecy(String word) {
        if (index.get(word) != null) {
            double i = index.get(word);
            double taille = index.size();
            return i / taille;
        }
        return 0;
    }


    /**
     * IDF(t) = log(N/df(t))   ---> cf cours
     *
     * @param word
     * @return IDF(word)
     */
    public double getIDF(String word) {
        if (index.get(word) != null) {
            double df = getTermFrequecy(word);
            double taille = index.size();
            return Math.log(taille / df);
        }
        return 0;
    }

    /**
     *
     * @return le plus grand tf (j'espere)
     */
    public double maxTF(){
        double max = 0;
        double temp = 0;
        for (Map.Entry<String, Integer> entry : index.entrySet()){
            temp = this.getTf(entry.getKey());
            if(temp > max){
                max = temp;
            }
        }

        return max;
    }

    /**
     *
     * @param doc
     * @param queryList
     * @return le score nnn
     */
    public static double nnn(Document doc, String[] queryList){
        Double score = 0.0;

        for(int i=0; i<queryList.length;i++){
            score += doc.getTermFrequecy(queryList[i]);
        }
        return score;
    }


    /**
     * Calcul le score OKAPI BM-25
     * @param doc
     * @param queryList
     * @return score BM25
     */
    public static double okapi(Document doc, String[] queryList, List<Document> listeDoc){
        double score = 0;

        double k1 = 1.5;
        double b=0.75;
        double tailleD = doc.index.size();

        //attention ici c'est la taille moyenne de la collection d'ou est extrait

        double avgD = 0;


        for (Document document : listeDoc) {
            avgD+=document.index.size();
        }
        avgD = avgD/listeDoc.size();


        for(int i=0; i<queryList.length; i++){
            score += doc.getIDF(queryList[i]) * ( ( doc.getTermFrequecy(queryList[i]) * (k1 +1) )/ ( doc.getTermFrequecy(queryList[i]) + k1  * (1 - b + b * (tailleD/avgD))));

        }


        return  score;

    }



    /**
     * ( 0.5 + 0.5 * (tf(ij)) / max(tf(i)) ) * idf(j)
     * @param doc
     * @param queryList
     * @return le score ATN
     */
    public static double atn(Document doc, String[] queryList){
        Double score = 0.0;

        for(int i=0; i<queryList.length; i++){
            score += (0.5 + 0.5 * (doc.getTf(queryList[i])/doc.maxTF())) * doc.getIDF(queryList[i]);
        }

        return score;

    }

    /**
     * @return la cosine siilarity (apparamment c'est LTN mais palus vraiment sur de ça ...peut etre LTC)
     */
    public static double cosineSimilarity(Document doc, String[] queryList) throws FileNotFoundException {
        double cosineSimilarity = 0;

        //  Document doc;
        //on creer le document correspondant a la requete
        Document queryDoc = new Document("stop");
        String query = "olive oil health benefit";
        //   String[] queryList = query.split(" ");

        List<String> stopWords = new ArrayList<>();
        //File file = new File("./stopWords");
        File file = new File("C:\\Users\\madji\\IdeaProjects\\Recherche-d-information\\RI\\src\\main\\java\\com\\mycompany\\ri\\stopWords");
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r"); //delimitor is one or more spaces

        while (input.hasNext()) {
            stopWords.add(input.next());
        }

        for (int i = 0; i < queryList.length; i++) {
            if (!stopWords.contains(queryList[i]) && queryList[i].length() > 1) {//Ajout des mots a l'index
                queryDoc.index.put(queryList[i], queryDoc.getTf(queryList[i]) + 1);
            }
        }


        //test
        // doc=queryDoc;

        //on verifie si le mot de la requete est present dans le document
        double dotProduct = 0;
        for (Map.Entry<String, Integer> entry : queryDoc.index.entrySet()) {
            if (doc.index.containsKey(entry.getKey())) {
                // System.out.println(">>> ce mot est partout :  "+entry.getKey());
                // le mot de la requete existe dans le document, le dot product ne sera donc pas nul
                double d1 = doc.getIDF(entry.getKey()) * doc.getTermFrequecy(entry.getKey());
                double d2 = queryDoc.getIDF(entry.getKey()) * queryDoc.getTermFrequecy(entry.getKey());
                dotProduct += d1 * d2;
            } else {
                // la clé n'est pas présente, donc le dot product vaudra 0, pas besoin d'y calculer
            }
        }


        double normeQuery = norme(queryDoc);
        double normeDoc = norme(doc);

        cosineSimilarity = dotProduct / (normeDoc * normeQuery);


        return cosineSimilarity;
    }


    /**
     * Méthode pour calculer la norme d'un doc
     *
     * @param doc
     * @return norme = ||d|| = RacineCarree(d[0]² + d[1]² + ... + d[n]²)
     */
    public static double norme(Document doc) {
        double norme = 0;
        for (Map.Entry<String, Integer> entry : doc.index.entrySet()) {
            norme += (doc.getIDF(entry.getKey()) * doc.getTermFrequecy(entry.getKey()))
                    * (doc.getIDF(entry.getKey()) * doc.getTermFrequecy(entry.getKey()));
        }
        return Math.sqrt(norme);
    }
}
