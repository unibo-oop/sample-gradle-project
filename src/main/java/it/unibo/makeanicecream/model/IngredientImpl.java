package it.unibo.makeanicecream.model;

import it.unibo.makeanicecream.api.Ingredient;
import it.unibo.makeanicecream.model.ingredient.IngredientType;

/**
 * IngredientImpl class implementing the Ingredient interface.
 * This class models an ingredient with a name and type.
 */
public class IngredientImpl implements Ingredient {
    private final String name;
    private final IngredientType type;

    /**
     * Constructor.
     * @param name the name of the ingredient
     * @param type the type of the ingredient
     */
    public IngredientImpl(final String name, final IngredientType type){
        this.name = name;
        this.type = type;
    }

    /**
     * Get the type of the ingredient.
     * @return the ingredient type
     */
    @Override
    public IngredientType getType(){
        return this.type;
    }

    /**
     * Get the name of the ingredient.
     * @return the ingredient name
     */
    public String getName(){
        return this.name;
    }
}
