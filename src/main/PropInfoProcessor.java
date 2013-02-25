package main;

import objects.PropertyTerm;
import objects.NameSpace;
import objects.BioObject;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javax.swing.JOptionPane;
import kmean.DataVector;
import kmean.DataCluster;
import kmean.KMeanClusterer;
import util.StopWatch;

/**
 * 2011/04/01 - Update weighing schemes
 * 2010/06/01 - Change names related to GO to property-information-related names
 * 2009/11/04 - Modify PropInfoProcessor class to parse new ontology_file.txt to retrieve parents of each term
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class PropInfoProcessor {
    Map<Object, Map<PropertyTerm, Double>> clusterNumMemMap = new HashMap<Object, Map<PropertyTerm, Double>>();
    /**
     * Use objectTermScoreMap for storing information for both bioNodes and cluster
     */
    Map<Object, Map<PropertyTerm, Double>> objectTermScoreMap = new HashMap<Object, Map<PropertyTerm, Double>>();
    private static SortedMap<String, NameSpace> nameNamespaceMap = new TreeMap<String, NameSpace>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    });
    public static Map<NameSpace, Double> weightMap = new HashMap();
    static Map<String, PropertyTerm> idPropTermsMap = new HashMap<String, PropertyTerm>();
    Map<Object, DataVector> nodesPropVectorMap = new HashMap<Object, DataVector>();
    Map<PropertyTerm, Set<String>> propTermAncestorsMap = new HashMap<PropertyTerm, Set<String>>();

    public static void setWeightMap(Map map) {
        weightMap = map;
    }

    public static Map<NameSpace, Double> getWeightMap() {
        return weightMap;
    }

    public static SortedMap<String, NameSpace> getNamespaceMap() {
        return nameNamespaceMap;
    }

    public static void setNamespaceMap(SortedMap<String, NameSpace> namespaceMap) {
        PropInfoProcessor.nameNamespaceMap = namespaceMap;
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
        nameNamespaceMap.clear();
        idPropTermsMap.clear();
        
        while ((st = br.readLine()) != null) {
            stn = new StringTokenizer(st, "\t");
            String id = stn.nextToken();
            String name = stn.nextToken();
            String shortName = stn.nextToken();
            String namespace = stn.nextToken();
            int depth = Integer.parseInt(stn.nextToken());

            PropertyTerm term = new PropertyTerm(id, depth);
            term.setName(name);
            NameSpace ns = nameNamespaceMap.get(namespace);
            if (ns == null) {
                ns = new NameSpace(namespace);
                nameNamespaceMap.put(namespace, ns);
            }
            term.setNamespace(ns);
            term.setShortName(shortName);

            /* add parent terms */
            if (stn.hasMoreTokens()) {
                String parentList = stn.nextToken();
                StringTokenizer stk = new StringTokenizer(parentList, "|");
                while (stk.hasMoreTokens()) {
                    term.addParent(stk.nextToken().trim());
                }

            }
            idPropTermsMap.put(id, term);
            count++;
        }
        System.out.println("number of property terms: " + count);

    }
    
    /**
     * Create mapping of nodes/clusters into property term-score map
     * property term-score map is mapping from property terms into score of those terms
     * @param cSet, a set of clusters/nodes
     * @return map of nodes/clusters into property term-score map
     */
    public Map<Object, Map<PropertyTerm, Double>> populatePropTerms(Set cSet) {
        boolean foundNullTerm = false;
        Set<String> setOfNullTerm = new HashSet<String>();
        objectTermScoreMap =
                new HashMap<Object, Map<PropertyTerm, Double>>();

        Map<PropertyTerm, Double> termNumMembersMap = new HashMap<PropertyTerm, Double>();
        Map<PropertyTerm, Double> termScoreMap = new HashMap<PropertyTerm, Double>();
        
        /* Assume that cSet purely contains either bioObject (nodes) or Set (clusters) */
        for (Object obj : cSet) {

            boolean isBioObjectSet = false;
            Set cluster = null;
            BioObject bioNode = null;

            if (obj instanceof BioObject) {
                bioNode = (BioObject) obj;
                isBioObjectSet = true;
            } else if (obj instanceof Set) {
                cluster = (Set) obj;
            } else {
                System.out.println("Error neither BioObj nor Set");
            }

            termNumMembersMap.clear();
            termScoreMap.clear();

            /* if cSet is a set of bioObject */
            if (isBioObjectSet) {

                Set<String> propTermIdSet = new HashSet<String>();
                propTermIdSet.addAll(bioNode.getPropTermList());
                for (String id : propTermIdSet) {
                    PropertyTerm term = idPropTermsMap.get(id);
                    if (term == null) {
                        foundNullTerm = true;
                        setOfNullTerm.add(id);
                    } else {
                        /* skip some terms whose weights are zero or namespace weight is set to zero */
                        if ((term.getWeight() == 0) || (nameNamespaceMap.get(term.getNamespace().getName()).getWeight() == 0)) {
                            continue;
                        }
                        /* for bioObject, for each property term, the score is the depth of that term */
                        termScoreMap.put(term, (double) 1 * term.getWeight() * nameNamespaceMap.get(term.getNamespace().getName()).getWeight());
                    }
                }

            } else {
                /* if cSet is a set of cluster */
                for (Object node : cluster) {
                    bioNode = (BioObject) node;
                    Set<String> propTermIdSet = new HashSet<String>();
                    propTermIdSet.addAll(bioNode.getPropTermList());
                    for (String id : propTermIdSet) {
                        PropertyTerm term = idPropTermsMap.get(id);
                        if (term == null) {
                            foundNullTerm = true;
                            setOfNullTerm.add(id);
                        } else {
                            /* skip some terms whose weights are zero or namespace weight is set to zero */
                            if ((term.getWeight() == 0) || (nameNamespaceMap.get(term.getNamespace().getName()).getWeight() == 0)) {
                                continue;
                            }

                            if (!termScoreMap.containsKey(idPropTermsMap.get(id))) {
                                termScoreMap.put(term, (Double) 1.0 / cluster.size() * term.getWeight() * nameNamespaceMap.get(term.getNamespace().getName()).getWeight());
                            } else {
                                termScoreMap.put(term, (Double) termScoreMap.get(term) + 1.0 / cluster.size() * term.getWeight() * nameNamespaceMap.get(term.getNamespace().getName()).getWeight());
                            }
                        }
                    }
                }

            }
            /* No need to normalize scoreMap here, will be handled in K-Means clusterer */
            Map<PropertyTerm, Double> map = new HashMap<PropertyTerm, Double>(termScoreMap);

            if (isBioObjectSet) {
                objectTermScoreMap.put(bioNode, map);
            } else {
                objectTermScoreMap.put(cluster, map);
            }

        }

        if (foundNullTerm) {
            MainApp.pw.println("=== List of Terms Not Found in Prop Info File ===");
            for (String id : setOfNullTerm) {
                MainApp.pw.print(id + "\t");
            }
            MainApp.pw.println("\n================================================");
            JOptionPane.showMessageDialog(null, "Some of the property terms annotated to the nodes of the current network disappear from the property information file.\n"
                    + "The network was clustered using other available terms, but the result might not be reliable.\n"
                    + "Please check your network files and the property information file, and run NaviCluster again. (Please see the log file for not found terms) ", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        System.out.println("objectTermScoreMap size: " + objectTermScoreMap.size());

        return objectTermScoreMap;
    }

    /**
     * Set colors for namespaces
     * 360 degrees of hue are divided by the number of namespaces, so that each color hue (angle) is set to be farthest from another one.
     * Brightness will be contrasted to the againstColor; if againstColor is dark, the labels will be light and vice versa.
     * Saturation is the same as againstColor
     */
    public static void setColorsForNamespaces(Color againstColor) {
        int numNamespace = nameNamespaceMap.size();
        float[] hsb = Color.RGBtoHSB(againstColor.getRed(), againstColor.getGreen(), againstColor.getBlue(), null);
        int hueInDegree = (int) (hsb[0] * 360);
        float brightnessToBeUsed = hsb[2];
        
        if (brightnessToBeUsed > 0.37) {
            brightnessToBeUsed -= 0.37;
        } else {
            brightnessToBeUsed += 0.37;
        }

        // count againstColor as 1, so divided by number of namespaces plus 1
        int step = 360 / (numNamespace + 1), ind = 1;
        for (Entry<String, NameSpace> entry : nameNamespaceMap.entrySet()) {

            float hueToBeUsed = (float) ((hueInDegree - ind * step) % 360) / 360;
            int rgb = Color.HSBtoRGB(hueToBeUsed, hsb[1], brightnessToBeUsed);
            entry.getValue().setColor(new Color(rgb));
            ind++;
        }
    }

    /**
     * Assume that populatePropTerms is already called.
     * Just put objectTermScoreMap to mapping of cluster into datavector
     * Do not clear nodesPropVectorMap for sake of reusability with nodes of many levels
     * @param cSet, a set of clusters/nodes
     * @return nodesPropVectorMap
     */
    public Map<Object, DataVector> getNodesPropVectorMapBeforeClustering(Set cSet) {

        for (Object obj : cSet) {
            if (objectTermScoreMap.get(obj) == null) {
                System.out.println("Vertex : " + obj + " has no score map");
            }
            DataVector vec = new DataVector(objectTermScoreMap.get(obj));
            vec.nodeRef = obj;
            nodesPropVectorMap.put(obj, vec);
        }
        return nodesPropVectorMap;
    }

    /**
     * For a set of BioObject only
     * Assume that for BioObject set, cSet is the same as comNodeCSet (no hierarchy exists)
     * @param cSet, a set of BioObject
     * @param numCluster, number of clusters wanted
     * @return a set of DataCluster
     */
    public DataCluster[] preKMeansCluster(Set cSet, int numCluster) {
        KMeanClusterer kCluster = new KMeanClusterer(numCluster);

        Object[] arrCluster = cSet.toArray(new Object[1]);

        for (int i = 0; i < arrCluster.length; i++) {

            Map map = objectTermScoreMap.get(arrCluster[i]);
            if (map == null) {
                System.out.println("NULL++++++++");
            }

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

    /**
     * For a set of clusters
     * @param comNodeClustToFlatClust, a map from a set of hierarchically organized clusters to a set of clusters
     * @param comNodeCSet, a set of hierarchically organized clusters
     * @param cSet, a set of clusters
     * @param numCluster, number of clusters wanted
     * @return a set of DataCluster
     */
    public DataCluster[] preKMeansCluster(Map<Set, Set> comNodeClustToFlatClust, Set<Set> comNodeCSet, Set<Set> cSet, int numCluster) {
        KMeanClusterer kCluster = new KMeanClusterer(numCluster);

        Set[] arrCluster = comNodeCSet.toArray(new Set[1]);

        Set<DataVector> sortedVecSet = new TreeSet<DataVector>(new Comparator() {
            /* try to compare deeply to ensure the order of DataVector is always the same */
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

            Set flatClust = comNodeClustToFlatClust.get(arrCluster[i]);
            
            /* get a term score map of a set (cluster) */
            Map<PropertyTerm, Double> mapOfComNodeClustI = objectTermScoreMap.get(flatClust);
            if (mapOfComNodeClustI == null) {
                System.out.println("Error! cannot find a term score map for the set " + flatClust);
            }

            DataVector vec = new DataVector(mapOfComNodeClustI);

            vec.nodeRef = flatClust;
            vec.comNodeClusterRef = arrCluster[i];

            sortedVecSet.add(vec);
        }
        
        /* at this step, it is ensured that the order of DataVector fed into the dataVectorList of kCluster is the same at all time */
        for (DataVector vec : sortedVecSet) {
            kCluster.dataVectorList.add(vec);
        }
        
        StopWatch sw = new StopWatch();
        sw.start();
        kCluster.assignKPoint();
        sw.stop();
        System.out.println("Time used for assigning K points: " + sw);

        sw.start();
        kCluster.cluster();
        sw.stop();
        System.out.println("Time used for clustering: " + sw);

        return kCluster.getClusterList();

    }

    /**
     * Test this class.
     * @param args
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        loadPropInfoFile(new File("src/datasets/ontology_file-100329-short.txt"));

    }
}
