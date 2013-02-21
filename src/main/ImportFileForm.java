package main;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import java.awt.Dimension;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;

/**
 * Dialog box for importing network files in forms of a node list file and edge list file
 * Feed the input from node and edge files to three arrays of Louvain Algorithm, sArr, dArr, and wArr
 * @author Y.Iihara (modified by Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo)
 */
public class ImportFileForm extends JDialog implements ActionListener {

    private static ImportFileForm oMySelf = null;
    private Graph network = null;
    private Map<Integer, BioObject> IdToNodeMap = new HashMap<Integer, BioObject>();
    private Set<Integer> disconnectedNodeSet = new HashSet<Integer>();
    private double maxBioEdgeWeight = 0;
    private double minBioEdgeWeight = Double.MAX_VALUE;
    /* source node array */
    private int[] sArr = null;
    /* destination node array */
    private int[] dArr = null;
    /* weight array */
    private double[] wArr = null;
    private File nodeListFile = null;
    private File edgeListFile = null;
    
    private JFrame ownerFrame = null;
    private JTextField oTxtNodeListFile = new JTextField(32);
    private JTextField oTxtEdgeListFile = new JTextField(32);
    private JButton oBtnNodeBrowse = null;
    private JButton oBtnEdgeBrowse = null;
    private JButton oBtnOk = null;
    private JButton oBtnCancel = null;
    
    private static final String BTN_BROWSE = "Browse...";
    private static final String BTN_BROWSE_NODE = "BROWSE_NODE";
    private static final String BTN_BROWSE_EDGE = "BROWSE_EDGE";
    private static final String BTN_OK = "OK";
    private static final String BTN_CANCEL = "Cancel";

    public ImportFileForm(JFrame owner) {
        super(owner);
        ownerFrame = owner;
        createUI();

        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
            }
        });
    }

    /**
     * Supply static instance of this class.
     * (Use factory design pattern)
     */
    public static ImportFileForm getInstance(JFrame owner) {
        if (oMySelf == null) {
            oMySelf = new ImportFileForm(owner);
        }

        oMySelf.oBtnNodeBrowse.setEnabled(true);
        oMySelf.oBtnEdgeBrowse.setEnabled(true);
        oMySelf.oBtnOk.setEnabled(true);
        oMySelf.oBtnCancel.setEnabled(true);

        return oMySelf;
    }

    /**
     * Create user-interface of network loading dialog
     */
    private void createUI() {
        setModal(true);

        this.setResizable(false);
        setTitle("Load Network Files - NaviCluster");
        setBounds(0, 0, 600, 168);

        JLabel oLabel = null;
        JLabel oLabelFile = null;
        JPanel oPnlMain = new JPanel();
        SpringLayout oLayout = new SpringLayout();
        oBtnNodeBrowse = new JButton(BTN_BROWSE);
        oBtnNodeBrowse.setActionCommand(BTN_BROWSE_NODE);
        oBtnEdgeBrowse = new JButton(BTN_BROWSE);
        oBtnEdgeBrowse.setActionCommand(BTN_BROWSE_EDGE);

        oPnlMain.setLayout(oLayout);

        add(oPnlMain);

        oLabelFile = new JLabel("Specify Node List and Edge List Files:");
        oPnlMain.add(oLabelFile);
        oLayout.putConstraint(SpringLayout.NORTH, oLabelFile, 8, SpringLayout.NORTH, oPnlMain);
        oLayout.putConstraint(SpringLayout.WEST, oLabelFile, 8, SpringLayout.WEST, oPnlMain);
        oLabel = new JLabel("Node List File: ");
        oLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        oLabel.setLabelFor(this.oTxtNodeListFile);
        oPnlMain.add(oLabel);
        oLayout.putConstraint(SpringLayout.NORTH, oLabel, 6, SpringLayout.SOUTH, oLabelFile);
        oLayout.putConstraint(SpringLayout.WEST, oLabel, 16, SpringLayout.WEST, oPnlMain);
        oPnlMain.add(this.oTxtNodeListFile);
        oBtnNodeBrowse.addActionListener(this);
        oBtnNodeBrowse.setMnemonic(KeyEvent.VK_B);
        oPnlMain.add(oBtnNodeBrowse);
//        oTxtNodeFile.setEnabled(false);
//        oTxtNodeFile.setFocusable(false);
        oBtnNodeBrowse.requestFocusInWindow();

        oLayout.putConstraint(SpringLayout.NORTH, this.oTxtNodeListFile, 5, SpringLayout.SOUTH, oLabelFile);
        oLayout.putConstraint(SpringLayout.WEST, this.oTxtNodeListFile, 8, SpringLayout.EAST, oLabel);
        oLayout.putConstraint(SpringLayout.NORTH, oBtnNodeBrowse, 4, SpringLayout.SOUTH, oLabelFile);
        oLayout.putConstraint(SpringLayout.WEST, oBtnNodeBrowse, 4, SpringLayout.EAST, this.oTxtNodeListFile);

        oLabel = new JLabel("Edge List File: ");
        oLabel.setDisplayedMnemonic(KeyEvent.VK_E);
        oLabel.setLabelFor(this.oTxtEdgeListFile);
        oPnlMain.add(oLabel);
        oLayout.putConstraint(SpringLayout.NORTH, oLabel, 9, SpringLayout.SOUTH, this.oTxtNodeListFile);
        oLayout.putConstraint(SpringLayout.WEST, oLabel, 16, SpringLayout.WEST, oPnlMain);
        oPnlMain.add(this.oTxtEdgeListFile);
        oBtnEdgeBrowse.addActionListener(this);
        oBtnEdgeBrowse.setMnemonic(KeyEvent.VK_R);
        oPnlMain.add(oBtnEdgeBrowse);
        oLayout.putConstraint(SpringLayout.NORTH, this.oTxtEdgeListFile, 8, SpringLayout.SOUTH, this.oTxtNodeListFile);
        oLayout.putConstraint(SpringLayout.WEST, this.oTxtEdgeListFile, 0, SpringLayout.WEST, this.oTxtNodeListFile);
        oLayout.putConstraint(SpringLayout.NORTH, oBtnEdgeBrowse, 7, SpringLayout.SOUTH, this.oTxtNodeListFile);
        oLayout.putConstraint(SpringLayout.WEST, oBtnEdgeBrowse, 4, SpringLayout.EAST, this.oTxtEdgeListFile);

        oBtnOk = new JButton(BTN_OK);
        oBtnOk.setMnemonic(KeyEvent.VK_O);
        oBtnCancel = new JButton(BTN_CANCEL);
        oBtnCancel.setMnemonic(KeyEvent.VK_C);
        oPnlMain.add(oBtnCancel);
        oPnlMain.add(oBtnOk);
        getRootPane().setDefaultButton(oBtnOk);

        oLayout.putConstraint(SpringLayout.EAST, oBtnCancel, -3, SpringLayout.EAST, oPnlMain);
        oLayout.putConstraint(SpringLayout.SOUTH, oBtnCancel, -3, SpringLayout.SOUTH, oPnlMain);
        oLayout.putConstraint(SpringLayout.EAST, oBtnOk, -4, SpringLayout.WEST, oBtnCancel);
        oLayout.putConstraint(SpringLayout.SOUTH, oBtnOk, -3, SpringLayout.SOUTH, oPnlMain);

        oBtnOk.addActionListener(this);
        oBtnCancel.addActionListener(this);

        JFrame oFrmParent = ownerFrame;
        Point oPoint = oFrmParent.getLocation();
        Dimension oParentDim = oFrmParent.getSize();
        Dimension oDim = getSize();
//        System.out.println("p lo "+oPoint+" p size "+oParentDim+" oDim "+oDim);
        int iX = oPoint.x + (oParentDim.width - oDim.width) / 2;
        int iY = oPoint.y + (oParentDim.height - oDim.height) / 2;
        setLocation(iX, iY);
//        pack();
//        validate();
    }

    public void reSetNewLocation() {
        JFrame oFrmParent = ownerFrame;
        Point oPoint = oFrmParent.getLocation();
        Dimension oParentDim = oFrmParent.getSize();
        Dimension oDim = getSize();
//        System.out.println("p lo "+oPoint+" p size "+oParentDim+" oDim "+oDim);
        int iX = oPoint.x + (oParentDim.width - oDim.width) / 2;
        int iY = oPoint.y + (oParentDim.height - oDim.height) / 2;
        setLocation(iX, iY);
    }

    /**
     * Action of this dialog.
     * @param	ae	Action event
     */
    public void actionPerformed(ActionEvent ae) {
        String sCmd = ae.getActionCommand();

        if (sCmd.equals(BTN_OK) || sCmd.equals(BTN_CANCEL)) {
            if (sCmd.equals(BTN_OK)) {
                if (!checkInput()) {
                    return;
                }

                oBtnNodeBrowse.setEnabled(false);
                oBtnEdgeBrowse.setEnabled(false);
                oBtnOk.setEnabled(false);
                oBtnCancel.setEnabled(false);

                loadImportFile();

            } else {
                network = null;
            }
            this.setVisible(false);
            
        } else if (sCmd.equals(BTN_BROWSE_NODE)) {
            String sFile = this.oTxtNodeListFile.getText();
            File oFile = null;
            File oDir = null;
            JFileChooser oDlg = null;

            if (sFile.length() > 0) {
                oFile = new File(sFile);
                if (oFile.exists()) {
                    if (oFile.isDirectory()) {
                        oDir = oFile;
                    } else {
                        oDir = oFile.getParentFile();
                    }
                }
            } else {
                sFile = this.oTxtEdgeListFile.getText();
                if (sFile.length() > 0) {
                    oFile = new File(sFile);
                    if (oFile.exists()) {
                        if (oFile.isDirectory()) {
                            oDir = oFile;
                        } else {
                            oDir = oFile.getParentFile();
                        }
                    }
                }
            }
            if (oDir == null) {
                oDlg = new JFileChooser();
            } else {
                oDlg = new JFileChooser(oDir);
            }
            oDlg.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String ext = null;
                    String s = f.getName();
                    int i = s.lastIndexOf('.');

                    if (i > 0 && i < s.length() - 1) {
                        ext = s.substring(i + 1).toLowerCase();
                    }

                    if (ext != null) {
                        if (ext.equals("node")) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    return false;
                }

                @Override
                public String getDescription() {
                    return ".node file";
                }
            });
            int iRet = oDlg.showOpenDialog(this);
            if (iRet == JFileChooser.APPROVE_OPTION) {
                try {
                    sFile = oDlg.getSelectedFile().getCanonicalPath();
                } catch (Exception e) {
                }
                this.oTxtNodeListFile.setText(sFile);
//                this.oTxtNodeFile.setCaretPosition(0);
            }
        } else if (sCmd.equals(BTN_BROWSE_EDGE)) {
            String sFile = this.oTxtEdgeListFile.getText();
            File oFile = null;
            File oDir = null;
            JFileChooser oDlg = null;

            if (sFile.length() > 0) {
                oFile = new File(sFile);
                if (oFile.exists()) {
                    if (oFile.isDirectory()) {
                        oDir = oFile;
                    } else {
                        oDir = oFile.getParentFile();
                    }
                }
            } else {
                sFile = this.oTxtNodeListFile.getText();
                if (sFile.length() > 0) {
                    oFile = new File(sFile);
                    if (oFile.exists()) {
                        if (oFile.isDirectory()) {
                            oDir = oFile;
                        } else {
                            oDir = oFile.getParentFile();
                        }
                    }
                }
            }
            if (oDir == null) {
                oDlg = new JFileChooser();
            } else {
                oDlg = new JFileChooser(oDir);
            }
            oDlg.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String ext = null;
                    String s = f.getName();
                    int i = s.lastIndexOf('.');

                    if (i > 0 && i < s.length() - 1) {
                        ext = s.substring(i + 1).toLowerCase();
                    }

                    if (ext != null) {
                        if (ext.equals("edge")) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    return false;
                }

                @Override
                public String getDescription() {
                    return ".edge file";
                }
            });
            int iRet = oDlg.showOpenDialog(this);
            if (iRet == JFileChooser.APPROVE_OPTION) {
                try {
                    sFile = oDlg.getSelectedFile().getCanonicalPath();
                } catch (Exception e) {
                }
                this.oTxtEdgeListFile.setText(sFile);
//                this.oTxtEdgeFile.setCaretPosition(0);
            }
        }
    }

    /**
     * Check if input is valid.
     * @return	True: valid / False: invalid
     */
    private boolean checkInput() {
        String sNodeFile = this.oTxtNodeListFile.getText();
        String sEdgeFile = this.oTxtEdgeListFile.getText();

        if ((sNodeFile == null) || (sNodeFile.length() == 0)) {
            JOptionPane.showMessageDialog(this, "Please specify a path to a node list file.", "Node List File Missing", JOptionPane.INFORMATION_MESSAGE);

            return false;
        }
        if ((sEdgeFile == null) || (sEdgeFile.length() == 0)) {
            JOptionPane.showMessageDialog(this, "Please specify a path to an edge list file.", "Edge List File Missing", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Read node and edge list files and store data in the three arrays of Louvain algorithm (sArr, dArr, wArr)
     */
    public void loadImportFile() {
        String sNodeListFile = this.oTxtNodeListFile.getText();
        String sEdgeListFile = this.oTxtEdgeListFile.getText();
        File oFile = new File(sNodeListFile);
        nodeListFile = oFile;
        
        String sName = null;
        BufferedReader oReader = null;
        String sLine = null;
        String[] saData = null;
        
        int iMode = 0;
        HashMap<String, BioObject> hNode = new HashMap<String, BioObject>();
        HashMap<String, Integer> hNodeID = new HashMap<String, Integer>();
        BioObject oNode = null;
        BioEdge oEdge = null;

        String sSource = null;
        String sTarget = null;
        double dWeight = 0.0;
        String sInter = null;
        BioObject oSrcNode = null;
        BioObject oTrgNode = null;
        
        boolean bFirstLine = true;
        int iTmp = -1;
        int iNodeID = 0;
        int iLineCnt = 0;

        sName = oFile.getName();
        iTmp = sName.lastIndexOf(".");
        if (iTmp > 0) {
            sName = sName.substring(0, iTmp);
        }

        network = new SparseMultigraph<Object, Object>();
        disconnectedNodeSet = new HashSet<Integer>();
        IdToNodeMap = new HashMap<Integer, BioObject>();
        maxBioEdgeWeight = 0;
        minBioEdgeWeight = Double.MAX_VALUE;
        try {

            oReader = new BufferedReader(new InputStreamReader(new FileInputStream(oFile)));
            while ((sLine = oReader.readLine()) != null) {
                if (bFirstLine) {
                    bFirstLine = false;
                    continue;
                }
                saData = sLine.split("\t");

                if (saData.length >= 1) {
                    /* check duplicate nodes 
                     saData[0] is node name */
                    if (hNode.containsKey(saData[0])) {
                        try {
                            oReader.close();
                        } catch (Exception e2) {
                        }
                        JOptionPane.showMessageDialog(ImportFileForm.this, "Duplicate nodes are found at line " + iLineCnt + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    oNode = new BioObject(iNodeID, "" + iNodeID, saData[0]);
                    oNode.setStandardName(saData[0]);

                    if (saData.length >= 2) {
                        /* saData[1] is database name */
                        oNode.setDatabaseName(saData[1]);
                        if (saData.length >= 3) {
                            /* saData[1] is id in the database */
                            oNode.setDatabaseID(saData[2]);
                            if (saData.length >= 4) {
                                /* load property information (a term list) */
                                StringTokenizer stz = new StringTokenizer(saData[3], "|");
                                while (stz.hasMoreTokens()) {
                                    oNode.getPropTermList().add(stz.nextToken());
                                }
                            }
                        }
                    }
                    hNode.put(saData[0], oNode);
                    hNodeID.put(saData[0], new Integer(iNodeID));
                    IdToNodeMap.put(iNodeID, oNode);
                    network.addVertex(oNode);
                    iNodeID++;
                }
            }
            oReader.close();

            disconnectedNodeSet.addAll(IdToNodeMap.keySet());

            bFirstLine = true;
            oFile = new File(sEdgeListFile);
            edgeListFile = oFile;
            iLineCnt = 0;

            SortedSet<BioEdge> sortedSet = new TreeSet<BioEdge>(new Comparator<BioEdge>() {
                @Override
                public int compare(BioEdge e1, BioEdge e2) {
                    int e1v1 = ((BioObject) e1.getNode1()).getId();
                    int e1v2 = ((BioObject) e1.getNode2()).getId();
                    int e2v1 = ((BioObject) e2.getNode1()).getId();
                    int e2v2 = ((BioObject) e2.getNode2()).getId();
                    if (e1v1 > e1v2) {
                        int temp = e1v1;
                        e1v1 = e1v2;
                        e1v2 = temp;
                    }
                    if (e2v1 > e2v2) {
                        int temp = e2v1;
                        e2v1 = e2v2;
                        e2v2 = temp;
                    }
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
                    return 0;
                }
            });
            
            oReader = new BufferedReader(new InputStreamReader(new FileInputStream(oFile)));
            while ((sLine = oReader.readLine()) != null) {
                if (bFirstLine) {
                    bFirstLine = false;
                    continue;
                }

                saData = sLine.split("\t");
                if (saData.length >= 3) {
                    /* saData[0] is a source node; saData[1] is a target node */
                    if ((hNode.containsKey(saData[0])) && (hNode.containsKey(saData[1]))) {
                        try {
                            dWeight = Double.parseDouble(saData[2]);
                            oSrcNode = hNode.get(saData[0]);
                            oTrgNode = hNode.get(saData[1]);
                            if (saData.length >= 4) {
                                sInter = saData[3];
                            } else {
                                /* protein-protein interaction */
                                sInter = "pp";
                            }

                            oEdge = new BioEdge(oSrcNode, oTrgNode, dWeight);
                            oEdge.setType(sInter);

                            if (!sortedSet.contains(oEdge)) {
                                sortedSet.add(oEdge);
                                network.addEdge(oEdge, oSrcNode, oTrgNode);

                                disconnectedNodeSet.remove(hNodeID.get(saData[0]));
                                disconnectedNodeSet.remove(hNodeID.get(saData[1]));

                                if (dWeight < minBioEdgeWeight) {
                                    minBioEdgeWeight = dWeight;
                                }
                                if (dWeight > maxBioEdgeWeight) {
                                    maxBioEdgeWeight = dWeight;
                                }
                            }
                        } catch (NumberFormatException e) {
                        }
                    } else {
                        try {
                            oReader.close();
                        } catch (Exception e2) {
                        }
                        String noNodeName = saData[0];
                        if (!hNode.containsKey(saData[1])) {
                            noNodeName = saData[1];
                        }
                        JOptionPane.showMessageDialog(ImportFileForm.this, "There is a node in the edge list file not existing in the list node file (" + iLineCnt + "). No node named " + noNodeName, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            
            if (maxBioEdgeWeight == minBioEdgeWeight) {
                maxBioEdgeWeight++;
            }

            sArr = new int[network.getEdgeCount()];
            dArr = new int[network.getEdgeCount()];
            wArr = new double[network.getEdgeCount()];
            int i = 0;
            for (BioEdge edge : sortedSet) {
                sArr[i] = hNodeID.get(((BioEdge) edge).node1.toString());
                dArr[i] = hNodeID.get(((BioEdge) edge).node2.toString());
                wArr[i] = ((BioEdge) edge).getWeight();
                i++;
            }

            System.out.println("network node " + network.getVertexCount() + " edge " + network.getEdgeCount());
            oReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ImportFileForm.this, "I/O Error in loading network files.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ImportFileForm.this, "Error in loading network files.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    public Graph<Object, Object> getNetwork() {
        return network;
    }

    public void setNetwork(Graph<Object, Object> network) {
        this.network = network;
    }

    public Set<Integer> getDisconnectedNodeSet() {
        return disconnectedNodeSet;
    }

    public double getMaxBioEdgeWeight() {
        return maxBioEdgeWeight;
    }

    public double getMinBioEdgeWeight() {
        return minBioEdgeWeight;
    }

    public int[] getsArr() {
        return sArr;
    }

    public double[] getwArr() {
        return wArr;
    }
    
    public int[] getdArr() {
        return dArr;
    }

    public Map<Integer, BioObject> getIdToNodeMap() {
        return IdToNodeMap;
    }

    public File getEdgeListFile() {
        return edgeListFile;
    }

    public File getNodeListFile() {
        return nodeListFile;
    }
    
    public JTextField getoTxtEdgeListFile() {
        return oTxtEdgeListFile;
    }

    public JTextField getoTxtNodeListFile() {
        return oTxtNodeListFile;
    }

    public void setoTxtEdgeListFile(JTextField oTxtEdgeListFile) {
        this.oTxtEdgeListFile = oTxtEdgeListFile;
    }

    public void setoTxtNodeListFile(JTextField oTxtNodeListFile) {
        this.oTxtNodeListFile = oTxtNodeListFile;
    }

}
