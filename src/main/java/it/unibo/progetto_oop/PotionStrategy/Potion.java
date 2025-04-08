package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class Potion {
    private final String name;
    private final String description;
    private int quantity;
    private final PotionEffectStrategy strategy;

    public Potion(String name, String description, int quantity, PotionEffectStrategy strategy){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.strategy = strategy;
    }

    public void use(CombatModel user){
        if (this.quantity > 0){
            this.strategy.apllyEffect(user);
            this.quantity--;
        }
        else{
            System.out.println("Non hai abbastanza pozioni " + this.name);
        }
    }

    public String getInfo(){
        return "Hai: " + this.quantity + "pozioni " + this.name + "la cui descrizione è " + this.description;
    }

}
