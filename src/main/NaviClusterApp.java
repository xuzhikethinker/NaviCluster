/**
 * Copyright (c) 2011, NaviCluster Development Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *  * Neither the name of Department of Computational Biology, the University of Tokyo, nor the names of its contributors may be used to endorse or
 *     promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package main;

/**
 *
 * @author Thanet Praneenararat
 */
import LouvainAlgorithm.MainLouvain;
// import com.apple.eawt.AppEvent.QuitEvent;
// import com.apple.eawt.Application;
// import com.apple.eawt.QuitHandler;
// import com.apple.eawt.QuitResponse;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;


import edu.uci.ics.jung.graph.Graph;


import edu.uci.ics.jung.graph.SparseMultigraph;

import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.Pair;

import edu.uci.ics.jung.visualization.GraphZoomScrollPane;

import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.AbstractEdgeShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;

import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kmean.DataCluster;
import kmean.DataVector;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;
import org.apache.commons.io.IOUtils;
import org.apache.xmlgraphics.image.writer.ImageWriterUtil;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;

import util.StopWatch;
//import org.geneontology.oboedit.datamodel.OBOSession;
//import util.EdgeWeightLabeller;

/**
 *
 * @author Thanet Praneenararat (Knack)
 * Department of Computational Biology, Graduate School of Frontier Sciences, University of Tokyo
 */
public class NaviClusterApp extends JFrame {

    private boolean UIWanted;
//    private String stringStream = "";
    private boolean isWeightGraph = false;
    private int[] sArr = null;
    private int[] dArr = null;
    private double[] wArr = null;
    public Color mainColor = new Color(255, 204, 22);
    public Color peripheralColor = new Color(0xF6FEAC);
//    public Color bioObjColor = new Color(145, 214, 134);
//    public Color searchedColor = new Color(135,137,211);
//    public Color searchedColor = new Color(0xECA3AE);
    public Color searchedColor = new Color(0xFFCCFF);
//    public Color prevProcessedColor = new Color(0xC7FFEF);
//    public Color prevProcessedColor = new Color(0xCCFFF0);
    public Color prevProcessedColor = new Color(0xD4FFF2);
//    public Color mainColor = new Color(138,240,87);
//    public Color mainColor = new Color(153,255,102);
    VisualizationViewer<Object, Object> vv;
    Map<Object, Paint> vertexPaints = LazyMap.<Object, Paint>decorate(new HashMap<Object, Paint>(),
            new ConstantTransformer(mainColor));
    Map<Object, Paint> edgePaints = LazyMap.<Object, Paint>decorate(new HashMap<Object, Paint>(),
            new ConstantTransformer(Color.BLACK));
    Map<Integer, BioObject> nodeMap = new HashMap<Integer, BioObject>();
    Set<Integer> disconNodeSet = new HashSet<Integer>();

    /* TO-DO I might allow users to adjust threshold for louvain clustering in the future */
//    private double threshold;
    private EdgeDisplayPredicate medp;
//    private EdgeWeightLabeller ew;
//    String filename = "src/datasets/allyeast.mnet";
//    String filename = "src/datasets/allyeast.mnet";
//    String filename = "src/datasets/yeastnet2.mnet";
//    String filename = "src/datasets/yeastnet2-noIEA.mnet";
//    String filename = "src/datasets/allint-latest.mnet";
//    String filename = "src/datasets/allint-apr10.mnet";
//    String filename = "src/datasets/allint-apr10_debug.mnet";
//    String filename = "src/datasets/mancur-apr10.mnet";
//    String filename = "src/datasets/ito2001.mnet";
//    String filename = "src/datasets/expmore3.mnet";
//    String filename = "src/datasets/refmore3.mnet";
//    String filename = "src/datasets/curmore2.mnet";
//    String filename = "src/datasets/bothint.mnet";
//    String filename = "src/datasets/atted-coexp.mnet";
//    String filename = "src/datasets/atted-coexp-3highest.mnet";
//    File propInfoFile = new File("src/datasets/gene_ontology-100329-short.txt");
    File propInfoFile = new File("gene_ontology-100329-short.txt");
//    File propInfoFile = new File("gene_ontology-150411.txt");
//    File propInfoFile = new File("src/datasets/mesh_file.txt");
//    String nodeFileName = "src/datasets/yeastnet2-noIEA.node";
    String nodeFileName = "yeastnet2-noIEA.node";
//    String nodeFileName = "atted.node";
//    String nodeFileName = "atted-loginvMR.node";
//    String nodeFileName = "atted-invMR.node";
//    String nodeFileName = "src/datasets/atted-coexp-3highest.node";
//    String nodeFileName = "src/datasets/2010-12-15-mint-Saccharomyces-binary.mitab26.txt.node";
//    String edgeFileName = "src/datasets/yeastnet2-noIEA.edge";
    String edgeFileName = "yeastnet2-noIEA.edge";
//    String edgeFileName = "atted.edge";
//    String edgeFileName = "atted-loginvMR.edge";
//    String edgeFileName = "atted-invMR.edge";
//    String edgeFileName = "src/datasets/atted-coexp-3highest.edge";
//    String edgeFileName = "src/datasets/2010-12-15-mint-Saccharomyces-binary.mitab26.txt.edge";
//    String nodeFileName = "src/datasets/p53.node";
//    String edgeFileName = "src/datasets/p53.edge";
    File fileToOpen = null;
    Graph<Object, Object> realGraph, originalGraph;
    Graph dynamicGraph, currentLevelGraph;
    AggregateLayout<Object, Object> layout;
//    private boolean firstLoad = true;
    StatusBar statusBar = new StatusBar();
    JButton zoomInButton;
    JButton zoomOutButton;
    JButton forwardButton;
    JButton backButton;
    JButton rewindButton;
    JScrollPane listScroller = new JScrollPane();
    DefaultListModel listModel = new DefaultListModel();
    JList searchResList = new JList();
    JTextField searchTextField = new JTextField(6);
    SpinnerNumberModel oClusterModel = new SpinnerNumberModel(12, 2, 30, 1);
    JSpinner thresholdTextField = new JSpinner(oClusterModel);
//    JTextField thresholdTextField = new JTextField("12", 4);
    SpinnerNumberModel oNumModel = new SpinnerNumberModel(2, 1, 100, 1);
    JSpinner numHops = new JSpinner(oNumModel);
//    JTextField numHops = new JTextField("2", 5);
    JScrollPane nsSlidersSP = new JScrollPane();
    JPanel namespaceSliders = new JPanel();
    ArrayList<JSlider> oLstOntSlider = new ArrayList<JSlider>();
    HashMap<JSlider, Integer> nsWeightMap = new HashMap<JSlider, Integer>();
//    JSlider ccSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
//    JSlider bpSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 10);
//    JSlider mfSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
    JSlider propEdgeSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 9);
//    Set<Set<Object>>[] hierCSet = null;
//    Set<Set<Object>>[] comNodehierSet = null;
    Set<Set>[] hierCSet = null;
    Set<Set>[] comNodeHierSet = null;
    public Object highlightedNode = null;
    public BioObject searchedBioObj = null;
    public Color highlightedNodeFormerColor = null;
    private int currentLevel = 0;

    /* now cSetToComNodeCSet is not used anywhere, it is only inputed*/
    Map<Set<Set>, Set<Set>> cSetToComNodeCSet = new HashMap<Set<Set>, Set<Set>>();
    Map<Set, Set> flatClustToComNodeClust = new HashMap<Set, Set>();
    Map<Set, Set> comNodeClustToFlatClust = new HashMap<Set, Set>();
//    Map<Set, DataCluster> nodeToDataCluster = new HashMap<Set, DataCluster>();
    private PropInfoProcessor curGOP = null;
    private PropInfoProcessor curGOPCentered = null;
    private boolean inRecenteredMode = false;
    private ArrayList clickedNodes = null;
    private Set<Set> curCSetCentered = null;
    private Set<Set> curCSetCenteredBPC = null;
    private Set<Set> curComNodeCSetCenteredBPC = null;
    private Set<Set> curComNodeCSetCentered = null;
    private Set centerNodeSet = null;
    private Set<Set> curCSet = null;
    private Set<Set> curCSetBeforePropClust = null;
    private Set<Set> curComNodeCSet = null;
    private Set<Set> curComNodeCSetBeforePropClust = null;
    private Map<Object, Point2D> curVertexLocMap = null;
    private Map<Object, Integer> curVertexSizeMap = new HashMap<Object, Integer>();
    private Map<Object, Point2D> curUsualVertexLocMap = null;
//    private ArrayList historyCSetList = new ArrayList<Set<Set<Object>>>();
//    private ArrayList historyComNodeCSetList = new ArrayList<Set<Set<Object>>>();
//    private ArrayList<PropInfoProcessor> historyGOProcessors = new ArrayList<PropInfoProcessor>();
    private ArrayList<ViewHistory> viewHistoryList = new ArrayList<ViewHistory>();
    private int currentViewHisPos = -1;
//    OBOSession obosession = null;
    /* numOfLouvainClusters is not used for specific purposes yet */
    public int numOfLouvainClusters = 0;
    DataVector centroid = new DataVector();
    DataCluster[] semanticClusterList = new DataCluster[1];
    Map<Object, DataVector> nodesPropVectorMap = new HashMap<Object, DataVector>();
    Map<BioObject, Object> nodeToClusterMap;
    Map<BioObject, Object> usualNodeToClusterMap;
    /*nodesLabelList is used for containing labels of each cluster (not primitive node)*/
    Map<Object, ArrayList<PropertyTerm>> nodesLabelList = new HashMap<Object, ArrayList<PropertyTerm>>();
    boolean splited = false;
    private boolean overallLessThanThresh = false;
    double maxMetaEdgeWeight = 0;
    double minMetaEdgeWeight = Double.MAX_VALUE;
    double maxBioEdgeWeight = 0;
    double minBioEdgeWeight = Double.MAX_VALUE;
    private int minNumOfMem = Integer.MAX_VALUE;
    private int maxNumOfMem = 0;
    private int clusterSizeInLayout = 88;
//    private int clusterSizeInLayout = 158;
    private int nodeSizeInLayout = 50;
//    private int nodeSizeInLayout = 90;
    private int canvasSizeWidth = 585;
//    private int canvasSizeWidth = 600;
//    private int canvasSizeWidth = 1080;
    private int canvasSizeHeight = 585;
    private int outerRadius = 585;
    private int innerRadius = 105;
//    private int innerRadius = 120;
//    private int innerRadius = 216;
    private int eastPanelSize = 205;
    private int westPanelSize = 225;
    private double sizeMultiplier = 0.5;
    static PrintWriter pw = null;
//    private Map<String, String> freqWordsDict = new HashMap<String, String>();
    private boolean isLinux = false;
    private Font vertexFont, plainEdgeFont, boldEdgeFont;
    private JPanel westPanel, eastPanel;
    private GraphZoomScrollPane center;
    private boolean proportionalScaling = false;

    public static void main(String[] args) {

        final NaviClusterApp app = new NaviClusterApp();

        if (args != null & args.length == 1) {
            if ("-pp".equalsIgnoreCase(args[0])) {
                app.proportionalScaling = true;
            }
        }
        app.setTitle("NaviCluster v.2.0");
        app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        app.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                app.pw.close();
                System.out.println("window closing");
            }
        });

        app.setupLookAndFeel();
        app.start();
        app.pack();

//        app.setLocation((int) (((double) Toolkit.getDefaultToolkit().getScreenSize().width - app.getSize().width) / 2), (int) (((double) Toolkit.getDefaultToolkit().getScreenSize().height - app.getSize().height) / 2));
        if (Toolkit.getDefaultToolkit().getScreenSize().width > app.getSize().width) {
            app.setLocation((int) (((double) Toolkit.getDefaultToolkit().getScreenSize().width - app.getSize().width) / 2), app.getLocation().y);
        }
//        app.setLocationByPlatform(true);
        app.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponents(g2d);
    }

    protected void setupLookAndFeel() {

        // Get the default toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();
        // Print the screen size
//        System.out.println("Screen size : " + scrnsize);
        scrnsize.setSize(scrnsize.getWidth() - 80, scrnsize.getHeight() - 175);
//        System.out.println("scrnsize "+scrnsize);
        canvasSizeWidth = (int) (scrnsize.getWidth() - eastPanelSize - westPanelSize);
        if (canvasSizeWidth < 570) {
            canvasSizeWidth = 570;
        } else if (canvasSizeWidth > 1900) {
            canvasSizeWidth = 1900;
        }
        canvasSizeHeight = (int) scrnsize.getHeight();
        if (canvasSizeHeight < 570) {
            canvasSizeHeight = 570;
        } else if (canvasSizeHeight > 1900) {
            canvasSizeHeight = 1900;
        }
        outerRadius = canvasSizeHeight < canvasSizeWidth ? canvasSizeHeight : canvasSizeWidth;
        innerRadius = (int) ((outerRadius - 585) * 0.224242424 + 105);

//        System.out.println("can wid "+canvasSizeWidth+" can hi "+canvasSizeHeight+" outerRadius "+outerRadius+" innerRadius "+innerRadius);
        vertexFont = new Font("Serif", Font.PLAIN, 13);
        plainEdgeFont = new Font("Helvetica", Font.PLAIN, 14);
        boldEdgeFont = new Font("Helvetica", Font.BOLD, 14);
//        plainEdgeFont = new Font("Sans", Font.PLAIN, 14);
//        boldEdgeFont = new Font("Sans", Font.BOLD, 14);

        System.setProperty("awt.useSystemAAFontSettings", "lcd");
        System.setProperty("swing.aatext", "true");

        String os = System.getProperty("os.name");

        try {
//			if (LookUtils.IS_OS_WINDOWS) {
            if (os.startsWith("Windows") || os.startsWith("Win") || os.startsWith("win")) {
                /*
                 * For Windows: just use platform default look & feel.
                 */
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            } else if (os.startsWith("Mac") || os.startsWith("mac")) {

                /*
                 * For Mac: move menue bar to OS X default bar (next to Apple
                 * icon)
                 */
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                com.apple.eawt.Application.getApplication().setQuitHandler(new com.apple.eawt.QuitHandler() {

                    @Override
                    public void handleQuitRequestWith(com.apple.eawt.AppEvent.QuitEvent qe, com.apple.eawt.QuitResponse qr) {
                        NaviClusterApp.pw.close();
                        System.out.println("window closing");
                        qr.performQuit();
                    }
                });

            } else {
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                String[] allFonts = env.getAvailableFontFamilyNames();
                HashSet<String> allFontSet = new HashSet<String>(Arrays.asList(allFonts));
                //edge font
                if (!allFontSet.contains("Helvetica")) {
                    plainEdgeFont = new Font("Sans", Font.PLAIN, 14);
                    boldEdgeFont = new Font("Sans", Font.BOLD, 14);
                }
                //vertex font
                if (allFontSet.contains("Century Schoolbook L")) {
                    vertexFont = new Font("Century Schoolbook L", Font.PLAIN, 12);
                } else if (allFontSet.contains("URW Bookman L")) {
                    vertexFont = new Font("URW Bookman L", Font.PLAIN, 12);
                } else if (allFontSet.contains("Bitstream Charter")) {
                    vertexFont = new Font("Bitstream Charter", Font.PLAIN, 13);
                } else if (allFontSet.contains("DejaVu Serif")) {
                    vertexFont = new Font("DejaVu Serif", Font.PLAIN, 12);
                } else if (allFontSet.contains("Lucida Bright")) {
                    vertexFont = new Font("Lucida Bright ", Font.PLAIN, 12);
                } else if (allFontSet.contains("Bitstream Vera Serif")) {
                    vertexFont = new Font("Bitstream Vera Serif", Font.PLAIN, 12);
                } else if (allFontSet.contains("FreeSerif")) {
                    vertexFont = new Font("FreeSerif", Font.PLAIN, 13);
                } else // assume that Seif font in Linux is larger than in Windows and Mac, so reduce the size to 12
                {
                    vertexFont = new Font("Serif", Font.PLAIN, 12);
                }
//                System.out.println("font used "+vertexFont.getName());


                /*
                 * For Unix platforms, use JGoodies Looks
                 */
//                UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
//                Plastic3DLookAndFeel.set3DEnabled(true);
//                Plastic3DLookAndFeel.setCurrentTheme(new com.jgoodies.looks.plastic.theme.SkyBluer());
//                Plastic3DLookAndFeel.setTabStyle(Plastic3DLookAndFeel.TAB_STYLE_METAL_VALUE);
//                Plastic3DLookAndFeel.setHighContrastFocusColorsEnabled(true);

                UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

            }
        } catch (Exception e) {
            pw.write("Can't set look & feel: \n" + e.getMessage());
        }
    }

    public void start() {

        UIWanted = true;

        try {
            pw = new PrintWriter(new File("output-log"));
//            PrintWriter ppw = new PrintWriter(new File("ooo"));
//            ppw.println("test");
//            ppw.close();

//            JFileChooser fileChooser = new JFileChooser(new File(filename + ".txt"));
//            fileChooser.setAcceptAllFileFilterUsed(false);
//            fileChooser.addChoosableFileFilter(new TextFileFilter());

//            System.out.println(new File(filename+".txt").getPath());
//            fileChooser.showOpenDialog(NaviClusterApp.this);
//            fileToOpen = fileChooser.getSelectedFile();

//            fileToOpen = new File(filename);
//            System.out.println(fileToOpen.getAbsolutePath());
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileToOpen)));
//
//            realGraph = new SparseMultigraph<Object, Object>();
            StopWatch loadingTime = new StopWatch();
            loadingTime.start();

            ImportFileForm form = ImportFileForm.getInstance(NaviClusterApp.this);
            form.getoTxtNodeFile().setText(nodeFileName);
            form.getoTxtEdgeFile().setText(edgeFileName);
//            System.out.println("form location "+form.getLocation());
            form.loadImportFile();
//                ImportFileForm form = new ImportFileForm(NaviClusterApp.this);
//                form.setVisible(true);

//                if (form.getNetwork() != null) {
//                if (fileToOpen != null) {
//                    try {
            minBioEdgeWeight = Double.MAX_VALUE;
            maxBioEdgeWeight = 0;

//                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileToOpen)));

//                        StopWatch loadingTime = new StopWatch();
//                        loadingTime.start();

            realGraph = form.getNetwork();
//                        for (int i = 0; i < sArr.length; i++){
//                            if (sArr[i] != form.getsArr()[i])
//                                System.out.println("i "+i+" sarr "+sArr[i]+" "+form.getsArr()[i]);
//                            if (dArr[i] != form.getdArr()[i])
//                                System.out.println("i "+i+" darr "+dArr[i]+" "+form.getdArr()[i]);
//                            if (wArr[i] != form.getwArr()[i])
//                                System.out.println("i "+i+" warr "+wArr[i]+" "+form.getwArr()[i]);
//                        }
            sArr = form.getsArr();
            dArr = form.getdArr();
            wArr = form.getwArr();
            nodeMap.clear();
            nodeMap.putAll(form.getIDToNodeMap());
            minBioEdgeWeight = form.getMinBioEdgeWeight();
            maxBioEdgeWeight = form.getMaxBioEdgeWeight();
            isWeightGraph = true;
            disconNodeSet.clear();
            disconNodeSet.addAll(form.getDisconNodeSet());

//            realGraph = loadModNet(br);
            originalGraph = realGraph;
            dynamicGraph = realGraph;

            loadingTime.stop();
            pw.println("Time used for loading from input file: " + loadingTime);
            System.out.println("Time used for loading from input file: " + loadingTime);

            statusBar.setMessage("Network Files: " + form.getNodeFile().getName() + ", " + form.getEdgeFile().getName() + " (<b>" + originalGraph.getVertexCount() + "</b> nodes <b>" + originalGraph.getEdgeCount() + "</b> edges).");
            statusBar.finalizeCoreText();

            /* Load frequent word dictionary file for processing labels*/
//            loadDictionary();

            /* This is top level. */
            ArrayList retList = louvainCluster(realGraph.getVertexCount(), true);
            preparePropertyInfoProcessor(propInfoFile);
            numOfLouvainClusters = ((Set) retList.get(0)).size();
//            System.out.println("numOfLouvainCluster"+ numOfLouvainClusters);
//            Set tmpset = (Set)retList.get(0);
//            for (Object obj : tmpset){
//                if (obj instanceof Set){
//                    System.out.println("num "+((Set)obj).size()+" "+obj);
//                } else
//                    System.out.println("num 1 "+obj);
//            }
            Set<Set> cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, true, true, true);
//            for (Object obj : cSet){
//                if (obj instanceof Set){
//                    System.out.println("num "+((Set)obj).size());
//                } else
//                    System.out.println("num 1");
//            }

            ///DEBUG///
//            printCSet(cSet);
            ///////////
            /* if do on all nodes in canvas, visualize the result also, or else just do clustering */
            loadingTime.start();
            dynamicGraph = createGraph(cSet);
            usualNodeToClusterMap = new HashMap(nodeToClusterMap);
            loadingTime.stop();
            pw.println("Time used for creating graph from cSet: " + loadingTime);
            System.out.println("Time used for creating graph from cSet: " + loadingTime);
            colorCluster(cSet, mainColor);
            currentLevelGraph = dynamicGraph;
//            nodesLabelList = chooseTwoTermsForLabel(dynamicGraph);
//            loadingTime.start();
            initVV();
//            loadingTime.stop();
//            pw.println("Time used for init VV: " + loadingTime);
//            System.out.println("Time used for init VV: " + loadingTime);
            initComponents();
            saveState();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            pw.println("Error in loading graph");
            System.out.println("Error in loading graph");
            e.printStackTrace();
        }
    }

    /**
     * DEPRECATED
     * Load input file in modified .net pajek format
     * @param br
     * @return
     * @throws java.io.IOException
     */
    public Graph<Object, Object> loadModNet(BufferedReader br) throws IOException {

        String st;
        StringTokenizer stn;
        int src, dest;
        double weight = 1, numNode = -1;

        Graph<Object, Object> newgraph = new SparseMultigraph<Object, Object>();
        BioObject bioObj = null;

        int id = -1;
        String stdName = "", name = "", databaseid = "", type = "";
        String propTermList = "", ref = "", aspect = "";
        StopWatch sw = new StopWatch();

        //check header of vertices zone
        while ((st = br.readLine()) != null) {
            stn = new StringTokenizer(st);
            if (stn.hasMoreTokens()) {
                String temp = stn.nextToken();
                if (temp.equalsIgnoreCase("*Vertices")) {
                    numNode = Integer.parseInt(stn.nextToken());
                    break;
                } else {
                    System.err.println("Error Loading Graph: file format is not relevant");
                    System.exit(1);
                }

            } else {
                continue;
            }
        }


        sw.start();
        for (int i = 0; i < numNode; i++) {
            st = br.readLine();
//            System.out.println(st);
            stn = new StringTokenizer(st, "\t", true);
            assert (stn.hasMoreTokens());
            id = Integer.parseInt(stn.nextToken());
            if (stn.nextToken().equals("\t"));

            assert (stn.hasMoreTokens());
            stdName = stn.nextToken();
            if (stdName.equals("\t")) {
                stdName = "";
            } else {
                stn.nextToken();
            }

            assert (stn.hasMoreTokens());
            name = stn.nextToken();
            if (stn.nextToken().equals("\t"));

            assert (stn.hasMoreTokens());
            databaseid = stn.nextToken();
            if (stn.nextToken().equals("\t"));

            if (databaseid.equals("\t")) {
                bioObj = new BioObject(id, name);
                bioObj.setStandardName(stdName);
            } else {
//                assert (stn.hasMoreTokens());
//                databaseid = stn.nextToken();
//                if (stn.nextToken().equals("\t"));
                assert (stn.hasMoreTokens());
                type = stn.nextToken();
                if (stn.nextToken().equals("\t"));

                assert (stn.hasMoreTokens());
                propTermList = stn.nextToken();
                if (stn.nextToken().equals("\t"));

                assert (stn.hasMoreTokens());
                ref = stn.nextToken();
                if (stn.nextToken().equals("\t"));

                assert (stn.hasMoreTokens());
                aspect = stn.nextToken();
//            if (stn.nextToken().equals("\t"));

                bioObj = new BioObject(id, databaseid, name);
                bioObj.setStandardName(stdName);
                bioObj.setType(type);

                StringTokenizer stz = new StringTokenizer(propTermList, "|");
                while (stz.hasMoreTokens()) {
                    bioObj.getPropTermList().add(stz.nextToken());
                }

                stz = new StringTokenizer(ref, "|");
                while (stz.hasMoreTokens()) {
                    bioObj.getRefList().add(stz.nextToken());
                }

                stz = new StringTokenizer(aspect, "|");
                while (stz.hasMoreTokens()) {
                    bioObj.getAspectList().add(stz.nextToken());
                }
            }
            newgraph.addVertex(bioObj);
            nodeMap.put(id, bioObj);
        }
        disconNodeSet.addAll(nodeMap.keySet());
        sw.stop();
        pw.println("load vertices time: " + sw);
        System.out.println("load vertices time: " + sw);
        sw.start();

        int numEdge = -1;

        //check header of edges zone
        while ((st = br.readLine()) != null) {
            stn = new StringTokenizer(st);
            if (stn.hasMoreTokens()) {
                String temp = stn.nextToken();
                if (temp.equalsIgnoreCase("*Edges")) {
                    numEdge = Integer.parseInt(stn.nextToken());
                    break;
                } else {
                    System.err.println("Error Loading Graph");
                    System.exit(1);
                }

            } else {
                continue;
            }
        }
        /* the previous version made use of StringBuilder which is faster than normal string
         * but slower than current version
         * The current version makes use of three arrays, sArr, dArr, WArr
         */
//        StringBuilder stb = new StringBuilder(10000);

        sArr = new int[numEdge];
        dArr = new int[numEdge];
        wArr = new double[numEdge];
        boolean isWeight = false;

//        ew = new EdgeWeightLabeller<Number>();
        for (int i = 0; i < numEdge; i++) {

            st = br.readLine();
//            if (tNumLinks%10000==0)
//               System.out.print(".");
            stn = new StringTokenizer(st);

            src = Integer.parseInt(stn.nextToken());
            dest = Integer.parseInt(stn.nextToken());
//            stb.append(src).append("\t").append(dest).append("\t");
            sArr[i] = src;
            dArr[i] = dest;

//            Number vSrc = null,vDest = null;
//            newGraph.addVertex(src);
//            newGraph.addVertex(dest);
//            System.out.println("src: "+src+" dest: "+dest);
//            String e1 = src + ":" + dest;
            if (stn.hasMoreTokens()) {
//                weight = Integer.parseInt(stn.nextToken());
                weight = Double.parseDouble(stn.nextToken());
//                stringStream += weight;
//                stb.append(weight);
                wArr[i] = weight;
//                ew.setWeight(e1, (int) weight);
                isWeight = true;
            }
//            stringStream += "\n";
//            stb.append("\n");
//            System.out.println("e1 "+e1);
            if (weight >= minBioEdgeWeight) {
                minBioEdgeWeight = weight;
            }
            if (weight < maxBioEdgeWeight) {
                maxBioEdgeWeight = weight;
            }
            disconNodeSet.remove(src);
            disconNodeSet.remove(dest);
            BioEdge e1 = new BioEdge(nodeMap.get(src), nodeMap.get(dest), weight);
            newgraph.addEdge(e1, nodeMap.get(src), nodeMap.get(dest));
//            if (!newGraph.getEdges().contains(new UndirectedSparseEdge(vSrc, vDest)))
//                e1 = (Edge) newGraph.addEdge(new UndirectedSparseEdge(vSrc, vDest));

        }

        if (maxBioEdgeWeight == minBioEdgeWeight) {
            maxBioEdgeWeight++;
        }
        sw.stop();
//        stringStream = stb.toString();
        if (!isWeight) {
            wArr = null;
        }
        isWeightGraph = isWeight;
        pw.println("Load edges time: " + sw);
        pw.println("num nodes: " + newgraph.getVertexCount() + " num edges: " + newgraph.getEdgeCount());
//        pw.println("Num nodes double checked: " + numNode + " Num edges double checked: " + numEdge);
        System.out.println("Load edges time: " + sw);
        System.out.println("num nodes: " + newgraph.getVertexCount() + " num edges: " + newgraph.getEdgeCount());
//        System.out.println("Num nodes double checked: " + numNode + " Num edges double checked: " + numEdge);
//        statusBar.setMessage("Network File: "+fileToOpen.getName()+" (<b>"+newgraph.getVertexCount()+"</b> nodes <b>"+newgraph.getEdgeCount()+"</b> edges), " +
//                "Prop Info File: "+ propInfoFile.getName()+".");
        statusBar.setMessage("Network File: " + fileToOpen.getName() + " (<b>" + newgraph.getVertexCount() + "</b> nodes <b>" + newgraph.getEdgeCount() + "</b> edges).");
        statusBar.finalizeCoreText();
//        System.out.println(newGraph.getEdges());
        return newgraph;
    }

//    public void loadDictionary() {
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(new File("src/datasets/freq-words-dict.txt"));
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String st = "", full = "", abbr = "";
//            StringTokenizer stz = null;
//            while ((st = br.readLine()) != null) {
//                stz = new StringTokenizer(st, "\t", true);
//                assert (stz.hasMoreTokens());
//                full = stz.nextToken().trim();
//
//                if (stz.nextToken().equals("\t"));
//
//                if (stz.hasMoreTokens()) {
//                    abbr = stz.nextToken().trim();
//                } else {
//                    abbr = "";
//                }
////                if (stz.nextToken().equals("\t"));
//
////            assert (stz.hasMoreTokens());
////            stz.nextToken();
////            if (stz.nextToken().equals("\t"));
////
////            assert (stz.hasMoreTokens());
////            stz.nextToken();
////            if (stz.nextToken().equals("\t"));
////
////            assert (stz.hasMoreTokens());
////            stz.nextToken();
////            if (stz.nextToken().equals("\t"));
////
////            assert (stz.hasMoreTokens());
////            stz.nextToken();
//
//                freqWordsDict.put(full, abbr);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fis.close();
//            } catch (IOException ex) {
//                Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }

    /* Business logic zones (Core methods) */
    public void clearState() {
        viewHistoryList.clear();
        currentViewHisPos = -1;
        backButton.setEnabled(false);
        forwardButton.setEnabled(false);
    }

    /**
     * Save a graph view along with other state information in viewhistory arraylist
     * Discard states from current position to the end of list.
     */
    public void saveState() {

//        ViewHistory viewhist = new ViewHistory(curCSetBeforePropClust, curComNodeCSetBeforePropClust, curGOP, dynamicGraph);
        ViewHistory viewhist = new ViewHistory(curCSetBeforePropClust, curComNodeCSetBeforePropClust, curGOP, layout);
//        viewHistoryList.add(viewhist);
        viewHistoryList.add(++currentViewHisPos, viewhist);
        ArrayList<ViewHistory> tempVHList = new ArrayList<ViewHistory>(viewHistoryList.subList(0, currentViewHisPos + 1));
        viewHistoryList.clear();
        viewHistoryList.addAll(tempVHList);

        viewhist.setCSet(curCSet);
        viewhist.setComNodeCSet(curComNodeCSet);
        viewhist.setClickedVertices(new ArrayList());
//        currentViewHisPos = viewHistoryList.size()-1;
        if (centerNodeSet == null) {
            viewhist.setRecenteredNodes(null);
        } else {
            viewhist.setRecenteredNodes(new ArrayList(centerNodeSet));
        }

        viewhist.setNodesPropVectorMap(nodesPropVectorMap);
        viewhist.setVertexSizeMap(curVertexSizeMap);
        viewhist.setVertexLocationMap(new HashMap<Object, Point2D>(curVertexLocMap));
//        System.out.println("--------------"+curVertexLocMap.size());
        viewhist.setUsualGraphVerLocMap(curUsualVertexLocMap);
//        System.out.println("--------------"+curUsualVertexLocMap.size());
//        viewhist.setVertexLocationMap(verLocationMap);

        viewhist.setStoredGraph(dynamicGraph);
        viewhist.setUsualGraph(currentLevelGraph);
        viewhist.setNodeToClusterMap(nodeToClusterMap);
        viewhist.setUsualNodeToClusterMap(usualNodeToClusterMap);

        viewhist.setInConcentrationMode(inRecenteredMode);
        viewhist.setCurGOPCentered(curGOPCentered);
        viewhist.setCurCSetCentered(curCSetCentered);
        viewhist.setCurCSetCenteredBPC(curCSetCenteredBPC);
        viewhist.setCurComNodeCSetCentered(curComNodeCSetCentered);
        viewhist.setCurComNodeCSetCenteredBPC(curComNodeCSetCenteredBPC);

        viewhist.setNodesLabelList(nodesLabelList);
        viewhist.setMaxBioEdge(maxBioEdgeWeight);
        viewhist.setMinBioEdge(minBioEdgeWeight);
        viewhist.setMaxMetaEdge(maxMetaEdgeWeight);
        viewhist.setMinMetaEdge(minMetaEdgeWeight);
        viewhist.setMaxNumMem(maxNumOfMem);
        viewhist.setMinNumMem(minNumOfMem);
        viewhist.setZoomInEnabled(zoomInButton.isEnabled());
//        Set<Set> tempCurComNodeCSet = new HashSet(curComNodeCSet);
//        int countBioObjSet = 0;
        boolean allBioObj = true;
        int numNodesInCurrentGraphView = 0;
        for (Object ver : dynamicGraph.getVertices()) {
            if (!(ver instanceof BioObject)) {
                allBioObj = false;
                numNodesInCurrentGraphView += ((Set) ver).size();
//                break;
            } else {
                numNodesInCurrentGraphView++;
            }
        }
        viewhist.setNumNodesInCurrentGraphView(numNodesInCurrentGraphView);
        if (allBioObj) {
            viewhist.setIsLastLevel(true);
            zoomInButton.setEnabled(false);
        } else {
            viewhist.setIsLastLevel(false);
            zoomInButton.setEnabled(true);
        }

//        System.out.println("GO Proc size: " + curGOP.clusterScoreMap.size());
//        zoomOutButton.setEnabled(true);
//        backButton.setEnabled(true);
    }

    /**
     * Performs Louvain-clustering using three relevant arrays (sArr, dArr, wArr)
     * It is used at first after loading input file
     * It calls louvainClusterer method of MainLouvain class
     * If we would like to re-run louvain-clustering after loading, we have to use reLouvainCluster method
     *
     * @param numNode   number of nodes
     * @param isTopLevel
     * @return hierarchy of communities
     */
    public ArrayList louvainCluster(int numNode, boolean isTopLevel) {
        ArrayList retList = null;
        try {
            StopWatch stopwatch = new StopWatch();
            stopwatch.start();
            MainLouvain ml = new MainLouvain();
            int[][] hierarchyComNode = null;

            if (sArr != null) {
                int numDiscNode = disconNodeSet.size();
                if (numDiscNode > 0) {
                    int[] tmpSArr = new int[sArr.length + numDiscNode], tmpDArr = new int[dArr.length + numDiscNode];
                    double[] tmpWArr = new double[wArr.length + numDiscNode];
//                    int[][] tmpHierarchyComNode = new int[hierarchyComNode.length][];
//                    tmpHierarchyComNode[0] = new int[hierarchyComNode[0].length + numDiscNode];
                    System.arraycopy(sArr, 0, tmpSArr, 0, sArr.length);
                    System.arraycopy(dArr, 0, tmpDArr, 0, dArr.length);
                    System.arraycopy(wArr, 0, tmpWArr, 0, wArr.length);
                    int ind = 0;
                    /* for disconnected nodes, add dummy self-loop edge to the list sent to Louvain Clustering
                     * These edge weights are 0.
                     */
                    for (Integer id : disconNodeSet) {
//                System.out.println("id "+id);
                        tmpSArr[sArr.length + ind] = id;
                        tmpDArr[dArr.length + ind] = id;
                        tmpWArr[wArr.length + ind] = 0.0D;
                        ind++;
                    }
                    sArr = tmpSArr.clone();
                    dArr = tmpDArr.clone();
                    wArr = tmpWArr.clone();
                }
                hierarchyComNode = ml.louvainClusterer(sArr, dArr, wArr);
            } else {
                hierarchyComNode = new int[2][];
                for (int i = 0; i < hierarchyComNode.length; i++) {
                    hierarchyComNode[i] = new int[numNode];
                    for (int j = 0; j < hierarchyComNode[i].length; j++) {
                        hierarchyComNode[i][j] = j;
                    }
                }
            }
//            for (int i = 0; i < hierarchyComNode.length; i ++){
//                for (int j = 0; j < hierarchyComNode[i].length; j++){
//                    System.out.print(hierarchyComNode[i][j]+ " ");
//                }
//                System.out.println("");
//            }
//            System.out.println("hierarchy "+hierarchyComNode[0][hierarchyComNode[0].length-1]);
//            System.out.println("hierarchy "+hierarchyComNode.length);
            pw.println("After Louvain Clustering");
            System.out.println("After Louvain Clustering");

            retList = processLouvainClusterSet(hierarchyComNode, isTopLevel);
            pw.println("After processing LC");
            System.out.println("After processing LC");
            stopwatch.stop();

            /* we can stop program in hard-code manner by setting UIWanted to false */
//            if (!UIWanted) {
//                System.exit(0);
//            }

//            return retList;
        } catch (FileNotFoundException ex) {
            pw.println("File not found! " + ex.getMessage());
            System.out.println("File not found! " + ex.getMessage());
            Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            pw.println("IOException! " + ex.getMessage());
            System.out.println("IOException! " + ex.getMessage());
            Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
        }

//        } finally {
//            return retList;
//        }
        return retList;
    }

    /** Initialize many things that will be used in property-based clustering
     * Currently, it loads property information file and flatClust colors for the labels of property terms
     */
    public void preparePropertyInfoProcessor(File propInfoFile) throws FileNotFoundException, IOException {
        /* load property terms map from text file */
//        PropInfoProcessor.loadGOTermsMap();
        PropInfoProcessor.loadPropInfoFile(propInfoFile);
        /* By default, flatClust Biological Process namespace to 1 and others to 0 for GO */
        if ((PropInfoProcessor.getNamespaceMap().keySet().size() == 3) && (PropInfoProcessor.getNamespaceMap().containsKey("biological_process"))
                && (PropInfoProcessor.getNamespaceMap().containsKey("molecular_function")) && (PropInfoProcessor.getNamespaceMap().containsKey("cellular_component"))) {
            PropInfoProcessor.getNamespaceMap().get("biological_process").setWeight(1);
            PropInfoProcessor.getNamespaceMap().get("molecular_function").setWeight(0);
            PropInfoProcessor.getNamespaceMap().get("cellular_component").setWeight(0);
        } else {
//            int i = 0;
//            for (NameSpace ns : PropInfoProcessor.getNamespaceMap().values()){
//                if (i > 0)
//                    ns.setWeight(0);
//                i++;
//            }
        }
        PropInfoProcessor.setColorsForNamespaces(mainColor);
        /* initialize map for GO namespace */
//        Map<NameSpace, Double> map = new HashMap<NameSpace, Double>();
//        map.put(NameSpace.BP, (double) bpSlider.getValue());
//        map.put(NameSpace.MF, (double) mfSlider.getValue());
//        map.put(NameSpace.CC, (double) ccSlider.getValue());
//        PropInfoProcessor.setWeightMap(map);
    }

    /**
     * Process raw cluster flatClust information from louvain clustering by correspondingly putting
     * BioObjects into sets.
     * isTopLevel boolean is used to specify if we want to input information of overall structure
     * into hierCSet and comNodehierSet.
     * Then, perform property-based clustering if appropriate.
     * Also, it visualizes the result as a graph of clusters and metaedges
     * 
     * @param hierarchyComNode
     * @param isTopLevel
     * @param doOnAllNodesInCanVas
     * @return
     * @throws java.io.IOException
     */
    private ArrayList<Set<Set>> processLouvainClusterSet(int[][] hierarchyComNode, boolean isTopLevel) throws IOException {
        ArrayList<Set<Set>> retList = putLouvainResultToSet(hierarchyComNode, isTopLevel);

//        if (isTopLevel) {
//            printStatAboutClusters(g,(Set)retList.get(0));
//        }


        return retList;

    }

    /**
     * Put raw louvain clustering results into the flatClust of flatClust of BioObjects
     * really used in the graph.
     * @param hierarchyComNode
     * @param isTopLevel
     * @return
     */
    private ArrayList<Set<Set>> putLouvainResultToSet(int[][] hierarchyComNode, boolean isTopLevel) {
        ArrayList<Integer> labelList = new ArrayList();
        ArrayList<Set<Object>> csList = new ArrayList();
        ArrayList<Set<Object>> newCsList = new ArrayList();
        ArrayList<Set<Object>> arrComNode = new ArrayList();
        ArrayList<Set<Object>> newArrComNode = new ArrayList();
        Set<Set> cSet = new HashSet<Set>();
        Set<Set> comNodeCSet = new HashSet<Set>();


//        int numDiscNode = 0;
//        int numDiscNode = disconNodeSet.size();
//        if (numDiscNode > 0) {
//            int[][] tmpHierarchyComNode = new int[hierarchyComNode.length][];
//            tmpHierarchyComNode[0] = new int[hierarchyComNode[0].length + numDiscNode];
//            System.arraycopy(hierarchyComNode[0], 0, tmpHierarchyComNode[0], 0, hierarchyComNode[0].length);
//            int ind = 0;
//            for (Integer id : disconNodeSet) {
////                System.out.println("id "+id);
//                tmpHierarchyComNode[0][hierarchyComNode[0].length + ind] = id;
//            }
//
//            hierarchyComNode[0] = tmpHierarchyComNode[0].clone();
//
//        }
        if (isTopLevel) {
            hierCSet = new HashSet[hierarchyComNode.length];
            comNodeHierSet = new HashSet[hierarchyComNode.length];
        }
//        System.out.println("numdiscnode "+numDiscNode);
//        System.out.println("hierarchyComNode 0 "+hierarchyComNode[0][hierarchyComNode[0].length-2]);
//        System.out.println("hierarchyComNode 0 "+hierarchyComNode[0][hierarchyComNode[0].length-1]);
        pw.println("Start process raw cluster set!");
        System.out.println("Start process raw cluster set!");

        // hierarchyComNode should have length >= 2
        // process level 0-1 of hierarchy array
        for (int j = 0; j < hierarchyComNode[1].length; j++) {
            csList.add(new HashSet<Object>());
            arrComNode.add(new HashSet());
        }
        for (int j = 0; j < hierarchyComNode[0].length; j++) {
            if (!labelList.contains(hierarchyComNode[0][j])) {
                labelList.add(hierarchyComNode[0][j]);
            }
            int index = labelList.indexOf(hierarchyComNode[0][j]);
            Set<Object> cluster = csList.get(index);
            Set clusterOfComNode = arrComNode.get(index);
//            if (nodeMap.get(j) == null)
//                System.out.println("found null "+j);
            cluster.add(nodeMap.get(j));
            clusterOfComNode.add(nodeMap.get(j));
        }

        comNodeCSet = new HashSet<Set>();
        for (int l = 0; l < csList.size(); l++) {
            /* only at this level we can use cluster instead of clusterOfComNode */
            Set<Object> cluster = csList.get(l);
            cSet.add(cluster);
            comNodeCSet.add(cluster);

            flatClustToComNodeClust.put(cluster, cluster);
            comNodeClustToFlatClust.put(cluster, cluster);
//            System.out.println(cluster);
        }

        cSetToComNodeCSet.put(cSet, comNodeCSet);
        flatClustToComNodeClust.put(cSet, comNodeCSet);

        if (isTopLevel) {
            hierCSet[0] = cSet;
            comNodeHierSet[0] = comNodeCSet;
//            System.out.println("\ncomNodehierSet "+0+": "+comNodehierSet[0]+"\n");
//            System.out.println("\nhierCSet "+0+": "+hierCSet[0]+"\n");
        }

        // process level i and i+1
        for (int i = 1; i < hierarchyComNode.length - 1; i++) {
            labelList.clear();
            newCsList.clear();
            newArrComNode.clear();
//            System.out.println("size "+(i)+" "+hierarchyComNode[i].length);
//            System.out.println("size "+(i+1)+" "+hierarchyComNode[i+1].length);
            for (int j = 0; j < hierarchyComNode[i + 1].length; j++) {
                newCsList.add(new HashSet<Object>());
                newArrComNode.add(new HashSet());
            }
//            System.out.println("size of csList "+csList.size());
//            System.out.println("size of new csList "+newCsList.size());
            for (int j = 0; j < hierarchyComNode[i].length; j++) {
                if (!labelList.contains(hierarchyComNode[i][j])) {
                    labelList.add(hierarchyComNode[i][j]);
                }

                int index = labelList.indexOf(hierarchyComNode[i][j]);
                Set<Object> cluster = newCsList.get(index);
                Set clusterOfComNode = newArrComNode.get(index);

                cluster.addAll(csList.get(j));
                clusterOfComNode.add(arrComNode.get(j));
            }

            //clone newCsList to csList safely
            csList.clear();
            for (Set<Object> av : newCsList) {
                Set<Object> list = new HashSet<Object>();
                csList.add(list);
                for (Object v : av) {
                    list.add(v);
                }
            }

            //clone newArrComNode to arrComNode safely
            arrComNode.clear();
            for (Set<Object> av : newArrComNode) {
                Set<Object> list = new HashSet<Object>();
                arrComNode.add(list);
                for (Object v : av) {
                    list.add(v);
                }
            }

            cSet = new HashSet<Set>();
            comNodeCSet = new HashSet<Set>();

            for (int l = 0; l < csList.size(); l++) {

                Set<Object> cluster = csList.get(l);
                Set clusterOfComNode = arrComNode.get(l);

                cSet.add(cluster);
                comNodeCSet.add(clusterOfComNode);

                flatClustToComNodeClust.put(cluster, clusterOfComNode);
                comNodeClustToFlatClust.put(clusterOfComNode, cluster);

//                System.out.println("ClusterOfComNode: "+clusterOfComNode);
//                System.out.println(cluster);
            }
            if ((cSet.size() > 0) && (comNodeCSet.size() > 0)) {
                cSetToComNodeCSet.put(cSet, comNodeCSet);
                flatClustToComNodeClust.put(cSet, comNodeCSet);
            }

            if (isTopLevel) {
                hierCSet[i] = cSet;
                comNodeHierSet[i] = comNodeCSet;

//                System.out.println("\ncomNodehierSet " + i + ": " + comNodeHierSet[i] + "\n");
//                System.out.println("\nhierCSet " + i + ": " + hierCSet[i] + "\n");
            }

        }
        ArrayList<Set<Set>> retList = new ArrayList<Set<Set>>();

        ////DEBUG////
//         printCSet(cSet);
//        printSortedCSet(cSet);
        /////////////
//        System.out.println("cset "+cSet);
//        System.out.println("comnode "+comNodeList);
        retList.add(cSet);
        retList.add(comNodeCSet);
        return retList;
    }

    //////DEBUG////
    public void printSortedCSet(Set<Set> cSet) {
        try {
            PrintWriter pw = new PrintWriter(new File("debug-navicluster-sortedCSet"));
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
    //////DEBUG////

    public void printCSet(Set<Set> cSet) {
        try {
            PrintWriter pw = new PrintWriter(new File("debug-navicluster-cSet"));
            for (Set set : cSet) {
                pw.println(set);
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Print statistics about louvain clusters
     * @param g
     * @param cSet
     */
    private void printStatAboutClusters(Graph g, Set<Set> cSet) {

        int numSingle = 0;
        int numClustSizeMore1 = 0;
        double avgClustSize = 0;
        int biggestClustSize = -1;
        int sumClustSize = 0;

        /* calculate some stats */

        for (Set<Object> set : cSet) {
            int size = set.size();
            if (size == 1) {
                numSingle++;
            } else {
                numClustSizeMore1++;
            }
            if (size > biggestClustSize) {
                biggestClustSize = size;
            }
            sumClustSize += size;
        }

        avgClustSize = (double) sumClustSize / cSet.size();
        double percSingle = (double) numSingle / g.getVertexCount();
        double percBiggest = (double) biggestClustSize / g.getVertexCount();

        pw.println("\n======================");
        pw.println("Statistics of louvain clusters");
        pw.println("======================");
        pw.println("Single node num: " + numSingle + " percent: " + percSingle);
        pw.println("No. cluster size > 1: " + numClustSizeMore1);
        pw.println("Avg. cluster size: " + avgClustSize);
        pw.println("Biggest cluster size: " + biggestClustSize + " percent: " + (percBiggest * 100));
        pw.println("======================\n");
        System.out.println("\n======================");
        System.out.println("Statistics of louvain clusters");
        System.out.println("======================");
        System.out.println("Single node num: " + numSingle + " percent: " + percSingle);
        System.out.println("No. cluster size > 1: " + numClustSizeMore1);
        System.out.println("Avg. cluster size: " + avgClustSize);
        System.out.println("Biggest cluster size: " + biggestClustSize + " percent: " + (percBiggest * 100));
        System.out.println("======================\n");
    }

    /**
     * Do property-based clustering by using the result from cSet and comNodeList
     * @param cSet
     * @param comNodeList
     * @param isTopLevel
     * @param doOnAllNodesInCanVas
     * @return
     */
    public Set<Set> propertyBasedCluster(Set<Set> cSet, Set<Set> comNodeCSet, PropInfoProcessor gop, boolean isTopLevel, boolean doOnAllNodesInCanVas, boolean mustRepopulatePropTerms) {

        if (doOnAllNodesInCanVas) {
            curCSetBeforePropClust = cSet;
            curComNodeCSetBeforePropClust = comNodeCSet;
        } else {
            /* for now we assume that if this method does not run on all nodes, it runs on peripheral nodes
             * of the node currently being centered and curCSetCentered et al. are used instead.
             */
            curCSetCentered = curCSetCenteredBPC = cSet;
            curComNodeCSetCentered = curComNodeCSetCenteredBPC = comNodeCSet;
        }

//        Set comNodeClust = splitClusters(cSet);
//        if (comNodeClust != null)
//            cSet = comNodeClust;

        PropInfoProcessor gp = null;
        /* if we provides PropInfoProcessor, use it */
        if (gop != null) {
            gp = gop;
            if (mustRepopulatePropTerms) {
                gp.populatePropTerms(cSet);
            }
        } else {
            gp = new PropInfoProcessor();
//        StopWatch sw = new StopWatch();
//        sw.start();
            gp.populatePropTerms(cSet);
//        sw.stop();
//        System.out.println("Populate GO Terms Time Used "+sw);
        }

//        int thresh = 12;
        if ((cSet.size() == 1) && (cSet.iterator().next().size() >= Integer.parseInt(thresholdTextField.getValue().toString()))) {
            /* In this case, cSet is composed of only BioObjects, so destroy it to get them */
            pw.println("\ncSet size = 1, destroy cSet to get raw BioObjs\n");
            pw.println("Start special property-based clustering!");
            System.out.println("\ncSet size = 1, destroy cSet to get raw BioObjs\n");
            System.out.println("Start special property-based clustering!");
            Set newset = new HashSet();
            newset.addAll(cSet.iterator().next());
            pw.println("New cSet which comprises BioObjects: " + newset);
            System.out.println("New cSet which comprises BioObjects: " + newset);

            gp.populatePropTerms(newset);
            semanticClusterList = gp.preCluster(newset, Integer.parseInt(thresholdTextField.getValue().toString()));
            cSet = transformDataClusterList(semanticClusterList, isTopLevel, doOnAllNodesInCanVas);

//            System.out.println("cSet after transform "+cSet);
            /*tentative*/
            if (doOnAllNodesInCanVas) {
                curCSetBeforePropClust = newset;
            } else {
                curCSetCenteredBPC = newset;
            }
            pw.println("End special property-based clustering!");
            System.out.println("End special property-based clustering!");

        } else if (cSet.size() >= Integer.parseInt(thresholdTextField.getValue().toString())) {
            pw.println("Start property-based clustering!");
            System.out.println("Start property-based clustering!");
            semanticClusterList = gp.preCluster(comNodeClustToFlatClust, comNodeCSet, cSet, Integer.parseInt(thresholdTextField.getValue().toString()));
            cSet = transformDataClusterList(semanticClusterList, isTopLevel, doOnAllNodesInCanVas);
            pw.println("End property-based clustering!");
            System.out.println("End property-based clustering!");

        } else {
            /* Do not run property-based clustering
             * split clusters to fill the canvas
             *
             */
            pw.println("Do not run property-based clustering!!");
            System.out.println("Do not run property-based clustering!!");

            Set tmpSet = new HashSet();
            int counter = 0;
            do {
                pw.println("\n===================");
                pw.println("split cluster round " + counter);
                pw.println("===================\n");
                System.out.println("\n===================");
                System.out.println("split cluster round " + counter);
                System.out.println("===================\n");
                splited = false;
                overallLessThanThresh = false;

                ArrayList<Set> pairOfClustSet = splitClusters(cSet);
                if (splited) {
                    cSet = pairOfClustSet.get(0);
                    if (doOnAllNodesInCanVas) {
                        curCSetBeforePropClust = cSet;
                        curCSet = cSet;
                        curComNodeCSetBeforePropClust = pairOfClustSet.get(1);
                        curComNodeCSet = pairOfClustSet.get(1);
                    } else {
                        curCSetCenteredBPC = cSet;
                        curCSetCentered = cSet;
                        curComNodeCSetCenteredBPC = pairOfClustSet.get(1);
                        curComNodeCSetCentered = pairOfClustSet.get(1);
                    }
                    pw.println("clusters splited!");
                    pw.println("cSet: " + cSet);
                    System.out.println("clusters splited!");
                    System.out.println("cSet: " + cSet);
                } else {
                    break;
                }
                if (cSet.size() >= Integer.parseInt(thresholdTextField.getValue().toString())) {
                    break;
                }
                if (overallLessThanThresh) {
                    break;
                }
//                if (comNodeClust != null) {
//                    if (comNodeClust == cSet)
//                        break;
//                    else{
//                        cSet = comNodeClust;
//                        System.out.println("clusters splited!");
//                    }
//                }
                counter++;
                if (counter == 10) {
                    break;
                }
            } while (true);

            gp.populatePropTerms(cSet);
            nodesPropVectorMap.putAll(putInNodesPropVectorMapRelatively(gp.getNodesPropVectorMapBeforeCluster(cSet)));

        }

        if (doOnAllNodesInCanVas) {
            curGOP = gp;
            pw.println("Prop Term Processor clusterScoreMap size: " + curGOP.clusterScoreMap.size());
            System.out.println("Prop Term Processor clusterScoreMap size: " + curGOP.clusterScoreMap.size());
        } else {
            /* if do not run on all nodes, assume that it runs on peripheral nodes */
            curGOPCentered = gp;
        }

        return cSet;
    }

    /**
     * splitClusters aims for splitting members of cSet as much as possible in order to
     * fill the canvas.
     * It works in greedy-based fashion, trying to split a member if the split does not produce
     * number of new members which exceeds the threshold.
     * If it cannot split the member, it will continue to the next members.
     *
     * The threshold used to split is that specified in the UI (default = 12)
     * ex.
     * If cSet comprises 3 members, which in turn contain 5,4, and 4 members respectively.
     * After splitClusters, cSet will contain 5 + 4 + 1 = 10 members.
     * The first 5 members are from the first previous member which contains 5 members.
     * The second 4 members are from the second previous member which contains 4 members.
     * The final 1 member is the same final previous member which contains 4 members.
     * This last member cannot be split because the threshold is 12.
     * If it is to split, the overall number will be = 5+4+4= 13 members, exceeding the threshold.
     *
     * Usually, splitCluster is called iteratively to fill the canvas
     * ex.
     * If there are 2 members in cSet, each of which contains 4 members.
     * After splitClusters, there will be 4+4 = 8 members on the canvas.
     * Assume the first member of these 8 members contains 3 members, and the second contains 2 members.
     * In this case, they can be splitted, and the overall number of nodes/clusters on the canvas
     * will be 3+2+3+4 = 12 members.
     *
     * @param cSet  a Set of clusters
     * @return ArrayList of flat set and com node set
     */
    public ArrayList<Set> splitClusters(Set<Set> cSet) {


        if (cSet.isEmpty()) {
            return null;
        }
        pw.println("split clusters: cSet size " + cSet.size());
        pw.println("split clusters: cSet " + cSet);
        System.out.println("split clusters: cSet size " + cSet.size());
        System.out.println("split clusters: cSet " + cSet);

        /* newCSet is a new flatClust for storing flat flatClust of clusters */
        Set<Set> newCSet = new HashSet<Set>();
        Set<Set> newComNodeSet = new HashSet<Set>();
        Object arrOfCSet[] = cSet.toArray(new Object[1]);
        Set setOfAllBioObj = new HashSet();

        int numOfAllMembers = 0;
        Integer arrOfSize[] = new Integer[arrOfCSet.length];
        ArrayList<Set> comNodeList = new ArrayList<Set>();

        Map<Set, Set> flatClustToComNodeClustMap = new HashMap<Set, Set>();
        Set<BioObject> bioObjSet = new HashSet<BioObject>();
//        int i = 0;
        for (int i = 0; i < arrOfCSet.length; i++) {
            Set flatClust = null;
            BioObject bioObj = null;
            if (arrOfCSet[i] instanceof Set) {
                flatClust = (Set) arrOfCSet[i];
                pw.println("i: " + i + " flatClust " + flatClust);
                System.out.println("i: " + i + " flatClust " + flatClust);
                numOfAllMembers += flatClust.size();
                setOfAllBioObj.addAll(flatClust);
                Set comNodeClust = flatClustToComNodeClust.get(flatClust);
                pw.println("comNodeClust size " + comNodeClust.size());
                pw.println("comNodeClust " + comNodeClust);
                System.out.println("comNodeClust size " + comNodeClust.size());
                System.out.println("comNodeClust " + comNodeClust);
                // Extract cluster of BioObjects from a nested set of clusters
                while ((comNodeClust.size() == 1) && (comNodeClust.iterator().next() instanceof Set)) {
                    comNodeClust = (Set) comNodeClust.iterator().next();
                    pw.println("current comNodeClust: " + comNodeClust);
                    System.out.println("current comNodeClust: " + comNodeClust);
                }
                pw.println("comNodeClust size after extracting process " + comNodeClust.size());
                System.out.println("comNodeClust size after extracting process " + comNodeClust.size());
                arrOfSize[i] = flatClust.size();
                comNodeList.add(comNodeClust);
                flatClustToComNodeClustMap.put(flatClust, comNodeClust);
            } else if (arrOfCSet[i] instanceof BioObject) {
                bioObj = (BioObject) arrOfCSet[i];
                bioObjSet.add(bioObj);
                pw.println("i: " + i + " BioObj " + bioObj);
                System.out.println("i: " + i + " BioObj " + bioObj);
                numOfAllMembers++;
                setOfAllBioObj.add(bioObj);
                arrOfSize[i] = 1;
            }

        }

        int threshold = Integer.parseInt(thresholdTextField.getValue().toString());
        /* If the numbers of members of all clusters are not greater than the threshold, then split all clusters to get BioObjects */
        if (numOfAllMembers <= threshold) {
            splited = true;
            overallLessThanThresh = true;
            pw.println("Overall Num less than threshold: setOfBioObj = " + setOfAllBioObj);
            System.out.println("Overall Num less than threshold: setOfBioObj = " + setOfAllBioObj);
            ArrayList<Set> pairOfClustSet = new ArrayList<Set>();
            // one for flat cluster set
            pairOfClustSet.add(setOfAllBioObj);
            // one for community cluster set
            pairOfClustSet.add(setOfAllBioObj);
            comNodeClustToFlatClust.put(setOfAllBioObj, setOfAllBioObj);
            flatClustToComNodeClust.put(setOfAllBioObj, setOfAllBioObj);
            return pairOfClustSet;
        }
        pw.println("In splitClusters: number of members of cSet " + cSet.size());
        System.out.println("In splitClusters: number of members of cSet " + cSet.size());
        Arrays.sort(arrOfSize, new Comparator<Integer>() {

            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) {
                    return 1;
                } else if (o1 == o2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        pw.println("array of size: " + Arrays.toString(arrOfSize));
        System.out.println("array of size: " + Arrays.toString(arrOfSize));
        Set sortedSet = new TreeSet(new Comparator<Entry<Set, Set>>() {

            public int compare(Entry<Set, Set> o1, Entry<Set, Set> o2) {
                if (o1.getKey().size() <= o2.getKey().size()) {
                    return 1;
//                } else if (o1.getKey().size() == o2.getKey().size()) {
//                    return 0;
                } else {
                    return -1;
                }
//                return 0;
            }
        });

        sortedSet.addAll(flatClustToComNodeClustMap.entrySet());

//        System.out.println("tm key flatClust size "+tm.size());
        pw.println("flatClustToComNodeClustMap size " + flatClustToComNodeClustMap.keySet().size());
        pw.println("sortedset size " + sortedSet.size());
        System.out.println("flatClustToComNodeClustMap size " + flatClustToComNodeClustMap.keySet().size());
        System.out.println("sortedset size " + sortedSet.size());

        for (Object obj : sortedSet) {
            Entry entry = (Entry) obj;
            pw.println("key " + entry.getKey() + " object " + entry.getValue());
            System.out.println("key " + entry.getKey() + " object " + entry.getValue());
        }
//            newComNodeSet = new HashSet();
        int currentMemSize = cSet.size();

        if (!bioObjSet.isEmpty()) {
            System.out.println("Adding bioObjSet " + bioObjSet);
            for (BioObject bioObj : bioObjSet) {
                Set tmpSet = new HashSet();
                tmpSet.add(bioObj);
                newCSet.add(tmpSet);
            }
//            newCSet.add(bioObjSet);
        }

        int j = 0;
        for (Object obj : sortedSet) {
            Entry<Set, Set> entry = (Entry) obj;
            pw.println("j " + j);
            System.out.println("j " + j);

            /*
             * I am not sure that below if is for what????
             * So I commented it out because it made the flatClust of one BioObject disappear from newCSet.
             */
//            if (arrOfSize[j] <= 1) {
//                continue;
//            }
            if (currentMemSize - 1 + entry.getValue().size() <= threshold) {
                splited = true;
                for (Object obje : entry.getValue()) {
                    pw.println("obje " + obje);
                    System.out.println("obje " + obje);

                    if (obje instanceof BioObject) {
                        Set set = new HashSet();
                        set.add(obje);
                        comNodeClustToFlatClust.put(set, set);
                        flatClustToComNodeClust.put(set, set);
                        newCSet.add(set);
                        newComNodeSet.add(set);
//                        newCSet.add(obje);
                    } else {
                        pw.println("comnodeclust flatclust: " + comNodeClustToFlatClust.get(obje));
                        System.out.println("comnodeclust flatclust: " + comNodeClustToFlatClust.get(obje));
                        newCSet.add(comNodeClustToFlatClust.get(obje));
                        newComNodeSet.add((Set) obje);
                    }

                }
//                newCSet.addAll(entry.getKey());
                currentMemSize = currentMemSize - 1 + entry.getValue().size();
                pw.println("arrOfSize j " + entry.getValue().size());
                pw.println("currentMemSize " + currentMemSize);
                System.out.println("arrOfSize j " + entry.getValue().size());
                System.out.println("currentMemSize " + currentMemSize);

            } else {
                // Assume there is no mixed flatClust of BioObject & flatClust
                Set val = entry.getKey();
                if (val == null) {
                    pw.println("flat clust is null");
                    System.out.println("flat clust is null");
                }
                /* Below if-condition seems no meaning because it should be the characteristic of all entry.getKey() (flat cluster) */
                if ((val.iterator().next() instanceof BioObject)) {
                    pw.println("flat clust set of bioobject: " + val);
                    System.out.println("flat clust set of bioobject: " + val);
                    newCSet.add(val);
                    newComNodeSet.add(entry.getValue());
//                    comNodeClustToFlatClust.put(entry.getValue(), val);
//                    flatClustToComNodeClust.put(val, entry.getValue());
                    comNodeClustToFlatClust.put(val, val);
                    flatClustToComNodeClust.put(val, val);
                }

            }
            j++;
        }

        ArrayList<Set> pairOfClustSet = new ArrayList<Set>();
        pairOfClustSet.add(newCSet);
        pairOfClustSet.add(newComNodeSet);
        return pairOfClustSet;
    }

    /**
     * Property vector values are subtracted by the local centroid of all members.
     * to bring out the property of each member compared to all of them.
     * 
     * @return map of object to property vector
     * @param localNodesPropVectorMap
     */
    public Map<Object, DataVector> putInNodesPropVectorMapRelatively(Map<Object, DataVector> localNodesPropVectorMap) {
        int allNumMember = 0;
        Map<Object, DataVector> newNodesPropVectorMap = new HashMap<Object, DataVector>();

        DataCluster dc = new DataCluster();
        dc.addAll(localNodesPropVectorMap.values());
        allNumMember = dc.getMembers().size();
//        for (DataVector dv: dc.getMembers()){
//            allNumMember += ((Set)dv.nodeRef).size();
//        }
//        System.out.println("putInNodesGOVectorMap all num member "+allNumMember);
        DataVector localCentroid = DataCluster.findCentroidFromDCList(new DataCluster[]{dc}, allNumMember);
//        System.out.println("centroid "+localCentroid);
        for (DataVector dv : dc.getMembers()) {
//            DataVector resultVector = DataVector.minusVector(dv, localCentroid);
//            System.out.println("node ref "+dv.nodeRef+" dv" +dv);
            DataVector resultVector = DataVector.modMinusVector(dv, localCentroid);
//            System.out.println("result "+resultVector);
            newNodesPropVectorMap.put(dv.nodeRef, resultVector);
        }
        ///DEBUG///
//        printStringDoubleMap(localCentroid.getValueMap());
        return newNodesPropVectorMap;
    }

    ////DEBUG////
//    public void printStringDoubleMap(Map<String, Double> map) {
//        try {
//            PrintWriter pw = new PrintWriter(new File("debug-navicluster-stdbmap"));
//            pw.println(map.size());
//            Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {
//
//                @Override
//                public int compare(Object o1, Object o2) {
//                    Entry<String, Double> e1 = (Entry<String, Double>) o1;
//                    Entry<String, Double> e2 = (Entry<String, Double>) o2;
//                    if (e1.getValue() > e2.getValue()) {
//                        return -1;
//                        // 2010/04/18 Takao Asanuma: "<" -> "<="
//                    } else if (e1.getValue() <= e2.getValue()) {
//                        return 1;
//                    } else {
//                        return e1.getKey().compareTo(e2.getKey());
//                    }
//// 2010/04/18 Takao Asanuma: "<" -> "<="
//// 				} else {
//// 					return 0;
//// 				}
////                    return 0;
//                }
//            });
//            sortedMap.addAll(map.entrySet());
//            for (Entry entry : sortedMap) {
//                pw.println(entry.getKey() + "\t" + entry.getValue());
//            }
//            pw.close();
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    /**
     * Transform the result from data cluster list to clusters really used in the graph.
     * @param dcList
     * @param topLevel
     * @param doOnAllNodesInCanvas
     * @return cSet containing new clusters
     */
    public Set<Set> transformDataClusterList(DataCluster[] dcList, boolean topLevel, boolean doOnAllNodesInCanvas) {
        Set<Set> cSet = new HashSet<Set>();
        Set<Set> comNodeCSet = new HashSet<Set>();
        Set cluster;
        Set clusterComNode;

        pw.println("Start transform data cluster to real clusters...");
        System.out.println("Start transform data cluster to real clusters...");

        int sumOfAllMembers = 0;
        for (DataCluster dc : dcList) {
            sumOfAllMembers += dc.getMembers().size();
        }

//        System.out.println("sum of all: "+sumOfAllMembers);
//        System.out.println("num of louvain "+numOfLouvainClusters);

        centroid = DataCluster.findCentroidFromDCList(dcList, sumOfAllMembers);

        for (int i = 0; i < dcList.length; i++) {
            DataCluster dc = dcList[i];

            if (dc.getMembers().isEmpty()) {
                pw.println("BUG!!!!: Found cluster with zero members !!!!!!");
                System.out.println("BUG!!!!: Found cluster with zero members !!!!!!");
                continue;
            }

            DataVector[] dvList = dc.getMembers().toArray(new DataVector[1]);
            cluster = new HashSet();
            clusterComNode = new HashSet();

            for (DataVector dv : dvList) {
                //nodeRef points to a cluster (flatClust of flatClust) not a real cluster in the graph
//                System.out.println("node ref "+dv.nodeRef+" dv" +dv);
                if (dv.nodeRef instanceof Set) {
                    cluster.addAll((Set) dv.nodeRef);
                } else {// nodeRef is BioObject
                    cluster.add(dv.nodeRef);
                }
                clusterComNode.add(dv.comNodeClusterRef);
            }

//            nodeToDataCluster.put(cluster, dc);
            flatClustToComNodeClust.put(cluster, clusterComNode);
            comNodeClustToFlatClust.put(clusterComNode, cluster);
            cSet.add(cluster);
            comNodeCSet.add(clusterComNode);

//            DataVector resultVector = DataVector.minusVector(dc.getRepresentative(), centroid);
//            System.out.println("centroid "+centroid);
            DataVector resultVector = DataVector.modMinusVector(dc.getRepresentative(), centroid);
//            System.out.println("result "+resultVector);
            nodesPropVectorMap.put(cluster, resultVector);

        }

        /* Input new information of hierCSet if this method is performed at the topLevel */
        if (topLevel) {
            //extend capacity and copy the contents for hierCSet and ComNodeHierSet
            Set<Set>[] tmpHierCSet = new HashSet[hierCSet.length + 1];
            for (int i = 0; i < hierCSet.length; i++) {
                tmpHierCSet[i] = hierCSet[i];
            }
            tmpHierCSet[hierCSet.length - 1] = cSet;
            hierCSet = tmpHierCSet;

            tmpHierCSet = new HashSet[comNodeHierSet.length + 1];
            for (int i = 0; i < comNodeHierSet.length; i++) {
                tmpHierCSet[i] = comNodeHierSet[i];
            }
            tmpHierCSet[comNodeHierSet.length - 1] = comNodeCSet;
            comNodeHierSet = tmpHierCSet;
//            System.out.println("\ncomNodehierSet " + (comNodeHierSet.length-2) + ": " + comNodeHierSet[comNodeHierSet.length-2] + "\n");
//            System.out.println("\nhierCSet " + (hierCSet.length-2) + ": " + hierCSet[hierCSet.length-2] + "\n");
        }

        /* do it on all nodes or else just do it on peripheral nodes of node being concentrated. */
        if (doOnAllNodesInCanvas) {
            curComNodeCSet = comNodeCSet;
            curCSet = cSet;
        } else {
            curComNodeCSetCentered = comNodeCSet;
            curCSetCentered = cSet;
        }

        cSetToComNodeCSet.put(cSet, comNodeCSet);
        flatClustToComNodeClust.put(cSet, comNodeCSet);

//        for (Set flatClust : cSet){
//            System.out.println("cluster with size "+flatClust.size());
//        }

        pw.println("End transform data cluster to real clusters...");
        System.out.println("End transform data cluster to real clusters...");

        return cSet;

    }

    /**
     * Create a graph from cSet
     * @param cSet
     * @return
     */
    public Graph createGraph(Set cSet) {
        //allNodesInCSet is used for updating the statur bar.
        int allNodesInCSet = 0;
        minNumOfMem = Integer.MAX_VALUE;
        maxNumOfMem = 0;
        minMetaEdgeWeight = Double.MAX_VALUE;
        maxMetaEdgeWeight = 0;

        Graph<Object, Object> newGraph = new SparseMultigraph<Object, Object>();
        nodeToClusterMap = new HashMap<BioObject, Object>();
        Map<Pair, MetaEdge> newEdgeMap = new HashMap<Pair, MetaEdge>();
        Set newSet = new HashSet(cSet);
        StopWatch sw = new StopWatch();
        sw.start();
        for (Object obj : cSet) {
            if (obj instanceof Set) {
                Set set = (Set) obj;
                if (set.size() == 1) {
                    Object suspect = set.iterator().next();
                    /* Destroy a flatClust of one BioObject and get its content */
                    if (suspect instanceof BioObject) {
//                    System.out.println("suspect detected "+suspect);
                        newGraph.addVertex(suspect);
                        newSet.add(suspect);
                        newSet.remove(obj);
                        nodesPropVectorMap.put(suspect, nodesPropVectorMap.get(obj));
                        nodesPropVectorMap.remove(obj);
                        allNodesInCSet++;
                        continue;
                    }
                } else {
                    if (zoomInButton != null) {
                        zoomInButton.setEnabled(true);
                    }
                }
                if (minNumOfMem >= set.size()) {
                    minNumOfMem = set.size();
                }
                if (maxNumOfMem < set.size()) {
                    maxNumOfMem = set.size();
                }
                allNodesInCSet += set.size();
            } else {
                allNodesInCSet++;
            }
            newGraph.addVertex(obj);
        }
        if (maxNumOfMem == minNumOfMem) {
            maxNumOfMem++;
        }
        sw.stop();
        pw.println("Time to create vertices " + sw);
        System.out.println("Time to create vertices " + sw);

        /* calculate property edge between cluster */
        sw.start();
        Object[] cSetArr = newSet.toArray(new Object[1]);
//        int []numEdgeArr = new int[cSetArr.length*2];
//        Object[] metaedgeArr = new Object[numEdgeArr.length];
        ArrayList<MetaEdge> metaedgeArr = new ArrayList();
        ArrayList<Pair> pairArr = new ArrayList();
//        int ind = 0;
        for (int i = 0; i < cSetArr.length; i++) {
            for (int j = i + 1; j < cSetArr.length; j++) {
                if (cSetArr[i] instanceof BioObject && cSetArr[j] instanceof BioObject) {
                    Object suspectedEdge = realGraph.findEdge(cSetArr[i], cSetArr[j]);
                    if (suspectedEdge != null) {
                        newGraph.addEdge(suspectedEdge, cSetArr[i], cSetArr[j]);
                    }
                } else {
                    Pair newPair = new Pair(cSetArr[i], cSetArr[j]);
                    MetaEdge metaedge = new MetaEdge(cSetArr[i] + ":" + cSetArr[j]);
                    metaedgeArr.add(metaedge);
                    pairArr.add(newPair);
                }
//                numEdgeArr[ind++] = 0;

//                Object edge = newGraph.findEdge(cSetArr[i], cSetArr[j]);

//                if (edge == null){
                Object edge = new PropertyEdge(cSetArr[i] + "<->" + cSetArr[j], nodesPropVectorMap.get(cSetArr[i]), nodesPropVectorMap.get(cSetArr[j]));
                newGraph.addEdge(edge, cSetArr[i], cSetArr[j]);
//                newGraph.addEdge(metaedge, cSetArr[i], cSetArr[j]);

//                }
//                else if (!(edge instanceof PropertyEdge))
//                    System.out.println("What 's edge!!!");

            }
            if (cSetArr[i] instanceof Set) {
                for (Object obj : (Set) cSetArr[i]) {
                    nodeToClusterMap.put((BioObject) obj, cSetArr[i]);
                }
            } else {
                nodeToClusterMap.put((BioObject) cSetArr[i], cSetArr[i]);
            }
        }
        sw.stop();
        pw.println("Time to create property edges " + sw);
        System.out.println("Time to create property edges " + sw);


        /* Create metaedges between clusters */
        sw.start();

//        StopWatch swlocal = new StopWatch();
//        swlocal.start();
        for (Object edge : originalGraph.getEdges()) {

            Pair pair = originalGraph.getEndpoints(edge);
            Object preClusterFirst = nodeToClusterMap.get(pair.getFirst());
            Object preClusterSecond = nodeToClusterMap.get(pair.getSecond());

            if (preClusterFirst == preClusterSecond
                    || preClusterFirst == null || preClusterSecond == null) {
                continue;
            }
            if (preClusterFirst instanceof BioObject && preClusterSecond instanceof BioObject) {
                continue;
            }

            Pair newPair = new Pair(preClusterFirst, preClusterSecond);

            /* if no such metaedge, create an edge, or else just add new edge into existing metaedge */

            int index = pairArr.indexOf(newPair);
            if (index == -1) {
                newPair = new Pair(preClusterSecond, preClusterFirst);
                index = pairArr.indexOf(newPair);


            }

            ((MetaEdge) metaedgeArr.get(index)).addEdge(edge);

        }
        sw.stop();
        pw.println("Time to create metaedges " + sw);
        System.out.println("Time to create metaedges " + sw);

        /* Add metaedges into graph */
        sw.start();

//        int allEdgesInThisView = 0;
        // maxMetaEdgeWeight is used in calculation of replusion and attraction forces in
        // FRLayout and other layout algorithms
        for (int k = 0; k < metaedgeArr.size(); k++) {
            int numEdgeBundled = ((MetaEdge) metaedgeArr.get(k)).getNumEdgeSetBundled();
            double weightedNumEdges = ((MetaEdge) metaedgeArr.get(k)).getWeightedNumEdges();
            if (numEdgeBundled > 0) {
//                if (numEdgeBundled > maxMetaEdgeWeight) {
//                    maxMetaEdgeWeight = numEdgeBundled;
//                }
                if (weightedNumEdges > maxMetaEdgeWeight) {
                    maxMetaEdgeWeight = weightedNumEdges;
                }
                if (weightedNumEdges <= minMetaEdgeWeight) {
                    minMetaEdgeWeight = weightedNumEdges;
                }
                newGraph.addEdge(metaedgeArr.get(k), pairArr.get(k).getFirst(), pairArr.get(k).getSecond());
//                allEdgesInThisView += numEdgeBundled;
            }

        }
        if (maxMetaEdgeWeight == minMetaEdgeWeight) {
            maxMetaEdgeWeight += 1;
        }
        sw.stop();
        pw.println("Time to add metaedges to the graph " + sw);
        System.out.println("Time to add metaedges to the graph " + sw);
        statusBar.addCurrentStatToText(allNodesInCSet);
        return newGraph;
    }


    /* Initialize components zone */
    /**
     * Initialize components in the GUI.
     */
    private void initComponents() {
        Container content = getContentPane();

//        content.add(new GraphZoomScrollPane(vv));
        this.setMinimumSize(new Dimension(westPanelSize + eastPanelSize, canvasSizeHeight));
//        this.setPreferredSize(new Dimension(westPanelSize + eastPanelSize+canvasSizeWidth, canvasSizeHeight));
        westPanel = initCompWest();
        eastPanel = initCompEast();
        center = new GraphZoomScrollPane(vv);

        // 15 stands for scroll bar's width
        center.setMinimumSize(new Dimension(canvasSizeWidth + 15, canvasSizeHeight));
        eastPanel.setMinimumSize(new Dimension(0, 0));

        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center, eastPanel);
        jsp.setDividerLocation(jsp.getSize().width - jsp.getInsets().right - jsp.getDividerSize() - eastPanelSize);
        jsp.setOneTouchExpandable(true);
//        jsp.setResizeWeight(0);

        jsp.setMinimumSize(new Dimension(canvasSizeWidth + 15 + jsp.getInsets().left + jsp.getInsets().right + jsp.getDividerSize() + eastPanelSize, canvasSizeHeight));
        westPanel.setMinimumSize(new Dimension(0, 0));

        JSplitPane jspLarge = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westPanel, jsp);
        jspLarge.setDividerLocation(westPanelSize + jspLarge.getInsets().left);
        jspLarge.setOneTouchExpandable(true);

        content.add(jspLarge);
//        content.add(jsp);

//        content.add(eastPanel, BorderLayout.EAST);
//        content.add(westPanel, BorderLayout.WEST);
//        statusBar = new StatusBar();
        content.add(statusBar, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    public class StatusBar extends JLabel {

        private String currentText = "";
        private String coreStatText = "";

        /** Creates a new instance of StatusBar */
        public StatusBar() {
            super();
            super.setPreferredSize(new Dimension(190 + 200 + 650, 16));
            setMessage("Ready!");
        }

        public void setMessage(String message) {
            currentText = message;
            setText("<html> " + currentText + "</html>");
//            revalidate();
        }

        public void addMessage(String message) {
            currentText += message;
            setMessage(currentText);
//            revalidate();
//            this.getParent().validate();
        }

        public void finalizeCoreText() {
            coreStatText = currentText;
        }

        public void addCurrentStatToText(int numNodes) {
            setMessage(coreStatText + " #Nodes in current view: <b>" + numNodes + "</b>");


        }
    }

    /**
     * Initialize components of the eastPanel part.
     * @return Panel of eastPanel part
     */
    public JPanel initCompWest() {
        JPanel west = new JPanel();
        west.setPreferredSize(new Dimension(westPanelSize, canvasSizeHeight));

        JButton openFile = new JButton("Load Network");
        openFile.setToolTipText("Open a new network file");
        openFile.addActionListener(new ActionListener() {

            JProgressBar progressBar;

            public void loadNetwork(ImportFileForm form) {
                try {

                    minBioEdgeWeight = Double.MAX_VALUE;
                    maxBioEdgeWeight = 0;

//                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileToOpen)));

                    StopWatch loadingTime = new StopWatch();
                    loadingTime.start();

                    realGraph = form.getNetwork();
//                        for (int i = 0; i < sArr.length; i++){
//                            if (sArr[i] != form.getsArr()[i])
//                                System.out.println("i "+i+" sarr "+sArr[i]+" "+form.getsArr()[i]);
//                            if (dArr[i] != form.getdArr()[i])
//                                System.out.println("i "+i+" darr "+dArr[i]+" "+form.getdArr()[i]);
//                            if (wArr[i] != form.getwArr()[i])
//                                System.out.println("i "+i+" warr "+wArr[i]+" "+form.getwArr()[i]);
//                        }
                    sArr = form.getsArr();
                    dArr = form.getdArr();
                    wArr = form.getwArr();
                    nodeMap.clear();
                    nodeMap.putAll(form.getIDToNodeMap());
                    minBioEdgeWeight = form.getMinBioEdgeWeight();
                    maxBioEdgeWeight = form.getMaxBioEdgeWeight();
                    isWeightGraph = true;
                    disconNodeSet.clear();
                    disconNodeSet.addAll(form.getDisconNodeSet());
//                        realGraph = loadModNet(br);

                    //////////////specialTest
//                        HashSet<BioEdge> edgeset = new HashSet<BioEdge>();
//                        for (Object edge : realGraph.getEdges()){
//                            BioEdge bioedge = (BioEdge)edge;
//                            edgeset.add(bioedge);
//                        }
//                        int counter = 0;
//
//                        for (Object edge : originalGraph.getEdges()){
//                            BioEdge bioedge = (BioEdge)edge;
//                            if (!edgeset.contains(bioedge)){
//                                counter++;
////                                System.out.println("bioedge "+bioedge);
//                                System.out.println(bioedge.node1+" "+bioedge.node2+" "+bioedge.weight);
//                            } else {
////                                System.out.println(bioedge.node1+" "+bioedge.node2+" "+bioedge.weight);
//                            }
//                        }
//                        System.out.println("counter "+counter);


                    originalGraph = realGraph;
                    dynamicGraph = realGraph;
//                        currentLevelGraph = dynamicGraph;
                    nodesPropVectorMap.clear();


                    loadingTime.stop();
                    pw.println("Time used for loading from input file: " + loadingTime);
                    System.out.println("Time used for loading from input file: " + loadingTime);
                    statusBar.setMessage("Network Files: " + form.getNodeFile().getName() + ", " + form.getEdgeFile().getName() + " (<b>" + originalGraph.getVertexCount() + "</b> nodes <b>" + originalGraph.getEdgeCount() + "</b> edges).");
                    statusBar.finalizeCoreText();

//                        louvainCluster(realGraph, realGraph.getVertexCount(),true);

                    ArrayList retList = louvainCluster(realGraph.getVertexCount(), true);
                    preparePropertyInfoProcessor(propInfoFile);
                    numOfLouvainClusters = ((Set) retList.get(0)).size();
                    Set<Set> cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, true, true, true);
                    /* if do on all nodes in canvas, visualize the result also, or else just do clustering */
                    dynamicGraph = createGraph(cSet);
                    usualNodeToClusterMap = new HashMap(nodeToClusterMap);

                    layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
                    ((CircleLayout) layout.getDelegate()).setRadius(0.40 * outerRadius);
//                        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 1080);
                    vv.getPickedVertexState().clear();
                    vv.setGraphLayout(layout);
//                        vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction(ew));
                    vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction());
//                        vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabeller(ew));
                    vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabeller());

                    layout = layoutGraph(layout, dynamicGraph);

//                        visualizeResults(cSet);
                    colorCluster(cSet, mainColor);
                    currentLevelGraph = dynamicGraph;
                    vv.repaint();

                    curVertexLocMap = new HashMap<Object, Point2D>();
                    for (Object ver : layout.getGraph().getVertices()) {
                        curVertexLocMap.put(ver, layout.transform(ver));
                    }
                    curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);
//                        vv.setGraphLayout(layout);
//                        vv.getRenderContext().getParallelEdgeIndexFunction().reset();

                    currentLevel = 0;
//                        zoomInButton.setEnabled(true);
                    zoomOutButton.setEnabled(false);
                    backButton.setEnabled(true);
                    forwardButton.setEnabled(false);
                    clearState();
                    saveState();
                } catch (IOException ex) {
                    Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public void actionPerformed(ActionEvent arg0) {

                inRecenteredMode = false;
                File tempFile = fileToOpen;

//                JFileChooser fileChooser = new JFileChooser(fileToOpen);
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//                fileChooser.setFileFilter(new MNetFileFilter());
////                    fileChooser.setFileHidingEnabled(false);
//                fileChooser.showOpenDialog(NaviClusterApp.this);
//                fileToOpen = fileChooser.getSelectedFile();

                final ImportFileForm form = ImportFileForm.getInstance(NaviClusterApp.this);
//                ImportFileForm form = new ImportFileForm(NaviClusterApp.this);
//                System.out.println("form location "+form.getLocation());
                form.reSetNewLocation();
                form.setVisible(true);
//                System.out.println("form location "+form.getLocation());

                if (form.getNetwork() != null) {
//                if (fileToOpen != null) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    eastPanel.setEnabled(false);
                    westPanel.setEnabled(false);
                    center.setEnabled(false);

                    final JDialog jd = new JDialog(NaviClusterApp.this, "Please wait...", false);

                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(new JLabel("Loading...", JLabel.CENTER));
//                panel.add(closePanel, BorderLayout.PAGE_END);

                    progressBar = new JProgressBar(0, 100);
                    progressBar.setValue(0);

                    //Call setStringPainted now so that the progress bar height
                    //stays the same whether or not the string is shown.
                    progressBar.setStringPainted(true);
                    progressBar.setIndeterminate(true);

                    JPanel progressPanel = new JPanel();
                    progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.LINE_AXIS));
                    progressPanel.add(Box.createHorizontalGlue());
                    progressPanel.add(progressBar);
                    progressPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    panel.add(progressBar, BorderLayout.PAGE_END);

                    panel.setOpaque(true);
                    jd.setContentPane(panel);

//                final JOptionPane optionPane = new JOptionPane("Re-clustering in Progress.",JOptionPane.INFORMATION_MESSAGE);
//                jd.setContentPane(optionPane);
//                jd.pack();
                    jd.setSize(new Dimension(300, 150));
                    jd.setLocationRelativeTo(NaviClusterApp.this);
                    jd.setVisible(true);
                    vv.setVisible(false);

                    //Background task for loading images.
                    SwingWorker worker = new SwingWorker<Void, Void>() {

                        @Override
                        public Void doInBackground() {
                            loadNetwork(form);
                            return null;
                        }

                        @Override
                        public void done() {
//                        jd.setVisible(false);
                            jd.dispose();
                            setCursor(null);
                            eastPanel.setEnabled(true);
                            westPanel.setEnabled(true);
                            center.setEnabled(true);
                            vv.setVisible(true);
//                        try {
//
//                        } catch (InterruptedException ignore) {
//                        } catch (java.util.concurrent.ExecutionException e) {
//                            String why = null;
//                            Throwable cause = e.getCause();
//                            if (cause != null) {
//                                why = cause.getMessage();
//                            } else {
//                                why = e.getMessage();
//                            }
//                            System.err.println("Error retrieving file: " + why);
//                        }
                        }
                    };

                    worker.execute();

                } else {
                    fileToOpen = tempFile;
                }
            }
        });


        JButton saveButton = new JButton("Save As...");
        saveButton.setToolTipText("Save the current graph view as JPG/PNG/PS/EPS/SVG formats");
        saveButton.addActionListener(new ActionListener() {
            /*
             * Get the extension of a file.
             */

            public String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                if (ext == null) {
                    ext = "";
                }
                return ext;
            }

            public void actionPerformed(ActionEvent e) {
                File imageFolder = new File("images");
                if (!imageFolder.exists()) {
                    imageFolder.mkdir();
                }
                JFileChooser chooser = new JFileChooser(imageFolder);
//                JFileChooser chooser = new JFileChooser();
                chooser.setAcceptAllFileFilterUsed(false);
                FileFilter jpgFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
                FileFilter pngFilter = new FileNameExtensionFilter("PNG file", "png");
                FileFilter psFilter = new FileNameExtensionFilter("PS file", "ps");
                FileFilter epsFilter = new FileNameExtensionFilter("EPS file", "eps");
                FileFilter svgFilter = new FileNameExtensionFilter("SVG file", "svg");

                chooser.setDialogTitle("Save the current graph view as JPG/PNG/PS/EPS formats");
                chooser.addChoosableFileFilter(epsFilter);
                chooser.addChoosableFileFilter(jpgFilter);
                chooser.addChoosableFileFilter(pngFilter);
                chooser.addChoosableFileFilter(psFilter);
                chooser.addChoosableFileFilter(svgFilter);
                chooser.setFileFilter(epsFilter);

                int option = chooser.showSaveDialog(NaviClusterApp.this);

                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();

                    try {
                        if (getExtension(file).equals("jpg") || getExtension(file).equals("jpeg")) {
                            generateJPG(file);
                        } else if (getExtension(file).equals("ps")) {
                            generatePS(file);
                        } else if (getExtension(file).equals("eps")) {
                            generateEPS(file);
                        } else if (getExtension(file).equals("png")) {
                            generatePNG(file);
                        } else if (getExtension(file).equals("svg")) {
                            generateSVG(file);
                        } else {
                            FileNameExtensionFilter filter = (FileNameExtensionFilter) chooser.getFileFilter();
                            String ext = filter.getExtensions()[0];
                            file = new File(file.getAbsolutePath() + "." + ext);
                            if ("eps".equals(ext)) {
                                generateEPS(file);
                            } else if ("jpg".equals(ext)) {
                                generateJPG(file);
                            } else if ("png".equals(ext)) {
                                generatePNG(file);
                            } else if ("ps".equals(ext)) {
                                generatePS(file);
                            } else if ("svg".equals(ext)) {
                                generateSVG(file);
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }

            private void generateJPG(File outputFile) {
                int width = vv.getWidth();
                int height = vv.getHeight();
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = bi.createGraphics();
                vv.paint(graphics);
                graphics.dispose();

                try {
                    ImageIO.write(bi, "jpeg", outputFile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            /**
             * Creates a PostScript file. The contents are painted using a Graphics2D implementation.
             * @param outputFile the target file
             * @throws IOException In case of an I/O error
             */
            private void generatePS(File outputFile) throws IOException {
                OutputStream out = new java.io.FileOutputStream(outputFile);
                out = new java.io.BufferedOutputStream(out);
                try {
                    //Instantiate the PSDocumentGraphics2D instance
//                    PSDocumentGraphics2D g2d = new PSDocumentGraphics2D(false);
                    EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);

                    g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
                    //Set up the document size
                    g2d.setupDocument(out, vv.getWidth(), vv.getHeight());
                    vv.paint(g2d);
                    //Cleanup
                    g2d.finish();
                } finally {
                    IOUtils.closeQuietly(out);
                }
            }

            /**
             * Creates a Encapsulated PostScript file. The contents are painted using a Graphics2D implementation.
             * @param outputFile the target file
             * @throws IOException In case of an I/O error
             */
            private void generateEPS(File outputFile) throws IOException {
                OutputStream out = new java.io.FileOutputStream(outputFile);
                out = new java.io.BufferedOutputStream(out);
                try {
                    //Instantiate the PSDocumentGraphics2D instance
//                    PSDocumentGraphics2D g2d = new PSDocumentGraphics2D(false);
                    EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);

                    g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
                    //Set up the document size
                    g2d.setupDocument(out, vv.getWidth(), vv.getHeight());
                    vv.paint(g2d);
                    //Cleanup
                    g2d.finish();
                } finally {
                    IOUtils.closeQuietly(out);
                }
            }

            /**
             * Creates a PNG file. The contents are painted using a Graphics2D implementation.
             * @param outputFile    the target file
             */
            private void generatePNG(File outputFile) throws IOException {
                BufferedImage image = new BufferedImage(vv.getWidth(), vv.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                vv.paint(g2d);
                g2d.dispose();
                ImageWriterUtil.saveAsPNG(image, outputFile);
            }

            /**
             * Creates a SVG file. The contents are painted using a Graphics2D implementation.
             * @param outputFile    the target file
             */
            private void generateSVG(File outputFile) throws IOException {
                DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
                String svgNS = "http://www.w3.org/2000/svg";
                Document document = domImpl.createDocument(svgNS, "svg", null);
                SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);
                ctx.setEmbeddedFontsOn(true);

                SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx, true);
                vv.paint(svgGenerator);

                // Finally, stream out SVG to the standard output using
                // UTF-8 encoding.
                boolean useCSS = true; // we want to use CSS style attributes
                Writer out = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
                svgGenerator.stream(out, useCSS);

            }
        });


//        JPanel panel1 = new JPanel(new GridLayout(2, 1));
//        JPanel panel1 = new JPanel(new BorderLayout());
//        grid2.setPreferredSize(new Dimension(178,500));

        JPanel grid = new JPanel(new GridLayout(1, 2));
//        JPanel grid = new JPanel();
        grid.setPreferredSize(new Dimension(175, 33));
        grid.add(openFile);
        grid.add(saveButton);
//        eastPanel.add(grid);

        //        p.add(rewindButton);
//        grid2.add(rewindButton);
//        panel1.add(grid, BorderLayout.NORTH);
//        grid2.add(panelWrapper);

        rewindButton = new JButton("<<");
        rewindButton.setToolTipText("Back to initial graph after loading");
        rewindButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

//                zoomInButton.setEnabled(true);
//                forwardButton.setEnabled(true);
                backButton.setEnabled(false);
                if (viewHistoryList.size() > 1) {
                    forwardButton.setEnabled(true);
                }

//                final ViewHistory vh = viewHistoryList.remove(viewHistoryList.size() - 1);
                final ViewHistory vh = viewHistoryList.get(0);
                currentViewHisPos = 0;

                /* retrieve system information from viewhistory */

                nodeToClusterMap = vh.getNodeToClusterMap();
                usualNodeToClusterMap = vh.getUsualNodeToClusterMap();
                dynamicGraph = vh.getStoredGraph();
                PropInfoProcessor gp = vh.getPropInfoProc();
                curCSetBeforePropClust = vh.getCSetBeforeVis();
                curComNodeCSetBeforePropClust = vh.getComNodeCSetBeforeVis();
                highlightedNode = vh.getHighlightedNode();
                highlightedNodeFormerColor = vh.getHighlightedNodeFormerColor();
                if (vh.isIsLastLevel()) {
                    zoomInButton.setEnabled(false);
                }

//                System.out.println("cset before vis clust size " + curCSetBeforePropClust.size());

                /* meaning that this is the top level, cannot zoom out anymore */
                if (viewHistoryList.size() == 0) {
                    zoomOutButton.setEnabled(false);
                }
                if (currentViewHisPos == 0) {
                    backButton.setEnabled(false);
                }

                /* changed 15 Jun 09 */
                curCSet = vh.getCSet();
                curComNodeCSet = vh.getComNodeCSet();
//                curCSet = curCSetBeforePropClust;
//                curComNodeCSet = curComNodeCSetBeforePropClust;
                curGOP = gp;

                curUsualVertexLocMap = vh.getUsualGraphVerLocMap();
                curVertexLocMap = vh.getVertexLocationMap();
                curVertexSizeMap = vh.getVertexSizeMap();
                nodesPropVectorMap = vh.getNodesPropVectorMap();

                clickedNodes = new ArrayList(vh.getClickedVertices());
                if (vh.getRecenteredNodes() == null) {
                    centerNodeSet = null;
                } else {
                    centerNodeSet = new HashSet(vh.getRecenteredNodes());
                }

                curGOPCentered = vh.getCurGOPCentered();
                inRecenteredMode = vh.isInConcentrationMode();
                curCSetCentered = vh.getCurCSetCentered();
                curCSetCenteredBPC = vh.getCurCSetCenteredBPC();
                curComNodeCSetCenteredBPC = vh.getCurComNodeCSetCenteredBPC();
                curComNodeCSetCentered = vh.getCurComNodeCSetCentered();

                nodesLabelList = vh.getNodesLabelList();
                maxNumOfMem = vh.getMaxNumMem();
                minNumOfMem = vh.getMinNumMem();
                maxMetaEdgeWeight = vh.getMaxMetaEdge();
                minMetaEdgeWeight = vh.getMinMetaEdge();
                maxBioEdgeWeight = vh.getMaxBioEdge();
                minBioEdgeWeight = vh.getMinBioEdge();
                statusBar.addCurrentStatToText(vh.getNumNodesInCurrentGraphView());
//                    System.out.println("GO Proc size: "+curGOP.clusterScoreMap.size());
//                    System.out.println("GO Proc: "+curGOP.clusterScoreMap);

//                layout.setGraph(dynamicGraph);
//                layout = (AggregateLayout<Object, Object>) vh.getLayout();
                layout = new AggregateLayout(new StaticLayout(dynamicGraph, new Transformer<Object, Point2D>() {

                    public Point2D transform(Object ver) {
                        return curVertexLocMap.get(ver);
                    }
                }));
//                visualizeResults(curCSet);
//                for (Object vertex : dynamicGraph.getVertices()){
//                    layout.lock(vertex, false);
//                }
                currentLevelGraph = vh.getUsualGraph();


//                ((CircleLayout)layout.getDelegate()).lock(true);
//                for (Object vertex : layout.getGraph().getVertices()){
//                    layout.setLocation(vertex, vh.getVertexLocationMap().get(vertex));
//                    System.out.println("Position "+vh.getVertexLocationMap().get(vertex));
////                    System.out.println("Real Position "+layout.transform(vertex));
//                    layout.lock(vertex, true);
//                }
//                for (Object node : clickedNodes) {
//                    vertexPaints.put(node, prevProcessedColor);
//                }
//                inConcentrationMode = false;


                /*previous reset function*/
//                    Layout layout = vv.getGraphLayout();
//                dynamicGraph = currentLevelGraph;
//                curVertexLocMap = new HashMap(curUsualVertexLocMap);
////                    layout.setDelegate(new CircleLayout<Object,Object>(currentLevelGraph));
////                layout.removeAll();
////                layout.setGraph(currentLevelGraph);
////                layout.initialize();
//                layout = new AggregateLayout(new StaticLayout(dynamicGraph, new Transformer() {
//
//                    public Object transform(Object ver) {
//                        return curUsualVertexLocMap.get(ver);
//                    }
//                }));

//                curCSetCentered = null;
//                curCSetCenteredBPC = null;
//                curComNodeCSetCentered = null;
//                curComNodeCSetCenteredBPC = null;
//                centerNodeSet = null;
//                highlightedNodeFormerColor = null;
//                highlightedNode = null;
//                nodeToClusterMap = new HashMap(usualNodeToClusterMap);

                colorCluster(curCSet, mainColor);
                if (clickedNodes != null) {
                    for (Object node : clickedNodes) {
                        vertexPaints.put(node, prevProcessedColor);
                    }
                }
                zoomInButton.setEnabled(vh.isZoomInEnabled());

//                vv.setGraphLayout(layout);
//                vv.repaint();

                /* clear picked state */
                vv.getPickedEdgeState().clear();
                vv.getPickedVertexState().clear();

                vv.setGraphLayout(layout);
                vv.repaint();
            }
        });

        backButton = new JButton("<");
        backButton.setEnabled(false);
        backButton.setToolTipText("Go to the previous graph view");
        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

//                zoomInButton.setEnabled(true);
                forwardButton.setEnabled(true);

//                final ViewHistory vh = viewHistoryList.remove(viewHistoryList.size() - 1);
//                System.out.println("currentViewHis "+currentViewHisPos);
                final ViewHistory vh = viewHistoryList.get(--currentViewHisPos);
//                System.out.println("currentViewHis "+currentViewHisPos);

                /* retrieve system information from viewhistory */
                nodeToClusterMap = vh.getNodeToClusterMap();
                usualNodeToClusterMap = vh.getUsualNodeToClusterMap();
                dynamicGraph = vh.getStoredGraph();
                PropInfoProcessor propInfoProc = vh.getPropInfoProc();
                curCSetBeforePropClust = vh.getCSetBeforeVis();
                curComNodeCSetBeforePropClust = vh.getComNodeCSetBeforeVis();
                highlightedNode = vh.getHighlightedNode();
                highlightedNodeFormerColor = vh.getHighlightedNodeFormerColor();
                if (vh.isIsLastLevel()) {
                    zoomInButton.setEnabled(false);
                }

//                System.out.println("cset before vis clust size " + curCSetBeforePropClust.size());

                /* meaning that this is the top level, cannot zoom out anymore */
                if (viewHistoryList.isEmpty()) {
                    zoomOutButton.setEnabled(false);
                }
                if (currentViewHisPos == 0) {
                    backButton.setEnabled(false);
                }

                /* changed 15 Jun 09 */
                curCSet = vh.getCSet();
                curComNodeCSet = vh.getComNodeCSet();
//                curCSet = curCSetBeforePropClust;
//                curComNodeCSet = curComNodeCSetBeforePropClust;
                curGOP = propInfoProc;

                curUsualVertexLocMap = vh.getUsualGraphVerLocMap();
                curVertexLocMap = vh.getVertexLocationMap();
                curVertexSizeMap = vh.getVertexSizeMap();
                nodesPropVectorMap = vh.getNodesPropVectorMap();


                clickedNodes = new ArrayList(vh.getClickedVertices());
                if (vh.getRecenteredNodes() == null) {
                    centerNodeSet = null;
                } else {
                    centerNodeSet = new HashSet(vh.getRecenteredNodes());
                }

                curGOPCentered = vh.getCurGOPCentered();
                inRecenteredMode = vh.isInConcentrationMode();
                curCSetCentered = vh.getCurCSetCentered();
                curCSetCenteredBPC = vh.getCurCSetCenteredBPC();
                curComNodeCSetCenteredBPC = vh.getCurComNodeCSetCenteredBPC();
                curComNodeCSetCentered = vh.getCurComNodeCSetCentered();

                nodesLabelList = vh.getNodesLabelList();
                maxNumOfMem = vh.getMaxNumMem();
                minNumOfMem = vh.getMinNumMem();
                maxMetaEdgeWeight = vh.getMaxMetaEdge();
                minMetaEdgeWeight = vh.getMinMetaEdge();
                maxBioEdgeWeight = vh.getMaxBioEdge();
                minBioEdgeWeight = vh.getMinBioEdge();
                statusBar.addCurrentStatToText(vh.getNumNodesInCurrentGraphView());
//                    System.out.println("GO Proc size: "+curGOP.clusterScoreMap.size());
//                    System.out.println("GO Proc: "+curGOP.clusterScoreMap);

//                layout.setGraph(dynamicGraph);
//                layout = (AggregateLayout<Object, Object>) vh.getLayout();
                layout = new AggregateLayout(new StaticLayout(dynamicGraph, new Transformer<Object, Point2D>() {

                    public Point2D transform(Object ver) {
                        return curVertexLocMap.get(ver);
                    }
                }));
//                visualizeResults(curCSet);
//                for (Object vertex : dynamicGraph.getVertices()){
//                    layout.lock(vertex, false);
//                }
                currentLevelGraph = vh.getUsualGraph();


//                ((CircleLayout)layout.getDelegate()).lock(true);
//                for (Object vertex : layout.getGraph().getVertices()){
//                    layout.setLocation(vertex, vh.getVertexLocationMap().get(vertex));
//                    System.out.println("Position "+vh.getVertexLocationMap().get(vertex));
////                    System.out.println("Real Position "+layout.transform(vertex));
//                    layout.lock(vertex, true);
//                }
                for (Object node : clickedNodes) {
                    vertexPaints.put(node, prevProcessedColor);
                }
                zoomInButton.setEnabled(vh.isZoomInEnabled());

                /* clear picked state */
                vv.getPickedEdgeState().clear();
                vv.getPickedVertexState().clear();

                vv.setGraphLayout(layout);
                vv.repaint();

            }
        });

        forwardButton = new JButton(">");
        forwardButton.setEnabled(false);
        forwardButton.setToolTipText("Go to the next graph view");
        forwardButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

//                zoomInButton.setEnabled(true);
//                forwardButton.setEnabled(true);
                zoomOutButton.setEnabled(true);
                backButton.setEnabled(true);

//        ViewHistory viewhist = new ViewHistory(curCSetBeforePropClust, curComNodeCSetBeforePropClust, curGOP, dynamicGraph);
                ViewHistory vh = viewHistoryList.get(++currentViewHisPos);
                if (currentViewHisPos == viewHistoryList.size() - 1) {
                    forwardButton.setEnabled(false);
                }
                if (vh.isIsLastLevel()) {
                    zoomInButton.setEnabled(false);
                }
                /* retrieve system information from viewhistory */
                nodeToClusterMap = vh.getNodeToClusterMap();
                usualNodeToClusterMap = vh.getUsualNodeToClusterMap();
                dynamicGraph = vh.getStoredGraph();
                PropInfoProcessor gp = vh.getPropInfoProc();
                curCSetBeforePropClust = vh.getCSetBeforeVis();
                curComNodeCSetBeforePropClust = vh.getComNodeCSetBeforeVis();
                highlightedNode = vh.getHighlightedNode();
                highlightedNodeFormerColor = vh.getHighlightedNodeFormerColor();

//        System.out.println("GO Proc size: " + curGOP.clusterScoreMap.size());

                /* changed 15 Jun 09 */
                curCSet = vh.getCSet();
                curComNodeCSet = vh.getComNodeCSet();
//                curCSet = curCSetBeforePropClust;
//                curComNodeCSet = curComNodeCSetBeforePropClust;
                curGOP = gp;

                curUsualVertexLocMap = vh.getUsualGraphVerLocMap();
                curVertexLocMap = vh.getVertexLocationMap();
                curVertexSizeMap = vh.getVertexSizeMap();
                nodesPropVectorMap = vh.getNodesPropVectorMap();

                clickedNodes = new ArrayList(vh.getClickedVertices());
                if (vh.getRecenteredNodes() == null) {
                    centerNodeSet = null;
                } else {
                    centerNodeSet = new HashSet(vh.getRecenteredNodes());
                }

                curGOPCentered = vh.getCurGOPCentered();
                inRecenteredMode = vh.isInConcentrationMode();
                curCSetCentered = vh.getCurCSetCentered();
                curCSetCenteredBPC = vh.getCurCSetCenteredBPC();
                curComNodeCSetCenteredBPC = vh.getCurComNodeCSetCenteredBPC();
                curComNodeCSetCentered = vh.getCurComNodeCSetCentered();

                nodesLabelList = vh.getNodesLabelList();
                maxNumOfMem = vh.getMaxNumMem();
                minNumOfMem = vh.getMinNumMem();
                maxMetaEdgeWeight = vh.getMaxMetaEdge();
                minMetaEdgeWeight = vh.getMinMetaEdge();
                maxBioEdgeWeight = vh.getMaxBioEdge();
                minBioEdgeWeight = vh.getMinBioEdge();
                statusBar.addCurrentStatToText(vh.getNumNodesInCurrentGraphView());

//                    System.out.println("GO Proc size: "+curGOP.clusterScoreMap.size());
//                    System.out.println("GO Proc: "+curGOP.clusterScoreMap);

//                layout.setGraph(dynamicGraph);
//                layout = (AggregateLayout<Object, Object>) vh.getLayout();
                layout = new AggregateLayout(new StaticLayout(dynamicGraph, new Transformer<Object, Point2D>() {

                    public Point2D transform(Object ver) {
                        return curVertexLocMap.get(ver);
                    }
                }));
//                visualizeResults(curCSet);
//                for (Object vertex : dynamicGraph.getVertices()){
//                    layout.lock(vertex, false);
//                }
                currentLevelGraph = vh.getUsualGraph();


//                ((CircleLayout)layout.getDelegate()).lock(true);
//                for (Object vertex : layout.getGraph().getVertices()){
//                    layout.setLocation(vertex, vh.getVertexLocationMap().get(vertex));
//                    System.out.println("Position "+vh.getVertexLocationMap().get(vertex));
////                    System.out.println("Real Position "+layout.transform(vertex));
//                    layout.lock(vertex, true);
//                }
                for (Object node : clickedNodes) {
                    vertexPaints.put(node, prevProcessedColor);
                }
                zoomInButton.setEnabled(vh.isZoomInEnabled());

                /* clear picked state */
                vv.getPickedEdgeState().clear();
                vv.getPickedVertexState().clear();

                vv.setGraphLayout(layout);
                vv.repaint();

            }
        });

        JPanel naviPanel = new JPanel(new GridLayout(1, 3));
        naviPanel.setPreferredSize(new Dimension(170, 33));
//        naviPanel.setBorder(BorderFactory.createTitledBorder("Navigation"));

        naviPanel.add(rewindButton);
        naviPanel.add(backButton);
        naviPanel.add(forwardButton);

        JPanel naviPanelWrapper = new JPanel(new BorderLayout());
        naviPanelWrapper.setBorder(BorderFactory.createTitledBorder("Graph View Navigation"));
//        naviPanelWrapper.setPreferredSize(new Dimension(177,51));
        naviPanelWrapper.add(naviPanel);
//        eastPanel.add(naviPanelWrapper);

        JPanel p = new JPanel();
//        p.add(naviPanelWrapper);
//        JPanel panelWrapper = new JPanel(new GridLayout(2, 1));
//        panelWrapper.add(p);

//        grid2.add(p);
//        panel1.add(naviPanelWrapper,BorderLayout.CENTER);
//        grid2.add(panelWrapper);
//        eastPanel.add(panel1);
//        eastPanel.add(zoomInButton);

        zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.setEnabled(false);
        zoomOutButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                zoomInButton.setEnabled(true);

                final ViewHistory vh = viewHistoryList.remove(viewHistoryList.size() - 1);
                /* retrieve system information from viewhistory */
                nodeToClusterMap = vh.getNodeToClusterMap();
                usualNodeToClusterMap = vh.getNodeToClusterMap();
                dynamicGraph = vh.getStoredGraph();
                PropInfoProcessor gp = vh.getPropInfoProc();
                curCSetBeforePropClust = vh.getCSetBeforeVis();
                curComNodeCSetBeforePropClust = vh.getComNodeCSetBeforeVis();
                highlightedNode = vh.getHighlightedNode();
                highlightedNodeFormerColor = vh.getHighlightedNodeFormerColor();


//                System.out.println("cset before vis clust size " + curCSetBeforePropClust.size());

                /* meaning that this is the top level, cannot zoom out anymore */
                if (viewHistoryList.size() == 0) {
                    zoomOutButton.setEnabled(false);
                }

                /* changed 15 Jun 09 */
                curCSet = vh.getCSet();
                curComNodeCSet = vh.getComNodeCSet();
//                curCSet = curCSetBeforePropClust;
//                curComNodeCSet = curComNodeCSetBeforePropClust;
                curGOP = gp;

                curUsualVertexLocMap = vh.getUsualGraphVerLocMap();
                curVertexLocMap = vh.getVertexLocationMap();
                curVertexSizeMap = vh.getVertexSizeMap();
                nodesPropVectorMap = vh.getNodesPropVectorMap();

                clickedNodes = new ArrayList(vh.getClickedVertices());
                if (vh.getRecenteredNodes() == null) {
                    centerNodeSet = null;
                } else {
                    centerNodeSet = new HashSet(vh.getRecenteredNodes());
                }
//                    System.out.println("GO Proc size: "+curGOP.clusterScoreMap.size());
//                    System.out.println("GO Proc: "+curGOP.clusterScoreMap);

//                layout.setGraph(dynamicGraph);
//                layout = (AggregateLayout<Object, Object>) vh.getLayout();
                layout = new AggregateLayout(new StaticLayout(dynamicGraph, new Transformer<Object, Point2D>() {

                    public Point2D transform(Object ver) {
                        return curVertexLocMap.get(ver);
                    }
                }));
//                visualizeResults(curCSet);
//                for (Object vertex : dynamicGraph.getVertices()){
//                    layout.lock(vertex, false);
//                }
                currentLevelGraph = vh.getUsualGraph();
                nodesLabelList = vh.getNodesLabelList();


//                ((CircleLayout)layout.getDelegate()).lock(true);
//                for (Object vertex : layout.getGraph().getVertices()){
//                    layout.setLocation(vertex, vh.getVertexLocationMap().get(vertex));
//                    System.out.println("Position "+vh.getVertexLocationMap().get(vertex));
////                    System.out.println("Real Position "+layout.transform(vertex));
//                    layout.lock(vertex, true);
//                }
                for (Object node : clickedNodes) {
                    vertexPaints.put(node, prevProcessedColor);
                }

                /* clear picked state */
                vv.getPickedEdgeState().clear();
                vv.getPickedEdgeState().clear();

                vv.setGraphLayout(layout);
                vv.repaint();



            }
        });


        zoomInButton = new JButton("Zoom In");
        zoomInButton.setToolTipText("Zoom in to explore the members of cluster(s)");
        zoomInButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Set pickedSet = vv.getPickedVertexState().getPicked();
                pw.println("pickedSet size: " + pickedSet.size());
                System.out.println("pickedSet size: " + pickedSet.size());
                boolean bad = false;
                if (pickedSet.size() != 0) {
                    for (Object obj : pickedSet) {
                        if (obj instanceof BioObject) {
                            bad = true;
                            break;
                        }
                    }
                    if (!bad) {
                        System.out.println("Num picked " + pickedSet.size());
                        System.out.println("Picked Set " + pickedSet);
                        zoomInAction(new HashSet(pickedSet));
//                        reLouvainCluster(curCSetBeforePropClust, true);
                    }
                }


            }
        });

//        JPanel panel2 = new JPanel(new GridLayout(3, 1));
        JPanel panel2 = new JPanel(new GridLayout(2, 1));
//        JPanel zoomPanel = new JPanel(new GridLayout(1, 1));
        JPanel zoomPanel = new JPanel(new BorderLayout());
//        zoomPanel.setPreferredSize(new Dimension(183,55));
        zoomPanel.setBorder(BorderFactory.createTitledBorder("Zooming"));
        zoomPanel.add(zoomInButton, BorderLayout.NORTH);
//        zoomPanel.add(zoomOutButton);
//        eastPanel.add(zoomPanel);
        panel2.add(naviPanelWrapper);
        panel2.add(zoomPanel);

//        JPanel zoomPanelWrapper = new JPanel(new BorderLayout());
//        JPanel zoomPanelWrapper = new JPanel();
//        zoomPanelWrapper.setBorder(BorderFactory.createTitledBorder("Zooming"));
//        zoomPanelWrapper.setPreferredSize(new Dimension(177,51));
//        zoomPanelWrapper.add(zoomPanel);
//        eastPanel.add(zoomPanelWrapper);

        p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder("Re-center"));
        JPanel grid3 = new JPanel(new GridLayout(1, 2));
//        JPanel grid3 = new JPanel(new GridLayout(1, 2));
        grid3.add(new JLabel("Num Hops: ", SwingConstants.CENTER));
//        numHops.setPreferredSize(new Dimension(64, 25));
        grid3.add(numHops);
//        numHops.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                recenterOnNodes();
//            }
//        });
        grid3.setPreferredSize(new Dimension(180, grid3.getPreferredSize().height));

        JButton recenterButton = new JButton("  Run  ");
//            reLouvainButton.setPreferredSize(new Dimension(100,20));
        recenterButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                recenterOnNodes();
            }
        });

        p.add(grid3, BorderLayout.NORTH);
        p.add(recenterButton, BorderLayout.SOUTH);
        p.setPreferredSize(new Dimension(173, p.getPreferredSize().height + 30));
        JPanel recenteringPanel = p;
//        panel2.add(p);

        p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Search (by Node Name)"));

//        JPanel grids = new JPanel(new GridLayout(2, 1));
        JPanel grids = new JPanel(new BorderLayout());

        JPanel tmpPanel = new JPanel();
//        tmpPanel = new JPanel();
        tmpPanel.add(searchTextField, FlowLayout.LEFT);
        grids.add(tmpPanel, BorderLayout.CENTER);

        searchTextField.setColumns(5);
        searchTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().length() != 0) {
                    searchFor();
                }

            }
        });

        JButton sButton = new JButton("Search");
        sButton.setToolTipText("Search for a node by its name");
        sButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().length() != 0) {
                    searchFor();
                }
            }
        });

//        JPanel tmpPanel = new JPanel();
        tmpPanel = new JPanel();
        tmpPanel.add(sButton);
        grids.add(tmpPanel, BorderLayout.EAST);

        p.add(grids, BorderLayout.NORTH);
//        eastPanel.add(p);

        searchResList = new JList(listModel);
//        searchResList = new JList();
        searchResList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResList.setLayoutOrientation(JList.VERTICAL);
//        searchResList.setVisibleRowCount(7);
//        searchResList.setPreferredSize(new Dimension(50,50));
        searchResList.setSize(50, 100);
        searchResList.setFixedCellWidth(7);

        listScroller = new JScrollPane(searchResList);
//        listScroller = new JScrollPane();
//        listScroller.setPreferredSize(new Dimension(10, 100));
//        listScroller.setMaximumSize(new Dimension(170,100));
//        listScroller

        final JButton focusSearchButton = new JButton("Highlight Selected Node");
        focusSearchButton.setActionCommand("Focus");
        focusSearchButton.setToolTipText("Highlight a cluster containing a node of interest");
        focusSearchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                focusNodeBySearch((BioObject) searchResList.getSelectedValue());
            }
        });

        searchResList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    focusSearchButton.doClick(); //emulate button click
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        listScroller.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        focusSearchButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(listScroller);
        panel.add(focusSearchButton);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
//        panel.setPreferredSize(new Dimension(70,panel.getPreferredSize().height));
        p.add(panel, BorderLayout.SOUTH);
//        p.setPreferredSize(new Dimension(150, p.getPreferredSize().height));
//        eastPanel.add(panel);
//        eastPanel.add(p);

//        JPanel panel3 = new JPanel(new GridLayout(2,1));
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.add(grid, BorderLayout.NORTH);
//        panel3.add(panel1,BorderLayout.NORTH);
        panel3.add(panel2, BorderLayout.SOUTH);

        JPanel panelAll = new JPanel(new BorderLayout());

//        panelAll.setPreferredSize(new Dimension(177,250));
        panelAll.add(panel3, BorderLayout.NORTH);
        JPanel recenterAndSearchPanel = new JPanel(new BorderLayout());
        recenterAndSearchPanel.add(recenteringPanel, BorderLayout.NORTH);
        recenterAndSearchPanel.add(p, BorderLayout.CENTER);
        panelAll.add(recenterAndSearchPanel, BorderLayout.SOUTH);

//        panelAll.add(p,BorderLayout.SOUTH);
        west.add(panelAll);

        final JPanel eastControls = new JPanel();
        eastControls.setOpaque(true);
        eastControls.setLayout(new BoxLayout(eastControls, BoxLayout.Y_AXIS));
        eastControls.add(Box.createVerticalGlue());

        //eastControls.add(eastSize);
//        eastControls.add(Box.createVerticalGlue());
//        eastControls.add(Box.createVerticalGlue());
//        eastControls.add(Box.createVerticalGlue());
//        eastControls.add(Box.createVerticalGlue());
//        eastControls.add(Box.createVerticalGlue());
        west.add(eastControls);

        return west;
    }

    /**
     * Initialize components of the eastPanel part.
     * @return Panel of eastPanel part
     */
    public JPanel initCompEast() {
        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(eastPanelSize, canvasSizeHeight));

        JPanel p = new JPanel(new BorderLayout());
//        p.setBorder(BorderFactory.createTitledBorder("Louvain Clustering"));
////        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));
////        p.setPreferredSize(new Dimension(183, p.getPreferredSize().height));
//        p.setPreferredSize(new Dimension(190, 55));
//
//        JButton reLouvainButton = new JButton("Clustering");
//        reLouvainButton.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                double ccValue = (double) ccSlider.getValue() / 10;
//                double mfValue = (double) mfSlider.getValue() / 10;
//                double bpValue = (double) bpSlider.getValue() / 10;
//
//                PropInfoProcessor.getWeightMap().put(NameSpace.BP, bpValue);
//                PropInfoProcessor.getWeightMap().put(NameSpace.MF, mfValue);
//                PropInfoProcessor.getWeightMap().put(NameSpace.CC, ccValue);
//                /* if we want to cluster peripheral clusters, not all clusters */
//                if (inConcentrationMode) {
////                if (curCSetCenteredBPC != null) {
//                    Set cSet = new HashSet();
//                    ArrayList retList = reLouvainCluster(curCSetCenteredBPC, false);
//                    Set<Set> cSetFromPropClust = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, false, false, true);
////                    curCSetCentered = (Set<Set>) retList.get(0);
//
//                    for (Object picked : centerNodeSet) {
//                        cSet.add(picked);
//                    }
////                    cSet.addAll(curCSetCentered);
//                    cSet.addAll(cSetFromPropClust);
//
//                    dynamicGraph = createGraph(cSet);
//
//                    layout = new AggregateLayout<Object, Object>(new CircleLayout<Object, Object>(dynamicGraph));
//                    ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 600);
//                    colorCluster(centerNodeSet, mainColor);
//                    colorCluster(cSetFromPropClust, peripheralColor);
//
//                    layout.setGraph(dynamicGraph);
//                    Graph allNodesGraph = SparseMultigraph.getFactory().create();
//                    for (Object ver : dynamicGraph.getVertices()) {
//                        allNodesGraph.addVertex(ver);
//                    }
////            nodesLabelList = chooseTwoTermsForLabel(dynamicGraph);
//
//                    Graph tempGraph = SparseMultigraph.getFactory().create();
//                    for (Object object : centerNodeSet) {
//                        tempGraph.addVertex(object);
//                    }
//                    for (Object ver : tempGraph.getVertices()) {
//                        allNodesGraph.addVertex(ver);
//                    }
//
//                    Layout subLayout = new CircleLayout(tempGraph);
//                    subLayout.setInitializer(vv.getGraphLayout());
//                    subLayout.setSize(new Dimension(120, 120));
//
//                    layout.put(subLayout, new Point(vv.getSize().width / 2, vv.getSize().height / 2));
////            nodesLabelList.putAll(chooseTwoTermsForLabel(tempGraph));
//                    nodesLabelList = chooseTwoTermsForLabel(allNodesGraph);
//                    layout.setGraph(dynamicGraph);
//                    vv.setGraphLayout(layout);
//
//                    curVertexLocMap = new HashMap<Object, Point2D>();
//                    for (Object ver : layout.getGraph().getVertices()) {
//                        curVertexLocMap.put(ver, layout.transform(ver));
//                    }
//                    backButton.setEnabled(true);
//                    forwardButton.setEnabled(false);
//                    saveState();
//                } else {
//                    /* redo louvainCluster on all nodes */
//                    ArrayList retList = null;
//                    Set<Set> cSet = null;
////                    if (!zoomOutButton.isEnabled()) {
////                        retList = reLouvainCluster(curCSetBeforePropClust, true);
////                        cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, true, true, true);
////                    } else {
//                    if (!backButton.isEnabled()) {
//                        retList = reLouvainCluster(curCSetBeforePropClust, true);
//                        cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, true, true, true);
//                    } else {
//                        retList = reLouvainCluster(curCSetBeforePropClust, false);
//                        cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, false, true, true);
//                    }
//
//                    dynamicGraph = createGraph(cSet);
//                    usualNodeToClusterMap = new HashMap(nodeToClusterMap);
//
//
//                    layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
//                    ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 600);
//                    vv.setGraphLayout(layout);
//
//                    layout = layoutGraph(layout, dynamicGraph);
////                    visualizeResults(cSet);
//                    colorCluster(cSet, mainColor);
//                    currentLevelGraph = dynamicGraph;
//                    vv.repaint();
//
////                    curVertexLocMap = new HashMap<Object,Point2D>();
////                    for (Object ver : layout.getGraph().getVertices()) {
////                        curVertexLocMap.put(ver, layout.transform(ver));
////                    }
////
////                    layout = new AggregateLayout(new FRLayout<Object,Object>(dynamicGraph));
////                    layout.setInitializer(new Transformer<Object,Point2D>(){
////
////                        public Point2D transform(Object arg0) {
////                            return curVertexLocMap.get(arg0);
////                        }
////                    });
////                    layout = new AggregateLayout(new CircleLayout<Object,Object>(dynamicGraph));
////                    ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 600);
////                    visualizeResults(cSet);
//
//                    curVertexLocMap = new HashMap<Object, Point2D>();
//                    for (Object ver : layout.getGraph().getVertices()) {
//                        curVertexLocMap.put(ver, layout.transform(ver));
//                    }
//                    curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);
//
//                    backButton.setEnabled(true);
//                    forwardButton.setEnabled(false);
//                    saveState();
//                }
//            }
//        });
//        p.add(reLouvainButton, BorderLayout.SOUTH);
//        eastPanel.add(p);
//        panelWrapper.add(p);

//        p = new JPanel();
        p = new JPanel(new BorderLayout());
//        JPanel p = new JPanel();
//        p.setBorder(BorderFactory.createTitledBorder("GO Namespaces Weights"));
        p.setBorder(BorderFactory.createTitledBorder("Property-Based Clustering"));
        JButton loadPropFileButton = new JButton("Load Prop. Info File");
        loadPropFileButton.setToolTipText("Load property information file");
        loadPropFileButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

//                inConcentrationMode = false;
                JFileChooser fileChooser = new JFileChooser(propInfoFile);
                File tempFile = propInfoFile;

                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//                fileChooser.setFileFilter(new MNetFileFilter());
//                    fileChooser.setFileHidingEnabled(false);
                fileChooser.showOpenDialog(NaviClusterApp.this);

                propInfoFile = fileChooser.getSelectedFile();

//                ImportFileForm form = ImportFileForm.getInstance(NaviClusterApp.this);
//                form.setVisible(true);

//                if (form.getNetwork() != null) {
                if (propInfoFile != null) {
                    try {
//                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(propInfoFile)));

                        StopWatch loadingTime = new StopWatch();
                        loadingTime.start();

//                        realGraph = form.getNetwork();
//                        sArr = form.getsArr(); dArr = form.getdArr(); wArr = form.getwArr();
//                        nodeMap.clear(); nodeMap.putAll(form.getIDToNodeMap());
//                        minBioEdgeWeight = form.getMinBioEdgeWeight();
//                        maxBioEdgeWeight = form.getMaxBioEdgeWeight();
//                        isWeightGraph = true;
//                        disconNodeSet.clear(); disconNodeSet.addAll(form.getDisconNodeSet());
                        preparePropertyInfoProcessor(propInfoFile);
                        makeSliders();
//                        currentLevelGraph = dynamicGraph;

                        loadingTime.stop();
                        pw.println("Time used for loading prop info file: " + loadingTime);
                        System.out.println("Time used for loading prop info file: " + loadingTime);

//                        louvainCluster(realGraph, realGraph.getVertexCount(),true);

                        ArrayList retList = louvainCluster(realGraph.getVertexCount(), true);
//                        preparePropertyInfoProcessor(null);
                        numOfLouvainClusters = ((Set) retList.get(0)).size();
                        Set<Set> cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, true, true, true);
                        /* if do on all nodes in canvas, visualize the result also, or else just do clustering */
                        dynamicGraph = createGraph(cSet);
                        usualNodeToClusterMap = new HashMap(nodeToClusterMap);

                        layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
                        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * outerRadius);
//                        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 1080);
                        vv.getPickedVertexState().clear();
                        vv.setGraphLayout(layout);
//                        vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction(ew));
                        vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction());
//                        vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabeller(ew));
                        vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabeller());

                        layout = layoutGraph(layout, dynamicGraph);

//                        visualizeResults(cSet);
                        colorCluster(cSet, mainColor);
                        currentLevelGraph = dynamicGraph;
                        vv.repaint();

                        curVertexLocMap = new HashMap<Object, Point2D>();
                        for (Object ver : layout.getGraph().getVertices()) {
                            curVertexLocMap.put(ver, layout.transform(ver));
                        }
                        curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);
//                        vv.setGraphLayout(layout);
//                        vv.getRenderContext().getParallelEdgeIndexFunction().reset();

                        currentLevel = 0;
//                        zoomInButton.setEnabled(true);
                        zoomOutButton.setEnabled(false);
                        backButton.setEnabled(true);
                        forwardButton.setEnabled(false);
                        clearState();
                        saveState();
                    } catch (IOException ex) {
                        Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    propInfoFile = tempFile;
                }
            }
        });
        JPanel northPanel = new JPanel(new GridLayout(2, 1));

        northPanel.add(loadPropFileButton);
        northPanel.add(new JLabel("Namespace Weights", SwingConstants.CENTER));
        northPanel.setPreferredSize(new Dimension(180, 50));
//        p.add(new JLabel("Namespace Weights", SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(northPanel, BorderLayout.NORTH);

        JPanel grid3 = new JPanel(new GridLayout(3, 1));

        nsSlidersSP.setBorder(new EmptyBorder(0, 0, 0, 0));
        nsSlidersSP.getViewport().add(namespaceSliders);



//        String sOntFile = oProp.getOntologyFile();
//        OntologyImport oImp = new OntologyImport(sOntFile);
//        int iCode = oImp.readData();
//
//        if (iCode == 0) {
        makeSliders();
//        }
        nsSlidersSP.setPreferredSize(new Dimension(180, nsSlidersSP.getPreferredSize().height));
//        p.add(nsSlidersSP);
        p.add(nsSlidersSP, BorderLayout.CENTER);

        JPanel grid2 = new JPanel(new GridLayout(2, 1));

        grid3 = new JPanel();

//            p = new JPanel();
//            p.setBorder(BorderFactory.createTitledBorder("Vis Threshold"));
//            grid3 = new JPanel(new GridLayout(2, 1));
        JLabel lab = new JLabel("#Clusters: ");
        lab.setLabelFor(thresholdTextField);
        grid3.add(lab);
        grid3.add(thresholdTextField);
//        thresholdTextField.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
////                    rePropCluster(false);
//                refinePropInfo(e);
//            }
//        });
        grid3.setPreferredSize(new Dimension(180, grid3.getPreferredSize().height));
        grid2.add(grid3);

        JButton refineButton = new JButton("Re-Cluster");
        refineButton.addActionListener(new ActionListener() {

            JProgressBar progressBar;

            public void actionPerformed(ActionEvent e) {
                if (inRecenteredMode) {
                    if (curCSetCenteredBPC.size() < Integer.parseInt(thresholdTextField.getValue().toString())) {
                        String toShown = "The number of clusters & nodes before the property-based clustering (" + curCSetCenteredBPC.size() + ") is less than that specified in the text box. ";
                        JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } else if (curCSetBeforePropClust.size() < Integer.parseInt(thresholdTextField.getValue().toString())) {
                    String toShown = "The number of clusters & nodes before the property-based clustering (" + curCSetBeforePropClust.size() + ") is less than that specified in the text box. ";
                    JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                eastPanel.setEnabled(false);
                westPanel.setEnabled(false);
                center.setEnabled(false);

                final JDialog jd = new JDialog(NaviClusterApp.this, "Please wait...", false);

                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new JLabel("Re-Clustering in Progress.", JLabel.CENTER));
//                panel.add(closePanel, BorderLayout.PAGE_END);

                progressBar = new JProgressBar(0, 100);
                progressBar.setValue(0);

                //Call setStringPainted now so that the progress bar height
                //stays the same whether or not the string is shown.
                progressBar.setStringPainted(true);
                progressBar.setIndeterminate(true);

                JPanel progressPanel = new JPanel();
                progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.LINE_AXIS));
                progressPanel.add(Box.createHorizontalGlue());
                progressPanel.add(progressBar);
                progressPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(progressBar, BorderLayout.PAGE_END);

                panel.setOpaque(true);
                jd.setContentPane(panel);

//                final JOptionPane optionPane = new JOptionPane("Re-clustering in Progress.",JOptionPane.INFORMATION_MESSAGE);
//                jd.setContentPane(optionPane);
//                jd.pack();
                jd.setSize(new Dimension(300, 150));
                jd.setLocationRelativeTo(NaviClusterApp.this);
                jd.setVisible(true);
                vv.setVisible(false);

                //Background task for loading images.
                SwingWorker worker = new SwingWorker<Void, Void>() {

                    @Override
                    public Void doInBackground() {
                        refinePropInfo();
                        return null;
                    }

                    @Override
                    public void done() {
//                        jd.setVisible(false);
                        jd.dispose();
                        setCursor(null);
                        eastPanel.setEnabled(true);
                        westPanel.setEnabled(true);
                        center.setEnabled(true);
                        vv.setVisible(true);
//                        try {
//
//                        } catch (InterruptedException ignore) {
//                        } catch (java.util.concurrent.ExecutionException e) {
//                            String why = null;
//                            Throwable cause = e.getCause();
//                            if (cause != null) {
//                                why = cause.getMessage();
//                            } else {
//                                why = e.getMessage();
//                            }
//                            System.err.println("Error retrieving file: " + why);
//                        }
                    }
                };

                worker.execute();
//                refinePropInfo();
//                jd.setVisible(false);
//                setCursor(null);
//                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                progressMonitor = new ProgressMonitor(NaviClusterApp.this,"Re-Clustering the network", "", 0, 100);
//                progressMonitor.setProgress(0);
//                task = new Task();
////                task.addPropertyChangeListener(this);
//                task.execute();

            }
        });
//            p.add(GORefineButton);
        JPanel tmpPanel = new JPanel(new BorderLayout());
        tmpPanel.add(refineButton, BorderLayout.NORTH);
        grid2.add(tmpPanel);


        grid2.setPreferredSize(new Dimension(180, 60));
//        grid2.setPreferredSize(new Dimension(180, grid2.getPreferredSize().height));
//        p.add(grid2);
        p.add(grid2, BorderLayout.SOUTH);
//        p.setPreferredSize(new Dimension(190, p.getPreferredSize().height + grid2.getPreferredSize().height));
        p.setPreferredSize(new Dimension(190, 280));
//        p.setPreferredSize(new Dimension(190, p.getPreferredSize().height+grid2.getPreferredSize().height));
//        p.setPreferredSize(new Dimension(190, p.getPreferredSize().height));
        east.add(p);


        p = new JPanel(new BorderLayout());

        p.setBorder(BorderFactory.createTitledBorder("Property Edges Filtering"));
        p.setToolTipText("Adjust property edges filter");
        propEdgeSlider.setMajorTickSpacing(2);
        propEdgeSlider.setMinorTickSpacing(1);
        propEdgeSlider.setPaintTicks(true);
        propEdgeSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    double threshold = source.getValue();
//                        System.out.println("threshold "+threshold);
                    if (threshold == source.getMaximum()) {
//                        System.out.println("thresh max: " + threshold);
                        medp.setThresholdForSE(0.0);
                    } else if (threshold == 0.0) {
                        /* inner product will never be more than 1, i.e. show all edges */
                        medp.setThresholdForSE(2.0);
                    } else {
//                        pw.println("thresh " + (source.getMaximum() - threshold));
//                        System.out.println("thresh " + (source.getMaximum() - threshold));
                        medp.setThresholdForSE(0.1 * (source.getMaximum() - threshold));
                    }
                    vv.repaint();
                }
            }
        });

//        JPanel grid3 = new JPanel(new GridLayout(1, 1));
//        grid3 = new JPanel(new GridLayout(1, 1));
//        grid3.add(propEdgeSlider);
//        grid3.setPreferredSize(new Dimension(173, grid3.getPreferredSize().height));
        p.add(propEdgeSlider, BorderLayout.SOUTH);
        propEdgeSlider.setPreferredSize(new Dimension(180, propEdgeSlider.getPreferredSize().height));
//        p.setPreferredSize(new Dimension(180, p.getPreferredSize().height));
//        eastPanel.add(p);
        east.add(p);

        p = new JPanel(new GridLayout(2, 1));
//        p.setBorder(BorderFactory.createTitledBorder("Concentrate on node(s)"));

        JButton createGraphViewButton = new JButton("Make Custom Graph View");
//        createGraphViewButton.setPreferredSize(new Dimension(173,33));
//            reLouvainButton.setPreferredSize(new Dimension(100,20));
        createGraphViewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                recenterOnNodes();
//                makeCustomGraphUI();
                MakeCustomGraphViewDialog dialog = new MakeCustomGraphViewDialog(NaviClusterApp.this, true, dynamicGraph, realGraph);
                dialog.setVisible(true);
                System.out.println("dialog return set size " + dialog.getSelectedNodeSet().size());
                System.out.println("dialog return set  " + dialog.getSelectedNodeSet());
                if (dialog.isAnswerOK()) {
                    Set cSet = new HashSet(dialog.getSelectedNodeSet());
                    PropInfoProcessor gp = new PropInfoProcessor();
                    gp.populatePropTerms(cSet);
//                    curGOP = propInfoProc;
                    vv.getRenderContext().getPickedEdgeState().clear();
                    vv.getRenderContext().getPickedVertexState().clear();
//                    nodesPropVectorMap.putAll(putInNodesPropVectorMapRelatively(propInfoProc.getNodesPropVectorMapBeforeCluster(cSet)));
                    nodesPropVectorMap.putAll(gp.getNodesPropVectorMapBeforeCluster(cSet));
                    dynamicGraph = createGraph(cSet);
                    usualNodeToClusterMap = new HashMap(nodeToClusterMap);

                    layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
//        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 600);
                    ((CircleLayout) layout.getDelegate()).setRadius(0.30 * outerRadius);
//                    ((CircleLayout) layout.getDelegate()).setRadius(0.30 * 1080);
                    vv.setGraphLayout(layout);

                    layout = layoutGraph(layout, dynamicGraph);

                    colorCluster(cSet, mainColor);
                    currentLevelGraph = dynamicGraph;
                    vv.repaint();
                    curVertexLocMap = new HashMap<Object, Point2D>();
                    for (Object ver : layout.getGraph().getVertices()) {
                        curVertexLocMap.put(ver, layout.transform(ver));
                    }
                    curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);
                    backButton.setEnabled(true);
                    forwardButton.setEnabled(false);
                    saveState();
                }
            }
        });


        p.add(createGraphViewButton);
        p.add(new JLabel("<html><center>To select nodes and clusters<br> more freely</center></html>"));
//        p.setPreferredSize(new Dimension(190, p.getPreferredSize().height + 30));
//        p.setPreferredSize(new Dimension(170, 33));
//        grid2.add(p);
//        panelWrapper.add(p);
        east.add(p);

        return east;
    }

    /**
     * Make sliders of namespaces for adjusting weights.
     */
    public void makeSliders() {
//        String[] nsNameList = {"Biological Process", "Cellular Component","Molecular Function", "Wataruuuu"};
        String[] nsNameList = PropInfoProcessor.getNamespaceMap().keySet().toArray(new String[1]);
        NameSpace[] nsList = PropInfoProcessor.getNamespaceMap().values().toArray(new NameSpace[1]);
        int iCnt = nsNameList.length;// NameSpace.size();
        int iCounter = 0;
        int iGridSize = 0;

        boolean isGO = false;
        if ((PropInfoProcessor.getNamespaceMap().keySet().size() == 3) && (PropInfoProcessor.getNamespaceMap().containsKey("biological_process"))
                && (PropInfoProcessor.getNamespaceMap().containsKey("molecular_function")) && (PropInfoProcessor.getNamespaceMap().containsKey("cellular_component"))) {
//            PropInfoProcessor.getNamespaceMap().get("biological_process").setWeight(1);
//            PropInfoProcessor.getNamespaceMap().get("molecular_function").setWeight(0);
//            PropInfoProcessor.getNamespaceMap().get("cellular_component").setWeight(0);
            isGO = true;
        }

        if (iCnt <= 3) {
            iGridSize = 3;
        } else {
            iGridSize = iCnt;
        }
        namespaceSliders.removeAll();
        oLstOntSlider.clear();
        nsWeightMap.clear();
        namespaceSliders.setPreferredSize(new Dimension(nsSlidersSP.getWidth() - 24, 47 * iGridSize));
        namespaceSliders.setLayout(new GridLayout(iGridSize, 1));
        for (iCounter = 0; iCounter < iCnt; iCounter++) {
            JSlider oSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 10);
            oSlider.setName(nsNameList[iCounter]);
            oSlider.setToolTipText(nsNameList[iCounter]);
            oSlider.setMajorTickSpacing(2);
            oSlider.setMinorTickSpacing(1);
            oSlider.setPaintTicks(true);
            oSlider.setSnapToTicks(true);
            oSlider.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    source.setValue(source.getValue());
                    if (!source.getValueIsAdjusting()) {
                        boolean isAllZero = true;
                        if (source.getValue() == 0) {

                            for (JSlider slider : oLstOntSlider) {
                                if (slider != source) {
                                    if (slider.getValue() > 0) {
                                        isAllZero = false;
                                    }
                                }
                            }
                            if (isAllZero) {
                                source.setValue(nsWeightMap.get(source));
                                String toShown = "Namespace weights should not be all zero.";
                                JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.WARNING_MESSAGE);
                            } else {
                                source.setValue(source.getValue());
                                nsWeightMap.put(source, source.getValue());
                            }
                        } else {
                            source.setValue(source.getValue());
                            nsWeightMap.put(source, source.getValue());
                        }
                    }
//                    if ((nsWeightMap.get(source) != 0) && (source.getValue() == 0)) {
//                        boolean isAllZero = true;
//                        for (JSlider slider : oLstOntSlider) {
//                            if (slider != source) {
//                                if (slider.getValue() > 0) {
//                                    isAllZero = false;
//                                }
//                            }
//                        }
//                        if (isAllZero) {
//                            System.out.println("new if");
//
//                            System.out.println("source " + source.getName());
//                            System.out.println("slider previous value " + nsWeightMap.get(source));
//                            source.setValue(nsWeightMap.get(source));
//                            System.out.println("source value " + source.getValue());
//                            String toShown = "Namespace weights should not be all zero.";
//                            JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.WARNING_MESSAGE);
//                        }
//                            else {
//                            System.out.println("new if");
//                            System.out.println("source " + source.getName());
//                            System.out.println("not all zero: source value " + source.getValue());
//                            source.setValue(source.getValue());
//                            nsWeightMap.put(source, source.getValue());
//                        }
//                    }
                }
            });
            TitledBorder oTitle = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), nsNameList[iCounter]);// NameSpace.getName(iCounter));
//            System.out.println("ns color "+nsList[iCounter].getColor());
            oTitle.setTitleColor(nsList[iCounter].getColor());
            oTitle.setTitlePosition(TitledBorder.CENTER);
//            oTitle.setTitleJustification(TitledBorder.CENTER);
            oSlider.setBorder(oTitle);
//            oSlider.setPreferredSize(new Dimension(namespaceSliders.getWidth(), 48));
            namespaceSliders.add(oSlider);
            oLstOntSlider.add(oSlider);
            nsWeightMap.put(oSlider, 10);
            if (isGO) {
                if (nsNameList[iCounter].equals("biological_process")) {
                    oSlider.setValue(10);
                } else if (nsNameList[iCounter].equals("molecular_function")) {
                    nsWeightMap.put(oSlider, 0);
                    oSlider.setValue(0);
                } else if (nsNameList[iCounter].equals("cellular_component")) {
                    nsWeightMap.put(oSlider, 0);
                    oSlider.setValue(0);
                }
            } else {
//                if (iCounter > 0)
//                    oSlider.setValue(0);
            }
        }

        if (iCnt < 3) {
            for (iCounter = 0; iCounter < 3 - iCnt; iCounter++) {
                namespaceSliders.add(new JPanel());
            }
        }

        nsSlidersSP.revalidate();
    }

    public void makeCustomGraphUI() {
        final JDialog jd = new JDialog(this);
        javax.swing.JButton jButton1;
        javax.swing.JButton jButton2;
        javax.swing.JButton jButton3;
        javax.swing.JButton jButton4;
        javax.swing.JLabel jLabel1;
        javax.swing.JLabel jLabel2;
        javax.swing.JLabel jLabel3;
        javax.swing.JLabel jLabel4;
        javax.swing.JPanel jPanel1;
        javax.swing.JPanel jPanel2;
        javax.swing.JPanel jPanel3;
        javax.swing.JPanel jPanel4;
        javax.swing.JScrollPane jScrollPane1;
        javax.swing.JScrollPane jScrollPane2;
        javax.swing.JScrollPane jScrollPane3;
        javax.swing.JScrollPane jScrollPane4;
        javax.swing.JSplitPane jSplitPane1;
        javax.swing.JSplitPane jSplitPane2;
        javax.swing.JTextArea jTextArea1;
        javax.swing.JTextArea jTextArea2;
        javax.swing.JTextArea jTextArea3;
        javax.swing.JTextArea jTextArea4;
        javax.swing.JPanel mainPanel;

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();


        jd.setTitle("Create a New Graph View");
        jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jd.setModal(true);

        JPanel panel = new JPanel();
        jd.setContentPane(panel);


        mainPanel.setName("mainPanel"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jButton2.setText("OK"); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton4.setText("Cancel"); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jLabel1.setText("Create a new graph view from the selected nodes?"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N


        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel1Layout.createSequentialGroup().add(28, 28, 28).add(jLabel1).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 200, Short.MAX_VALUE).add(jButton2).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jButton4).add(47, 47, 47)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(24, Short.MAX_VALUE).add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel1).add(jButton4).add(jButton2)).addContainerGap()));

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel2.setText("Please select nodes from the left and press >> button to move it to the right"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("If you do not need some nodes on the right pane, select them and then press << button to move them back"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("When you finish node selection, press the OK button, or else press the cancel button."); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel3Layout.createSequentialGroup().addContainerGap().add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jLabel2).add(jLabel4).add(jLabel3)).addContainerGap(57, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel3Layout.createSequentialGroup().addContainerGap().add(jLabel2).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jLabel3).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jLabel4).addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel4.setName("jPanel4"); // NOI18N

        jSplitPane1.setBorder(null);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.7);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jSplitPane2.setBorder(null);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPane2.setName("jSplitPane2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setBorder(null);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane2.setViewportView(jTextArea2);

        jSplitPane2.setTopComponent(jScrollPane2);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setBorder(null);
        jTextArea3.setName("jTextArea3"); // NOI18N
        jScrollPane3.setViewportView(jTextArea3);

        jSplitPane2.setRightComponent(jScrollPane3);

        jSplitPane1.setLeftComponent(jSplitPane2);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setBorder(null);
        jTextArea4.setName("jTextArea4"); // NOI18N
        jScrollPane4.setViewportView(jTextArea4);

        jSplitPane1.setRightComponent(jScrollPane4);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.setName("jPanel2"); // NOI18N

        jButton1.setText(">>"); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton3.setText("<<"); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel2Layout.createSequentialGroup().addContainerGap().add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel2Layout.createSequentialGroup().add(141, 141, 141).add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).add(18, 18, 18).add(jButton3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).add(144, 144, 144)));

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel4Layout.createSequentialGroup().addContainerGap().add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(2, 2, 2).add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE).add(8, 8, 8)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup().add(20, 20, 20).add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE).add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)).addContainerGap()));

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainPanelLayout.createSequentialGroup().add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainPanelLayout.createSequentialGroup().add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).add(13, 13, 13)).add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(mainPanelLayout.createSequentialGroup().add(9, 9, 9).add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE))).add(20, 20, 20)));
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup().add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).add(0, 0, 0).add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));


        jd.setContentPane(mainPanel);

//        jd.setPreferredSize(new Dimension(600, 600));
//        jd.setResizable(false);
        jd.pack();
        jd.setVisible(true);

        jd.setLocationRelativeTo(this);


    }


    /* Action performed when acting with components on the GUI zone*/
    /**
     * Highlight node being searched for.
     * @param nodeToFocus
     */
    public void focusNodeBySearch(BioObject nodeToFocus) {

        if (highlightedNode != null) {
            vertexPaints.put(highlightedNode, highlightedNodeFormerColor);
        }
        searchedBioObj = nodeToFocus;
        highlightedNode = nodeToClusterMap.get(nodeToFocus);
        highlightedNodeFormerColor = (Color) vertexPaints.get(highlightedNode);
        vertexPaints.put(highlightedNode, searchedColor);

        vv.repaint();

    }

    /**
     * Search for BioObject specified in the searchTextField
     * and populate the list shown on GUI
     */
    public void searchFor() {
        Set listRes = new HashSet();
        for (Object obj : dynamicGraph.getVertices()) {
            if (obj instanceof Set) {
//                System.out.println("num mem "+((Set)obj).size());
                for (Object bioObj : (Set) obj) {
                    BioObject bio = (BioObject) bioObj;
                    if (bio.getName().toUpperCase().contains(searchTextField.getText().toUpperCase())) {
                        listRes.add(bio);
                    } else if (bio.getStandardName().toUpperCase().contains(searchTextField.getText().toUpperCase())) {
                        listRes.add(bio);
                    } else {
                        for (String st : bio.getSynonym()) {
                            if (st.toUpperCase().contains(searchTextField.getText().toUpperCase())) {
                                listRes.add(bio);
                                break;
                            }
                        }
                    }

                }
            } else if (obj instanceof BioObject) {
//                System.out.println("num mem 1");
                BioObject bio = (BioObject) obj;
                if (bio.getName().toUpperCase().contains(searchTextField.getText().toUpperCase())) {
                    listRes.add(bio);
                } else if (bio.getStandardName().toUpperCase().contains(searchTextField.getText().toUpperCase())) {
                    listRes.add(bio);
                } else {
                    for (String st : bio.getSynonym()) {
                        if (st.toUpperCase().contains(searchTextField.getText().toUpperCase())) {
                            listRes.add(bio);
                            break;
                        }
                    }
                }
            }
        }
        listModel.removeAllElements();
//        pw.println("Search result: ");
//        System.out.println("Search result: ");
        for (Object res : listRes) {
//            pw.println(res);
//            System.out.println(res);
            listModel.addElement(res);

        }

    }

    /**
     * This method re-run property-based clustering based on the current nodes on the canvas
     * If namespace weights are adjusted, the property-based clustering will take new values
     * into consideration too.
     * @param e
     */
    public void refinePropInfo() {

//        double ccValue = (double) ccSlider.getValue() / 10;
//        double mfValue = (double) mfSlider.getValue() / 10;
//        double bpValue = (double) bpSlider.getValue() / 10;
//        System.out.println("cc "+ccValue);
//        System.out.println("mf "+mfValue);
//        System.out.println("bp "+bpValue);
        boolean nochange = true;
        int ind = 0;
        for (Entry<String, NameSpace> entry : PropInfoProcessor.getNamespaceMap().entrySet()) {
            if (entry.getValue().getWeight() != (double) oLstOntSlider.get(ind).getValue() / 10) {
                nochange = false;
                entry.getValue().setWeight((double) oLstOntSlider.get(ind).getValue() / 10);
                System.out.println("entry " + entry.getValue().getName() + " weight " + entry.getValue().getWeight());
            }
            ind++;
        }
//        if ((PropInfoProcessor.getWeightMap().get(NameSpace.BP) != bpValue)
//                || (PropInfoProcessor.getWeightMap().get(NameSpace.MF) != mfValue)
//                || (PropInfoProcessor.getWeightMap().get(NameSpace.CC) != ccValue)) {
//            nochange = false;
//        }
        //for now, force the PropInfoProcessor to use new values always.
        nochange = false;

//        PropInfoProcessor.getWeightMap().put(NameSpace.BP, bpValue);
//        PropInfoProcessor.getWeightMap().put(NameSpace.MF, mfValue);
//        PropInfoProcessor.getWeightMap().put(NameSpace.CC, ccValue);
        rePropCluster(!nochange);
    }

    /**
     * Concentrate on selected nodes by gathering nodes connecting with them between the distance
     * specified in number of hops textbox
     */
    public void recenterOnNodes() {
        inRecenteredMode = true;
        String toNumHops = numHops.getValue().toString();//.getText();
        int numHopsFromCenter = 0;

        try {
            numHopsFromCenter = Integer.parseInt(toNumHops);
        } catch (NumberFormatException e) {
            String toShown = "Number of hops should be integer only.";
            JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.INFORMATION_MESSAGE);
        }

        Set pickedSet = vv.getPickedVertexState().getPicked();
        pw.println("pickedSet size: " + pickedSet.size());
        System.out.println("pickedSet size: " + pickedSet.size());

        if (!pickedSet.isEmpty()) {
            /* check if the vertex is the flatClust (of flatClust or of BioObject) */
            /* Process vertices to be recentered */
            Set cSet = new HashSet(), comNodeCSet = new HashSet(), cSetCenter = new HashSet();
            Set nodesInCenterSet = new HashSet();
            Set nodesAroundCenter = new HashSet();
            Set toBeProcessed = new HashSet();
            centerNodeSet = new HashSet(pickedSet);
//            for (Object object : centerNodeSet){
//                BioObject bioobj = (BioObject)object;
//                if (bioobj.getStandardName().equals("OCA1")){
//                    System.out.println("found oca1");
////                    System.out.println("num neighbors "+originalGraph.getNeighborCount(bioobj));
//                    System.out.println("num neighbors "+originalGraph.getNeighbors(bioobj));
//                }
//            }
            clickedNodes = new ArrayList(pickedSet);
            viewHistoryList.get(currentViewHisPos).setClickedVertices(new ArrayList(clickedNodes));

            for (Object ver : pickedSet) {
//                    System.out.println("\nVertex: "+ver);
                if (ver instanceof Set) {
                    Set vertex = (Set) ver;
//                        System.out.println("\nVertex size: "+vertex.size());
                    nodesInCenterSet.addAll(vertex);
                    cSet.add(vertex);
                    cSetCenter.add(vertex);
                    comNodeCSet.add(flatClustToComNodeClust.get(vertex));
                } else if (ver instanceof BioObject) {

                    BioObject vertex = (BioObject) ver;
//                    System.out.println("ver : "+vertex);
                    nodesInCenterSet.add(vertex);
                    cSet.add(vertex);
                    cSetCenter.add(vertex);
                    /* Below code seems to be ok but not 100% sure */
                    comNodeCSet.add(vertex);
                }

            }
            toBeProcessed.addAll(nodesInCenterSet);

            for (int i = 1; i <= numHopsFromCenter; i++) {
                Set neighbors = new HashSet();
                Set tmpSet = new HashSet(toBeProcessed);
//                System.out.println("toBeProcessed: "+toBeProcessed);
                Iterator it = tmpSet.iterator();
                for (; it.hasNext();) {
                    Object object = it.next();
                    BioObject bio = (BioObject) object;
//                    System.out.println("Bio: " + bio);

                    for (Object neighbor : originalGraph.getNeighbors(bio)) {
//                        System.out.println("neighbor: " + neighbor);
                        if (!nodesInCenterSet.contains(neighbor) && !nodesAroundCenter.contains(neighbor)) {
                            neighbors.add(neighbor);
                        }
                    }
//                    System.out.println("neighbors: "+neighbors);
                    toBeProcessed.addAll(neighbors);
                    toBeProcessed.remove(object);
                }
                nodesAroundCenter.addAll(toBeProcessed);
//                System.out.println("tobeprocessed: "+toBeProcessed);
            }
//            System.out.println("Node around clusters "+nodesAroundCenter);
            Set newCSet = new HashSet();
            ArrayList retList = reLouvainCluster(nodesAroundCenter, false);
            Set cSetFromPropClust = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, false, false, true);
            newCSet.addAll(cSetFromPropClust);

            cSet.addAll(newCSet);

            /* if edge size to be relouvain clustered is zero, curComNodeCSetCentered will be null
             * and no need to add to comnodecSet
             */
            if (curComNodeCSetCentered != null) {
                comNodeCSet.addAll(curComNodeCSetCentered);
            }

//            System.out.println("cSet: " + cSet.size());

            dynamicGraph = createGraph(cSet);
//            layout = new AggregateLayout(new FRLayout<Object,Object>(dynamicGraph));
            layout = new AggregateLayout<Object, Object>(new CircleLayout<Object, Object>(dynamicGraph));
            ((CircleLayout) layout.getDelegate()).setRadius(0.40 * outerRadius);
//            ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 1080);
            colorCluster(newCSet, peripheralColor);
            colorCluster(cSetCenter, mainColor);
            layout.setGraph(dynamicGraph);
            Graph allNodesGraph = SparseMultigraph.getFactory().create();
            for (Object ver : dynamicGraph.getVertices()) {
                allNodesGraph.addVertex(ver);
            }
//            nodesLabelList = chooseTwoTermsForLabel(dynamicGraph);

            Graph tempGraph = SparseMultigraph.getFactory().create();
            for (Object object : cSetCenter) {
                tempGraph.addVertex(object);
            }
            for (Object ver : tempGraph.getVertices()) {
                allNodesGraph.addVertex(ver);
            }

            Layout subLayout = new CircleLayout(tempGraph);
            subLayout.setInitializer(vv.getGraphLayout());
            subLayout.setSize(new Dimension(innerRadius, innerRadius));
//            subLayout.setSize(new Dimension(216, 216));

            layout.put(subLayout, new Point(vv.getSize().width / 2, vv.getSize().height / 2));
//            nodesLabelList.putAll(chooseTwoTermsForLabel(tempGraph));
//            System.out.println("Test: num of allNodesGraph "+allNodesGraph.getVertexCount());
            nodesLabelList = chooseTwoTermsForLabel(allNodesGraph);
            vv.setGraphLayout(layout);

            curVertexLocMap = new HashMap<Object, Point2D>();
            for (Object ver : layout.getGraph().getVertices()) {
                curVertexLocMap.put(ver, layout.transform(ver));
            }

            vv.repaint();

            backButton.setEnabled(true);
            forwardButton.setEnabled(false);
            saveState();

        }
    }

    /**
     * Re-run louvain clustering by taking nodes in cSet as input
     * @param cSet
     * @param doOnAllNodesInCanvas
     * @return
     */
    public ArrayList reLouvainCluster(Set cSet, boolean isTopLevel) {
////DEBUG
//        printSortedCSet(cSet);
        /* Build new input for Louvain Clusterer */
//        Set<BioObject> bioObjectToClusteredSet = new HashSet<BioObject>();
        ////DEBUG////
        Set<BioObject> bioObjectToClusteredSet = new TreeSet<BioObject>(new Comparator(){

            @Override
            public int compare(Object t, Object t1) {
                BioObject bio1 = (BioObject)t;
                BioObject bio2 = (BioObject)t1;
                return bio1.getName().compareTo(bio2.getName());
            }
            
        });
       
        int id = 0;
        nodeMap.clear();
        disconNodeSet.clear();
        final Map<BioObject, Integer> idMap = new HashMap<BioObject, Integer>();
        Iterator iter = cSet.iterator();
        System.out.println("cset " + cSet);
        /* Add bioObject to the flatClust */
        for (; iter.hasNext();) {
            Object obj = iter.next();
            if (obj instanceof Set) {
                Set<BioObject> set = (Set<BioObject>) obj;
                for (BioObject bio : set) {
                    ////DEBUG////
//                    if (bio.getName().equalsIgnoreCase("AT2G38040"))
//                        System.out.println("id "+id+" name "+bio);
                    bioObjectToClusteredSet.add(bio);
                    nodeMap.put(id, bio);
                    idMap.put(bio, id);
                    id++;
                }
            } //                bioObjectToClusteredSet.addAll((Set)obj);
            else if (obj instanceof BioObject) {
                ////DEBUG////
                BioObject bio = (BioObject)obj;
//                if (bio.getName().equalsIgnoreCase("AT2G38040"))
//                        System.out.println("id "+id+" name "+bio);
                bioObjectToClusteredSet.add(bio);
                nodeMap.put(id, (BioObject) obj);
                idMap.put((BioObject) obj, id);
//                System.out.println("obj "+obj+" id "+id);
                id++;
            }
        }
//        int id = 0;
//        for (BioObject bio : bioObjectToClusteredSet){
//            nodeMap.put(id,bio);
//            idMap.put(bio,id);
//            id++;
//        }
//        disconNodeSet.addAll(nodeMap.keySet());
      
//        Comparator cmp = new Comparator() {
//
//            ////DEBUG////   
////            int count = 0;
//            public int compare(Object e1, Object e2) {
//                Pair pair1 = originalGraph.getEndpoints(e1);
//                Object e1v1 = pair1.getFirst();
//                Object e1v2 = pair1.getSecond();
//                Pair pair2 = originalGraph.getEndpoints(e2);
//                Object e2v1 = pair2.getFirst();
//                Object e2v2 = pair2.getSecond();
//                if (idMap.get(e1v1) < idMap.get(e2v1)) {
//                    return -1;
//                } else if (idMap.get(e1v1) == idMap.get(e2v1)) {
//                    if (idMap.get(e1v2) < idMap.get(e2v2)) {
//                        return -1;
//                    } else if (idMap.get(e1v2) == idMap.get(e2v2)) {
//                        ////DEBUG////
////                        System.out.println("edge " + e1+"\t"+e2);
////                        count = count+1;
//                        return 0;
//                    } else {
//                        return 1;
//                    }
//                } else {
//                    return 1;
//                }
//            }
//        };
//        Set edgeSet = new TreeSet(cmp);
        Set<BioEdge> edgeSet = new TreeSet<BioEdge>(new Comparator() {

            public int compare(Object o1, Object o2) {
//                Pair pair1 = originalGraph.getEndpoints(e1);
                BioEdge e1 = (BioEdge)o1;
                BioEdge e2 = (BioEdge)o2;
                Object e1v1 = ((BioObject) e1.getNode1());
                Integer i11 = idMap.get(e1v1);
                Object e1v2 = ((BioObject) e1.getNode2());
                Integer i12 = idMap.get(e1v2);
//                Pair pair2 = originalGraph.getEndpoints(e2);
                Object e2v1 = ((BioObject) e2.getNode1());
                Integer i21 = idMap.get(e2v1);
                Object e2v2 = ((BioObject) e2.getNode2());
                Integer i22 = idMap.get(e2v2);
                if (i11 > i12) {
                    int temp = i11;
                    i11 = i12;
                    i12 = temp;
                }
                if (i21 > i22) {
                    int temp = i21;
                    i21 = i22;
                    i22 = temp;
                }
                if (i11 < i21) {
//                    System.out.println(e1v1 + " " + e1v2 + "<" + e2v1 + " " + e2v2);
                    return -1;
                } else if (i11 > i21) {
//                    System.out.println(e1v1 + " " + e1v2 + ">" + e2v1 + " " + e2v2);
                    return 1;
                } else if (i11 == i21) {
                    if (i12 < i22) {
//                        System.out.println(e1v1 + " " + e1v2 + "<" + e2v1 + " " + e2v2);
                        return -1;
                    } else if (i12 > i22) {
//                        System.out.println(e1v1 + " " + e1v2 + ">" + e2v1 + " " + e2v2);
                        return 1;
                    } else {
//                        System.out.println(e1v1 + " " + e1v2 + "=" + e2v1 + " " + e2v2);
                        return 0;
                    }
                }
                return 0;
//                if (idMap.get(e1v1) < idMap.get(e2v1)) {
//                    return -1;
//                } else if (idMap.get(e1v1) == idMap.get(e2v1)) {
//                    if (idMap.get(e1v2) < idMap.get(e2v2)) {
//                        return -1;
//                    } else if (idMap.get(e1v2) == idMap.get(e2v2)) {
//                        return 0;
//                    } else {
//                        return 1;
//                    }
//                } else {
//                    return 1;
//                }
            }
        });

        ////DEBUG////
//        System.out.println("count edge dup "+count);
//        System.out.println(originalGraph.getEdgeCount());
        /* Add corresponding edges to the edgeSet*/

        ////DEBUG////
//        PrintWriter pwlocal = null;
//        Set<Entry<BioObject,Integer>> sortedMap = new TreeSet<Entry<BioObject,Integer>>(new Comparator(){
//
//            @Override
//            public int compare(Object t1, Object t2) {
//                Entry<BioObject,Integer> e1 = (Entry<BioObject,Integer>)t1;
//                Entry<BioObject,Integer> e2 = (Entry<BioObject,Integer>)t2;
////                return e1.getKey().toString().compareTo(e2.getKey().toString());
//                if (e1.getValue() > e2.getValue()){
//                    return -1;
//                } else if (e1.getValue() < e2.getValue())
//                    return 1;             
//                else 
//                    return e1.getKey().getName().compareTo(e2.getKey().getName());
//            }
//            
//        });
//        Map<BioObject,Integer> bioMap = new HashMap<BioObject,Integer>();
        ////DEBUG////
//        try {
//            pwlocal = new PrintWriter(new File("debug-incidentedges"));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
//        }

        ////DEBUG////
        int count = 0, ind = 0;
//        Set notOrphanSet = new HashSet();
        Set noEdgeAddSet = new HashSet();
        ////DEBUG////
            Object problemEdge = null;
        for (BioObject bio : bioObjectToClusteredSet) {
            boolean noEdgeAdded = true;
            if (originalGraph.getIncidentEdges(bio) == null) {
                System.out.println("bio problem " + bio);
                continue;
            }
//            ////DEBUG////
//            if (ind%250 == 0)
//                System.out.println("discon "+disconNodeSet.size());
//            if (disconNodeSet.size() < 4652 && disconNodeSet.size() > 4630){
////                if (ind%10 == 0)
//                System.out.println("discon fine "+disconNodeSet.size());
//                System.out.println("bio "+bio+"\t"+originalGraph.getIncidentEdges(bio).size());
//                    
//            }
            ////DEBUG////
//            pwlocal.println(bio+"\t"+originalGraph.getIncidentEdges(bio).size());
//            bioMap.put(bio, originalGraph.getIncidentEdges(bio).size());


            
            for (Object edge : originalGraph.getIncidentEdges(bio)) {
                BioEdge bioEdge = (BioEdge)edge;
//                Pair pair = originalGraph.getEndpoints(edge);
                Object pairEnd = bioEdge.getNode1();
                if (pairEnd.equals(bio))
                    pairEnd = bioEdge.getNode2();
//                Object secondVertex = bioEdge.getNode2();
                
//                Object firstVertex = pair.getFirst();
//                Object secondVertex = pair.getSecond();
                
                ////DEBUG////
//                if (problemEdge == null)
//                    if (((BioObject) pairEnd).getName().equalsIgnoreCase("AT1G23600") && ((BioObject) secondVertex).getName().equalsIgnoreCase("AT1G15360")
//                            || ((BioObject) pairEnd).getName().equalsIgnoreCase("AT1G15360") && ((BioObject) secondVertex).getName().equalsIgnoreCase("AT1G23600")) {
//
//                        problemEdge = edge;
//                    }
                
//                ////DEBUG////
//                if (bio.getName().equalsIgnoreCase("AT2G38040") || secondVertex.toString().equalsIgnoreCase("AT2G38040")) {
//                    System.out.println(edge);
//                }
//                ////DEBUG////
//                if (bio.getName().equalsIgnoreCase("AT1G12230")) {
//                    System.out.println(edge);
//                }
                                
                if (!idMap.containsKey(pairEnd)) {
                    continue;
                }
                
//                ////DEBUG////
//                if (bio.getName().equalsIgnoreCase("AT2G38040") || secondVertex.toString().equalsIgnoreCase("AT2G38040")) {
//                    System.out.println("found first");
//                    System.out.println(idMap.get(firstVertex)+" found in discon set? "+disconNodeSet.contains(idMap.get(firstVertex)));
//                }
//                ////DEBUG////
//                if (bio.getName().equalsIgnoreCase("AT1G12230")) {
//                    System.out.println("found first");
//                    System.out.println(idMap.get(firstVertex)+" found in discon set? "+disconNodeSet.contains(idMap.get(firstVertex)));
//                }
                
//                if (!idMap.containsKey(secondVertex)) {
//                    continue;
//                }
                ////DEBUG////
                if (bio.getName().equalsIgnoreCase("AT1G23600")) {
                    System.out.print(edge+"\t");
                    System.out.println(edgeSet.contains(edge));
                    
                }
                ////DEBUG////
                if (bio.getName().equalsIgnoreCase("AT1G15360")) {
                    System.out.print(edge+"\t");
                    System.out.println(edgeSet.contains(edge));
                }
                
//                ////DEBUG////
//                if (bio.getName().equalsIgnoreCase("AT2G38040") || secondVertex.toString().equalsIgnoreCase("AT2G38040")) {
//                    System.out.println("found second");
//                    System.out.println(idMap.get(secondVertex)+" found in discon set? "+disconNodeSet.contains(idMap.get(secondVertex)));
//                }
//                ////DEBUG////
//                if (bio.getName().equalsIgnoreCase("AT1G12230")) {
//                    System.out.println("found second");
//                    System.out.println(idMap.get(secondVertex)+" found in discon set? "+disconNodeSet.contains(idMap.get(secondVertex)));
//                }
//                
//                disconNodeSet.remove(idMap.get(firstVertex));
//                disconNodeSet.remove(idMap.get(secondVertex));
//                notOrphanSet.add(firstVertex);
//                notOrphanSet.add(secondVertex);

                ////DEBUG////
                int temp = edgeSet.size();
                edgeSet.add(bioEdge);
                if (edgeSet.size() == temp) {
                    count++;
                }
                ////DEBUG////
                if (bio.getName().equalsIgnoreCase("AT1G23600")) {
                    if (problemEdge != null){
                        System.out.println("prob edge contains "+ edgeSet.contains(problemEdge));
                        System.out.println("edge size "+edgeSet.size());
                    }
//                    System.out.println(edgeSet.size());
                    
                }
                ////DEBUG////
                if (bio.getName().equalsIgnoreCase("AT1G15360")) {
                    if (problemEdge != null){
                        System.out.println("prob edge contains "+ edgeSet.contains(problemEdge));
                        System.out.println("edge size "+edgeSet.size());
                    }
//                    System.out.println(edgeSet.size());
                    
                }
                noEdgeAdded = false;
            }
            if (noEdgeAdded) {
                noEdgeAddSet.add(bio);
            }
            ////DEBUG////
//            if (ind%50 == 0)
            if (edgeSet.size() < 30650 && edgeSet.size() > 30590) {
                System.out.println("edgeset "+edgeSet.size());
                System.out.println("bio: "+bio+" incident edge size "+originalGraph.getIncidentEdges(bio).size());
                if (problemEdge != null){
                        System.out.println("prob edge contains "+ edgeSet.contains(problemEdge));
                        System.out.println("edge size "+edgeSet.size());
                    }
            }
            if (ind%250 == 0)
                if (problemEdge != null){
                        System.out.println("prob edge contains "+ edgeSet.contains(problemEdge));
                        System.out.println("edge size "+edgeSet.size());
                    }
//            edgeSet.addAll(originalGraph.getIncidentEdges(bio));
            ind++;
        }
        ////DEBUG////
//        sortedMap.addAll(bioMap.entrySet());
//        for (Entry<BioObject,Integer> entry : sortedMap){
//            pwlocal.println(entry.getKey()+"\t"+entry.getValue());
//        }
//        pwlocal.close(); 

//        tmpSortedSet.addAll(edgeSet);
//        edgeSet = tmpSortedSet;

//        System.out.println("Disconn node set before remove: " + disconNodeSet.size());
//        for (Object obj : notOrphanSet){
//            disconNodeSet.remove(idMap.get(obj));
//        }
        for (Object obj : noEdgeAddSet){
            BioObject bio = (BioObject)obj;
//            BioObject bio = nodeMap.get(idObj);
            BioEdge edge = new BioEdge(bio,bio,0.0);
            edgeSet.add(edge);
        }
//        disconNodeSet.clear();
//        System.out.println("noedgeadd size "+noEdgeAddSet.size());
//        int pivot = 0;
//        for (Object obj : noEdgeAddSet) {
//            /* swap "bio" to the front of the id list so that it can be recognized
//             * after louvain clustering
//             */
//            BioObject bio = (BioObject) obj;
//            int tmp = 0;
//            BioObject tmpBio = null;
//            if (pivot == idMap.get(bio))
//            {
//                if (pivot+1 < nodeMap.size())
//                    pivot++;
//                else 
//                    continue;
//            }
//            if (idMap.get(bio) != pivot) {
//                // swap
//                tmp = idMap.get(bio);
//                tmpBio = nodeMap.get(pivot);
//                idMap.put(bio, pivot);
//                nodeMap.put(pivot, bio);
//                idMap.put(tmpBio, tmp);
//                nodeMap.put(tmp, tmpBio);
////                    System.out.println("tmpBio "+tmpBio+ " tmp "+tmp);
////                    System.out.println("bio "+bio+" pivot "+pivot);
//            } 
//            pivot++;
//        }
        int numEdge = edgeSet.size();
//        pwlocal.println("Edge set size to be new input for Louvain Clustering: " + numEdge);
//        pwlocal.println("BioObject set size to be new input for Louvain Clustering: " + bioObjectToClusteredSet.size());
        System.out.println("Edge set size to be new input for Louvain Clustering: " + numEdge);
        System.out.println("BioObject set size to be new input for Louvain Clustering: " + bioObjectToClusteredSet.size());
        System.out.println("Disconn node set: " + disconNodeSet.size());
        System.out.println("dup count: " + count);
        System.out.println("idMap count: "+idMap.size());

        sArr = new int[numEdge];
        dArr = new int[numEdge];
        if (wArr != null) {
            wArr = new double[numEdge];
        }
        int i = 0;

        /* Build new three input array for Louvain Clusterer */
//        StopWatch sw = new StopWatch();
//        sw.start();

        for (Object edge : edgeSet) {
            BioEdge bioEdge = (BioEdge)edge;
            Object firstVertex = bioEdge.getNode1();
            Object secondVertex = bioEdge.getNode2();             

//            Pair pair = originalGraph.getEndpoints(edge);
//            Object firstVertex = pair.getFirst();
//            Object secondVertex = pair.getSecond();
//            Object tmp = null;
//            if (idMap.get(firstVertex) > idMap.get(secondVertex)){
//                tmp = firstVertex;
//                firstVertex = secondVertex;
//                secondVertex = tmp;
//            }
//            System.out.println(idMap.get(firstVertex)+":"+idMap.get(secondVertex));
            sArr[i] = idMap.get(firstVertex);
            dArr[i] = idMap.get(secondVertex);
            if (sArr[i] > dArr[i]){
                int tmp = sArr[i];
                sArr[i] = dArr[i];
                dArr[i] = tmp;
            }
            if (isWeightGraph) {
                double weight = 1.0;
                if (edge instanceof BioEdge) {
                    weight = ((BioEdge) edge).getWeight();
                }
                wArr[i] = weight;
            }
            i++;
        }
        if (i == 0) {
            sArr = null;
        }

//        sw.stop();
//        System.out.println("Load to String time "+sw);

        ArrayList retList = louvainCluster(bioObjectToClusteredSet.size(), isTopLevel);
        return retList;
    }

    /**
     * Re-run property-based clustering on the current nodes on the canvas
     * Namespace weights at the time of running this method are considered also.
     * @param newAnnot
     */
    public void rePropCluster(boolean newAnnot) {
        vv.getPickedVertexState().clear();
        vv.getPickedEdgeState().clear();
        /* if inConcentrationMode is true, the program is concentrating on some node(s). */
        if (inRecenteredMode) {
//            if (curCSetCenteredBPC.size() >= Integer.parseInt(thresholdTextField.getValue().toString())) {

            Set cSetFromPropClust = propertyBasedCluster(curCSetCenteredBPC, curComNodeCSetCenteredBPC, curGOPCentered, false, false, newAnnot);
            Set cSet = new HashSet();
//                System.out.println("CenterNodeSet: "+centerNodeSet);
            for (Object picked : centerNodeSet) {
                cSet.add(picked);
            }
            cSet.addAll(cSetFromPropClust);
            dynamicGraph = createGraph(cSet);

            layout = new AggregateLayout<Object, Object>(new CircleLayout<Object, Object>(dynamicGraph));
            ((CircleLayout) layout.getDelegate()).setRadius(0.40 * outerRadius);
//                ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 1080);
            colorCluster(centerNodeSet, mainColor);
            colorCluster(cSetFromPropClust, peripheralColor);

            layout.setGraph(dynamicGraph);
            Graph allNodesGraph = SparseMultigraph.getFactory().create();
            for (Object ver : dynamicGraph.getVertices()) {
                allNodesGraph.addVertex(ver);
            }
//            nodesLabelList = chooseTwoTermsForLabel(dynamicGraph);

            Graph tempGraph = SparseMultigraph.getFactory().create();
            for (Object object : centerNodeSet) {
                tempGraph.addVertex(object);
            }
            for (Object ver : tempGraph.getVertices()) {
                allNodesGraph.addVertex(ver);
            }

            Layout subLayout = new CircleLayout(tempGraph);
            subLayout.setInitializer(vv.getGraphLayout());
            subLayout.setSize(new Dimension(innerRadius, innerRadius));
//                subLayout.setSize(new Dimension(216, 216));

            layout.put(subLayout, new Point(vv.getSize().width / 2, vv.getSize().height / 2));
//            nodesLabelList.putAll(chooseTwoTermsForLabel(tempGraph));
            nodesLabelList = chooseTwoTermsForLabel(allNodesGraph);
            layout.setGraph(dynamicGraph);
            vv.setGraphLayout(layout);
            curVertexLocMap = new HashMap<Object, Point2D>();
            for (Object ver : layout.getGraph().getVertices()) {
                curVertexLocMap.put(ver, layout.transform(ver));
            }

            vv.repaint();
            saveState();
            backButton.setEnabled(true);
            forwardButton.setEnabled(false);
//            } else {
//                String toShown = "The number of Louvain clusters on the canvas (" + curCSetCenteredBPC.size() + ") is less than that specified in the text box. ";
//                JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.INFORMATION_MESSAGE);
//
//            }
        } else if (curCSetBeforePropClust.size() >= Integer.parseInt(thresholdTextField.getValue().toString())) {
            Set cSet = propertyBasedCluster(curCSetBeforePropClust, curComNodeCSetBeforePropClust, curGOP, false, true, newAnnot);
            dynamicGraph = createGraph(curCSet);
            usualNodeToClusterMap = new HashMap(nodeToClusterMap);

//            layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
//            ((CircleLayout) layout.getDelegate()).setRadius(0.40 * outerRadius);
////            ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 1080);
//            vv.setGraphLayout(layout);

            layout = layoutGraph(layout, dynamicGraph);
//            visualizeResults(cSet);
            colorCluster(cSet, mainColor);
            currentLevelGraph = dynamicGraph;
//            vv.repaint();

            curVertexLocMap = new HashMap<Object, Point2D>();
            for (Object ver : layout.getGraph().getVertices()) {
                curVertexLocMap.put(ver, layout.transform(ver));
            }
            curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);
            saveState();
            backButton.setEnabled(true);
            forwardButton.setEnabled(false);
        }
//        else {
//            String toShown = "The number of Louvain clusters on the canvas (" + curCSetBeforePropClust.size() + ") is less than that specified in the text box. ";
//            JOptionPane.showMessageDialog(NaviClusterApp.this, toShown, "Notice", JOptionPane.INFORMATION_MESSAGE);
//
//        }

    }

    /**
     * Already commonly used by doubleclickplugin function
     * Assume that vertexToProcessed is not null flatClust
     */
    private boolean zoomInAction(Set vertexToProcessed) {
        inRecenteredMode = false;
        int countBioObjSet = 0;
        Set setOfComNodeCSet = new HashSet();

        /* check if the vertex is the flatClust (of flatClust or of BioObject) */
        boolean lastLevel = false;
        ViewHistory viewhist = viewHistoryList.get(currentViewHisPos);

        // DEBUG ----- below viewhistory management will not be used anymore (3/23)
//        ViewHistory viewhist = new ViewHistory(curCSetBeforePropClust, curComNodeCSetBeforePropClust, curGOP, dynamicGraph);
//        ViewHistory viewhist = new ViewHistory(curCSetBeforePropClust, curComNodeCSetBeforePropClust, curGOP, layout);
//        viewhist.setCSet(curCSet);
//        viewhist.setComNodeCSet(curComNodeCSet);
////        viewHistoryList.add(viewhist);
        viewhist.setClickedVertices(new ArrayList(vertexToProcessed));
//        currentViewHisPos = viewHistoryList.size()-1;
//        if (centerNodeSet == null) {
//            viewhist.setRecenteredNodes(null);
//        } else {
//            viewhist.setRecenteredNodes(new ArrayList(centerNodeSet));
//        }
//
//        viewhist.setVertexLocationMap(curVertexLocMap);
////        System.out.println("--------------"+curVertexLocMap.size());
//        viewhist.setUsualGraphVerLocMap(curUsualVertexLocMap);
////        System.out.println("--------------"+curUsualVertexLocMap.size());
////        viewhist.setVertexLocationMap(verLocationMap);
//
//        viewhist.setStoredGraph(dynamicGraph);
//        viewhist.setUsualGraph(currentLevelGraph);
//        viewhist.setNodeToClusterMap(nodeToClusterMap);
//        viewhist.setUsualNodeToClusterMap(usualNodeToClusterMap);

//        System.out.println("GO Proc size: " + curGOP.clusterScoreMap.size());
        zoomOutButton.setEnabled(true);
        backButton.setEnabled(true);
        forwardButton.setEnabled(false);
        vv.getPickedEdgeState().clear();
        vv.getPickedVertexState().clear();
        Set tempCSet = new HashSet();
        Set comNodeSet = new HashSet();

        System.out.println("vertexToProcessed " + vertexToProcessed);
        /* Deal with extracting members from vertex selected */
        for (Object ver : vertexToProcessed) {
            Set vertex = (Set) ver;
            pw.println("\nVertex: " + vertex);
            pw.println("\nVertex size: " + vertex.size());
//            pw.println("Vertex Position " + layout.transform(ver));
            System.out.println("\nVertex: " + vertex);
            System.out.println("\nVertex size: " + vertex.size());
//            System.out.println("Vertex Position " + layout.transform(ver));

            /* consider the case when mixing clusters together later */
            curComNodeCSet = flatClustToComNodeClust.get(vertex);
            pw.println("curComNodeCSet: " + curComNodeCSet);
            System.out.println("curComNodeCSet: " + curComNodeCSet);

            /* considered now if populate two tempCSet */

            while ((curComNodeCSet.size() == 1) && (curComNodeCSet.iterator().next() instanceof Set)) {
                /* for [[A,B]] flatClust, it will terminate immediately and do not run in this loop */
                Set tmpSet = curComNodeCSet.iterator().next();
                if ((tmpSet.iterator().next() instanceof BioObject)) {
                    countBioObjSet++;
                    break;
                }
                curComNodeCSet = tmpSet;
                pw.println("curComNodeCSet: " + curComNodeCSet);
                System.out.println("curComNodeCSet: " + curComNodeCSet);
            }
            setOfComNodeCSet.add(curComNodeCSet);
        }
        /* If not all pickedSet vertex contains BioObject directly,
         * Just treat them as normal flatClust
         * Set of one member containing BioObject is also treated normally.
         * I will not extract a member of such sets anymore
         */
        /* if not all sets contain BioObjects directly, then */
        if (countBioObjSet != vertexToProcessed.size()) {
            for (Object object : setOfComNodeCSet) {
                Set comNodeCSet = (Set) object;
                Iterator it = comNodeCSet.iterator();
                for (; it.hasNext();) {
                    Object obj = it.next();
//                    System.out.println("Obj " + obj);
                    /* TO-DO: may not be used, need to be confirmed */
                    if (obj instanceof BioObject) {
                        tempCSet.add(obj);
                        lastLevel = true;
                    } else {
                        tempCSet.add(comNodeClustToFlatClust.get(obj));
//                        tempCSet.addAll(comNodeClustToFlatClust.get(obj));
                    }

                }
                comNodeSet.addAll(comNodeCSet);
            }
        } /* Extract BioObjects of each flatClust and mix them together to get one larger flatClust
         */ else {
            for (Object object : setOfComNodeCSet) {
                Set comNodeCSet = (Set) object;
                /* assume that there is only one member (flatClust of bioObj)*/
                lastLevel = true;
                Set setOfBioObj = (Set) comNodeCSet.iterator().next();
                Iterator it = setOfBioObj.iterator();
                for (; it.hasNext();) {
                    Object obj = it.next();
                    pw.println("Obj " + obj);
                    System.out.println("Obj " + obj);
                    if (obj instanceof BioObject) {
                        tempCSet.add(obj);

                    } /* TO-DO: may not be used, need to be confirmed */ else {
                        tempCSet.add(comNodeClustToFlatClust.get(obj));
//                        tempCSet.addAll(comNodeClustToFlatClust.get(obj));
                    }
                }
                comNodeSet.addAll(setOfBioObj);
            }
        }

        /* Deal with new cluster flatClust, just leave it and build graph or re-louvain cluster it*/
        curCSet = tempCSet;
        curComNodeCSet = comNodeSet;
        curCSetBeforePropClust = curCSet;
        curComNodeCSetBeforePropClust = curComNodeCSet;

        Set<Set> cSet = tempCSet;
        ////DEBUG
//        printSortedCSet(cSet);
        System.out.println("cSet size when zooming: " + cSet.size());
        if (cSet.size() < Integer.parseInt(thresholdTextField.getValue().toString())) {
            pw.println("zoom in: LCs fewer than threshold");
            System.out.println("zoom in: LCs fewer than threshold");
            PropInfoProcessor gp = new PropInfoProcessor();
            Set tmpSet = new HashSet();

            int counter = 0;
            do {
                pw.println("\n===================");
                pw.println("split cluster round " + counter);
                pw.println("===================\n");
                System.out.println("\n===================");
                System.out.println("split cluster round " + counter);
                System.out.println("===================\n");
                splited = false;
                overallLessThanThresh = false;
                ArrayList<Set> pairOfClustSet = splitClusters(cSet);
                if (splited) {
                    cSet = pairOfClustSet.get(0);
                    curCSetBeforePropClust = cSet;
                    curCSet = cSet;
                    curComNodeCSetBeforePropClust = pairOfClustSet.get(1);
                    curComNodeCSet = pairOfClustSet.get(1);
                    pw.println("clusters splited!");
                    pw.println("cSet: " + cSet);
                    System.out.println("clusters splited!");
                    System.out.println("cSet: " + cSet);

                } else {
                    break;
                }
                if (cSet.size() >= Integer.parseInt(thresholdTextField.getValue().toString())) {
                    break;
                }
                if (overallLessThanThresh) {
                    break;
                }

//                if (comNodeClust != null) {
//                    if (comNodeClust == cSet)
//                        break;
//                    else{
//                        cSet = comNodeClust;
//                        System.out.println("clusters splited!");
//                    }
//                }
                counter++;
                if (counter == 10) {
                    break;
                }
            } while (true);

            gp.populatePropTerms(cSet);
            curGOP = gp;
            nodesPropVectorMap.putAll(putInNodesPropVectorMapRelatively(gp.getNodesPropVectorMapBeforeCluster(cSet)));
            if (lastLevel) {
//                viewhist.setIsLastLevel(true);
                /* 2011/5 Thanet    commented out according to Nalapro source code; it may not be real last level  */
//                zoomInButton.setEnabled(false);
                pw.println("cSet: " + cSet);
                System.out.println("cSet: " + cSet);
            }
        } else {// run property based clustering because number of LCs is larger than threshold
            StopWatch sw = new StopWatch();
            sw.start();
            ArrayList retList = reLouvainCluster(curCSetBeforePropClust, false);
            cSet = (Set) retList.get(0);
            
            ////DEBUG
//        printSortedCSet(cSet);
            cSet = propertyBasedCluster((Set) retList.get(0), (Set) retList.get(1), null, false, true, true);
            sw.stop();
            System.out.println("zoom in relouvain time "+sw.elapsedTimeMillis());
        }

        dynamicGraph = createGraph(cSet);
        usualNodeToClusterMap = new HashMap(nodeToClusterMap);

        layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * outerRadius);
//        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 1080);
//        ((CircleLayout) layout.getDelegate()).setRadius(0.30 * 600);
        vv.setGraphLayout(layout);

        layout = layoutGraph(layout, dynamicGraph);

        colorCluster(dynamicGraph.getVertices(), mainColor);
        currentLevelGraph = dynamicGraph;
        vv.repaint();
        curVertexLocMap = new HashMap<Object, Point2D>();
        for (Object ver : layout.getGraph().getVertices()) {
            curVertexLocMap.put(ver, layout.transform(ver));
        }
        curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);

        /* Deal with search function */
        Object[] searchedListArr = new Object[listModel.size()];
        listModel.copyInto(searchedListArr);
//            System.out.println("searchedList "+searchedListArr.length);
        Set settemp = new HashSet(Arrays.asList(searchedListArr));
        Set currentVertices = new HashSet();
        for (Object obj : dynamicGraph.getVertices()) {
            if (obj instanceof Set) {
                currentVertices.addAll((Set) obj);
            } else if (obj instanceof BioObject) {
                currentVertices.add(obj);
            }
        }

        currentVertices.retainAll(settemp);
        listModel.removeAllElements();

        for (Object obj : currentVertices) {
            listModel.addElement(obj);
        }

        if ((highlightedNode != null) && vertexToProcessed.contains(highlightedNode)) {
            focusNodeBySearch(searchedBioObj);
            searchResList.setSelectedValue(searchedBioObj, true);
        }

        /* clear picked vertex state */
        Set pickedNodes = new HashSet(vertexToProcessed);
        for (Object v : pickedNodes) {
            vv.getPickedVertexState().pick(v, false);
        }

        vv.repaint();
        saveState();
        return true;
    }

    /**
     * Listener for double-click on vertices
     * @param <V>
     * @param <E>
     */
    public class DoubleClickZoomInPlugin<V, E> extends AbstractGraphMousePlugin
            implements MouseListener {

        public DoubleClickZoomInPlugin() {
            this(MouseEvent.BUTTON1_DOWN_MASK);
        }

        public DoubleClickZoomInPlugin(int modifier) {
            super(modifier);
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();

                final VisualizationViewer<V, E> vv = (VisualizationViewer<V, E>) e.getSource();
                final Point2D p = e.getPoint();
                GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();
                if (pickSupport != null) {

                    final V vertex = pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());

                    if (vertex != null) {
                        pw.println("\nVertex picked: " + vertex);
                        System.out.println("\nVertex picked: " + vertex);

                        if (vertex instanceof Set) {
                            Set toZoomInAction = new HashSet();
                            toZoomInAction.add(vertex);
                            zoomInAction(toZoomInAction);
                        }
                    }
                }

            }
        }

        public void mouseEntered(MouseEvent e) {
            JComponent c = (JComponent) e.getSource();
            c.setCursor(cursor);
        }

        public void mouseExited(MouseEvent e) {
            JComponent c = (JComponent) e.getSource();
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public AggregateLayout layoutGraph(AggregateLayout layout, Graph graph) {

//        curVertexLocMap = new HashMap<Object, Point2D>();
//        for (Object ver : layout.getGraph().getVertices()) {
//            curVertexLocMap.put(ver, layout.transform(ver));
//            System.out.println(layout.transform(ver));
//        }

//        lay = new AggregateLayout(new FRLayout<Object, Object>(graph,new Dimension(550,550)));
//        ((FRLayout) lay.getDelegate()).setRepulsionMultiplier(2);
//        ((FRLayout) lay.getDelegate()).setAttractionMultiplier(4);

//        lay = new AggregateLayout(new FRLayout2<Object, Object>(graph,new Dimension(550,550)));
//        ((FRLayout2) lay.getDelegate()).setRepulsionMultiplier(2);
//        ((FRLayout2) lay.getDelegate()).setAttractionMultiplier(4);


//        MetaPropLengthFunction<Object> mplf = new MetaPropLengthFunction<Object>();
//        mplf.setMaxWeight(maxMetaEdgeWeight);

//        layout = new AggregateLayout(new SpringLayout<Object,Object>(dynamicGraph,mplf));
//        ((SpringLayout) layout.getDelegate()).setSize(new Dimension(500,500));
////        ((SpringLayout) layout.getDelegate()).setRepulsionRange(100);
//        ((SpringLayout) layout.getDelegate()).setForceMultiplier(70000);


//        layout = new AggregateLayout(new SpringLayout2<Object,Object>(dynamicGraph,mplf));
//        ((SpringLayout2) layout.getDelegate()).setSize(new Dimension(550,550));
//        //Repulsion is exerted all the time.
//        ((SpringLayout2) layout.getDelegate()).setRepulsionRange(1000);
//        ((SpringLayout2) layout.getDelegate()).setForceMultiplier(200000);
//        ((SpringLayout2) layout.getDelegate()).setMaxWeight(maxMetaEdgeWeight);

//        layout = new AggregateLayout(new FRLayout3<Object, Object>(dynamicGraph,new Dimension(550,550)));
//        ((FRLayout3) layout.getDelegate()).setRepulsionMultiplier(2);
//        ((FRLayout3) layout.getDelegate()).setAttractionMultiplier(4);
        nodesLabelList = chooseTwoTermsForLabel(graph);
        AggregateLayout lay = new AggregateLayout(new KKLayout(graph, new DijkstraDistance(graph, new Transformer<Object, Double>() {

            @Override
            public Double transform(Object edge) {
                if (edge instanceof BioEdge) {
                    double weight = ((BioEdge) edge).getWeight();
                    if (weight < 1) {
                        weight += 1;
                    }
                    return weight;
                } else if (edge instanceof MetaEdge) {
                    return (double) ((MetaEdge) edge).getNumEdgeSetBundled();
//                    return ((MetaEdge) edge).getWeightedNumEdges();
                }
//                else if (edge instanceof PropertyEdge){
//                    if (((PropertyEdge)edge).getInnerProduct() < 0.0)
//                        return 0.001;
//                    else
//                        return ((PropertyEdge)edge).getInnerProduct();
//
//                }
                return 1.0;
            }
        }, true)));
        ((KKLayout) lay.getDelegate()).setSize(new Dimension(canvasSizeWidth, canvasSizeHeight));
//        ((KKLayout) lay.getDelegate()).setSize(new Dimension(1080,1080));
//        ((KKLayout) lay.getDelegate()).setSize(new Dimension(1500,1000));
        ((KKLayout) lay.getDelegate()).setLengthFactor(1.14);
        ((KKLayout) lay.getDelegate()).adjustForGravity();
//        lay = new AggregateLayout(new NodeShapeFRLayout<Object, Object>(graph, new VertexShapeSizeAspect<Object, Object>(graph), new Dimension(550, 550)));
////        ((NodeShapeFRLayout) lay.getDelegate()).setRepulsionMultiplier(1.5);
//        ((NodeShapeFRLayout) lay.getDelegate()).setRepulsionMultiplier(1.4);
//        // the less the attraction multiplier is, the more the algorithm attracts vertices together.
//        ((NodeShapeFRLayout) lay.getDelegate()).setAttractionMultiplier(1.2);
////        ((NodeShapeFRLayout) lay.getDelegate()).setMaxIterations(100);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMaxIterations(10);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMaxMetaWeight(maxMetaEdgeWeight);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMinMetaWeight(minMetaEdgeWeight);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMaxEdgeWeight(maxBioEdgeWeight);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMinEdgeWeight(minBioEdgeWeight);
////        ((NodeShapeFRLayout) lay.getDelegate()).setNodeSize((int)(clusterSizeInLayout*(sizeMultiplier+0.2)));
//        ((NodeShapeFRLayout) lay.getDelegate()).setMaxNumMem(maxNumOfMem);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMinNumMem(minNumOfMem);
//        ((NodeShapeFRLayout) lay.getDelegate()).setMinNodeSize(clusterSizeInLayout);
//        ((NodeShapeFRLayout) lay.getDelegate()).setSizeMultiplier(sizeMultiplier);

        StopWatch sw = new StopWatch();
        sw.start();

//        lay.setInitializer(vv.getGraphLayout());
        lay.initialize();
//        vv.setGraphLayout(lay);

//        VisualizationViewer<Object, Object> vviewer = new VisualizationViewer<Object, Object>(lay, new Dimension(canvasSizeWidth, canvasSizeHeight));
        int i = 0;
        while (!lay.done() && (i < 150)) {
            lay.step();
            i++;
//            vv.getModel().getRelaxer().prerelax();
        }
        sw.stop();
        pw.println("Time used for multi-step layout: " + sw);
        System.out.println("Time used for multi-step layout: " + sw);
//        vv.getModel().getRelaxer().prerelax();
        AggregateLayout layout2 = new AggregateLayout(new StaticLayout<Object, Object>(graph, lay));
//        AggregateLayout layout2 = new AggregateLayout(new StaticLayout<Object, Object>(graph, vviewer.getGraphLayout()));
//        lay = layout2;


//        layout.setInitializer(new Transformer<Object, Point2D>() {
//
//            public Point2D transform(Object arg0) {
////                System.out.println(curVertexLocMap.get(arg0));
//                return curVertexLocMap.get(arg0);
//            }
//        });


//        vv.setGraphLayout(lay);
        vv.setGraphLayout(layout2);

        return lay;
    }
    /* Decoration and VisualizationViewer related zone */

    /**
     * Initialize visualization viewer of JUNG
     */
    private void initVV() {
        layout = new AggregateLayout(new CircleLayout<Object, Object>(dynamicGraph));
//        ((CircleLayout) layout.getDelegate()).setRadius(0.40 * 600);
        ((CircleLayout) layout.getDelegate()).setRadius(0.20 * outerRadius);
        //        ((CircleLayout) layout.getDelegate()).setRadius(0.20 * 1200);
//        ((CircleLayout) layout.getDelegate()).setRadius(0.20 * 1080);

//        layout = new AggregateLayout(new KKLayout(dynamicGraph, new DijkstraDistance(dynamicGraph, new Transformer<Object,Double>() {
//
//            @Override
//            public Double transform(Object edge) {
//                if (edge instanceof BioEdge)
//                    return ((BioEdge)edge).getWeight();
//                else if (edge instanceof MetaEdge)
//                    return ((MetaEdge)edge).getWeightedNumEdges();
////                else if (edge instanceof PropertyEdge){
////                    if (((PropertyEdge)edge).getInnerProduct() < 0.0)
////                        return 0.001;
////                    else
////                        return ((PropertyEdge)edge).getInnerProduct();
////
////                }
//                return 1.0;
//            }
//        }, true)));
//        ((KKLayout) layout.getDelegate()).setSize(new Dimension(550,550));
//        ((KKLayout) layout.getDelegate()).setLengthFactor(1.14);
//        ((KKLayout) layout.getDelegate()).adjustForGravity();


        vv = new VisualizationViewer<Object, Object>(layout, new Dimension(canvasSizeWidth, canvasSizeHeight));
//        vv = new VisualizationViewer<Object, Object>(layout,new Dimension(canvasSizeWidth+10, canvasSizeHeight+10));
//        vv = new VisualizationViewer<Object, Object>(layout,new Dimension(1500, 1100));
//        vv = new VisualizationViewer<Object, Object>(layout,new Dimension(1080, 1080));
        vv.setBackground(Color.white);

//        StopWatch sw = new StopWatch();
//        sw.start();
        layout = layoutGraph(layout, dynamicGraph);
//        sw.stop();
//        pw.println("Time used for lay out graph : " + sw);
//        System.out.println("Time used for lay out graph : " + sw);

        vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
        vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();

//        layout = new AggregateLayout(new FRLayout<Object,Object>(dynamicGraph));


        curVertexLocMap = new HashMap<Object, Point2D>();
        for (Object ver : layout.getGraph().getVertices()) {
            curVertexLocMap.put(ver, layout.transform(ver));
//            System.out.println("vertex position: "+layout.transform(ver));
        }
        curUsualVertexLocMap = new HashMap<Object, Point2D>(curVertexLocMap);
//            System.out.println("vv "+vv.getSize());
//        DefaultParallelEdgeIndexFunction dpeif = DefaultParallelEdgeIndexFunction.getInstance();
//        vv.getRenderContext().setParallelEdgeIndexFunction(dpeif);

//        vv.scaleToLayout(new CrossoverScalingControl());
//        vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabeller(ew));
        vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabeller());
//        vv.getRenderContext().setEdgeLabelClosenessTransformer(null);


        medp = new EdgeDisplayPredicate();
//            vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Object,Object>());
        ((AbstractEdgeShapeTransformer<Object, Object>) vv.getRenderContext().getEdgeShapeTransformer()).setControlOffsetIncrement(35);
//        ((AbstractEdgeShapeTransformer<Object,Object>)vv.getRenderContext().getEdgeShapeTransformer()).setControlOffsetIncrement(70);
        vv.getRenderContext().setEdgeIncludePredicate(medp);

        vv.setVertexToolTipTransformer(new NumMemPropTermTips());
        vv.getRenderContext().setVertexLabelTransformer(new PropInfoStringLabeller());
        vv.getRenderContext().setVertexFontTransformer(new VertexFontTransformer());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
//            vv.getRenderContext().setVertexShapeTransformer(new VertexShapeSizeAspect<Object, Object>(realGraph));

        vv.getRenderContext().setVertexFillPaintTransformer(MapTransformer.<Object, Paint>getInstance(vertexPaints));

        vv.getRenderContext().setVertexDrawPaintTransformer(new Transformer<Object, Paint>() {

            public boolean evaluateEdge(Object e) {
//            Graph<V, E> graph = context.graph;
//            if ((e instanceof MetaEdge) || (isLowestLevel)) {
                if (e instanceof MetaEdge) {
                    return true;
                }
                if (e instanceof PropertyEdge) {
                    double val = ((PropertyEdge) e).getInnerProduct();
                    EdgeDisplayPredicate edp = (EdgeDisplayPredicate) vv.getRenderContext().getEdgeIncludePredicate();
                    if ((val > 0) && (val >= edp.getThresholdForSE())) {
                        return true;
                    } else {
                        return false;
                    }

                }

                return true;
            }

            public Paint transform(Object v) {
                if (vv.getPickedVertexState().isPicked(v)) {
//                    return Color.RED;
//                    return new Color(0x003DF5); //Blue
                    return new Color(0xF5003D); //Red
//                    return new Color(0xFF70DB); //Pink
//                    return new Color(0x5CFFFF);
//                    return new Color(0xFF8533);
//                    return new Color(0xFF00FF);
//                    return new Color(0xCCFF33);
//                    return new Color(0xFF33CC);
//                    return new Color(0x33FFCC);
                } else {
                    Color forNeighbor = new Color(0xFF33CC);
                    //There are two types of edges, so getNeighbors alone is not enough.
                    for (Object w : dynamicGraph.getNeighbors(v)) {
                        for (Object e : dynamicGraph.findEdgeSet(v, w)) {
                            if (e instanceof MetaEdge) {
                                if (vv.getPickedVertexState().isPicked(w)) {
                                    return forNeighbor;
                                }
                            } else if (e instanceof PropertyEdge) {
                                if (evaluateEdge(e)) {
                                    if (vv.getPickedVertexState().isPicked(w)) {
                                        return forNeighbor;
                                    }
                                }
                            } else if (e instanceof BioEdge) {
                                if (vv.getPickedVertexState().isPicked(w)) {
                                    return forNeighbor;
                                }
                            }
                        }


////                    for (Iterator iter = graph.getNeighbors(v)v.getNeighbors().iterator(); iter.hasNext(); )
////                    {
////                        Vertex w = (Vertex)iter.next();
//                    if (vv.getPickedVertexState().isPicked((V)w)) {
//                        return heavy;
//                    }
                    }
                    return Color.BLACK;
                }
            }
        });
        vv.getRenderContext().setVertexStrokeTransformer(new VertexWeightStrokeFunction());
        vv.getRenderContext().setEdgeFontTransformer(new EdgeFontTransformer());

//        vv.getRenderContext().setEdgeDrawPaintTransformer(MapTransformer.<Object, Paint>getInstance(edgePaints));
//        vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction(ew));
        vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction());

        vv.getRenderContext().setEdgeDrawPaintTransformer(
                new PickedEdgePaintFunction<Object, Object>(new PickableEdgePaintTransformer<Object>(vv.getPickedEdgeState(), Color.black, new Color(0xF5003D)), vv));

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        vv.setGraphMouse(gm);
        gm.setMode(Mode.PICKING);
        gm.add(new DoubleClickZoomInPlugin<Object, Object>());

        gm.add(new PopupGraphMousePlugin());

        vv.getRenderContext().setVertexShapeTransformer(new VertexShapeSizeAspect<Object, Object>(dynamicGraph));
//        getContentPane().add(new GraphZoomScrollPane(vv));

    }

    private void colorCluster(Collection vertices, Color c) {
        for (Object v : vertices) {
            vertexPaints.put(v, c);
        }

    }

    /**
     * This method provides label(s) for each nodes
     * In case the highest-score term of any given node is not in duplicate with others', it provides only one label
     * If not, it tries to choose the second term that is not in duplicate with others'
     * If it cannot provide the second terms that are not duplicate with each other at all,
     * it provides the second terms of the highest rank in which there are least number of duplicates.
     *
     * EX. 1
     * Node:    1   2   3   4
     * Rank 1   A   A   A   A
     * Rank 2   B   B   C   C
     * Rank 3   D   D   B   E
     * In this case, it chooses the terms of rank 3 because there are only 1 duplicate of D,
     * while the rank 2 has 2 duplicates (1 of B and C each).
     * 
     * EX. 2
     * Node:    1   2   3   4
     * Rank 1   A   A   A   A
     * Rank 2   B   B   D   C
     * Rank 3   D   D   B   E
     * In this case, it chooses the terms of rank 2 because there are 1 duplicate of B and rank 2 is higher than
     * rank 3.
     *
     * EX. 3
     * Node:    1   2   3   4
     * Rank 1   A   A   A   A
     * Rank 2   B   B   D   C
     * Rank 3   D   D   B   E
     * Rank 4   E   G   C   D
     * In this case, it chooses the terms of rank 4 because there are no duplicates at all.
     *
     * EX. 4
     * Node:    1   2   3   4
     * Rank 1   A   A   A   A
     * Rank 2   B   B   D   C
     * Rank 3   D   D   B   E
     * Rank 4   E   G   C
     * In this case, it chooses the terms of rank 2 because there are 1 duplicate of B and rank 2 is higher than
     * rank 3. Although the rank 4 seems to contain no duplicates, node 4 has no label in that rank.
     * This method tries to provide labels that are most informative, so it will stop if it finds that some nodes have
     * no label in any given rank. Then, it will return the most appropriate labels.
     *
     * NOTE: For nodes that are instances of Set only.
     * @param graph
     * @return map of nodes and their labels
     */
    public Map<Object, ArrayList<PropertyTerm>> chooseTwoTermsForLabel(Graph graph) {
        Map<Object, Set<Entry<String, Double>>> sortedNodesPropScoreMap = new HashMap<Object, Set<Entry<String, Double>>>();
//        System.out.println("currentViewHisPos "+currentViewHisPos);
//        if (currentViewHisPos > -1)
//            nodesPropVectorMap = viewHistoryList.get(currentViewHisPos).getNodesPropVectorMap();
        for (Object vertex : graph.getVertices()) {

            if (vertex instanceof Set) {
                DataVector vec = nodesPropVectorMap.get(vertex);
                if (vec != null) {
                    Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            Entry<String, Double> e1 = (Entry<String, Double>) o1;
                            Entry<String, Double> e2 = (Entry<String, Double>) o2;
                            if (e1.getValue() > e2.getValue()) {
                                return -1;
                            } else if (e1.getValue() < e2.getValue()) {
                                return 1;
                            } else {
                                PropertyTerm p1 = PropInfoProcessor.propTermsMap.get(e1.getKey());
                                PropertyTerm p2 = PropInfoProcessor.propTermsMap.get(e2.getKey());
                                if (p1.getWeight() > p2.getWeight()) {
                                    return -1;
                                } else if (p1.getWeight() < p2.getWeight()) {
                                    return 1;
                                } else {
                                    String name1 = PropInfoProcessor.propTermsMap.get(e1.getKey()).getName();
                                    String name2 = PropInfoProcessor.propTermsMap.get(e2.getKey()).getName();
                                    return name1.compareTo(name2);
                                }

                            }
//                            else {
//                            return 0;
//                            }
                        }
                    });

                    sortedMap.addAll(vec.getValueMap().entrySet());
                    sortedNodesPropScoreMap.put(vertex, sortedMap);
                } else {
                    sortedNodesPropScoreMap.put(vertex, new TreeSet<Entry<String, Double>>());
                }
            }
        }

//        System.out.println("sortedNodes "+sortedNodesPropScoreMap.size());
//        Set<Map<Object,ArrayList<String>>> processed = new HashSet<Map<Object,ArrayList<String>>>();
        Set<Object> processed = new HashSet<Object>();
        Map<PropertyTerm, ArrayList> duplicate = new HashMap<PropertyTerm, ArrayList>();
        Map<Object, ArrayList<PropertyTerm>> nodesLabelList = new HashMap<Object, ArrayList<PropertyTerm>>();

        //Process the first term of each node: the highest score term
        for (Object vertex : sortedNodesPropScoreMap.keySet()) {
            Set<Entry<String, Double>> propScoreMap = sortedNodesPropScoreMap.get(vertex);
            ArrayList<PropertyTerm> stList = new ArrayList<PropertyTerm>();
            nodesLabelList.put(vertex, stList);

            if (propScoreMap.size() == 0) {
                stList.add(null);
                continue;
            } else {
//                System.out.println("------");
//                int ind = 0;
//                for (Entry<String,Double> entry : propScoreMap){
//                    System.out.println("term "+PropInfoProcessor.propTermsMap.get(entry.getKey()).getName()+" score "+entry.getValue());
//                    ind++;
//                    if (ind == 10)
//                        break;
//                }
                Iterator<Entry<String, Double>> iter = propScoreMap.iterator();

                PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) iter.next().getKey());
//                System.out.println("propTerm "+propTerm.getName());
//                PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) propScoreMap.iterator().next().getKey());
//                while ((iter.hasNext()) && (((propTerm.getName().equalsIgnoreCase("molecular_function")) || (propTerm.getName().equalsIgnoreCase("cellular_component"))
//                        || (propTerm.getName().equalsIgnoreCase("biological_process"))))){
////                    iter.next();
//                    propTerm = PropInfoProcessor.propTermsMap.get((String) iter.next().getKey());
////                    System.out.println("propTerm "+propTerm.getName());
//                }

                //do not show molecular function term because it's too broad.
//                if ((propTerm.getName().equalsIgnoreCase("molecular_function")) || (propTerm.getName().equalsIgnoreCase("cellular_component"))
//                        || (propTerm.getName().equalsIgnoreCase("biological_process")))
//                    {
//                    Iterator<Entry<String, Double>> iter = propScoreMap.iterator();
//                        System.out.println("iter: "+iter.next());
//                    propTerm = PropInfoProcessor.propTermsMap.get((String) iter.next().getKey());
//                    System.out.println("PropertyTerm: "+propTerm);
//                }
                stList.add(propTerm);
//                System.out.println("");
//                for (Entry<String,Double> entry :propScoreMap)
//                {
//                    PropertyTerm term = PropInfoProcessor.propTermsMap.get(entry.getKey());
//                    System.out.println("term "+term.getName());
//                }
//                System.out.println("-------------");
//                System.out.println("vertex term "+propTerm.getName());
            }


            boolean notDuplicate = true;
            for (Object verToCompare : processed) {

                if (stList.get(0).getName().equals(nodesLabelList.get(verToCompare).get(0).getName())) {
                    if (duplicate.get(stList.get(0)) == null) {
                        ArrayList list = new ArrayList();
                        duplicate.put(stList.get(0), list);
                        list.add(vertex);
                        list.add(verToCompare);
                    } else {
                        duplicate.get(stList.get(0)).add(vertex);
                    }
                    notDuplicate = false;
                    break;
                }

            }
            if (notDuplicate) {
                processed.add(vertex);
            }
        }

//        System.out.println("Processed "+processed.size());
//        for (PropertyTerm term : duplicate.keySet()){
//            System.out.println("key "+term.getName()+" num "+duplicate.get(term).size());
//        }
        /* Select the second term for each node whose first term is duplicate with others'
         * Try to find the terms in the same level which are not duplicate with others at all
         */
        for (Entry<PropertyTerm, ArrayList> entry : duplicate.entrySet()) {
            ArrayList list = entry.getValue();
//            System.out.println("entry key "+entry.getKey().getName());
            int i = 1; // start from the second highest score term
            boolean stop = false;
            while (!stop) {
                int dupCount = 0, maxDupCount = Integer.MAX_VALUE;
                boolean foundNull = false;
                Set<PropertyTerm> termSet = new HashSet<PropertyTerm>();
                ArrayList<PropertyTerm> tempList = new ArrayList<PropertyTerm>();
                for (Object vertex : list) {
//                Set<Entry<String,Double>> propScoreMap = sortedNodesPropScoreMap.get(vertex);
                    Entry<String, Double>[] propScoreMap = (Entry<String, Double>[]) sortedNodesPropScoreMap.get(vertex).toArray(new Entry[0]);
//                    System.out.println("TEST: i "+i);
                    if (propScoreMap.length <= 1) {
//                        nodesLabelList.get(vertex).add(null);
                        foundNull = true;
                        tempList.add(null);

                    } else if (propScoreMap.length <= i) {
//                    nodesLabelList.get(vertex).add(null);
                        tempList.add(null);
                        foundNull = true;
                        stop = true;
//                        dupCount++;
                    } else {
//                        System.out.println("TEST: propScore length "+propScoreMap.length);
//                        for (int ii = 0; ii < propScoreMap.length; ii++)
//                            System.out.println("TEST: prop score "+propScoreMap[ii]);
                        if (propScoreMap[i].getValue() <= 0.00000001) {
                            tempList.add(null);
                            foundNull = true;
                            stop = true;
                        } else {
                            PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) propScoreMap[i].getKey());
                            if (propTerm != null) {

                                tempList.add(propTerm);
                                if (!termSet.contains(propTerm)) {
                                    termSet.add(propTerm);
                                } else {
                                    dupCount++;
                                }
                            } else {
                                System.out.println("prop term " + propScoreMap[i].getKey());
                            }

                        }
                    }
                }
//                System.out.print("Round " + i + " DupCount " + dupCount + " TermSet: ");
//                for (PropertyTerm t : tempList) {
//                    if (t != null)
//                        System.out.print(t.getName() + "|||");
//                    else
//                        System.out.print("\t|||");
//                }
//                System.out.println("");
                if (i == 1) {
                    int k = 0;
                    for (Object vertex : list) {
                        nodesLabelList.get(vertex).add(tempList.get(k++));
                    }
                } else if (dupCount < maxDupCount) {
                    int k = 0;
                    for (Object vertex : list) {
                        if (tempList.get(k) != null) {
                            nodesLabelList.get(vertex).set(1, tempList.get(k++));
                        } else {
                            Entry<String, Double>[] propScoreMap = (Entry<String, Double>[]) sortedNodesPropScoreMap.get(vertex).toArray(new Entry[0]);
                            if (propScoreMap.length > 1) {
                                nodesLabelList.get(vertex).set(1, PropInfoProcessor.propTermsMap.get((String) propScoreMap[1].getKey()));
                            } else {
                                nodesLabelList.get(vertex).set(1, null);
                            }
                            k++;
                        }
                    }
                }
                if (dupCount == 0) {
                    stop = true;

                }
                i++;
            }
        }
//        for (ArrayList<PropertyTerm> list : nodesLabelList.values()){
//            System.out.print(list.get(0).getName()+"||");
//            if (list.size() > 1)
//                System.out.println(list.get(1).getName());
//            else
//                System.out.println("");
//        }
//        System.out.println("duplicate "+duplicate);
        return nodesLabelList;
    }

    /** Tool tip generating method.
     *  If vertex is a flatClust, generate #nodes inside, along with list of property terms and score.
     *  If vertex is a bioObject, generate list of property terms and score.
     */
    public class NumMemPropTermTips<V, E> implements Transformer<V, String> {

        public String transform(V vertex) {
            String res = "<html>";

            if (vertex instanceof Set) {
                res += "Num Nodes Inside: " + ((Set) vertex).size();
            }
//            System.out.println("currentViewHispos "+currentViewHisPos);
//            nodesPropVectorMap = viewHistoryList.get(currentViewHisPos).getNodesPropVectorMap();
            DataVector vec = nodesPropVectorMap.get(vertex);
//            System.out.println("vec "+vec);
//            System.out.println("vec "+viewHistoryList.get(currentViewHisPos).getNodesPropVectorMap().get(vertex));

            if (vec != null) {
                Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Entry<String, Double> e1 = (Entry<String, Double>) o1;
                        Entry<String, Double> e2 = (Entry<String, Double>) o2;
                        if (e1.getValue() > e2.getValue()) {
                            return -1;
                        } else if (e1.getValue() < e2.getValue()) {
                            return 1;
                        } else {
                            PropertyTerm p1 = PropInfoProcessor.propTermsMap.get(e1.getKey());
                            PropertyTerm p2 = PropInfoProcessor.propTermsMap.get(e2.getKey());
                            if (p1.getWeight() > p2.getWeight()) {
                                return -1;
                            } else if (p1.getWeight() < p2.getWeight()) {
                                return 1;
                            } else {
                                String name1 = PropInfoProcessor.propTermsMap.get(e1.getKey()).getName();
                                String name2 = PropInfoProcessor.propTermsMap.get(e2.getKey()).getName();
                                return name1.compareTo(name2);
                            }

                        }
//                        else {
//                        return 0;
//                        }
                    }
                });


                sortedMap.addAll(vec.getValueMap().entrySet());

                int i = 0;
                for (Entry entry : sortedMap) {

                    PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) entry.getKey());
                    String toPrint = propTerm.getName();
//                    System.out.println("entry "+entry.getKey()+" value "+entry.getValue());
//                    if ((toPrint.equalsIgnoreCase("molecular_function")) || (toPrint.equalsIgnoreCase("biological_process"))
//                            || (toPrint.equalsIgnoreCase("cellular_component"))) {
//                        continue;
//                    }
//                        if (toPrint.length() > 20) {
//                            toPrint = toPrint.substring(0, 20);
//                            toPrint += "...";
//                        }

                    DecimalFormat myFormatter = new DecimalFormat(".000");
                    String output = myFormatter.format(entry.getValue());

                    res += "<p>";
                    if (Double.compare((Double) entry.getValue(), 0.0000000001D) < 0) {
                        res += "<font color = #7A7A7A>";
                    } else {
                        NameSpace ns = PropInfoProcessor.getNamespaceMap().get(propTerm.getNamespace().getName());

                        res += "<font color = rgb(" + ns.getColor().getRed() + "," + ns.getColor().getGreen() + "," + ns.getColor().getBlue() + ")>";
//                        if (propTerm.getNamespace().equals(NameSpace.CC)) {
//                            res += "<font color = #ED143F>";
//                        } else if (propTerm.getNamespace().equals(NameSpace.MF)) {
//                            res += "<font color = #338F06>";
//                        } else {
//                            res += "<font color = #3425B1>";
//                        }
                    }
                    res += toPrint + " (" + output + ")";
                    res += "</font>";

                    i++;
                    /* list up to 7 terms */
                    if (i == 10) {
                        break;
                    }

                }
                if (i == 0) {
                    if (vertex instanceof Set) {
                        res += "<br>";
                    }
                    res += "<i>No property information available.</i>";
                }
            } else {
//                if (vertex instanceof BioObject) {
////                    BioObject bio = (BioObject) vertex;
////                    ArrayList<String> goidList = bio.getPropTermList();
////                    int i = 0;
////                    for (String s : goidList) {
////                        res += "<p>" + s;// + " with " + String.format("%.3f", entry.getValue());
////                        i++;
////                        if (i == 10) {
////                            break;
////                        }
////                    }
//                    res += "<i>No GO terms information available.</i>";
//                }
                res += "<i>No property information available.</i>";
            }

            res += "</html>";
            return res;
        }
    }

    /** This class specify what to be written on each node
     *  If vertex is a flatClust, write top three high-score property terms, or else write the name of vertex.
     * It also tries to split the word at preferable position, such as split at spaces between words, etc.
     */
    public class PropInfoStringLabeller extends ToStringLabeller {

        int charPerLine = 12;

        /**
         * This method is used for producing one-line label.
         * If the full name of property term can be written in one line, it will show as is.
         * If not, it will use the short name instead, and use ellipsis (...) to indicate that the name does not end.
         * @param propTerm
         * @param charsPerLine
         * @return one-line label
         */
        public String oneLineLabeller(PropertyTerm propTerm, int charsPerLine) {
            String st = "";
            if (propTerm != null) {
                st = propTerm.getName();
                char[] charArr = st.toCharArray();
                int upper = 0, lower = 0, paren = 0;
                int percent = 0, ampersand = 0, punc = 0, space = 0;
                int len = st.length();
                if (len > charsPerLine) {
                    len = charsPerLine;
                }
                for (int i = 0; i < len; i++) {
                    if (Character.isUpperCase(charArr[i])) {
                        upper++;
                    } else if (Character.isLowerCase(charArr[i])) {
                        lower++;
                    } else if ((charArr[i] == ')') || (charArr[i] == '(') || (charArr[i] == ']') || (charArr[i] == '[')) {
                        paren++;
                    } else if (charArr[i] == '%') {
                        percent++;
                    } else if (charArr[i] == '&') {
                        ampersand++;
                    } else if (Character.isSpaceChar(charArr[i])) {
                        space++;
                    } else {
                        punc++;
                    }
                }
//                System.out.println("propTerm "+propTerm.getName());
//                System.out.println("charperline first "+charsPerLine);
                int realSpace = (int) (lower + space + (double) 7.0 / 5.0 * upper + (double) 4.0 / 7.0 * punc + (double) 4.0 / 6.0 * paren
                        + (double) 9.0 / 6.0 * ampersand + (double) 10.0 / 6.0 * percent);
                if (realSpace > charsPerLine) {
                    charsPerLine -= (realSpace - charsPerLine);
//                    System.out.println("change!! \n");
                }

//                System.out.println("u "+upper+" l "+lower+" s "+space);
//                charsPerLine = (int) (lower + space + (double) 5.0 / 7.0 * upper + (double) 7.0 / 4.0 * punc + (double) 6.0 / 4.0 * paren +
//                        (double) 6.0 / 9.0 * ampersand + (double) 6.0 / 10.0 * percent);
//                System.out.println("realSpace later "+realSpace);
//                System.out.println("charperline later "+charsPerLine);

            }
            if (st.length() <= charsPerLine) {
                return st;
            } else {

                ArrayList<String> arrOfSt = new ArrayList<String>();
//                int[] lenArrOfSt;// = new int[arrOfSt.size()];
                String result = "";
                arrOfSt = new ArrayList<String>(Arrays.asList(propTerm.getShortName().split("\\s")));
//                lenArrOfSt = new int[arrOfSt.size()];
//                arrOfSt = lookUpInDict(st);
//                lenArrOfSt = new int[arrOfSt.size()];
                StringBuilder stb = new StringBuilder();
                int lengthOfSt = 0;
                for (int i = 0; i < arrOfSt.size(); i++) {
//                    lenArrOfSt[i] = arrOfSt.get(i).length();
                    if (i > 0) {
                        if (!arrOfSt.get(i).equals(",") && !arrOfSt.get(i).equals("/")
                                && !arrOfSt.get(i).equals(":") && !arrOfSt.get(i).equals("-") && !arrOfSt.get(i).equals(" ")
                                && (!arrOfSt.get(i - 1).equals(",") && !arrOfSt.get(i - 1).equals("/")
                                && !arrOfSt.get(i - 1).equals(":") && !arrOfSt.get(i - 1).equals("-") && !arrOfSt.get(i - 1).equals(" "))) {
                            stb.append(" ");
                            lengthOfSt += 1;
                        }
                    }
                    stb.append(arrOfSt.get(i));
                    lengthOfSt += arrOfSt.get(i).length();//lenArrOfSt[i];
                }
//                lengthOfSt--;
//                stb.delete(stb.length() - 1, stb.length());
                result = stb.toString();

                // after looking up, if the result length fits in one line.
                if (lengthOfSt <= charsPerLine) {
                    return result;
                } else {
                    return result.substring(0, charsPerLine - 3) + "...";
                }
            }
        }

        /**
         * This method composes words into each line of label separated by the html tag <br>.
         * It cares of the beauty of the word arrangement, such as spacing, word cutting, etc.
         * Also, it tries to make use of spaces as much efficient as possible.
         * @param arrOfSt
         * @param charsPerLine
         * @return string of words delimited by <br> used to indicate new line separation
         */
        public String composeWordsWithCare(ArrayList<String> arrOfSt, int charsPerLine) {
            String res = "";
            int upToNow = 0;
            int i = 0;
//            System.out.println("COMPOSE");
//            System.out.println("charperline "+charsPerLine);
            String[] punctuation = {",", "-", "/", ":"};
            ArrayList<String> arrPunc = new ArrayList<String>(Arrays.asList(punctuation));
            for (String word : arrOfSt) {
//                System.out.println("word " + word);
                if (word.equals(" ") || word.equals("")) {
                    i++;
                    continue;
                }
                // if new word fits in the current line, add it
                if (upToNow + word.length() <= charsPerLine) {
                    res += word;
                    upToNow += word.length();
//                    System.out.println("res " + res + " uptonow " + upToNow);

                    //if the current word and next word are not punctuation marks, add a space
                    if ((!arrPunc.contains(word)) && (i + 1 < arrOfSt.size())) {
//                    if (!(arrOfSt.get(i+1).equals("/")) || !(arrOfSt.get(i+1).equals(".")) ||
//                            !(arrOfSt.get(i+1).equals(":")) || !(arrOfSt.get(i+1).equals("-"))) {
                        if (!arrPunc.contains(arrOfSt.get(i + 1))) {
                            if ((upToNow + 1) == charsPerLine) {
                                upToNow++;
//                                System.out.println("res "+res+ " uptonow "+upToNow);
                            } else if ((upToNow + 1 < charsPerLine)) {
                                res += " ";
                                upToNow++;
//                                System.out.println("res " + res + " uptonow " + upToNow);
                            }
                        }
                    }

                } else {
                    /* it the length of the current line is less than 5, the current word should still be added on this line
                     *  to optimize space utilization.
                     * However, if the current word is too short, do not split it because it will be harder to read.
                     */
                    if ((upToNow <= 5) && (word.length() >= 5) && (charsPerLine - upToNow >= 3)) {
                        int prefixLen = 2;
                        // try to avoid separating many characters and one character like "RNA Processin<BR>g"
                        // instead, force it to split like "RNA Processi<BR>ng"
                        // The number of characters in the prefix can be adjusted by prefixLen
                        if (word.length() - (charsPerLine - upToNow) >= prefixLen) {
                            res += word.substring(0, charsPerLine - upToNow) + "-<br>";
                            res += word.substring(charsPerLine - upToNow);
                            upToNow = word.length() - (charsPerLine - upToNow);
//                            System.out.println("res " + res + " uptonow " + upToNow);
                        } else {
                            res += word.substring(0, word.length() - prefixLen) + "-<br>";
                            res += word.substring(word.length() - prefixLen);
                            upToNow = prefixLen;
//                            System.out.println("res " + res + " uptonow " + upToNow);
                        }
                        // add a space if not punctuation mark
                        if ((!arrPunc.contains(word)) && (i + 1 < arrOfSt.size())) {
//                    if (!(arrOfSt.get(i+1).equals("/")) || !(arrOfSt.get(i+1).equals(".")) ||
//                            !(arrOfSt.get(i+1).equals(":")) || !(arrOfSt.get(i+1).equals("-"))) {
                            if (!arrPunc.contains(arrOfSt.get(i + 1))) {
                                if ((upToNow + 1 <= charsPerLine)) {
                                    res += " ";
                                    upToNow++;
//                                    System.out.println("res " + res + " uptonow " + upToNow);
                                }
                            }
                        }
                    } // or else enter new line, and add the word
                    else {
                        if (res.endsWith(" ")) {
                            res = res.substring(0, res.length() - 1);
                        }
                        if (!res.equals("")) {
                            res += "<br>";
                        }
                        if (word.length() <= charsPerLine) {
                            res += word;
                            upToNow = word.length();
                        } else {
                            res += word.substring(0, charsPerLine);
                            res += "-<br>" + word.substring(charsPerLine);
                            upToNow = word.length() - charsPerLine;
                        }
//                        System.out.println("res " + res + " uptonow " + upToNow);

                        //add a space if not punctuation marks
                        if ((!arrPunc.contains(word)) && (i + 1 < arrOfSt.size())) {
//                    if (!(arrOfSt.get(i+1).equals("/")) || !(arrOfSt.get(i+1).equals(".")) ||
//                            !(arrOfSt.get(i+1).equals(":")) || !(arrOfSt.get(i+1).equals("-"))) {
                            if (!arrPunc.contains(arrOfSt.get(i + 1))) {
                                if ((upToNow + 1 <= charsPerLine)) {
                                    res += " ";
                                    upToNow++;
//                                    System.out.println("res " + res + " uptonow " + upToNow);
                                }
                            }
                        }
                    }
                }

                // if the length equal to no. of characters allowed in one line, add <BR> to enter a new line
                if (upToNow == charsPerLine) {
                    upToNow = 0;
                    res += "<br>";
//                    System.out.println("res " + res + " uptonow " + upToNow);
                }
                i++;

            }

            //if it ends with <BR> sharply (no more characters after this), remove this symbol to make it two-line label.
            if (upToNow == 0) {
                res = res.substring(0, res.length() - "<br>".length());
            }


            return res;

//            }


        }

        /**
         * This method process each property term by finding the most suitable label for the term.
         * Generally, it make use of composeWordsWithCare and shortName of property terms to find the best label.
         * It cares of beauty, readability, space utilization of each label.
         *
         * NOTE: some "if"s might overlap each other. But for now, I do not modify them because it works already.
         * I may change them in the future if I can be sure that the "if"s can be combined.
         * @param propTerm
         * @param charsPerLine
         * @return processed label
         */
        public String processLabeller(PropertyTerm propTerm, int charsPerLine) {
            String st = "";
            if (propTerm != null) {
                st = propTerm.getName();
                // work for only two lines, not more than two lines!!!!
                char[] charArr = st.toCharArray();
                int upper = 0, lower = 0, paren = 0;
                int percent = 0, ampersand = 0, punc = 0, space = 0;
                int len = st.length();
                if (len > charsPerLine) {
                    len = charsPerLine;
                }
                for (int i = 0; i < len; i++) {
                    if (Character.isUpperCase(charArr[i])) {
                        upper++;
                    } else if (Character.isLowerCase(charArr[i])) {
                        lower++;
                    } else if ((charArr[i] == ')') || (charArr[i] == '(') || (charArr[i] == ']') || (charArr[i] == '[')) {
                        paren++;
                    } else if (charArr[i] == '%') {
                        percent++;
                    } else if (charArr[i] == '&') {
                        ampersand++;
                    } else if (Character.isSpaceChar(charArr[i])) {
                        space++;
                    } else {
                        punc++;
                    }
                }
//                System.out.println("propTerm "+propTerm.getName());
//                System.out.println("charperline first "+charsPerLine);
                int realSpace = (int) (lower + space + (double) 7.0 / 5.0 * upper + (double) 4.0 / 7.0 * punc + (double) 4.0 / 6.0 * paren
                        + (double) 9.0 / 6.0 * ampersand + (double) 10.0 / 6.0 * percent);
                if (realSpace > charsPerLine) {
                    charsPerLine -= (realSpace - charsPerLine);
//                    System.out.println("change!! \n");
                }

//                System.out.println("u "+upper+" l "+lower+" s "+space);
//                charsPerLine = (int) (lower + space + (double) 5.0 / 7.0 * upper + (double) 7.0 / 4.0 * punc + (double) 6.0 / 4.0 * paren +
//                        (double) 6.0 / 9.0 * ampersand + (double) 6.0 / 10.0 * percent);
//                System.out.println("realSpace later "+realSpace);
//                System.out.println("charperline later "+charsPerLine);
            }
//            StringTokenizer stn = new StringTokenizer(st);
            ArrayList<String> arrOfSt = new ArrayList<String>();
            int[] lenArrOfSt;// = new int[arrOfSt.size()];
            //fit in one line
//            System.out.println("\nstring: " + st + " charsPerLine " + charsPerLine);

            //if the name of the term fits in one line, just return it as is.
            if (st.length() <= charsPerLine) {
//                System.out.println("case 1");
                return st;
            } // if it fits in two lines, ...
            else if (st.length() <= charsPerLine * 2) {
//                System.out.println("case 2");
//                System.out.println(st);

                /*
                 * First, try to compose words with care. if the result fits in two lines, use it.
                 * In this case, ignore the short name.
                 */
                arrOfSt = new ArrayList<String>(Arrays.asList(st.split("\\s")));
                lenArrOfSt = new int[arrOfSt.size()];
//                System.out.println("arrofst " + arrOfSt);
                for (int i = 0; i < arrOfSt.size(); i++) {
                    lenArrOfSt[i] = arrOfSt.get(i).length();
                }


                String resultComposed = composeWordsWithCare(arrOfSt, charsPerLine);
//                System.out.println("resultComposed " + resultComposed);
                int start = resultComposed.indexOf("<br>");
                int count = 0, secondBr = -1, firstBr = -1;
                while (start != -1) {

                    if (count == 0) {
                        firstBr = start;
                    }
                    if (count == 1) {
                        secondBr = start;
                    }
                    start = resultComposed.indexOf("<br>", start + "<br>".length());
                    count++;
                }
                if (count < 2) {
//                    if ((resultComposed.length()-"<br>".length()*count) <= charsPerLine*2){
//                    System.out.println("case 2.1");
//                    if (secondBr != -1) {
//                        return resultComposed.substring(0, secondBr - 3) + "...";
//                    } else {
                    return resultComposed;
//                    }
                } /* Or else use the short name of the term instead.
                 * If using the short name results in fitting in one line, use it.
                 * If not, compose the words with care.
                 */ else {
//                    System.out.println("case 2.2");
                    arrOfSt = new ArrayList<String>(Arrays.asList(propTerm.getShortName().split("\\s")));
                    lenArrOfSt = new int[arrOfSt.size()];
//                    arrOfSt = lookUpInDict(st);
//                    lenArrOfSt = new int[arrOfSt.size()];
                    StringBuilder stb = new StringBuilder();
                    int lengthOfSt = 0;
                    for (int i = 0; i < arrOfSt.size(); i++) {
                        lenArrOfSt[i] = arrOfSt.get(i).length();
                        if (i > 0) {
                            if (!arrOfSt.get(i).equals(",") && !arrOfSt.get(i).equals("/")
                                    && !arrOfSt.get(i).equals(":") && !arrOfSt.get(i).equals("-") && !arrOfSt.get(i).equals(" ")
                                    && (!arrOfSt.get(i - 1).equals(",") && !arrOfSt.get(i - 1).equals("/")
                                    && !arrOfSt.get(i - 1).equals(":") && !arrOfSt.get(i - 1).equals("-") && !arrOfSt.get(i - 1).equals(" "))) {
                                stb.append(" ");
                                lengthOfSt += 1;
                            }
                        }
                        stb.append(arrOfSt.get(i));
                        lengthOfSt += lenArrOfSt[i];
                    }
//                    lengthOfSt--;
//                    stb.delete(stb.length() - 1, stb.length());
                    String result = stb.toString();

                    // after looking up, if the result length fits in one line.
                    if (lengthOfSt <= charsPerLine) {
//                        System.out.println("case 2.2.1");
                        return result;
                    }
//                    else if (lengthOfSt <= charsPerLine * 2) {
                    /*
                     * if composing words with care produces a result fitting in two lines, use this result to retain readability
                     */
                    resultComposed = composeWordsWithCare(arrOfSt, charsPerLine);
//                    System.out.println("arrofst " + arrOfSt);
//                    System.out.println("resultCompose " + resultComposed);
                    start = resultComposed.indexOf("<br>");
                    count = 0;
                    secondBr = firstBr = -1;
                    while (start != -1) {

                        if (count == 0) {
                            firstBr = start;
                        }
                        if (count == 1) {
                            secondBr = start;
                        }
                        start = resultComposed.indexOf("<br>", start + "<br>".length());
                        count++;
                    }
                    // if the result fits in two lines.
                    if (count < 2) {
//                    if ((resultComposed.length()-"<br>".length()*count) <= charsPerLine*2){
//                        System.out.println("case 2.2.2");
//                        if (secondBr != -1)
//                            return resultComposed.substring(0,secondBr-3)+"...";
//                        else
                        return resultComposed;
                    } // if the result does not fit in two lines.
                    else {
                        if (secondBr != -1) {
//                            System.out.println("secondBr " + secondBr + " firstBr " + firstBr + " charsPerLine " + charsPerLine);
                            /*
                             * if the second line contains characters whose number is still less than charsPerLine.
                             * Return all words plus "..."
                             */
                            if (secondBr - firstBr - "<br>".length() <= charsPerLine - 3) {
//                                System.out.println("resultComposed " + resultComposed.substring(0, secondBr) + "...");
                                return resultComposed.substring(0, secondBr) + "...";
                            } /*
                             * if the second line is longer than charsPerLine, use only substring of it followed by "..."
                             */ else {
//                                System.out.println("resultComposed "+resultComposed.substring(0, firstBr + charsPerLine+"<br>".length() - 3) + "...");
                                return resultComposed.substring(0, firstBr + charsPerLine + "<br>".length() - 3) + "...";
                            }
                        } // may not be reached, just in case.
                        else {
                            return resultComposed;
                        }
                    }

                }


            } // if the name is longer than two lines
            else {
//                System.out.println("case 3");
//                System.out.println(st);
//                arrOfSt = lookUpInDict(st);
                /*
                 * Use the short name and if it fits in one line, use it.
                 */
                arrOfSt = new ArrayList<String>(Arrays.asList(propTerm.getShortName().split("\\s")));
                lenArrOfSt = new int[arrOfSt.size()];
//                System.out.println("afterlook " + arrOfSt);
//                lenArrOfSt = new int[arrOfSt.size()];
                StringBuilder stb = new StringBuilder();
                int lengthOfSt = 0;
                for (int i = 0; i < arrOfSt.size(); i++) {
                    lenArrOfSt[i] = arrOfSt.get(i).length();
                    if (i > 0) {
                        if (!arrOfSt.get(i).equals(",") && !arrOfSt.get(i).equals("/")
                                && !arrOfSt.get(i).equals(":") && !arrOfSt.get(i).equals("-") && !arrOfSt.get(i).equals(" ")
                                && (!arrOfSt.get(i - 1).equals(",") && !arrOfSt.get(i - 1).equals("/")
                                && !arrOfSt.get(i - 1).equals(":") && !arrOfSt.get(i - 1).equals("-") && !arrOfSt.get(i - 1).equals(" "))) {
                            stb.append(" ");
                            lengthOfSt += 1;
                        }
                    }
                    stb.append(arrOfSt.get(i));
                    lengthOfSt += lenArrOfSt[i];
                }
//                lengthOfSt--;
//                stb.delete(stb.length() - 1, stb.length());
//                String result = stb.toString();
//                System.out.println("result " + result);

                // if the result length fits in one line.
                if (lengthOfSt <= charsPerLine) {
//                    System.out.println("case 3.1");
                    return stb.toString();
                } // if the result length fits in two lines.
                else if (lengthOfSt <= charsPerLine * 2) {
//                    System.out.println("case 3.2");
                    /*
                     * if composing words with care produces a result fitting in two lines, use this result to retain readability
                     * Else, use the substring of the result.
                     */
//                    stb = new StringBuilder(composeWordsWithCare(arrOfSt,lenArrOfSt,2));
                    String resultComposed = composeWordsWithCare(arrOfSt, charsPerLine);
//                    System.out.println("resultCompose " + resultComposed);
                    int start = resultComposed.indexOf("<br>");
                    int count = 0, secondBr = -1, firstBr = -1;
                    while (start != -1) {

                        if (count == 0) {
                            firstBr = start;
                        }
                        if (count == 1) {
                            secondBr = start;
                        }
                        start = resultComposed.indexOf("<br>", start + "<br>".length());
                        count++;

                    }
                    // fits in two lines.
                    if (count < 2) {
//                    if ((resultComposed.length()-"<br>".length()*count) <= charsPerLine*2){
//                        System.out.println("case 3.2.1");

//                        if (secondBr != -1)
//                            return resultComposed.substring(0,secondBr-3)+"...";
//                        else
                        return resultComposed;
                    } // longer than two lines.
                    else {
                        if (secondBr != -1) {
//                            System.out.println("secondBr " + secondBr + " firstBr " + firstBr + " charsPerLine " + charsPerLine);
                            if (secondBr - firstBr - "<br>".length() <= charsPerLine - 3) {
                                /*
                                 * if the second line contains characters whose number is still less than charsPerLine.
                                 * Return all words plus "..."
                                 */
//                                System.out.println("resultComposed " + resultComposed.substring(0, secondBr) + "...");
                                return resultComposed.substring(0, secondBr) + "...";
                            } else {
                                /*
                                 * if the second line is longer than charsPerLine, use only substring of it followed by "..."
                                 */
//                                System.out.println("resultComposed "+resultComposed.substring(0, firstBr + charsPerLine+"<br>".length() - 3) + "...");
                                return resultComposed.substring(0, firstBr + charsPerLine + "<br>".length() - 3) + "...";
                            }
                        } else {
                            return resultComposed;
                        }
                    }

                } /* if the result length is still more than two lines.
                 * Compose words with care to retain beauty and readability of the label
                 */ else {
//                    System.out.println("case 3.3");
                    String resultComposed = composeWordsWithCare(arrOfSt, charsPerLine);
//                    System.out.println("resultComposed " + resultComposed);
                    int start = resultComposed.indexOf("<br>");
                    int count = 0, secondBr = -1, firstBr = -1;
                    while (start != -1) {

                        if (count == 0) {
                            firstBr = start;
                        }
                        if (count == 1) {
                            secondBr = start;
                        }
                        start = resultComposed.indexOf("<br>", start + "<br>".length());
                        count++;

                    }
//                    if (count < 2) {
//                    if ((resultComposed.length()-"<br>".length()*count) <= charsPerLine*2){
//                        System.out.println("case 3.2.1");
                    if (secondBr != -1) {
//                        System.out.println("secondBr "+secondBr+" firstBr "+firstBr+" charsPerLine "+charsPerLine);
                        /*
                         * if the second line contains characters whose number is still less than charsPerLine.
                         * Return all words plus "..."
                         */
                        if (secondBr - firstBr - "<br>".length() <= charsPerLine - 3) {
//                            System.out.println("resultComposed "+resultComposed.substring(0, secondBr) + "...");
                            return resultComposed.substring(0, secondBr) + "...";
                        } else {
                            /*
                             * if the second line is longer than charsPerLine, use only substring of it followed by "..."
                             */
//                            System.out.println("resultComposed "+resultComposed.substring(0, firstBr + charsPerLine+"<br>".length() - 3) + "...");
                            return resultComposed.substring(0, firstBr + charsPerLine + "<br>".length() - 3) + "...";
                        }
                    } else {
                        return resultComposed;
                    }
//                    }
//                    return result.substring(0,charsPerLine*2+"<br>".length()-3)+"...";
                }
            }
//            System.out.println("st " + st);

        }

        @Override
        public String transform(Object v) {

            String res = "<html><center>";
//            nodesPropVectorMap = viewHistoryList.get(currentViewHisPos).getNodesPropVectorMap();
            if (v instanceof Set) {
//                res += "<font size=\"+1\" color=#000000>" + ((Set) v).size() + "</font>";
                res += "<p style=\"font-size:125%;color:#000000\">" + ((Set) v).size() + "</p>";

//                DataVector vec = nodesPropVectorMap.get(v);
                ArrayList<PropertyTerm> termList = nodesLabelList.get(v);
                int numMember = ((Set) v).size();
                int numPerLine = (NaviClusterApp.this.proportionalScaling ? 12 + (int) (numMember * 0.01) : 12 + (int) (((double) (numMember - minNumOfMem) / (maxNumOfMem - minNumOfMem)) * 7));
//                int numPerLine = 12 + (int) (numMember * 0.01);
//                if (vec != null) {
                int i = 0;
//                    System.out.println("Termlist "+termList);
                for (PropertyTerm propTerm : termList) {
                    String toPrint = "";
                    if (i == 0) {
                        res += "<p style=\"font-size:104%;";
//                            toPrint = oneLineLabeller(toPrint, 12);
//                            toPrint = "<b>"+processLabeller(toPrint, 12)+"</b>";
                        toPrint = processLabeller(propTerm, numPerLine);
                    } else {
                        res += "<p style=\"font-size:98%;";
                        toPrint = "[" + oneLineLabeller(propTerm, numPerLine + 1) + "]";
                    }

//                        DecimalFormat myFormatter = new DecimalFormat(".00");
//                        String output = myFormatter.format(vec.getValueMap().get(propTerm.getId()));
                    if (propTerm != null) {
                        NameSpace ns = PropInfoProcessor.getNamespaceMap().get(propTerm.getNamespace().getName());
//                            System.out.println(ns.getName()+" "+ns.getColor());
                        res += "color: rgb(" + ns.getColor().getRed() + "," + ns.getColor().getGreen() + "," + ns.getColor().getBlue() + ")\">";
//                            System.out.println("color: rgb(" + ns.getColor().getRed()+","+ns.getColor().getGreen()+","+ns.getColor().getBlue()+")\">");
//                            if (propTerm.getNamespace().equals(NameSpace.CC)) {
////                            res += "<font color = #ED143F>";
//                                res += "color:#ED143F\">";
//                            } else if (propTerm.getNamespace().equals(NameSpace.MF)) {
//                                res += "color:#068E1D\">";
////                            res += "<font color = #338F06>";
////                            res += "<font color = #068E1D>";
////                            res += "<font color = #338E06>";
////                            res += "<font color = #068E61>"
//                            } else {
////                            res += "<font color = #3425B1>";
//                                res += "color:#3425B1\">";
//                            }
                    } else {
                        res += "color:#000000\">";
                    }
//                        res += toPrint + "(" + output + ")";
//                        System.out.println("res "+res);
                    if (toPrint.equals("")) {
                        toPrint = "<i>No Info.</i>";
                    }
                    res += toPrint;
//                        res += "</font>";
                    res += "</p>";
                    i++;
                }
                if (i == 1) {
                    res += "<p><br></p>";
                }
                res += "</center>";
//                }
//                else {
//                    res += "#Nodes: " + ((Set) v).size();
//                }
            } else if (v instanceof BioObject) {
                if (((BioObject) v).getStandardName().length() != 0) {
                    res += ((BioObject) v).getStandardName();
                } else {
                    res += ((BioObject) v).getName();
                }
            }
            res += "</html>";
            return res;
        }
    }

    /**
     * DEPRECATED
     * File filter for modified .net pajek file
     */
    private class MNetFileFilter extends FileFilter {

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
                if (ext.equals("mnet")) {
                    return true;
                } else {
                    return false;
                }
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "Modified .net Pajek file";
        }
    }

    /**
     * Predicate for displaying edges
     * Whether to display edge or not depends on threshold and type of edge
     * @param <V>
     * @param <E>
     */
    private final class EdgeDisplayPredicate<V, E> implements Predicate<Context<Graph<V, E>, E>> //extends AbstractGraphPredicate<V,E>
    {

        private double thresholdForSE = .1;
        /* to be deleted in the future */
        private boolean isLowestLevel = false;

        public void setLowestLevel(boolean value) {
            isLowestLevel = value;
        }

        public double getThresholdForSE() {
            return thresholdForSE;
        }

        public void setThresholdForSE(double thresholdForSE) {
            this.thresholdForSE = thresholdForSE;
        }

        public boolean evaluate(Context<Graph<V, E>, E> context) {
//            Graph<V, E> graph = context.graph;
            E e = context.element;
//            if ((e instanceof MetaEdge) || (isLowestLevel)) {
            if (e instanceof PropertyEdge) {
                double val = ((PropertyEdge) e).getInnerProduct();
                if (!((val > 0) && (val >= thresholdForSE))) {
                    return false;
                } else {


                    if (vv.getPickedVertexState().getPicked().size() == 0) {
                        return true;
                    } else {
//                    boolean notIncidentAtAll = true;
                        boolean onlyEdge = true;
                        for (Object obj : vv.getPickedVertexState().getPicked()) {
                            if ((obj instanceof BioObject) || (obj instanceof Set)) {
                                onlyEdge = false;
                            }

                            if (dynamicGraph.isIncident(obj, e)) {
                                return true;
                            }
                        }
                        if (!onlyEdge) {
                            return false;
                        }

                    }
                }

            }
            if (e instanceof MetaEdge) {
                if (vv.getPickedVertexState().getPicked().size() == 0) {
                    return true;
                } else {
//                    boolean notIncidentAtAll = true;
                    boolean onlyEdge = true;
                    for (Object obj : vv.getPickedVertexState().getPicked()) {
                        if ((obj instanceof BioObject) || (obj instanceof Set)) {
                            onlyEdge = false;
                        }

                        if (dynamicGraph.isIncident(obj, e)) {
                            return true;
                        }
                    }
                    if (!onlyEdge) {
                        return false;
                    }

                }
                return true;
            }
            if (e instanceof BioEdge) {
                if (vv.getPickedVertexState().getPicked().size() == 0) {
                    return true;
                } else {
                    if (((BioEdge) e).getWeight() < 0.0) {
                        return false;
                    }
//                    boolean notIncidentAtAll = true;
                    boolean onlyEdge = true;
                    for (Object obj : vv.getPickedVertexState().getPicked()) {
                        if ((obj instanceof BioObject) || (obj instanceof Set)) {
                            onlyEdge = false;
                        }

                        if (dynamicGraph.isIncident(obj, e)) {
                            return true;
                        }
                    }
                    if (!onlyEdge) {
                        return false;
                    }

                }
                return true;
            }


            return true;
        }
    }

    /**
     * A method that labels edges.
     * @param <E>
     */
    private final class EdgeLabeller<E> implements Transformer<E, String> {

//        protected EdgeWeightLabeller edge_weight;
//        public EdgeLabeller(EdgeWeightLabeller edgeWeight) {
//            this.edge_weight = edgeWeight;
//        }
        public EdgeLabeller() {
        }

        public String transform(E e) {
            double value = 1;
            String res = "<html><p style=\"font-size:103%;color:#6E6E6E\">";
            if (e instanceof MetaEdge) {
                value = ((MetaEdge) e).numEdgeSetBundled;
//                value = ((MetaEdge) e).getWeightedNumEdges();
//                return "" + String.format("%.3f", value);
//                return "" + String.format("%d", (int) value);
                res += String.format("%d", (int) value);
            } else if (e instanceof PropertyEdge) {
                value = ((PropertyEdge) e).getInnerProduct();
//                return "" + String.format("%.3f", value);
                res += String.format("%.3f", value);
            } else if (e instanceof BioEdge) {
//                value = edge_weight.getWeight(e);
                value = ((BioEdge) e).getWeight();
//                return "" + String.format("%.3f", value);
                res += String.format("%.3f", value);
            } else {
                value = 1;
                res += value;
            }
            res += "</p></html>";
            return res;
//            if (value > 1) {
//                return "" + String.format("%d", (int) value);
//            } else {
//                return "";
//            }

        }
    }

    /**
     * A method that draw stroke for vertices
     * @param <V>
     */
    private final class VertexWeightStrokeFunction<V> implements Transformer<V, Stroke> {

        protected final Stroke basic = new BasicStroke(1);
        protected final Stroke heavy = new BasicStroke(3);
        protected final Stroke heavyPlus = new BasicStroke(5);
//        protected final Stroke basic = new BasicStroke(2);
//        protected final Stroke heavy = new BasicStroke(5);
//        protected final Stroke heavyPlus = new BasicStroke(9);

        public boolean evaluateEdge(Object e) {
//            Graph<V, E> graph = context.graph;
//            if ((e instanceof MetaEdge) || (isLowestLevel)) {
            if (e instanceof MetaEdge) {
                return true;
            }
            if (e instanceof PropertyEdge) {
                double val = ((PropertyEdge) e).getInnerProduct();
                EdgeDisplayPredicate edp = (EdgeDisplayPredicate) vv.getRenderContext().getEdgeIncludePredicate();
                if ((val > 0) && (val >= edp.getThresholdForSE())) {
                    return true;
                } else {
                    return false;
                }

            }

            return true;
        }

        public Stroke transform(V v) {
            if (vv.getPickedVertexState().isPicked(v)) {
                return heavyPlus;
            } else {

                //There are two types of edges, so getNeighbors alone is not enough.
                for (Object w : dynamicGraph.getNeighbors(v)) {
                    for (Object e : dynamicGraph.findEdgeSet(v, w)) {
                        if (e instanceof MetaEdge) {
                            if (vv.getPickedVertexState().isPicked(w)) {
                                return heavy;
                            }
                        } else if (e instanceof PropertyEdge) {
                            if (evaluateEdge(e)) {
                                if (vv.getPickedVertexState().isPicked(w)) {
                                    return heavy;
                                }
                            }
                        } else if (e instanceof BioEdge) {
                            if (vv.getPickedVertexState().isPicked(w)) {
                                return heavy;
                            }
                        }
                    }


////                    for (Iterator iter = graph.getNeighbors(v)v.getNeighbors().iterator(); iter.hasNext(); )
////                    {
////                        Vertex w = (Vertex)iter.next();
//                    if (vv.getPickedVertexState().isPicked((V)w)) {
//                        return heavy;
//                    }
                }
            }
            if (clickedNodes != null) {
                if (centerNodeSet != null) {
                    if (clickedNodes.indexOf(v) != -1 || centerNodeSet.contains(v)) {
                        return heavyPlus;
                    }
                }
                if (clickedNodes.indexOf(v) != -1) {
                    return heavy;
                }
            }
            if (centerNodeSet != null && centerNodeSet.contains(v)) {
                return heavyPlus;
            }
            return basic;
        }
    }

    private final class EdgeFontTransformer<E> implements Transformer<E, Font> {

        protected boolean bold = true;
//        Font f = new Font("Helvetica", Font.PLAIN, 14);
//        Font b = new Font("Helvetica", Font.BOLD, 14);
//        Font f = new Font("Sans", Font.PLAIN, 14);
//        Font b = new Font("Sans", Font.BOLD, 14);
//        Font f = new Font("Helvetica", Font.PLAIN, 25);
//        Font b = new Font("Helvetica", Font.BOLD, 25);
        Font f = plainEdgeFont;
        Font b = boldEdgeFont;

        public void setBold(boolean bold) {
            this.bold = bold;
        }

        public Font transform(E e) {
            if (bold) {
                return b;
            } else {
                return f;
            }
        }
    }

    /**
     * A method that draw stroke of edges according to their weights
     * @param <E>
     */
    private final class EdgeWeightStrokeFunction<E> implements Transformer<E, Stroke> {

        float[] Dashes = {10.0F, 3.0F, 3.0F, 3.0F};
        protected final Stroke basic = new BasicStroke(1F);
        protected final Stroke heavy = new BasicStroke(2.3F);
        protected final Stroke heavy2 = new BasicStroke(4.3F);
        protected final Stroke sBasic = new BasicStroke(1.2F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, Dashes, 0.F);
        protected final Stroke sHeavy = new BasicStroke(3.5F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, Dashes, 0.F);
        protected final Stroke sHeavy2 = new BasicStroke(4.7F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, Dashes, 0.F);
//        protected final Stroke basic = new BasicStroke(1F*1.8F);
//        protected final Stroke heavy = new BasicStroke(2.3F*1.8F);
//        protected final Stroke heavy2 = new BasicStroke(4.3F*1.8F);
//        protected final Stroke sBasic = new BasicStroke(1.2F*1.8F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F*1.8F, Dashes, 0.F);
//        protected final Stroke sHeavy = new BasicStroke(3.5F*1.8F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F*1.8F, Dashes, 0.F);
//        protected final Stroke sHeavy2 = new BasicStroke(4.7F*1.8F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F*1.8F, Dashes, 0.F);
        ;
        protected final Stroke dotted = RenderContext.DOTTED;
//        protected EdgeWeightLabeller edge_weight;
        protected Graph graph;

//        public EdgeWeightStrokeFunction(EdgeWeightLabeller edge_weight) {
//            this.edge_weight = edge_weight;
//        }
        public EdgeWeightStrokeFunction() {
        }

        public Stroke transform(E e) {
            double value;
            if (e instanceof MetaEdge) {
//                value = ((MetaEdge) e).getNumEdgeSetBundled();
                value = ((MetaEdge) e).getWeightedNumEdges();
                float edgeVal = (float) (1.5F + ((double) (value - minMetaEdgeWeight) / (maxMetaEdgeWeight - minMetaEdgeWeight)) * 3.1F);
//                float edgeVal = (float) (1.5F + ((double) (value - minMetaEdgeWeight) / (maxMetaEdgeWeight - minMetaEdgeWeight)) * 3.1F)*1.8F;
                return new BasicStroke(edgeVal);
//                if (value > 7) {
//                    return heavy2;
//                } else if (value > 3) {
//                    return heavy;
//                } else {
//                    return basic;
//                }
            } else if (e instanceof PropertyEdge) {
                value = ((PropertyEdge) e).getInnerProduct();
                if (value > 0.8) {
                    return sHeavy2;
                } else if (value > 0.3) {
                    return sHeavy;
                } else {
                    return sBasic;
                }
            } else if (e instanceof BioEdge) {
                value = ((BioEdge) e).getWeight();
                float edgeVal = (float) (1F + ((double) (value - minBioEdgeWeight) / (maxBioEdgeWeight - minBioEdgeWeight)) * 2F);
//                float edgeVal = (float) (1F + ((double) (value - minBioEdgeWeight) / (maxBioEdgeWeight - minBioEdgeWeight)) * 2F)*1.8F;
                return new BasicStroke(edgeVal);
//                if (value > 7) {
//                    return heavy;
//                } else if (value > 3) {
//                    return new BasicStroke(3);
//                } else {
//                    return basic;
//                }

            }
            return basic;

        }
    }

    /**
     * Font for vertices
     */
    public class VertexFontTransformer implements Transformer<Object, Font> {

        public Font transform(Object vertex) {
//            return new Font("Serif", Font.PLAIN, 13);
            return vertexFont;
//            return new Font("Century Schoolbook", Font.PLAIN, 12);
//            return new Font("Serif", Font.PLAIN, 23);
        }
    }

    /**
     * Tell vv how to deal with vertex shape and size
     * And generate color of vertex according to its number of BioObjs.
     * @param <V>
     * @param <E>
     */
    private final class VertexShapeSizeAspect<V, E>
            extends AbstractVertexShapeTransformer<V>
            implements Transformer<V, Shape> {

        int overallNumber = originalGraph.getVertexCount() / Integer.parseInt(thresholdTextField.getValue().toString());

        public Shape transform(V v) {

            if (v instanceof BioObject) {
//                return factory.getRegularStar(v, 6);
//                return factory.getRoundRectangle(v);
//                return factory.getEllipse(v);
                return factory.getRegularPolygon(v, 6);
//                return factory.getRegularPolygon(v, 3);
            }
//            return factory.getRectangle(v);
            return factory.getRoundRectangle(v);

//            }
        }

        public VertexShapeSizeAspect(Graph<V, E> graphIn) {

            setSizeTransformer(new Transformer<V, Integer>() {

                public Integer transform(V v) {

                    int numMember = 0;
                    int value = 85;

                    if (v instanceof Set) {
//                        float[] hsbvals = new float[3];   
//                        hsbvals [0.13018598, 0.9137255, 1.0]
                        numMember = ((Set) v).size();
//                        Color.RGBtoHSB(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), hsbvals);
//                        System.out.println("hsbvals "+Arrays.toString(hsbvals));
                        float hue = 0.13018598f, satur = (float) numMember / overallNumber, bright = 1.0f;

                        if (satur > 1) {
                            satur = 1.0f;
                        } else if (satur < 0.20) {
                            satur = 0.20f;
                        }
                        if ((clickedNodes == null || !clickedNodes.contains(v)) && (highlightedNode != v)) {
                            vertexPaints.put(v, new Color(Color.HSBtoRGB(hue, satur, bright)));
                        }
                        if (!curVertexSizeMap.containsKey(v)) {
                            value = (NaviClusterApp.this.proportionalScaling ? clusterSizeInLayout + (int) (numMember * clusterSizeInLayout * 0.0008)
                                    : clusterSizeInLayout + (int) (((double) (numMember - minNumOfMem) / (maxNumOfMem - minNumOfMem)) * clusterSizeInLayout * sizeMultiplier));
//                            value = clusterSizeInLayout + (int) (numMember  * clusterSizeInLayout * 0.0008);
//                            if (value == 0) {
//                                System.out.println("first v " + v);
//                                System.out.println("nummember " + numMember);
//                                System.out.println("minNum " + minNumOfMem);
//                                System.out.println("maxNum " + maxNumOfMem);
//                                System.out.println("clusterSize " + clusterSizeInLayout);
//                                System.out.println("sizemul " + sizeMultiplier);
//                            }
                            curVertexSizeMap.put(v, value);
                        } else {
                            value = curVertexSizeMap.get(v);
//                            if (value == 0) {
//                                System.out.println("second v " + v);
//                                System.out.println("nummember " + numMember);
//                                System.out.println("minNum " + minNumOfMem);
//                                System.out.println("maxNum " + maxNumOfMem);
//                                System.out.println("clusterSize " + clusterSizeInLayout);
//                                System.out.println("sizemul " + sizeMultiplier);
//                            }
                        }

//                        numMember = ((Set)v).size();
//                        int value = (int) (Math.log10(numMember) / Math.log10(4) * 20);
//                        if (numMember == 0)
//                            return 20;
//                        else if (value < 20)
//                            return 20;
//                        else
//                            return value;
//                        System.out.println("minnum "+minNumOfMem+"maxnum "+maxNumOfMem);
//                        System.out.println((clusterSizeInLayout+((double)(numMember-minNumOfMem)/(maxNumOfMem-minNumOfMem))*clusterSizeInLayout*sizeMultiplier));
//                        return (int)(clusterSizeInLayout+((double)(numMember-minNumOfMem)/(maxNumOfMem-minNumOfMem))*clusterSizeInLayout*sizeMultiplier);
                        return value;


                    } else {
                        return nodeSizeInLayout;
                    }
                }
            });



        }
    }

    /**
     * Draw edge color when the edge is picked
     * @param <V>
     * @param <E>
     */
    public class PickedEdgePaintFunction<V, E> implements Transformer<E, Paint> {

        private Transformer<E, Paint> defaultFunc;

        public PickedEdgePaintFunction(Transformer<E, Paint> defaultEdgePaintFunction,
                VisualizationViewer<V, E> vv) {
            this.defaultFunc = defaultEdgePaintFunction;
        }

        public Paint transform(E e) {

            if (e instanceof PropertyEdge) {
                return new Color(182, 182, 182);
            } else {
                return defaultFunc.transform(e);
            }

        }
    }

    /**
     * Deal with pop up menu when right-clicking nodes or edges on the canvas
     * Menu that will appear will change according to what are clicked. (nodes or edges or both)
     */
    protected class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin
            implements MouseListener {

        public PopupGraphMousePlugin() {
            this(MouseEvent.BUTTON3_MASK);
        }

        public PopupGraphMousePlugin(int modifiers) {
            super(modifiers);
        }

        public void openUrl(String url) {
            String os = System.getProperty("os.name");
            Runtime runtime = Runtime.getRuntime();
            try {
// Block for Windows Platform
                if (os.startsWith("Windows")) {
                    String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
                    Process p = runtime.exec(cmd);
                } //Block for Mac OS
                else if (os.startsWith("Mac OS")) {
                    Class fileMgr = Class.forName("com.apple.eio.FileManager");
                    Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
                    openURL.invoke(null, new Object[]{url});
                } //Block for UNIX Platform
                else {
                    String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                    String browser = null;
                    for (int count = 0; count < browsers.length && browser == null; count++) {
                        if (runtime.exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
                            browser = browsers[count];
                        }
                    }
                    if (browser == null) {
                        throw new Exception("Could not find web browser");
                    } else {
                        runtime.exec(new String[]{browser, url});
                    }
                }
            } catch (Exception x) {
                System.err.println("Exception occurred while invoking Browser!");
                x.printStackTrace();
            }
        }

        public void showInfoFromWeb(BioObject ver) throws IOException {
            if (ver.getDatabaseName().equalsIgnoreCase("SGD")) {

                openUrl("http://db.yeastgenome.org/cgi-bin/locus.pl?locus=" + ver.getDatabaseID());

            } else if (ver.getDatabaseName().equalsIgnoreCase("GRID") || ver.getDatabaseName().equalsIgnoreCase("BIOGRID")) {

                openUrl("http://thebiogrid.org/" + ver.getDatabaseID());

            } else if (ver.getDatabaseName().equalsIgnoreCase("RefSeq")) {

                openUrl("http://www.ncbi.nlm.nih.gov/sites/entrez?db=protein&cmd=DetailsSearch&term=" + ver.getDatabaseID());

            } else if (ver.getDatabaseName().equalsIgnoreCase("GenBank")) {

                openUrl("http://www.ncbi.nlm.nih.gov/sites/entrez?Db=protein&Cmd=DetailsSearch&Term=" + ver.getDatabaseID());

            } else if (ver.getDatabaseName().equalsIgnoreCase("UniProt") || ver.getDatabaseName().equalsIgnoreCase("UniProtKB")) {

                openUrl("http://www.uniprot.org/uniprot/?query=" + ver.getDatabaseID());

            } else if (ver.getDatabaseName().equalsIgnoreCase("TAIR")) {

                openUrl("http://www.arabidopsis.org/servlets/Search?type=general&search_action=detail&method=1&show_obsolete=F&name=" + ver.getName() + "&sub_type=protein&SEARCH_EXACT=4&SEARCH_CONTAINS=1");

            } else if (ver.getDatabaseName().equalsIgnoreCase("ATTED")) {

                openUrl("http://atted.jp/data/locus/" + ver.getDatabaseID() + ".shtml");

            } else {
                if (ver.getStandardName().length() != 0) {
                    openUrl("http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&term=" + ver.getStandardName() + "&dispmax=50&dopt=d");
                } else {
                    openUrl("http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&term=" + ver.getName() + "&dispmax=50&dopt=d");
                }
            }
        }

        ////DEBUG////
//        public void printSortedMap(Set<Entry<String, Double>> sortedMap) {
//            try {
//                PrintWriter pw = new PrintWriter(new File("debug-navicluster-linkoutnode"));
//                pw.println(sortedMap.size());
//                for (Entry entry : sortedMap) {
//                    pw.println(entry.getKey() + "\t" + entry.getValue());
//                }
//                pw.close();
//            } catch (FileNotFoundException ex) {
//                java.util.logging.Logger.getLogger(PropInfoProcessor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        public void showNodeList(Set<Object> vSet) {
            final JDialog jd = new JDialog(NaviClusterApp.this);
            jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            jd.setLocationRelativeTo(NaviClusterApp.this);

//            JTextArea jta = new JTextArea(15, 50);
//            jta.setEditable(false);

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");


            JPanel panel = new JPanel(new BorderLayout());
            jd.setContentPane(panel);
//            Container pane = jd.getContentPane();
//            panel.add(jta, BorderLayout.CENTER);

            JPanel subPanel = new JPanel();
            if (vSet.size() == 1) {
                subPanel.add(new JLabel("Node List with Highest-Score Property Terms"));
            } else {
                subPanel.add(new JLabel("Node Lists with Highest-Score Property Terms"));
            }
            panel.add(subPanel, BorderLayout.NORTH);
//            jta.setPreferredSize(new Dimension(250, 400));

//            JScrollPane jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            JScrollPane jsp = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            jsp.setPreferredSize(new Dimension(450, 300));
            jsp.setMinimumSize(new Dimension(450, 300));

            panel.add(jsp, BorderLayout.CENTER);
            StringBuilder stb = new StringBuilder();
            stb.append("<html>");

            int j = 0;
            for (Object ver : vSet) {
                if (ver instanceof BioObject) {
                    BioObject bioObj = (BioObject) ver;
//                    String res = "";
                    if (bioObj.getStandardName().length() != 0) {
                        stb.append("<b>" + (1 + j) + ".</b> <b>" + bioObj.getStandardName() + "</b>");
                    } else {
                        stb.append("<b>" + (1 + j) + ".</b> <b>" + bioObj.getName() + "</b>");
                    }

                    DataVector vec = nodesPropVectorMap.get(ver);

                    if (vec != null) {
                        String res = "";
                        Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {

                            public int compare(Object o1, Object o2) {
                                Entry<String, Double> e1 = (Entry<String, Double>) o1;
                                Entry<String, Double> e2 = (Entry<String, Double>) o2;

                                if (e1.getValue() > e2.getValue()) {
                                    return -1;
                                } else if (e1.getValue() < e2.getValue()) {
                                    return 1;
                                } else {
                                    PropertyTerm p1 = PropInfoProcessor.propTermsMap.get(e1.getKey());
                                    PropertyTerm p2 = PropInfoProcessor.propTermsMap.get(e2.getKey());
                                    if (p1.getWeight() > p2.getWeight()) {
                                        return -1;
                                    } else if (p1.getWeight() < p2.getWeight()) {
                                        return 1;
                                    } else {
                                        String name1 = PropInfoProcessor.propTermsMap.get(e1.getKey()).getName();
                                        String name2 = PropInfoProcessor.propTermsMap.get(e2.getKey()).getName();
                                        return name1.compareTo(name2);
                                    }

                                }
//                                else {
//                                return 0;
//                                }
                            }
                        });

                        sortedMap.addAll(vec.getValueMap().entrySet());

                        int i = 0;

                        for (Entry entry : sortedMap) {

                            PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) entry.getKey());
                            String toPrint = propTerm.getName();

//                        if (toPrint.length() > 20) {
//                            toPrint = toPrint.substring(0, 20);
//                            toPrint += "...";
//                        }

                            DecimalFormat myFormatter = new DecimalFormat(".00");
                            String output = myFormatter.format(entry.getValue());

                            res += " (";
                            NameSpace ns = PropInfoProcessor.getNamespaceMap().get(propTerm.getNamespace().getName());
                            res += "<font color = rgb(" + ns.getColor().getRed() + "," + ns.getColor().getGreen() + "," + ns.getColor().getBlue() + ")>";
//                            if (propTerm.getNamespace().equals(NameSpace.CC)) {
//                                res += "<font color = #ED143F>";
//                            } else if (propTerm.getNamespace().equals(NameSpace.MF)) {
//                                res += "<font color = #338F06>";
//                            } else {
//                                res += "<font color = #3425B1>";
//                            }
                            res += "" + toPrint + " (" + output + ")";
                            res += "</font>)";

                            i++;

                            if (i == 1) {
                                break;
                            }

                        }
                        if (i == 0) {
                            res += " <i>(No Info.)</i>";
                        }
                        stb.append(res);
                    } else {
                        if (ver instanceof BioObject) {
                            String res = "";
                            BioObject bio = (BioObject) ver;
                            ArrayList<String> propTermIdList = bio.getPropTermList();
                            int i = 0;
                            for (String s : propTermIdList) {
                                res += " (" + s + ") ";// + " with " + String.format("%.3f", entry.getValue());
                                i++;
                                if (i == 1) {
                                    break;
                                }
                            }
                            if (i == 0) {
                                res += " <i>(No Info.)</i>";
                            }
                            stb.append(res);
                        }
                    }

                } else if (ver instanceof Set) {
                    Set set = (Set) ver;
                    PropInfoProcessor gp = new PropInfoProcessor();
                    gp.populatePropTerms(set);
                    Map<Object, DataVector> propVecMap = new HashMap<Object, DataVector>(putInNodesPropVectorMapRelatively(gp.getNodesPropVectorMapBeforeCluster(set)));
//                    String res = "";
                    stb.append("<b>" + (1 + j) + ".</b> Number of nodes inside: <b>" + set.size() + "</b>");
                    for (Object verInSet : set) {
                        if (verInSet instanceof BioObject) {
                            BioObject bioObj = (BioObject) verInSet;
//                    String res = "";
                            if (bioObj.getStandardName().length() != 0) {
                                stb.append("<br>- <b>" + bioObj.getStandardName() + "</b>");
                            } else {
                                stb.append("<br>- <b>" + bioObj.getName() + "</b>");
                            }

                            DataVector vec = propVecMap.get(verInSet);
//                            DataVector vec = nodesPropVectorMap.get(verInSet);

                            if (vec != null) {
                                String res = "";
                                Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {

                                    public int compare(Object o1, Object o2) {
                                        Entry<String, Double> e1 = (Entry<String, Double>) o1;
                                        Entry<String, Double> e2 = (Entry<String, Double>) o2;
                                        if (e1.getValue() > e2.getValue()) {
                                            return -1;
                                        } else if (e1.getValue() < e2.getValue()) {
                                            return 1;
                                        } else {
                                            PropertyTerm p1 = PropInfoProcessor.propTermsMap.get(e1.getKey());
                                            PropertyTerm p2 = PropInfoProcessor.propTermsMap.get(e2.getKey());
                                            if (p1.getWeight() > p2.getWeight()) {
                                                return -1;
                                            } else if (p1.getWeight() < p2.getWeight()) {
                                                return 1;
                                            } else {
                                                String name1 = PropInfoProcessor.propTermsMap.get(e1.getKey()).getName();
                                                String name2 = PropInfoProcessor.propTermsMap.get(e2.getKey()).getName();
                                                return name1.compareTo(name2);
                                            }

                                        }
//                                        else {
//                                        return 0;
//                                        }
                                    }
                                });

                                sortedMap.addAll(vec.getValueMap().entrySet());

                                int i = 0;

                                for (Entry entry : sortedMap) {

                                    PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) entry.getKey());
                                    String toPrint = propTerm.getName();

//                        if (toPrint.length() > 20) {
//                            toPrint = toPrint.substring(0, 20);
//                            toPrint += "...";
//                        }

                                    DecimalFormat myFormatter = new DecimalFormat(".00");
                                    String output = myFormatter.format(entry.getValue());

                                    res += " (";
                                    NameSpace ns = PropInfoProcessor.getNamespaceMap().get(propTerm.getNamespace().getName());
                                    res += "<font color = rgb(" + ns.getColor().getRed() + "," + ns.getColor().getGreen() + "," + ns.getColor().getBlue() + ")>";
//                                    if (propTerm.getNamespace().equals(NameSpace.CC)) {
//                                        res += "<font color = #ED143F>";
//                                    } else if (propTerm.getNamespace().equals(NameSpace.MF)) {
//                                        res += "<font color = #338F06>";
//                                    } else {
//                                        res += "<font color = #3425B1>";
//                                    }
                                    res += "" + toPrint + " (" + output + ")";
                                    res += "</font>)";

                                    i++;

                                    if (i == 1) {
                                        break;
                                    }

                                }
                                if (i == 0) {
                                    res += " <i>(No Info.)</i>";
                                }
                                stb.append(res);
                            } else {
                                if (verInSet instanceof BioObject) {
                                    String res = "";
                                    BioObject bio = (BioObject) verInSet;
                                    ArrayList<String> propTermIdList = bio.getPropTermList();
                                    int i = 0;
                                    for (String s : propTermIdList) {
                                        res += " (" + s + ") ";// + " with " + String.format("%.3f", entry.getValue());
                                        i++;
                                        if (i == 1) {
                                            break;
                                        }
                                    }
                                    if (i == 0) {
                                        res += " <i>(No Info.)</i>";
                                    }
                                    stb.append(res);
                                }
                            }
                        } else {

//                    String res = "";
                            stb.append("<br>- Unidentified Object: <b>" + verInSet.toString() + "</b>");
                        }
                    }
                }

                stb.append("<p>");
                j++;
            }
            stb.append("</html>");
            editorPane.setText(stb.toString());
            editorPane.setCaretPosition(0);
            editorPane.setPreferredSize(new Dimension(450, 300));

            JButton closeButton = new JButton("Close");
            subPanel = new JPanel();
            subPanel.add(closeButton);
            panel.add(subPanel, BorderLayout.SOUTH);
////            JOptionPane.showConfirmDialog(NaviClusterApp.this, "hey");
////            JOptionPane.showMessageDialog(NaviClusterApp.this, "hey");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    jd.setVisible(false);

                }
            });
            jd.setPreferredSize(new Dimension(450, 385));
            jd.setLocation((int) (NaviClusterApp.this.getPreferredSize().getWidth() / 2 - jd.getPreferredSize().getWidth() / 2),
                    (int) (NaviClusterApp.this.getPreferredSize().getHeight() / 2 - jd.getPreferredSize().getHeight() / 2));
//            jd.setResizable(false);
            jd.pack();
            jd.getRootPane().setDefaultButton(closeButton);
            closeButton.requestFocusInWindow();
            jd.setVisible(true);
        }

        public void showEdgeDensity(Set<Object> vSet) {
            final JDialog jd = new JDialog(NaviClusterApp.this);
            jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            jd.setLocationRelativeTo(NaviClusterApp.this);

//            JTextArea jta = new JTextArea(15, 50);
//            jta.setEditable(false);

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");


            JPanel panel = new JPanel(new BorderLayout());
            jd.setContentPane(panel);
//            Container pane = jd.getContentPane();
//            panel.add(jta, BorderLayout.CENTER);

            JPanel subPanel = new JPanel();
//            if (vSet.size() == 1) {
            subPanel.add(new JLabel("Edge Density"));
//            } else {
//                subPanel.add(new JLabel("Edge Information of The Clusters"));
//            }
            panel.add(subPanel, BorderLayout.NORTH);
//            jta.setPreferredSize(new Dimension(250, 400));

//            JScrollPane jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            JScrollPane jsp = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            jsp.setPreferredSize(new Dimension(450, 300));
            jsp.setMinimumSize(new Dimension(450, 300));

            panel.add(jsp, BorderLayout.CENTER);
            StringBuilder stb = new StringBuilder();
            stb.append("<html>");

            int j = 0;
            for (Object ver : vSet) {
                if (ver instanceof Set) {
//                    Set flatClust = comNodeClustToFlatClust.get((Set)ver);
                    Set flatClust = (Set) ver;
//                    System.out.println("flatClust "+flatClust);
                    if (flatClust != null) {
                        int totalEdgesInCluster = 0, clusterSize = flatClust.size();
                        int totalEdgesOutsideCluster = 0;
                        double edgeDensity = 0.0, edgeRatio = 0.0;
                        for (Object node : flatClust) {
                            BioObject bioObj = (BioObject) node;
                            for (Object neighbor : realGraph.getNeighbors(bioObj)) {
                                if (flatClust.contains(neighbor)) {
                                    totalEdgesInCluster += 1;
                                } else {
                                    totalEdgesOutsideCluster += 1;
                                }
                            }
//                            totalEdgesInCluster += realGraph.getNeighborCount(bioObj);
                        }
                        totalEdgesInCluster /= 2;
                        edgeDensity = totalEdgesInCluster / ((double) clusterSize * (clusterSize - 1) / 2.0);
                        edgeRatio = (double) totalEdgesInCluster / totalEdgesOutsideCluster;
                        stb.append("<b>" + (1 + j) + ".</b> Cluster with <b>" + clusterSize + "</b> nodes:<br>");
                        stb.append("<b>(A)</b> Intra-Cluster Edge Number: <b>" + totalEdgesInCluster + "</b><br>");
                        stb.append("<b>(B)</b> Inter-cluster Edge Number: <b>" + totalEdgesOutsideCluster + "</b><br>");
                        stb.append("<b>(C)</b> Ratio between (A) and (B): <b>" + String.format("%.3f", edgeRatio) + "</b><br>");
                        stb.append("<b>(D)</b> Edge Density: <b>" + String.format("%.3f", edgeDensity) + "</b><br>(calculated by dividing (A) by N(N-1)/2 where N = the number of nodes inside the cluster)");
                        stb.append("<p>");
                    }
                    j++;
                }
            }
            stb.append("</html>");

            editorPane.setText(stb.toString());
            editorPane.setCaretPosition(0);
            editorPane.setPreferredSize(new Dimension(450, 300));

            JButton closeButton = new JButton("Close");
            subPanel = new JPanel();
            subPanel.add(closeButton);
            panel.add(subPanel, BorderLayout.SOUTH);
////            JOptionPane.showConfirmDialog(NaviClusterApp.this, "hey");
////            JOptionPane.showMessageDialog(NaviClusterApp.this, "hey");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    jd.setVisible(false);
                    jd.dispose();
                }
            });
            jd.setPreferredSize(new Dimension(450, 385));
            jd.setLocation((int) (NaviClusterApp.this.getPreferredSize().getWidth() / 2 - jd.getPreferredSize().getWidth() / 2),
                    (int) (NaviClusterApp.this.getPreferredSize().getHeight() / 2 - jd.getPreferredSize().getHeight() / 2));
//            jd.setResizable(false);
            jd.pack();
            jd.getRootPane().setDefaultButton(closeButton);
            closeButton.requestFocusInWindow();
            jd.setVisible(true);
        }

        public void showPropTermList(Set<Object> vSet) {
            final JDialog jd = new JDialog(NaviClusterApp.this);
            jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            jd.setLocationRelativeTo(NaviClusterApp.this);

//            JTextArea jta = new JTextArea(15, 50);
//            jta.setEditable(false);

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");


            JPanel panel = new JPanel(new BorderLayout());
            jd.setContentPane(panel);
//            Container pane = jd.getContentPane();
//            panel.add(jta, BorderLayout.CENTER);

            JPanel subPanel = new JPanel();
            if (vSet.size() == 1) {
                subPanel.add(new JLabel("Property term list"));
            } else {
                subPanel.add(new JLabel("Property term lists"));
            }
            panel.add(subPanel, BorderLayout.NORTH);
//            jta.setPreferredSize(new Dimension(250, 400));

//            JScrollPane jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            JScrollPane jsp = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            jsp.setPreferredSize(new Dimension(450, 300));
            jsp.setMinimumSize(new Dimension(450, 300));

            panel.add(jsp, BorderLayout.CENTER);
            StringBuilder stb = new StringBuilder();
            stb.append("<html>");

            int j = 0;
            for (Object ver : vSet) {
                if (ver instanceof BioObject) {
                    BioObject bioObj = (BioObject) ver;
//                    String res = "";
                    if (bioObj.getStandardName().length() != 0) {
                        stb.append("<b>" + (1 + j) + ".</b> <b>" + bioObj.getStandardName() + "</b>");
                    } else {
                        stb.append("<b>" + (1 + j) + ".</b> <b>" + bioObj.getName() + "</b>");
                    }

                } else if (ver instanceof Set) {
                    Set set = (Set) ver;
//                    String res = "";
                    stb.append("<b>" + (1 + j) + ".</b> Number of nodes inside: <b>" + set.size() + "</b>");
                }


                DataVector vec = nodesPropVectorMap.get(ver);

                if (vec != null) {
                    String res = "";
                    Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {

                        public int compare(Object o1, Object o2) {
                            Entry<String, Double> e1 = (Entry<String, Double>) o1;
                            Entry<String, Double> e2 = (Entry<String, Double>) o2;
                            System.out.println(e1.getKey());
                            if (e1.getValue() > e2.getValue()) {
                                return -1;
                            } else if (e1.getValue() < e2.getValue()) {
                                return 1;
                            } else {
                                PropertyTerm p1 = PropInfoProcessor.propTermsMap.get(e1.getKey());
                                PropertyTerm p2 = PropInfoProcessor.propTermsMap.get(e2.getKey());
                                if (p1.getWeight() > p2.getWeight()) {
                                    return -1;
                                } else if (p1.getWeight() < p2.getWeight()) {
                                    return 1;
                                } else {
                                    String name1 = PropInfoProcessor.propTermsMap.get(e1.getKey()).getName();
                                    String name2 = PropInfoProcessor.propTermsMap.get(e2.getKey()).getName();
                                    return name1.compareTo(name2);
                                }

                            }
//                            else {
//                            return 0;
//                            }
                        }
                    });

                    sortedMap.addAll(vec.getValueMap().entrySet());
                    ////DEBUG////
//                    printSortedMap(sortedMap);
                    /////////////

                    int i = 0;

                    for (Entry entry : sortedMap) {

                        PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) entry.getKey());
                        String toPrint = propTerm.getName();

//                        if (toPrint.length() > 20) {
//                            toPrint = toPrint.substring(0, 20);
//                            toPrint += "...";
//                        }

                        DecimalFormat myFormatter = new DecimalFormat(".00");
                        String output = myFormatter.format(entry.getValue());

                        res += "<br>- ";
                        NameSpace ns = PropInfoProcessor.getNamespaceMap().get(propTerm.getNamespace().getName());
                        res += "<font color = rgb(" + ns.getColor().getRed() + "," + ns.getColor().getGreen() + "," + ns.getColor().getBlue() + ")>";
//                        if (propTerm.getNamespace().equals(NameSpace.CC)) {
//                            res += "<font color = #ED143F>";
//                        } else if (propTerm.getNamespace().equals(NameSpace.MF)) {
//                            res += "<font color = #338F06>";
//                        } else {
//                            res += "<font color = #3425B1>";
//                        }
                        res += "" + toPrint + " (" + output + ")";
                        res += "</font>";

                        i++;
                        /* list up to 7 terms */
                        if (i == 10) {
                            break;
                        }

                    }
                    if (i == 0) {
                        res += "<br><i>No Information</i>";
                    }
                    stb.append(res);
                } else {
                    if (ver instanceof BioObject) {
                        String res = "";
                        BioObject bio = (BioObject) ver;
                        ArrayList<String> propTermIdList = bio.getPropTermList();
                        int i = 0;
                        for (String s : propTermIdList) {
                            res += "<br>- " + s;// + " with " + String.format("%.3f", entry.getValue());
                            i++;
                            if (i == 10) {
                                break;
                            }
                        }
                        if (i == 0) {
                            res += "<br><i>No Information</i>";
                        }
                        stb.append(res);
                    }
                }


                stb.append("<p>");
                j++;
            }
            stb.append("</html>");
            editorPane.setText(stb.toString());
            editorPane.setCaretPosition(0);
            editorPane.setPreferredSize(new Dimension(450, 300));

            JButton closeButton = new JButton("Close");
            subPanel = new JPanel();
            subPanel.add(closeButton);
            panel.add(subPanel, BorderLayout.SOUTH);

            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    jd.setVisible(false);

                }
            });
            jd.setPreferredSize(new Dimension(450, 385));
//            jd.setResizable(false);
            jd.pack();
            jd.setLocation((int) (NaviClusterApp.this.getPreferredSize().getWidth() / 2 - jd.getPreferredSize().getWidth() / 2),
                    (int) (NaviClusterApp.this.getPreferredSize().getHeight() / 2 - jd.getPreferredSize().getHeight() / 2));
            jd.getRootPane().setDefaultButton(closeButton);
            closeButton.requestFocusInWindow();
            jd.setVisible(true);

        }

        protected void showEdgesDetail(Set<Object> eSet) {
            final JDialog jd = new JDialog(NaviClusterApp.this);
            jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            jd.setLocationRelativeTo(NaviClusterApp.this);

//            JTextArea jta = new JTextArea(15, 50);
//            jta.setEditable(false);

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");


            JPanel panel = new JPanel(new BorderLayout());
            jd.setContentPane(panel);
//            Container pane = jd.getContentPane();
//            panel.add(jta, BorderLayout.CENTER);
            JPanel subPanel = new JPanel();
            if (eSet.size() == 1) {
                if (eSet.iterator().next() instanceof BioEdge) {
                    subPanel.add(new JLabel("Edge details"));
                } else if (eSet.iterator().next() instanceof MetaEdge) {
                    subPanel.add(new JLabel("Meta-Edge details"));
                } else if (eSet.iterator().next() instanceof PropertyEdge) {
                    subPanel.add(new JLabel("Property Edge details"));
                }
            } else {
                subPanel.add(new JLabel("Edge details"));
            }
            panel.add(subPanel, BorderLayout.NORTH);
//            jta.setPreferredSize(new Dimension(250, 400));

//            JScrollPane jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            JScrollPane jsp = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            jsp.setPreferredSize(new Dimension(450, 300));
            jsp.setMinimumSize(new Dimension(450, 300));

            panel.add(jsp, BorderLayout.CENTER);
            StringBuilder stb = new StringBuilder();
            stb.append("<html>");

            int j = 0;
            for (Object edge : eSet) {
                Pair pair = vv.getGraphLayout().getGraph().getEndpoints(edge);
                Object firstNode = pair.getFirst();
                Object secondNode = pair.getSecond();
                String fString = "", sString = "";
                if (firstNode instanceof Set) {
                    fString += "Cluster (" + ((Set) firstNode).size() + " nodes)";
                } else if (firstNode instanceof BioObject) {
                    fString += firstNode.toString();
                }
                if (secondNode instanceof Set) {
                    sString += "Cluster (" + ((Set) secondNode).size() + " nodes)";
                } else if (secondNode instanceof BioObject) {
                    sString += secondNode.toString();
                }
                if (edge instanceof MetaEdge) {
                    MetaEdge metaedge = (MetaEdge) edge;
//                    String res = "";

                    stb.append("<b>" + (1 + j) + ".</b> Metaedge: <b>" + fString + "</b> &lt;--> <b>" + sString + "</b>");
                    stb.append("<br>- <b>" + metaedge.getNumEdgeSetBundled() + "</b> edges bundled");
                    for (Object bundEdge : metaedge.getEdgeSetBundled()) {
                        stb.append("<br>&nbsp;&nbsp;&nbsp;- " + realGraph.getEndpoints(bundEdge).getFirst() + " &lt;--> " + realGraph.getEndpoints(bundEdge).getSecond() + " weight: " + ((BioEdge) bundEdge).getWeight());
                    }

                } else if (edge instanceof PropertyEdge) {
                    PropertyEdge propedge = (PropertyEdge) edge;
                    stb.append("<b>" + (1 + j) + ".</b> Property edge: <b>" + fString + "</b> &lt;--> <b>" + sString + "</b>");
                    stb.append("<br>- Weight = <b>" + String.format("%.5f", propedge.getInnerProduct()) + "</b>");

                    Set toPrintPropTermSet = new HashSet();
                    toPrintPropTermSet.add(firstNode);
                    toPrintPropTermSet.add(secondNode);

                    int k = 0;
                    for (Object forVec : toPrintPropTermSet) {
                        if (k == 0) {
                            stb.append("<br>&nbsp;&nbsp;&nbsp;- <b>" + fString + "</b>'s terms:");
                        } else {
                            stb.append("<br>&nbsp;&nbsp;&nbsp;- <b>" + sString + "</b>'s terms:");
                        }
                        k++;
                        DataVector vec = nodesPropVectorMap.get(forVec);

                        if (vec != null) {
                            String res = "";
                            Set<Entry<String, Double>> sortedMap = new TreeSet<Entry<String, Double>>(new Comparator() {

                                public int compare(Object o1, Object o2) {
                                    Entry<String, Double> e1 = (Entry<String, Double>) o1;
                                    Entry<String, Double> e2 = (Entry<String, Double>) o2;
                                    if (e1.getValue() > e2.getValue()) {
                                        return -1;
                                    } else if (e1.getValue() < e2.getValue()) {
                                        return 1;
                                    } else {
                                        PropertyTerm p1 = PropInfoProcessor.propTermsMap.get(e1.getKey());
                                        PropertyTerm p2 = PropInfoProcessor.propTermsMap.get(e2.getKey());
                                        if (p1.getWeight() > p2.getWeight()) {
                                            return -1;
                                        } else if (p1.getWeight() < p2.getWeight()) {
                                            return 1;
                                        } else {
                                            String name1 = PropInfoProcessor.propTermsMap.get(e1.getKey()).getName();
                                            String name2 = PropInfoProcessor.propTermsMap.get(e2.getKey()).getName();
                                            return name1.compareTo(name2);
                                        }

                                    }
//                                    else {
//                                    return 0;
//                                    }
                                }
                            });

                            sortedMap.addAll(vec.getValueMap().entrySet());

                            int i = 0;

                            for (Entry entry : sortedMap) {

                                PropertyTerm propTerm = PropInfoProcessor.propTermsMap.get((String) entry.getKey());
                                String toPrint = propTerm.getName();

//                        if (toPrint.length() > 20) {
//                            toPrint = toPrint.substring(0, 20);
//                            toPrint += "...";
//                        }

                                DecimalFormat myFormatter = new DecimalFormat(".00");
                                String output = myFormatter.format(entry.getValue());

                                res += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- ";
                                NameSpace ns = PropInfoProcessor.getNamespaceMap().get(propTerm.getNamespace().getName());
                                res += "<font color = rgb(" + ns.getColor().getRed() + "," + ns.getColor().getGreen() + "," + ns.getColor().getBlue() + ")>";
//                                if (propTerm.getNamespace().equals(NameSpace.CC)) {
//                                    res += "<font color = #ED143F>";
//                                } else if (propTerm.getNamespace().equals(NameSpace.MF)) {
//                                    res += "<font color = #338F06>";
//                                } else {
//                                    res += "<font color = #3425B1>";
//                                }
                                res += "" + toPrint + " (" + output + ")";
                                res += "</font>";

                                i++;
                                /* list up to 7 terms */
                                if (i == 10) {
                                    break;
                                }

                            }
                            if (i == 0) {
                                res += " <i>No Information</i>";
                            }
                            stb.append(res);
                        } else {
                            if (forVec instanceof BioObject) {
                                String res = "";
                                BioObject bio = (BioObject) forVec;
                                ArrayList<String> propTermIdList = bio.getPropTermList();
                                int i = 0;
                                for (String s : propTermIdList) {
                                    res += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- " + s;// + " with " + String.format("%.3f", entry.getValue());
                                    i++;
                                    if (i == 10) {
                                        break;
                                    }
                                }
                                if (i == 0) {
                                    res += " <i>No Information</i>";
                                }
                                stb.append(res);
                            }
                        }
                    }

//                    Set flatClust = (Set) ver;
////                    String res = "";
//                    stb.append((1 + j) + ". Number of nodes inside: <b><font size=+1>" + flatClust.size() + "</font></b>");
                } else if (edge instanceof SGDInteraction) {
                    SGDInteraction sgdint = (SGDInteraction) edge;
                    stb.append("<b>" + (1 + j) + ".</b> Yeast SGD interaction: <b>" + fString + "</b> &lt;--> <b>" + sString + "</b>");
                    stb.append("<br>- Number of evidences = <b>" + sgdint.getNumEvidence() + "</b>");
                } else if (edge instanceof BioEdge) {
                    BioEdge bioedge = (BioEdge) edge;
                    stb.append("<b>" + (1 + j) + ".</b> Edge: <b>" + fString + "</b> &lt;--> <b>" + sString + "</b>");
                    stb.append("<br>- Weight = <b>" + bioedge.getWeight() + "</b>");
                } else {
                    stb.append("<b>" + (1 + j) + ".</b> Edge: <b>" + fString + "</b> &lt;--> <b>" + sString + "</b>");
                    stb.append("<br>- Weight = <b>" + 1 + "</b>");
                }
                stb.append("<br><i>Other edge(s) between these two entities: </i>");
                Collection edgeCol = vv.getGraphLayout().getGraph().findEdgeSet(firstNode, secondNode);
                boolean have = false;
                for (Object concurEdge : edgeCol) {
                    if (!concurEdge.equals(edge)) {
                        if (concurEdge instanceof MetaEdge) {
                            have = true;
                            stb.append("<br>- Metaedge (<b>" + ((MetaEdge) concurEdge).getNumEdgeSetBundled() + "</b> edges bundled)");
                        } else if (concurEdge instanceof PropertyEdge) {
                            have = true;
                            stb.append("<br>- Property Edge (weight = <b>" + String.format("%.5f", ((PropertyEdge) concurEdge).getInnerProduct()) + "</b>)");
                        } else if (concurEdge instanceof SGDInteraction) {
                            have = true;
                            stb.append("<br>- Number of evidences = <b>" + ((SGDInteraction) concurEdge).getNumEvidence() + "</b>");
                        } else if (concurEdge instanceof BioEdge) {
                            have = true;
                            stb.append("<br>- Weight = <b>" + ((BioEdge) concurEdge).getWeight() + "</b>");
                        } else {
                            have = true;
                            stb.append("<br>- Weight = <b>" + 1 + "</b>");
                        }
                    }
                }
                if (!have) {
                    stb.append("<b>None</b>");
                }

                stb.append("<p>");
                j++;
            }
            stb.append("</html>");
            editorPane.setText(stb.toString());
            editorPane.setCaretPosition(0);
//            editorPane.setPreferredSize(new Dimension(450,300));

            JButton closeButton = new JButton("Close");
            subPanel = new JPanel();
            subPanel.add(closeButton);
            panel.add(subPanel, BorderLayout.SOUTH);
////            JOptionPane.showConfirmDialog(NaviClusterApp.this, "hey");
////            JOptionPane.showMessageDialog(NaviClusterApp.this, "hey");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    jd.setVisible(false);

                }
            });
            closeButton.requestFocusInWindow();
            jd.setPreferredSize(new Dimension(450, 385));
            jd.setLocation((int) (NaviClusterApp.this.getPreferredSize().getWidth() / 2 - jd.getPreferredSize().getWidth() / 2),
                    (int) (NaviClusterApp.this.getPreferredSize().getHeight() / 2 - jd.getPreferredSize().getHeight() / 2));
            jd.pack();
            jd.getRootPane().setDefaultButton(closeButton);
//            jd.setResizable(false);
            closeButton.requestFocusInWindow();
            jd.setVisible(true);
        }

        @SuppressWarnings("unchecked")
        protected void handlePopup(MouseEvent e) {
            final VisualizationViewer<Object, Object> vv =
                    (VisualizationViewer<Object, Object>) e.getSource();
            Point2D p = e.getPoint();

            boolean handledOnSite = false;
            GraphElementAccessor<Object, Object> pickSupport = vv.getPickSupport();
            if (pickSupport != null) {
                final Object v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
                /* Vertex is picked */
                if (v != null) {
                    handledOnSite = true;
                    JPopupMenu popup = new JPopupMenu();
                    final Set<Object> verSet = new HashSet();
                    verSet.add(v);

                    popup.add(new AbstractAction("Top 10 Property Terms") {

                        public void actionPerformed(ActionEvent e) {
                            showPropTermList(verSet);

                            vv.repaint();
                        }
                    });
                    if (!(v instanceof BioObject)) {
                        popup.add(new AbstractAction("Node List w/ Top Score Property Terms") {

                            public void actionPerformed(ActionEvent e) {
                                showNodeList(verSet);

                                vv.repaint();
                            }
                        });
                        popup.add(new AbstractAction("Edge Density") {

                            public void actionPerformed(ActionEvent e) {
                                showEdgeDensity(verSet);

                                vv.repaint();
                            }
                        });
                    }
                    if (v instanceof BioObject) {
                        popup.add(new AbstractAction("Show Information from Relevant Online Database") {

                            public void actionPerformed(ActionEvent e) {
                                try {
                                    showInfoFromWeb((BioObject) v);
                                } catch (IOException ex) {
                                    Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                vv.repaint();
                            }
                        });
                    }
                    popup.show(vv, e.getX(), e.getY());
                } else {
                    /* edge is picked */
                    final Object edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
                    if (edge != null) {
                        handledOnSite = true;
                        final Set<Object> edgeSet = new HashSet();
                        edgeSet.add(edge);
                        JPopupMenu popup = new JPopupMenu();
                        String label = "View Edge Details";
                        if (edge instanceof MetaEdge) {
                            label = "View Meta-Edge Details";
                        } else if (edge instanceof PropertyEdge) {
                            label = "View Property Edge Details";
                        }
                        popup.add(new AbstractAction(label) {

                            public void actionPerformed(ActionEvent e) {
                                showEdgesDetail(edgeSet);
//                        System.err.println("got " + edge);
                            }
                        });
                        popup.show(vv, e.getX(), e.getY());

                    }
                }
            }
            if (!handledOnSite) {
                final Set<Object> vSet = vv.getPickedVertexState().getPicked();
                final Set<Object> eSet = vv.getPickedEdgeState().getPicked();
                if (vSet.size() != 0 && eSet.size() != 0) {
                } else if (vSet.size() != 0) {
                    /* vertices are picked */
                    JPopupMenu popup = new JPopupMenu();
                    popup.add(new AbstractAction("Top 10 Property Terms") {

                        public void actionPerformed(ActionEvent e) {
                            showPropTermList(vSet);
                            vv.repaint();
                        }
                    });
                    if (vSet.size() > 1 || (vSet.size() == 1 && !(vSet.iterator().next() instanceof BioObject))) {
                        popup.add(new AbstractAction("Node Lists w/ Top Score Property Terms") {

                            public void actionPerformed(ActionEvent e) {
                                showNodeList(vSet);
                                vv.repaint();
                            }
                        });
                        popup.add(new AbstractAction("Edge Density") {

                            public void actionPerformed(ActionEvent e) {
                                showEdgeDensity(vSet);

                                vv.repaint();
                            }
                        });
                    }
                    if (vSet.size() == 1) {
                        final Object ver = vSet.iterator().next();
                        if (ver instanceof BioObject) {
                            popup.add(new AbstractAction("Show Information from Relevant Online Database") {

                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        showInfoFromWeb((BioObject) ver);
                                    } catch (IOException ex) {
                                        Logger.getLogger(NaviClusterApp.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    vv.repaint();
                                }
                            });
                        }
                    }
                    popup.show(vv, e.getX(), e.getY());
                } else if (eSet.size() != 0) {
                    Object edge = null;
                    if (eSet.size() == 1) {
                        edge = eSet.iterator().next();
                    }
                    /* edges are picked */
                    JPopupMenu popup = new JPopupMenu();
                    String label = "View Edge Details";
                    if (edge instanceof MetaEdge) {
                        label = "View Meta-Edge Details";
                    } else if (edge instanceof PropertyEdge) {
                        label = "View Property Edge Details";
                    }
                    popup.add(new AbstractAction(label) {

                        public void actionPerformed(ActionEvent e) {
                            showEdgesDetail(eSet);
//                        System.err.println("got " + edge);
                        }
                    });
                    popup.show(vv, e.getX(), e.getY());
                }
            }
//            else {
//
//            }


        }
    }
}
