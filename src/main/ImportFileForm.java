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
import java.util.ArrayList;
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
 * Dialog box for importing network files.
 * @author Y.Iihara
 */
public class ImportFileForm extends JDialog implements ActionListener {

    private static ImportFileForm oMySelf = null;
    private Graph network = null;
    private Map<Integer, BioObject> IDToNodeMap = new HashMap<Integer, BioObject>();
    private Set<Integer> disconNodeSet = new HashSet<Integer>();
    private double maxBioEdgeWeight = 0;
    private double minBioEdgeWeight = Double.MAX_VALUE;
//    private ArrayList<Integer> sArrList = new ArrayList<Integer>();
//    private ArrayList<Integer> dArrList = new ArrayList<Integer>();
//    private ArrayList<Integer> wArrList = new ArrayList<Integer>();
    private int[] sArr = null;
    private int[] dArr = null;
    private double[] wArr = null;
    private JFrame ownerFrame = null;
//	private NaviClusterPlugin oPlugin = null;
    private JTextField oTxtNodeFile = new JTextField(32);
    private JTextField oTxtEdgeFile = new JTextField(32);
    private File nodeFile = null;
    private File edgeFile = null;
    private JButton oBtnNodeBrowse = null;
    private JButton oBtnEdgeBrowse = null;
    private JButton oBtnOk = null;
    private JButton oBtnCancel = null;
    private static final String BTN_BROWSE = "Browse...";
    private static final String BTN_BROWSE_NODE = "BROWSE_NODE";
    private static final String BTN_BROWSE_EDGE = "BROWSE_EDGE";
    private static final String BTN_OK = "OK";
    private static final String BTN_CANCEL = "Cancel";

    /**
     * Constructor
     * @param	oPlug	Instance of this plugin
     */
//	private ImportFileForm(NaviClusterPlugin oPlug)
//    private ImportFileForm(JFrame owner) {
    public ImportFileForm(JFrame owner) {
//		super(Cytoscape.getDesktop());
        super(owner);
        ownerFrame = owner;
//		oPlugin = oPlug;

        formLayout();
//        oTxtNodeFile.setFocusable(true);
//        oTxtNodeFile.setEnabled(true);
//        validate();

        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
            }
        });
    }

    /**
     * Supply static instance of this class.
     * @param	oPlug	Instance of this plugin
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
     * Layout of this dialog.
     */
    private void formLayout() {
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

        oLabelFile = new JLabel("Specify Files Containing Node List and Edge List.");
        oPnlMain.add(oLabelFile);
        oLayout.putConstraint(SpringLayout.NORTH, oLabelFile, 8, SpringLayout.NORTH, oPnlMain);
        oLayout.putConstraint(SpringLayout.WEST, oLabelFile, 8, SpringLayout.WEST, oPnlMain);
        oLabel = new JLabel("Node File: ");
        oLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        oLabel.setLabelFor(this.oTxtNodeFile);
        oPnlMain.add(oLabel);
        oLayout.putConstraint(SpringLayout.NORTH, oLabel, 6, SpringLayout.SOUTH, oLabelFile);
        oLayout.putConstraint(SpringLayout.WEST, oLabel, 16, SpringLayout.WEST, oPnlMain);
        oPnlMain.add(this.oTxtNodeFile);
        oBtnNodeBrowse.addActionListener(this);
        oBtnNodeBrowse.setMnemonic(KeyEvent.VK_B);
        oPnlMain.add(oBtnNodeBrowse);
//        oTxtNodeFile.setEnabled(false);
//        oTxtNodeFile.setFocusable(false);
        oBtnNodeBrowse.requestFocusInWindow();

        oLayout.putConstraint(SpringLayout.NORTH, this.oTxtNodeFile, 5, SpringLayout.SOUTH, oLabelFile);
        oLayout.putConstraint(SpringLayout.WEST, this.oTxtNodeFile, 8, SpringLayout.EAST, oLabel);
        oLayout.putConstraint(SpringLayout.NORTH, oBtnNodeBrowse, 4, SpringLayout.SOUTH, oLabelFile);
        oLayout.putConstraint(SpringLayout.WEST, oBtnNodeBrowse, 4, SpringLayout.EAST, this.oTxtNodeFile);

        oLabel = new JLabel("Edge File: ");
        oLabel.setDisplayedMnemonic(KeyEvent.VK_E);
        oLabel.setLabelFor(this.oTxtEdgeFile);
        oPnlMain.add(oLabel);
        oLayout.putConstraint(SpringLayout.NORTH, oLabel, 9, SpringLayout.SOUTH, this.oTxtNodeFile);
        oLayout.putConstraint(SpringLayout.WEST, oLabel, 16, SpringLayout.WEST, oPnlMain);
        oPnlMain.add(this.oTxtEdgeFile);
        oBtnEdgeBrowse.addActionListener(this);
        oBtnEdgeBrowse.setMnemonic(KeyEvent.VK_R);
        oPnlMain.add(oBtnEdgeBrowse);
        oLayout.putConstraint(SpringLayout.NORTH, this.oTxtEdgeFile, 8, SpringLayout.SOUTH, this.oTxtNodeFile);
        oLayout.putConstraint(SpringLayout.WEST, this.oTxtEdgeFile, 0, SpringLayout.WEST, this.oTxtNodeFile);
        oLayout.putConstraint(SpringLayout.NORTH, oBtnEdgeBrowse, 7, SpringLayout.SOUTH, this.oTxtNodeFile);
        oLayout.putConstraint(SpringLayout.WEST, oBtnEdgeBrowse, 4, SpringLayout.EAST, this.oTxtEdgeFile);

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

                // 2010/04/04 Takao Asanuma
//                oPlugin.clearAllandDestroyNetwork();
//                oPlugin.destroyNetwork();

                loadImportFile();

                // 2010/04/19 Takao Asanuma
//                Util.redrawGraph();
            } else {
                network = null;
            }
            this.setVisible(false);
        } else if (sCmd.equals(BTN_BROWSE_NODE)) {
            String sFile = this.oTxtNodeFile.getText();
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
                sFile = this.oTxtEdgeFile.getText();
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
                this.oTxtNodeFile.setText(sFile);
//                this.oTxtNodeFile.setCaretPosition(0);
            }
        } else if (sCmd.equals(BTN_BROWSE_EDGE)) {
            String sFile = this.oTxtEdgeFile.getText();
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
                sFile = this.oTxtNodeFile.getText();
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
                this.oTxtEdgeFile.setText(sFile);
//                this.oTxtEdgeFile.setCaretPosition(0);
            }
        }
    }

    /**
     * Check if inputted is suitable.
     * @return	True: suitable / False: not suitable
     */
    private boolean checkInput() {
        String sNodeFile = this.oTxtNodeFile.getText();
        String sEdgeFile = this.oTxtEdgeFile.getText();

        if ((sNodeFile == null) || (sNodeFile.length() == 0)) {
//            Util.warningMessage("Please set importing Node file path.");
            JOptionPane.showMessageDialog(this, "Please set importing Node file path.", "Node File Path Missing", JOptionPane.INFORMATION_MESSAGE);

            return false;
        }
        if ((sEdgeFile == null) || (sEdgeFile.length() == 0)) {
//            Util.warningMessage("Please set importing Edge file path.");
            JOptionPane.showMessageDialog(this, "Please set importing Edge file path.", "Edge File Path Missing", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Read files for importing network.
     */
    public void loadImportFile() {
        String sNodeFile = this.oTxtNodeFile.getText();
        String sEdgeFile = this.oTxtEdgeFile.getText();
        File oFile = new File(sNodeFile);
        nodeFile = oFile;
        String sName = null;
        BufferedReader oReader = null;
        String sLine = null;
        String[] saData = null;
        int iMode = 0;
        HashMap<String, BioObject> hNode = new HashMap<String, BioObject>();
        HashMap<String, Integer> hNodeID = new HashMap<String, Integer>();
        BioObject oNode = null;
        BioEdge oEdge = null;
//            CyAttributes oAttrNode = Cytoscape.getNodeAttributes();
//            CyAttributes oAttrEdge = Cytoscape.getEdgeAttributes();
        String sSource = null;
        String sTarget = null;
        double dWeight = 0.0;
        String sInter = null;
        BioObject oSrcNode = null;
        BioObject oTrgNode = null;
        boolean bFirst = true;
        long lFileSize = oFile.length();
        long lFinished = 0;
        int iPercent = 0;
        int iTmp = -1;
//        int iNodeID = 1;
        int iNodeID = 0;
        int iLineCnt = 0;

        sName = oFile.getName();
        iTmp = sName.lastIndexOf(".");
        if ((iTmp != -1) && (iTmp != 0)) {
            sName = sName.substring(0, iTmp);
        }

        // 2010/04/27 Takao Asanuma
//            NcPanel.enableStartNaviClusterButton(false);

//        Graph<BioObject, BioEdge> oNetwork = new SparseMultigraph<BioObject, BioEdge>();
        network = new SparseMultigraph<Object, Object>();
        disconNodeSet = new HashSet<Integer>();
        IDToNodeMap = new HashMap<Integer, BioObject>();
        maxBioEdgeWeight = 0;
        minBioEdgeWeight = Double.MAX_VALUE;
        try {
//                oTaskMonitor.setStatus("Loading node file...");
//                oTaskMonitor.setPercentCompleted(0);

            oReader = new BufferedReader(new InputStreamReader(new FileInputStream(oFile)));
            while ((sLine = oReader.readLine()) != null) {
//                iLineCnt++;
//                lFinished += sLine.length() + 1;
//                iPercent = (int) (lFinished * 100 / lFileSize);
//                    oTaskMonitor.setPercentCompleted(iPercent);
                if (bFirst) {
                    bFirst = false;
                    continue;
                }
                saData = sLine.split("\t");

                if (saData.length >= 1) {
                    if (hNode.containsKey(saData[0])) {
                        try {
                            oReader.close();
                        } catch (Exception e2) {
                        }
                        JOptionPane.showMessageDialog(ImportFileForm.this, "Overlapping node is found at node file(" + iLineCnt + ").", "Error", JOptionPane.ERROR_MESSAGE);
//                            oTaskMonitor.setException(new Exception(), "Overlapping node is found at node file(" + iLineCnt + ").");
//                            return;
                    }

//                        oNode = Cytoscape.getCyNode("" + iNodeID, true);
                    oNode = new BioObject(iNodeID, "" + iNodeID, saData[0]);
                    oNode.setStandardName(saData[0]);
//                        oNetwork.addNode(oNode);
//                    oNetwork.addVertex(oNode);

//                        oAttrNode.setAttribute(oNode.getIdentifier(), Const.NODE_ID, "" + iNodeID);
//                        oAttrNode.setAttribute(oNode.getIdentifier(), Const.NODE_NAME, saData[0]);
//                    oNode.setName(saData[0]);


                    if (saData.length >= 2) {
//                            oAttrNode.setAttribute(oNode.getIdentifier(), Const.NODE_DB_NAME, saData[1]);
                        oNode.setDatabaseName(saData[1]);
                        if (saData.length >= 3) {
//                                oAttrNode.setAttribute(oNode.getIdentifier(), Const.NODE_DB_ID, saData[2]);
                            oNode.setDatabaseID(saData[2]);
                            if (saData.length >= 4) {
                                StringTokenizer stz = new StringTokenizer(saData[3], "|");
                                while (stz.hasMoreTokens()) {
                                    oNode.getPropTermList().add(stz.nextToken());
                                }
//                                    oAttrNode.setAttribute(oNode.getIdentifier(), Const.NODE_ONT, saData[3]);
                            }
                        }
                    }
                    hNode.put(saData[0], oNode);
                    hNodeID.put(saData[0], new Integer(iNodeID));
                    IDToNodeMap.put(iNodeID, oNode);
                    network.addVertex(oNode);

                    iNodeID++;
                }
            }
            oReader.close();

            disconNodeSet.addAll(IDToNodeMap.keySet());

            bFirst = true;
            oFile = new File(sEdgeFile);
            edgeFile = oFile;
//            System.out.println("ofile "+oFile);
//                oTaskMonitor.setStatus("Loading edge file...");
//                oTaskMonitor.setPercentCompleted(0);
            lFileSize = oFile.length();
            lFinished = 0;
            iPercent = 0;
            iLineCnt = 0;
            int iSrcID = -1;
            int iTrgID = -1;
//            int netvercount = network.getVertexCount();
            SortedSet<BioEdge> sortedSet = new TreeSet<BioEdge>(new Comparator<BioEdge>() {

                public int compare(BioEdge e1, BioEdge e2) {
//                    BioObject bio1v1 = ((BioObject) e1.getNode1());
//                    BioObject bio1v2 = ((BioObject) e1.getNode2());
//                    BioObject bio2v1 = ((BioObject) e2.getNode1());
//                    BioObject bio2v2 = ((BioObject) e2.getNode2());
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
//                System.out.println(e1v1 + " " + e1v2);
//                System.out.println(e2v1 + " " + e2v2);
                    ///DEBUG///
//                    if (("AT3G06890".equalsIgnoreCase(bio1v1.getName()) && "AT2G14290".equalsIgnoreCase(bio1v2.getName()))) //                            && ("AT2G14290".equalsIgnoreCase(bio2v1.getName()))) 
//                    //                        && "AT3G06890".equals(bio2v2.getStandardName())))
//                    {
//
//                        System.out.println("e1 " + e1v1 + " " + e1v2 + " " + bio1v1 + " " + bio1v2);
//                        System.out.println("e2 " + e2v1 + " " + e2v2 + " " + bio2v1 + " " + bio2v2);
//                    }

//                    if (("AT3G06890".equalsIgnoreCase(bio1v2.getName()) && "AT2G14290".equalsIgnoreCase(bio1v1.getName())) )
////                            && ("AT2G14290".equalsIgnoreCase(bio2v1.getName()))) 
////                        && "AT3G06890".equals(bio2v2.getStandardName())))
//                    {
//                        
//                        System.out.println("case 2");
//                        System.out.println("e1 "+e1v1+" "+e1v2+" "+bio1v1+" "+bio1v2);
//                        System.out.println("e2 "+e2v1+" "+e2v2+" "+bio2v1+" "+bio2v2);
//                    }
//                    if ((e1v1 == e2v2) && (e1v2 == e2v1)) {
//                        return 0;
//                    } 
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
            int iii = 0;
            oReader = new BufferedReader(new InputStreamReader(new FileInputStream(oFile)));
            ///DEBUG///
//            BioEdge savedEdge = null;
            while ((sLine = oReader.readLine()) != null) {
//                iLineCnt++;
//                lFinished += sLine.length() + 1;
//                iPercent = (int) (lFinished * 100 / lFileSize);
//                    oTaskMonitor.setPercentCompleted(iPercent);
                if (bFirst) {
                    bFirst = false;
                    continue;
                }

                saData = sLine.split("\t");
                if (saData.length >= 3) {
                    if ((hNode.containsKey(saData[0])) && (hNode.containsKey(saData[1]))) {
                        try {
                            dWeight = Double.parseDouble(saData[2]);
                            oSrcNode = hNode.get(saData[0]);
                            oTrgNode = hNode.get(saData[1]);
                            if (saData.length >= 4) {
                                sInter = saData[3];
                            } else {
                                sInter = "pp";
                            }

                            iSrcID = hNodeID.get(saData[0]).intValue();
                            iTrgID = hNodeID.get(saData[1]).intValue();

//                            if (iSrcID <= iTrgID) {
                            oEdge = new BioEdge(oSrcNode, oTrgNode, dWeight);
                            oEdge.setType(sInter);
                            ///DEBUG///
                            
//                            if ("AT2G14290".equals(saData[0]) && "AT3G06890".equals(saData[1])) {
//                                savedEdge = oEdge;
//                                System.out.println("id 0 " + iSrcID);
//                                System.out.println("id 1 " + iTrgID);
//                                System.out.println("" + savedEdge.equals(oEdge));
//                                System.out.println("" + sortedSet.contains(oEdge));
//                                System.out.println("" + sortedSet.contains(savedEdge));
//                            }
//
//                            if ("AT3G06890".equals(saData[0]) && "AT2G14290".equals(saData[1])) {
//                                System.out.println("savedEdge " + savedEdge);
//                                System.out.println("oEdge " + oEdge);
//                                System.out.println("id 0 " + iSrcID);
//                                System.out.println("id 1 " + iTrgID);
//
//                                System.out.println("" + savedEdge.equals(oEdge));
//                                System.out.println("" + sortedSet.contains(oEdge));
//                                System.out.println("" + sortedSet.contains(savedEdge));
//                            }
                            if (!sortedSet.contains(oEdge)) {
                                sortedSet.add(oEdge);
                                network.addEdge(oEdge, oSrcNode, oTrgNode);

//                                System.out.println(network.containsVertex(oSrcNode));


//                                    oEdge = Cytoscape.getCyEdge(oSrcNode, oTrgNode, Semantics.INTERACTION, sInter, true);
//                            } else {
//                                oEdge = new BioEdge(oTrgNode, oSrcNode, dWeight);
//                                network.addEdge(oEdge, oTrgNode, oSrcNode);
//                                oEdge.setType(sInter);
//                                    oEdge = Cytoscape.getCyEdge(oTrgNode, oSrcNode, Semantics.INTERACTION, sInter, true);

//                            }


                                disconNodeSet.remove(hNodeID.get(saData[0]));
                                disconNodeSet.remove(hNodeID.get(saData[1]));
//                                oNetwork.addEdge(oEdge);
//                            oNetwork.addEdge(oEdge, oSrcNode, oTrgNode);

//                           if ((iii > 13735) && (iii < 13742)){
//                                System.out.println("bioedge "+oEdge);
//                                System.out.println("sortedset "+sortedSet);
//                            }
//                                oAttrEdge.setAttribute(oEdge.getIdentifier(), Const.EDGE_ATTR_WEIGHT, (Double) dWeight);
//                            oEdge.setWeight(dWeight);
//                            if (netvercount != network.getVertexCount()){
//                                System.out.println(network.getVertexCount());
//                            }

                                if (dWeight >= minBioEdgeWeight) {
                                    minBioEdgeWeight = dWeight;
                                }
                                if (dWeight < maxBioEdgeWeight) {
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
                        JOptionPane.showMessageDialog(ImportFileForm.this, "There is a node in the edge file that does not exist in the node file (" + iLineCnt + "). No node named " + noNodeName, "Error", JOptionPane.ERROR_MESSAGE);
//                            oTaskMonitor.setException(new Exception(), "Node of edge is not found at edge file(" + iLineCnt + ").");
                        return;
                    }
                }
                iii++;
            }
            
            if (maxBioEdgeWeight == minBioEdgeWeight) {
                maxBioEdgeWeight++;
            }
            System.out.println("sortedSet size " + sortedSet.size());
//            sortedSet.addAll(network.getEdges());
            sArr = new int[network.getEdgeCount()];
            dArr = new int[network.getEdgeCount()];
            wArr = new double[network.getEdgeCount()];
            int i = 0;
//            for (Object edge : network.getEdges()) {
            for (BioEdge edge : sortedSet) {
//                System.out.println("edge "+network.getEndpoints(edge));
//                if (network.getEndpoints(edge).getFirst() == null)
//                    System.out.println(edge);
//                else if (network.getEndpoints(edge).getSecond() == null)
//                    System.out.println(edge);
                sArr[i] = hNodeID.get(((BioEdge) edge).node1.toString());
                dArr[i] = hNodeID.get(((BioEdge) edge).node2.toString());
                wArr[i] = ((BioEdge) edge).getWeight();
//                System.out.println("s "+sArr[i]+" d "+dArr[i]+" w "+wArr[i]);
                i++;
            }
//            System.out.println("i " + i);
//            System.out.println("discon " + disconNodeSet.size());
//            System.out.println("IDTONodeMap " + IDToNodeMap.size());
//            System.out.println("sorted set size "+sortedSet.size());
            System.out.println("network node " + network.getVertexCount() + " edge " + network.getEdgeCount());
            oReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ImportFileForm.this, "I/O Error in loading import file.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                oTaskMonitor.setException(e, "I/O Error in loading import file.");
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ImportFileForm.this, "Error in loading import file.", "Error", JOptionPane.ERROR_MESSAGE);
//                oTaskMonitor.setException(e, "I/O Error in loading import file.");
            return;
        }


//            oTaskMonitor.setStatus("Creating Cytoscape network...");
//            oTaskMonitor.setPercentCompleted(-1);

//            CytoscapeInit.getProperties().setProperty("maximizeViewOnCreate", "true");
//            Cytoscape.createNetworkView(oNetwork, sName);

        // 2010/04/27 Takao Asanuma
//            NcPanel.enableStartNaviClusterButton(true);


    }

    public Graph<Object, Object> getNetwork() {
        return network;
    }

    public void setNetwork(Graph<Object, Object> network) {
        this.network = network;
    }

    public int[] getdArr() {
        return dArr;
    }

    public Set<Integer> getDisconNodeSet() {
        return disconNodeSet;
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

    public Map<Integer, BioObject> getIDToNodeMap() {
        return IDToNodeMap;
    }

    public JTextField getoTxtEdgeFile() {
        return oTxtEdgeFile;
    }

    public JTextField getoTxtNodeFile() {
        return oTxtNodeFile;
    }

    public File getEdgeFile() {
        return edgeFile;
    }

    public File getNodeFile() {
        return nodeFile;
    }

    public void setoTxtEdgeFile(JTextField oTxtEdgeFile) {
        this.oTxtEdgeFile = oTxtEdgeFile;
    }

    public void setoTxtNodeFile(JTextField oTxtNodeFile) {
        this.oTxtNodeFile = oTxtNodeFile;
    }
    ///DEBUG///
//    public static void main(String[] args) {
//        SortedSet<BioEdge> sortedSet = new TreeSet<BioEdge>(new Comparator<BioEdge>() {
//
//            public int compare(BioEdge e1, BioEdge e2) {
//                int e1v1 = ((BioObject) e1.getNode1()).getId();
//                int e1v2 = ((BioObject) e1.getNode2()).getId();
//                int e2v1 = ((BioObject) e2.getNode1()).getId();
//                int e2v2 = ((BioObject) e2.getNode2()).getId();
//                System.out.println(e1v1 + " " + e1v2);
//                System.out.println(e2v1 + " " + e2v2);
//                if (e1v1 > e1v2) {
//                    int temp = e1v1;
//                    e1v1 = e1v2;
//                    e1v2 = temp;
//                }
//                if (e2v1 > e2v2) {
//                    int temp = e2v1;
//                    e2v1 = e2v2;
//                    e2v2 = temp;
//                }
////                if ((e1v1 == e2v2) && (e1v2 == e2v1)) {
////                    return 0;
////                }
//                if (e1v1 < e2v1) {
////                    System.out.println(e1v1 + " " + e1v2 + "<" + e2v1 + " " + e2v2);
//                    return -1;
//                } else if (e1v1 > e2v1) {
////                    System.out.println(e1v1 + " " + e1v2 + ">" + e2v1 + " " + e2v2);
//                    return 1;
//                } else if (e1v1 == e2v1) {
//                    if (e1v2 < e2v2) {
////                        System.out.println(e1v1 + " " + e1v2 + "<" + e2v1 + " " + e2v2);
//                        return -1;
//                    } else if (e1v2 > e2v2) {
////                        System.out.println(e1v1 + " " + e1v2 + ">" + e2v1 + " " + e2v2);
//                        return 1;
//                    } else {
////                        System.out.println(e1v1 + " " + e1v2 + "=" + e2v1 + " " + e2v2);
//                        return 0;
//                    }
//                }
////                e1v1.toString()
//                return 0;
//            }
//        });
//
//        BioObject bio1 = new BioObject(1, "1");
//        BioObject bio2 = new BioObject(2, "2");
//        BioEdge oEdge2 = new BioEdge(bio1, bio2, 4.5);
//        BioEdge oEdge = new BioEdge(bio2, bio1, 4.7);
//        sortedSet.add(oEdge);
//        System.out.println("" + (oEdge.equals(oEdge2)));
//        System.out.println("" + sortedSet.contains(oEdge2));
//        sortedSet.add(oEdge2);
//        System.out.println("" + sortedSet);
//        System.out.println(sortedSet.add(oEdge2));
//        System.out.println(sortedSet.add(oEdge));
//    }
}
