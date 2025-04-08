package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.google.CombatController;

public class EnemyTurnState implements CombatState{

    @Override
    public void enterState(CombatController context) {
        
        context.getView().setButtonsEnabled(false); // in caso non fosse successo con animatingState
        context.startDelayedEnemyTurn();
    }

    @Override
    public void exitState(CombatController context) {
        
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        context.setStates(new AnimatingState());

        context.performEnemyTurn();
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBackInput'");
    }

    @Override
    public void handleBagInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public void handleTimerExpired(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTimerExpired'");
    }
}
