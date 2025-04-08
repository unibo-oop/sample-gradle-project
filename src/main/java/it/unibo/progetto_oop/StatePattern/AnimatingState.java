package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.combattimento.Position;
import it.unibo.progetto_oop.google.CombatController;
import it.unibo.progetto_oop.google.CombatModel;
import it.unibo.progetto_oop.google.CombatView;

public class AnimatingState implements CombatState{
    Position centerOfDying;

    @Override
    public void enterState(CombatController context) {
        System.out.println("\n\nEntering Animating State\n\n");
        context.getView().setButtonsEnabled(false);
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("Exit\n");
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // Non deve fare assolutamente niente DA IGNORARE
        return;
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

        CombatModel model = context.getModel();
        CombatView view = context.getView();

        boolean isPlayerTurn = model.isPlayerTurn();
        boolean isEnemyPoisoned = model.isEnemyPoisoned();

        if (!isPlayerTurn){
            if (isEnemyPoisoned && model.getEnemyHealth() > 0){
                System.out.println("FUNZIONA \n\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Health => " + model.getEnemyHealth());
                model.decreaseEnemyHealth(model.getPlayerPoisonPower());
                System.out.println("Health => " + model.getEnemyHealth());
                view.updateEnemyHealth(model.getEnemyHealth());
            }
        }

        else {
            /*
             * Effetti fine turno nemico da fare più avanti
            */
        }

        if (context.checkGameOver()) {
            System.out.println("Game Over detected. Starting death animation.");
    
            // Determine who died and get their position
            if (model.getPlayerHealth() <= 0) {
                centerOfDying = model.getPlayerPosition(); // Use player's last known position
                System.out.println("Player died at " + centerOfDying);
            } else {
                centerOfDying = model.getEnemyPosition(); // Use enemy's last known position
                System.out.println("Enemy died at " + centerOfDying);
            }
    
            // *** Instead of setState(GameOver), start the death animation ***
            // The callback will set the GameOverState when the animation finishes
            context.performDeathAnimation(centerOfDying, () -> {
                System.out.println("Death animation onComplete: Setting GameOverState.");
                context.setStates(new GameOverState());
            });
    
            return; // Stop further processing (don't transition to next turn)
        }
        else{
            context.setStates(isPlayerTurn ? new PlayerTurnState() : new EnemyTurnState());
        }

    }

    @Override
    public void handleTimerExpired(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTimerExpired'");
    }
    
}
