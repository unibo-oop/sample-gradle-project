package it.unibo.progetto_oop.StatePattern; // Or your appropriate package

import it.unibo.progetto_oop.google.CombatController;

public class PlayerTurnState implements CombatState { // Must implement the interface

    @Override
    public void enterState(CombatController context) {
        // --- What happens when this state starts ---
        System.out.println("Entering Player Turn State"); // For debugging
        context.getView().showOriginalButtons();       // Show Attack/Bag/Run/Info
        context.getView().setButtonsEnabled(true);      // Allow player input
        
    }

    @Override
    public void exitState(CombatController context) {
        // --- Optional cleanup when leaving this state ---
        System.out.println("Exiting Player Turn State"); // For debugging
        // Input disabling is usually handled by the *next* state's enterState
        // context.getView().clearInfo(); // Optional: Clear the info message
    }

    // --- Handle VALID inputs for this state ---

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        
        System.out.println("PlayerTurnState: Physical Attack requested.");
        // 1. Tell controller to change state because an animation will start
        context.setStates(new AnimatingState()); // Or use context.setStates if that's your final name
        
        // 2. Tell controller to start the actual attack sequence
        context.performPlayerPhysicalAttack();
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        System.out.println("PlayerTurnState: Long Range Attack requested (Poison: " + isPoison + ")");
        context.applyPlayerLongRangeAttack(isPoison);
        context.setStates(new AnimatingState()); // Or use context.setStates
    }

    @Override
    public void handleInfoInput(CombatController context) {
        System.out.println("PlayerTurnState: Info requested.");
        context.setStates(new AnimatingState()); // Assuming info has animation
        context.performInfoAnimation();
    }

    @Override
    public void handleBagInput(CombatController context) {
        System.out.println("PlayerTurnState: Bag action requested (Not Implemented).");
        context.getView().showInfo("Bag not implemented yet!");
        // Does NOT change state unless opening the bag leads to a blocking state/animation
    }

    @Override
    public void handleRunInput(CombatController context) {
        System.out.println("PlayerTurnState: Run action requested (Not Implemented).");
        context.getView().showInfo("Run not implemented yet!");
        // Might change state immediately to GameOverState (if successful escape) or EnemyTurnState (if failed)
    }

    // --- Handle inputs/events that are INVALID or UNEXPECTED in this state ---

    @Override
    public void handleBackInput(CombatController context) {
        // The 'Back' button when viewing the main Attack/Bag/Run/Info buttons
        // probably does nothing in terms of game state. The UI switch logic
        // for getting *to* the specific attack buttons (Physical/LongRange/etc.)
        // and back is likely handled just by the Controller/View directly.
        System.out.println("PlayerTurnState: Back input ignored in main turn view.");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        // An animation should not complete while waiting for player input.
        System.err.println("LOGIC ERROR: handleAnimationComplete called during PlayerTurnState!");
    }

    @Override
    public void handleTimerExpired(CombatController context) {
        // If you had a turn timer, you'd handle it here. Otherwise, ignore.
        System.out.println("PlayerTurnState: Timer expired (no action configured).");
    }
}