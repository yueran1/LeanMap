package sharingiscaring.sharingiscaring.DataStructure;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kevin on 4/3/2016.
 */
public class Type {
    //http://www.smogon.com/dex/xy/types/
    //where bug v bug is [1][1] and water v water is [17][17]
    //[0][0] for when there isn't a second type
    private double[][] TypeTable = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,2,1,1,.5,.5,.5,.5,.5,2,1,1,1,.5,2,1,.5,1},
            {1,1,.5,1,1,.5,.5,1,1,2,1,1,1,1,1,2,1,1,1},
            {1,1,1,2,1,0,1,1,1,1,1,1,1,1,1,1,1,.5,1},
            {1,1,1,.5,.5,1,1,1,2,1,.5,0,1,1,1,1,1,1,2},
            {1,1,2,2,1,1,2,.5,1,1,1,1,1,1,.5,1,1,.5,1},
            {1,.5,2,1,1,.5,1,1,.5,0,1,1,2,2,.5,.5,2,2,1},
            {1,2,1,.5,1,1,1,.5,1,1,2,1,2,1,1,1,.5,2,.5},
            {1,2,1,1,.5,1,2,1,1,1,2,1,1,1,1,1,.5,.5,1},
            {1,1,.5,1,1,1,1,1,1,2,1,1,1,0,1,2,1,1,1},
            {1,.5,1,.5,1,1,1,.5,.5,1,.5,2,1,1,.5,1,2,.5,2},
            {1,.5,1,1,2,1,1,2,0,1,.5,1,1,1,2,1,2,2,1},
            {1,1,1,2,1,1,1,.5,2,1,2,2,.5,1,1,1,1,.5,.5},
            {1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,.5,.5,1},
            {1,1,1,1,1,2,1,1,1,.5,2,.5,1,1,.5,1,.5,0,2},
            {1,1,0,1,1,1,2,1,1,1,1,1,1,1,2,.5,1,.5,1},
            {1,2,1,1,1,1,.5,2,2,1,1,.5,2,1,1,1,1,.5,1},
            {1,1,1,1,.5,2,1,.5,1,1,1,1,2,1,1,1,2,.5,.5},
            {1,1,1,.5,1,1,1,2,1,1,.5,2,1,1,1,1,2,1,.5}};
    private Map<String,Integer> typeKeys;
    public Type() {

        this.typeKeys.put("",0);
        this.typeKeys.put("bug",1);
        this.typeKeys.put("dark",2);
        this.typeKeys.put("dragon",3);
        this.typeKeys.put("electric",4);
        this.typeKeys.put("fairy",5);
        this.typeKeys.put("fighting",6);
        this.typeKeys.put("fire",7);
        this.typeKeys.put("flying",8);
        this.typeKeys.put("ghost",9);
        this.typeKeys.put("grass",10);
        this.typeKeys.put("ground",11);
        this.typeKeys.put("ice",12);
        this.typeKeys.put("normal",13);
        this.typeKeys.put("poison",14);
        this.typeKeys.put("psychic",15);
        this.typeKeys.put("rock",16);
        this.typeKeys.put("steel",17);
        this.typeKeys.put("water",18);

    }

    public double getSTAB(String pokeType, String pokeType2, String moveType){
        if(pokeType.equals(moveType)||pokeType2.equals(moveType)){
            return 1.5;
        }
        return 1;
    }

    public int typeEffectiveness(String moveType,String enemyType1,String enemyType2){
        int multiplier=1;
        int moveIndex = typeKeys.get(moveType);
        int firstIndex = typeKeys.get(enemyType1);
        int secondIndex = typeKeys.get(enemyType2);
        multiplier *= TypeTable[moveIndex][firstIndex];
        multiplier *= TypeTable[moveIndex][secondIndex];
        return multiplier;
    }


}