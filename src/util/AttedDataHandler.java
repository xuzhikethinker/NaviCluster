/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import objects.BioObject;
import objects.CoExpRelation;

/**
 *
 * @author Knacky
 */
public class AttedDataHandler {

    private static final int MAX = 100000;
//    public static Set geneSet = new HashSet();
    public static Set geneObjSet = new HashSet();
    public static Set hasAnnotSet = new HashSet();
    public static Map<String, String> systemToCanonicalNameMap = new HashMap<String, String>();
    static SortedSet<BioObject> sortedGeneSet = new TreeSet<BioObject>(new Comparator<BioObject>() {

        public int compare(BioObject v1, BioObject v2) {
            if (v1.getId() < v2.getId()) {
                return -1;
            } else if (v1.getId() == v2.getId()) {
                return 0;
            } else {
                return 1;
            }
        }
    });
    static SortedSet<CoExpRelation> sortedEdgeSet = new TreeSet<CoExpRelation>(new Comparator<CoExpRelation>() {

        public int compare(CoExpRelation e1, CoExpRelation e2) {
            int e1v1 = ((BioObject) e1.getBioObj1()).getId();
            int e1v2 = ((BioObject) e1.getBioObj2()).getId();
            int e2v1 = ((BioObject) e2.getBioObj1()).getId();
            int e2v2 = ((BioObject) e2.getBioObj2()).getId();
//                System.out.println(e1v1 + " " + e1v2);
//                System.out.println(e2v1 + " " + e2v2);

            int temp;
//                if (e1v1 > e1v2) {
//                    temp = e1v1;
//                    e1v1 = e1v2;
//                    e1v2 = temp;
//                }
//                if (e2v1 > e2v2) {
//                    temp = e2v1;
//                    e2v1 = e2v2;
//                    e2v2 = temp;
//                }
            if (e1v1 < e2v1) {
//                    System.out.println(e1v1 + " " + e1v2 + "<" + e2v1 + " " + e2v2);
                return -1;
            } else if (e1v1 > e2v1) {
//                    System.out.println(e1v1 + " " + e1v2 + ">" + e2v1 + " " + e2v2);
                return 1;
            } else if (e1v1 == e2v1) {
                if (e1v2 < e2v2) {
//                        System.out.println(e1v1 + " " + e1v2 + "<" + e2v1 + " " + e2v2);
                    return -1;
                } else if (e1v2 > e2v2) {
//                        System.out.println(e1v1 + " " + e1v2 + ">" + e2v1 + " " + e2v2);
                    return 1;
                } else {
//                        System.out.println(e1v1 + " " + e1v2 + "=" + e2v1 + " " + e2v2);
                    return 0;
                }
            }
//                e1v1.toString()
            return 0;
        }
    });
    public static Map<String, BioObject> geneMap = new HashMap();
//    public static Map<String, Integer> geneMapToID = new HashMap();
//    public static Map<Integer, String> idMapToGene = new TreeMap();
    public static Map<String, CoExpRelation> coexpMap = new HashMap();
    public static double mrArr[] = new double[MAX];
    public static double prArr[] = new double[MAX];
    public static int deg[] = new int[MAX];
    public static ArrayList geneArr = new ArrayList();
    public static String partner[] = new String[MAX];
    static boolean firstParse = true;
    static StringBuilder sb, sb2;
    static int allCount = 0;
    static String st, sMR, sPR, geneName, geneNameOfThisFile;
    static double mutualRank, pearson;
    static int indexcount = 0;
    private static final double MRBound = 30;

    /**
     * Parses raw ATTED dataset file.
     * This method is for creating an edge file used in NaviCluster & NaviClusterCS
     * @param attedFile
     * @param edgeFileWriter
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void parseAttedFile(File attedFile, PrintWriter edgeFileWriter) throws FileNotFoundException, IOException {
        geneNameOfThisFile = attedFile.getName().toUpperCase();
//        System.out.println(geneNameOfThisFile);
        BioObject object = null;

        if (!geneMap.containsKey(geneNameOfThisFile)) {
//            geneSet.add(geneNameOfThisFile);
            object = new BioObject(indexcount++, geneNameOfThisFile);
//                geneMapToID.put(locus,i);
//                idMapToGene.put(i, locus);
            if (systemToCanonicalNameMap.containsKey(geneNameOfThisFile)) {
                object.setStandardName(systemToCanonicalNameMap.get(geneNameOfThisFile));
            } else {
                object.setStandardName(geneNameOfThisFile);
            }
//            object.setAspectList(new ArrayList<String>());
//            object.setEviCodeList(new ArrayList<String>());
            object.setPropTermList(new ArrayList<String>());
//            object.setRefList(new ArrayList<String>());
//            object.setWithOrFromList(new ArrayList<String>());
//            object.setType("gene");
            geneMap.put(geneNameOfThisFile, object);
        } else {
            object = geneMap.get(geneNameOfThisFile);
        }
//        if (!geneArr.contains(geneNameOfThisFile))
//            geneArr.add(geneNameOfThisFile);

//        else
//            System.out.println("already contains "+geneNameOfThisFile);
        BufferedReader br = new BufferedReader(new FileReader(attedFile));
//        PrintWriter edgeFileWriter = new PrintWriter(edgeFileWriter);

        StringTokenizer stn = null;
//        int i = deg[geneArr.indexOf(geneNameOfThisFile)];
        int i = 0;
        while ((st = br.readLine()) != null) {
//            int index = 0;
            BioObject object2 = null;
            stn = new StringTokenizer(st);
            geneName = stn.nextToken().toUpperCase();


//            if (!geneArr.contains(geneName))
//                continue;
//            else
//            {
//                i += deg[geneArr.indexOf(geneNameOfThisFile)]+1;
//
//            }
            if (!geneMap.containsKey(geneName)) {
//                geneSet.add(geneName);
                object2 = new BioObject(indexcount++, geneName);
                if (systemToCanonicalNameMap.containsKey(geneName)) {
                    object2.setStandardName(systemToCanonicalNameMap.get(geneName));
                } else {
                    object2.setStandardName(geneName);
                }
//                geneMapToID.put(locus,i);
//                idMapToGene.put(i, locus);
//                object2.setAspectList(new ArrayList<String>());
//                object2.setEviCodeList(new ArrayList<String>());
                object2.setPropTermList(new ArrayList<String>());
//                object2.setRefList(new ArrayList<String>());
//                object2.setWithOrFromList(new ArrayList<String>());
//                object2.setType("gene");
                geneMap.put(geneName, object2);
            } else {
                object2 = geneMap.get(geneName);
            }

            sMR = stn.nextToken();
            sPR = stn.nextToken();
            mutualRank = Double.parseDouble(sMR);
            // New measure is defined as a "1/MR"
//            if (mutualRank > MRBound) {
//                break;
//            } else //                mutualRank = Math.log10((double)MRBound/mutualRank);
//            {
//                mutualRank = 1 / mutualRank;
//            }

            /* New measure is defined as a "bound minus a mutual rank" */
            if (mutualRank > MRBound) {
                break;
            } else {
                mutualRank = MRBound - mutualRank;
            }
            pearson = Double.parseDouble(sPR);
//            geneSet.add(geneName);

//            partner[index] = geneName;
//            sMR[index] = gen

            CoExpRelation cer = new CoExpRelation(object, object2, mutualRank, pearson);
            sb = new StringBuilder(geneNameOfThisFile);
            sb.append(":").append(geneName);
            sb2 = new StringBuilder(geneName);
            sb2.append(":").append(geneNameOfThisFile);
//            if (!coexpMap.containsKey(geneNameOfThisFile+":"+geneName) &&
//                    !coexpMap.containsKey(geneName+":"+geneNameOfThisFile))
            ///DEBUG///
//            if ("At1g01010".equalsIgnoreCase(geneNameOfThisFile) && "At1g62300".equalsIgnoreCase(geneName)) {
//                System.out.println("sb " + sb);
//                System.out.println("sb2 " + sb2);
//                System.out.println(coexpMap.containsKey(sb.toString()));
//                System.out.println(coexpMap.containsKey(sb2.toString()));
//            }
//            if ("At1g62300".equalsIgnoreCase(geneNameOfThisFile) && "At1g01010".equalsIgnoreCase(geneName)) {
//                System.out.println("sb " + sb);
//                System.out.println("sb2 " + sb2);
//                System.out.println(coexpMap.containsKey(sb.toString()));
//                System.out.println(coexpMap.containsKey(sb2.toString()));
//
//            }
            int tmpSize = coexpMap.size();
            if (!coexpMap.containsKey(sb.toString()) && !coexpMap.containsKey(sb2.toString())) {
                coexpMap.put(sb.toString(), cer);
                edgeFileWriter.println(geneNameOfThisFile + "\t" + geneName + "\t" + mutualRank);
                allCount++;
                if (coexpMap.size() != allCount)
                    System.out.println("sb "+sb);
            }
            i++;
//            if (i == 3)
//                break;
//            else
//                System.out.println("already contains "+cer);
//            System.out.println("geneName: "+geneName+"\t"+mutualRank+"\t"+pearson);

        }
        br.close();
    }

    /**
     * Parses raw ATTED dataset file.
     * @param f
     * @throws FileNotFoundException
     * @throws IOException
     */
//    public static void parseAttedFile(File f) throws FileNotFoundException, IOException {
//
//        geneNameOfThisFile = f.getName().toUpperCase();
////        System.out.println(geneNameOfThisFile);
//        BioObject object = null;
//        if (!geneMap.containsKey(geneNameOfThisFile)) {
////            geneSet.add(geneNameOfThisFile);
//            object = new BioObject(indexcount++, geneNameOfThisFile);
////                geneMapToID.put(locus,i);
////                idMapToGene.put(i, locus);
//            if (systemToCanonicalNameMap.containsKey(geneNameOfThisFile)) {
//                object.setStandardName(systemToCanonicalNameMap.get(geneNameOfThisFile));
//            } else {
//                object.setStandardName(geneNameOfThisFile);
//            }
//            object.setAspectList(new ArrayList<String>());
//            object.setEviCodeList(new ArrayList<String>());
//            object.setPropTermList(new ArrayList<String>());
//            object.setRefList(new ArrayList<String>());
//            object.setWithOrFromList(new ArrayList<String>());
////            object.setType("gene");
//            geneMap.put(geneNameOfThisFile, object);
//        } else {
//            object = geneMap.get(geneNameOfThisFile);
//        }
////        if (!geneArr.contains(geneNameOfThisFile))
////            geneArr.add(geneNameOfThisFile);
//
////        else
////            System.out.println("already contains "+geneNameOfThisFile);
//        BufferedReader br = new BufferedReader(new FileReader(f));
//
//        StringTokenizer stn = null;
////        int i = deg[geneArr.indexOf(geneNameOfThisFile)];
//        int i = 0;
//        while ((st = br.readLine()) != null) {
//            int index = 0;
//            BioObject object2 = null;
//            stn = new StringTokenizer(st);
//            geneName = stn.nextToken().toUpperCase();
//
////            if (!geneArr.contains(geneName))
////                continue;
////            else
////            {
////                i += deg[geneArr.indexOf(geneNameOfThisFile)]+1;
////
////            }
//            if (!geneMap.containsKey(geneName)) {
////                geneSet.add(geneName);
//                object2 = new BioObject(indexcount++, geneName);
//                if (systemToCanonicalNameMap.containsKey(geneName)) {
//                    object2.setStandardName(systemToCanonicalNameMap.get(geneName));
//                } else {
//                    object2.setStandardName(geneName);
//                }
////                geneMapToID.put(locus,i);
////                idMapToGene.put(i, locus);
//                object2.setAspectList(new ArrayList<String>());
//                object2.setEviCodeList(new ArrayList<String>());
//                object2.setPropTermList(new ArrayList<String>());
//                object2.setRefList(new ArrayList<String>());
//                object2.setWithOrFromList(new ArrayList<String>());
////                object2.setType("gene");
//                geneMap.put(geneName, object2);
//            } else {
//                object2 = geneMap.get(geneName);
//            }
//
//            sMR = stn.nextToken();
//            sPR = stn.nextToken();
//            mutualRank = Double.parseDouble(sMR);
//            if (mutualRank > MRBound) {
//                break;
//            }
//            pearson = Double.parseDouble(sPR);
////            geneSet.add(geneName);
//
////            partner[index] = geneName;
////            sMR[index] = gen
//
//            CoExpRelation cer = new CoExpRelation(object, object2, mutualRank, pearson);
//
//            sb = new StringBuilder(geneNameOfThisFile);
//            sb.append(":").append(geneName);
//            sb2 = new StringBuilder(geneName);
//            sb2.append(":").append(geneNameOfThisFile);
////            if (!coexpMap.containsKey(geneNameOfThisFile+":"+geneName) &&
////                    !coexpMap.containsKey(geneName+":"+geneNameOfThisFile))
//            if (!coexpMap.containsKey(sb.toString())
//                    && !coexpMap.containsKey(sb2.toString())) {
//                coexpMap.put(sb.toString(), cer);
//            }
//            i++;
//            if (i == 3) {
//                break;
//            }
////            else
////                System.out.println("already contains "+cer);
////            System.out.println("geneName: "+geneName+"\t"+mutualRank+"\t"+pearson);
//
//        }
//        br.close();
////        if (geneArr.size()-1 > 0)
////            deg[geneArr.size()-1] = i+deg[geneArr.size()-1-1];
////        else
////            deg[geneArr.size()-1] = i;
//
////        for (Object obj : coexpMap){
////            CoExpRelation coexp = (CoExpRelation)obj;
////            System.out.println(coexp.getBioObj1()+"\t"+coexp.getBioObj2()+"\t"+coexp.getMutualRank()+"\t"+coexp.getPearson());
////        }
////        System.out.println(geneSet.contains("At1g54040"));
//    }

//    public static void initialize(File f) throws FileNotFoundException, IOException{
//        String geneNameOfThisFile = f.getName();
//        geneArr.add(geneNameOfThisFile);
//
////        else
////            System.out.println("already contains "+geneNameOfThisFile);
//        BufferedReader br = new BufferedReader(new FileReader(f));
//        String st;
//        StringTokenizer stn = null;
//        int i = 1;
//        while ((st = br.readLine()) != null){
//
//            stn = new StringTokenizer(st);
//            String geneName = stn.nextToken();
//            if (!geneArr.contains(geneName))
//                geneArr.add(geneName);
//        }
//    }
    public static void printGenesToFile(PrintWriter pw) {
        Set allGenes = geneMap.keySet();
        allGenes.removeAll(hasAnnotSet);
        System.out.println("has annotate gene set size " + hasAnnotSet.size());
        System.out.println("Number of genes that have no annotations " + allGenes.size());
//        System.out.println("gene set size "+geneSet.size());
        System.out.println("All gene set size " + geneMap.size());
        pw.println("*Vertices\t" + (geneMap.size()));
        int i = 0;
        for (BioObject bio : sortedGeneSet) {
//        for (BioObject bio : geneMap.values()){
            pw.print(bio.getId() + "\t" + bio.getStandardName() + "\t" + bio.getName() + "\t" + bio.getDatabaseID() + "\t" + bio.getType() + "\t");
            int ind = 0;
            for (String st : bio.getPropTermList()) {
                if (ind != 0) {
                    pw.print("|");
                }
                pw.print(st);
                ind++;
            }
            pw.print("\t");
            ind = 0;
            for (String st : bio.getRefList()) {
                if (ind != 0) {
                    pw.print("|");
                }
                pw.print(st);
                ind++;
            }
            pw.print("\t");
            ind = 0;
            for (String st : bio.getAspectList()) {
                if (ind != 0) {
                    pw.print("|");
                }
                pw.print(st);
                ind++;
            }
            pw.println();
            i++;
        }
    }

    public static void printGenesToFile(File f) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(f);
        printGenesToFile(pw);
        pw.close();
    }

    public static void printCoexpToFile(PrintWriter pw) throws FileNotFoundException {

        sortedEdgeSet.addAll(coexpMap.values());
//        System.out.println("coexpMap size "+coexpMap.size());
        System.out.println("edge set size " + sortedEdgeSet.size());
//        pw.println("*Edges\t"+coexpMap.values().size());
        pw.println("*Edges\t" + sortedEdgeSet.size());

//        for (CoExpRelation coexp : coexpMap.values()){
//            pw.println(coexp.getBioObj1().getName()+"\t"+coexp.getBioObj2().getName()+"\t"+coexp.getMutualRank()+"\t"+coexp.getPearson());
//        }


        for (CoExpRelation edge : sortedEdgeSet) {
//        for (CoExpRelation edge : coexpMap.values()) {
            int first = ((BioObject) edge.getBioObj1()).getId();
            int second = ((BioObject) edge.getBioObj2()).getId();
//            int temp;
//            if (first > second) {
//                temp = first;
//                first = second;
//                second = temp;
//            }
//            pw.println(((BioObject)edge.getBait()).getId()+" "+((BioObject)edge.getHit()).getId()+" "+edge.getNumEvidence());
//            pw.print(first + "\t" + second + "\t" + edge.getMutualRank() + "\t" + edge.getBioObj1().getName() + "\t" + edge.getBioObj2().getName());
            pw.print(first + "\t" + second + "\t" + edge.getMutualRank() + "\t");
            int ind = 0;

            pw.println();
        }


    }

    public static void printCoexpToFile(File f) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(f);
        printCoexpToFile(pw);
        pw.close();
    }

    public static void parseTairFile(File tairFile, File nodeFile) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(tairFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        PrintWriter pw = new PrintWriter(nodeFile);
        pw.println("display_name\tsource_db_name\tsource_db_id\tprop_info");
        st = "";
        StringTokenizer stz = null;
        String tairID = "", locus = "", goid = "", goterm = "", ref = "", evid = "", evidwith = "";
        String aspect = "", objname = "", keywordID = "", date = "", goslim = "", annotator = "";
        String rel = "";

//        Set debugSet = new HashSet();
//        ArrayList debugSet = new ArrayList();
        int i = -1;


        while ((st = br.readLine()) != null) {

            stz = new StringTokenizer(st, "\t", true);
            assert (stz.hasMoreTokens());
            locus = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            tairID = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            objname = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            BioObject object = null;

            if (!geneMap.keySet().contains(locus)) {
                continue;
            } else {
                hasAnnotSet.add(locus);
            }


            /* geneMap might always contain key locus because
            the key must have been input via parseAttedFile
            before running to this line
            or else, the program will "continue" to the next iteration
             */
//            if (!geneMap.containsKey(locus))
//            {
//                i++;
//                object = new BioObject(i, tairID, locus);
//                object.setStandardName(objname);
//                geneMap.put(locus,object);
////                geneMapToID.put(locus,i);
////                idMapToGene.put(i, locus);
//                object.setAspectList(new ArrayList<String>());
//                object.setEviCodeList(new ArrayList<String>());
//                object.setPropTermList(new ArrayList<String>());
//                object.setRefList(new ArrayList<String>());
//                object.setWithOrFromList(new ArrayList<String>());
//                object.setType("gene");
//            } else {
            object = geneMap.get(locus);
            if (object.getDatabaseID().equals("")) {
                object.setType("gene");
                object.setDatabaseID(tairID);
//                    object.setStandardName(objname);
            }
//            }

            assert (stz.hasMoreTokens());
            rel = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            goterm = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            goid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            keywordID = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            aspect = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            goslim = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            evid = stz.nextToken();
            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            evidwith = stz.nextToken();
//            if (evidwith.equals("\t"))
//                evidwith = "";
//            else{
//                stz.nextToken();
//            }
//
//            assert (stz.hasMoreTokens());
//            ref = stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            annotator = stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
////            System.out.println("locus name "+locus+" evidwith "+annotator);
//            assert (stz.hasMoreTokens());
//            date = stz.nextToken();
//            if (stz.nextToken().equals("\t"));


//            object.getAspectList().add(aspect);
//            object.getEviCodeList().add(evid);
            if (!"IEA".equalsIgnoreCase(evid)) {
                object.getPropTermList().add(goid);
            }
//            System.out.println("goid "+goid);
//            object.getNotModifierList().add(not);
//            ref = ref.replace("|", "/");
//            object.getRefList().add(ref);
//            object.getWithOrFromList().add(evidwith);
        }
        br.close();
//        System.out.println("count "+i);
//        int j = 0;

        for (BioObject bio : geneMap.values()) {

            pw.print(bio.getName() + "\tTAIR\t" + bio.getDatabaseID() + "\t");
            int ind = 0;
            for (String term : bio.getPropTermList()) {
                if (ind != 0) {
                    pw.print("|");
                }
                pw.print(term);
                ind++;
            }
            pw.println();

        }
        pw.close();
        System.out.println("geneMap size " + geneMap.size());
//        sortedGeneSet.addAll(geneMap.values());
    }

    public static void parseTairFile(File f) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        st = "";
        StringTokenizer stz = null;
        String tairID = "", locus = "", goid = "", goterm = "", ref = "", evid = "", evidwith = "";
        String aspect = "", objname = "", keywordID = "", date = "", goslim = "", annotator = "";

//        Set debugSet = new HashSet();
//        ArrayList debugSet = new ArrayList();
        int i = -1;
        while ((st = br.readLine()) != null) {

            stz = new StringTokenizer(st, "\t", true);
            assert (stz.hasMoreTokens());
            locus = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            tairID = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            objname = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            BioObject object = null;

            if (!geneMap.keySet().contains(locus)) {
                continue;
            } else {
                hasAnnotSet.add(locus);
            }


            /* geneMap might always contain key locus because
            the key must have been input via parseAttedFile
            before running to this line
            or else, the program will "continue" to the next iteration
             */
//            if (!geneMap.containsKey(locus))
//            {
//                i++;
//                object = new BioObject(i, tairID, locus);
//                object.setStandardName(objname);
//                geneMap.put(locus,object);
////                geneMapToID.put(locus,i);
////                idMapToGene.put(i, locus);
//                object.setAspectList(new ArrayList<String>());
//                object.setEviCodeList(new ArrayList<String>());
//                object.setPropTermList(new ArrayList<String>());
//                object.setRefList(new ArrayList<String>());
//                object.setWithOrFromList(new ArrayList<String>());
//                object.setType("gene");
//            } else {
            object = geneMap.get(locus);
            if (object.getDatabaseID().equals("")) {
                object.setType("gene");
                object.setDatabaseID(tairID);
//                    object.setStandardName(objname);
            }
//            }


            assert (stz.hasMoreTokens());
            goterm = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            goid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            keywordID = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            aspect = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            goslim = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            evid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            evidwith = stz.nextToken();
            if (evidwith.equals("\t")) {
                evidwith = "";
            } else {
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            ref = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            annotator = stz.nextToken();
            if (stz.nextToken().equals("\t"));

//            System.out.println("locus name "+locus+" evidwith "+annotator);
            assert (stz.hasMoreTokens());
            date = stz.nextToken();
//            if (stz.nextToken().equals("\t"));


            object.getAspectList().add(aspect);
            object.getEviCodeList().add(evid);
            object.getPropTermList().add(goid);
//            object.getNotModifierList().add(not);
            ref = ref.replace("|", "/");
            object.getRefList().add(ref);
            object.getWithOrFromList().add(evidwith);
        }
        br.close();
//        System.out.println("count "+i);
//        int j = 0;
//        for (String s : geneMap.keySet()){
//            System.out.println("keySet "+ s);
//            j++;
//            if (j == 50)
//                break;
//        }
        System.out.println("genemap size " + geneMap.size());
        sortedGeneSet.addAll(geneMap.values());
        /* 11/04/14 */
//        for (String bioName : geneMap.keySet()){
//            sortedGeneSet.add(geneMap.get(bioName));
//        }


    }

    /**
     * Deprecated
     * 11/04/14     commented out
     */
//    public static void checkRedundancy(){
//        Set newSet = new HashSet();
//        for (BioObject bio : sortedGeneSet){
//            newSet.add(bio.getName());
//        }
//        for (Object name : geneSet){
//            if (newSet.contains((String)name))
//                System.out.println("contain "+name);
//            else
//                newSet.add(name);
//        }
//    }
    public static void genModPajekFormat(File f) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(f);
        printGenesToFile(pw);
        printCoexpToFile(pw);
        pw.close();
    }

    public static void parseSymbolMappingFile(File f) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String st = "";
        while ((st = br.readLine()) != null) {
            StringTokenizer stk = new StringTokenizer(st);
            systemToCanonicalNameMap.put(stk.nextToken(), stk.nextToken());
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File dir = new File("/Users/knacky/atted-ii/");
//        File dir = new File("D:\\Thanet\\atted-ii\\");
//        PrintWriter edgeFileWriter = new PrintWriter(new File("/Users/knacky/atted-ii/atted-invMR.edge"));
        PrintWriter edgeFileWriter = new PrintWriter(new File("/Users/knacky/atted-ii/atted.edge"));
//        PrintWriter edgeFileWriter = new PrintWriter(new File("D:\\Thanet\\atted-ii\\atted.edge"));
        edgeFileWriter.println("source_node\ttarget_node\tweight");
        System.out.println("dir " + dir.isDirectory());
//        parseSymbolMappingFile(new File("/Users/Knacky/atted-ii/locus-symbol-2009-07-07.txt"));
        StopWatch sw = new StopWatch();
        int count = 0;
        for (File subdir : dir.listFiles()) {

            if (subdir.isDirectory() && !subdir.getName().contains(".DS")) {
                System.out.println("subdir: " + subdir.getName());

                int i = 0;
                sw.start();
                for (File attedFile : subdir.listFiles()) {
                    if (!attedFile.getName().contains(".DS")) {
//                        System.out.println("attedFile : "+attedFile.getName());
//                        if (firstParse)
//                            initialize(attedFile);
//                        parseAttedFile(attedFile);

                        parseAttedFile(attedFile, edgeFileWriter);
                        i++;
                        if (i % 1000 == 0) {
                            System.out.println("count : " + i);
                            sw.stop();
                            System.out.println("stop watch " + sw);
//                            System.out.println("Gene set size "+geneSet.size());
                            System.out.println("Coexp size " + coexpMap.size());
                            sw.start();
//                            break;

                        }
//                        if (i%300 == 0){
//                            break;
//                        }

                    }

                }

//                break;
//                count++;
//                System.out.println("Dir "+count+" have "+i+" files.");
//                System.out.println("gene set size "+geneSet.size());
//                System.out.println("coexp map size "+coexpMap.size());
            }

        }
        
//        parseTairFile(new File("/Users/thanet/atted-ii/ATH_GO_GOSLIM.txt"), new File("/Users/knacky/atted-ii/atted-invMR.node"));
        parseTairFile(new File("/Users/thanet/atted-ii/ATH_GO_GOSLIM.txt"), new File("/Users/knacky/atted-ii/atted.node"));
//        parseTairFile(new File("D:\\Thanet\\atted-ii\\ATH_GO_GOSLIM.txt"),new File("D:\\Thanet\\atted-ii\\atted.node"));
//        printGenesToFile(new File("atted-gene.txt"));
//        printCoexpToFile(new File("atted-coexp.txt"));
//        genModPajekFormat(new File("atted-coexp-3highest.mnet"));
//        checkRedundancy();

//        parseAttedFile(new File("At1g01010"));
//        parseAttedFile(new File("At1g54040"));
//        CoExpRelation cer1 = new CoExpRelation("11","22",1,2);
//        CoExpRelation cer2 = new CoExpRelation("22","11",2,2);
//        HashSet set = new HashSet<CoExpRelation>();
//        set.add(cer1);
//        System.out.println(cer1.equals(cer2));
//        System.out.println(set.contains(cer2));
        edgeFileWriter.close();
    }
}
