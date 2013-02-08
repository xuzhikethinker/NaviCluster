/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
*
* @author user
*/
public class GMLParser {
  public static void main(String[] args) throws FileNotFoundException, IOException {
      File f = new File("src/karate.gml");
      FileInputStream fis = new FileInputStream(f);
      BufferedReader br = new BufferedReader(new InputStreamReader(fis));
      PrintWriter pw = new PrintWriter("src/karate.net");
      String st,out = "";
      pw.print("*Vertices\t");
      //Vertices
      int numnode = 0;
      while ((st = br.readLine())!= null){
          if (!(st.contains("Graph") || st.contains("graph")))
              continue;
          else break;
      }
      while ((st = br.readLine())!= null){
          if (st.length() == 0) continue;
          StringTokenizer stn = new StringTokenizer(st);        
          String s = stn.nextToken();
          if (s.contains("node"))
          {
              st = br.readLine();
              st = br.readLine();
              stn = new StringTokenizer(st);
              stn.nextToken();
              out += stn.nextToken()+"\n";
              st = br.readLine();
              numnode++;
          }
          else if (s.contains("edge"))
              break;
      }
      pw.println(numnode);
      pw.print(out);
      
      //Edges
      pw.println("*Edges");
      
      int numedge = 0;
      while ((st = br.readLine())!= null){
          if (st.length() == 0) continue;
          StringTokenizer stn = new StringTokenizer(st);        
          String s = stn.nextToken();
          if (s.contains("source")){
              numedge++;
              pw.print(stn.nextToken()+" ");
          } else if (s.contains("target")){
              pw.println(stn.nextToken()+" 1");
          }
          

      }
//      System.out.println(numedge);
      br.close();
      pw.close();
  }
}
