/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kmean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Knacky
 */
public class DataCluster{
    DataVector representative = null;
    
    public DataVector getRepresentative() {
        return representative;
    }
    List<DataVector> members = new LinkedList<DataVector>();
    String name = "";
    
    public List<DataVector> getMembers(){
        return members;
    }
    public void setRepresentative(DataVector v){
        representative = v;
    }
    public DataVector find(DataVector v){
        DataVector temp = null;
        for (DataVector data : members){
            if (data.equals(v)){
                temp = data;
                break;
            }
        }
        return temp;
    }
    public void addAll(Collection<DataVector> dvList){
        for (DataVector dv : dvList){
            this.add(dv);
        }
    }
    public void add(DataVector v){
        if (find(v) == null){
            members.add(v);
            v.belongToCluster = this;
        }
    }
    public DataVector remove(DataVector v){
        DataVector temp = find(v);
        if (temp != null){
            members.remove(temp);
            temp.belongToCluster = null;
        }
        return temp;
    }
    public String toString(){
        String s = "[";
        for (int i = 0; i < members.size(); i++){
            s += members.get(i).toString()+" / ";
        }
        s += "]";
        return s;
    }
    
    public static DataVector findCentroidFromDCList(DataCluster[] dcList,int numOfAllMembers){
        
        Map<String,Double> valMap = new TreeMap<String,Double>();
//        int numOfAllMembers = 0;
//        for (DataCluster dc : dcList){
//            numOfAllMembers += dc.members.size();
//        }
        for (DataCluster dc : dcList){
                        
            for (DataVector member : dc.getMembers()){
//                System.out.println("member: "+member);
//                for (int j = 0; j < member.dimValueList.length; j++) {
                for (String key : member.getValueMap().keySet()) {
//                    int index = nameArrList.indexOf(member.dimNameList[j]);
//                    int index = nameArrList.indexOf(key);
//                    if (index == -1){
                    if (!valMap.containsKey(key)){
//                        nameArrList.add(member.dimNameList[j]);
//                        zigmaList.add((double)member.dimValueList[j]/clusterList[i].members.size());
                        valMap.put(key, (double)member.getValueMap().get(key)/numOfAllMembers);
                    } else {
                        valMap.put(key, valMap.get(key)+(double)member.getValueMap().get(key)/numOfAllMembers);
//                        zigmaList.set(index,zigmaList.get(index)+(double)member.dimValueList[j]/clusterList[i].members.size());
                    }
//                    zigmaList[j] += (double)member.dimValueList[j]/clusterList[i].members.size();
                }
                
            }
            
        }
        return new MeanVector(valMap);
    }
    
}
