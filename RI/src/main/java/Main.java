
import java.util.ArrayList;
import java.util.List;
import com.mycompany.ri.Indexation;
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
       List<String> stopWords= new ArrayList<>();
         File file = new File("./stopWords");
        Scanner input = new Scanner(file);
        input.useDelimiter(" +|\\n|\\r"); //delimitor is one or more spaces

        while (input.hasNext()) {
            stopWords.add(input.next());
        }
      
       try {
           System.out.println(stopWords);
           Indexation.indexFile("./file",stopWords);
       } catch (FileNotFoundException ex) {
           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
    
}
