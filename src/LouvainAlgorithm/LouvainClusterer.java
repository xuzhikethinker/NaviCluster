/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LouvainAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * LouvainClusterer class is a core class that performs the Louvain algorithm (like phase 1 and phase 2, and so on).
 * Refer to the original paper of the Louvain algorithm:
 *      Blondel, V., et al. (2008) Fast unfolding of communities in large networks, J Stat Mech, 2008, P10008
 *
 * Here, we refer to each cluster (community) by their number starting from 0.
 * Using primitive data structures instead of sophisticated Java data types so that it can retain the fast speed
 * of clustering
 *
 * <code>g</code> is a graph/network used only in Louvain algorithm
 * <code>nodeToCom</code> tells us each node belong to what community (cluster); Indices stand for the numbers (IDs) of nodes.
 * Values stand for their clusters.
 * <code>in</code> stores a summation of all edge weights in each cluster
 * <code>tot</code> stores a summation of all weights of edges adjacent to node members of each cluster
 *
 * <code>size</code> is the number of nodes in the graph.
 * <code>maxNumPass</code> is the maximum number of passes allowed. When the number of iterations is over this value, the program stops running pass.
 * <code>minMod</code> defines the minimum value of the modularity that the algorithm repeats the two phases.
 * If the modularity of the partition is below this value, it will stop the two phases.
 *
 * @author Knacky
 */
public class LouvainClusterer {

    LouvainGraph g;
    int size;
    public int[] nodeToCom;
    double[] in, tot;
    int maxNumPass;
    double minMod;

    /**
     * A polymorphic constructor of this class that takes a string as parameter.
     * According to the content of <code>st</code> (specified by the flag <code>isFileName</code>), it decides which constructor is used to create a Louvain graph.
     * Then it calls the <code>init</code> method.
     *
     * @param st    a string containing edge and weight information or file name
     * @param maxNumPass
     * @param minm
     * @param isFileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public LouvainClusterer(String st, int numPass, double minm, boolean isFileName) throws FileNotFoundException, IOException {
        if (isFileName) {
            g = new LouvainGraph(new File(st));
        } else {
            g = new LouvainGraph(st);
        }
        init(numPass, minm);

    }

    /**
     * A polymorphic constructor of this class that takes three arrays related to graph edges as parameters.
     * It creates a graph from these three arrays and then calls the <code>init</code> method.
     *
     * @param sArr  a list of source nodes
     * @param dArr  a list of destination nodes
     * @param wArr  a list of edge weights
     * @param maxNumPass
     * @param minm
     * @throws FileNotFoundException
     * @throws IOException
     */
    public LouvainClusterer(int[] sArr, int[] dArr, double[] wArr, int numPass, double minm) throws FileNotFoundException, IOException {
        g = new LouvainGraph(sArr, dArr, wArr);
        init(numPass, minm);
    }

    /**
     * A polymorphic constructor of this class that take a Louvain graph instance as a parameter.
     * It sends the graph to the <code>init</code> method directly.
     *
     * @param gc    a Louvain graph
     * @param maxNumPass
     * @param minm
     */
    public LouvainClusterer(LouvainGraph gc, int numPass, double minm) {
        g = gc;
        init(numPass, minm);
    }

    /**
     * Initializes each variables.
     * <code>size</code> is set to equal to the number of nodes
     * <code>nodeToCom</code>, <code>in</code>, <code>tot</code> are set their sizes to equal to the number of nodes
     * Each node belongs to each own cluster.
     * <code>in</code> for each cluster equals to each own node member's self-loop weight.
     * <code>tot</code> for each cluster equals to the weighted degree of its node member.
     *
     * @param maxNumPass TODO
     * @param minm
     */
    private final void init(int numPass, double minm) {
        size = g.numNodes;
        nodeToCom = new int[size];
        in = new double[size];
        tot = new double[size];
        for (int i = 0; i < size; i++) {
            nodeToCom[i] = i;
            in[i] = g.numSelfLoops(i);
            tot[i] = g.weightedDegree(i);
        }
        this.maxNumPass = numPass;
        minMod = minm;
    }

    /**
     * Removes the <code>node</code> from the cluster <code>comm</code>
     * <code>tot</code> for this cluster will be reduced by the weighted degree of <code>node</code>
     * <code>in</code> for this cluster will be reduced by the number of self-loops of <code>node</code> and two times <code>dnodecomm</code>
     *
     * @param node      the ID of the node to be removed from <code>comm</code>
     * @param comm      the ID of the cluster from which <code>node</code> will be removed
     * @param dnodecomm total weights of edges from <code>node</code> to all nodes in the cluster <code>comm</code>
     */
    private void remove(int node, int comm, double dnodecomm) {
        tot[comm] -= g.weightedDegree(node);
        in[comm] -= 2 * dnodecomm + g.numSelfLoops(node);
        nodeToCom[node] = -1;
    }

    /**
     * Adds the <code>node</code> into the cluster <code>comm</code>
     * <code>tot</code> for this cluster will be increased by the weighted degree of <code>node</code>
     * <code>in</code> for this cluster will be increased by the number of self-loops of <code>node</code> and two times <code>dnodecomm</code>
     *
     * @param node      the ID of the node to be added to <code>comm</code>
     * @param comm      the ID of the cluster to which <code>node</code> will be added
     * @param dnodecomm total weights of edges from <code>node</code> to all nodes in the cluster <code>comm</code>
     */
    private void insert(int node, int comm, double dnodecomm) {
        tot[comm] += g.weightedDegree(node);
        in[comm] += 2 * dnodecomm + g.numSelfLoops(node);
        nodeToCom[node] = comm;
    }

    /**
     * Calculates modularity gain from moving the <code>node</code> to the cluster <code>comm</code>.
     * Note that the modularity gain here is equal to g.doubleSumWeight times the modularity gain in the paper.
     *
     * @param node      the ID of the node to be added to <code>comm</code>
     * @param comm      the ID of the cluster to which <code>node</code> will be added
     * @param dnodecomm total weights of edges from <code>node</code> to all nodes in the cluster <code>comm</code>
     * @return  the modularity gain from the movement
     */
    private double modGain(int node, int comm, double dnodecomm) {
        double totc = tot[comm];
        double degc = g.weightedDegree(node);
        double m2 = g.doubleSumWeight;

        return (dnodecomm - totc * degc / m2);
    }

    /**
     * Calculates modularity of the current partition (derived from the modularity formula)
     *
     * @return the modularity value
     */
    public double modularity() {
        double q = 0.0;
        double m2 = g.doubleSumWeight;

        for (int i = 0; i < size; i++) {
            if (tot[i] > 0) {
                q += (in[i] / m2) - (tot[i] / m2) * (tot[i] / m2);
            }
        }

        return q;

    }

    /**
     * Finds all clusters of the adjacent nodes of the <code>node</code> along with the total weights of edges to the clusters' members.
     * 
     * @param node  a node of interest
     * @return a Map of clusters to the total weights of edges between <code>node</code> and their neighbors.
     */
    public Map<Integer, Double> getNeighCommToWeight(int node) {
        Map<Integer, Double> res = new HashMap();
        int index = g.startingIndex(node);
        int deg = g.getDegree(node);

        res.put(nodeToCom[node], 0.0);

        // visit each neighbor
        for (int i = 0; i < deg; i++) {
            // neigh is a neighbor's id (number)
            int neigh = g.links[index + i];
            // neighCommNo is the cluster of neigh
            int neighComm = nodeToCom[neigh];
            // neighWeight is the weight of the edge between the node and neigh
            double neighWeight = (g.weights != null) ? g.weights[index + i] : 1;

            // take care only clusters different from the node's cluster
            if (neigh != node) {
                if (res.containsKey(neighComm)) {
                    // add up the weight of the edge between the node and this neighbor.
                    res.put(neighComm, res.get(neighComm) + neighWeight);
                } else {
                    res.put(neighComm, neighWeight);
                }
            }
        }
        return res;
    }

    /**
     * Performs phase 1 of the Louvain algorithm
     * 
     * @return the updated modularity of the partition of the graph
     */
    public double phase1() {
        boolean improvement = false;
        // count number of passes
        int numPassDone = 0;
        double newMod = modularity();
        double curMod = newMod;

        /*
         * Below codes were used to randomly begin phase 1 with different starting nodes.
         * To make the results the same every time we run the program, I have commented these out.
         */
//        Random r = new Random(System.currentTimeMillis());
//        Vertex[] verList = (Vertex[])graph.getVertices().toArray();
//        Vertex[] verList = (Vertex[])graph.getVertices().toArray(new Vertex[graph.getVertices().size()]);
//        Integer []randomOrder = (Integer [])graph.getVertices().toArray(new Integer[graph.getVertices().size()]);
//        int randomOrder[] = new int [size];
//        for (int i = 0; i < randomOrder.length; i++)
//            randomOrder[i] = i;
//        for (int i = 0; i < size-1; i++){
//            int rand_pos = r.nextInt(size-i)+i;
////            System.out.println(rand_pos);
//            int tmp = randomOrder[i];
//            randomOrder[i] = randomOrder[rand_pos];
//            randomOrder[rand_pos] = tmp;
//        }

        do {
            curMod = newMod;
            improvement = false;
            numPassDone++;
            // iterate over all nodes
            for (int nodeTemp = 0; nodeTemp < size; nodeTemp++) {
                int node = nodeTemp;
//                int node = randomOrder[nodeTemp];
                // nodeComm = the cluster of the node
                int nodeComm = nodeToCom[node];

                // Retrieves a Map of neighboring nodes to their edges' weights to the node
                Map<Integer, Double> ncommMap = getNeighCommToWeight(node);
                // Firstly, removes the node from its cluster
                remove(node, nodeComm, ncommMap.get(nodeComm));

                int bestComm = nodeComm;
                double bestNumLinks = 0;
                double bestIncrease = 0;

                // iterate over each neighbor's distinct cluster
                for (Entry it : ncommMap.entrySet()) {
                    // Calculates gain in modularity from moving the node to this cluster (it.getKey())
                    double increase = modGain(node, (Integer) it.getKey(), (Double) it.getValue());
                    // Updates the best increase in modularity gain and relevant information
                    if (increase > bestIncrease) {
                        bestComm = (Integer) it.getKey();
                        bestNumLinks = (Double) it.getValue();
                        bestIncrease = increase;
                    }
                }

                // Inserts the node into the best cluster (which results in the largest increase in modularity)
                insert(node, bestComm, bestNumLinks);

                // Checks if the new cluster is the same as before
                if (bestComm != nodeComm) {
                    improvement = true;
                }

            }

            newMod = modularity();
            System.out.println("Pass " + (numPassDone) + " modularity: " + newMod);
        } while (improvement && newMod - curMod > minMod && numPassDone != maxNumPass);

        return newMod;
    }

    /**
     * Performs phase 2 of the Louvain algorithm
     *
     * @return the new Graph created from the results of phase 1
     */
    public LouvainGraph phase2() {
        // create a new array to store new nodes (corresponding to the resulting clusters of phase 1)
        int[] renumber = new int[size];
        Arrays.fill(renumber, -1);
        // Counts the number of nodes per cluster
        for (int node = 0; node < size; node++) {
            renumber[nodeToCom[node]]++;
        }

        int newSize = 0;
        // Calculates the number of the new nodes (size)
        for (int i = 0; i < size; i++) {
            if (renumber[i] != -1) {
                renumber[i] = newSize++;
            }
        }

        // Each slot (corresponding to a new node or old cluster) of commNodes contains a list of its members (old nodes).
        ArrayList<Integer>[] commNodes = new ArrayList[newSize];
        // Adds each member to the relevant list.
        for (int node = 0; node < size; node++) {
            if (commNodes[renumber[nodeToCom[node]]] == null) {
                commNodes[renumber[nodeToCom[node]]] = new ArrayList<Integer>();
            }
            commNodes[renumber[nodeToCom[node]]].add(node);
        }

        /*
         * Creates new Louvain graph
         */
        LouvainGraph g2 = new LouvainGraph();
        g2.numNodes = commNodes.length;
        g2.degrees = new int[commNodes.length];
        g2.links = new int[g.links.length];
        g2.weights = new double[g.links.length];
        g2.isWeighted = true;

        int where = 0;
        int commDeg = commNodes.length;
        // Iterates over each new node (old cluster)
        for (int comm = 0; comm < commDeg; comm++) {
            // neighToEdgeWeightMap stores a Map from neighbors' IDs to the weights of the edges from them to the (new) node
            Map<Integer, Double> neighToEdgeWeightMap = new HashMap();
            // the size of old cluster (commNodes[comm])
            int commSize = commNodes[comm].size();
            // Iterates over each old node (of the old cluster)
            for (int node = 0; node < commSize; node++) {
                int index = g.startingIndex(commNodes[comm].get(node));
                int deg = g.getDegree(commNodes[comm].get(node));
                // Iterates over each neighbor of the old node
                for (int i = 0; i < deg; i++) {
                    int neigh = g.links[index + i];
                    // neighCommNo is the new id of the (old) cluster of the neighbor neigh
                    int neighCommNo = renumber[nodeToCom[neigh]];
                    double neighWeight = (g.weights != null) ? g.weights[index + i] : 1;

                    // Adds the weight of the edge to the node
                    if (neighToEdgeWeightMap.containsKey(neighCommNo)) {
                        neighToEdgeWeightMap.put(neighCommNo, neighToEdgeWeightMap.get(neighCommNo) + neighWeight);
                    } else {
                        neighToEdgeWeightMap.put(neighCommNo, neighWeight);
                    }
                }
            }

            /* Set each element's value of the field degrees
             * Note that this array stores cumulative degrees.
             */
            g2.degrees[comm] = (comm == 0) ? neighToEdgeWeightMap.size() : g2.degrees[comm - 1] + neighToEdgeWeightMap.size();
            g2.numLinks += neighToEdgeWeightMap.size();
            // After pass 1, numLink is equal to 2*numEdges
            for (Entry it : neighToEdgeWeightMap.entrySet()) {
                g2.doubleSumWeight += (Double) it.getValue();
                g2.links[where] = (Integer) it.getKey();
                g2.weights[where] = (Double) it.getValue();
                where++;
            }
        }

        // Reduces the size of g2.links to the real size
        int[] tempLinks = new int[where];
        for (int i = 0; i < tempLinks.length; i++) {
            tempLinks[i] = g2.links[i];
        }
        g2.links = tempLinks;

        // Reduces the size of g2.weights to the real size
        double[] tempWeights = new double[where];
        for (int i = 0; i < tempWeights.length; i++) {
            tempWeights[i] = g2.weights[i];
        }
        g2.weights = tempWeights;
        return g2;
    }
}
