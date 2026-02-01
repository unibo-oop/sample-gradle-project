package it.unibo.makeanicecream.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.makeanicecream.model.IceCreamImpl;
import it.unibo.makeanicecream.model.ingredient.Conetype;
import it.unibo.makeanicecream.model.ingredient.FlavorType;
import it.unibo.makeanicecream.model.ingredient.LiquidTopping;
import it.unibo.makeanicecream.model.ingredient.SolidTopping;

/**
 * IceCreamBuilder is responsible for constructing an IceCreamImpl instance step by step.
 * It maintains the current state of the ice cream being built and provides methods to add flavors,
 * liquid toppings, and a solid topping. Once the ice cream is finalized (closed with a solid topping),
 * no further modifications are allowed. 
 */
public class IceCreamBuilder{
    private IceCreamImpl iceCream;
    private boolean toppingEnabled;

    /**
     * The constructor initializes the builder.
     * Default toppings are disabled.
     */
    public IceCreamBuilder(){
        this.toppingEnabled = false;
        this.iceCream = null;
    }

    /**
     * Enable or disable the addition of toppings.
     * @param enabled
     */
    public void setToppingEnabled(boolean enabled){
        this.toppingEnabled = enabled;
    }

    /**
     * Ensure that the ice cream has been initialized.
     * @throws IllegalStateException if the ice cream is not initialized
     */
    private void requiredInitialized(){
        if(this.iceCream == null){
            throw new IllegalStateException("Ice cream not initialized. Call reset() first.");
        }
    }
    /**
     * Start/Restart with an empty ice cream with the given cone type.
     * @param coneType the selected cone type for the ice cream
     */
    public void reset(final Conetype coneType){
        this.iceCream = new IceCreamImpl(
            coneType,
            new ArrayList<>(),
            new ArrayList<>(),
            null
        );
    }

    /**
     * Add a new flavor as a scoop to the ice cream.
     * A maximum of 3 flavors is allowed.
     * @param flavor the flavor to add
     * @throws IllegalStateException if the ice cream is closed or maximum flavors reached
     */
    public void addFlavor(FlavorType flavor){
        requiredInitialized();
        if(this.iceCream.isClosed()){
            throw new IllegalStateException("Ice cream is closed, cannot add more flavors.");
        }
        if(this.iceCream.getFlavors().size() >= 3){
            throw new IllegalStateException("Maximum number of flavors reached.");
        }

        this.iceCream.getFlavors().add(flavor);
        this.iceCream.getLiquidsToppings().add(null);
    }

    /**
     * Add a liquid topping associated with a specific flavor index.
     * @param index the index of the flavor to associate the liquid topping with 
     * @param liquid the liquid topping to add
     * @throws IllegalStateException if the ice cream is closed, liquid topping already present, or toppings are disabled
     * @throws IllegalArgumentException if the flavor index is invalid
     */
    public void addLiquidTopping(int index, LiquidTopping liquid){
        requiredInitialized();
        if(this.iceCream.isClosed()){
            throw new IllegalStateException("Ice cream is closed, cannot add liquid toppings.");
        }
        if(index < 0 || index >= this.iceCream.getFlavors().size()){
            throw new IllegalArgumentException("Invalid flavor index.");
        }
        if(this.iceCream.getLiquidsToppings().get(index) != null){
            throw new IllegalStateException("Liquid topping already present for this flavor.");
        }
        if(toppingEnabled == false){
            throw new IllegalStateException("Liquid toppings are disabled.");
        }

        this.iceCream.getLiquidsToppings().set(index, liquid);
    }

    /**
     * Add a solid topping on top of the ice cream.
     * Only one solid topping is allowed, and it closes the ice cream.
     * @param solid the solid topping to add
     * @throws IllegalStateException if the ice cream is already closed or toppings are disabled or no flavors present
     */
    public void addSolidTopping(SolidTopping solid){
        requiredInitialized();
        if(this.iceCream.isClosed()){
            throw new IllegalStateException("Ice cream is already closed, cannot add another solid topping.");
        }
        if(this.iceCream.getFlavors().isEmpty()){
            throw new IllegalStateException("Cannot add solid topping to an ice cream with no flavors.");
        }
        if(toppingEnabled == false){
            throw new IllegalStateException("Solid toppings are disabled.");
        }

        this.iceCream = new IceCreamImpl(
            this.iceCream.getConetype(),
            this.iceCream.getFlavors(),
            this.iceCream.getLiquidsToppings(),
            solid
        );
    }

    /**
     * Return the constructed IceCreamImpl instance.
     * @return the ice cream instance
     */
    public IceCreamImpl getIceCream(){
        requiredInitialized();
        return this.iceCream;
    }
}
