package sharingiscaring.sharingiscaring;

import android.test.ActivityInstrumentationTestCase2;

import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;

/**
 * Created by hoye on 3/14/16.
 */
public class testElasticSearchPokemon extends ActivityInstrumentationTestCase2 {
    public testElasticSearchPokemon() { super(EsPokemonControl.class); }

    public void testAddPokemon() throws ExecutionException, InterruptedException {
        EsPokemonControl.AddPokemonTask addPokemon = new EsPokemonControl.AddPokemonTask();
        EsPokemonControl.GetPokemonTask getPokemon = new EsPokemonControl.GetPokemonTask();
        User user = new User();
        Pokemon pokemon = new Pokemon();
        Pokemon result;

        addPokemon.execute(pokemon);
        getPokemon.execute(pokemon.getIdentifier());
        result = getPokemon.get().get(0);

        assertEquals(result.getOwner(), user.getName());
    }
}
