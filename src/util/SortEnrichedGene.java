/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import edu.uci.ics.jung.graph.Graph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import main.BioObject;

/**
 *
 * @author knacky
 */
public class SortEnrichedGene {
    
    Map<String, BioObject> enrichedNameObjMap = new HashMap<String, BioObject>();
    SortedSet<BioObject> bioObjSet = new TreeSet<BioObject>(new Comparator<BioObject>() {

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

    SortedSet<BioObject> sortedByNumGO = new TreeSet<BioObject>(new Comparator<BioObject>() {

        public int compare(BioObject v1, BioObject v2) {
            if (v1.getPropTermList().size() < v2.getPropTermList().size()) {
                return 1;
            } else if (v1.getPropTermList().size() >= v2.getPropTermList().size()) {
                return -1;
            } else 
                return 0;
//            else {
//                return -1;
//            }
        }
    });

    public void parseGeneAssoc(File f) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String st = "";
        StringTokenizer stz = null;
        String sgdid = "", symbol = "", not = "", goid = "", ref = "", evid = "", withfrom = "";
        String aspect = "", objname = "", objsyn = "", type = "", taxon = "", date = "";

        int iden = 0;
        // cannot receive any comment lines
//        Set debugSet = new HashSet();
//        ArrayList debugSet = new ArrayList();
        bioObjSet.clear();
//        bioObjSet.addAll(graph.getVertices());
        while ((st = br.readLine()) != null) {
            /* ! precedes comments */
            if (st.startsWith("!")) {
                continue;
            }
            stz = new StringTokenizer(st, "\t", true);
            assert (stz.hasMoreTokens());
            stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            sgdid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            symbol = stz.nextToken();
            if (stz.nextToken().equals("\t"));


//            BioObject object = null;
            BioObject object = enrichedNameObjMap.get(symbol);

//            if (symbol.equals("HAP1")) {
//                int jj = 2;
//            }
//            ArrayList vertices = new ArrayList(graph.getVertices());
//            System.out.println(vertices.get(4219));



            assert (stz.hasMoreTokens());
            not = stz.nextToken();
            if (not.equals("\t")) {
                not = "";
            } else {
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            goid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            ref = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            evid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            withfrom = stz.nextToken();
            if (withfrom.equals("\t")) {
                withfrom = "";
            } else {
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            aspect = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            objname = stz.nextToken();
            if (objname.equals("\t")) {
                objname = "";
            } else {
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            objsyn = stz.nextToken();
            if (objsyn.equals("\t")) {
                objsyn = "";
            } else {
                stz.nextToken();
            }

            ArrayList listOfSyn = new ArrayList();
            StringTokenizer stn = new StringTokenizer(objsyn, "|");
            while (stn.hasMoreTokens()) {
//                String sss = stn.nextToken();
                listOfSyn.add(stn.nextToken());

            }

            assert (stz.hasMoreTokens());
            type = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            taxon = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            date = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            if (object == null) {
                object = new BioObject(iden++, sgdid, symbol);
//            if (object.getDatabaseID().equals("")) {
                object.setStandardName(symbol);
                object.setDatabaseID(sgdid);
                object.setTaxon(taxon);
                object.setType(type);
                //for now date is the same for all annotations of one object
                object.setDate(date);
                if (!"".equals(objname)) {
                    object.setObjName(objname);
                }
                object.setSynonym(new ArrayList<String>());
                object.getSynonym().addAll(listOfSyn);
                object.setAspectList(new ArrayList<String>());
//                object.getAspectList().add(aspect);
                object.setEviCodeList(new ArrayList<String>());
//                object.getEviCodeList().add(evid);
                object.setPropTermList(new ArrayList<String>());
//                object.getPropTermList().add(goid);
//                if (!"".equals(not)) {
                object.setNotModifierList(new ArrayList<String>());
//                    object.getNotModifierList().add(not);
//                }
                object.setRefList(new ArrayList<String>());
                object.getRefList().add(ref);
//                if (!"".equals(withfrom)) {
                object.setWithOrFromList(new ArrayList<String>());
//                    object.getWithOrFromList().add(withfrom);
//                }
                enrichedNameObjMap.put(object.getStandardName(), object);
            }
            if (!"IEA".equalsIgnoreCase(evid)) {
                object.getAspectList().add(aspect);
                object.getEviCodeList().add(evid);
                object.getPropTermList().add(goid);
                if (!"".equals(not)) {
                    object.getNotModifierList().add(not);
                }
                object.getRefList().add(ref);
                if (!"".equals(withfrom)) {
                    object.getWithOrFromList().add(withfrom);
                }
            }
//            if (!enrichedNameObjMap.containsKey(object.getStandardName())) {
//                enrichedNameObjMap.put(object.getStandardName(), object);
//            }
        }
        Set<BioObject> tmpSet = new HashSet<BioObject>(enrichedNameObjMap.values());
        for (BioObject bioObj : tmpSet) {
            if (!enrichedNameObjMap.containsKey(bioObj.getName())) {
                enrichedNameObjMap.put(bioObj.getName(), bioObj);
            }
        }
        tmpSet = new HashSet<BioObject>(enrichedNameObjMap.values());
        for (BioObject bioObj : tmpSet) {
            for (String syn : bioObj.getSynonym()) {
                if (!enrichedNameObjMap.containsKey(syn)) {
                    enrichedNameObjMap.put(syn, bioObj);
                }
            }
        }
        br.close();
        bioObjSet.addAll(enrichedNameObjMap.values());
        sortedByNumGO.addAll(bioObjSet);
        System.out.println("bioobjset "+bioObjSet.size());
        System.out.println("sorted "+sortedByNumGO.size());
//        g = graph;
//        return graph;
//        for (Object v : g.getVertices()){
//                BioObject vertex = (BioObject)v;
//                if (debugSet.indexOf(vertex.getId()) > -1)
//                    System.out.println("replicated vertex id: "+vertex.getId()+" name: "+vertex.getName());
//                debugSet.add(vertex.getId());
////                    if (debugSet.contains(vertex));
////
//////                        System.out.println("replicated vertex id: "+vertex.getId()+" name: "+vertex.getName());
////                    else
////                        debugSet.add(vertex);
////                    if (vertex.getName().equals("YAL011W"))
////                        System.out.println(vertex.getId());
////                    if (vertex.getName().equals("Y0R251C"))
////                        System.out.println("Found 2");
//
//
//            }
//        System.out.println("debug set "+debugSet.size());
    }

    public static void main(String[] args) throws IOException{
        SortEnrichedGene seg = new SortEnrichedGene();
        StopWatch sw = new StopWatch();
        sw.start();
        seg.parseGeneAssoc(new File("src/datasets/gene_association.sgd"));
        sw.stop();
        System.out.println("stop watch " + sw);
        int i = 0;
        for (BioObject bio : seg.sortedByNumGO){
//            System.out.print("gene name: "+bio.getStandardName());
//            System.out.println("\tnumber of GOs: "+bio.getPropTermList().size());
            System.out.print(""+bio.getStandardName()+" [SGD|http://www.yeastgenome.org/cgi-bin/locus.fpl?locus="+bio.getStandardName()+"] ");
            System.out.println(bio.getPropTermList().size()+" GOs");
//                    +" list: "+bio.getPropTermList());
            i++;
            if (i == 50){
                break;
            }
        }
    }
}
