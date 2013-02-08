/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

//import edu.uci.ics.jung.algorithms.layout.SpringLayout.LengthFunction;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.Set;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Knacky
 */
public class LengthFunctionBySize<E> implements Transformer<E, Double> {

    int length;
    Graph graph;

    public LengthFunctionBySize(int length, Graph graph) {
        this.length = length;
        this.graph = graph;
    }
//        public double getLength(E e) {

    @Override
    public Double transform(E e) {
//            Graph graph= getGraph();
        Pair pair = graph.getEndpoints(e);
        Object v1 = pair.getFirst(), v2 = pair.getSecond();

        int size1, size2;
        if (v1 instanceof Set) {
            int numMember = ((Set) v1).size();
            int value = (int) (Math.log10(numMember) / Math.log10(4) * 20);
            if ((numMember == 0) || (value < 20)) {
                size1 = 20;
            } else {
                size1 = value;
            }
        } else {
            size1 = 20;
        }

        if (v2 instanceof Set) {
            int numMember = ((Set) v2).size();
            int value = (int) (Math.log10(numMember) / Math.log10(4) * 20);
            if ((numMember == 0) || (value < 20)) {
                size2 = 20;
            } else {
                size2 = value;
            }
        } else {
            size2 = 20;
        }
        System.out.println("size1: " + size1 + " size2: " + size2);
        return length + ((double) size1 / 2 + (double) size2 / 2);
    }
}
