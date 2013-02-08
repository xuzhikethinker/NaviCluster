/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author knacky
 */
public class MNetConverter {
    static String fileName = "src/datasets/atted-coexp-3highest.mnet";;
    public static void main(String[] saArgs) {
        saArgs = new String[1];
        saArgs[0] = fileName;
        // File name
        if ((saArgs == null) || (saArgs.length == 0)) {
            System.out.println("Error: Input file name");
            return;
        }

        File oFile = new File(saArgs[0]);
        String directory = "";
        String sNodeFile = null;
        String sEdgeFile = null;
        int iIndex = -1;

        if ((!oFile.exists()) || (!oFile.isFile())) {
            System.out.println("Error: No such file exists.");
            return;
        }

//        sNodeFile = oFile.getName();
        sNodeFile = oFile.getPath();
        
        iIndex = sNodeFile.lastIndexOf(".");
        if (iIndex != -1) {
            sNodeFile = sNodeFile.substring(0, iIndex);
        }
        sEdgeFile = sNodeFile;
        sNodeFile += ".node";
        sEdgeFile += ".edge";

        // File read
        BufferedReader oReader = null;
        String sLine = null;
        String[] saData = null;
        Vector<String> vNodeID = new Vector<String>();
        Vector<Node> vNode = new Vector<Node>();
        Vector<Edge> vEdge = new Vector<Edge>();
        Node oNode = null;
        Edge oEdge = null;
        int iMode = 0;
        double dWeight = 0.0;
        int iTmpSrc = 0;
        int iTmpTrg = 0;

        try {
            oReader = new BufferedReader(new InputStreamReader(new FileInputStream(oFile)));
            while ((sLine = oReader.readLine()) != null) {
                if (sLine.startsWith("*Vertices")) {
                    iMode = 1;
                    continue;
                }
                if (sLine.startsWith("*Edges")) {
                    iMode = 2;
                    continue;
                }

                saData = sLine.split("\t");
                if (iMode == 1) {
                    if (saData.length < 2) {
                        continue;
                    }

                    oNode = new Node();
                    oNode.sName = saData[1];

                    if (saData.length >= 4) {
                        oNode.sDbName = "SGD";
                        oNode.sDbID = saData[3];
                        if (saData.length >= 6) {
                            oNode.sOntology = saData[5];
                        }
                    }
                    vNodeID.addElement(saData[0]);
                    vNode.addElement(oNode);
                } else if (iMode == 2) {
                    if (saData.length < 3) {
                        continue;
                    }

                    if ((vNodeID.indexOf(saData[0]) != -1) && (vNodeID.indexOf(saData[1]) != -1)) {
                        oEdge = new Edge();
                        iTmpSrc = Integer.parseInt(saData[0]);
                        iTmpTrg = Integer.parseInt(saData[1]);

//                        if (iTmpSrc > iTmpTrg) {
//                            oEdge.sSource = vNode.elementAt(vNodeID.indexOf(saData[1])).sName;
//                            oEdge.sTarget = vNode.elementAt(vNodeID.indexOf(saData[0])).sName;
//                        } else {
                            oEdge.sSource = vNode.elementAt(vNodeID.indexOf(saData[0])).sName;
                            oEdge.sTarget = vNode.elementAt(vNodeID.indexOf(saData[1])).sName;
//                        }
                        dWeight = Double.parseDouble(saData[2]);
                        oEdge.sWeight = "" + dWeight;

//                        if (iTmpSrc == 770)
//                        {
//                            System.out.println("source "+oEdge.sSource+" "+oEdge.sTarget+" "+oEdge.sWeight);
//                        }
                        vEdge.addElement(oEdge);
                    }

                }
            }
            oReader.close();
        } catch (Exception e) {
            System.out.println("I/O Error in reading file.");
            return;
        }

        // File save
        PrintWriter oWriter = null;
        int iCnt = vNode.size();
        int iCounter = 0;

        try {
            oWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sNodeFile)))));
            oWriter.println("display_name	source_db_name	source_db_id	prop_info");
            for (iCounter = 0; iCounter < iCnt; iCounter++) {
                oNode = vNode.elementAt(iCounter);
                oWriter.println(oNode.sName + "\t" + oNode.sDbName + "\t" + oNode.sDbID + "\t" + oNode.sOntology);
            }
            oWriter.close();

            iCnt = vEdge.size();
            oWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sEdgeFile)))));
            oWriter.println("source_node	target_node	weight");
            for (iCounter = 0; iCounter < iCnt; iCounter++) {
//                if (oEdge.sSource.equals("CDC25")) {
//                    System.out.println("source " + oEdge.sSource + " " + oEdge.sTarget + " " + oEdge.sWeight);
//                }
                oEdge = vEdge.elementAt(iCounter);
                oWriter.println(oEdge.sSource + "\t" + oEdge.sTarget + "\t" + oEdge.sWeight);
            }
            oWriter.close();
        } catch (Exception e) {
            System.out.println("I/O Error in saving file.");
            return;
        }
    }
}

class Node {

    public String sName = "";
    public String sDbName = "";
    public String sDbID = "";
    public String sOntology = "";
}

class Edge {

    public String sSource = "";
    public String sTarget = "";
    public String sWeight = "";
}


