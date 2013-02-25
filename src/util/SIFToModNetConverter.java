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
import java.util.Comparator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import objects.BioObject;
import objects.SGDInteraction;

/**
 *
 * @author Knacky
 */
public class SIFToModNetConverter {
    private int iden = 0;
    private Graph<BioObject,SGDInteraction> g;
//    ArrayList<SGDInteraction> interactList = new ArrayList<SGDInteraction>();
    static String fileToPrint = "src/temp.net";
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


    public void parseSIF(File f) throws FileNotFoundException, IOException{
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

        while ((st = br.readLine()) != null) {
            stz = new StringTokenizer(st, "\t",true);

            assert (stz.hasMoreTokens());
            baitName = stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            stz.nextToken();
            if (stz.nextToken().equals("\t"));

            assert (stz.hasMoreTokens());
            hitName = stz.nextToken();
            


//            System.out.println(hitStName);
            BioObject bait = null; //new BioObject();
            BioObject hit = null;//new BioObject();

//            System.out.println(vertices);
//            if (vertices.indexOf(bait) > -1)
//                bait = (BioObject) vertices.get(vertices.indexOf(bait));
//            if (vertices.indexOf(hit) > -1)
//                hit = (BioObject) vertices.get(vertices.indexOf(hit));
            boolean already = false;
            boolean already2 = false;
            for (Object v : g.getVertices()){
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
                bait.setStandardName("");
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
                hit.setStandardName("");
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
//            String edge = new String(bait+"\t:\t"+hit);
            SGDInteraction edge = new SGDInteraction();
            edge.setBait(bait); edge.setHit(hit);
            edge.setNumEvidence(1);

            if (!g.isNeighbor(bait, hit)){
//                interactList.add(edge);
                g.addEdge(edge, bait, hit);

//                edge.setUserDatum("Note",list, UserData.SHARED);
            }
        }
        br.close();
//        System.out.println("contains? "+g.getVertices().contains(debugObj)+" "+debugObj.getName()+" "+debugObj.getStandardName());
        System.out.println("Num nodes: "+g.getVertexCount()+" Num edges: "+g.getEdgeCount());
//        System.out.println(graph.numEdges()+" "+graph.numVertices());
//        System.out.println("Num ref want: "+numRefWant);
    }

    public Graph parseGeneAssoc(File f, Graph graph) throws FileNotFoundException, IOException{
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
            /* ! precedes comments */
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
//            ArrayList vertices = new ArrayList(graph.getVertices());
//            System.out.println(vertices.get(4219));
            


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

            ArrayList listOfSyn = new ArrayList();
            StringTokenizer stn = new StringTokenizer(objsyn, "|");
            while (stn.hasMoreTokens()){
//                String sss = stn.nextToken();
                listOfSyn.add(stn.nextToken());

            }

            for (Object v : graph.getVertices()) {
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
                } else if (listOfSyn.contains(vertex.getName())){
                    object = vertex;
                    break;
                }

            }

            if (object == null) {
                continue;
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
                object.setStandardName(symbol);
                object.setDatabaseID(sgdid);
                object.setTaxon(taxon);
                object.setType(type);
                object.setDate(date);
                object.setObjName(objname);
                object.setSynonym(new ArrayList<String>());
                object.getSynonym().addAll(listOfSyn);

            }

            object.getAspectList().add(aspect);
            object.getEviCodeList().add(evid);
            object.getPropTermList().add(goid);
            object.getNotModifierList().add(not);
            object.getRefList().add(ref);
            object.getWithOrFromList().add(withfrom);
        }
        br.close();
//        g = graph;
        return graph;
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

    public void genModPajekFormat(File f,Graph graph) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(f);
        pw.println("*Vertices\t"+graph.getVertexCount());
        bioObjSet.addAll(graph.getVertices());
        edgeSet.addAll(graph.getEdges());
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

//        edgeSet.addAll(graph.getEdges());
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

    public Graph rearrangeID(Graph<BioObject,SGDInteraction> graph){
        int id = 0;
        for (BioObject vertex : graph.getVertices()){
            vertex.setId(id++);
        }
        return graph;
    }

    public static void main(String[] args) throws IOException {
        SIFToModNetConverter sifcon = new SIFToModNetConverter();
        StopWatch sw = new StopWatch();
//        sgdhd.parseInteractionTab(new File("..\\BlondelAlgorithm\\src\\interaction_data.tab"));
//        sgdhd.parseInteractionTab(new File("../BlondelAlgorithm/src/interaction_data.tab"));
        sw.start();
        sifcon.parseSIF(new File("src/datasets/Ito_2001.sif"));
        sw.stop();
        System.out.println("stop watch "+sw);
        sifcon.g = sifcon.rearrangeID(sifcon.g);
        sw.start();
        sifcon.g = sifcon.parseGeneAssoc(new File("src/datasets/gene_association.sgd"),sifcon.g);
        sw.stop();
        System.out.println("stop watch "+sw);
        sw.start();
//        sgdhd.genModPajekFormat(new File("expmore3.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("curmore2.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("refmore3.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("bothint.mnet"),graph);
//        sgdhd.genModPajekFormat(new File("allint-latest.mnet"),graph);
        sifcon.genModPajekFormat(new File("src/datasets/ito2001.mnet"), sifcon.g);
        sw.stop();
        System.out.println("Time used to gen mod pajek net: "+sw);
    }

}
