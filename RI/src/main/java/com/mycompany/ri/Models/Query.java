package com.mycompany.ri.Models;

import com.mycompany.ri.PorterStemmer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
    public Map<String, Double> index;
    public String id;

    public Query(String query, List<Document> listDoc, String id) {
        index = new HashMap<>();
        this.id = id;
        String[] queryList = query.split(" ");
        for (String s : queryList) {
            s = s.replace("+", "");
            s = PorterStemmer.stemWord(s);
            double nbDocPertin = 0;
            for (Document doc : listDoc) {
                if (doc.index.get(s) != null)
                    nbDocPertin++;
            }
            index.put(s, nbDocPertin);
        }
    }

    public double getDf(String word) {
        if (index.get(word) != null) {
            double test=index.get(word);
            return index.get(word);
        }
        return 0;
    }

    public double getIDf(double nbDoc, String word) {
     //   System.out.println(nbDoc +" bbb ");

        double testt=nbDoc / (getDf(word) );
        double d=Math.log10(nbDoc / (getDf(word) ));
        return Math.log10(nbDoc / (getDf(word)));

    }

    public double getIDfBM25(double nbDoc, String word) {
       // System.out.print(nbDoc- getDf(word) +" bbb ");
        //System.out.print(getDf(word) + 1+" totaaaall");
        //System.out.println(Math.log((nbDoc - getDf(word) + 0.5) / (getDf(word) + 0.5)));

        return Math.log10((nbDoc - getDf(word) + 0.5) / (getDf(word) + 0.5));

    }
}
