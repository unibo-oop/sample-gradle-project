package it.unibo.makeanicecream.model.ingredient;

import it.unibo.makeanicecream.api.Topping;

/**
 * Topping in liquid form enumeration.
 */
public enum LiquidTopping implements Topping {
    CHOCOLATE_SYRUP,
    STRAWBERRY_SYRUP;

    @Override
    public boolean isLiquid() {
        return true;
    }
}
