package it.unibo.makeanicecream.api;

import java.util.List;

import it.unibo.makeanicecream.model.ingredient.Conetype;
/**
 * An ice cream configuration:
 * - the conetype
 * - the list of ingredients (scoops/toppings)
 * (if a solid topping is present, the ice cream is considered closed, so the ice cream is not modifiable anymore)
 */
public interface Icecream {

    /**
     * Return the type of the selected cone/type.
     * @return the conetype
     */
    Conetype getConetype();

    /**
     * Return the list of selected/ordered ingredients (scoops/toppings).
     * @return the list of ingredients
     */
    List<Ingredient> getIngredients();

    /**
     * Return if the ice cream is closed (solid topping present).
     * @return true if closed, false otherwise
     */
    boolean isClosed();
            
}
