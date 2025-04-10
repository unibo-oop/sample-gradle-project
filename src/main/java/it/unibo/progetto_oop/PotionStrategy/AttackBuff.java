package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class AttackBuff implements PotionEffectStrategy {

    private final int power = 5;

    @Override
    public void apllyEffect(CombatModel user) {
        user.increasePlayerPower(this.power);
    }
    
}
