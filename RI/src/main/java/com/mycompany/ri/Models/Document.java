/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri.Models;

import com.mycompany.ri.Main;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fdr
 */
public class Document {
    public String id;
    public Map<String, Double> index;
    public double totalWords;

    public Document(String id) {
        this.id = id;
        this.index = new HashMap<>();
        this.totalWords = 0;
    }

    public double getTf(String word) {
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
            double result=1+Math.log10(i / this.totalWords);
                return i / this.totalWords;
        }
        return 0;
    }


    /**
     * @return le plus grand tf (j'espere)
     */
    public double maxTF() {
        double max = 0;
        double temp = 0;
        for (Map.Entry<String, Double> entry : index.entrySet()) {
            temp = this.getTf(entry.getKey());
            if (temp > max) {
                max = temp;
            }
        }

        return max;
    }

    /**
     * @param doc
     * @param queryList
     * @return le score nnn
     */
    public static double nnn(Document doc, String[] queryList) {
        Double score = 0.0;

        for (int i = 0; i < queryList.length; i++) {
            score += doc.getTermFrequecy(queryList[i]);
        }
        return score;
    }


    /**
     * Calcul le score OKAPI BM-25
     *
     * @return score BM25
     */
    public double okapi(Query query, List<Document> listeDoc) {
        double score = 0;

        double k1 = Main.k1;
        double b = Main.b;
        double tailleD = this.totalWords;

        //attention ici c'est la taille moyenne de la collection d'ou est extrait

        double avgD = 0;


        for (Document document : listeDoc) {
            avgD += document.totalWords;
        }
        avgD = avgD / listeDoc.size();

        for (Map.Entry<String, Double> entry : query.index.entrySet()) {
            score += ((this.getTf(entry.getKey()) * (k1 + 1)) / (this.getTf(entry.getKey()) + k1 * ((1 - b) + b * (tailleD / avgD)))) * query.getIDfBM25(listeDoc.size(), entry.getKey());
        }


        return score;

    }


    /**
     * ( 0.5 + 0.5 * (tf(ij)) / max(tf(i)) ) * idf(j)
     *
     * @param doc
     * @return le score ATN
     */
    public static double atn(Document doc, Query query, List<Document> listeDoc) {
        Double score = 0.0;

        for (Map.Entry<String, Double> entry : query.index.entrySet()) {
            score += (0.5 + 0.5 * (doc.getTf(entry.getKey()) / doc.maxTF())) * query.getIDf(listeDoc.size(), entry.getKey());

        }

        return score;

    }

    public double LTN(Query query, List<Document> listDoc) {
        double score = 0;
        Query test=query;
        for (Map.Entry<String, Double> entry : query.index.entrySet()) {

            score += (1+Math.log10(this.getTf(entry.getKey()))) * query.getIDf(listDoc.size(),entry.getKey());
        }
        return score;

    }

    /**
     * @return la cosine siilarity (apparamment c'est LTN mais palus vraiment sur de ça ...peut etre LTC)
     */
    public static double cosineSimilarity(Document doc, Query query, List<Document> listeDoc) throws FileNotFoundException {
        double cosineSimilarity = 0;

        //  Document doc;
        //on creer le document correspondant a la requete
        //   String[] queryList = query.split(" ");


        //test
        // doc=queryDoc;


        //on verifie si le mot de la requete est present dans le document
        double dotProduct = 0;
      /*  for (Map.Entry<String, Integer> entry : query.index.entrySet()) {
            if (doc.index.containsKey(entry.getKey())) {
                // System.out.println(">>> ce mot est partout :  "+entry.getKey());
                // le mot de la requete existe dans le document, le dot product ne sera donc pas nul
                double d1 = doc.getIDF(listeDoc.size(),entry.getKey()) * doc.getTermFrequecy(entry.getKey());
                double d2 = query.getIDF(listeDoc.size(),entry.getKey()) * doc.getTermFrequecy(entry.getKey());
                dotProduct += d1 * d2;
            } else {
                // la clé n'est pas présente, donc le dot product vaudra 0, pas besoin d'y calculer
            }
        }


        double normeQuery = norme(queryDoc,listeDoc);
        double normeDoc = norme(doc,listeDoc);

        cosineSimilarity = dotProduct / (normeDoc * normeQuery);

*/
        return cosineSimilarity;
    }


    /**
     * Méthode pour calculer la norme d'un doc
     *
     * @param doc
     * @return norme = ||d|| = RacineCarree(d[0]² + d[1]² + ... + d[n]²)
     */
    public static double norme(Document doc, List<Document> listeDoc) {
        double norme = 0;
     /*   for (Map.Entry<String, Integer> entry : doc.index.entrySet()) {
            norme += (doc.getIDF(entry.getKey(),listeDoc) * doc.getTermFrequecy(entry.getKey()))
                    * (doc.getIDF(entry.getKey(),listeDoc) * doc.getTermFrequecy(entry.getKey()));
        }*/
        return Math.sqrt(norme);
    }
}
