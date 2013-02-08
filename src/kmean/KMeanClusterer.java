/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kmean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import main.PropInfoProcessor;
import util.StopWatch;

/**
 *
 * @author Knacky
 */
public class KMeanClusterer {

    private int kValue = 0;
    private double threshold = 0.0;
    private double MSE = 0.0;
    public int numDim = 0;
    final private int MaxIteration = 5;
    private boolean changed = false;
    private DataCluster smallestCluster = null;
    DataCluster[] clusterList = new DataCluster[1];
    String[] universalColNameList = new String[1];
    public ArrayList<DataVector> dataVectorList = new ArrayList<DataVector>();
    Map<DataVector, Map<DataVector, Double>> distanceMap = new HashMap<DataVector, Map<DataVector, Double>>();
    double[][] simMatrix = new double[1][1];

    public KMeanClusterer(int k) {
        kValue = k;
        clusterList = new DataCluster[k];
        for (int i = 0; i < k; i++) {
            clusterList[i] = new DataCluster();
        }

    }

    public KMeanClusterer(int k, double threshold) {
        this(k);
        this.threshold = threshold;

    }

    public DataCluster[] getClusterList() {
        return clusterList;
    }

    /**
     * Find a vector in the TSet that is nearest (most similar to) vec.
     * Return the value of similarity between vec and that vector.
     * @param vec
     * @param TSet
     * @return maximum similarity value
     */
    private double findMaxSimFromSet(DataVector vec, Set<DataVector> TSet) {
        double max = -1, sim;

        for (DataVector considered : TSet) {
            if (considered == vec) {
                continue;
            }
            int indexVec = dataVectorList.indexOf(vec);
            int indexConsidered = dataVectorList.indexOf(considered);
            if (indexVec < indexConsidered) {
                sim = simMatrix[indexVec][indexConsidered];
            } else {
                sim = simMatrix[indexConsidered][indexVec];
            }

            if (sim > max) {
                max = sim;
            }
        }
        return max;
    }

    /**
     * Selects the first k representative points by trying to separate those points as much as possible
     * In other words, select points which are as LESS SIMILAR as possible.
     */
    public void selectByLeastSimilar() {
//        Set<DataVector> TSet = new HashSet<DataVector>();
//        Set<DataVector> theRest = new HashSet<DataVector>();
        Comparator dvCmp = new Comparator() {

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
        };
        Set<DataVector> TSet = new TreeSet<DataVector>(dvCmp);
        Set<DataVector> theRest = new TreeSet<DataVector>(dvCmp);
//        Set<DataVector> TSet = new HashSet<DataVector>();
//        Set<DataVector> theRest = new HashSet<DataVector>();
        
        DataVector properVec = null;
        double minofmax = Double.MAX_VALUE - 1, maxsim = -1;
        theRest.addAll(dataVectorList);
        //For now, use the first data that has GO Terms as initial seed

        int j = 0;
//        while (dataVectorList.get(j).valueMap.size() == 0)
//            j++;
        TSet.add(dataVectorList.get(j));
        theRest.remove(dataVectorList.get(j));
        clusterList[0].representative = dataVectorList.get(j);
        clusterList[0].add(dataVectorList.get(j));
//        System.out.println("TSet: "+TSet.size());
//        System.out.println("theRest: "+theRest.size());
//        System.out.println("clusterlist 0 "+((Set)clusterList[0].members.iterator().next().nodeRef).size());

        for (int i = 1; i < kValue; i++) {
//            System.out.println("Cluster "+i);
            minofmax = Double.MAX_VALUE;
            properVec = null;

            for (DataVector vec : theRest) {
//                System.out.println("size "+((Set)vec.nodeRef).size());
//                if (vec.valueMap.size() == 0)
//                    continue;
                maxsim = findMaxSimFromSet(vec, TSet);
                if (minofmax > maxsim) {
                    minofmax = maxsim;
                    properVec = vec;
                }

            }

//            System.out.println("ProperVec: "+properVec);
            TSet.add(properVec);
            theRest.remove(properVec);
//            System.out.println("TSEt "+TSet.size() );
//            for (DataVector dv : TSet){
//                System.out.print(((Set)dv.nodeRef).size()+" ");
//            }
//            System.out.println("");
//            System.out.println("theRest: "+theRest.size());
//            System.out.println("-----");
//            clusterList[i].representative = dataVectorList.get(i);
//            clusterList[i].add(dataVectorList.get(i));
            clusterList[i].representative = properVec;
            clusterList[i].add(properVec);
//            System.out.println("TSet: "+TSet);
//            System.out.println("theRest: "+theRest);
        }

    }

    public static double similarity(DataVector point1, DataVector point2) {
//        for (Object key : tmpKeySet){
//            res += Math.pow(longOne.valueMap.get(key), 2);
//        }
        double res = 0.0;
        DataVector shortOne = null, longOne = null;
        if ((point1 == null) || (point2 == null) || (point1.valueMap.size() == 0) || (point2.valueMap.size() == 0)) {
            return -1;
        }
        if (point1.valueMap.keySet().size() > point2.valueMap.keySet().size()) {
            shortOne = point2;
            longOne = point1;
        } else {
            shortOne = point1;
            longOne = point2;
        }

        double normShort = 0.0, normLong = 0.0;

        Set keySet = new HashSet(longOne.valueMap.keySet());
        for (Object key : shortOne.valueMap.keySet()) {
            normShort += shortOne.valueMap.get(key) * shortOne.valueMap.get(key);
            if (longOne.valueMap.containsKey(key)) {
                normLong += longOne.valueMap.get(key) * longOne.valueMap.get(key);
                keySet.remove(key);
                res += shortOne.valueMap.get(key) * longOne.valueMap.get(key);
//                        System.out.print("short one: "+shortOne.valueMap.get(key));
//                        System.out.println(" long one: "+longOne.valueMap.get(key));

            }
        }
        for (Object key : keySet) {
            normLong += longOne.valueMap.get(key) * longOne.valueMap.get(key);
        }
        normShort = Math.sqrt(normShort);
        normLong = Math.sqrt(normLong);
        res /= (normLong * normShort);
        return res;

    }

    /**
     * Calculate the similarity between point1 and the center of "cluster"
     * The calculation excludes the vectors which have no prop terms.
     * @param point1
     * @param cluster
     * @return similarity
     */
    public double similarPointToCluster(DataVector point1, DataCluster cluster) {
        double res = 0.0;
        int indpoint1 = dataVectorList.indexOf(point1);
        int excludedNo = 0;
        for (DataVector member : cluster.members) {
            int indmember = dataVectorList.indexOf(member);
            if (indpoint1 < indmember) {
                if (!Double.isNaN(simMatrix[indpoint1][indmember])) {
                    res += simMatrix[indpoint1][indmember];
                }
//                else
//                    excludedNo++;
            } else {
                if (!Double.isNaN(simMatrix[indmember][indpoint1])) {
                    res += simMatrix[indmember][indpoint1];
                }
//                else
//                    excludedNo++;
            }
        }
        if ((cluster.members.size() - excludedNo) == 0) {
            res = -1;
        } else {
            res /= (cluster.members.size() - excludedNo);
        }


        return res;
    }

    /**
     * Calculate mean square error (MSE) of the current partition.
     * However, decision to stop the algorithm or not does not depend on this value anymore
     * because the meaning of MSE with property information similarity is not clear.
     * Instead, the algorithm will stop when there are no changes in any clusters anymore or
     * the number of iteration exceeds the threshold (e.g., 10 times)
     * @return MSE
     */
    public double calcMSE() {
        double resultList[] = new double[numDim];
        double result = 0.0;
        for (int i = 0; i < clusterList.length; i++) {

//            System.out.println("cluster "+i);
            for (DataVector member : clusterList[i].members) {
                double tempRes = 0.0;
//                resultList = new double[clusterList[i].representative.dimNameList.length];

                // ignore vectors and clusters that have no property information
                if (member.valueMap.isEmpty() || clusterList[i].representative.valueMap.isEmpty()) {
                    continue;
                }

                // representative vector should have more/equal dimensions than/to any data vectors in its cluster.
                DataVector shortOne = null, longOne = null;
//                if (clusterList[i].representative.valueMap.keySet().size() > member.valueMap.keySet().size()) {
                    shortOne = member;
                    longOne = clusterList[i].representative;
//                } else {
//                    shortOne = clusterList[i].representative;
//                    longOne = member;
//                }
                resultList = new double[longOne.valueMap.keySet().size()];
//                Set tmpKeySet = longOne.valueMap.keySet();
                int j = 0;
                for (Object key : longOne.valueMap.keySet()) {
                    if (shortOne.valueMap.containsKey(key)) {
//                        System.out.println("Member dimvaluelist index: "+shortOne.valueMap.get(key));
//                        System.out.println("cluster i representative: "+longOne.valueMap.get(key));
                        resultList[j] += shortOne.valueMap.get(key) - longOne.valueMap.get(key);
//                        tmpKeySet.remove(key);
                    } else {
                        resultList[j] += longOne.valueMap.get(key);
                    }
                    j++;
                }
                for (j = 0; j < resultList.length; j++) {
//                    System.out.println("j: "+j+" resultList j "+resultList[j]);
                    tempRes += resultList[j] * resultList[j];
//                    System.out.println("Temp Res: "+tempRes);
                }
                result += tempRes;

            }


        }
//        result /= dataVectorList.size();
        return result;
    }

    /**
     * Re-calculate the representatives of each cluster by averaging the values of all members in
     * the cluster.
     * The results are MeanVector of the clusters
     *
     */
    public void recalRepresentVector() {

        for (int i = 0; i < clusterList.length; i++) {
//            System.out.println("cluster "+i);

            Map<String, Double> valMap = new TreeMap<String, Double>();

            for (DataVector member : clusterList[i].members) {
//                System.out.println("member: "+member);
//                for (int j = 0; j < member.dimValueList.length; j++) {
                if (member.valueMap.isEmpty()) {
                    continue;
                }
                for (String key : member.valueMap.keySet()) {

                    if (!valMap.containsKey(key)) {

                        valMap.put(key, (double) member.valueMap.get(key) / clusterList[i].members.size());
                    } else {
                        valMap.put(key, valMap.get(key) + (double) member.valueMap.get(key) / clusterList[i].members.size());

                    }
                }

            }
            clusterList[i].representative = new MeanVector(valMap);
//            System.out.println("new rep: "+clusterList[i].representative);
//            System.out.println("");
        }
    }

    /**
     * iterate one step of K-means algorithm.
     * For each vector (datum), calculate the distance from it to all clusters' centers.
     * Move it to the nearest cluster.
     *
     */
    public void iterateOneStep() {
        changed = false;
        int i = 0;
        StopWatch sw = new StopWatch();
            sw.start();
        for (DataVector datum : dataVectorList) {
//            System.out.println("data: "+datum);
            if (i%500 == 0){
                sw.stop();
                System.out.println("data " + i+ " "+sw);
                sw.start();
            }
            double diff = 0.0, maxsim = -1;
            /* deal with data with no GO terms */
//            if (datum.valueMap.size() == 0) {
////                System.out.println("datum with valuemap size 0 ");
//
//                if (datum.belongToCluster == null){
////                    if (smallestCluster == null){
//                        int smallestMembers = Integer.MAX_VALUE - 2;
//                        smallestCluster = null;
//                        System.out.println("----datum "+datum.nodeRef);
//                        for (DataCluster cluster : clusterList) {
//                            System.out.println("cluster member "+cluster.members.size());
//                            for (DataVector vec : cluster.members){
//                                if (vec.nodeRef instanceof Set){
//                                    System.out.print(((Set)vec.nodeRef).size()+" ");
//                                    if (((Set)vec.nodeRef).size() == 1)
//                                        System.out.print(vec.nodeRef+" "); }
//                                else
//                                    System.out.print(vec.nodeRef+" ");
//                            }
//                            System.out.println("");
////                            System.out.println("cluster "+((Set)cluster.members.get(0).nodeRef).size());
////                            System.out.println("cluster represen "+((Set)cluster.representative.nodeRef).size());
//                            if (cluster.members.size() < smallestMembers) {
//                                smallestMembers = cluster.members.size();
//                                smallestCluster = cluster;
//                            }
//                        }
////                    }
//                    changed = true;
//                    smallestCluster.add(datum);
//                    System.out.println("smallest cluster");
//                    for (DataVector vec : smallestCluster.members){
//                                if (vec.nodeRef instanceof Set){
//                                    System.out.print(((Set)vec.nodeRef).size()+" ");
//                                    if (((Set)vec.nodeRef).size() == 1)
//                                        System.out.print(vec.nodeRef+" ");
//                                }
//                                else
//                                    System.out.print(vec.nodeRef+" ");
//                            }
//                            System.out.println("");
//
//                } else {
//                    int smallestMembers = Integer.MAX_VALUE - 2;
//                    for (DataCluster cluster : clusterList) {
//                        int clusterMemberSize = cluster.members.size();
////                        System.out.println("clusterMemberSize "+clusterMemberSize);
//                        if (cluster == datum.belongToCluster) {
//                            clusterMemberSize--;
////                            System.out.println("clusterMemberSize reduced "+clusterMemberSize);
//                        }
//                        if (clusterMemberSize < smallestMembers) {
//                            smallestMembers = clusterMemberSize;
//                            smallestCluster = cluster;
//                        }
//                    }
//                    if (smallestCluster != datum.belongToCluster){
//                        changed = true;
//                        DataCluster dc = datum.belongToCluster;
//                        if (dc.members.size() == 1) {
//                            System.out.println("From data with no GO terms");
//                            System.out.println("found zero members");
//                            System.out.println("smallest cluster sim: " + similarPointToCluster(datum,smallestCluster));
//                            System.out.println("self sim: " + similarPointToCluster(datum, datum.belongToCluster));
//                        }
//                        datum.belongToCluster.remove(datum);
//                        smallestCluster.add(datum);
//                    }
//                }
//
//            } else {
//                System.out.println("datum "+((Set)datum.nodeRef).size());
            DataCluster maxCluster = null;
//                if ((datum.belongToCluster != null) && (similarPointToCluster(datum, datum.belongToCluster) > 0.999999999999D)); else {
            if ((datum.belongToCluster != null) && (datum.belongToCluster.members.size() == 1)); else {
                for (DataCluster cluster : clusterList) {
//                diff = distance(datum,cluster.representative);
//                    StopWatch sw2 = new StopWatch();
//                    sw2.start();
                    diff = similarPointToCluster(datum, cluster);
//                    sw2.stop();
//                    System.out.println("diff cal time "+sw2);
//                    if (Double.isNaN(diff))
//                        continue;
//                System.out.println("cluster rep: "+cluster.representative+" with diff "+diff);

                    int result = Double.compare(diff, maxsim);

                    if (result > 0) {
                        maxsim = diff;
                        maxCluster = cluster;
//                            if (maxsim > 0.98) {
//                                System.out.println("found similar > 0.98 cluster " + maxsim);
//
//                                if (datum.belongToCluster == null) {
//                                    System.out.println("because datum has no cluster");
//                                } else if (cluster != datum.belongToCluster) {
//                                    System.out.println("but not own cluster");
//                                    System.out.println("self sim " + similarPointToCluster(datum, datum.belongToCluster));
//                                }
//                            }

                    }
                }
            }
            if (maxCluster != null) {
//                    if (datum.belongToCluster == null) {
//                        if (maxCluster != null) {
//                            changed = true;
//                            maxCluster.add(datum);
//                        }
//                    } else
//                    if (maxCluster != datum.belongToCluster) {
                changed = true;
//                        DataCluster dc = datum.belongToCluster;
//                        if (dc.members.size() == 1) {
//                            System.out.println("found zero members");
//                            System.out.println("max sim: " + maxsim);
//                            System.out.println("self sim: " + similarPointToCluster(datum, datum.belongToCluster));
//                        }
                if (datum.belongToCluster != null) {
                    datum.belongToCluster.remove(datum);
                }
                maxCluster.add(datum);
//                    }
            }
//            sw.stop();
//            System.out.println("one vector time "+sw);
            i++;
        }
//            System.out.println("min cluster: "+minCluster+" with "+mindiff);
//        }

    }

    public void cluster() {
        double prevMSE = 0.0;
        // while until no change of representative;
        int iteration = 0;
        do {
            StopWatch sw = new StopWatch();
            sw.start();
            iterateOneStep();
            sw.stop();
            System.out.println("iterate one step "+sw);
            sw.start();
            recalRepresentVector();
            sw.stop();
            System.out.println("recal represent vector "+sw);
            MSE = calcMSE();
            System.out.println("Current WCSS: " + MSE + " iter " + iteration+" ");
            if (Math.abs(MSE - prevMSE) < 0.001) {
                changed = false;
            }
            prevMSE = MSE;
            iteration++;
        } while (changed && iteration < MaxIteration);

    }

    ///DEBUG
    public void printSimMatrix(){
        try {
            PrintWriter pw = new PrintWriter(new File("debug-simmatrix"));
            for (double[] row : simMatrix) {
                for (double cell : row){
                    pw.print(cell+"\t");
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Calculate the similarities between every pair of vectors
     * Store them in the simMatrix.
     */
    public void calcSim() {
//        StopWatch sw = new StopWatch();
//        System.out.println("datavectorlist size "+dataVectorList.size());
        /////// DEBUG
//        HashMap<DataVector,Double> vectorNormMap = new HashMap<DataVector,Double>();
        int biggestVecSize = 0;
        simMatrix = new double[dataVectorList.size()][dataVectorList.size()];
        for (int i = 0; i < simMatrix.length; i++) {
//            System.out.println("Cluster "+i+": "+dataVectorList.get(i));
//            sw.start();
          //////// DEBUG
//            System.out.println("\ni = "+i+" Vec size "+dataVectorList.get(i).valueMap.size());
            for (int j = i; j < simMatrix.length; j++) {
//                System.out.println("Cluster "+j+": "+dataVectorList.get(j));
                /* if vector(s) have/has no GO terms, the distance between them is set to -1
                 * (or not similar at all)
                 */
//                System.out.println("j = "+j);
                if (dataVectorList.get(i).valueMap.isEmpty() || dataVectorList.get(j).valueMap.isEmpty()) {
                    if (dataVectorList.get(i).valueMap.isEmpty() && dataVectorList.get(j).valueMap.isEmpty()) {
                        simMatrix[i][j] = 1;
                    } else {
                        simMatrix[i][j] = -1;
                    }
                    continue;
                }
                double res = 0.0;
                DataVector shortOne = null, longOne = null;
                if (dataVectorList.get(i).valueMap.keySet().size() > dataVectorList.get(j).valueMap.keySet().size()) {
                    shortOne = dataVectorList.get(j);
                    longOne = dataVectorList.get(i);
                } else {
                    shortOne = dataVectorList.get(i);
                    longOne = dataVectorList.get(j);
                }
                /////////// DEBUG
                if (longOne.valueMap.size() > biggestVecSize){
                    biggestVecSize = longOne.valueMap.size();
                }
                double normShort = 0.0, normLong = 0.0;
//                Set keySet = new HashSet(longOne.valueMap.keySet());
//                Set<String> calAlreadySet = new HashSet<String>(longOne.valueMap.keySet());
                Set<String> mustBeCal = new HashSet<String>(longOne.valueMap.keySet());

//                System.out.println("shortOne "+shortOne.valueMap.values());
                for (Object key : shortOne.valueMap.keySet()) {
                    double valueShort = shortOne.valueMap.get(key);
                    normShort += valueShort * valueShort;
                    Object valLong = longOne.valueMap.get(key);
                    if (valLong != null) {
//                        calAlreadySet.add((String)key);
                        double valueLong = (Double) valLong;
//                        normLong += valueLong*valueLong;
//                        keySet.remove(key);
                        res += valueShort * valueLong;
                        normLong += valueLong * valueLong;
                        mustBeCal.remove((String) key);
//                        System.out.print("short one: "+shortOne.valueMap.get(key));
//                        System.out.println(" long one: "+longOne.valueMap.get(key));

                    }
                }

                normShort = Math.sqrt(normShort);
//                System.out.println("normShort "+normShort);
//                vectorNormMap.put(shortOne, normShort);
//                System.out.println("vecmap size "+vectorNormMap.keySet().size());
//                mustBeCal.removeAll(calAlreadySet);
//                for (Object key : keySet){
//                if (vectorNormMap.containsKey(longOne)){
//                    normLong = vectorNormMap.get(longOne);
////                    System.out.println("hit");
//                    System.out.println("long one size "+longOne.valueMap.size());
////                    System.out.println("longOne "+longOne.valueMap.values());
////                    System.out.println("normLong "+normLong);
//                }
//                else {
//                System.out.println("mustbecal size "+mustBeCal.size());
                    for (Object key : mustBeCal) {
//                for (Object key : longOne.valueMap.keySet()){
                        double valueLong = longOne.valueMap.get(key);
                        normLong += valueLong * valueLong;
                    }
                    normLong = Math.sqrt(normLong);
//                    vectorNormMap.put(longOne, normLong);
//                }

                res /= (normLong * normShort);
                simMatrix[i][j] = res;
//                System.out.println("Result: "+res);


            }
            
//            sw.stop();
//            System.out.println("time used for loop j "+sw);
        }
        //////// DEBUG
//        printSimMatrix();
        System.out.println("biggest vec size "+biggestVecSize);
    }

    /**
     * First, calculate similarities between each vector and store them in a matrix.
     * Assign first K representative points by using "selectByLeastSimilar" method
     */
    public void assignKPoint() {
        StopWatch sw = new StopWatch();
        sw.start();
        calcSim();
        sw.stop();
        System.out.println("calcsim " +sw);
        if (kValue > dataVectorList.size()) {
            System.out.println("error kValue > data vector list");
            return;
        }
        sw.start();
        selectByLeastSimilar();
        sw.stop();
        System.out.println("selectKcenters "+sw);
//        for (int i = 0; i < kValue; i++){
//            clusterList[i].representative = dataVectorList.get(i);
//            clusterList[i].add(dataVectorList.get(i));
//        }
    }

    /**
     * Utility class used to create mapping of string into double
     * It is currently used in main method of this class.
     * @param s
     * @param d
     * @return
     */
    private static Map putStringDoubleMap(String[] s, double[] d) {
        Map<String, Double> m = new TreeMap<String, Double>();
        for (int i = 0; i < d.length; i++) {
            m.put(s[i], d[i]);
        }
        return m;
    }

    /**
     * used to test this class.
     * @param args
     */
    public static void main(String[] args) {
        KMeanClusterer kClusterer = new KMeanClusterer(3);
        Map<String, Double> map = new TreeMap<String, Double>();
        String[] namelist = {"a", "b", "c"};
        kClusterer.numDim = 3;
//        kClusterer.populateNameList(namelist);
//        DataVector.populateNameList(namelist);
        map = putStringDoubleMap(namelist, new double[]{0, 1, 0});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{1, 0, 0});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{0, 0, 0});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{1, 1, 0});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{0, -1, 0});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{0, 0, 1});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{1, 0, 1});
        kClusterer.dataVectorList.add(new DataVector(map));
        map = putStringDoubleMap(namelist, new double[]{1, 1, -1});
        kClusterer.dataVectorList.add(new DataVector(map));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{0, 1, 0}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{1, 0, 0}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{0, 0, 0}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{1, 1, 0}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{0, -1, 0}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{0, 0, 1}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{1, 0, 1}));
//        kClusterer.dataVectorList.add(new DataVector(new double[]{1, 1, -1}));
//        for (DataVector vec : kClusterer.dataVectorList){
//            vec.setNameList(namelist);
//        }

        namelist = new String[]{"b", "a"};
        map = putStringDoubleMap(namelist, new double[]{2, 0});
        DataVector vec = new DataVector(map);
//        vec = new DataVector(new double[]{2, 0});
//        vec.setNameList(namelist);
        kClusterer.dataVectorList.add(vec);

        map = putStringDoubleMap(namelist, new double[]{-1, 2});
        vec = new DataVector(map);
//        vec = new DataVector(new double[]{-1, 2});
//        vec.setNameList(namelist);
        kClusterer.dataVectorList.add(vec);
//        kClusterer.dataVectorList.add(new DataVector(new double[]{1, 0, 1}));
        System.out.println(kClusterer.dataVectorList.size());
        kClusterer.assignKPoint();
        kClusterer.cluster();
    }
}


