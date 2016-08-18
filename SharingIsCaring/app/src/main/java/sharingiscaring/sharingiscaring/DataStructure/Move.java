package sharingiscaring.sharingiscaring.DataStructure;

/**
 * @author klark
 * @version 0.1
 * @since 11/02/16
 *
 *
 */

public class Move {
    private String moveName;
    // A move has a base power that it checks against the attack type
    // ie. drain punch has a power of 75
    private int damagePower;
    //private int accuracy;
    private String type; // each move has a SINGLE type
    private String attackType; //Physical or Special attacks determine
    private int index;

    public Move(String moveName, int damagePower, int accuracy,
                String type, String attackType, int index) {
        this.moveName = moveName;
        this.damagePower = damagePower;
        //this.accuracy = accuracy;
        this.type = type;
        this.attackType = attackType;
        this.index=index; //ask stu if needed for ES
    }
    public Move(){
        this.moveName = "default";
        this.damagePower = 100;
        this.type = "";
        this.attackType="physical";
        this.index=0;
    }

    public String getMoveName() {return moveName;}
    public void setMoveName(String moveName) {this.moveName = moveName; }

    public int getDamagePower() {return damagePower;}
    public void setDamagePower(int damagePower) {this.damagePower = damagePower;}

    /*public int getAccuracy() {return accuracy;}
    public void setAccuracy(int accuracy) {this.accuracy = accuracy;}*/

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getAttackType() {return attackType;}
    public void setAttackType(String attackType) {this.attackType = attackType;}
    // whether SpA or Att Stat is used

    public int getIndex() {return index;}
    public void setIndex(int index) {this.index = index;}
}
