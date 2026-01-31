package it.unibo.makeanicecream.model.ingredient;

import it.unibo.makeanicecream.api.Topping;

/**
 * Topping in solid form enumeration.
 */
public enum SolidTopping implements Topping {
    /**
     * Cherry topping.
     */
    CHERRY,
    /**
     * Cookies topping.
     */
    COOKIES;

    @Override
    public boolean isLiquid() {
        return false;
    }
}
