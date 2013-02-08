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
import java.util.StringTokenizer;

/**
 * A graph class used in the Louvain algorithm.
 * Main fields are <code>degrees</code>, <code>links</code>, and <code>weights</code> arrays.
 * Each slot of <code>degrees</code> contains information about cumulative degrees from the first node up to the slot's corresponding node.
 * <code>links</code> contains information about graph edges, each of whose slot store the id (number) of the pair node of the slot's corresponding node.
 * <code>weights</code> corresponds to <code>links</code>; it tells us the weight of each corresponding edge.
 * Note that <code>links</code> and <code>weights</code> contains a certain edge twice, for example, an edge between node #1 and #2 will be stored in the slots of node #1
 * and node #2 as well.
 * Thus, the number of these arrays will be twice the number of edges.
 * 
 * @author Knacky
 */
public class LouvainGraph {

    public boolean isWeighted = false;
    public int numNodes;
    public int numLinks;
    public double doubleSumWeight;
    public int[] degrees;
    public int[] links;
    public double[] weights;

    /**
     * Default constructor
     */
    public LouvainGraph() {
        numNodes = 0;
        numLinks = 0;
        doubleSumWeight = 0;
    }

    /**
     * A constructor that uses three arrays related to graph edges to initialize a graph.
     * Assume that all array lengths are the same.
     * @param sArr  an array of source nodes
     * @param dArr  an array of destination nodes
     * @param wArr  an array of edge weights
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public LouvainGraph(int[] sArr, int[] dArr, double[] wArr) throws FileNotFoundException, IOException {
        //isWeighted is determined implicitly by the wArr array.
        isWeighted = (wArr == null) ? false : true;
//        int tNumLinks = sArr.length;
        // src: source node number; dest: destination node number
        int src, dest;
        double weight = 1;
        ArrayList<Integer> tDegrees = new ArrayList<Integer>();
        //count degree
        for (int i = 0; i < sArr.length; i++) {
            /*
             * below codes are used to output something during loading.
             */
//            if (i % 10000 == 0) {
//                System.out.print(".");
//            }
            src = sArr[i];
            dest = dArr[i];

            /*
             * tDegrees will be gradually expanded while sArr and dArr are read.
             * It will infer number of nodes from the information of src and dest
             * if tDegrees size is less than maximum between src and dest plus 1, we find a new node from them.
             * EX. if src = 3, dest = 5, and tDegrees is 4 slots large, then it implicitly means that there are new node numbers 4 and 5.
             * Then, we add new slots into the tDegrees with initial value of 0.
             */
            if (tDegrees.size() <= Math.max(src, dest) + 1) {
//                tDegrees.ensureCapacity(Math.max(src, dest)+1);
                for (int j = tDegrees.size(); j < Math.max(src, dest) + 1; j++) {
                    tDegrees.add(j, 0);
                }
            }
            // increase degree of node src by 1
            tDegrees.set(src, tDegrees.get(src) + 1);
            // careful not to add degree twice.
            if (src != dest) {
                tDegrees.set(dest, tDegrees.get(dest) + 1);
            }
        }

        // Each slot of degrees contains the CUMULATIVE degree from the first node up to that slot's node.
        // The reason to use CUMULATIVE degrees is to allow the below calculation scheme.
        degrees = new int[tDegrees.size()];
        // Number of nodes can be inferred from degrees length.
        numNodes = degrees.length;
        int sum = 0;
        for (int k = 0; k < tDegrees.size(); k++) {
            sum += tDegrees.get(k);
            degrees[k] = sum;

        }
        /* The number of links is equal to the total number of degrees of all nodes divided by 2.
         */
        numLinks = degrees[degrees.length - 1] / 2;
        /*
         * links and weights have the same sizes as the total number of degrees of ALL nodes.
         * Each slot of links stores the id or number of the pair node of the corresponding node.
         * Each slot of weights stores the weight of the edge between the corresponding node and its pair.
         */
        links = new int[degrees[degrees.length - 1]];
        weights = new double[degrees[degrees.length - 1]];

        /* renumber is used to track where should the new information inserted, for each node.
         * Each slot of renumber counts how many edges the corresponding node has so far.
         * Please see below loop for example.
         */
        int renumber[] = new int[degrees.length];
        int numSelfLoop = 0;

        for (int i = 0; i < sArr.length; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }
            src = sArr[i];
            dest = dArr[i];

            //detect self-loop
            if (src == dest) {
                numSelfLoop++;
                // if src is 0 (node #0), we can put its pair node (src) to renumber[src]-th slot of the links array directly.
                if (src == 0) {
                    links[renumber[src]] = src;
                } /* Or else, we need to add the cumulative degrees up to node #degrees[src-1] to renumber[src] to find correct position to store
                 * its pair node (src).
                 */ else {
                    links[degrees[src - 1] + renumber[src]] = src;
                }
                if (isWeighted) {
                    weight = wArr[i];
                    // To store information about weights, do the same as above.
                    if (src == 0) {
                        weights[renumber[src]] = weight;
                    } else {
                        weights[degrees[src - 1] + renumber[src]] = weight;
                    }
                    doubleSumWeight += weight;
                }
                // increase renumber[src] by 1, meaning that it has one more pair.
                renumber[src]++;
            } else {
                // if src is 0 (node #0), we can put its pair node (dest) to renumber[src]-th slot of the links array directly.
                if (src == 0) {
                    links[renumber[src]] = dest;
                } /* Or else, we need to add the cumulative degrees up to node #degrees[src-1] to renumber[src] to find correct position to store
                 * its pair node (dest).
                 */ else {
                    links[degrees[src - 1] + renumber[src]] = dest;
                }
                // To store information of node #dest, do the same as node #src
                if (dest == 0) {
                    links[renumber[dest]] = src;
                } else {
                    links[degrees[dest - 1] + renumber[dest]] = src;
                }
                // in case the graph is weighted,
                if (isWeighted) {
                    weight = wArr[i];
                    // To store information about weights, do the same as above.
                    if (src == 0) {
                        weights[renumber[src]] = weight;
                    } else {
                        weights[degrees[src - 1] + renumber[src]] = weight;
                    }
                    if (dest == 0) {
                        weights[renumber[dest]] = weight;
                    } else {
                        weights[degrees[dest - 1] + renumber[dest]] = weight;
                    }
                    doubleSumWeight += 2 * weight;
                }
                // increase renumber[src] and [dest] by 1, meaning that they have one more pairs.
                renumber[src]++;
                renumber[dest]++;
            }

        }
        if (!isWeighted) {
            weights = null;
            doubleSumWeight += (degrees[degrees.length - 1] - numSelfLoop);
        }
    }

    /**
     * A constructor that reads the <code>file</file> content to find edges and their weights information.
     * Number of nodes is inferred from the edge list information.
     * Example: (If file contains the following text information.
     * 1 2 3
     * 1 3 1
     * 2 3 4
     * The above means this graph contains 3 nodes, and 3 edges; An edge between node 1 and 2 has weight of 3, and so on.
     * @param file  a File containing edge and weight information
     * @throws FileNotFoundException
     * @throws IOException
     */
    public LouvainGraph(File file) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int tNumLinks = 0;
        String st;
        // stn tokenizes each line into words
        StringTokenizer stn;
        // src: source node number; dest: destination node number
        int src, dest;
        double weight = 1;
        ArrayList<Integer> tDegrees = new ArrayList<Integer>();

        // count degrees for the first line.
        if ((st = br.readLine()) != null) {
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());
            // if there are three words per line, this means this graph is weighted.
            if (stn.hasMoreTokens()) {
                isWeighted = true;
            } else {
                isWeighted = false;
            }

            /*
             * tDegrees will be gradually expanded while src and dest are read.
             * It will infer number of nodes from the information of src and dest
             * if tDegrees size is less than maximum between src and dest plus 1, we find a new node from them.
             * EX. if src = 3, dest = 5, and tDegrees is 4 slots large, then it implicitly means that there are new node numbers 4 and 5.
             * Then, we add new slots into the tDegrees with initial value of 0.
             */
            if (tDegrees.size() <= Math.max(src, dest) + 1) {
//                tDegrees.ensureCapacity(Math.max(src, dest)+1);
                for (int j = tDegrees.size(); j < Math.max(src, dest) + 1; j++) {
                    tDegrees.add(j, 0);
                }
            }
            // increase degree of node src by 1
            tDegrees.set(src, tDegrees.get(src) + 1);
            // careful not to add degree twice.
            if (src != dest) {
                tDegrees.set(dest, tDegrees.get(dest) + 1);
            }
            tNumLinks++;

        }

        // count degrees for the rest of lines
        while ((st = br.readLine()) != null) {
            /*
             * below codes are used to output something during loading.
             */
            if (tNumLinks % 10000 == 0) {
                System.out.print(".");
            }
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());

            /*
             * tDegrees will be gradually expanded while src and dest are read.
             * It will infer number of nodes from the information of src and dest
             * if tDegrees size is less than maximum between src and dest plus 1, we find a new node from them.
             * EX. if src = 3, dest = 5, and tDegrees is 4 slots large, then it implicitly means that there are new node numbers 4 and 5.
             * Then, we add new slots into the tDegrees with initial value of 0.
             */
            if (tDegrees.size() <= Math.max(src, dest) + 1) {
//                tDegrees.ensureCapacity(Math.max(src, dest)+1);
                for (int j = tDegrees.size(); j < Math.max(src, dest) + 1; j++) {
                    tDegrees.add(j, 0);
                }
            }
            // increase degree of node src by 1
            tDegrees.set(src, tDegrees.get(src) + 1);
            // careful not to add degree twice.
            if (src != dest) {
                tDegrees.set(dest, tDegrees.get(dest) + 1);
            }
            tNumLinks++;
        }
        br.close();

        // Each slot of degrees contains the CUMULATIVE degree from the first node up to that slot's node.
        // The reason to use CUMULATIVE degrees is to allow the below calculation scheme.
        degrees = new int[tDegrees.size()];
        // Number of nodes can be inferred from degrees length.
        numNodes = degrees.length;
        int sum = 0;
        for (int k = 0; k < tDegrees.size(); k++) {
            sum += tDegrees.get(k);
            degrees[k] = sum;

        }

        /* The number of links is equal to the total number of degrees of all nodes divided by 2.
         */
        numLinks = degrees[degrees.length - 1] / 2;
        /*
         * links and weights have the same sizes as the total number of degrees of ALL nodes.
         * Each slot of links stores the id or number of the pair node of the corresponding node.
         * Each slot of weights stores the weight of the edge between the corresponding node and its pair.
         */
        links = new int[degrees[degrees.length - 1]];
        weights = new double[degrees[degrees.length - 1]];

        /* renumber is used to track where should the new information inserted, for each node.
         * Each slot of renumber counts how many edges the corresponding node has so far.
         * Please see below loop for example.
         */
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//        br = new BufferedReader(new InputStreamReader(fis));
        int index = 0;
        int renumber[] = new int[degrees.length];
        int numSelfLoop = 0;
        while ((st = br.readLine()) != null) {
            if (index % 10000 == 0) {
                System.out.print(".");
            }
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());

            // detect self-loops
            if (src == dest) {
                numSelfLoop++;
                // if src is 0 (node #0), we can put its pair node (src) to renumber[src]-th slot of the links array directly.
                if (src == 0) {
                    links[renumber[src]] = src;
                } 
                /* Or else, we need to add the cumulative degrees up to node #degrees[src-1] to renumber[src] to find correct position to store 
                 * its pair node (src).
                 */
                else {
                    links[degrees[src - 1] + renumber[src]] = src;
                }
                if (isWeighted) {
                    weight = Double.parseDouble(stn.nextToken());
                    if (src == 0) {
                        weights[renumber[src]] = weight;
                    } else {
                        weights[degrees[src - 1] + renumber[src]] = weight;
                    }

                    doubleSumWeight += weight;
                }
                // increase renumber[src] by 1, meaning that it has one more pair.
                renumber[src]++;
            } else {
                // if src is 0 (node #0), we can put its pair node (dest) to renumber[src]-th slot of the links array directly.
                if (src == 0) {
                    links[renumber[src]] = dest;
                } /* Or else, we need to add the cumulative degrees up to node #degrees[src-1] to renumber[src] to find correct position to store
                 * its pair node (dest).
                 */else {
                    links[degrees[src - 1] + renumber[src]] = dest;
                }
                // To store information of node #dest, do the same as node #src
                if (dest == 0) {
                    links[renumber[dest]] = src;
                } else {
                    links[degrees[dest - 1] + renumber[dest]] = src;
                }
                // in case the graph is weighted,
                if (isWeighted) {
                    weight = Double.parseDouble(stn.nextToken());
                    // To store information about weights, do the same as above.
                    if (src == 0) {
                        weights[renumber[src]] = weight;
                    } else {
                        weights[degrees[src - 1] + renumber[src]] = weight;
                    }
                    if (dest == 0) {
                        weights[renumber[dest]] = weight;
                    } else {
                        weights[degrees[dest - 1] + renumber[dest]] = weight;
                    }
                    doubleSumWeight += 2 * weight;
                }
                // increase renumber[src] and [dest] by 1, meaning that they have one more pairs.
                renumber[src]++;
                renumber[dest]++;
            }

            index++;
        }
        if (!isWeighted) {
            weights = null;
            doubleSumWeight += (degrees[degrees.length - 1] - numSelfLoop);
        }

    }

    /**
     * A constructor that uses a <code>stream</code> containing information about edges to initialize a graph.
     * @param stream    a String containing information about edges
     * @throws FileNotFoundException
     * @throws IOException
     */
    public LouvainGraph(String stream) {
        int tNumLinks = 0;
        String st = "";

        // src: source node number; dest: destination node number
        int src, dest;
        double weight = 1;
        // streamReader tokenizes a stream into lines.
        StringTokenizer streamReader = new StringTokenizer(stream, "\n");
        // stn tokenizes each line into words
        StringTokenizer stn;
        ArrayList<Integer> tDegrees = new ArrayList<Integer>();

        // count degrees for the first line.
        if (streamReader.hasMoreTokens()) {
            st = streamReader.nextToken();
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());

            // if there are three words per line, this means this graph is weighted.
            if (stn.hasMoreTokens()) {
                isWeighted = true;
            } else {
                isWeighted = false;
            }

            /*
             * tDegrees will be gradually expanded while src and dest are read.
             * It will infer number of nodes from the information of src and dest
             * if tDegrees size is less than maximum between src and dest plus 1, we find a new node from them.
             * EX. if src = 3, dest = 5, and tDegrees is 4 slots large, then it implicitly means that there are new node numbers 4 and 5.
             * Then, we add new slots into the tDegrees with initial value of 0.
             */
            if (tDegrees.size() <= Math.max(src, dest) + 1) {
//                tDegrees.ensureCapacity(Math.max(src, dest)+1);
                for (int j = tDegrees.size(); j < Math.max(src, dest) + 1; j++) {
                    tDegrees.add(j, 0);
                }
            }
            // increase degree of node src by 1
            tDegrees.set(src, tDegrees.get(src) + 1);
            // careful not to add degree twice.
            if (src != dest) {
                tDegrees.set(dest, tDegrees.get(dest) + 1);
            }
            tNumLinks++;

        }
        // count degrees for the rest of lines
        while (streamReader.hasMoreTokens()) {
            st = streamReader.nextToken();
            /*
             * below codes are used to output something during loading.
             */
            if (tNumLinks % 10000 == 0) {
                System.out.print(".");
            }
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());

            /*
             * tDegrees will be gradually expanded while src and dest are read.
             * It will infer number of nodes from the information of src and dest
             * if tDegrees size is less than maximum between src and dest plus 1, we find a new node from them.
             * EX. if src = 3, dest = 5, and tDegrees is 4 slots large, then it implicitly means that there are new node numbers 4 and 5.
             * Then, we add new slots into the tDegrees with initial value of 0.
             */
            if (tDegrees.size() <= Math.max(src, dest) + 1) {
//                tDegrees.ensureCapacity(Math.max(src, dest)+1);
                for (int j = tDegrees.size(); j < Math.max(src, dest) + 1; j++) {
                    tDegrees.add(j, 0);
                }
            }
            // increase degree of node src by 1
            tDegrees.set(src, tDegrees.get(src) + 1);
            // careful not to add degree twice.
            if (src != dest) {
                tDegrees.set(dest, tDegrees.get(dest) + 1);
            }
            tNumLinks++;
        }

        // Each slot of degrees contains the CUMULATIVE degree from the first node up to that slot's node.
        // The reason to use CUMULATIVE degrees is to allow the below calculation scheme.
        degrees = new int[tDegrees.size()];
        // Number of nodes can be inferred from degrees length.
        numNodes = degrees.length;
        int sum = 0;
        for (int k = 0; k < tDegrees.size(); k++) {
            sum += tDegrees.get(k);
            degrees[k] = sum;

        }

        /* The number of links is equal to the total number of degrees of all nodes divided by 2.
         */
        numLinks = degrees[degrees.length - 1] / 2;
        /*
         * links and weights have the same sizes as the total number of degrees of ALL nodes.
         * Each slot of links stores the id or number of the pair node of the corresponding node.
         * Each slot of weights stores the weight of the edge between the corresponding node and its pair.
         */
        links = new int[degrees[degrees.length - 1]];
        weights = new double[degrees[degrees.length - 1]];

        /* renumber is used to track where should the new information inserted, for each node.
         * Each slot of renumber counts how many edges the corresponding node has so far.
         * Please see below loop for example.
         */
        int renumber[] = new int[degrees.length];
        int numSelfLoop = 0;

        streamReader = new StringTokenizer(stream, "\n");
        int index = 0;

        while (streamReader.hasMoreTokens()) {
            st = streamReader.nextToken();
            if (index % 10000 == 0) {
                System.out.print(".");
            }
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());

            // detect self-loops
            if (src == dest) {
                numSelfLoop++;
                // if src is 0 (node #0), we can put its pair node (src) to renumber[src]-th slot of the links array directly.
                if (src == 0) {
                    links[renumber[src]] = src;
                } /* Or else, we need to add the cumulative degrees up to node #degrees[src-1] to renumber[src] to find correct position to store
                 * its pair node (src).
                 */ else {
                    links[degrees[src - 1] + renumber[src]] = src;
                }
                if (isWeighted) {
                    weight = Double.parseDouble(stn.nextToken());
                    if (src == 0) {
                        weights[renumber[src]] = weight;
                    } else {
                        weights[degrees[src - 1] + renumber[src]] = weight;
                    }

                    doubleSumWeight += weight;
                }
                // increase renumber[src] by 1, meaning that it has one more pair.
                renumber[src]++;
            } else {
                // if src is 0 (node #0), we can put its pair node (dest) to renumber[src]-th slot of the links array directly.
                if (src == 0) {
                    links[renumber[src]] = dest;
                } /* Or else, we need to add the cumulative degrees up to node #degrees[src-1] to renumber[src] to find correct position to store
                 * its pair node (dest).
                 */ else {
                    links[degrees[src - 1] + renumber[src]] = dest;
                }
                // To store information of node #dest, do the same as node #src
                if (dest == 0) {
                    links[renumber[dest]] = src;
                } else {
                    links[degrees[dest - 1] + renumber[dest]] = src;
                }
                // in case the graph is weighted,
                if (isWeighted) {
                    weight = Double.parseDouble(stn.nextToken());
                    // To store information about weights, do the same as above.
                    if (src == 0) {
                        weights[renumber[src]] = weight;
                    } else {
                        weights[degrees[src - 1] + renumber[src]] = weight;
                    }
                    if (dest == 0) {
                        weights[renumber[dest]] = weight;
                    } else {
                        weights[degrees[dest - 1] + renumber[dest]] = weight;
                    }
                    doubleSumWeight += 2 * weight;
                }
                // increase renumber[src] and [dest] by 1, meaning that they have one more pairs.
                renumber[src]++;
                renumber[dest]++;
            }
            index++;
        }
        if (!isWeighted) {
            weights = null;
            doubleSumWeight += (degrees[degrees.length - 1] - numSelfLoop);
        }
    }

    /**
     * Returns the number of degrees of the <code>node</code>.
     * @param node  node number
     * @return number of degrees
     */
    public int getDegree(int node) {
//        assert(node >= 0 && node < numNodes);
        if (node == 0) {
            return degrees[0];
        } else {
            // remind that degrees store information accumulatively.
            return degrees[node] - degrees[node - 1];
        }
    }

    /**
     * Returns the number of the first slot that contains information about the <code>node</code>.
     * @param node  node number
     * @return starting index
     */
    public int startingIndex(int node) {
//        assert(node >= 0 && node < numNodes);
        // node number 0's information starts at slot 0; For others, it depends on the cumulative neighbors of all nodes prior to them, i.e., degrees[node-1].
        if (node == 0) {
            return 0;
        } else {
            return degrees[node - 1];
        }
    }

    /**
     * Returns weighted number of self-loop of the <code>node</code>.
     * @param node  node number
     * @return number of self-loop (or weight of self-loop)
     */
    public double numSelfLoops(int node) {
//        assert(node >= 0 && node < numNodes);
        int index = startingIndex(node);
        // Below loop is used to find the slot storing self-loop edge.
        for (int i = 0; i < getDegree(node); i++) {
            if (links[i + index] == node) {
                // if the weighted graph, return weight of the self-loop edge; Or else return 1
                if (weights != null) {
                    return weights[i + index];
                } else {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Returns weighted degree of the <code>node</code>.
     * @param node  node number
     * @return weighted degree
     */
    public double weightedDegree(int node) {
//        assert(node >= 0 && node < numNodes);
        /* index is starting slot number of weights array to begin with.
         * It will point to the first slot that contain information of the "node".
         */
        int index = startingIndex(node);
        if (weights == null) {
            // if the graph is unweighted, just return the degree of the node
            return getDegree(node);
        } else {
            // Sum up the weight of each incident edge of the node.
            double res = 0;
            for (int i = 0; i < getDegree(node); i++) {
                res += weights[i + index];
            }
            return res;
        }

    }
}
