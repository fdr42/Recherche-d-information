/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri.Models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fdr
 */
public class Document {
    public String id;
    public Map<String,Integer> index;
    public Document(String id){
      this.id=id;
    this.index=new HashMap<>();
    }
    public int getTf(String word){
        if(index.get(word)!=null){
        return index.get(word);
        }
        return 0;
        
    }
}
