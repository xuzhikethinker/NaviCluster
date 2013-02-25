/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import objects.PropertyTerm;
import objects.BioObject;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kmean.DataVector;

/**
 *
 * @author Knacky
 */
public class ViewHistory {
    /*for now, try not storing these two below map*/
    Map<Set, Set> flatClustToComNodeClust = new HashMap<Set, Set>();
    Map<Set, Set> comNodeClustToFlatClust = new HashMap<Set, Set>();

    private PropInfoProcessor curGOPCentered = null;
    private boolean inConcentrationMode = false;
    private Set<Set> curCSetCentered = null;
    private Set<Set> curCSetCenteredBPC = null;
    private Set<Set> curComNodeCSetCenteredBPC = null;
    private Set<Set> curComNodeCSetCentered = null;

    private Map<Object, DataVector> nodesGOVectorMap = new HashMap<Object, DataVector>();
    private Set cSetBeforeVis, cSet;
    private Set comNodeCSetBeforeVis, comNodeCSet;
    private PropInfoProcessor propInfoProc;
    private Graph storedGraph;
    private Graph usualGraph;
    private Map nodeToClusterMap;
    private Map usualNodeToClusterMap;
    private Object highlightedNode = null;
    private BioObject searchedBioObj = null;
    private Color highlightedNodeFormerColor = null;
    private Layout layout = null;
    private Map<Object,Point2D> vertexLocationMap = null;
    private Map<Object,Integer> vertexSizeMap = null;
    private Map<Object,Point2D> usualGraphVerLocMap = null;

    private double maxBioEdge = 10.0;
    private double minBioEdge = 1.0;
    private double maxMetaEdge = 100.0;
    private double minMetaEdge = 1.0;
    private int maxNumMem = 100;
    private int minNumMem = 1;
    private int numNodesInCurrentGraphView = 0;
    private boolean lastLevel = false;
    private ArrayList clickedVertices = new ArrayList();
    private ArrayList recenteredNodes = new ArrayList();
    private Map colorMap = new HashMap();
    private boolean zoomInEnabled = true;

    Map<Object, ArrayList<PropertyTerm>> nodesLabelList = new HashMap<Object, ArrayList<PropertyTerm>>();
    
    public ViewHistory(Set cSetBeforeVis, Set comNodeCSetBeforeVis,PropInfoProcessor gop, Graph graph){
        this.cSetBeforeVis = cSetBeforeVis;
        this.comNodeCSetBeforeVis = comNodeCSetBeforeVis;
        this.propInfoProc = gop;
        this.storedGraph = graph;
    }
    public ViewHistory(Set cSetBeforeVis, Set comNodeCSetBeforeVis,PropInfoProcessor gop, Layout layout){
        this.cSetBeforeVis = cSetBeforeVis;
        this.comNodeCSetBeforeVis = comNodeCSetBeforeVis;
        this.propInfoProc = gop;
        this.layout = layout;
    }
    public ViewHistory(){
        
    }

    public int getNumNodesInCurrentGraphView() {
        return numNodesInCurrentGraphView;
    }

    public void setNumNodesInCurrentGraphView(int numNodesInCurrentGraphView) {
        this.numNodesInCurrentGraphView = numNodesInCurrentGraphView;
    }

    public boolean isZoomInEnabled() {
        return zoomInEnabled;
    }

    public void setZoomInEnabled(boolean zoomInEnabled) {
        this.zoomInEnabled = zoomInEnabled;
    }

    public double getMaxMetaEdge() {
        return maxMetaEdge;
    }

    public void setMaxMetaEdge(double maxMetaEdge) {
        this.maxMetaEdge = maxMetaEdge;
    }

    public int getMaxNumMem() {
        return maxNumMem;
    }

    public void setMaxNumMem(int maxNumMem) {
        this.maxNumMem = maxNumMem;
    }

    public double getMinMetaEdge() {
        return minMetaEdge;
    }

    public void setMinMetaEdge(double minMetaEdge) {
        this.minMetaEdge = minMetaEdge;
    }

    public int getMinNumMem() {
        return minNumMem;
    }

    public void setMinNumMem(int minNumMem) {
        this.minNumMem = minNumMem;
    }

    public double getMaxBioEdge() {
        return maxBioEdge;
    }

    public void setMaxBioEdge(double maxBioEdge) {
        this.maxBioEdge = maxBioEdge;
    }

    public double getMinBioEdge() {
        return minBioEdge;
    }

    public void setMinBioEdge(double minBioEdge) {
        this.minBioEdge = minBioEdge;
    }

    
    public Map<Object, DataVector> getNodesPropVectorMap() {
        return nodesGOVectorMap;
    }

    public void setNodesPropVectorMap(Map<Object, DataVector> nodesGOVectorMap) {
        this.nodesGOVectorMap = new HashMap<Object,DataVector>(nodesGOVectorMap);
    }


    public Map<Object, ArrayList<PropertyTerm>> getNodesLabelList() {
        return nodesLabelList;
    }

    public void setNodesLabelList(Map<Object, ArrayList<PropertyTerm>> nodesLabelList) {
        this.nodesLabelList = nodesLabelList;
    }

    
    public Set getcSet() {
        return cSet;
    }

    public void setcSet(Set cSet) {
        this.cSet = cSet;
    }

    public Set getcSetBeforeVis() {
        return cSetBeforeVis;
    }

    public void setcSetBeforeVis(Set cSetBeforeVis) {
        this.cSetBeforeVis = cSetBeforeVis;
    }

    public Set<Set> getCurCSetCentered() {
        return curCSetCentered;
    }

    public void setCurCSetCentered(Set<Set> curCSetCentered) {
        this.curCSetCentered = curCSetCentered;
    }

    public Set<Set> getCurCSetCenteredBPC() {
        return curCSetCenteredBPC;
    }

    public void setCurCSetCenteredBPC(Set<Set> curCSetCenteredBPC) {
        this.curCSetCenteredBPC = curCSetCenteredBPC;
    }

    public Set<Set> getCurComNodeCSetCentered() {
        return curComNodeCSetCentered;
    }

    public void setCurComNodeCSetCentered(Set<Set> curComNodeCSetCentered) {
        this.curComNodeCSetCentered = curComNodeCSetCentered;
    }

    public Set<Set> getCurComNodeCSetCenteredBPC() {
        return curComNodeCSetCenteredBPC;
    }

    public void setCurComNodeCSetCenteredBPC(Set<Set> curComNodeCSetCenteredBPC) {
        this.curComNodeCSetCenteredBPC = curComNodeCSetCenteredBPC;
    }

    public PropInfoProcessor getCurGOPCentered() {
        return curGOPCentered;
    }

    public void setCurGOPCentered(PropInfoProcessor curGOPCentered) {
        this.curGOPCentered = curGOPCentered;
    }

    public boolean isInConcentrationMode() {
        return inConcentrationMode;
    }

    public void setInConcentrationMode(boolean inConcentrationMode) {
        this.inConcentrationMode = inConcentrationMode;
    }
    
    public Map<Object, Point2D> getUsualGraphVerLocMap() {
        return usualGraphVerLocMap;
    }

    public void setUsualGraphVerLocMap(Map<Object, Point2D> usualGraphVerLocMap) {
        this.usualGraphVerLocMap = usualGraphVerLocMap;
    }

    public boolean isIsLastLevel() {
        return lastLevel;
    }

    public void setIsLastLevel(boolean isLastLevel) {
        this.lastLevel = isLastLevel;
    }

    public Map<Object, Point2D> getVertexLocationMap() {
        return vertexLocationMap;
    }

    public void setVertexLocationMap(Map<Object, Point2D> vertexLocationMap) {
        this.vertexLocationMap = vertexLocationMap;
    }

    public Map<Object, Integer> getVertexSizeMap() {
        return vertexSizeMap;
    }

    public void setVertexSizeMap(Map<Object, Integer> vertexSizeMap) {
        this.vertexSizeMap = vertexSizeMap;
    }
    
    
    public Object getHighlightedNode() {
        return highlightedNode;
    }

    public void setHighlightedNode(Object highlightedNode) {
        this.highlightedNode = highlightedNode;
    }

    public Color getHighlightedNodeFormerColor() {
        return highlightedNodeFormerColor;
    }

    public void setHighlightedNodeFormerColor(Color highlightedNodeFormerColor) {
        this.highlightedNodeFormerColor = highlightedNodeFormerColor;
    }

    public BioObject getSearchedBioObj() {
        return searchedBioObj;
    }

    public void setSearchedBioObj(BioObject searchedBioObj) {
        this.searchedBioObj = searchedBioObj;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    
    public Map getNodeToClusterMap() {
        return nodeToClusterMap;
    }

    public void setNodeToClusterMap(Map nodeToClusterMap) {
        this.nodeToClusterMap = nodeToClusterMap;
    }

    public Map getUsualNodeToClusterMap() {
        return usualNodeToClusterMap;
    }

    public void setUsualNodeToClusterMap(Map UsualNodeToClusterMap) {
        this.usualNodeToClusterMap = UsualNodeToClusterMap;
    }
    
    
    public Graph getUsualGraph() {
        return usualGraph;
    }

    public void setUsualGraph(Graph usualGraph) {
        this.usualGraph = usualGraph;
    }
    
    public Set getCSet() {
        return cSet;
    }

    public void setCSet(Set cSet) {
        this.cSet = cSet;
    }

    public Set getCSetBeforeVis() {
        return cSetBeforeVis;
    }

    public void setCSetBeforeVis(Set cSetBeforeVis) {
        this.cSetBeforeVis = cSetBeforeVis;
    }

    public ArrayList getClickedVertices() {
        return clickedVertices;
    }

    public void setClickedVertices(ArrayList clickedVertices) {
        this.clickedVertices = clickedVertices;
    }

    public ArrayList getRecenteredNodes() {
        return recenteredNodes;
    }

    public void setRecenteredNodes(ArrayList recenteredNodes) {
        this.recenteredNodes = recenteredNodes;
    }

    

    public Map getColorMap() {
        return colorMap;
    }

    public void setColorMap(Map colorMap) {
        this.colorMap = colorMap;
    }

    public Set getComNodeCSet() {
        return comNodeCSet;
    }

    public void setComNodeCSet(Set comNodeCSet) {
        this.comNodeCSet = comNodeCSet;
    }

    public Set getComNodeCSetBeforeVis() {
        return comNodeCSetBeforeVis;
    }

    public void setComNodeCSetBeforeVis(Set comNodeCSetBeforeVis) {
        this.comNodeCSetBeforeVis = comNodeCSetBeforeVis;
    }

    public PropInfoProcessor getPropInfoProc() {
        return propInfoProc;
    }

    public void setPropInfoProc(PropInfoProcessor propInfoProc) {
        this.propInfoProc = propInfoProc;
    }

    public Graph getStoredGraph() {
        return storedGraph;
    }

    public void setStoredGraph(Graph storedGraph) {
        this.storedGraph = storedGraph;
    }
    
}
