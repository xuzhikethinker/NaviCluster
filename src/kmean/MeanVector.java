/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kmean;

import java.util.Map;

/**
 *
 * @author Knacky
 */
public class MeanVector extends DataVector{
//    public MeanVector(double[] valueList){
//        this.dimValueList = valueList;
//    }
//    public MeanVector(Double[] valueList){
//        super(valueList);
//        int i = 0;
//        for (Double dd : valueList) {
//            this.dimValueList[i] = dd;
//            i++;
//        }
        
//    }
    public MeanVector(Map map){
        super(map);
    }
}
