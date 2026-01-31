package it.unibo.makeanicecream.api;

/**
 * Component representing a topping.
 * The construction logic requires only the topping type (liquid or solid).
 */
public interface Topping {
    /**
     * Return the type of the topping.
     * @return true if the topping is liquid, false if it is solid
     */
    boolean isLiquid();    
}
