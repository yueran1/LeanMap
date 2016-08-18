package sharingiscaring.sharingiscaring.ViewPokemon;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;

/**
 * Created by Ryan on 2016-03-21.
 */
public class ViewPokemonStateOwnedUnavailable extends ViewPokemonState {
    private final Context context;
    private Pokemon item;
    private final TextView pokeView;
    private final TextView currentTrainer;
    private final TextView editNameOU;
    private final EditText editLevelOU;
    private final EditText editType1OU;
    private final EditText editType2OU;
    private final EditText editHPOU;
    private final EditText editAttackOU;
    private final EditText editDefenseOU;
    private final EditText editSpAtkOU;
    private final EditText editSpDefOU;
    private final EditText editSpeedOU;
    private final EditText editMove1OU;
    private final EditText editMove2OU;
    private final EditText editMove3OU;
    private final EditText editMove4OU;
    private final EditText editNatureOU;
    private final EditText editAbilityOU;
    private final EditText editItemOU;
    private final EditText description;
    private final EditText level;
    private final EditText type1;
    private final EditText type2;
    private final EditText nature;
    private final EditText ability;
    private final EditText pokeItem;
    private final EditText hp;
    private final EditText attack;
    private final EditText defense;
    private final EditText spAtk;
    private final EditText spDef;
    private final EditText speed;
    private final EditText move1;
    private final EditText move2;;
    private final EditText move3;
    private final EditText move4;

    public ViewPokemonStateOwnedUnavailable(Context context, Pokemon pokemon) {
        this.context = context;
        this.item = pokemon;

        pokeView = (TextView) ((Activity) context).findViewById(R.id.editNameOU);
        currentTrainer = (TextView) ((Activity) context).findViewById(R.id.editOwnerOU);
        editNameOU = (TextView) ((Activity) context).findViewById(R.id.editNameOU);
        editLevelOU = (EditText) ((Activity) context).findViewById(R.id.editLevelOU);
        editType1OU = (EditText) ((Activity) context).findViewById(R.id.editType1OU);
        editType2OU = (EditText) ((Activity) context).findViewById(R.id.editType2OU);
        editHPOU = (EditText) ((Activity) context).findViewById(R.id.editHPOU);
        editAttackOU = (EditText) ((Activity) context).findViewById(R.id.editAttackOU);
        editDefenseOU = (EditText) ((Activity) context).findViewById(R.id.editDefenseOU);
        editSpAtkOU = (EditText) ((Activity) context).findViewById(R.id.editSpAtkOU);
        editSpDefOU = (EditText) ((Activity) context).findViewById(R.id.editSpDefOU);
        editSpeedOU = (EditText) ((Activity) context).findViewById(R.id.editSpeedOU);
        editMove1OU = (EditText) ((Activity) context).findViewById(R.id.editMove1OU);
        editMove2OU = (EditText) ((Activity) context).findViewById(R.id.editMove2OU);
        editMove3OU = (EditText) ((Activity) context).findViewById(R.id.editMove3OU);
        editMove4OU = (EditText) ((Activity) context).findViewById(R.id.editMove4OU);
        editNatureOU = (EditText) ((Activity) context).findViewById(R.id.editNatureOU);
        editAbilityOU = (EditText) ((Activity) context).findViewById(R.id.editAbilityOU);
        editItemOU = (EditText) ((Activity) context).findViewById(R.id.editItemOU);
        description = (EditText) ((Activity) context).findViewById(R.id.editDescriptionOU);
        level = (EditText) ((Activity) context).findViewById(R.id.editLevelOU);
        type1 = (EditText) ((Activity) context).findViewById(R.id.editType1OU);
        type2 = (EditText) ((Activity) context).findViewById(R.id.editType2OU);
        nature = (EditText) ((Activity) context).findViewById(R.id.editNatureOU);
        ability = (EditText) ((Activity) context).findViewById(R.id.editAbilityOU);
        pokeItem = (EditText) ((Activity) context).findViewById(R.id.editItemOU);
        hp = (EditText) ((Activity) context).findViewById(R.id.editHPOU);
        attack = (EditText) ((Activity) context).findViewById(R.id.editAttackOU);
        defense = (EditText) ((Activity) context).findViewById(R.id.editDefenseOU);
        spAtk = (EditText) ((Activity) context).findViewById(R.id.editSpAtkOU);
        spDef = (EditText) ((Activity) context).findViewById(R.id.editSpDefOU);
        speed = (EditText) ((Activity) context).findViewById(R.id.editSpeedOU);
        move1 = (EditText) ((Activity) context).findViewById(R.id.editMove1OU);
        move2 = (EditText) ((Activity) context).findViewById(R.id.editMove2OU);
        move3 = (EditText) ((Activity) context).findViewById(R.id.editMove3OU);
        move4 = (EditText) ((Activity) context).findViewById(R.id.editMove4OU);
    }

    public void populate() {
        pokeView.setText(item.getTitle());
        currentTrainer.setText(item.getOwner());
        editNameOU.setText(item.getTitle());
        editLevelOU.setText(Integer.toString(item.getLevel()), TextView.BufferType.EDITABLE);
        editType1OU.setText(item.getPrimaryType(), TextView.BufferType.EDITABLE);
        editType2OU.setText(item.getSecondaryType(), TextView.BufferType.EDITABLE);
        editHPOU.setText(Integer.toString(item.getHealthPoints()), TextView.BufferType.EDITABLE);
        editAttackOU.setText(Integer.toString(item.getAttack()), TextView.BufferType.EDITABLE);
        editDefenseOU.setText(Integer.toString(item.getDefense()), TextView.BufferType.EDITABLE);
        editSpAtkOU.setText(Integer.toString(item.getSpecialAttack()), TextView.BufferType.EDITABLE);
        editSpDefOU.setText(Integer.toString(item.getSpecialDefense()), TextView.BufferType.EDITABLE);
        editSpeedOU.setText(Integer.toString(item.getSpeed()), TextView.BufferType.EDITABLE);
        editMove1OU.setText(item.getMove1(), TextView.BufferType.EDITABLE);
        editMove2OU.setText(item.getMove2(), TextView.BufferType.EDITABLE);
        editMove3OU.setText(item.getMove3(), TextView.BufferType.EDITABLE);
        editMove4OU.setText(item.getMove4(), TextView.BufferType.EDITABLE);
        editNatureOU.setText(item.getNature(), TextView.BufferType.EDITABLE);
        editAbilityOU.setText(item.getAbility(), TextView.BufferType.EDITABLE);
        editItemOU.setText(item.getPokemonItem(), TextView.BufferType.EDITABLE);
        description.setText(item.getDescription(), TextView.BufferType.EDITABLE);
        level.setText(Integer.toString(item.getLevel()), TextView.BufferType.EDITABLE);
        type1.setText(item.getPrimaryType(), TextView.BufferType.EDITABLE);
        type2.setText(item.getSecondaryType(), TextView.BufferType.EDITABLE);
        nature.setText(item.getNature(), TextView.BufferType.EDITABLE);
        ability.setText(item.getAbility(), TextView.BufferType.EDITABLE);
        pokeItem.setText(item.getPokemonItem(), TextView.BufferType.EDITABLE);
        hp.setText(Integer.toString(item.getHealthPoints()), TextView.BufferType.EDITABLE);
        attack.setText(Integer.toString(item.getAttack()), TextView.BufferType.EDITABLE);
        defense.setText(Integer.toString(item.getDefense()), TextView.BufferType.EDITABLE);
        spAtk.setText(Integer.toString(item.getSpecialAttack()), TextView.BufferType.EDITABLE);
        spDef.setText(Integer.toString(item.getSpecialDefense()), TextView.BufferType.EDITABLE);
        speed.setText(Integer.toString(item.getSpeed()), TextView.BufferType.EDITABLE);
        move1.setText(item.getMove1(), TextView.BufferType.EDITABLE);
        move2.setText(item.getMove2(), TextView.BufferType.EDITABLE);
        move3.setText(item.getMove3(), TextView.BufferType.EDITABLE);
        move4.setText(item.getMove4(), TextView.BufferType.EDITABLE);
    }

    public Pokemon getItem(){
        item.setPokemonName(editNameOU.getText().toString());
        item.setLevel(Integer.decode(editLevelOU.getText().toString()));
        item.setPrimaryType(editType1OU.getText().toString());
        item.setSecondaryType(editType2OU.getText().toString());
        item.setHealthPoints(Integer.decode(editHPOU.getText().toString()));
        item.setAttack(Integer.decode(editAttackOU.getText().toString()));
        item.setDefense(Integer.decode(editDefenseOU.getText().toString()));
        item.setSpecialAttack(Integer.decode(editSpAtkOU.getText().toString()));
        item.setSpecialDefense(Integer.decode(editSpDefOU.getText().toString()));
        item.setSpeed(Integer.decode(editSpeedOU.getText().toString()));
        item.setMove1Name(editMove1OU.getText().toString());
        item.setMove2Name(editMove2OU.getText().toString());
        item.setMove3Name(editMove3OU.getText().toString());
        item.setMove4Name(editMove4OU.getText().toString());
        item.setNature(editNatureOU.getText().toString());
        item.setAbility(editAbilityOU.getText().toString());
        item.setDescription(description.getText().toString());
        return item;
    }
}
