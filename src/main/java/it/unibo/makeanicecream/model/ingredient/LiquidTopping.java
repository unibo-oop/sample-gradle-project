package it.unibo.makeanicecream.model.ingredient;

import it.unibo.makeanicecream.api.Ingredient;

/**
 * LiquidTopping class representing a liquid topping ingredient.
 */
public class LiquidTopping implements Ingredient {

    private final LiquidToppingType topping;

    /**
     * Constructor for LiquidTopping.
     * @param topping the liquid topping type
     */
    public LiquidTopping(final LiquidToppingType topping){
        this.topping = topping;
    }

    /**
     * Returns the liquid topping.
     * @return the liquid topping type
     */
    public LiquidToppingType getTopping(){
        return topping;
    }

    /**
     * Returns the ingredient type.
     * @return the ingredient type which is LIQUID_TOPPING
     */
    @Override
    public IngredientType getType(){
        return IngredientType.LIQUID_TOPPING;
    }
}
