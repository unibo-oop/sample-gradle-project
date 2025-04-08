package it.unibo.progetto_oop.StatePattern;

import java.awt.Component;

import it.unibo.progetto_oop.google.CombatController;

public class InfoDisplayState implements CombatState{

    @Override
    public void enterState(CombatController context) {
        context.getView().showInfo("Name => " + context.getModel().getEnemyName());
        context.getView().showInfo("\n");
        context.getView().showInfo("Power => " + context.getModel().getEnemyPower());
        context.getView().showAttackOptions();
        context.getView().setButtonsEnabled(false);
        
        for (Component comp : context.getView().getAttackButtonPanel().getComponents()) {
            comp.setEnabled(false);
        }
        context.getView().getBackButton().setEnabled(true);
        context.getView().showAttackOptions();
    
    }

    @Override
    public void exitState(CombatController context) {
        context.getModel().resetPositions();
        context.redrawView();
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
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
        context.setStates(new PlayerTurnState());
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
