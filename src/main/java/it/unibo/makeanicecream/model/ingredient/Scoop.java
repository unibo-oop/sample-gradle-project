package it.unibo.makeanicecream.model.ingredient;

import it.unibo.makeanicecream.api.Ingredient;

/**
 * Scoop class representing an ice cream scoop ingredient.
 */
public class Scoop implements Ingredient {

    private final FlavorType flavor;

    /**
     * Constructor with the specified flavor.
     * @param flavor
     */
    public Scoop(final FlavorType flavor) {
        this.flavor = flavor;
    }

    /**
     * Get the flavor of the scoop.
     * @return the flavor
     */
    public FlavorType getFlavor() {
        return flavor;
    }

    /**
     * Get the type of the ingredient.
     * @return the ingredient type, in this case SCOOP
     */
    @Override
    public IngredientType getType() {
        return IngredientType.SCOOP;
    }
}
