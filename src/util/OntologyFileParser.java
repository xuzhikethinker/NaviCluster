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
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @author knacky
 */
public class OntologyFileParser {
    Map<String,Integer> wordOccurrenceMap = new HashMap<String,Integer>();
     SortedSet<Entry<String, Integer>> sortedWordOccurrenceMap = new TreeSet<Entry<String, Integer>>(new Comparator<Entry<String,Integer>>(){

        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            if (o1.getValue() < o2.getValue()){
                return 1;
            } else if (o1.getValue() >= o2.getValue()){
                return -1;
            }
            return 0;
        }

    });
    public void parseOntologyFile(File f) throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String st = "", goid = "", goname = "";
        StringTokenizer stz = null;
        wordOccurrenceMap.clear();
        while ((st = br.readLine()) != null) {
            stz = new StringTokenizer(st, "\t", true);
            assert (stz.hasMoreTokens());
            goid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            goname = stz.nextToken();
            if (stz.nextToken().equals("\t"));

//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
            

            StringTokenizer stok = new StringTokenizer(goname);
            while (stok.hasMoreTokens()){
                String word = stok.nextToken();
                if (!wordOccurrenceMap.containsKey(word))
                    wordOccurrenceMap.put(word, 1);
                else
                    wordOccurrenceMap.put(word,wordOccurrenceMap.get(word)+1);
            }

        }
        sortedWordOccurrenceMap.clear();
        for (Entry e : wordOccurrenceMap.entrySet()){
            sortedWordOccurrenceMap.add(e);
        }
//        sortedWordOccurrenceMap.addAll(wordOccurrenceMap);
        int i = 0;
        for (Entry e : sortedWordOccurrenceMap){
            if (i >= 100)
            System.out.println(e.getKey()+" ("+e.getValue()+") --> ");
            i++;
            if (i == 200)
                break;
        }
    }
    public void parseTmpFreqWordsFile(File f) throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String st = "",full = "", abbr = "";
        StringTokenizer stz = null;
        PrintWriter pw = new PrintWriter(new File("src/datasets/freq-words-dict.txt"));
        while ((st = br.readLine()) != null) {
//            stz = new StringTokenizer(st, "-->");
            String[] phases = st.split("-->");
            full = phases[0].trim();
            int leftParen = full.lastIndexOf("(");
            full = full.substring(0,leftParen);
//            assert (stz.hasMoreTokens());
//            full = stz.nextToken().trim();
//            System.out.println("full "+full);
//            StringTokenizer stz2 = new StringTokenizer(full," ");
//            full = full.split("\\s")[0];
//            System.out.println("full "+full);

//            full = stz2.nextToken();
//            if (stz.nextToken().equals("\t"));

            if (phases.length > 1){
                abbr = phases[1].trim();
                if (abbr.toLowerCase().contains("delete"))
                    abbr = "";
            } else
                abbr = full;
            
//            if (stz.hasMoreTokens()){
//                abbr = stz.nextToken().trim();
//
////                if (abbr.trim();
//                if (abbr.toLowerCase().contains("delete"))
//                    abbr = "";
//            }
//            else
//                abbr = full;
//            if (stz.nextToken().equals("\t"));

//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
            System.out.println(full+"\t"+abbr);
            pw.println(full+"\t"+abbr);

//            StringTokenizer stok = new StringTokenizer(goname);
//            while (stok.hasMoreTokens()){
//                String word = stok.nextToken();
//                if (!wordOccurrenceMap.containsKey(word))
//                    wordOccurrenceMap.put(word, 1);
//                else
//                    wordOccurrenceMap.put(word,wordOccurrenceMap.get(word)+1);
//            }

        }
        System.out.println("endoplasmic reticulum\tER");
        System.out.println("mitochondria\tmito.");
        System.out.println("mitochondrion\tmito.");
        System.out.println("dependent\tdepend.");
        pw.println("endoplasmic reticulum\tER");
        pw.println("mitochondria\tmito.");
        pw.println("mitochondrion\tmito.");
        pw.println("dependent\tdepend");
        pw.close();
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        OntologyFileParser ofp = new OntologyFileParser();
//        StopWatch sw = new StopWatch();
////        sgdhd.parseInteractionTab(new File("..\\BlondelAlgorithm\\src\\interaction_data.tab"));
////        sgdhd.parseInteractionTab(new File("../BlondelAlgorithm/src/interaction_data.tab"));
//        sw.start();
////        sifcon.parseSIF(new File("src/datasets/Ito_2001.sif"));
////        ofp.parseOntologyFile(new File("src/datasets/ontology_file-100329.txt"));
//        ofp.parseTmpFreqWordsFile(new File("src/datasets/tmp-freq-words.txt"));
//        sw.stop();
//        System.out.println("stop watch " + sw);
////        String st = "ddxe";
////        StringBuilder stb = new StringBuilder(st);
////        System.out.println(stb.toString());
////        stb.append("appended");
////        System.out.println(stb.toString());
////        stb.insert(3, "inserted");
////        System.out.println(stb.toString());

//        String word = "eee-,//,eee-xxx/-xxx";
//        String word = "eee-//ee-xx,";
//        String word = ", dddd-ddd ssxx//eee/-xx xx-";
//        String word = " ";
//        String word = "-dd//eee-,d";
//        String[] stemStrArr;
//        String newWord = "";
//        StringBuilder newWordBuilder = new StringBuilder();
//
//        stemStrArr = word.split(",");
//        newWordBuilder = new StringBuilder();
////        if (stemStrArr.length > 1) {
//        int index = 0;
//        int accumLen = 0;
//        for (String stem : stemStrArr) {
////            String entry = freqWordsDict.get(stem);
//            String entry = stem;
//            System.out.println("entry "+entry);
////            if (entry == null) {
////                entry = stem;
////            }
//            if (!"".equals(entry) && !" ".equals(entry)) {
//                if ((index > 0)) {// && ((!"".equals(arrOfSt.get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
////                                    arrOfSt.add(ind + 1 + index - 1, "-");
//                    if ((accumLen > 0)) {//&& (newWordBuilder.length() > 0) &&
////                            ((newWordBuilder.charAt(newWordBuilder.length()-1) != '/') && (entry.charAt(0) != '/') &&
////                            (newWordBuilder.charAt(newWordBuilder.length()-1) != '-') && (entry.charAt(0) != '-') &&
////                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ':') && (entry.charAt(0) != ':')
////                            )){
////                        if (stem.contains(" ") || (stem.contains("\n")) || (stem.contains("\t")))
////                            newWordBuilder.append(", ");
////                        else
//                            newWordBuilder.append(",");
//                    }
////                    index++;
//                }
////                                arrOfSt.add(ind + 1 + index, entry);
//                newWordBuilder.append(entry);
//                accumLen += entry.length();
////                index++;
//
//            }
//                index++;
//        }
//        if ((stemStrArr.length == 1) && (word.length() > 0) && (word.charAt(word.length()-1) == ',')){
//            newWordBuilder.append(",");
//        }
//
//        System.out.println("stb "+newWordBuilder.toString());
//        newWord = newWordBuilder.toString();
//         stemStrArr = newWord.split("/");
//         newWordBuilder = new StringBuilder();
//
//
////        if (stemStrArr.length > 1) {
//             accumLen = 0;
//            index = 0;
//            for (String stem : stemStrArr) {
////                String entry = freqWordsDict.get(stem);
//                String entry = stem;
//                System.out.println("entry "+entry);
////                if (entry == null) {
////                    entry = stem;
////                }
//                if (!"".equals(entry) && !" ".equals(entry)) {
//                    if ((index > 0)) {// && ((!"".equals(newWordBuilder.indexOf("")get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
////                                    arrOfSt.add(ind + 1 + index - 1, "/");
//                        if ((accumLen > 0)) {
////                                &&
////                            ((newWordBuilder.charAt(newWordBuilder.length()-1) != ':') && (entry.charAt(0) != ':') &&
////                            (newWordBuilder.charAt(newWordBuilder.length()-1) != '-') && (entry.charAt(0) != '-') &&
////                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ',') && (entry.charAt(0) != ',')
////                            )){
//                            newWordBuilder.append("/");
//                        }
////                                newWordBuilder.insert(accumLen, "/");
////                                index++;
//                    }
////                                arrOfSt.add(ind + 1 + index, entry);
//                    newWordBuilder.append(entry);
////                                newWordBuilder.
//                    accumLen += entry.length();
//                }
//                index++;
//            }
////        }
////        else {
//////            newWordBuilder = new StringBuilder(word);
////
////        }
//        System.out.println("stb "+newWordBuilder.toString());
//        newWord = newWordBuilder.toString();
//        //split by - and look up
//        stemStrArr = newWord.split("-");
//        newWordBuilder = new StringBuilder();
////        if (stemStrArr.length > 1) {
//        index = 0;
//        accumLen = 0;
//        for (String stem : stemStrArr) {
////            String entry = freqWordsDict.get(stem);
//            String entry = stem;
//                System.out.println("entry "+entry);
////            if (entry == null) {
////                entry = stem;
////            }
//            if (!"".equals(entry) && !" ".equals(entry)) {
//                if ((index > 0)) {// && ((!"".equals(arrOfSt.get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
////                                    arrOfSt.add(ind + 1 + index - 1, "-");
//                    if ((accumLen > 0) && (newWordBuilder.length() > 0) &&
//                            ((newWordBuilder.charAt(newWordBuilder.length()-1) != '/') && (entry.charAt(0) != '/') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ':') && (entry.charAt(0) != ':') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ',') && (entry.charAt(0) != ',')
//                            )){
//                    newWordBuilder.append("-");
//                    }
////                    index++;
//                }
////                                arrOfSt.add(ind + 1 + index, entry);
//                newWordBuilder.append(entry);
//                accumLen += entry.length();
////                index++;
//
//            }
//                index++;
//        }
////        } else {
////            newWordBuilder = new StringBuilder(newWord);
////        }
//        System.out.println("stb "+newWordBuilder.toString());
//        newWord = newWordBuilder.toString();
//        //split by : and look up
//        stemStrArr = newWord.split(":");
//        newWordBuilder = new StringBuilder();
////        if (stemStrArr.length > 1) {
//        index = 0;
//        accumLen = 0;
//        for (String stem : stemStrArr) {
////            String entry = freqWordsDict.get(stem);
//            String entry = stem.trim();
//                System.out.println("entry "+entry);
////            if (entry == null) {
////                entry = stem;
////            }
//            if (!"".equals(entry) && !" ".equals(entry)) {
//                if ((index > 0)) {// && ((!"".equals(arrOfSt.get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
////                                    arrOfSt.add(ind + 1 + index - 1, "-");
//                    if ((accumLen > 0) && (newWordBuilder.length() > 0) &&
//                            ((newWordBuilder.charAt(newWordBuilder.length()-1) != '/') && (entry.charAt(0) != '/') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != '-') && (entry.charAt(0) != '-') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ',') && (entry.charAt(0) != ',')
//                            )){
//                    newWordBuilder.append(":");
//                    }
////                    index++;
//                }
////                                arrOfSt.add(ind + 1 + index, entry);
//                newWordBuilder.append(entry);
//                accumLen += entry.length();
////                index++;
//
//            }
//                index++;
//        }
////        } else {
////            newWordBuilder = new StringBuilder(newWord);
////        }
//        System.out.println("stb "+newWordBuilder.toString());
//        newWord = newWordBuilder.toString();

        String st = "dd-ddd:eee/KK/LL,";
        StringTokenizer stk = new StringTokenizer(st,":/-,",true);
        while (stk.hasMoreTokens()){
            System.out.println(stk.nextToken());
        }
        String[] punctuation = {"/",":"};
        ArrayList<String> arrPunc = new ArrayList<String>(Arrays.asList(punctuation));
        System.out.println(arrPunc.contains(":"));

        
    }
}
