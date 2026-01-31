package it.unibo.makeanicecream.api;

import java.util.List;

import it.unibo.makeanicecream.model.ingredient.Conetype;
import it.unibo.makeanicecream.model.ingredient.FlavorType;
import it.unibo.makeanicecream.model.ingredient.LiquidTopping;
import it.unibo.makeanicecream.model.ingredient.SolidTopping;

/**
 * An ice cream configuration:
 * - the conetype
 * - the list of flavors
 * - the list of liquid toppings which associated to each flavor (null if absent)
 * - a single solid topping may be present on top (null if absent)
 * (if a solid topping is present, the ice cream is considered closed, so the ice cream is not modifiable anymore)
 */
public interface Icecream {

    /**
     * Return the type of the selected cone/type.
     * @return the conetype
     */
    Conetype getConetype();

    /**
     * Return the list of selected/ordered flavors.
     * @return the list of flavors
     */
    List<FlavorType> getFlavors();

    /**
     * The list is parallel to the flavors list: each position contains the liquid topping
     * associated to the flavor in the same position (null if absent).
     * @return the list of liquid toppings
     */
    List<LiquidTopping> getLiquidsToppings();

    /** 
     * Return the solid topping on top of the ice cream (null if absent).
     * @return the solid topping
     */
    SolidTopping getSolidTopping();

    /**
     * After the addition of a solid topping, the ice cream is not modifiable anymore.
     * @return true if the ice cream is closed (added a solid topping), false otherwise
     */
    boolean isClosed();
            
}
