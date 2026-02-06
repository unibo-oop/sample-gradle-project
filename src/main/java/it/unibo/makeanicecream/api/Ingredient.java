package it.unibo.makeanicecream.api;

import it.unibo.makeanicecream.model.ingredient.IngredientType;

/**
 * Ingredient interface.
 */
@FunctionalInterface
public interface Ingredient {
        /**
         * Returns the ingredient type.
         * 
         * @return the ingredient type
         */
        IngredientType getType();
}
