/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thanet Praneenararat
 * This class converts gene ontology in OBO format to NaviCluster-compatible property information file.
 * The source code is developed based on OBOEdit source codes.
 * Refer to OBO file format guide: http://www.geneontology.org/GO.format.obo-1_2.shtml
 *
 * Usage: java -Xmx1024m OBOFileConverter -obofilename -propinfofilename -freqwordfilename
 *
 * freqwordfilename is the name of a text file containing mapping of frequently used words to their abbreviated names.
 * These names will be used in labeling small clusters instead of their full spelled words to save the space.
 * Example of freqwordfile:
 * ---------------------
 * activity 	act.
 * of
 * regulation 	reg.
 * process 	proc.
 * cell 	cell
 * positive 	pos.
 * ---------------------
 * 1) Full names come first and short names come in the second column.
 * 2) If you want to omit the name, just leave nothing in the second column (like "of" in the above example)
 * 3) If you don't want to use any short names, please specify an empty file as the third parameter.
 */
public class OBOFileConverter {
    enum StanzaType {unknown, term, typedef, instance};
    private StanzaType stanzaBeingProcessed = StanzaType.unknown;
    Set<OBOTerm> bpSet = new HashSet<OBOTerm>();
    Set<OBOTerm> mfSet = new HashSet<OBOTerm>();
    Set<OBOTerm> ccSet = new HashSet<OBOTerm>();
    ArrayList<OBOTerm> allTermList = new ArrayList<OBOTerm>();
    Map<String,Integer> termDepthMap = new HashMap<String,Integer>();

    public static void main(String[] args) throws FileNotFoundException {
        OBOFileConverter obofcv = new OBOFileConverter();
//        String filename = "src/datasets/gene_ontology.1_2-100329.obo";

        // File name
        if ((args == null) || (args.length == 0)) {
            System.out.println("Error: no input file name");
            System.out.println("usage: java -Xmx1024m OBOFileConverter -obofilename -propinfofilename -freqwordfilename");
            return;
        } else if (args.length == 1) {
            System.out.println("Error: no output file name");
            System.out.println("usage: java -Xmx1024m OBOFileConverter -obofilename -propinfofilename -freqwordfilename");
            return;
        } else if (args.length == 2) {
            System.out.println("Error: no frequent word file name");
            System.out.println("usage: java -Xmx1024m OBOFileConverter -obofilename -propinfofilename -freqwordfilename");
            return;
        }

        File oboFile = new File(args[0]);

        if ((!oboFile.exists()) || (!oboFile.isFile())) {
            System.out.println("Error: No OBO file exists.");
            return;
        }
        File dicFile = new File(args[2]);
        if ((!dicFile.exists()) || (!dicFile.isFile())) {
            System.out.println("Error: No dictionary file exists.");
            return;
        }
        File propInfoFile = new File(args[1]);

        obofcv.loadOBOFile(oboFile);
        obofcv.findDepthForAllTerm();
//        obofcv.loadDictionary("src/datasets/freq-words-dict.txt");
        obofcv.loadDictionary(args[2]);
//        obofcv.printToFile(new File("src/datasets/ontology_file-100329-short.txt"));
        obofcv.printToFile(propInfoFile);

    }

    public void loadOBOFile(String obofile){
        loadOBOFile(new File(obofile));
    }

    public void loadOBOFile(File file){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while ((line = br.readLine()) != null){
                if (line.startsWith("[") && line.endsWith("]"))
                    break;
//                else
//                    System.out.println(line);
            }

//            System.out.println("line "+line);
            if (line.contains("Term"))
                stanzaBeingProcessed = StanzaType.term;
            else if (line.contains("Typedef"))
                stanzaBeingProcessed = StanzaType.typedef;
            else if (line.contains("Instance"))
                stanzaBeingProcessed = StanzaType.instance;
            else {
                System.err.println("Error: invalid stanza");
//                System.exit(1);
            }
            readStanzas(br);
            
        } catch (IOException ex) {
            Logger.getLogger(OBOFileConverter.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public String readTerm(BufferedReader br) throws IOException {

        OBOTerm newTerm = new OBOTerm();
        String line = "";
        while ((line = br.readLine()) != null) {
//            if (line.startsWith("id")){
//            System.out.println("line "+line);

            if (line.startsWith("[") && line.endsWith("]")) {
                break;
            }
            if (line.startsWith("\n") || line.length() == 0)
                continue;
            String res[] = line.split(":", 2);
            if (res.length != 2) {
                System.err.println("Error: wrong format! "+line);
            } else {
                String tag = res[0].trim();
                String value = res[1].trim();

//                System.out.print("" + tag);
//                System.out.println(": " + value);

                if (value.contains("!")) {
                    value = value.split("!", 2)[0];

                }

                if (tag.toLowerCase().equals("id".toLowerCase())) {
                    newTerm.setId(value.trim());
//                    System.out.println("id "+value.trim());

                } else if (tag.toLowerCase().equals("name".toLowerCase())) {
                    if (newTerm.getName().length() > 0) {
                        System.err.println("Error: term name is defined twice.: " + newTerm.getName() + " and " + value);
                    }
                    newTerm.setName(value.trim());
                } else if (tag.toLowerCase().equals("namespace".toLowerCase())) {
                    if (newTerm.getNamespace().length() > 0) {
                        System.err.println("Error: term namespace is defined twice.: " + newTerm.getNamespace() + " and " + value);
                    }
                    newTerm.setNamespace(value.trim());
                } else if (tag.toLowerCase().equals("def".toLowerCase())) {
                    /* at this stage, do not take special care of dbxref format */
                    if (newTerm.getDefinition().length() > 0) {
                        System.err.println("Error: definition is defined twice.: " + newTerm.getDefinition() + " and " + value);
                    }
                    newTerm.setDefinition(value.substring(1, value.indexOf("\"", 1)));
                } else if (tag.toLowerCase().equals("comment".toLowerCase())) {
                    if (newTerm.getComment().length() > 0) {
                        System.err.println("Error: comment is defined twice.: " + newTerm.getComment() + " and " + value);
                    }
                    newTerm.setComment(value.trim());
                } else if (tag.toLowerCase().equals("alt_id".toLowerCase())) {

                    newTerm.getAltIdList().add(value.trim());
                } else /* There are no is_anonymous tags in current OBO file, so it does not processed here, in this implementation. */
                if (tag.toLowerCase().equals("synonym".toLowerCase())) {

                    newTerm.getSynonymList().add(value.substring(1, value.indexOf("\"", 1)));
//                    System.out.println(value.substring(1, value.indexOf("\"", 1)));
                    value = value.substring((value.indexOf("\"", 1)+1));
//                    System.out.println(value);
                    StringTokenizer stk = new StringTokenizer(value);

                    if (stk.hasMoreTokens()) {
                        newTerm.getScopeModList().add(ScopeModifier.valueOf(stk.nextToken().toUpperCase()));
                    } else {
                        newTerm.getScopeModList().add(ScopeModifier.RELATED);
                    }
                    if (stk.hasMoreTokens()) {
                        /* currently, do nothing with synonym type names and dbxref */
                    }
                    if (stk.hasMoreTokens()) {
                        /* currently, do nothing with synonym type names and dbxref */
                    }
                } else if (tag.toLowerCase().equals("subset".toLowerCase())) {
                    /* currently, do not check if subset appears in the heading of the file or not */
                    newTerm.getSubsetSet().add(value.trim());
                } else if (tag.toLowerCase().equals("xref".toLowerCase())) {
                    /* currently, do not check if subset appears in the heading of the file or not */
                    StringTokenizer stk = new StringTokenizer(value);
                    Xref xref = new Xref(stk.nextToken("\"").trim());

                    if (stk.hasMoreTokens()) {
//                        String tmp = stk.nextToken();
                        String tmp = stk.nextToken("\"");
//                        xref.setXrefDesc(tmp.substring(1, tmp.indexOf("\"", 1)));
                        xref.setXrefDesc(tmp);
                    }
                    /* Currently, do nothing with trailing modifiers */
                    newTerm.getXrefSet().add(xref);
                } else if (tag.toLowerCase().equals("is_a".toLowerCase())) {
                    /* currently, do not check if subset appears in the heading of the file or not */
                    newTerm.getIsaSet().add(value.trim());
                } else if (tag.toLowerCase().equals("relationship".toLowerCase())) {
                    /* currently, do not check if relationship exists in heading or not */
                    StringTokenizer stk = new StringTokenizer(value);
                    Relationship rel = new Relationship();
                    rel.setType(stk.nextToken());
                    if (stk.hasMoreTokens()) {
                        rel.setTarget(stk.nextToken().trim());
                    } else {
                        System.err.println("Error: dangling reference");
                    }
                    newTerm.getRelationshipSet().add(rel);

                } else if (tag.toLowerCase().equals("is_obsolete".toLowerCase())) {
                    /* currently, do not check if obsolete terms have a relationship with other terms or not */
                    newTerm.setIsObsolete(Boolean.parseBoolean(value));
                } else if (tag.toLowerCase().equals("replaced_by".toLowerCase())) {
                    /* currently, do not check if subset appears in the heading of the file or not */
                    newTerm.getReplacedSet().add(value.trim());
                } else if (tag.toLowerCase().equals("consider".toLowerCase())) {
                    /* currently, do not check if subset appears in the heading of the file or not */
                    newTerm.getConsiderSet().add(value.trim());
                }




            }
            

//                break;

//            }
//            if (!line.contains("Term") && !line.contains("Typedef") && line.contains("Instance"))
//            {
//                System.err.println("Error: invalid stanza");
//            }
        }
//        System.out.println("id " + newTerm.getId());
//        System.out.println(newTerm.getName());
//        System.out.println(newTerm.getNamespace());
//        System.out.println(newTerm.getAltIdList());
//        System.out.println(newTerm.getDefinition());
//        System.out.println(newTerm.getComment());
//        System.out.println(newTerm.getSubsetSet());
//        System.out.println(newTerm.getSynonymList());
//        System.out.println(newTerm.getScopeModList());
//        System.out.println(newTerm.getXrefSet());
//        System.out.println(newTerm.getIsaSet());
//
//        System.out.println(newTerm.getRelationshipSet());
//        System.out.println(newTerm.getReplacedSet());
//        System.out.println(newTerm.getConsiderSet());
//        if (newTerm.getId().equals("GO:0048311"))
//            System.out.println("Found!");
        allTermList.add(newTerm);
        if (newTerm.getNamespace().equalsIgnoreCase("biological_process")){
            bpSet.add(newTerm);
        } else if (newTerm.getNamespace().equalsIgnoreCase("molecular_function")){
            mfSet.add(newTerm);
        } else if (newTerm.getNamespace().equalsIgnoreCase("cellular_component")){
            ccSet.add(newTerm);
        }
            
            
        return line;
    }

    public String readTypedef(BufferedReader br){
        return null;
    }

    public void readStanzas(BufferedReader br) throws IOException{
        String line = "";

//        for (int i = 0; i < 5000; i++){
        while (line != null){

            if (stanzaBeingProcessed == StanzaType.term) {
                line = readTerm(br);
            } else if (stanzaBeingProcessed == StanzaType.typedef) {
                line = readTypedef(br);
            }

//            System.out.println("");
            if (line == null) {
                break;
            }
            if (line.contains("Term")) {
                stanzaBeingProcessed = StanzaType.term;
            } else if (line.contains("Typedef")) {
                stanzaBeingProcessed = StanzaType.typedef;
            } else if (line.contains("Instance")) {
                stanzaBeingProcessed = StanzaType.instance;
            } else {
                System.err.println("Error: invalid stanza");
            }
        }
//        while ((line = br.readLine()) != null){
//            if (!line.contains("Term") && !line.contains("Typedef") && line.contains("Instance"))
//            {
//                System.err.println("Error: invalid stanza");
//            }
//        }
        System.out.println("bpSet "+bpSet.size());
        System.out.println("mfSet "+mfSet.size());
        System.out.println("ccSet "+ccSet.size());
    }

    class EntryInQueue{
        Set<String> ancestors = new HashSet<String>();
        Set<String> parents = new HashSet<String>();
        int dist = 0;
    }

    Map<OBOTerm,Set<String>> ancestorsMap = new HashMap<OBOTerm,Set<String>>();
    public Set<String> getAllAncestors(OBOTerm oboterm){
        Set<String> ancestors = new HashSet<String>();

        /* Collect only parents (parents) that have is_a or part_of relationships
         * with the the oboclass are valid.
         */
//        for (String isaParent : oboterm.getIsaSet()){
//            ancestors.add(isaParent);
//        }
//        for (Relationship relParent : oboterm.getRelationshipSet()){
//            if (relParent.getType().equalsIgnoreCase("part_of")){
//                ancestors.add(relParent.getTarget());
//            }
//        }
        ancestors.addAll(getParents(oboterm));
        Set<String> tempAncestors = new HashSet<String>(ancestors);
        for (String term : tempAncestors){
            OBOTerm tempTerm = new OBOTerm(term);
            Set<String> requiredAncestors = ancestorsMap.get(tempTerm);
            if (requiredAncestors == null){
                requiredAncestors = getAllAncestors(allTermList.get(allTermList.indexOf(tempTerm)));
                ancestorsMap.put(tempTerm, requiredAncestors);
            }
            ancestors.addAll(requiredAncestors);
        }
        return ancestors;
    }

    public Set<String> getParents(OBOTerm oboterm){
        Set<String> parents = new HashSet<String>();

        /* Collect only parents (parents) that have is_a or part_of relationships
         * with the the oboclass are valid.
         */
        for (String isaParent : oboterm.getIsaSet()){
            parents.add(isaParent);
        }
        for (Relationship relParent : oboterm.getRelationshipSet()){
            if (relParent.getType().equalsIgnoreCase("part_of")){
                parents.add(relParent.getTarget());
            }
        }
        return parents;
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
    public int getMaxDepth(OBOTerm oboterm){
        LinkedList<EntryInQueue> queue = new LinkedList<EntryInQueue>();
        if (termDepthMap.containsKey(oboterm.getId()))
           return termDepthMap.get(oboterm.getId());
//            if (GODepthMap.containsKey(oboclass.getID()))
//                return GODepthMap.get(oboclass.getID());
//            System.out.println("id: "+oboclass.getID());
        Set<String> parents = getParents(oboterm);
        Set<String> ancestors = ancestorsMap.get(oboterm);
        if (ancestors == null){
            ancestors = getAllAncestors(oboterm);
            ancestorsMap.put(oboterm, ancestors);
        }

//        Collection<LinkedObject> parents = TermUtil.getAncestors(lo);
//        Collection<Link> parentLinks = oboclass.getParents();
//        Collection<LinkedObject> parents = new HashSet<LinkedObject>();
        
        Set<Integer> alreadyKnown = new HashSet<Integer>();
        Set<EntryInQueue> alreadyRemoved = new HashSet();
        EntryInQueue entry = new EntryInQueue();
        entry.ancestors.addAll(ancestors);
        entry.parents.addAll(parents);
        /* dist in entry is a distance from the "oboclass" being considered */
        entry.dist = 1;
        queue.addLast(entry);
        int currentDist = 1;
        while (queue.size() != 0){
            entry = queue.removeFirst();
            alreadyRemoved.add(entry);
            int dist = entry.dist;
            if (dist > currentDist)
            {
                currentDist = dist;
                /* clear alreadyRemoved set when considering next level (depth) of hierarchy
                 * because at this time, we cannot use below heuristics. */
                alreadyRemoved.clear();
            }

            /* Check all valid parents of the popped entry */
//            for (LinkedObject linkObj : entry.parents){
            for (String parent : entry.parents) {
                boolean skip = false;
                Integer containedDepth = termDepthMap.get(parent);
                /* if it exists in termDepthMap, its depth has already been calculated.
                 * In this case, just add the current calculated depth + the term's depth to the alreadyKnown */
                if (containedDepth != null) {
                    alreadyKnown.add(dist + containedDepth);
                } else {
                    /* my heuristics
                     * the one that appears in other line as ancestor, but not parent should be excluded
                     * this is because it will be processed again in that line and the "oboclass" being calculated
                     * will gain higher depth from that line.
                     */
                    for (EntryInQueue eq : queue) {
                        /* eq.dist == dist means that it is checking only entries in the queue which are in the
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
                        if ((eq.dist == dist) && eq.ancestors.contains(parent) && !eq.parents.contains(parent)) {
                            skip = true;
                            break;
                        }
                    }
                    for (EntryInQueue eq : alreadyRemoved) {
                        if ((eq.dist == dist) && eq.ancestors.contains(parent) && !eq.parents.contains(parent)) {
                            skip = true;
                            break;
                        }
                    }
                    if (skip) {
                        continue;
                    }

//                    System.out.println("parent " + parent);
                    OBOTerm parentTerm = new OBOTerm(parent);
                    ancestors = ancestorsMap.get(parentTerm);
                    if (ancestors == null){
                        ancestors = getAllAncestors(allTermList.get(allTermList.indexOf(parentTerm)));
                        ancestorsMap.put(parentTerm, ancestors);
                    }
                    parents = getParents(allTermList.get(allTermList.indexOf(new OBOTerm(parent))));
                    entry = new EntryInQueue();
                    entry.ancestors.addAll(ancestors);
                    entry.parents.addAll(parents);
                    entry.dist = 1 + dist;
                    queue.addLast(entry);
                }
            }
        }
        for (Integer i : alreadyKnown){
            if (currentDist < i){
                currentDist = i;
            }
        }
        /* currentDist is now maximum distance from all known distances.*/

        return currentDist;
    }

    public void findDepthForAllTerm(){
        termDepthMap.put("GO:0008150", 1);
        termDepthMap.put("GO:0005575", 1);
        termDepthMap.put("GO:0003674", 1);
//        System.out.println("alltermlist size "+allTermList.size());
        /* Find depth for terms in Biological Process */
        int i = 0;
        for (OBOTerm term : allTermList) {
//            System.out.println("i "+i);
            if (i%1000 == 0)
                System.out.println("i:"+i+" id "+term.getId());
            termDepthMap.put(term.getId(), getMaxDepth(term));

//                if (i%1000 == 0)
//                    System.out.println(term.getId()+" "+term.getName()+" "+term.getNamespace()+" depth: "+term.getDepth());
            i++;
//            if (i == 200)
//                break;
//                setOfGO.add(term);



        }
        for (Entry<String,Integer> e : termDepthMap.entrySet()){
            System.out.println(e.getKey()+" "+e.getValue());
        }
        /* Find depth for terms in Molecular Function */
        /* Find depth for terms in Cellular Component */
    }
    private Map<String, String> freqWordsDict = new HashMap<String, String>();

    public void loadDictionary(String fileName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(fileName));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String st = "", full = "", abbr = "";
            StringTokenizer stz = null;
            while ((st = br.readLine()) != null) {
                stz = new StringTokenizer(st, "\t", true);
                assert (stz.hasMoreTokens());
                full = stz.nextToken().trim();

                if (stz.nextToken().equals("\t"));

                if (stz.hasMoreTokens()) {
                    abbr = stz.nextToken().trim();
                } else {
                    abbr = "";
                }
//                if (stz.nextToken().equals("\t"));

//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();
//            if (stz.nextToken().equals("\t"));
//
//            assert (stz.hasMoreTokens());
//            stz.nextToken();

                freqWordsDict.put(full, abbr);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(OBOFileConverter.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    /**
     * Replace each word with its abbreviated form if any.
     * @param st
     * @return array of words after looking up in the abbreviation dictionary
     */
    public ArrayList<String> lookUpInDict(String st) {
//        System.out.println("st "+st);
        String[] arrOfWords = st.split("\\s");
        ArrayList<String> arrOfSt = new ArrayList<String>();
        // bigram check, e.g., for the word "endoplasmic reticulum"
        for (int i = 0; i < arrOfWords.length - 1; i++) {
            String word = arrOfWords[i] + " " + arrOfWords[i + 1];
//                System.out.println("word "+word+ " i "+i);
//                System.out.println("arrofword i "+arrOfWords[i]);
//                System.out.println("arrofword i+1 "+arrOfWords[i+1]);
            if (!freqWordsDict.containsKey(word)) {
                arrOfSt.add(arrOfWords[i]);
                if (i == arrOfWords.length - 2) {
                    arrOfSt.add(arrOfWords[i + 1]);
                }
//                    arrOfSt.add(word);
            } else {
                arrOfSt.add(freqWordsDict.get(word));
//                    arrOfWords[i] = freqWordsDict.get(word);
//                    arrOfWords[i+1] = "";
            }

        }
        if (arrOfWords.length == 1) {
            arrOfSt.add(arrOfWords[0]);
        }

        arrOfWords = arrOfSt.toArray(new String[1]);
//        System.out.println("arrOfSt "+arrOfSt);
//        System.out.println("size arrofst "+arrOfSt.size()+" arrofword "+arrOfWords.length);
//            System.out.println("\nNew words");
        int ind = 0, accum = 0;
//            for (String word : arrOfSt){
        // one-gram check. Look up in the dict if there is an entry for each word
        for (String word : arrOfWords) {
//                String word = stn.nextToken();
//            System.out.println("word "+word);
            if (!freqWordsDict.containsKey(word)) {
//                    arrOfSt.add(word);
//                    lenArrOfSt[ind] = word.length();
                //split by / and look up
                String[] stemStrArr;
                String newWord = "";
                StringBuilder newWordBuilder = new StringBuilder();

                //split by , and look up
                stemStrArr = word.split(",");
                newWordBuilder = new StringBuilder();
//        if (stemStrArr.length > 1) {
                int index = 0;
                int accumLen = 0;
                for (String stem : stemStrArr) {
                    String entry = freqWordsDict.get(stem);
//                        String entry = stem;
//                        System.out.println("entry " + entry);
                    if (entry == null) {
                        entry = stem;
                    }
                    if (!"".equals(entry) && !" ".equals(entry)) {
                        if ((index > 0)) {// && ((!"".equals(arrOfSt.get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
//                                    arrOfSt.add(ind + 1 + index - 1, "-");
                            if ((accumLen > 0)) {//&& (newWordBuilder.length() > 0) &&
//                            ((newWordBuilder.charAt(newWordBuilder.length()-1) != '/') && (entry.charAt(0) != '/') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != '-') && (entry.charAt(0) != '-') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ':') && (entry.charAt(0) != ':')
//                            )){
//                        if (stem.contains(" ") || (stem.contains("\n")) || (stem.contains("\t")))
//                            newWordBuilder.append(", ");
//                        else
                                newWordBuilder.append(",");
                            }
//                    index++;
                        }
//                                arrOfSt.add(ind + 1 + index, entry);
                        newWordBuilder.append(entry);
                        accumLen += entry.length();
//                index++;

                    }
                    index++;
                }
                if ((stemStrArr.length == 1) && (word.length() > 0) && (word.charAt(word.length() - 1) == ',')) {
                    newWordBuilder.append(",");
                }

//                    System.out.println("stb " + newWordBuilder.toString());
                newWord = newWordBuilder.toString();

                //split by / and look up
                stemStrArr = newWord.split("/");
                newWordBuilder = new StringBuilder();
//        if (stemStrArr.length > 1) {
                accumLen = 0;
                index = 0;
                for (String stem : stemStrArr) {
                    String entry = freqWordsDict.get(stem);
//                        String entry = stem;
//                        System.out.println("entry " + entry);
                    if (entry == null) {
                        entry = stem;
                    }
                    if (!"".equals(entry) && !" ".equals(entry)) {
                        if ((index > 0)) {// && ((!"".equals(newWordBuilder.indexOf("")get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
//                                    arrOfSt.add(ind + 1 + index - 1, "/");
                            if ((accumLen > 0)) {
//                                &&
//                            ((newWordBuilder.charAt(newWordBuilder.length()-1) != ':') && (entry.charAt(0) != ':') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != '-') && (entry.charAt(0) != '-') &&
//                            (newWordBuilder.charAt(newWordBuilder.length()-1) != ',') && (entry.charAt(0) != ',')
//                            )){
                                newWordBuilder.append("/");
                            }
//                                newWordBuilder.insert(accumLen, "/");
//                                index++;
                        }
//                                arrOfSt.add(ind + 1 + index, entry);
                        newWordBuilder.append(entry);
//                                newWordBuilder.
                        accumLen += entry.length();
                    }
                    index++;
                }
//        }
//        else {
////            newWordBuilder = new StringBuilder(word);
//
//        }
//                    System.out.println("stb " + newWordBuilder.toString());
                newWord = newWordBuilder.toString();

                //split by - and look up
                stemStrArr = newWord.split("-");
                newWordBuilder = new StringBuilder();
//        if (stemStrArr.length > 1) {
                index = 0;
                accumLen = 0;
                for (String stem : stemStrArr) {
                    String entry = freqWordsDict.get(stem);
//                        String entry = stem;
//                        System.out.println("entry " + entry);
                    if (entry == null) {
                        entry = stem;
                    }
                    if (!"".equals(entry) && !" ".equals(entry)) {
                        if ((index > 0)) {// && ((!"".equals(arrOfSt.get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
//                                    arrOfSt.add(ind + 1 + index - 1, "-");
                            if ((accumLen > 0) && (newWordBuilder.length() > 0)
                                    && ((newWordBuilder.charAt(newWordBuilder.length() - 1) != '/') && (entry.charAt(0) != '/')
                                    && (newWordBuilder.charAt(newWordBuilder.length() - 1) != ':') && (entry.charAt(0) != ':')
                                    && (newWordBuilder.charAt(newWordBuilder.length() - 1) != ',') && (entry.charAt(0) != ','))) {
                                newWordBuilder.append("-");
                            }
//                    index++;
                        }
//                                arrOfSt.add(ind + 1 + index, entry);
                        newWordBuilder.append(entry);
                        accumLen += entry.length();
//                index++;

                    }
                    index++;
                }
//        } else {
//            newWordBuilder = new StringBuilder(newWord);
//        }
//                    System.out.println("stb " + newWordBuilder.toString());
                newWord = newWordBuilder.toString();

                //split by : and look up
                stemStrArr = newWord.split(":");
                newWordBuilder = new StringBuilder();
//        if (stemStrArr.length > 1) {
                index = 0;
                accumLen = 0;
                for (String stem : stemStrArr) {
                    String entry = freqWordsDict.get(stem);
//                        String entry = stem;
//                        System.out.println("entry " + entry);
                    if (entry == null) {
                        entry = stem;
                    }
                    if (!"".equals(entry) && !" ".equals(entry)) {
                        if ((index > 0)) {// && ((!"".equals(arrOfSt.get(ind + 1 + index - 1))) || (!" ".equals(arrOfSt.get(ind + 1 + index - 1)))) ) {
//                                    arrOfSt.add(ind + 1 + index - 1, "-");
                            if ((accumLen > 0) && (newWordBuilder.length() > 0)
                                    && ((newWordBuilder.charAt(newWordBuilder.length() - 1) != '/') && (entry.charAt(0) != '/')
                                    && (newWordBuilder.charAt(newWordBuilder.length() - 1) != '-') && (entry.charAt(0) != '-')
                                    && (newWordBuilder.charAt(newWordBuilder.length() - 1) != ',') && (entry.charAt(0) != ','))) {
                                newWordBuilder.append(":");
                            }
//                    index++;
                        }
//                                arrOfSt.add(ind + 1 + index, entry);
                        newWordBuilder.append(entry);
                        accumLen += entry.length();
//                index++;

                    }
                    index++;
                }
//        } else {
//            newWordBuilder = new StringBuilder(newWord);
//        }
//                    System.out.println("stb " + newWordBuilder.toString());
                newWord = newWordBuilder.toString();

                StringTokenizer stk = new StringTokenizer(newWord, "/:,-", true);
                ArrayList<String> newArrOfSt = new ArrayList<String>();
                while (stk.hasMoreTokens()) {
                    newArrOfSt.add(stk.nextToken());
                }
                if ((newArrOfSt.size() > 1) && (newArrOfSt.get(newArrOfSt.size() - 1).equals(","))) {
                    newArrOfSt.remove(newArrOfSt.size() - 1);
                    newArrOfSt.set(newArrOfSt.size() - 1, newArrOfSt.get(newArrOfSt.size() - 1) + ",");
                }


//                    System.out.println("ind "+ind+" accum "+accum);
//                    System.out.println("before remove "+arrOfSt.get(ind+accum));
//                    System.out.println("arrofst ind "+arrOfSt.get(ind));
                arrOfSt.remove(ind + accum);
                arrOfSt.addAll(ind + accum, newArrOfSt);
//                    System.out.println("after addall "+newArrOfSt);
                accum += newArrOfSt.size() - 1;
//                    arrOfSt.set(ind, newWord);
            } else {

                arrOfSt.set(ind + accum, freqWordsDict.get(word));
//                    System.out.println("ind "+ind);
//                    System.out.println("arrofst ind "+arrOfSt.get(ind));
//                    accum += 1;
//                    lenArrOfSt[ind] = freqWordsDict.get(word).length();
            }
            ind++;
        }
        return arrOfSt;
    }


    /**
     * Compose words by aware of punctuation marks, spaces, etc.
     * @param arrOfSt
     * @return label
     */
    public String composeWordsWithCare(ArrayList<String> arrOfSt) {
        String res = "";
        int upToNow = 0;
        int i = 0;
//            System.out.println("COMPOSE");
        String[] punctuation = {",", "-", "/", ":"};
        ArrayList<String> arrPunc = new ArrayList<String>(Arrays.asList(punctuation));
        for (String word : arrOfSt) {
//                System.out.println("word " + word);
            if (word.equals(" ") || word.equals("")) {
                i++;
                continue;
            }
            if ((i > 0) && ((arrPunc.contains(word) && arrOfSt.get(i-1).equals(word)) || (word.endsWith(",") && arrOfSt.get(i-1).endsWith(",")))){
                if (word.equals(","))
                    res += " ";
                i++;
                continue;
            }
//            if (upToNow + word.length() <= charsPerLine) {
                res += word;
                upToNow += word.length();
//                    System.out.println("res " + res + " uptonow " + upToNow);
                if ((!arrPunc.contains(word)) && (i + 1 < arrOfSt.size())) {

                    if (!arrPunc.contains(arrOfSt.get(i + 1))) {

                            res += " ";
                            upToNow++;

                    }
                }

            i++;

        }

        return res;
    }


    /**
     * Creates GO terms' short names by looking up the dictionary of abbreviation
     * @param name
     * @return
     */
    public String findGOTermShortName(String name) {
        String result = new String(name);
        ArrayList<String> arrOfSt = new ArrayList<String>();
        int[] lenArrOfSt;// = new int[arrOfSt.size()];
        arrOfSt = new ArrayList<String>(Arrays.asList(name.split("\\s")));
        lenArrOfSt = new int[arrOfSt.size()];
//                System.out.println("arrofst " + arrOfSt);
        for (int i = 0; i < arrOfSt.size(); i++) {
            lenArrOfSt[i] = arrOfSt.get(i).length();
        }

        arrOfSt = lookUpInDict(name);
        result = composeWordsWithCare(arrOfSt);
//        lenArrOfSt = new int[arrOfSt.size()];
//        StringBuilder stb = new StringBuilder();
//        int lengthOfSt = 0;
//        for (int i = 0; i < arrOfSt.size(); i++) {
//            lenArrOfSt[i] = arrOfSt.get(i).length();
//            if (i > 0) {
//                if (!arrOfSt.get(i).equals(",") && !arrOfSt.get(i).equals("/")
//                        && !arrOfSt.get(i).equals(":") && !arrOfSt.get(i).equals("-") && !arrOfSt.get(i).equals(" ")
//                        && (!arrOfSt.get(i - 1).equals(",") && !arrOfSt.get(i - 1).equals("/")
//                        && !arrOfSt.get(i - 1).equals(":") && !arrOfSt.get(i - 1).equals("-") && !arrOfSt.get(i - 1).equals(" "))) {
//                    stb.append(" ");
//                    lengthOfSt += 1;
//                }
//            }
//            stb.append(arrOfSt.get(i));
//            lengthOfSt += lenArrOfSt[i];
//        }
////        lengthOfSt--;
////        stb.delete(stb.length() - 1, stb.length());
//        result = stb.toString();
        return result;
    }

    public void printToFile(File f) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(f);
        int i = 0;
        for (OBOTerm term : allTermList){
            if (term.isObsolete)
                continue;
            String parentIdList = "";
            StringBuilder sb = new StringBuilder();
            Set<String> parentSet = getParents(term);
            for (String parentId : parentSet)
                sb.append(parentId+"|");
            parentIdList = sb.toString();
            if (parentIdList.length() > 0)
                parentIdList = parentIdList.substring(0, parentIdList.length()-1);
//            System.out.println("term "+term.getName());
            String shortName = findGOTermShortName(term.getName());
            pw.println(term.getId()+"\t"+term.getName()+"\t"+shortName+"\t"+term.getNamespace()+"\t"+
                    termDepthMap.get(term.getId())+"\t"+parentIdList);
//            pw.println(term.getId()+"\t"+term.getName()+"\t"+term.getName()+"\t"+term.getNamespace()+"\t"+
//                    termDepthMap.get(term.getId())+"\t"+parentIdList);
//            i++;
//            if (i == 20)
//                break;
        }

        pw.close();
    }
    
}

enum ScopeModifier {EXACT, BROAD, NARROW, RELATED}

class OBOType{

}

class OBOTerm{
    String id = "";
    Set<String> altIdList = new HashSet<String>();
    String name = "";
    String shortName = "";
    String namespace = "";
    String definition = "";
    /* More than one comment generates parse error.*/
    String comment = "";
    ArrayList<String> synonymList = new ArrayList<String>();
    ArrayList<ScopeModifier> scopeModList = new ArrayList<ScopeModifier>();
    
    Set<String> subsetSet = new HashSet<String>();
    Set<Xref> xrefSet = new HashSet<Xref>();
    Set<String> isaSet = new HashSet<String>();
    Set<Relationship> relationshipSet = new HashSet<Relationship>();

    boolean isObsolete = false;
    Set<String> replacedSet = new HashSet<String>();
    Set<String> considerSet = new HashSet<String>();

    public Set<String> getAltIdList() {
        return altIdList;
    }

    public void setAltIdList(Set<String> altIdList) {
        this.altIdList = altIdList;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<String> getConsiderSet() {
        return considerSet;
    }

    public void setConsiderSet(Set<String> considerSet) {
        this.considerSet = considerSet;
    }

    public Set<String> getReplacedSet() {
        return replacedSet;
    }

    public void setReplacedSet(Set<String> replacedSet) {
        this.replacedSet = replacedSet;
    }



    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsObsolete() {
        return isObsolete;
    }

    public void setIsObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public Set<String> getIsaSet() {
        return isaSet;
    }

    public void setIsaSet(Set<String> isaSet) {
        this.isaSet = isaSet;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Set<Relationship> getRelationshipSet() {
        return relationshipSet;
    }

    public void setRelationshipSet(Set<Relationship> relationshipSet) {
        this.relationshipSet = relationshipSet;
    }

    public ArrayList<ScopeModifier> getScopeModList() {
        return scopeModList;
    }

    public void setScopeModList(ArrayList<ScopeModifier> scopeModList) {
        this.scopeModList = scopeModList;
    }

    public Set<String> getSubsetSet() {
        return subsetSet;
    }

    public void setSubsetSet(Set<String> subsetSet) {
        this.subsetSet = subsetSet;
    }

    public ArrayList<String> getSynonymList() {
        return synonymList;
    }

    public void setSynonymList(ArrayList<String> synonymList) {
        this.synonymList = synonymList;
    }

    public Set<Xref> getXrefSet() {
        return xrefSet;
    }

    public void setXrefSet(Set<Xref> xrefSet) {
        this.xrefSet = xrefSet;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OBOTerm other = (OBOTerm) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public OBOTerm(){

    }
    public OBOTerm(String id){
        this.id = new String(id);
    }

}
class Xref{
    /* Ignore all trailing modifiers */
    private String xrefName = "";
    private String xrefDesc = "";

    public Xref(){

    }
    public Xref(String name){
        this.xrefName = name;
    }
    public String getXrefDesc() {
        return xrefDesc;
    }

    public void setXrefDesc(String xrefDesc) {
        this.xrefDesc = xrefDesc;
    }

    public String getXrefName() {
        return xrefName;
    }

    public void setXrefName(String xrefName) {
        this.xrefName = xrefName;
    }

    public String toString(){
        return "xrefName: "+xrefName+" XrefDescription: "+xrefDesc;
    }

}
class Relationship{
    /* Ignore all trailing modifiers */
    private String type = "";
    private String target = null;

    public Relationship() {

    }
    public Relationship(String type){
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return "relationship: "+type+" w/ "+target;
    }
}
class PartOfRelationship extends Relationship{

}
class RegulatesRelationship extends Relationship{

}
class PositiveRegulatesRelationship extends RegulatesRelationship{

}
class NegativeRegulatesRelationship extends RegulatesRelationship{

}