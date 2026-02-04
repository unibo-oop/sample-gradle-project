package it.unibo.makeanicecream.model.ingredient;

import it.unibo.makeanicecream.api.Ingredient;

/**
 * SolidTopping class representing a solid topping ingredient.
 */
public class SolidTopping implements Ingredient{
    private final SolidToppingType topping;

    /**
     * Constructor.
     * @param topping the solid topping type
     */
    public SolidTopping(final SolidToppingType topping){
        this.topping = topping;
    }

    /**
     * Returns the solid topping.
     * @return the solid topping type
     */
    public SolidToppingType getTopping(){
        return topping;
    }

    /**
     * Returns the ingredient type.
     * @return the ingredient type which is SOLID_TOPPING
     */
    @Override
    public IngredientType getType(){
        return IngredientType.SOLID_TOPPING;
    }
}
