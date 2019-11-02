
import java.util.ArrayList;
import java.util.List;
import com.mycompany.ri.Indexation;
import com.mycompany.ri.Models.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fdr
 */
public class Main {
   public static void main(String[] args) throws FileNotFoundException{


       System.out.println("Test cosine similarity <<<<<>>>>>");
       // Document testd = null;
      // double test = Document.cosineSimilarity();

      // System.out.println(test);








       List<String> stopWords= new ArrayList<>();
       File file = new File("C:\\Users\\madji\\IdeaProjects\\Recherche-d-information\\RI\\src\\main\\java\\stopWords");
       Scanner input = new Scanner(file);
       input.useDelimiter(" +|\\n|\\r"); //delimitor is one or more spaces

        while (input.hasNext()) {
            stopWords.add(input.next());
        }
      
       try {
           System.out.println(stopWords);
           Indexation.indexFile("C:\\Users\\madji\\IdeaProjects\\Recherche-d-information\\RI\\src\\main\\java\\file",stopWords);
       } catch (FileNotFoundException ex) {
           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
       }




        
    }
    
}
