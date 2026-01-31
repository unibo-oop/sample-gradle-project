package it.unibo.makeanicecream.model;

import java.util.List;
import it.unibo.makeanicecream.api.Icecream;
import it.unibo.makeanicecream.model.ingredient.Conetype;
import it.unibo.makeanicecream.model.ingredient.FlavorType;
import it.unibo.makeanicecream.model.ingredient.LiquidTopping;
import it.unibo.makeanicecream.model.ingredient.SolidTopping;

/**
 * IceCreamImpl class implementing the Icecream interface.
 * This class models the state of an ice cream, including cone type,
 * flavors, liquid toppings, and optional solid topping.
 * It contains no construction logic and is updated by the builder.
 */
public class IceCreamImpl implements Icecream{
    final Conetype conetype;
    final List<FlavorType> flavors;
    final List<LiquidTopping> liquidToppings;
    final SolidTopping solidTopping;

    /**
     * Constructor.
     * @param conetype the type of cone
     * @param flavors the list of flavors
     * @param liquidToppings the list of liquid toppings
     * @param solidTopping the solid topping
     * @returns new IceCreamImpl instance
     */
    public IceCreamImpl(final Conetype conetype, final List<FlavorType> flavors,
            final List<LiquidTopping> liquidToppings, final SolidTopping solidTopping){
        
        this.conetype = conetype;
        this.flavors = flavors;
        this.liquidToppings = liquidToppings;
        this.solidTopping = solidTopping;
    }

    @Override
    public Conetype getConetype(){
        return conetype;
    }

    @Override
    public List<FlavorType> getFlavors(){
        return flavors;
    }

    @Override
    public List<LiquidTopping> getLiquidsToppings(){
        return liquidToppings;
    }

    @Override
    public SolidTopping getSolidTopping(){
        return solidTopping;
    }

    @Override
    public boolean isClosed(){
        return solidTopping != null;
    }
}
