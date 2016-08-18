package sharingiscaring.sharingiscaring.DataStructure;

import java.util.ArrayList;

/**
 * @author klark
 * @version 0.1
 * @since 11/02/16
 *
 */

public class Pokemon extends Item {
    //private ArrayList<Integer> stats; for now i am ignoring EV's and IV's
    //Should we contract the variable names to HP, Att, Def, SpA, SpD, Spe

    private String pokemonName;
    private String Ability;
    private int Level;
    private int HealthPoints;
    private int Attack;
    private int Defense;
    private int SpecialAttack;
    private int SpecialDefense;
    private int Speed;
    private String PrimaryType;
    private String SecondaryType;
    private String move1;
    private String move2;
    private String move3;
    private String move4;
    //should we give nature and item their own class because the impact the stats
    private String nature;
    // talk with group about using items or not due to variety and ease of implementation
    private String pokemonItem;

    public Pokemon(String identifier, String owner, String title, String description,
                   ArrayList<Offer> offers, String ability, int level, int healthPoints, int attack,
                   int defense, int specialAttack, int specialDefense, int speed,
                   String primaryType, String secondaryType, String move1name, String move2name,
                   String move3name, String move4name, String nature, String pokemonItem) {
        super(identifier, owner, title, description, offers);
        Ability = ability;
        Level = level;
        HealthPoints = healthPoints;
        Attack = attack;
        Defense = defense;
        SpecialAttack = specialAttack;
        SpecialDefense = specialDefense;
        Speed = speed;
        PrimaryType = primaryType;
        SecondaryType = secondaryType;
        move1 = move1name;
        move2 = move2name;
        move3 = move3name;
        move4 = move4name;
        this.nature = nature;
        this.pokemonItem = pokemonItem;
    }

    public Pokemon() {
        super();
        Ability = "";
        Level = 0;
        HealthPoints = 0;
        Attack = 0;
        Defense = 0;
        SpecialAttack = 0;
        SpecialDefense = 0;
        Speed = 0;
        PrimaryType = "";
        SecondaryType = "";
        this.move1 = "";
        this.move2 = "";
        this.move3 = "";
        this.move4 = "";
        this.nature = "";
        this.pokemonItem = "";
    }

    public String getAbility() {
        return Ability;
    }

    public void setAbility(String ability) {
        Ability = ability;
    }

    public int getLevel() {
        return Level;
    }
    public void setLevel(int level) {
        Level = level;
    }

    public int getHealthPoints() {
        return HealthPoints;
    }
    public void setHealthPoints(int healthPoints) {
        HealthPoints = healthPoints;
    }

    public int getAttack() {
        return Attack;
    }
    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getDefense() {
        return Defense;
    }
    public void setDefense(int defense) {
        Defense = defense;
    }

    public int getSpecialAttack() {
        return SpecialAttack;
    }
    public void setSpecialAttack(int specialAttack) {
        SpecialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return SpecialDefense;
    }
    public void setSpecialDefense(int specialDefense) {
        SpecialDefense = specialDefense;
    }

    public int getSpeed() {
        return Speed;
    }
    public void setSpeed(int speed) {
        Speed = speed;
    }

    public String getPrimaryType() {
        return PrimaryType;
    }
    public void setPrimaryType(String primaryType) {
        PrimaryType = primaryType;
    }

    public String getSecondaryType() {
        return SecondaryType;
    }
    public void setSecondaryType(String secondaryType) {
        SecondaryType = secondaryType;
    }

    public String getMove1() {
        return move1;
    }
    public void setMove1Name(String move1name) {this.move1 = move1name; }

    public String getMove2() {
        return move2;
    }

    public void setMove2Name(String move2name) {this.move2 = move2name; }

    public String getMove3() {
        return move3;
    }

    public void setMove3Name(String move3name) {this.move3 = move3name; }

    public String getMove4() {
        return move4;
    }

    public void setMove4Name(String move4name) {this.move4 = move4name;}

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getPokemonItem() {
        return pokemonItem;
    }

    public void setPokemonItem(String pokemonItem) {
        this.pokemonItem = pokemonItem;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }
}
