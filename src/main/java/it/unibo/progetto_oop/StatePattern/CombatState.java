package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.google.CombatController;

public interface CombatState {
    
    void enterState(CombatController context);

    void exitState(CombatController context);

    void handlePhysicalAttackInput(CombatController context);

    void handleLongRangeAttackInput(CombatController context, boolean isPoison);

    void handleInfoInput(CombatController context);

    void handleBackInput(CombatController context);

    void handleBagInput(CombatController context);

    void handleRunInput(CombatController context);

    void handleAnimationComplete(CombatController context);

    void handleTimerExpired(CombatController context);
}
