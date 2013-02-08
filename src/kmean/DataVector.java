/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kmean;

import java.lang.String;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import main.PropertyTerm;

/**
 *
 * @author Knacky
 */
public class DataVector{
//    String[] dimNameList = new String[1];
//    double[] dimValueList = new double[1];
    String name = "";
    public Object nodeRef = null;
    public Object comNodeClusterRef = null;
    Map<PropertyTerm,String> GOTermIDMap = new HashMap<PropertyTerm,String>();
    Map<String,Double> valueMap = new TreeMap<String,Double>(new Comparator<String>() {

        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
//            throw new UnsupportedOperationException("Not supported yet.");
        }
    });
    DataCluster belongToCluster = null;
    public Map<String,Double> getValueMap(){
        return valueMap;
    }
//    public static void populateNameList(String list[]){
//        dimNameList = new String[list.length];
//        
//        for (int i = 0; i < list.length; i++){
//            dimNameList[i] = list[i];
//        }
//    }
//    public int getIndexOfName(String query){
//        int index = -1,i  = 0;
//        for (String s : dimNameList){
//            if (s.equals(query)){
//                index = i;
//                break;
//            }
//            i++;
//        }
//        return index;
//    }
//    public void setNameList(String list[]){
//        dimNameList = new String[list.length];
//        
//        for (int i = 0; i < list.length; i++){
//            dimNameList[i] = list[i];
//        }
//    }
    public DataVector(){
        
    }
    public DataVector(Map map){
        if (map.keySet().size() != 0) {
            if (map.keySet().iterator().next() instanceof String) {
                valueMap = map;
            } else if (map.keySet().iterator().next() instanceof PropertyTerm) {
                for (Object e : map.entrySet()) {
                    Entry entry = (Entry) e;
                    PropertyTerm term = (PropertyTerm) entry.getKey();
                    GOTermIDMap.put(term, term.getId());
                    valueMap.put(term.getId(), (Double) entry.getValue());
                }
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataVector other = (DataVector) obj;
        if (this.nodeRef != other.nodeRef && (this.nodeRef == null || !this.nodeRef.equals(other.nodeRef))) {
            return false;
        }
        if (this.valueMap != other.valueMap && (this.valueMap == null || !this.valueMap.equals(other.valueMap))) {
            return false;
        }
        return true;
    }

    public boolean equals(DataVector obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataVector other = (DataVector) obj;
        if (this.nodeRef != other.nodeRef && (this.nodeRef == null || !this.nodeRef.equals(other.nodeRef))) {
            return false;
        }
        if (this.valueMap != other.valueMap && (this.valueMap == null || !this.valueMap.equals(other.valueMap))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.nodeRef != null ? this.nodeRef.hashCode() : 0);
        hash = 67 * hash + (this.valueMap != null ? this.valueMap.hashCode() : 0);
        return hash;
    }
    
//    public DataVector(double[] valueList){
//        this.dimValueList = valueList;
//    }
//    public DataVector(Double[] valueList){
//        int i = 0;
//        this.dimValueList = new double[valueList.length];
//        for (Double dd : valueList) {
//            this.dimValueList[i] = dd;
//            i++;
//        }
//        
//    }
//    public boolean equals(DataVector v){
//        if (v.valueMap.size() != this.valueMap.size())
//            return false;
//        for (Object key : this.valueMap.keySet()){
//            if (!v.valueMap.containsKey(key))
//                return false;
//            else if (v.valueMap.get(key) != this.valueMap.get(key))
//                return false;
//        }
//        return true;
////        for (int i = 0; i < dimValueList.length; i++){
////            if (v.dimValueList[i] != this.dimValueList[i])
////                return false;
////        }
////        return true;
//    }

    
//    public String toString(){
//        String s = "(";
//        for (String name : dimNameList)
//            s+=name+", ";
//        s+=")=(";
//        for (double value : dimValueList)
//            s+=value+", ";
//        s+=")";
//        return s;
//    }
    public String toString(){
        String s = "(", s2 = ")=(";
        for (String name : valueMap.keySet())
        {
            s+=name+", "; s2 += valueMap.get(name)+", ";
        }
        s2 += ")";
        return s+s2;
    }

    public static DataVector minusVector(DataVector v1, DataVector v2){
        DataVector resVec = new DataVector();
        Set<String> keyV2 = v2.valueMap.keySet();
        for (String s : v1.valueMap.keySet()){
            if (v2.valueMap.containsKey(s)){
                resVec.valueMap.put(s, v1.valueMap.get(s)-v2.valueMap.get(s));
                keyV2.remove(s);
            }
            else
            {
                resVec.valueMap.put(s,v1.valueMap.get(s));
            }
        }
        for (String s : keyV2){
            resVec.valueMap.put(s,-v2.valueMap.get(s));
        }
        return resVec;
    }

    /**
     * This is the modified version of minusVector, not a NORMAL subtraction.
     * It ignores the rest of the keys of v2 and only keys in v1 are retained.
     * This is used in the situation that we want to subtract v2 vector (ex. MeanVector of the graph view) from v1
     * and after that only v1 keys are shown.
     * @param v1
     * @param v2
     * @return a subtracted vector which have only the keys of v1.
     */
    public static DataVector modMinusVector(DataVector v1, DataVector v2){
        DataVector resVec = new DataVector();
//        Set<String> keyV2 = new HashSet<String>(v2.valueMap.keySet());
        for (String s : v1.valueMap.keySet()){
            if (v2.valueMap.containsKey(s)){
                resVec.valueMap.put(s, v1.valueMap.get(s)-v2.valueMap.get(s));
//                keyV2.remove(s);
            }
            else
            {
                resVec.valueMap.put(s,v1.valueMap.get(s));
            }
        }
//        for (String s : keyV2){
//            resVec.valueMap.put(s,-v2.valueMap.get(s));
//        }
        return resVec;
    }
}
