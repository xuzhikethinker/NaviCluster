/*
 * MakeCustomGraphViewDialog.java
 *
 * Created on Jul 3, 2009, 4:14:35 PM
 */
package main;

import objects.BioObject;
import edu.uci.ics.jung.graph.Graph;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class MakeCustomGraphViewDialog extends javax.swing.JDialog {

    /** Creates new form MakeCustomGraphViewDialog */
    public MakeCustomGraphViewDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public MakeCustomGraphViewDialog(java.awt.Frame parent, boolean modal, Graph dynamicGraph, Graph realGraph) {
        this(parent, modal);
        this.dynamicGraph = dynamicGraph;
        this.realGraph = realGraph;
        loadGraphData();
    }

    public Graph getDynamicGraph() {
        return dynamicGraph;
    }

    public void setDynamicGraph(Graph dynamicGraph) {
        this.dynamicGraph = dynamicGraph;
    }

    public Graph getRealGraph() {
        return realGraph;
    }

    public void setRealGraph(Graph realGraph) {
        this.realGraph = realGraph;
    }

    //compare by using standard name
    private class BioObjectComparator implements Comparator<BioObject> {

        public int compare(BioObject o1, BioObject o2) {
            return o1.toString().compareToIgnoreCase(o2.toString());
        }
    }

    public void loadGraphData() {

        nodesOutsideViewSet.addAll(realGraph.getVertices());
        for (Object ver : dynamicGraph.getVertices()) {
            if (ver instanceof Set) {
                nodesInsideClusterSet.addAll((Set) ver);
            } else if (ver instanceof BioObject) {
                nodesOutsideClusterSet.add(ver);
            }
            nodesClustersInViewSet.add(ver);
        }

        /* all clusters/nodes in the current view */
        nodesClustersInViewLM = new DefaultListModel();
        oriNodesClustersInViewLM = new DefaultListModel();
        nodesClustersInViewLM.addElement("<html><b>All clusters and nodes in the current view: " + nodesClustersInViewSet.size() + " clusters/nodes</b></html>");
        for (Object ver : nodesClustersInViewSet) {
            nodesClustersInViewLM.addElement(ver);
        }
        for (Enumeration e = nodesClustersInViewLM.elements(); e.hasMoreElements();) {
            oriNodesClustersInViewLM.addElement(e.nextElement());
        }
        nodesClustersInViewJList.setModel(nodesClustersInViewLM);

        /* all objects insides clusters in the current view */
        nodesInsideClusterLM = new DefaultListModel();
        oriNodesInsideClusterLM = new DefaultListModel();
        nodesInsideClusterLM.addElement("<html><b>All nodes inside clusters in the current view: " + nodesInsideClusterSet.size() + " nodes</b></html>");
        for (Object ver : nodesInsideClusterSet) {
            nodesInsideClusterLM.addElement(ver);
        }
        for (Enumeration e = nodesInsideClusterLM.elements(); e.hasMoreElements();) {
            oriNodesInsideClusterLM.addElement(e.nextElement());
        }
        nodesInsideClusterJList.setModel(nodesInsideClusterLM);

        nodesInsideClusterSetCopy.addAll(nodesInsideClusterSet);
        // remove all nodes which were in the clusters
        nodesOutsideViewSet.removeAll(nodesInsideClusterSet);
        // remove all nodes in the current view which were not in the clusters
        nodesOutsideViewSet.removeAll(nodesOutsideClusterSet);

        nodesOutsideViewLM = new DefaultListModel();
        oriNodesOutsideViewLM = new DefaultListModel();
        nodesOutsideViewLM.removeAllElements();
        
        /* all nodes not in the current view and not in clusters in the current view */
        nodesOutsideViewLM.addElement("<html><b>All nodes NOT in the current view: " + nodesOutsideViewSet.size() + " nodes</b></html>");
        for (Object ver : nodesOutsideViewSet) {
            nodesOutsideViewLM.addElement(ver);
        }
        for (Enumeration e = nodesOutsideViewLM.elements(); e.hasMoreElements();) {
            oriNodesOutsideViewLM.addElement(e.nextElement());
        }
        nodesOutsideViewJList.setModel(nodesOutsideViewLM);

        selectedNodesJList.setModel(selectedNodesLM);
        selectedNodesLM.addElement("<html><b>Selected items: 0 items</b></html>");
        oriSelectedNodesLM.addElement("<html><b>Selected items: 0 items</b></html>");

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        searchBox = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        AddButton = new javax.swing.JButton();
        RemoveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        selectedNodesJList = new javax.swing.JList();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        nodesClustersInViewJList = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        nodesInsideClusterJList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        nodesOutsideViewJList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        OKButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Make Custom Graph View");
        setResizable(false);

        jLabel2.setText("Select clusters/nodes from the left and press >> to move them to the right");

        jLabel3.setText("If you don't need some clusters/nodes on the right panel, select them and press << to move them back");

        jLabel4.setText("When you finish selection, press OK. Press Cancel to return to the main program");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jLabel4)
                    .add(jLabel3))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setText("Search for a node (Press Esc to clear the box):");

        searchBox.setText("Type name and press Enter");
        searchBoxBg = searchBox.getBackground();
        searchBox.getDocument().addDocumentListener(new SearchBoxDocListener());
        InputMap im = searchBox.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = searchBox.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
        am.put(CANCEL_ACTION, new CancelAction());
        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        searchBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                removeDefaultText(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                insertDefaultText(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabel5)
                .add(18, 18, 18)
                .add(searchBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 234, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(32, 32, 32))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(searchBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        AddButton.setText(">>");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        jPanel2.add(AddButton);

        RemoveButton.setText("<<");
        RemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveButtonActionPerformed(evt);
            }
        });
        jPanel2.add(RemoveButton);

        jScrollPane1.setViewportView(selectedNodesJList);

        jSplitPane1.setBorder(null);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.7);
        jSplitPane1.setOneTouchExpandable(true);

        jSplitPane2.setBorder(null);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPane2.setOneTouchExpandable(true);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setBorder(null);
        jScrollPane3.setViewportView(jTextArea3);

        jSplitPane2.setRightComponent(jScrollPane3);

        nodesClustersInViewJList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "<html><b>All clusters and nodes in the current view</b></html>" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(nodesClustersInViewJList);

        jSplitPane2.setLeftComponent(jScrollPane2);

        nodesInsideClusterJList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "<html><b>All nodes inside clusters in the current view</b></html>" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(nodesInsideClusterJList);

        jSplitPane2.setBottomComponent(jScrollPane5);

        jSplitPane1.setLeftComponent(jSplitPane2);

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setBorder(null);
        jScrollPane4.setViewportView(jTextArea4);

        jSplitPane1.setRightComponent(jScrollPane4);

        nodesOutsideViewJList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "<html><b>All nodes NOT in the current view</b></html>" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        nodesOutsideViewJList.setName(""); // NOI18N
        jScrollPane6.setViewportView(nodesOutsideViewJList);

        jSplitPane1.setBottomComponent(jScrollPane6);

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                .addContainerGap())
        );

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Make a new view from the selected clusters/nodes?");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 211, Short.MAX_VALUE)
                .add(OKButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(CancelButton)
                .add(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(CancelButton)
                    .add(OKButton)
                    .add(jLabel1))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 570, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, mainPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.setVisible(false);
}//GEN-LAST:event_CancelButtonActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        if (selectedNodesLM.size() <= 1) {
            answerOK = false;
        } else {
            answerOK = true;
        }
        this.setVisible(false);
        selectedNodesLM.remove(0);
        selectedNodeSet = new HashSet(Arrays.asList(selectedNodesLM.toArray()));
    }//GEN-LAST:event_OKButtonActionPerformed

    private void RemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveButtonActionPerformed

        int selectedIndArr[];
        int start = 0;
        selectedIndArr = selectedNodesJList.getSelectedIndices();
        start = 0;
        if (selectedIndArr.length > 0) {
            if (selectedIndArr[0] == 0) 
                start = 1;

            for (int i = start; i < selectedIndArr.length; i++) {
                Object item = selectedNodesLM.elementAt(selectedIndArr[i] - (i - start));
                if (dynamicGraph.containsVertex(item)) {
                    nodesClustersInViewSet.add(item);
                } else if (nodesInsideClusterSetCopy.contains(item)) {
                    nodesInsideClusterSet.add(item);
                } else {
                    nodesOutsideViewSet.add(item);
                }
                selectedNodesLM.removeElementAt(selectedIndArr[i] - (i - start));
            }

            selectedNodesJList.clearSelection();
            selectedNodesLM.set(0, "<html><b>Selected items: " + (selectedNodesLM.size() - 1) + " items</b></html>");

            /* re-populate the list */
            if (nodesClustersInViewLM.size() > 1) {
                nodesClustersInViewLM.removeRange(1, nodesClustersInViewLM.getSize() - 1);
            }
            for (Object ver : nodesClustersInViewSet) {
                nodesClustersInViewLM.addElement(ver);
            }
            nodesClustersInViewLM.set(0, "<html><b>All clusters and nodes in the current view: " + (nodesClustersInViewSet.size()) + " nodes</b></html>");

            /* re-populate the list */
            if (nodesInsideClusterLM.size() > 1) {
                nodesInsideClusterLM.removeRange(1, nodesInsideClusterLM.getSize() - 1);
            }
            for (Object ver : nodesInsideClusterSet) {
                nodesInsideClusterLM.addElement(ver);
            }
            nodesInsideClusterLM.set(0, "<html><b>All nodes inside clusters in the current view: " + (nodesInsideClusterSet.size()) + " nodes</b></html>");

            /* re-populate the list */
            if (nodesOutsideViewLM.size() > 1) {
                nodesOutsideViewLM.removeRange(1, nodesOutsideViewLM.getSize() - 1);
            }
            for (Object ver : nodesOutsideViewSet) {
                nodesOutsideViewLM.addElement(ver);
            }
            nodesOutsideViewLM.set(0, "<html><b>All nodes NOT in the current view: " + (nodesOutsideViewSet.size()) + " nodes</b></html>");

        }

    }//GEN-LAST:event_RemoveButtonActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed


        int selectedIndArr[];
        int start = 0;

        /* process JList of nodes/clusters in the current view */
        selectedIndArr = nodesClustersInViewJList.getSelectedIndices();
        Set nodesInsideClustersOfThisViewSet = new HashSet();
        if (selectedIndArr.length > 0) {
            start = 0;
            /* remove the first row, which is a description text */
            if (selectedIndArr[0] == 0) 
                start = 1;

            for (int i = start; i < selectedIndArr.length; i++) {
                Object item = nodesClustersInViewLM.elementAt(selectedIndArr[i] - (i - start));
                selectedNodesLM.addElement(item);
                nodesClustersInViewSet.remove(item);
                nodesClustersInViewLM.removeElementAt(selectedIndArr[i] - (i - start));
            }
            nodesClustersInViewJList.clearSelection();
            nodesClustersInViewLM.set(0, "<html><b>All clusters and nodes in the current view: " + (nodesClustersInViewLM.size() - 1) + " nodes</b></html>");
        }

        for (Object member : selectedNodesLM.toArray()) {
            if (member instanceof Set) {
                nodesInsideClustersOfThisViewSet.addAll((Set) member);
            }
        }
        
        /* process JList of nodes inside clusters in the current view */
        /* skip used to not redundantly add nodes which are already included in a cluster which was already added */
        int skip = 0;
        selectedIndArr = nodesInsideClusterJList.getSelectedIndices();
        if (selectedIndArr.length > 0) {
            start = 0;
            /* remove the first row, which is a description text */
            if (selectedIndArr[0] == 0) 
                start = 1;

            for (int i = start; i < selectedIndArr.length; i++) {
                Object item = nodesInsideClusterLM.elementAt(selectedIndArr[i] - (i - start) + skip);
                if (nodesInsideClustersOfThisViewSet.contains(item)) {
                    skip++;
                } else {
                    selectedNodesLM.addElement(item);
                    nodesInsideClusterSet.remove(item);
                    nodesInsideClusterLM.removeElementAt(selectedIndArr[i] - (i - start) + skip);
                }
            }
            nodesInsideClusterJList.clearSelection();
            nodesInsideClusterLM.set(0, "<html><b>All nodes inside clusters in the current view: " + (nodesInsideClusterLM.size() - 1) + " nodes</b></html>");

        }

        /* process JList of nodes outside the current view */
        selectedIndArr = nodesOutsideViewJList.getSelectedIndices();
        start = 0;
        if (selectedIndArr.length > 0) {
            if (selectedIndArr[0] == 0) 
                start = 1;

            for (int i = start; i < selectedIndArr.length; i++) {
                Object item = nodesOutsideViewLM.elementAt(selectedIndArr[i] - (i - start));
                selectedNodesLM.addElement(item);
                nodesOutsideViewSet.remove(item);
                nodesOutsideViewLM.removeElementAt(selectedIndArr[i] - (i - start));
            }
            nodesOutsideViewJList.clearSelection();
            nodesOutsideViewLM.set(0, "<html><b>All nodes NOT in the current view: " + (nodesOutsideViewLM.size() - 1) + " nodes</b></html>");
        }
        selectedNodesLM.set(0, "<html><b>Selected items: " + (selectedNodesLM.size() - 1) + " items</b></html>");
}//GEN-LAST:event_AddButtonActionPerformed

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_searchBoxActionPerformed
    private int removeTextCounter = 0;
    private void removeDefaultText(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_removeDefaultText
//        if (removeTextCounter > 0)
//            searchBox.setText("");
//        removeTextCounter++;
    }//GEN-LAST:event_removeDefaultText

    private void insertDefaultText(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_insertDefaultText
        // TODO add your handling code here:
//        if (searchBox.getText().isEmpty()) {
//            searchBox.setText("Type name and press Enter");
//            searchBox.setBackground(searchBoxBg);
//        }
    }//GEN-LAST:event_insertDefaultText

    public boolean isAnswerOK() {
        return answerOK;
    }
    
    public Set getSelectedNodeSet() {
        return selectedNodeSet;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MakeCustomGraphViewDialog dialog = new MakeCustomGraphViewDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JButton RemoveButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JPanel mainPanel;
    /* nodesClustersInViewJList is a JList of nodesClustersInViewSet */
    private javax.swing.JList nodesClustersInViewJList;
    /* nodesInsideClusterJList is a JList of nodesInsideClusterSet*/
    private javax.swing.JList nodesInsideClusterJList;
    /* nodesOutsideViewJList is a JList of nodesOutsideViewSet*/
    private javax.swing.JList nodesOutsideViewJList;
    private javax.swing.JTextField searchBox;
    private javax.swing.JList selectedNodesJList;
    // End of variables declaration//GEN-END:variables
    private Graph dynamicGraph;
    private Graph realGraph;
    
   
    
    /* nodesClustersInViewSet contains every cluster/node appearing in a view */
    private Set nodesClustersInViewSet = new TreeSet(new NodeClusterComparator());
    /* nodesClustersInViewLM is a list model of nodesClustersInViewSet */
    private DefaultListModel nodesClustersInViewLM;
    /* oriNodesClustersInViewLM is an unmodified version of nodesClustersInViewLM */
    private DefaultListModel oriNodesClustersInViewLM;
    
    /* nodesInsideClusterSet contains nodes insides clusters in a view */
    private Set nodesInsideClusterSet = new TreeSet(new BioObjectComparator());
    /* nodesInsideClusterLM is a list model of nodesInsideClusterSet */
    private DefaultListModel nodesInsideClusterLM;
    /* oriNodesInsideClusterLM is an unmodified version of nodesInsideClusterLM */
    private DefaultListModel oriNodesInsideClusterLM;
    
    /* nodesOutsideViewSet contains all nodes NOT appearing in a view */
    private Set nodesOutsideViewSet = new TreeSet(new BioObjectComparator());
    /* nodesOutsideViewLM is a list model of nodesOutsideViewSet */
    private DefaultListModel nodesOutsideViewLM;
    /* oriNodesOutsideViewLM is an unmodified version of nodesOutsideViewLM */
    private DefaultListModel oriNodesOutsideViewLM;
    
    /* selectedNodesLM is a list model of selectNodeJList */
    private DefaultListModel selectedNodesLM = new DefaultListModel();
    /* oriSelectedNodesLM is an unmodified version of selectedNodesLM */
    private DefaultListModel oriSelectedNodesLM = new DefaultListModel();
    
    private Set nodesOutsideClusterSet = new TreeSet(new BioObjectComparator());
    private Set nodesInsideClusterSetCopy = new TreeSet(new BioObjectComparator());
    
    private boolean answerOK = false;
    private Set selectedNodeSet = new HashSet();
    final Color ERROR_COLOR = Color.PINK;
    Color searchBoxBg;
    final static String CANCEL_ACTION = "cancel-search";

    class NodeClusterComparator implements Comparator {
        /* compare between cluster/cluster, cluster/BioObject, or BioObject/BioObject */
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof BioObject && o2 instanceof BioObject) {
                return o1.toString().compareToIgnoreCase(o2.toString());
            } else if (o1 instanceof Set && o2 instanceof Set) {
                if (((Set) o1).size() < ((Set) o2).size()) {
                    return -1;
                } else if (((Set) o1).size() > ((Set) o2).size()) {
                    return 1;
                } else {
                    if (((Set) o1).isEmpty()) {
                        return 0;
                    }
                    return ((Set) o1).iterator().next().toString().compareToIgnoreCase(((Set) o2).iterator().next().toString());
//                        return 0;
                }

            } else {
                if ((o1 instanceof BioObject) && (o2 instanceof Set)) {
                    return -1;
                } else if ((o1 instanceof Set) && (o2 instanceof BioObject)) {
                    return 1;
                } else {
                    return 0;
                }
            }

        }
    }

    class CancelAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ev) {
//            hilit.removeAllHighlights();
            searchBox.setText("");
            searchBox.setBackground(searchBoxBg);
        }
    }

    class SearchBoxDocListener implements DocumentListener {

        public void search() {
//            hilit.removeAllHighlights();

            /* clear all lists and populate all entities that are not selected */
            String s = searchBox.getText();

            /* not searched; Populate all lists as usual */
            if (s.length() <= 0) {
                searchBox.setText("");
                searchBox.setBackground(searchBoxBg);

                nodesClustersInViewLM = new DefaultListModel();
                for (Object o : nodesClustersInViewSet) {
                    nodesClustersInViewLM.addElement(o);
                }
                nodesClustersInViewLM.insertElementAt("<html><b>All clusters and nodes in the current view: " + nodesClustersInViewLM.size() + " clusters/nodes</b></html>", 0);
                nodesClustersInViewJList.setModel(nodesClustersInViewLM);

                nodesInsideClusterLM = new DefaultListModel();
                for (Object o : nodesInsideClusterSet) {
                    nodesInsideClusterLM.addElement(o);
                }
                nodesInsideClusterLM.insertElementAt("<html><b>All nodes inside clusters in the current view: " + nodesInsideClusterLM.size() + " nodes</b></html>", 0);
                nodesInsideClusterJList.setModel(nodesInsideClusterLM);

                nodesOutsideViewLM = new DefaultListModel();
                for (Object o : nodesOutsideViewSet) {
                    nodesOutsideViewLM.addElement(o);
                }
                nodesOutsideViewLM.insertElementAt("<html><b>All nodes NOT in the current view: " + nodesOutsideViewLM.size() + " nodes</b></html>", 0);
                nodesOutsideViewJList.setModel(nodesOutsideViewLM);

                return;
            }

            /* filter only nodes whose names start with the search name */
            
            /* all nodes and clusters in the current view */
            Set filteredNodesClustersInViewSet = new TreeSet(new NodeClusterComparator());
            for (Object obj : nodesClustersInViewSet) {
                if (obj instanceof BioObject) {
                    BioObject bioObj = (BioObject) obj;
                    if (bioObj.getName().toUpperCase().startsWith(s.toUpperCase()) || (bioObj.getStandardName().toUpperCase().startsWith(s.toUpperCase()))
                            || (bioObj.getObjName().toUpperCase().startsWith(s.toUpperCase()))) {
                        filteredNodesClustersInViewSet.add(bioObj);
                    } else {
                        for (String st : bioObj.getSynonym()) {
                            if (st.toUpperCase().startsWith(s.toUpperCase())) {
                                filteredNodesClustersInViewSet.add(bioObj);
                                break;
                            }
                        }

                    }
                    
                } else if (obj instanceof Set) {
                    Set set = (Set) obj;
                    for (Object element : set) {
                        if (element instanceof BioObject) {
                            BioObject bioObj = (BioObject) element;
                            if (bioObj.getName().toUpperCase().startsWith(s.toUpperCase()) || (bioObj.getStandardName().toUpperCase().startsWith(s.toUpperCase()))
                                    || (bioObj.getObjName().toUpperCase().startsWith(s.toUpperCase()))) {
                                filteredNodesClustersInViewSet.add(set);
                                break;
                            } else {
                                boolean br = false;
                                for (String st : bioObj.getSynonym()) {
                                    if (st.toUpperCase().startsWith(s.toUpperCase())) {
                                        filteredNodesClustersInViewSet.add(set);
                                        br = true;
                                    }
                                }
                                if (br) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            /* all nodes inside clusters in the current view */
            Set filteredNodesInsideClustersSet = new TreeSet(new NodeClusterComparator());
            for (Object obj : nodesInsideClusterSet) {
                if (obj instanceof BioObject) {
                    BioObject bioObj = (BioObject) obj;
                    if (bioObj.getName().toUpperCase().startsWith(s.toUpperCase()) || (bioObj.getStandardName().toUpperCase().startsWith(s.toUpperCase()))
                            || (bioObj.getObjName().toUpperCase().startsWith(s.toUpperCase()))) {
                        filteredNodesInsideClustersSet.add(bioObj);
                    } else {
                        for (String st : bioObj.getSynonym()) {
                            if (st.toUpperCase().startsWith(s.toUpperCase())) {
                                filteredNodesInsideClustersSet.add(bioObj);
                                break;
                            }
                        }
                    }
                }

            }

            /* all primitive entities in the graph */
            Set filteredNodesOutsideViewSet = new TreeSet(new NodeClusterComparator());
            for (Object obj : nodesOutsideViewSet) {
                if (obj instanceof BioObject) {
                    BioObject bioObj = (BioObject) obj;
                    if (bioObj.getName().toUpperCase().startsWith(s.toUpperCase()) || (bioObj.getStandardName().toUpperCase().startsWith(s.toUpperCase()))
                            || (bioObj.getObjName().toUpperCase().startsWith(s.toUpperCase()))) {
                        filteredNodesOutsideViewSet.add(bioObj);
                    } else {
                        for (String st : bioObj.getSynonym()) {
                            if (st.toUpperCase().startsWith(s.toUpperCase())) {
                                filteredNodesOutsideViewSet.add(bioObj);
                                break;
                            }
                        }
                    }
                }

            }

            /* update the three lists */
            if (filteredNodesClustersInViewSet.isEmpty() && filteredNodesOutsideViewSet.isEmpty() && filteredNodesInsideClustersSet.isEmpty()) {
                /* not found at all */
                searchBox.setBackground(ERROR_COLOR);
            } else {
                nodesClustersInViewLM = new DefaultListModel();
                if (!filteredNodesClustersInViewSet.isEmpty()) {
                    for (Object o : filteredNodesClustersInViewSet) {
                        nodesClustersInViewLM.addElement(o);
                    }
                }
                nodesClustersInViewLM.insertElementAt("<html><b>All clusters and nodes in the current view: " + nodesClustersInViewLM.size() + " clusters/nodes</b></html>", 0);
                nodesClustersInViewJList.setModel(nodesClustersInViewLM);

                nodesInsideClusterLM = new DefaultListModel();
                if (!filteredNodesInsideClustersSet.isEmpty()) {

                    for (Object o : filteredNodesInsideClustersSet) {
                        nodesInsideClusterLM.addElement(o);
                    }
                }
                nodesInsideClusterLM.insertElementAt("<html><b>All nodes inside clusters in the current view: " + nodesInsideClusterLM.size() + " nodes</b></html>", 0);
                nodesClustersInViewJList.setModel(nodesInsideClusterLM);

                nodesOutsideViewLM = new DefaultListModel();
                if (!filteredNodesOutsideViewSet.isEmpty()) {

                    for (Object o : filteredNodesOutsideViewSet) {
                        nodesOutsideViewLM.addElement(o);
                    }
                }
                nodesOutsideViewLM.insertElementAt("<html><b>All nodes NOT in the current view: " + nodesOutsideViewLM.size() + " nodes</b></html>", 0);
                nodesOutsideViewJList.setModel(nodesOutsideViewLM);

            }

        }

        @Override
        public void insertUpdate(DocumentEvent ev) {
            search();
        }

        @Override
        public void removeUpdate(DocumentEvent ev) {
            search();
        }

        @Override
        public void changedUpdate(DocumentEvent ev) {
        }
    }
}
