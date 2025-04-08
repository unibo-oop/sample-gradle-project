package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class CurePoison implements PotionEffectStrategy{

    @Override
    public void apllyEffect(CombatModel user) {
        user.setPlayerPoisoned(false);
    }
    
}
