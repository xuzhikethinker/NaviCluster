/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LouvainAlgorithm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import util.StopWatch;

/**
 * Based on the code provided by the authors of the Louvain algorithm
 * @author Knacky
 */
public class MainLouvain {

//    static boolean isWeighted = false;
    static int numPass = 0;
    // precision used to control convergence
    static double precision = 0.000001;

    /**
     * The method that create a LouvainClusterer instance which is then passsed to callOnePass to run Louvain clustering.
     * This polymorph requires three arrays related to edge list.
     * The three arrays must be consistent; one triple corresponds to one edge.
     * THIS IS THE MODIFIED METHOD TO ENHANCE THE SPEED WHEN USED IN NAVICLUSTER.
     *
     * The result is a 2-d array whose each cell specifying which cluster the corresponding node belongs to.
     * @param sArr, a list of source nodes
     * @param dArr, a list of destination nodes
     * @param wArr, a list of edge weights
     * @return a hierarchy of cluster assignments
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int[][] louvainClusterer(int[] sArr, int[] dArr, double[] wArr) throws FileNotFoundException, IOException {
        // Call real Louvain Clusterer
        LouvainClusterer c = new LouvainClusterer(sArr, dArr, wArr, -1, precision);

        return callOnePass(c);
    }

    /**
     * The method that create a LouvainClusterer instance which is then passsed to callOnePass to run Louvain clustering.
     * This polymorph requires a file name as a parameter.
     * THIS IS THE ORIGINAL METHOD CREATED BY THE LOUVAIN ALGORITHM DEVELOPERS.
     *
     * The result is a 2-d array whose each cell specifying which cluster the corresponding node belongs to.
     * @param st, a file name
     * @param isFileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int[][] louvainClusterer(String st, boolean isFileName) throws FileNotFoundException, IOException {
        // Call real Louvain Clusterer (another polymorph)
        LouvainClusterer c = new LouvainClusterer(st, -1, precision, isFileName);

        return callOnePass(c);
    }

    /**
     * The method that order the LouvainClusterer class to really run phase 1 and phase 2.
     * It requires LouvainClusterer instance as a parameter.
     * It was designed to run the mutual procedure of two polymorphs of the louvainClusterer method.
     *
     * The result is a 2-d array whose each cell specifying which cluster the corresponding node belongs to.
     * @param c, LouvainCluster
     * @return a hierarchy of cluster assignments
     */
    private int[][] callOnePass(LouvainClusterer c){
        // comNodeHierarchy is used for collecting results of this method
        ArrayList<int[]> comNodeHieararchy = new ArrayList();
        // Stop Watch used to measure the time used.
        StopWatch overallTime = new StopWatch();
        overallTime.start();
        // Calculate modularity of the current network partition
        double mod = c.modularity();

        System.out.println("\nEpoch 0");
        System.out.println("Modularity: " + mod);
        System.out.println("num node: " + c.g.numNodes + " num edge: " + c.g.numLinks + " total weight: " + c.g.doubleSumWeight);

        StopWatch sw = new StopWatch();
        sw.start();
        // Run phase 1
        double newMod = c.phase1();
        sw.stop();
        System.out.println("Phase 1 Time Used: " + sw);

        // store the first (deepest level) cluster assignment
        comNodeHieararchy.add(c.nodeToCom);

        sw.start();
        // Run phase 2
        LouvainGraph g = c.phase2();
        sw.stop();
        System.out.println("Phase 2 Time Used: " + sw);
        System.out.println("Modularity increased from " + mod + " to " + newMod);

        // repeat until the modularity change is smaller than the threshold
        int level = 0;
        while (newMod - mod > precision) {
            mod = newMod;

            System.out.println("\nEpoch " + (level + 1));
            System.out.println("num node: " + g.numNodes + " num edge: " + g.numLinks + " total weight: " + g.doubleSumWeight);
            System.out.println("Modularity: " + mod);
            // Call real Lovain Clusterer (another polymorph)
            c = new LouvainClusterer(g, -1, precision);

            sw.start();
            // Run phase 1
            newMod = c.phase1();
            sw.stop();
            System.out.println("Phase 1 Time Used: " + sw);

            comNodeHieararchy.add(c.nodeToCom);

            sw.start();
            // Run phase 2
            g = c.phase2();
            sw.stop();
            System.out.println("Phase 2 Time Used: " + sw);

            System.out.println("Modularity increased from " + mod + " to " + newMod);

            level++;
        }

        overallTime.stop();
        System.out.println("Overall Time Used: " + overallTime);

        // Transform the arraylist to nested array
        int toReturn[][] = (int[][]) comNodeHieararchy.toArray(new int[comNodeHieararchy.size()][]);
        return toReturn;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filename = "src/karate.txt";

        if (args.length > 0) {
            filename = args[0];
        }

        MainLouvain ml = new MainLouvain();

        int[][] comNodeHierarchy = ml.louvainClusterer(filename, true);
        /* Below is the code used for showing the results from Louvain algorithm */
//        for (int i = 0; i < comNodeHierarchy.length; i++)
//        {
//            for (int j = 0; j < comNodeHierarchy[i].length; j++){
//                System.out.print(comNodeHierarchy[i][j]+" ");
//            }
//            System.out.println("");
//        }
    }
}
