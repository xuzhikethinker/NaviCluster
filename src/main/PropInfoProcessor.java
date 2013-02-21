/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import kmean.DataVector;
import kmean.DataCluster;
import kmean.KMeanClusterer;
import util.StopWatch;

/**
 * 2011/04/01 - Knack   - Update weighing schemes
 * 2010/06/01 - Knack   - Change names related to GO to property-information-related names
 * 2009/11/04 - Knack   - Modify PropInfoProcessor class to parse new ontology_file.txt to retrieve parents of each term
 * @author Knacky
 */
public class PropInfoProcessor {
//    static OBOSession session;

//    private static Set<PropertyTerm> setOfPropTerms = new HashSet<PropertyTerm>();
    Map<Object, Map<PropertyTerm, Double>> clusterNumMemMap = new HashMap<Object, Map<PropertyTerm, Double>>();
    /**
     * Use clusterScoreMap for storing information for both bioNodes and cluster
     */
    Map<Object, Map<PropertyTerm, Double>> clusterScoreMap = new HashMap<Object, Map<PropertyTerm, Double>>();
    private static SortedMap<String, NameSpace> namespaceMap = new TreeMap<String, NameSpace>(new Comparator<String>() {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    });
    public static Map<NameSpace, Double> weightMap = new HashMap();
//    public double ccWeight = 1;
//    public double mfWeight = 1;
//    public double bpWeight = 1;
    static Map<String, PropertyTerm> propTermsMap = new HashMap<String, PropertyTerm>();
    Map<Object, DataVector> nodesPropVectorMap = new HashMap<Object, DataVector>();
    Map<PropertyTerm, Set<String>> ancestorsMap = new HashMap<PropertyTerm, Set<String>>();

    public static void setWeightMap(Map map) {
        weightMap = map;
    }

    public static Map<NameSpace, Double> getWeightMap() {
        return weightMap;
    }

    public static SortedMap<String, NameSpace> getNamespaceMap() {
        return namespaceMap;
    }

    public static void setNamespaceMap(SortedMap<String, NameSpace> namespaceMap) {
        PropInfoProcessor.namespaceMap = namespaceMap;
    }

    /**
     * Set colors for namespaces.
     * 360 degrees of hue will be divided by the number of namespaces, so that each color hue (angle) is set to be farthest from another one.
     * Brightness will be contrasted to the againstColor; if againstColor is dark, the labels will be light and vice versa.
     * Saturation is the same as againstColor
     */
    public static void setColorsForNamespaces(Color againstColor) {
        int numNamespace = namespaceMap.size();
        float[] hsb = Color.RGBtoHSB(againstColor.getRed(), againstColor.getGreen(), againstColor.getBlue(), null);
        int hueInDegree = (int) (hsb[0] * 360);
        float brightnessToBeUsed = hsb[2];
        if (brightnessToBeUsed > 0.37) {
            brightnessToBeUsed -= 0.37;
        } else {
            brightnessToBeUsed += 0.37;
        }
//        System.out.println(againstColor);
//        System.out.println(hsb[0]+" "+hsb[1]+" "+hsb[2]);
//        System.out.println(numNamespace+" "+hueInDegree+" "+brightnessToBeUsed);
        // count againstColor as 1, so divided by number of namespaces plus 1
        int step = 360 / (numNamespace + 1), ind = 1;
        for (Entry<String, NameSpace> entry : namespaceMap.entrySet()) {

//            float hueToBeUsed = (float)((ind*step+hueInDegree)%360)/360;
            float hueToBeUsed = (float) ((hueInDegree - ind * step) % 360) / 360;
//            System.out.println(step+" "+ind*step+" "+hueToBeUsed);
//            System.out.println("Color "+hueToBeUsed+" "+ hsb[1]+" "+ brightnessToBeUsed);
            int rgb = Color.HSBtoRGB(hueToBeUsed, hsb[1], brightnessToBeUsed);
            entry.getValue().setColor(new Color(rgb));
            ind++;
        }
    }

    /**
     * DEPRECATED ALREADY
     * Load GO Terms from text file
     * Text file stores GO ids, names, depths, etc.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void loadGOTermsMap() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/datasets/GO_with_depth.txt"))));
        String st = "";
        StringTokenizer stn = null;
        int count = 0;
        namespaceMap.clear();
        propTermsMap.clear();
        while ((st = br.readLine()) != null) {
            stn = new StringTokenizer(st, "\t");
            String id = stn.nextToken();
            String name = stn.nextToken();
            String namespace = stn.nextToken();
//            System.out.println(id+ " "+name+" "+namespace);
            int depth = Integer.parseInt(stn.nextToken());
            PropertyTerm term = new PropertyTerm(id, depth);
//            System.out.println(NameSpace.valueOf(namespace));
            term.setName(name);
            NameSpace ns = namespaceMap.get(namespace);
            if (ns == null) {
                ns = new NameSpace(namespace);
                namespaceMap.put(namespace, ns);
            }
//            term.setNamespace(NameSpace.valueOf(namespace));
            term.setNamespace(ns);
            propTermsMap.put(id, term);
            count++;
        }
        System.out.println("count " + count);

    }

    /**
     * Load Property Terms and their information from ontology text file
     * Text file stores ontology ids, names, categories (namespaces), weights (depths), and parents.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void loadPropInfoFile(File file) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String st = "";
        StringTokenizer stn = null;
        int count = 0;
        namespaceMap.clear();
        propTermsMap.clear();
        while ((st = br.readLine()) != null) {
            stn = new StringTokenizer(st, "\t");
            String id = stn.nextToken();
            String name = stn.nextToken();

            String shortName = stn.nextToken();
            String namespace = stn.nextToken();
//            System.out.println(id+ " "+name+" "+namespace);
            int depth = Integer.parseInt(stn.nextToken());
            PropertyTerm term = new PropertyTerm(id, depth);
//            System.out.println(NameSpace.valueOf(namespace));
            term.setName(name);
            NameSpace ns = namespaceMap.get(namespace);
            if (ns == null) {
                ns = new NameSpace(namespace);
                namespaceMap.put(namespace, ns);
            }
//            term.setNamespace(NameSpace.getNameSpace(namespace));
            term.setNamespace(ns);
            term.setShortName(shortName);
            if (stn.hasMoreTokens()) {
                String parentList = stn.nextToken();
                StringTokenizer stk = new StringTokenizer(parentList, "|");
                while (stk.hasMoreTokens()) {
                    term.addParent(stk.nextToken().trim());
                }

            }
            propTermsMap.put(id, term);
            count++;
        }
        System.out.println("number of ontology terms: " + count);

    }

    public Set<String> getAllAncestors(PropertyTerm goterm) {
        Set<String> ancestors = new HashSet<String>();

        /* Collect only parents (parents) that have is_a or part_of relationships
         * with the the oboclass are valid.
         */
//        for (String isaParent : goterm.getIsaSet()){
//            ancestors.add(isaParent);
//        }
//        for (Relationship relParent : goterm.getRelationshipSet()){
//            if (relParent.getType().equalsIgnoreCase("part_of")){
//                ancestors.add(relParent.getTarget());
//            }
//        }
        ancestors.addAll(goterm.getParents());
        Set<String> tempAncestors = new HashSet<String>(ancestors);
        for (String term : tempAncestors) {
            PropertyTerm tempTerm = propTermsMap.get(term);
            Set<String> requiredAncestors = ancestorsMap.get(tempTerm);
            if (requiredAncestors == null) {
                requiredAncestors = getAllAncestors(tempTerm);
                ancestorsMap.put(tempTerm, requiredAncestors);
            }
            ancestors.addAll(requiredAncestors);
        }
        return ancestors;
    }

    ////DEBUG////
//    public void printNSMap() {
//        try {
//            PrintWriter pw = new PrintWriter(new File("debug-navicluster-nsmap"));
//            for (NameSpace ns : namespaceMap.values()) {
//                pw.println(ns.getName() + "\t" + ns.getWeight());
//            }
//            pw.close();
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    ////DEBUG////
//    public void printComToFlat() {
//        try {
//            PrintWriter pw = new PrintWriter(new File("debug-navicluster-propmap"));
//            Set<Entry<String, PropertyTerm>> sortedMap = new TreeSet<Entry<String, PropertyTerm>>(new Comparator() {
//
//                public int compare(Object o1, Object o2) {
//                    Entry<String, PropertyTerm> e1 = (Entry<String, PropertyTerm>) o1;
//                    Entry<String, PropertyTerm> e2 = (Entry<String, PropertyTerm>) o2;
//                    return e1.getKey().compareTo(e2.getKey());
////                            }
//                }
//            });
//            sortedMap.addAll(propTermsMap.entrySet());
//            for (Entry<String, PropertyTerm> entry : sortedMap) {
//                PropertyTerm term = entry.getValue();
//                pw.println(term.getId() + "\t" + term.getWeight());
//            }
//            pw.close();
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    /**
     * Create mapping of nodes into property term-score map
     * property term-score map is mapping from property terms into score of that terms
     * @param cSet
     * @return map of node into property term-score map
     */
    public Map<Object, Map<PropertyTerm, Double>> populatePropTerms(Set cSet) {
        ////DEBUG////
//        printNSMap();
//        printComToFlat();
        /////////////
        boolean foundNullTerm = false;
        Set<String> setOfNullTerm = new HashSet<String>();
        clusterScoreMap =
                new HashMap<Object, Map<PropertyTerm, Double>>();

        Map<PropertyTerm, Double> numMembersMap = new HashMap<PropertyTerm, Double>();
        Map<PropertyTerm, Double> scoreMap = new HashMap<PropertyTerm, Double>();
//        setOfPropTerms.add(new PropertyTerm("GO:0008150", 1));
//        setOfPropTerms.add(new PropertyTerm("GO:0005575", 1));
//        setOfPropTerms.add(new PropertyTerm("GO:0003674", 1));
        /* Assume that cSet purely contains either bioObject or set. */
        for (Object obj : cSet) {

            boolean isBioObjectSet = false;
            Set cluster = null;
            BioObject bioNode = null;
//            if (isBioObjectSet) {
            if (obj instanceof BioObject) {
                bioNode = (BioObject) obj;
                isBioObjectSet = true;
            } else if (obj instanceof Set) {
                cluster = (Set) obj;
            } else {
                System.out.println("Error neither BioObj nor Set");
            }

            numMembersMap.clear();
            scoreMap.clear();

            /* if cSet is a set of bioObject */
            if (isBioObjectSet) {

                Set<String> goidset = new HashSet<String>();
                /* add all ancestors of property terms specified in the GoIdlist to expand annotating term set
                 */
//                for (String goid : bioNode.getPropTermList()) {
//                    goidset.add(goid);
//                    Set<String> ancestors = ancestorsMap.get(goid);
//                    if (ancestors == null) {
//                        ancestors = getAllAncestors(propTermsMap.get(goid));
//                        ancestorsMap.put(propTermsMap.get(goid), ancestors);
//                    }
//                    goidset.addAll(ancestors);
//                }
                goidset.addAll(bioNode.getPropTermList());
                for (String id : goidset) {

//                    PropertyTerm term = PropertyTerm.findTerm(scoreMap.keySet(), id);
                    PropertyTerm term = propTermsMap.get(id);

                    if (term == null) {
                        foundNullTerm = true;
                        setOfNullTerm.add(id);
//                        System.out.println("found null term !!!" + id);
                    } else {
                        if ((term.getWeight() == 0) || (namespaceMap.get(term.getNamespace().getName()).getWeight() == 0)) {
                            continue;
                        }
//                    if (term == null) {

//                        term = new PropertyTerm(id, propTermsMap.get(id).getWeight());
//                        term.setNamespace(propTermsMap.get(id).getNamespace());
                        /* for bioObject, for each property term, the score is the depth of that term */
//                        scoreMap.put(term, (double) 1 * Math.pow(term.getWeight(), 1) * weightMap.get(term.getNamespace()));
                        scoreMap.put(term, (double) 1 * term.getWeight() * namespaceMap.get(term.getNamespace().getName()).getWeight());
//                        System.out.println("term dep "+term.getWeight());
//                    }
                    }
                    /* There shouldn't be duplicate of a specific property term in one BioObject */
                }

            } else {
                //DEBUG
                boolean printMode = false;
                PrintWriter pw = null;
                /* if cSet is a set of cluster */
                for (Object node : cluster) {
                    bioNode = (BioObject) node;
//                    //DEBUG
//                    if (bioNode.getName().equals("CSN9")){
//                        printMode = true;
//                        try {
//                            pw = new PrintWriter(new File("debug-final"));
//                        } catch (FileNotFoundException ex) {
//                            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }

//                System.out.println("Node "+bioNode);
                    Set<String> propIdSet = new HashSet<String>();
                    /* add all ancestors of property terms specified in the GoIdlist to expand annotating term set
                     */
//                    for (String goid : bioNode.getPropTermList()) {
//                        goidset.add(goid);
//                        Set<String> ancestors = ancestorsMap.get(goid);
//                        if (ancestors == null) {
//                            ancestors = getAllAncestors(propTermsMap.get(goid));
//                            ancestorsMap.put(propTermsMap.get(goid), ancestors);
//                        }
//                        goidset.addAll(ancestors);
//                    }
                    propIdSet.addAll(bioNode.getPropTermList());
                    for (String id : propIdSet) {

                        PropertyTerm term = propTermsMap.get(id);

                        if (term == null) {
                            foundNullTerm = true;
                            setOfNullTerm.add(id);
//                            System.out.println("Find null term: " + id);
                        } else {
                            if ((term.getWeight() == 0) || (namespaceMap.get(term.getNamespace().getName()).getWeight() == 0)) {
                                continue;
                            }

                            if (!scoreMap.containsKey(propTermsMap.get(id))) {
//                            scoreMap.put(term, (double) 1 / cluster.size() * term.getWeight()*weightMap.get(term.getNamespace()));
//                            scoreMap.put(term, (double) 1 * term.getWeight()*weightMap.get(term.getNamespace())*10);
//                            scoreMap.put(term, (double) 1 * Math.pow(term.getWeight(), 1) * weightMap.get(term.getNamespace()));
                                scoreMap.put(term, (Double) 1.0 / cluster.size() * term.getWeight() * namespaceMap.get(term.getNamespace().getName()).getWeight());
                            } else {
//                            scoreMap.put(term, (Double) scoreMap.get(term) + ((double) 1 * term.getWeight())*weightMap.get(term.getNamespace())*10);
//                            scoreMap.put(term, (Double) scoreMap.get(term) + ((double) 1 * Math.pow(term.getWeight(), 1)) * weightMap.get(term.getNamespace()));
                                scoreMap.put(term, (Double) scoreMap.get(term) + 1.0 / cluster.size() * term.getWeight() * namespaceMap.get(term.getNamespace().getName()).getWeight());
                            }
//                            //DEBUG
//                                if (printMode)
//                                {
//                                   pw.println(term.getName()+"\t"+scoreMap.get(term));
//                                }
                        }
                    }

                }
//                //DEUBG
//                if (printMode)
//                    pw.close();

            }
//            double normalizedFactor = 0.0;
            /* No need to normalize scoreMap here, will be handled in K-Means clusterer */
//            for (Entry<PropertyTerm, Double> entry : scoreMap.entrySet()) {
//                normalizedFactor += entry.getValue() * entry.getValue();
//            }
//            normalizedFactor = Math.sqrt(normalizedFactor);
//            for (PropertyTerm key : scoreMap.keySet()) {
//                scoreMap.put(key, scoreMap.get(key) / normalizedFactor);
//
//            }

//            System.out.println("Stat for cluster");
//            System.out.println("Size: " + cluster.size());

//            Map<PropertyTerm, Double> map = new HashMap<PropertyTerm, Double>(numMembersMap);
            Map<PropertyTerm, Double> map = new HashMap<PropertyTerm, Double>(scoreMap);
//            clusterNumMemMap.put(cluster, map);
//            System.out.println("numberMap size: "+numMembersMap.size());
//            map.clear();
//            map = new HashMap<PropertyTerm, Double>(scoreMap);

            if (isBioObjectSet) {
//              clusterScoreMap.remove(cluster);
                clusterScoreMap.put(bioNode, map);
            } else {
                clusterScoreMap.put(cluster, map);
            }

        }

//        Logger logger = Logger.getLogger("NaviCluster");
//            FileHandler fh;
//            try {
//                fh = new FileHandler("output-log", true);
//                // This block configure the logger with handler and formatter
//                logger.addHandler(fh);
//                logger.setLevel(Level.ALL);
//                SimpleFormatter formatter = new SimpleFormatter();
//                fh.setFormatter(formatter);
//
//                // the following statement is used to log any messages   
//                logger.log(Level.WARNING, "My first log");
//            } catch (IOException ex) {
//                Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SecurityException ex) {
//                Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
        if (foundNullTerm) {
            MainApp.pw.println("=== Null Term List ===");
            for (String id : setOfNullTerm) {
                MainApp.pw.print(id + "\t");
            }
            MainApp.pw.println("\n======================");
            JOptionPane.showMessageDialog(null, "Some of the property terms annotated to the nodes of the current network disappear from the property information file.\n"
                    + "The network was clustered using other available terms, but the result might not be reliable.\n"
                    + "Please check your network files and the property information file, and run NaviCluster again. (Please see the log file for the mismatched terms) ", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        System.out.println("clusterScoreMap size: " + clusterScoreMap.size());

        /// DEBUG ///
//        printClusterScoreMap(clusterScoreMap);
        /////////////

        return clusterScoreMap;
    }

    ////DEBUG////
    public void printClusterScoreMap(Map<Object, Map<PropertyTerm, Double>> clusterScoreMap) {
        try {
            PrintWriter pw = new PrintWriter(new File("debug-navicluster-clusterscoremap"));
            Set<Entry<Object, Map<PropertyTerm, Double>>> sortedMap1 = new TreeSet<Entry<Object, Map<PropertyTerm, Double>>>(new Comparator() {

                public int compare(Object o1, Object o2) {
                    Entry<Object, Map<PropertyTerm, Double>> e1 = (Entry<Object, Map<PropertyTerm, Double>>) o1;
                    Entry<Object, Map<PropertyTerm, Double>> e2 = (Entry<Object, Map<PropertyTerm, Double>>) o2;
                    if (((Set) e1.getKey()).size() >= ((Set) e2.getKey()).size()) {
                        return -1;
                    } else if (((Set) e1.getKey()).size() < ((Set) e2.getKey()).size()) {
                        return 1;
                    }
//                            else {
//                                return e1.getKey().getName().compareTo(e2.getKey().getName());
//                            }
//                            else {
                    return 0;
//                            }
                }
            });
            sortedMap1.addAll(clusterScoreMap.entrySet());
            for (Entry<Object, Map<PropertyTerm, Double>> entry : sortedMap1) {
                pw.println("Node: " + entry.getKey());
                Map<PropertyTerm, Double> scoreMap = clusterScoreMap.get(entry.getKey());
                Set<Entry<PropertyTerm, Double>> sortedMap = new TreeSet<Entry<PropertyTerm, Double>>(new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Entry<PropertyTerm, Double> e1 = (Entry<PropertyTerm, Double>) o1;
                        Entry<PropertyTerm, Double> e2 = (Entry<PropertyTerm, Double>) o2;
                        if (e1.getValue() > e2.getValue()) {
                            return -1;
                        } else if (e1.getValue() < e2.getValue()) {
                            return 1;
                        } else {
                            return e1.getKey().getName().compareTo(e2.getKey().getName());
                        }
//                            else {
//                            return 0;
//                            }
                    }
                });
                sortedMap.addAll(scoreMap.entrySet());
                int i = 0;
                for (Entry<PropertyTerm, Double> entry1 : sortedMap) {
                    PropertyTerm term = entry1.getKey();
                    pw.println("Term: " + term.getId() + " Score: " + scoreMap.get(term));
                    i++;
                    if (i == 10) {
                        break;
                    }

                }
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Assume that populatePropTerms is already called.
     * Just put clusterScoreMap to mapping of cluster into datavector
     * Do not clear nodesPropVectorMap for sake of reusability with nodes of many levels
     * @param cSet
     * @return nodesPropVectorMap
     */
    public Map<Object, DataVector> getNodesPropVectorMapBeforeCluster(Set cSet) {

        for (Object obj : cSet) {
            if (clusterScoreMap.get(obj) == null) {
                System.out.println("Vertex : " + obj + " has no clusterScoreMap");
            }
            DataVector vec = new DataVector(clusterScoreMap.get(obj));
            vec.nodeRef = obj;
            nodesPropVectorMap.put(obj, vec);
        }
        System.out.println(nodesPropVectorMap.size());
        return nodesPropVectorMap;
    }

    /**
     * For cSet of BioObject only
     * Assume that for BioObject set, cSet is the same as comNodeCSet
     * @param cSet
     * @param numCluster
     * @return
     */
    public DataCluster[] preCluster(Set cSet, int numCluster) {
        KMeanClusterer kCluster = new KMeanClusterer(numCluster);

//        Set[] arrCluster = cSet.toArray(new Set[1]);
        Object[] arrCluster = cSet.toArray(new Object[1]);

        for (int i = 0; i < arrCluster.length; i++) {

            Map<PropertyTerm, Double> scoreMap;
            Map<String, Double> stringScoreMap;
            Map map = clusterScoreMap.get(arrCluster[i]);
            if (map == null) {
                System.out.println("NULL++++++++");
            }
            scoreMap = new HashMap<PropertyTerm, Double>(map);
            stringScoreMap = new HashMap<String, Double>();

//            int ind = 0;
//            for (Entry<PropertyTerm, Double> entry : scoreMap.entrySet()) {
//
//                stringScoreMap.put(entry.getKey().getId(), entry.getValue());
//                ind++;
//            }

            DataVector vec = new DataVector(map);

            vec.nodeRef = arrCluster[i];
            /* vec.comNodeClusterRef has no special meaning in this case */
            vec.comNodeClusterRef = arrCluster[i];

            kCluster.dataVectorList.add(vec);

        }
        kCluster.assignKPoint();
        StopWatch sw = new StopWatch();
        sw.start();
        kCluster.cluster();
        sw.stop();
        System.out.println("Time used for clustering: " + sw);

        return kCluster.getClusterList();

    }

//    public void printComNodeCSet(Set[] comNodeCSetArr) {
//        try {
//            PrintWriter pw = new PrintWriter(new File("debug-comnodecset"));
//            for (int i = 0; i < comNodeCSetArr.length; i++) {
//                pw.println(comNodeCSetArr[i]);
//            }
//            pw.close();
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void printDV(DataCluster[] list) {
        try {
            PrintWriter pw = new PrintWriter(new File("debug-dv"));
            for (DataCluster dc : list) {
                pw.println("dc----");
                for (DataVector dv : dc.getMembers()) {
                    pw.println("dv size " + ((Set) dv.nodeRef).size());
                }
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////DEBUG////
//    public void printComToFlat(Map<Set,Set> comNodeClustToFlatClust) {
//        try {
//            PrintWriter pw = new PrintWriter(new File("debug-navicluster-comtoflat"));
//            Set<Entry<Set, Set>> sortedMap = new TreeSet<Entry<Set, Set>>(new Comparator() {
//
//                @Override
//                public int compare(Object o1, Object o2) {
//                    Entry<Set, Set> e1 = (Entry<Set, Set>) o1;
//                    Entry<Set, Set> e2 = (Entry<Set, Set>) o2;
//                    if (e1.getValue().size() > e2.getValue().size()) {
//                        return -1;
//                    } else if (e1.getValue().size() < e2.getValue().size()) {
//                        return 1;
//                    } else {
//                        if (e1.getKey().size() >= e2.getKey().size()) {
//                            return -1;
//                        } else if (e1.getKey().size() < e2.getKey().size()) {
//                            return 1;
//                        }
//                    }
//                    return 0;
//                }
//            });
//            sortedMap.addAll(comNodeClustToFlatClust.entrySet());
//            for (Entry<Set, Set> entry : sortedMap) {
//                
//                pw.println(entry.getValue().size() + "\t" + entry.getKey().size());
//            }
//            pw.close();
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    ////DEBUG
    public void printDataVecList(ArrayList<DataVector> list) {
        try {
            PrintWriter pw = new PrintWriter(new File("debug-veclist"));
            for (DataVector dv : list) {
                pw.println("dv size " + ((Set) dv.nodeRef).size());
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //////DEBUG////
    public void printSortedCSet(Set<Set> cSet) {
        try {
            PrintWriter pw = new PrintWriter(new File("debug-navicluster-sortedCSetinprop"));
            Set<Set> sortedSet = new TreeSet<Set>(new Comparator<Set>() {

                @Override
                public int compare(Set t, Set t1) {
                    if (t.size() <= t1.size()) {
                        return 1;
//                } else if (o1.getKey().size() == o2.getKey().size()) {
//                    return 0;
                    } else {
                        return -1;
                    }
                    
                }
            });
            sortedSet.addAll(cSet);
            for (Set set : sortedSet) {
                pw.println(set.size());
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * For cSet of clusters
     * @param comNodeClustToFlatClust
     * @param comNodeCSet
     * @param cSet
     * @param numCluster
     * @return
     */
    public DataCluster[] preCluster(Map<Set, Set> comNodeClustToFlatClust, Set<Set> comNodeCSet, Set<Set> cSet, int numCluster) {
        KMeanClusterer kCluster = new KMeanClusterer(numCluster);

        Set[] arrCluster = comNodeCSet.toArray(new Set[1]);
        ///DEBUG
//        printSortedCSet(cSet);
//        printComNodeCSet(arrCluster);
        Set<DataVector> sortedVecSet = new TreeSet<DataVector>(new Comparator() {

            @Override
            public int compare(Object t1, Object t2) {
                Set flatClust1 = (Set) ((DataVector) t1).nodeRef;
                Set flatClust2 = (Set) ((DataVector) t2).nodeRef;
                if (flatClust1.size() < flatClust2.size()) {
                    return -1;
                } else if (flatClust1.size() > flatClust2.size()) {
                    return 1;
                } else {
                    Comparator cmp = new Comparator() {

                        @Override
                        public int compare(Object t1, Object t2) {
                            return t1.toString().compareTo(t2.toString());
                        }
                    };
                    Set clusterSortedSet1 = new TreeSet(cmp);
                    Set clusterSortedSet2 = new TreeSet(cmp);
                    clusterSortedSet1.addAll(flatClust1);
                    clusterSortedSet2.addAll(flatClust2);
                    return clusterSortedSet1.toArray()[0].toString().compareTo(clusterSortedSet2.toArray()[0].toString());
                }
            }
        });
        for (int i = 0; i < arrCluster.length; i++) {

            Map<PropertyTerm, Double> scoreMap;
            Map<String, Double> stringScoreMap;
            Set flatClust = comNodeClustToFlatClust.get(arrCluster[i]);
            Map<PropertyTerm, Double> mapOfComNodeClustI = clusterScoreMap.get(flatClust);
            if (mapOfComNodeClustI == null) {
                System.out.println("Error! cannot find entry for " + flatClust);
            }
            scoreMap = new HashMap<PropertyTerm, Double>(mapOfComNodeClustI);
            stringScoreMap = new HashMap<String, Double>();

            int ind = 0;
            for (Entry<PropertyTerm, Double> entry : scoreMap.entrySet()) {
//                System.out.println(entry.getKey());
                stringScoreMap.put(entry.getKey().getId(), entry.getValue());
                ind++;
            }

            DataVector vec = new DataVector(mapOfComNodeClustI);

            vec.nodeRef = flatClust;
            vec.comNodeClusterRef = arrCluster[i];

//            kCluster.dataVectorList.add(vec);
            sortedVecSet.add(vec);

        }
        for (DataVector vec : sortedVecSet) {
            kCluster.dataVectorList.add(vec);
        }
        ////DEBUG
//        printDataVecList(kCluster.dataVectorList);
        StopWatch sw = new StopWatch();
        sw.start();
        kCluster.assignKPoint();
        ////DEBUG
//        printDV(kCluster.getClusterList());

        sw.stop();
        System.out.println("Time used for assigning K points: " + sw);

        sw.start();
        kCluster.cluster();

//        for (DataCluster dc : kCluster.getClusterList()){
//            System.out.println("dc----");
//            for (DataVector dv : dc.getMembers()){
//                System.out.println("dv size "+((Set)dv.nodeRef).size());
//            }
//        }
        sw.stop();
        System.out.println("Time used for clustering: " + sw);

        return kCluster.getClusterList();

    }

//    /**
//     * Copied and modified version of getSession method described in gene ontology website
//     * This is used for parse obo flat file (text file describing property terms)
//     * @param path
//     * @return
//     * @throws java.io.IOException
//     * @throws org.geneontology.oboedit.dataadapter.OBOParseException
//     */
//    public static OBOSession getSession(String path) throws IOException, OBOParseException {
//
//        DefaultOBOParser parser = new DefaultOBOParser();
//        OBOParseEngine engine = new OBOParseEngine(parser);
//        // GOBOParseEngine can parse several files at once
//        // and create one munged-together ontology,
//        // so we need to provide a Collection to the setPaths() method
//        Collection paths = new LinkedList();
//        paths.add(path);
//        engine.setPaths(paths);
//        engine.parse();
//        session = parser.getSession();
//        return session;
//    }
    /**
     * Previously used to test this class.
     * @param args
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        loadPropInfoFile(new File("src/datasets/ontology_file-100329-short.txt"));
//        try {
//            loadGOTermsMap();
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Set s1 = new HashSet();Set s2 = new HashSet();Set s3 = new HashSet();
//        s2.add(1); s2.add(2); s3.add(3); s3.add(4);
//        s1.addAll(s2); s1.addAll(s3);
//        Set ss1 = new HashSet(); Set ss2 = new HashSet();
//        ss1.addAll(s1);
//        System.out.println("ss1" +ss1);
//        s1.clear();
//        s1.add(s2); s1.add(s3);
//        ss1.clear();
//        ss1.add(s1);
//        System.out.println("ss1" +ss1);

    }
}
