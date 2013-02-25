/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import objects.BioEdge;
import objects.BioObject;
import objects.SGDInteraction;

/**
 *
 * @author user
 */
public class SGDDataHandler {
    private int iden = 0;
    private Graph<BioObject,SGDInteraction> g;
//    ArrayList<SGDInteraction> interactList = new ArrayList<SGDInteraction>();
    static String fileToPrint = "src/temp.net";
    Map<String, BioObject> bioNameObjMap = new HashMap<String, BioObject>();
    Map<String, BioObject> enrichedNameObjMap = new HashMap<String, BioObject>();
    SortedSet<BioObject> bioObjSet = new TreeSet<BioObject>(new Comparator<BioObject>(){
        public int compare(BioObject v1, BioObject v2) {
            if (v1.getId() < v2.getId())
                return -1;
            else if (v1.getId() == v2.getId())
                return 0;
            else
                return 1;
        }
    });
    SortedSet<SGDInteraction> edgeSet = new TreeSet<SGDInteraction>(new Comparator<SGDInteraction>() {

            public int compare(SGDInteraction e1, SGDInteraction e2) {
                int e1v1 = ((BioObject)e1.getBait()).getId();
                int e1v2 = ((BioObject)e1.getHit()).getId();
                int e2v1 = ((BioObject)e2.getBait()).getId();
                int e2v2 = ((BioObject)e2.getHit()).getId();
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

    public void parseInteractionTab(File f) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        g = SparseMultigraph.<BioObject, SGDInteraction>getFactory().create();
//        ArrayList<BioObject> bioObjectList = new ArrayList<BioObject>();
//        ArrayList<SGDInteraction> interactList = new ArrayList<SGDInteraction>();

        String st, out = "";
        StringTokenizer stz;
        String baitName = "", baitStName = "";
        String hitName = "", hitStName = "";
        String exper = "", intType = "", source = "", curation = "";
        String note = "", phenotype = "", refer = "", citation = "";
//        int numRefWant = 0;
        ArrayList<String> list;
        double weight = 1;
        int numEdge = 0;
//        BioObject esf2 = null;

        while ((st = br.readLine()) != null) {
            stz = new StringTokenizer(st, "\t",true);

            assert (stz.hasMoreTokens());
            baitName = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            baitStName = stz.nextToken();
            if (baitStName.equals("\t"))
                baitStName = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            hitName = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            //ignore self loop
            if (hitName.equals(baitName)){
                continue;//System.out.println("Self Loop "+hitName);
            }


//            if (hitName.equals("YOR251C"))
//                 System.out.println("Yeah");
            assert (stz.hasMoreTokens());
            hitStName = stz.nextToken();
            if (hitStName.equals("\t"))
                hitStName = "";
            else{
                stz.nextToken();
            }

//            System.out.println(hitStName);
            BioObject bait = null; //new BioObject();
            BioObject hit = null;//new BioObject();

//            System.out.println(vertices);
//            if (vertices.indexOf(bait) > -1)
//                bait = (BioObject) vertices.get(vertices.indexOf(bait));
//            if (vertices.indexOf(hit) > -1)
//                hit = (BioObject) vertices.get(vertices.indexOf(hit));
//            boolean already = false;
//            boolean already2 = false;

            if (!bioNameObjMap.containsKey(baitName)) {
//            if (!bioObjNameSet.contains(baitName)){
                bait = new BioObject(iden++, baitName);
                bait.setStandardName(baitStName);
                bait.setAspectList(new ArrayList<String>());
                bait.setEviCodeList(new ArrayList<String>());
                bait.setPropTermList(new ArrayList<String>());
                bait.setNotModifierList(new ArrayList<String>());
                bait.setRefList(new ArrayList<String>());
                bait.setSynonym(new ArrayList<String>());
                bait.setWithOrFromList(new ArrayList<String>());
                g.addVertex(bait);
                bioNameObjMap.put(baitName, bait);

//                bioObjNameSet.add(baitName);
//                bioObjSet.add(bait);
            } else {
                bait = bioNameObjMap.get(baitName);

            }
            if (!bioNameObjMap.containsKey(hitName)) {
                hit = new BioObject(iden++, hitName);
                hit.setStandardName(hitStName);
                hit.setAspectList(new ArrayList<String>());
                hit.setEviCodeList(new ArrayList<String>());
                hit.setPropTermList(new ArrayList<String>());
                hit.setNotModifierList(new ArrayList<String>());
                hit.setRefList(new ArrayList<String>());
                hit.setSynonym(new ArrayList<String>());
                hit.setWithOrFromList(new ArrayList<String>());
                g.addVertex(hit);
                bioNameObjMap.put(hitName, hit);

//                bioObjNameSet.add(hitName);
            } else {
                hit = bioNameObjMap.get(hitName);
            }

//            edge.setNode1(bait);
//            edge.setNode2(hit);
//            edge.setWeight(weight);

//            System.out.println(sl.hasStringLabeller(graph, "feature name"));

//            System.out.println(sl.getLabel(bait)+" "+sl.getLabel(hit));
            SGDInteraction edge = new SGDInteraction(bait,hit);

            assert (stz.hasMoreTokens());
            exper = stz.nextToken();
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            intType = stz.nextToken();
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            source = stz.nextToken();
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            curation = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            note = stz.nextToken();
            if (note.equals("\t"))
                note = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            phenotype = stz.nextToken();
            if (phenotype.equals("\t"))
                phenotype = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            refer = stz.nextToken();
//            if (refer.contains("11283351"))
//                numRefWant++;
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            citation = stz.nextToken();
//            System.gc();
            list = new ArrayList<String>();


            if (!g.isNeighbor(bait, hit)){
//                interactList.add(edge);
                g.addEdge(edge, bait, hit);
                numEdge++;
//                edge.setBait(bait);
//                edge.setHit(hit);
                edge.setNumEvidence(1);
//                edge.setUserDatum("NumEvidence", (int)1, UserData.SHARED);
//                ewl.setWeight(edge, 1);
                list.add(exper);
                edge.setExperimentList(list);
//                edge.setUserDatum("Experiment",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(curation);
                edge.setCurationList(list);
//                edge.setUserDatum("Curation",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(citation);
                edge.setCitationList(list);
//                edge.setUserDatum("Citation",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(intType);
                edge.setInteractionList(list);
//                edge.setUserDatum("Interaction",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(refer);
                edge.setReferenceList(list);
//                edge.setUserDatum("Reference",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(phenotype);
                edge.setPhenotypeList(list);
//                edge.setUserDatum("Phenotype",list, UserData.SHARED);
//
                list = new ArrayList<String>();
                list.add(note);
                edge.setNotesList(list);

                list = new ArrayList<String>();
                list.add(source);
                edge.setSourceList(list);
//                edge.setUserDatum("Note",list, UserData.SHARED);
            } else {
                edge = g.findEdge(bait, hit);

//                int oldnum = (Integer)edge.getUserDatum("NumEvidence");
                edge.setNumEvidence(1+edge.getNumEvidence());
//                edge.setUserDatum("NumEvidence", 1+oldnum , UserData.SHARED);
//                ewl.setWeight(edge,1+oldnum);
                edge.getExperimentList().add(exper);
                edge.getCurationList().add(curation);
                edge.getCitationList().add(citation);
                edge.getInteractionList().add(intType);
                edge.getReferenceList().add(refer);
                edge.getPhenotypeList().add(phenotype);
                edge.getNotesList().add(note);
                edge.getSourceList().add(source);

//                System.out.println(baitName+" "+hitName+" "+((ArrayList)edge.getUserDatum("Phenotype")));
            }
//            break;
        }

        br.close();
//        System.out.println("num node: "+bioNameObjMap.size()+" num edge "+numEdge);
//        System.out.println("contains? "+g.getVertices().contains(debugObj)+" "+debugObj.getName()+" "+debugObj.getStandardName());
        System.out.println("Num nodes: "+g.getVertexCount()+" Num edges: "+g.getEdgeCount());
//        System.out.println(graph.numEdges()+" "+graph.numVertices());
//        System.out.println("Num ref want: "+numRefWant);
    }

    /*
    public void parseInteractionTab(File f) throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        g = SparseMultigraph.<BioObject,SGDInteraction>getFactory().create();
//        ArrayList<BioObject> bioObjectList = new ArrayList<BioObject>();
//        ArrayList<SGDInteraction> interactList = new ArrayList<SGDInteraction>();

        String st, out = "";
        StringTokenizer stz;
        String baitName = "", baitStName = "";
        String hitName = "", hitStName = "";
        String exper = "", intType = "", source = "", curation = "";
        String note = "", phenotype = "", refer = "", citation = "";
//        int numRefWant = 0;
        ArrayList <String> list;
        BioObject debugObj = null;
        while ((st = br.readLine()) != null) {
            stz = new StringTokenizer(st, "\t",true);

            assert (stz.hasMoreTokens());
            baitName = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            baitStName = stz.nextToken();
            if (baitStName.equals("\t"))
                baitStName = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            hitName = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            //ignore self loop
            if (hitName.equals(baitName)){
                continue;//System.out.println("Self Loop "+hitName);
            }
//            if (hitName.equals("YOR251C"))
//                 System.out.println("Yeah");
            assert (stz.hasMoreTokens());
            hitStName = stz.nextToken();
            if (hitStName.equals("\t"))
                hitStName = "";
            else{
                stz.nextToken();
            }
//            System.out.println(hitStName);
            BioObject bait = null; //new BioObject();
            BioObject hit = null;//new BioObject();
            ArrayList vertices = new ArrayList(g.getVertices());
//            System.out.println(vertices);
//            if (vertices.indexOf(bait) > -1)
//                bait = (BioObject) vertices.get(vertices.indexOf(bait));
//            if (vertices.indexOf(hit) > -1)
//                hit = (BioObject) vertices.get(vertices.indexOf(hit));
            boolean already = false;
            boolean already2 = false;
            for (Object v : vertices){
                BioObject vertex = (BioObject)v;
                if (!already) {
                    if (vertex.getName().equals(baitName)) {
                        already = true;
                        bait = vertex;
                    }
                }
                if (!already2) {
                    if (vertex.getName().equals(hitName)) {
                        already2 = true;
                        hit = vertex;
                    }
                }
                if (already && already2)
                    break;

//                else {
//                    if (vertex.getSynonym().contains(baitName))
//                    {
//                        already = true;
//                        break;
//                    }
//                }
            }

//            if (bait == null)
            if (!already)
            {
                
//                if (baitName.equals("YLR390W")){
//                    System.out.println("found ylr");
//                    System.out.println(iden);
//                }
                    
                bait = new BioObject(iden++,baitName);
                bait.setStandardName(baitStName);
                bait.setAspectList(new ArrayList<String>());
                bait.setEviCodeList(new ArrayList<String>());
                bait.setPropTermList(new ArrayList<String>());
                bait.setNotModifierList(new ArrayList<String>());
                bait.setRefList(new ArrayList<String>());
                bait.setSynonym(new ArrayList<String>());
                bait.setWithOrFromList(new ArrayList<String>());
                g.addVertex(bait);
//                if (baitName.equals("YOR251C")){
//                    debugObj = bait;
//                    System.out.println("found"+baitStName);
//                    System.out.println(iden);
//                }
            }

//            if (hit == null) {
//            {
            if (!already2){
                
//                if (hitName.equals("YLR390W")){
//                    System.out.println("found ylr");
//                    System.out.println(iden);
//                }
                    
                hit = new BioObject(iden++,hitName);
                hit.setStandardName(hitStName);
                hit.setAspectList(new ArrayList<String>());
                hit.setEviCodeList(new ArrayList<String>());
                hit.setPropTermList(new ArrayList<String>());
                hit.setNotModifierList(new ArrayList<String>());
                hit.setRefList(new ArrayList<String>());
                hit.setSynonym(new ArrayList<String>());
                hit.setWithOrFromList(new ArrayList<String>());
                g.addVertex(hit);
//                if (hitName.equals("YOR251C")){
//                    debugObj = hit;
//                    System.out.println("found "+hitStName+" with "+baitName+ " "+baitStName );
//                    System.out.println(st);
//                    System.out.println(iden);
//                }
            }

//            System.out.println(sl.hasStringLabeller(graph, "feature name"));

//            System.out.println(sl.getLabel(bait)+" "+sl.getLabel(hit));
            SGDInteraction edge = new SGDInteraction();

            assert (stz.hasMoreTokens());
            exper = stz.nextToken();
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            intType = stz.nextToken();
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            source = stz.nextToken();
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            curation = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            note = stz.nextToken();
            if (note.equals("\t"))
                note = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            phenotype = stz.nextToken();
            if (phenotype.equals("\t"))
                phenotype = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            refer = stz.nextToken();
//            if (refer.contains("11283351"))
//                numRefWant++;
            if (stz.nextToken().equals("\t"));
            assert (stz.hasMoreTokens());
            citation = stz.nextToken();
//            System.gc();
            list = new ArrayList<String>();

            if (!g.isNeighbor(bait, hit)){
//                interactList.add(edge);
                g.addEdge(edge, bait, hit);
                edge.setBait(bait);
                edge.setHit(hit);
                edge.setNumEvidence(1);
//                edge.setUserDatum("NumEvidence", (int)1, UserData.SHARED);
//                ewl.setWeight(edge, 1);
                list.add(exper);
                edge.setExperimentList(list);
//                edge.setUserDatum("Experiment",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(curation);
                edge.setCurationList(list);
//                edge.setUserDatum("Curation",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(citation);
                edge.setCitationList(list);
//                edge.setUserDatum("Citation",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(intType);
                edge.setInteractionList(list);
//                edge.setUserDatum("Interaction",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(refer);
                edge.setReferenceList(list);
//                edge.setUserDatum("Reference",list, UserData.SHARED);
                list = new ArrayList<String>();
                list.add(phenotype);
                edge.setPhenotypeList(list);
//                edge.setUserDatum("Phenotype",list, UserData.SHARED);
//
                list = new ArrayList<String>();
                list.add(note);
                edge.setNotesList(list);

                list = new ArrayList<String>();
                list.add(source);
                edge.setSourceList(list);
//                edge.setUserDatum("Note",list, UserData.SHARED);
            } else {
                edge = g.findEdge(bait, hit);
                
//                int oldnum = (Integer)edge.getUserDatum("NumEvidence");
                edge.setNumEvidence(1+edge.getNumEvidence());
//                edge.setUserDatum("NumEvidence", 1+oldnum , UserData.SHARED);
//                ewl.setWeight(edge,1+oldnum);
                edge.getExperimentList().add(exper);
                edge.getCurationList().add(curation);
                edge.getCitationList().add(citation);
                edge.getInteractionList().add(intType);
                edge.getReferenceList().add(refer);
                edge.getPhenotypeList().add(phenotype);
                edge.getNotesList().add(note);
                edge.getSourceList().add(source);
                
//                System.out.println(baitName+" "+hitName+" "+((ArrayList)edge.getUserDatum("Phenotype")));
            }
//            break;
        }
        br.close();
//        System.out.println("contains? "+g.getVertices().contains(debugObj)+" "+debugObj.getName()+" "+debugObj.getStandardName());
        System.out.println("Num nodes: "+g.getVertexCount()+" Num edges: "+g.getEdgeCount());
//        System.out.println(graph.numEdges()+" "+graph.numVertices());
//        System.out.println("Num ref want: "+numRefWant);
    }
    */

    public Graph parseGeneAssoc(File f, Graph graph) throws FileNotFoundException, IOException {
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
//        bioObjSet.clear();
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
            //Ignore GO terms assigned by electronic annotation (IEA evidence code)
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
//        g = graph;
        return putInfoToNodes(graph);
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
    public Graph putInfoToNodes(Graph graph){
        for (Object obj : graph.getVertices()){
            BioObject bioObj = (BioObject)obj;
//            System.out.println(bioObj.getStandardName());
            BioObject enrichedObj = enrichedNameObjMap.get(bioObj.getStandardName());
            if (enrichedObj != null){
                bioObj.setAspectList(enrichedObj.getAspectList());
                bioObj.setName(enrichedObj.getSynonym().get(0));
                bioObj.setDatabaseID(enrichedObj.getDatabaseID());
                bioObj.setDate(enrichedObj.getDate());
                bioObj.setEviCodeList(enrichedObj.getEviCodeList());
                bioObj.setPropTermList(enrichedObj.getPropTermList());
                bioObj.setNotModifierList(enrichedObj.getNotModifierList());
                bioObj.setObjName(enrichedObj.getObjName());
                bioObj.setRefList(enrichedObj.getRefList());
                bioObj.setSynonym(enrichedObj.getSynonym());
                bioObj.setTaxon(enrichedObj.getTaxon());
                bioObj.setType(enrichedObj.getType());
                bioObj.setWithOrFromList(enrichedObj.getWithOrFromList());
            }
        }
        return graph;
    }

     /**
     * Use id of BioObject to print out louvain clustering format file
     * id should be sorted beforehand and run in consecutive order.
     * @param f
     * @param graph
     * @throws java.io.FileNotFoundException
     */
    public void genLouvainFormat(File f, Graph graph) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(f);
        
        edgeSet.addAll(graph.getEdges());
        for (SGDInteraction edge : edgeSet){
//        for (SGDInteraction edge : interactList){
            int first = ((BioObject)edge.getBait()).getId();
            int second = ((BioObject)edge.getHit()).getId();
//            int temp;
//            if (first > second) {
//                temp = first;
//                first = second;
//                second = temp;
//            }
//            pw.println(((BioObject)edge.getBait()).getId()+" "+((BioObject)edge.getHit()).getId()+" "+edge.getNumEvidence());
            pw.println(first+" "+second+" "+edge.getNumEvidence());
        }
        pw.close();
    }
    /**
     * use name of the bioObject to print out tab-limited file
     * @param f
     * @param graph
     * @throws java.io.FileNotFoundException
     */
    public void genTabLimitFormat(File f, Graph graph) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(f);

        edgeSet.addAll(graph.getEdges());
        for (SGDInteraction edge : edgeSet){
//        for (SGDInteraction edge : interactList){
            String first = ((BioObject)edge.getBait()).getStandardName();
            String second = ((BioObject)edge.getHit()).getStandardName();
//            int temp;
//            if (first > second) {
//                temp = first;
//                first = second;
//                second = temp;
//            }
//            pw.println(((BioObject)edge.getBait()).getId()+" "+((BioObject)edge.getHit()).getId()+" "+edge.getNumEvidence());
            pw.println(first+"\t"+second+"\t"+edge.getNumEvidence());
//            pw.println(first+"\t"+second);
        }
        pw.close();
    }
    /**
     * use name of the bioObject to print out tab-limited file with no weight
     * @param f
     * @param graph
     * @throws java.io.FileNotFoundException
     */
    public void genTabLimitNoWeightFormat(File f, Graph graph) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(f);

        edgeSet.addAll(graph.getEdges());
        for (SGDInteraction edge : edgeSet){
//        for (SGDInteraction edge : interactList){
            String first = ((BioObject)edge.getBait()).getStandardName();
            String second = ((BioObject)edge.getHit()).getStandardName();
//            int temp;
//            if (first > second) {
//                temp = first;
//                first = second;
//                second = temp;
//            }
//            pw.println(((BioObject)edge.getBait()).getId()+" "+((BioObject)edge.getHit()).getId()+" "+edge.getNumEvidence());
//            pw.println(first+"\t"+second+"\t"+edge.getNumEvidence());
            pw.println(first+"\t"+second);
        }
        pw.close();
    }
    /**
     * Simple interaction format as used in Cytoscape
     * @param f
     * @param graph
     * @throws java.io.FileNotFoundException
     */
    public void genSIFFormat(File f, Graph graph) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(f);

        edgeSet.addAll(graph.getEdges());
        for (SGDInteraction edge : edgeSet){
//        for (SGDInteraction edge : interactList){
            String first = ((BioObject)edge.getBait()).getStandardName();
            String second = ((BioObject)edge.getHit()).getStandardName();
//            int temp;
//            if (first > second) {
//                temp = first;
//                first = second;
//                second = temp;
//            }
//            pw.println(((BioObject)edge.getBait()).getId()+" "+((BioObject)edge.getHit()).getId()+" "+edge.getNumEvidence());
            pw.println(first+"\t"+"pp"+"\t"+second);
        }
        pw.close();
    }

    public void genModPajekFormat(File f,Graph graph) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(f);
        pw.println("*Vertices\t"+graph.getVertexCount());
        bioObjSet.addAll(graph.getVertices());
        for (BioObject bio : bioObjSet){
            pw.print(bio.getId()+"\t"+bio.getStandardName()+"\t"+bio.getName()+"\t"+bio.getDatabaseID()+"\t"+bio.getType()+"\t");
            int ind = 0;
            for (String st : bio.getPropTermList()){
                if (ind != 0)
                    pw.print("|");
                pw.print(st);
                ind++;
            }
            pw.print("\t");
            ind = 0;
            for (String st : bio.getRefList()){
                if (ind != 0)
                    pw.print("|");
                pw.print(st);
                ind++;
            }
            pw.print("\t");
            ind = 0;
            for (String st : bio.getAspectList()){
                if (ind != 0)
                    pw.print("|");
                pw.print(st);
                ind++;
            }
            pw.println();
        }

        edgeSet.addAll(graph.getEdges());
        pw.println("*Edges\t"+graph.getEdgeCount());
        for (SGDInteraction edge : edgeSet){
            int first = ((BioObject)edge.getBait()).getId();
            int second = ((BioObject)edge.getHit()).getId();
//            int temp;
//            if (first > second) {
//                temp = first;
//                first = second;
//                second = temp;
//            }
//            pw.println(((BioObject)edge.getBait()).getId()+" "+((BioObject)edge.getHit()).getId()+" "+edge.getNumEvidence());
            pw.print(first+"\t"+second+"\t"+edge.getNumEvidence()+"\t");
//            int ind = 0;
//            for (String st : edge.getExperimentList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }
//
//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getInteractionList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }
//
//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getSourceList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }
//
//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getCurationList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }

//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getNotesList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }
//
//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getPhenotypeList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }

//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getReferenceList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }
//
//            pw.print("\t");
//            ind = 0;
//            for (String st : edge.getCitationList()){
//                if (ind != 0)
//                    pw.print("|");
//                pw.print(st);
//                ind++;
//            }
            pw.println();
        }
        pw.close();
    }

    /*
    public void parseGeneAssoc(File f, Graph graph) throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String st = "";
        StringTokenizer stz = null;
        String sgdid = "", symbol = "", not = "", goid = "", ref = "", evid = "", withfrom = "";
        String aspect = "",objname = "", objsyn = "",type = "", taxon = "", date = "";
        // cannot receive any comment lines
//        Set debugSet = new HashSet();
//        ArrayList debugSet = new ArrayList();
        while ((st = br.readLine()) != null) {
            // ! precedes comments
            if (st.startsWith("!"))
                continue;
            stz = new StringTokenizer(st, "\t",true);
            assert (stz.hasMoreTokens());
            stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            sgdid = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            symbol = stz.nextToken();
            if (stz.nextToken().equals("\t"));


            BioObject object = null;
            
//            if (symbol.equals("HAP1")) {
//                int jj = 2;
//            }
            ArrayList vertices = new ArrayList(graph.getVertices());
//            System.out.println(vertices.get(4219));
            for (Object v : vertices) {
                BioObject vertex = (BioObject) v;
//                    if (debugSet.contains(vertex));
////                        System.out.println("replicated vertex id: "+vertex.getId()+" name: "+vertex.getName());
//                    else
//                        debugSet.add(vertex);
//                    if (vertex.getName().equals("YAL011W"))
//                        System.out.println(vertex.getId());
//                    if (vertex.getName().equals("Y0R251C"))
//                        System.out.println("Found 2");
//                if ("HAP1".equals(vertex.getStandardName())) {
//                    int jj = 2;
//                }
                if (vertex.getStandardName().equals(symbol)) {

                    object = vertex;
                    break;
                } else if (vertex.getName().equals(symbol)) {
                    object = vertex;
                    break;
                } else if (vertex.getSynonym().contains(symbol)) {
                    object = vertex;
                    break;
                }

            }
            
            if (object == null) {
                continue;
            }
            
            assert (stz.hasMoreTokens());
            not = stz.nextToken();
            if (not.equals("\t"))
                not = "";
            else{
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
            if (withfrom.equals("\t"))
                withfrom = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            aspect = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            objname = stz.nextToken();
            if (objname.equals("\t"))
                objname = "";
            else{
                stz.nextToken();
            }

            assert (stz.hasMoreTokens());
            objsyn = stz.nextToken();
            if (objsyn.equals("\t"))
                objsyn = "";
            else{
                stz.nextToken();
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

            if (object.getDatabaseID().equals("")){
                object.setDatabaseID(sgdid);
                object.setTaxon(taxon);
                object.setType(type);
                object.setDate(date);
                object.setObjName(objname);
                object.setSynonym(new ArrayList<String>());
                StringTokenizer stn = new StringTokenizer(objsyn, "|");
                while (stn.hasMoreTokens()){
                    object.getSynonym().add(stn.nextToken());
                }
                
            }

            object.getAspectList().add(aspect);
            object.getEviCodeList().add(evid);
            object.getPropTermList().add(goid);
            object.getNotModifierList().add(not);
            object.getRefList().add(ref);
            object.getWithOrFromList().add(withfrom);
        }
        br.close();
        g = graph;
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
    */
    public Graph genBothInt(){
        Graph<BioObject,SGDInteraction> g2 = SparseMultigraph.<BioObject,SGDInteraction>getFactory().create();


//        g2 = (UndirectedSparseGraph) graph.copy();
        ArrayList<String> list = new ArrayList();
        for (SGDInteraction edge : g.getEdges()) {

//            list = ((ArrayList)edge.getUserDatum("Citation"));

            list = edge.getInteractionList();
            Set s = new HashSet();
            s.addAll(list);
            if (s.size() >= 2){
                if (!g2.containsVertex(g.getEndpoints(edge).getFirst()))
                    g2.addVertex(g.getEndpoints(edge).getFirst());
                if (!g2.containsVertex(g.getEndpoints(edge).getSecond()))
                    g2.addVertex(g.getEndpoints(edge).getSecond());
                g2.addEdge(edge,g.getEndpoints(edge).getFirst(),g.getEndpoints(edge).getSecond());
            }

//            if (s.size() != list.size()){
//                System.out.println(s);
//                System.out.println(list);
//            }

        }

//        pw.save(g2, "src/ExpMore2intdata.net",sl,ewl);

        System.out.println("Num edges: "+g2.getEdgeCount()+" Num nodes: "+g2.getVertexCount());
//        System.out.println("Num Ver Del: "+numVerDel);
        return g2;
    }


    public Graph genCurMore2(){
        Graph<BioObject,SGDInteraction> g2 = SparseMultigraph.<BioObject,SGDInteraction>getFactory().create();

        
//        g2 = (UndirectedSparseGraph) graph.copy();
        ArrayList<String> list = new ArrayList();
        for (SGDInteraction edge : g.getEdges()) {
            
//            list = ((ArrayList)edge.getUserDatum("Citation"));
            list = edge.getCurationList();
            Set s = new HashSet();
            s.addAll(list);
            if (s.size() >= 2){
                if (!g2.containsVertex(g.getEndpoints(edge).getFirst()))
                    g2.addVertex(g.getEndpoints(edge).getFirst());
                if (!g2.containsVertex(g.getEndpoints(edge).getSecond()))
                    g2.addVertex(g.getEndpoints(edge).getSecond());
                g2.addEdge(edge,g.getEndpoints(edge).getFirst(),g.getEndpoints(edge).getSecond());
            }

//            if (s.size() != list.size()){
//                System.out.println(s);
//                System.out.println(list);
//            }

        }

//        pw.save(g2, "src/ExpMore2intdata.net",sl,ewl);
        
        System.out.println("Num edges: "+g2.getEdgeCount()+" Num nodes: "+g2.getVertexCount());
//        System.out.println("Num Ver Del: "+numVerDel);
        return g2;
    }

    public Graph genManCur(){
        Graph<BioObject,SGDInteraction> g2 = SparseMultigraph.<BioObject,SGDInteraction>getFactory().create();


//        g2 = (UndirectedSparseGraph) graph.copy();
        ArrayList<String> list = new ArrayList();
        for (SGDInteraction edge : g.getEdges()) {

//            list = ((ArrayList)edge.getUserDatum("Citation"));
            list = edge.getCurationList();
            Set s = new HashSet();
            s.addAll(list);
            if (s.contains("manually curated")){
//            if (s.size() >= 2){
                if (!g2.containsVertex(g.getEndpoints(edge).getFirst()))
                    g2.addVertex(g.getEndpoints(edge).getFirst());
                if (!g2.containsVertex(g.getEndpoints(edge).getSecond()))
                    g2.addVertex(g.getEndpoints(edge).getSecond());
                g2.addEdge(edge,g.getEndpoints(edge).getFirst(),g.getEndpoints(edge).getSecond());
            }

//            if (s.size() != list.size()){
//                System.out.println(s);
//                System.out.println(list);
//            }

        }

//        pw.save(g2, "src/ExpMore2intdata.net",sl,ewl);

        System.out.println("Num edges: "+g2.getEdgeCount()+" Num nodes: "+g2.getVertexCount());
//        System.out.println("Num Ver Del: "+numVerDel);
        return g2;
    }

    public Graph geneRefMore3(){
        Graph<BioObject,SGDInteraction> g2 = SparseMultigraph.<BioObject,SGDInteraction>getFactory().create();

        
//        g2 = (UndirectedSparseGraph) graph.copy();
        ArrayList<String> list = new ArrayList();
        for (SGDInteraction edge : g.getEdges()) {
            
//            list = ((ArrayList)edge.getUserDatum("Citation"));
            list = edge.getReferenceList();
            Set s = new HashSet();
            s.addAll(list);
            if (s.size() >= 3){
                if (!g2.containsVertex(g.getEndpoints(edge).getFirst()))
                    g2.addVertex(g.getEndpoints(edge).getFirst());
                if (!g2.containsVertex(g.getEndpoints(edge).getSecond()))
                    g2.addVertex(g.getEndpoints(edge).getSecond());
                g2.addEdge(edge,g.getEndpoints(edge).getFirst(),g.getEndpoints(edge).getSecond());
            }

//            if (s.size() != list.size()){
//                System.out.println(s);
//                System.out.println(list);
//            }

        }

//        pw.save(g2, "src/ExpMore2intdata.net",sl,ewl);
        
        System.out.println("Num edges: "+g2.getEdgeCount()+" Num nodes: "+g2.getVertexCount());
//        System.out.println("Num Ver Del: "+numVerDel);
        return g2;
    }
    public Graph genExpMore3(){
        
        Graph<BioObject,SGDInteraction> g2 = SparseMultigraph.<BioObject,SGDInteraction>getFactory().create();

        
//        g2 = (UndirectedSparseGraph) graph.copy();
        ArrayList<String> list = new ArrayList();
        for (SGDInteraction edge : g.getEdges()) {
            
//            list = ((ArrayList)edge.getUserDatum("Citation"));
            list = edge.getExperimentList();
            Set s = new HashSet();
            s.addAll(list);
            //suspected to be bug ---> should be !containsVertex
            if (s.size() >= 3){
                if (g2.containsVertex(g.getEndpoints(edge).getFirst()))
                    g2.addVertex(g.getEndpoints(edge).getFirst());
                if (g2.containsVertex(g.getEndpoints(edge).getSecond()))
                    g2.addVertex(g.getEndpoints(edge).getSecond());
                g2.addEdge(edge,g.getEndpoints(edge).getFirst(),g.getEndpoints(edge).getSecond());
            }

//            if (s.size() != list.size()){
//                System.out.println(s);
//                System.out.println(list);
//            }

        }

//        pw.save(g2, "src/ExpMore2intdata.net",sl,ewl);
        
        System.out.println("Num edges: "+g2.getEdgeCount()+" Num nodes: "+g2.getVertexCount());
//        System.out.println("Num Ver Del: "+numVerDel);
        return g2;
    }
    public Graph rearrangeID(Graph<BioObject,SGDInteraction> graph){
        int id = 0;
        for (BioObject vertex : graph.getVertices()){
            vertex.setId(id++);
        }
        return graph;
    }
    public static void main(String[] args) throws IOException {
        SGDDataHandler sgdhd = new SGDDataHandler();
        StopWatch sw = new StopWatch();
//        sgdhd.parseInteractionTab(new File("..\\BlondelAlgorithm\\src\\interaction_data.tab"));
//        sgdhd.parseInteractionTab(new File("../BlondelAlgorithm/src/interaction_data.tab"));
        sw.start();
        sgdhd.parseInteractionTab(new File("src/datasets/interaction_data-new.tab"));
        sw.stop();
        System.out.println("Time used to parse interaction file: "+sw);
        sw.start();
//        Graph graph = sgdhd.genExpMore3();
//        Graph graph = sgdhd.genCurMore2();
//        Graph graph = sgdhd.geneRefMore3();
//        Graph graph = sgdhd.genBothInt();
        Graph graph = sgdhd.genManCur();
//        Graph graph = sgdhd.g;
        sw.stop();
        System.out.println("Time used to filter edges: "+sw);
        
        sw.start();

        graph = sgdhd.rearrangeID(graph);
//        Graph graph = sgdhd.rearrangeID(sgdhd.g);
        sw.stop();
        System.out.println("Time used to rearrange graph id: "+sw);


        sw.start();
        graph = sgdhd.parseGeneAssoc(new File("src/datasets/gene_association.sgd"),graph);
        sw.stop();
        System.out.println("Time used to parse gene assoc: "+sw);

        sw.start();
//        sgdhd.genLouvainFormat(new File("src/datasets/expmore3.txt"),graph);
//        sgdhd.genLouvainFormat(new File("src/datasets/curmore2.txt"),graph);
//        sgdhd.genLouvainFormat(new File("src/datasets/refmore3.txt"),graph);
//        sgdhd.genLouvainFormat(new File("src/datasets/bothint.txt"),graph);
//        sgdhd.genLouvainFormat(new File("src/datasets/allint-apr10.txt"),graph);
        sgdhd.genLouvainFormat(new File("src/datasets/mancur-apr10.txt"),graph);
        sw.stop();
        System.out.println("Time used to gen louvain format: " + sw);

        sw.start();
//        sgdhd.genModPajekFormat(new File("src/datasets/expmore3.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("src/datasets/curmore2.mnet"), graph);
//                sgdhd.genModPajekFormat(new File("src/datasets/refmore3.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("src/datasets/bothint.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("src/datasets/allint-apr10.mnet"),graph);
        sgdhd.genModPajekFormat(new File("src/datasets/mancur-apr10.mnet"),graph);
        sw.stop();
        System.out.println("Time used to gen mod pajek net: " + sw);

        sw.start();
//        sgdhd.genSIFFormat(new File("bothint.sif"),graph);
//        sgdhd.genSIFFormat(new File("src/datasets/curmore2.sif"),graph);
//        sgdhd.genSIFFormat(new File("src/datasets/expmore3.sif"),graph);
//        sgdhd.genSIFFormat(new File("src/datasets/refmore3.sif"),graph);
//        sgdhd.genSIFFormat(new File("src/datasets/bothint.sif"),graph);
//        sgdhd.genSIFFormat(new File("src/datasets/allint-apr10.sif"),graph);
        sgdhd.genSIFFormat(new File("src/datasets/mancur-apr10.sif"),graph);
        sw.stop();
        System.out.println("Time used to gen sif: "+sw);

        sw.start();
//        sgdhd.genTabLimitFormat(new File("bothint.tab"),graph);
//        sgdhd.genTabLimitFormat(new File("src/datasets/curmore2.tab"),graph);
//        sgdhd.genTabLimitFormat(new File("src/datasets/expmore3.tab"),graph);
//        sgdhd.genTabLimitFormat(new File("src/datasets/refmore3.tab"),graph);
//        sgdhd.genTabLimitFormat(new File("src/datasets/bothint.tab"),graph);
//        sgdhd.genTabLimitFormat(new File("src/datasets/allint-apr10.tab"),graph);
        sgdhd.genTabLimitFormat(new File("src/datasets/mancur-apr10.tab"),graph);
        sw.stop();
        System.out.println("Time used to gen tab limited: "+sw);

//        sgdhd.genTabLimitNoWeightFormat(new File("src/datasets/curmore2-noweight.txt"),graph);
//        sgdhd.genTabLimitNoWeightFormat(new File("src/datasets/expmore3-noweight.txt"),graph);
//        sgdhd.genTabLimitNoWeightFormat(new File("src/datasets/refmore3-noweight.txt"),graph);
//        sgdhd.genTabLimitNoWeightFormat(new File("src/datasets/bothint-noweight.txt"),graph);
//        sgdhd.genTabLimitNoWeightFormat(new File("src/datasets/allint-apr10-noweight.txt"),graph);
        sgdhd.genTabLimitNoWeightFormat(new File("src/datasets/mancur-apr10-noweight.txt"),graph);
    }
}
