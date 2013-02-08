/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *
 * @author Knacky
 */
public class CheckReplication {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filename = "src/all_yeast_intdata.txt";
        File f = new File(filename);
//        System.out.println(f.getAbsolutePath());
//        ArrayList <String> listEdge = new ArrayList();
        ArrayList<String>listEdge[] = new ArrayList[90000];
        for (int i = 0; i < listEdge.length; i++)
            listEdge[i] = new ArrayList<String>();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String st;
        int numDup = 0;
        int index = 0;
        while ((st = br.readLine()) != null){
            if ((index%10000)== 0) 
                System.out.print(".");
            StringTokenizer stn = new StringTokenizer(st);
            String src = stn.nextToken();
            String dest = stn.nextToken();
//            System.out.println(src);
            String weight = "";
            if (stn.hasMoreTokens())
                weight = stn.nextToken();
            if (!listEdge[Integer.parseInt(src)].contains(dest))
                listEdge[Integer.parseInt(src)].add(dest);
            else
            {
                numDup++;
                System.out.println(src+" "+dest+" "+weight);
            }
//            if (listEdge[Integer.parseInt(dest)].contains(src))
//            {
//                numDup++;
//                System.out.println(src+" "+dest+" "+weight);
//            }
            index++;
        }
        System.out.println(numDup);
    }
}
