package com.mycompany.ri.Models;

import com.mycompany.ri.PorterStemmer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
    public Map<String, Integer> index;
    public String id;

    public Query(String query, List<Document> listDoc, String id) {
        index = new HashMap<>();
        this.id = id;
        String[] queryList = query.split(" ");
        for (String s : queryList) {
            s = s.replace("+", "");
            s = PorterStemmer.stemWord(s);
            int nbDocPertin = 0;
            for (Document doc : listDoc) {
                if (doc.index.get(s) != null)
                    nbDocPertin++;
            }
            index.put(s, nbDocPertin);
        }
    }

    public int getDf(String word) {
        if (index.get(word) != null) {
            return index.get(word);
        }
        return 0;
    }

    public double getIDf(int nbDoc, String word) {
        return Math.log(nbDoc / (getDf(word) + 1));

    }

    public double getIDfBM25(int nbDoc, String word) {
        return Math.log((nbDoc - getDf(word) + 0.5) / (getDf(word) + 0.5));

    }
}
