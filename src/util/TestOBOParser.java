/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import org.geneontology.oboedit.dataadapter.DefaultOBOParser;
import org.geneontology.oboedit.dataadapter.OBOParseEngine;
import org.geneontology.oboedit.dataadapter.OBOParseException;
import org.geneontology.oboedit.datamodel.IdentifiedObject;
import org.geneontology.oboedit.datamodel.Link;
import org.geneontology.oboedit.datamodel.LinkedObject;
import org.geneontology.oboedit.datamodel.OBOClass;
import org.geneontology.oboedit.datamodel.OBOProperty;
import org.geneontology.oboedit.datamodel.OBOSession;
import org.geneontology.oboedit.reasoner.ForwardChainingReasoner;


/**
 *
 * @author Knacky
 */



public class TestOBOParser {
    private static IdentifiedObject term;
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
	OBOSession session = parser.getSession();
	return session;
    }
    
    public static void main(String[] args) throws IOException, OBOParseException {
        
        OBOSession obosess = TestOBOParser.getSession("gene_ontology.1_2.obo");
        System.out.println(obosess.getNamespace("molecular_function").getPath());
        System.out.println(obosess.getObject("GO:0031563").getNamespace());
        term = obosess.getObject("GO:0031563");
        if (term instanceof LinkedObject) {
		LinkedObject lo = (LinkedObject) term;
                System.out.println(term.getClass());
                System.out.println(term instanceof OBOClass);
                OBOClass oboclass = (OBOClass) term;
                
                System.out.println(oboclass.getDefinition());
                System.out.println(oboclass.getCreatedBy());
		System.out.println(" with "+lo.getParents().size()+" parents");
                for (Link loo : lo.getParents())
                    System.out.println("Parents: "+loo);
                System.out.println(lo.getType());
                System.out.println(lo.getPropertyValues());
        }
        ForwardChainingReasoner reasoner = new ForwardChainingReasoner();
        reasoner.setLinkDatabase(obosess.getLinkDatabase());
        
        reasoner.recache();
        for (IdentifiedObject io : reasoner.getObjects()) {
            if (io instanceof LinkedObject) {
                LinkedObject lo = (LinkedObject) io;
                System.out.println("object " + lo + " started with " + lo.getParents().size() + " parents, but the reasoner now thinks there are " + reasoner.getParents(lo).size());
                
            }
        }
    }
}   
