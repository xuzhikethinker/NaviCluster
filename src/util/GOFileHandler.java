/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.PropertyTerm;
import objects.NameSpace;
import org.geneontology.oboedit.dataadapter.DefaultOBOParser;
import org.geneontology.oboedit.dataadapter.OBOParseEngine;
import org.geneontology.oboedit.dataadapter.OBOParseException;
import org.geneontology.oboedit.datamodel.IdentifiedObject;
import org.geneontology.oboedit.datamodel.Link;
import org.geneontology.oboedit.datamodel.LinkedObject;
import org.geneontology.oboedit.datamodel.OBOClass;
import org.geneontology.oboedit.datamodel.OBOSession;
import org.geneontology.oboedit.datamodel.RootAlgorithm;
import org.geneontology.oboedit.datamodel.impl.OBORestrictionImpl;
import org.geneontology.oboedit.util.TermUtil;

/**
 *
 * @author Knacky
 */
public class GOFileHandler {
    public static HashSet<NameSpace> namespaceSet = new HashSet<NameSpace>();
    private static int getDepth(IdentifiedObject oboclass){
        
        int depth = 0;
        int max = -1;
//        if (oboclass instanceof OBORestriction){
//            OBORestrictionImpl obo = (OBORestrictionImpl) oboclass;
//            return getWeight(obo.getParent());
//        
//        }
        String name = "";
        
        if (oboclass instanceof OBOClass) {
            PropertyTerm term = PropertyTerm.findTerm(setOfGO, oboclass.getID());
            if (term != null)
                return term.getWeight();
//            if (GODepthMap.containsKey(oboclass.getID()))
//                return GODepthMap.get(oboclass.getID());
//            System.out.println("id: "+oboclass.getID());
            for (Link obj : ((OBOClass) oboclass).getParents()) {
//                System.out.println(obj.getID());
//                System.out.println(obj.getType());
                if (!obj.getType().toString().contains("is_a") && !obj.getType().toString().contains("part_of"))
                    continue;
                if (obj instanceof OBORestrictionImpl) {
//                    OBORestrictionImpl parent = (OBORestrictionImpl) obj;
                    OBOClass parent = (OBOClass) obj.getParent();
                    
//                    System.out.println(parent.getType());
                    // detect biological pocess, cellular component, molecular function
//                    if (("GO:0008150".equals(parent.getID())) || ("GO:0005575".equals(parent.getID())) 
//                            || ("GO:0003674".equals(parent.getID())))
//                        return 1;
                    
                    depth = getDepth(parent);
                    if (depth > max) {
                        name = obj.getParent().getID();
                        max = depth;
                    }
                }
            }
//            System.out.print("parent: "+name+"\t");
            if (term == null)
            {
                term = new PropertyTerm(oboclass.getID());
                term.setWeight(max+1);
                setOfGO.add(term);
            }
            
//            GODepthMap.put(oboclass.getID(),max+1);
            return max+1;
            
        }
        return 0;
        
       
        
    }
    static OBOSession session;
//    ArrayList<String> GOIDList = new ArrayList<String>();
    
//    static Map<String,Integer> GODepthMap = new HashMap<String, Integer>();
    static Set<PropertyTerm> setOfGO = new HashSet<PropertyTerm>();
    static Set<PropertyTerm> toPrint = new TreeSet<PropertyTerm>(new Comparator<PropertyTerm>() {

        public int compare(PropertyTerm o1, PropertyTerm o2) {
            return o1.getId().compareTo(o2.getId());
        }
    });

        
    public static OBOSession getSession(String path) throws IOException, OBOParseException {
        
	DefaultOBOParser parser = new DefaultOBOParser();
	OBOParseEngine engine = new OBOParseEngine(parser);
	// GOBOParseEngine can parse several files at once
	// and create one munged-together ontology,
	// so we need to provide a Collection to the setPaths() method
	Collection paths = new LinkedList();
	paths.add(path);
	engine.setPaths(paths);
	engine.parse();
	session = parser.getSession();
	return session;
    }
    public static void testOBOSession() throws IOException, OBOParseException{
        OBOSession obosession = null;
         obosession = GOFileHandler.getSession("gene_ontology.1_2.obo");
            IdentifiedObject obj = obosession.getObject("GO:0000003");
//            int childcount = TermUtil.getChildCount(obosession.getLinkDatabase(),(LinkedObject) obj);
//            Collection<LinkedObject> link = TermUtil.getAncestors((LinkedObject) obj);
//            link = TermUtil.getDescendants((LinkedObject) obj);
//            TreeSet<LinkedObject> set = new TreeSet(link);
//            Set<LinkedObject> outSet = new HashSet<LinkedObject>();
//            TermUtil.detectRoots(outSet, obosession.getLinkDatabase(), RootAlgorithm.GREEDY);
//            System.out.println("outSet "+outSet.size());
//            System.out.println("outSet "+outSet.iterator().next());
//            System.out.println("ancestors "+link.size());
//            System.out.println("descendants "+link.size());
//            System.out.println("child count "+childcount);
//            System.out.println("Parent count "+TermUtil.getParentCount(obosession.getLinkDatabase(), (LinkedObject)obj));
//            System.out.println(TermUtil.getObjectCount(obosession.getLinkDatabase()));
//            System.out.println(TermUtil.getRoots(obosession.getLinkDatabase()).size());
//            System.out.println(TermUtil.getRoots(obosession));
            System.out.println(TermUtil.getRoot((LinkedObject) obj));
            System.out.println(TermUtil.getTerms(obosession).size());
            System.out.println(TermUtil.getTerms(obosession).iterator().next());
            int i = 0;
            for (OBOClass ob : TermUtil.getTerms(obosession)){
                System.out.println("obj: "+ob.getID()+" "+ob.getName());
                System.out.println(ob.getPropertyValues()+" "+ob.getType());
                System.out.println(ob.getSynonyms());
                System.out.println(ob.getCategories());
                i++;
                if (i == 10)
                    break;
            }
            OBOClass oboclass = (OBOClass)obj;
            System.out.println(oboclass.getParents());
            System.out.println("oboclass: "+oboclass.getID()+" "+oboclass.getName());
            System.out.println(oboclass.getPropertyValues() + " " + oboclass.getType());
            System.out.println(oboclass.getSynonyms());
            System.out.println(oboclass.getCategories());
//            for (LinkedObject lo : set){
//                System.out.println("Linked obj: "+lo);
//                
//            }
//            IdentifiedObject ancestor = obosession.getObject("GO:0050777");
//            System.out.println(TermUtil.hasAncestor((LinkedObject)obj, (LinkedObject)ancestor));
//            System.out.println(TermUtil.isDescendant((LinkedObject)ancestor, (LinkedObject)obj));
            
    }
    static class EntryInQueue{
        Set<LinkedObject> ancestors = new HashSet<LinkedObject>();
        Set<LinkedObject> parents = new HashSet<LinkedObject>();
        int depth = 0;
    }
    /**
     * Calculate the maximum depth of oboclass that can be calculated from any lines of its inheritance.
     * EX.        A
     *           / \
     *           | C
     *            \|
     *             D
     * In this case, D will have max depth = 2 (from D to C to A), not 1 (directly D to A).
     * @param oboclass
     * @return maximum depth
     */
    public static int getMaxDepth(OBOClass oboclass){
        LinkedList<EntryInQueue> queue = new LinkedList<EntryInQueue>();
        
//        int max = -1;
//        if (oboclass instanceof OBORestriction){
//            OBORestrictionImpl obo = (OBORestrictionImpl) oboclass;
//            return getWeight(obo.getParent());
//        
//        }
        String name = "";
        PropertyTerm term = PropertyTerm.findTerm(setOfGO, oboclass.getID());
        if (term != null)
           return term.getWeight();
//            if (GODepthMap.containsKey(oboclass.getID()))
//                return GODepthMap.get(oboclass.getID());
//            System.out.println("id: "+oboclass.getID());
        LinkedObject lo = oboclass;
        Collection<LinkedObject> ancestors = TermUtil.getAncestors(lo);
        Collection<Link> parentLinks = oboclass.getParents();
        Collection<LinkedObject> parents = new HashSet<LinkedObject>();
        /* Collect parents from the parentLinks collection
         * Only parents that have is_a or part_of relationships with the the oboclass are valid.
         */
        for (Link obj : parentLinks) {
//                System.out.println(obj.getID());
//                System.out.println(obj.getType());
            if (!obj.getType().toString().contains("is_a") && !obj.getType().toString().contains("part_of")) {
                continue;
            }
            if (obj instanceof OBORestrictionImpl) {
//                    OBORestrictionImpl parent = (OBORestrictionImpl) obj;
                OBOClass parent = (OBOClass) obj.getParent();

//                    System.out.println(parent.getType());
                // detect biological pocess, cellular component, molecular function
//                    if (("GO:0008150".equals(parent.getID())) || ("GO:0005575".equals(parent.getID())) 
//                            || ("GO:0003674".equals(parent.getID())))
//                        return 1;

                parents.add(parent);
            }
        }
        
        Set<Integer> alreadyKnown = new HashSet<Integer>();
        Set<EntryInQueue> alreadyRemoved = new HashSet();
        EntryInQueue entry = new EntryInQueue();
        entry.ancestors.addAll(ancestors);
        entry.parents.addAll(parents);
        /* depth in entry is actually a distance from the "oboclass" being considered */
        entry.depth = 1;
        queue.addLast(entry);
        int currentDepth = 1;
        while (queue.size() != 0){
            entry = queue.removeFirst();
            alreadyRemoved.add(entry);
            int depth = entry.depth;
            if (depth > currentDepth)
            {
                currentDepth = depth;
                /* clear alreadyRemoved set when considering next level (depth) of hierarchy
                 * because at this time, we cannot use below heuristics. */
                alreadyRemoved.clear();
            }
            /* Check all valid parents of the popped entry */
            for (LinkedObject linkObj : entry.parents){
                boolean skip = false;
                term = PropertyTerm.findTerm(setOfGO, linkObj.getID());
                /* if it exists in setOfGO, its depth has already been calculated.
                 * In this case, just add the current calculated depth + the term's depth to the alreadyKnown */
                if (term != null)
                    alreadyKnown.add(depth+term.getWeight());
                else {
                    /* my heuristics
                     * the one that appears in other line as ancestor, but not parent should be excluded
                     * this is because it will be processed again in that line and the "oboclass" being calculated
                     * will gain higher depth from that line.
                     */
                    for (EntryInQueue eq : queue){
                        /* eq.depth == depth means that it is checking only entries in the queue which are in the
                         * same level (depth)
                         *       E <--- linkObj
                         *      / \
                         *     /   F
                         *    /     \
                         *   /       D <--- eq
                         *  /         \
                         * B           C
                         *  \____A____/
                         */
                        if ((eq.depth == depth) && eq.ancestors.contains(linkObj) && !eq.parents.contains(linkObj))
                        {
                            skip = true;
                            break;
                        }
                    }
                    for (EntryInQueue eq : alreadyRemoved){
                        if ((eq.depth == depth) && eq.ancestors.contains(linkObj) && !eq.parents.contains(linkObj))
                        {
                            skip = true;
                            break;
                        }
                    }
                    if (skip) {
                        continue;
                    }
                    
                    ancestors = TermUtil.getAncestors(linkObj);
                    parentLinks = linkObj.getParents();
                    parents = new HashSet<LinkedObject>();
                    for (Link obj : parentLinks) {
//                System.out.println(obj.getID());
//                System.out.println(obj.getType());
                        if (!obj.getType().toString().contains("is_a") && !obj.getType().toString().contains("part_of")) {
                            continue;
                        }
                        if (obj instanceof OBORestrictionImpl) {
//                    OBORestrictionImpl parent = (OBORestrictionImpl) obj;
                            OBOClass parent = (OBOClass) obj.getParent();

//                    System.out.println(parent.getType());
                            // detect biological pocess, cellular component, molecular function
//                    if (("GO:0008150".equals(parent.getID())) || ("GO:0005575".equals(parent.getID())) 
//                            || ("GO:0003674".equals(parent.getID())))
//                        return 1;

                            parents.add(parent);
                        }
                    }

                    entry = new EntryInQueue();
                    entry.ancestors.addAll(ancestors);
                    entry.parents.addAll(parents);
                    entry.depth = 1+depth;
                    queue.addLast(entry);
                    }
            }
        }
        for (Integer i : alreadyKnown){
            if (currentDepth < i){
                currentDepth = i;
            }
        }
        /* currentDepth is now maximum depth from all known depths.*/
        
//        
//            for (Link obj : ((OBOClass) oboclass).getParents()) {
////                System.out.println(obj.getID());
////                System.out.println(obj.getType());
//                if (!obj.getType().toString().contains("is_a") && !obj.getType().toString().contains("part_of"))
//                    continue;
//                if (obj instanceof OBORestrictionImpl) {
////                    OBORestrictionImpl parent = (OBORestrictionImpl) obj;
//                    OBOClass parent = (OBOClass) obj.getParent();
//                    
////                    System.out.println(parent.getType());
//                    // detect biological pocess, cellular component, molecular function
////                    if (("GO:0008150".equals(parent.getID())) || ("GO:0005575".equals(parent.getID())) 
////                            || ("GO:0003674".equals(parent.getID())))
////                        return 1;
//                    
//                    depth = getWeight(parent);
//                    if (depth > max) {
//                        name = obj.getParent().getID();
//                        max = depth;
//                    }
//                }
//            }
////            System.out.print("parent: "+name+"\t");
//            if (term == null)
//            {
//                term = new PropertyTerm(oboclass.getID());
//                term.setWeight(max+1);
//                setOfGO.add(term);
//            }
//            
////            GODepthMap.put(oboclass.getID(),max+1);
//            return max+1;
            
        return currentDepth;
//        return 0;
    }
    public static void findDepthAllTerms(Collection<OBOClass> allTerms){
        int i = 0;
        setOfGO.add(new PropertyTerm("GO:0008150", 1));
        setOfGO.add(new PropertyTerm("GO:0005575", 1));
        setOfGO.add(new PropertyTerm("GO:0003674",1));
        toPrint.add(new PropertyTerm("GO:0008150", 1));
        toPrint.add(new PropertyTerm("GO:0005575", 1));
        toPrint.add(new PropertyTerm("GO:0003674",1));
        namespaceSet.add(new NameSpace("molecular_function",1.0));
        namespaceSet.add(new NameSpace("biological_process",1.0));
        namespaceSet.add(new NameSpace("cellular_component",1.0));
        for (OBOClass oClass : allTerms) {
            if (oClass.getID().contains("GO")) {
                PropertyTerm term = new PropertyTerm(oClass.getID());
                term.setName(oClass.getName());
//                term.setNamespace(NameSpace.getNameSpace(oClass.getNamespace().toString()));
                term.setNamespace(new NameSpace(oClass.getNamespace().toString()));
                term.setWeight(getMaxDepth(oClass));

//                if (i%1000 == 0)  
//                    System.out.println(term.getId()+" "+term.getName()+" "+term.getNamespace()+" depth: "+term.getWeight());
                i++;
                toPrint.add(term);
//                setOfGO.add(term);
            }
            
        }
    }
    
    public static void printToFile(File f) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(f);
        for (PropertyTerm goterm : toPrint){
            pw.println(goterm.getId()+"\t"+goterm.getName()+"\t"+goterm.getNamespace()+"\t"+goterm.getWeight());
        }
        pw.close();
    }
    public static void main(String[] args) throws OBOParseException {
//        OBOSession obosession = null;
        try {
//            testOBOSession();
            session = GOFileHandler.getSession("gene_ontology.1_2.obo");
            Collection<OBOClass> allTerms = TermUtil.getTerms(session);
            findDepthAllTerms(allTerms);
            printToFile(new File("GO_with_depth.txt"));
        } catch (IOException ex) {
            Logger.getLogger(GOFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
