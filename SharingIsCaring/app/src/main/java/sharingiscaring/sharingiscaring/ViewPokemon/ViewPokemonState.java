package sharingiscaring.sharingiscaring.ViewPokemon;

import sharingiscaring.sharingiscaring.DataStructure.Offer;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;

/**
 * Created by Ryan on 2016-03-21.
 */
public abstract class ViewPokemonState {
    public abstract void populate();
    public abstract Pokemon getItem();
    public Offer getOffer(){return null;}
}
