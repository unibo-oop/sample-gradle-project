package it.unibo.makeanicecream.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.makeanicecream.api.Ingredient;
import it.unibo.makeanicecream.model.ingredient.Conetype;
import it.unibo.makeanicecream.model.ingredient.IngredientType;

/**
 * IceCreamBuilder is responsible for constructing an IceCreamImpl instance step by step.
 * It maintains the current state of the ice cream being built and provides methods to add flavors,
 * liquid toppings, and a solid topping. Once the ice cream is finalized (closed with a solid topping),
 * no further modifications are allowed. 
 */

public class IceCreamBuilder {
    private static final int MAX_SCOOPS = 3;
    private Conetype cone;
    private final List<Ingredient> lisIngredients = new ArrayList<>();
    private boolean isClosed;
    private boolean toppingEnabled;

    /**
     * Constructor.
     */
    public IceCreamBuilder() {
        this.cone = null;
        this.isClosed = false;
        this.toppingEnabled = false;
    }

    /**
     * Enable (after level >=4) or disable the addition of toppings.
     * 
     * @param enabled true to enable, false to disable
     */
    public void setToppingEnabled(final boolean enabled) {
        this.toppingEnabled = enabled;
    }

    /**
     * Return if the toppings are enabled.
     * 
     * @return true if enabled, false otherwise
     */
    public boolean isToppingEnabled() {
        return this.toppingEnabled;
    }

    /**
     * Return if the ice cream is closed (solid topping present).
     * 
     * @return true if closed, false otherwise
     */
    public boolean isClosed() {
        return this.isClosed;
    }

    /**
     * Choose a cone for the ice cream.
     * 
     * @param conetype the cone type to choose
     * @return true if successfully chosen, false otherwise
     */
    public boolean chooseCone(final Conetype conetype) {
        if (conetype == null) {
            return false;
        }
        this.cone = conetype;
        return true;
    }

    /**
     * Count the number of scoops in the current ingredients list.
     * 
     * @return the number of scoops
     */
    private int countScoops() {
        int count = 0;
        for (final Ingredient ingredient : lisIngredients) {
            if (ingredient.getType() == IngredientType.SCOOP) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check if there is at least one scoop in the current ingredients list.
     * 
     * @return true if there is at least one scoop, false otherwise
     */
    private boolean hasScoops() {
        return countScoops() > 0;
    }

    /**
     * Try to add an ingredient to the ice cream.
     * 
     * @param ingredient the ingredient to add
     * @return true if added successfully, false otherwise
     */
    public boolean addIngredient(final Ingredient ingredient) {
        //ingredient cannot be null
        if (ingredient == null) {
            return false;
        }

        //cone must be selected
        if (cone == null) {
            return false;
        }

        //after solid topping, no more ingredients can be added
        if (isClosed) {
            return false;
        }

        //if topping are disabled, cannot add toppings
        if (!toppingEnabled && ingredient.getType() != IngredientType.SCOOP) {
            return false;
        }

        //when adding a topping, at least one scoop must be present
        if (ingredient.getType() != IngredientType.SCOOP && !hasScoops()) {
            return false;
        }

        //cannot add more than MAX_SCOOPS scoops
        if (ingredient.getType() == IngredientType.SCOOP && countScoops() >= MAX_SCOOPS) {
            return false;
        }

        //if solid topping is added, ice cream is closed
        if (ingredient.getType() == IngredientType.SOLID_TOPPING) {
            isClosed = true;
        }

        this.lisIngredients.add(ingredient);
        return true;
    }

    /**
     * Return the constructed IceCreamImpl instance.
     * 
     * @return the ice cream instance
     */
    public IceCreamImpl getIceCream() {
        return new IceCreamImpl(this.cone, List.copyOf(lisIngredients), this.isClosed);
    }

    /**
     * Submit the current ice cream and return it.
     * 
     * @return the ice cream instance
     */
    public IceCreamImpl submit() {
        return getIceCream();
    }

    /**
     * Restart with an empty ice cream.
     */
    public void reset() {
        cone = null;
        lisIngredients.clear();
        isClosed = false;
    }
}
