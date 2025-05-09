package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class Healing implements PotionEffectStrategy{

    private final int healingAmount = 10;

    @Override
    public void apllyEffect(CombatModel user) {
        
        user.increasePlayerHealth(healingAmount);
    }
    
}
