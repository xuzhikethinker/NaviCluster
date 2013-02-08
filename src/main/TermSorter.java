package main;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.geneontology.oboedit.dataadapter.DefaultOBOParser;
import org.geneontology.oboedit.dataadapter.OBOParseEngine;
import org.geneontology.oboedit.dataadapter.OBOParseException;
import org.geneontology.oboedit.datamodel.IdentifiedObject;
import org.geneontology.oboedit.datamodel.LinkedObject;
import org.geneontology.oboedit.datamodel.OBOSession;
import org.geneontology.oboedit.test.TestUtil;

public class TermSorter {
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
	public static void main(String[] args) throws Exception {
//		OBOSession session = TestUtil.getSession(args[0]);
            OBOSession session = TermSorter.getSession("gene_ontology.1_2.obo");
		List<IdentifiedObject> objects = new ArrayList<IdentifiedObject>();
		objects.addAll(session.getObjects());
		Comparator<IdentifiedObject> nameComparator = new Comparator<IdentifiedObject>() {

			public int compare(IdentifiedObject arg0, IdentifiedObject arg1) {
				return arg0.getName().compareToIgnoreCase(arg1.getName());
			}			
		};
		Comparator<IdentifiedObject> idComparator = new Comparator<IdentifiedObject>() {

			public int compare(IdentifiedObject arg0, IdentifiedObject arg1) {
				return arg0.getID().compareToIgnoreCase(arg1.getID());
			}			
		};
		Collections.sort(objects, nameComparator);
		System.out.println("Here are the ontology objects sorted by name:");
		printObjects(objects);
		Collections.sort(objects, idComparator);
		System.out.println("Here are the ontology objects sorted by id:");
		printObjects(objects);
	}

	private static void printObjects(List<IdentifiedObject> objects) {
		for(IdentifiedObject io : objects) {
			System.out.println("   "+io.getName()+" {"+io.getID()+"}");
		}
		System.out.println();
	}
}
