package it.unibo.progetto_oop.google;


import javax.swing.Timer; // Use Swing Timer for UI updates
import java.util.List;
import it.unibo.progetto_oop.Button_commands.LongRangeButton;
import it.unibo.progetto_oop.Button_commands.MeleeButton;
import it.unibo.progetto_oop.StatePattern.AnimatingState;
import it.unibo.progetto_oop.StatePattern.CombatState;
import it.unibo.progetto_oop.StatePattern.InfoDisplayState;
import it.unibo.progetto_oop.StatePattern.PlayerTurnState;
import it.unibo.progetto_oop.combattimento.Position;

public class CombatController {

    private final CombatModel model;
    private final CombatView view;
    private final MeleeButton meleeCommand; // Command for physical movement/attack
    private final LongRangeButton longRangeCommand; // Command for long range attack
    private CombatState currentState;

    private Timer animationTimer; // Timer for animations
    // Constants for animation/timing
    private static final int ANIMATION_DELAY = 100; // ms
    private static final int INFO_ZOOM_DELAY = 200; // ms for zoomer animation
    
    public CombatController(CombatModel model, CombatView view) {
        this.model = model;
        this.view = view;
        this.meleeCommand = new MeleeButton();
        this.longRangeCommand = new LongRangeButton();

        // Initialize View based on Model
        this.view.setHealthBarMax(model.getMaxHealth());
        this.view.updatePlayerHealth(model.getPlayerHealth());
        this.view.updateEnemyHealth(model.getEnemyHealth());
        this.redrawView(); // Initial draw

        // Attach listeners from View to Controller methods
        attachListeners();

        this.currentState = new PlayerTurnState();
    }

    private void attachListeners() {
        view.addAttackButtonListener(e -> handleAttackMenu());
        view.addPhysicalAttackButtonListener(e -> handlePlayerPhysicalAttack());
        view.addLongRangeButtonListener(e -> handlePlayerLongRangeAttack(false)); // false = not poison
        view.addPoisonButtonListener(e -> handlePlayerLongRangeAttack(true)); // true = poison
        view.addBackButtonListener(e -> handleBackToMainMenu());
        view.addInfoButtonListener(e -> handleInfo());
        view.addBagButtonListener(e -> System.out.println("Bag clicked - Not implemented")); // Placeholder
        view.addRunButtonListener(e -> System.out.println("Run clicked - Not implemented"));   // Placeholder
    }

    public void startCombat() {
        view.display();
        // Any other initial setup
    }

    public void redrawView() {
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getFlamePosition(),
            true, true, false, false, 1, 1, false, new Position(0, 0)); // Default redraw
        view.updatePlayerHealth(model.getPlayerHealth());
        view.updateEnemyHealth(model.getEnemyHealth());
    }

    private void redrawView(boolean drawFlame, boolean drawPoison, boolean drawPlayer, int playerRange, int enemyRange) {
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getFlamePosition(),
            drawPlayer, true, drawFlame, drawPoison, playerRange, enemyRange, false, new Position(0, 0));
        view.updatePlayerHealth(model.getPlayerHealth());
        view.updateEnemyHealth(model.getEnemyHealth());
    }


    // --- Action Handlers (called by View listeners) ---

    private void handleAttackMenu() {
        if (!model.isPlayerTurn()) {
            return; // Only allow actions on player's turn
        }
        view.showAttackOptions();
        view.clearInfo();
    }

    private void handleBackToMainMenu() {
        this.currentState.handleBackInput(this);
        view.showOriginalButtons();
        view.clearInfo();
        // Stop any ongoing animations if necessary
        stopAnimationTimer();
    }

    private void handleInfo() {
        
        this.currentState.handleInfoInput(this);
    }

    public void handlePlayerPhysicalAttack() {

        currentState.handlePhysicalAttackInput(this);
    }

    public void performPlayerPhysicalAttack(){
        // Define what happens after the player's attack animation completes
        Runnable onPlayerAttackComplete = () -> {
            
            this.currentState.handleAnimationComplete(this);
            
            // If enemy survived, start enemy turn after a delay
            startDelayedEnemyTurn();

        };

        animatePhysicalMove(
                model.getPlayerPosition(),
                model.getEnemyPosition(),
                true, // isPlayerAttacker
                model.getPlayerPower(),
                onPlayerAttackComplete
        );
    }

    public void handlePlayerLongRangeAttack(boolean applyPoison) {

        this.currentState.handleLongRangeAttackInput(this, applyPoison);
    }
    
    public void applyPlayerLongRangeAttack(boolean applyPoison){
        
        longRangeAttackAnimation(applyPoison, () -> {
            System.out.println("IS PLAYER TURN => " + model.isPlayerTurn() + "\n\n\n\n\n\n\n\n\n\n");
            model.setPlayerTurn(!model.isPlayerTurn());
            System.out.println("IS PLAYER TURN => " + model.isPlayerTurn() + "\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Player STATE => " + this.currentState.getClass().getName());
            this.currentState.handleAnimationComplete(this);
        });
    }

    // --- Enemy Turn Logic ---

    public void startDelayedEnemyTurn() {
        
        // Use a timer for the delay
        
        Timer enemyTurnDelayTimer = new Timer(150, e -> {
            this.handleEnemyTurn();
        });
        
        enemyTurnDelayTimer.setRepeats(false);
        enemyTurnDelayTimer.start();
    }

    private void handleEnemyTurn() {
    
        this.currentState.handlePhysicalAttackInput(this);
    }

    public void performEnemyTurn(){
        view.showInfo("Enemy attacks!");

        // Define what happens after the enemy's attack animation completes
        Runnable onEnemyAttackComplete = () -> {

            this.currentState.handleAnimationComplete(this);
        };

         // Simple AI: Enemy always uses physical attack
        animatePhysicalMove(
            model.getEnemyPosition(),
            model.getPlayerPosition(),
            false, // isPlayerAttacker = false
            model.getEnemyPower(),
            onEnemyAttackComplete
        );
    }


    // --- Animation Logic ---

    private void stopAnimationTimer() {
        System.out.println("\n\n\stoppato\n\n\n");
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            animationTimer = null; // Release reference
        }
    }
    
    private void animatePhysicalMove( final Position attackerStartPos, final Position targetStartPos, final boolean isPlayerAttacker, final int attackPower, final Runnable onComplete){

        stopAnimationTimer(); // Ensure no other animation is running

        final int moveDirection = isPlayerAttacker ? 1 : -1;
        final int returnDirection = -moveDirection;
        final int meleeCheckDistance = 1; // Distance for neighbor check

        // Use arrays to hold mutable positions within the lambda
        final Position[] currentAttackerPos = { new Position(attackerStartPos.x(), attackerStartPos.y()) };
        final Position[] currentTargetPos = { new Position(targetStartPos.x(), targetStartPos.y()) };

        // State machine for animation steps
        final int[] state = {0}; // 0: Moving forward, 1: Attack & Start Return, 2: Returning
        final boolean[] damageApplied = {false};

        // Initial placement before animation starts
        if (isPlayerAttacker) {
            model.setPlayerPosition(currentAttackerPos[0]);
            model.setEnemyPosition(currentTargetPos[0]);
        } else {
            model.setPlayerPosition(currentTargetPos[0]); // Target is player
            model.setEnemyPosition(currentAttackerPos[0]); // Attacker is enemy
        }
       // redrawView(); // No immediate redraw needed, timer will handle steps

        animationTimer = new Timer(ANIMATION_DELAY, null);
        
        animationTimer.addActionListener(event -> {

            Position nextAttackerPos = currentAttackerPos[0];
            Position nextTargetPos = currentTargetPos[0];

            if (!(currentState instanceof AnimatingState)) {
                // State changed unexpectedly, stop animation
                stopAnimationTimer(); // Make sure this method exists and stops/nulls timer
                System.out.println("Animation timer stopped: State is no longer AnimatingState.");
                return;
            }

            // --- State Logic ---
            if (state[0] == 0) { // Moving forward
                meleeCommand.setAttributes(currentAttackerPos[0], currentTargetPos[0], moveDirection, meleeCheckDistance);
                System.out.println("Attacker => " + currentAttackerPos[0] + "Target => " + currentTargetPos[0]);
                List<Position> result = meleeCommand.execute(); // Get next positions
                nextAttackerPos = result.get(0);
                nextTargetPos = result.get(1); // Target might be pushed back
                
                // Check if contact made or target moved
                
                if (meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance) || !nextTargetPos.equals(currentTargetPos[0])) {
                    state[0] = 1; // Contact or push occurred, move to attack state
                } 
                
                else if (!nextAttackerPos.equals(currentAttackerPos[0])) {
                    // Attacker moved forward, continue state 0
                } 
                
                else {
                    // No movement, assume contact if close enough (failsafe)
                    if (meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance + 1)) { // Check slightly wider range
                       state[0] = 1;
                    } else {
                        // Stuck? Force state change or stop? For now, assume contact.
                        System.err.println("Animation stuck in state 0? Forcing state 1.");
                        state[0] = 1;
                    }
                }
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;

            } else if (state[0] == 1) { // Apply damage and start returning
                if (!damageApplied[0]) {
                    if (isPlayerAttacker) {
                        model.decreaseEnemyHealth(attackPower);
                        view.updateEnemyHealth(model.getEnemyHealth());
                    } 
                    else {
                        model.decreasePlayerHealth(attackPower);
                        view.updatePlayerHealth(model.getPlayerHealth());
                    }
                    damageApplied[0] = true;
                    // Check for game over immediately after damage
                }
                // Move both attacker and target back one step to initiate return
                nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());
                nextTargetPos = new Position(currentTargetPos[0].x() + returnDirection, currentTargetPos[0].y()); // Target also moves back
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;
                state[0] = 2; // Move to returning state

            } else { // State 2: Returning to start
                if (currentAttackerPos[0].x() == attackerStartPos.x()) { // Attacker reached start X
                    stopAnimationTimer();

                    // Ensure final positions are exactly the start/end positions
                    currentAttackerPos[0] = attackerStartPos;
                    // Target position might have changed if pushed back
                    // currentTargetPos[0] remains as it was after the push back step

                     // Update model with final positions AFTER animation
                    if (isPlayerAttacker) {
                        model.setPlayerPosition(currentAttackerPos[0]);
                        model.setEnemyPosition(currentTargetPos[0]);
                        this.getModel().setPlayerTurn(!isPlayerAttacker);    
                    } else {
                        model.setPlayerPosition(currentTargetPos[0]); // Player is target
                        model.setEnemyPosition(currentAttackerPos[0]); // Enemy is attacker
                        this.getModel().setPlayerTurn(!isPlayerAttacker);
                    }
                    redrawView(); // Final redraw at resting positions

                    // Execute the completion action (e.g., start enemy turn)
                    if (onComplete != null) {
                        onComplete.run();
                    }
                    return; // Exit timer listener

                } else { // Continue moving back
                    nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());
                     // Target stays put during return phase after initial step back
                    currentAttackerPos[0] = nextAttackerPos;
                    System.out.println("\n\nTARGET POS => " + currentTargetPos[0]);
                }
            }

            // --- Update Model & View during animation step ---
            if (isPlayerAttacker) {
                model.setPlayerPosition(currentAttackerPos[0]);
                model.setEnemyPosition(currentTargetPos[0]);
            } else {
                model.setPlayerPosition(currentTargetPos[0]); // Player is target
                model.setEnemyPosition(currentAttackerPos[0]); // Enemy is attacker
            }
            // Redraw based on current animation positions
            redrawView(); // Use standard redraw

        });
        animationTimer.setInitialDelay(150);
        animationTimer.start();
        System.out.println("\n\n\niniziato\n\n\n");
    }

    private void longRangeAttackAnimation(boolean isPoison, Runnable onHit) {
        stopAnimationTimer(); // Ensure no other animation is running

        model.setFlamePosition(model.getPlayerPosition()); // Start flame at player

        animationTimer = new Timer(ANIMATION_DELAY, e -> {
            // Check if flame reached or passed the enemy
            if (model.getFlamePosition().x() >= model.getEnemyPosition().x() -1) { // -1 to hit when adjacent
                stopAnimationTimer();
                // Reset flame position visually (optional, could just hide it)
                model.setFlamePosition(model.getPlayerPosition()); // Move flame back instantly
                
                this.model.decreaseEnemyHealth(model.getPlayerPower());
                model.setEnemyPoisoned(this.model.isEnemyPoisoned() || isPoison);
                System.out.println(this.model.isEnemyPoisoned());
                System.out.println(isPoison);
                // this.view.updateEnemyHealth(model.getEnemyHealth() - model.getPlayerPower());
                // model.decreaseEnemyHealth(model.getPlayerPower());
                redrawView(false, false, true, 1, 1); // Redraw without flame/poison visible
                if (onHit != null) {
                   onHit.run(); // Execute the action upon hitting
                }
                
                return;
            }

            // Move flame forward
            longRangeCommand.setAttributes(model.getFlamePosition());
            Position nextFlamePos = longRangeCommand.execute().get(0);
            model.setFlamePosition(nextFlamePos);

            // Redraw showing the flame/poison projectile
            redrawView( !isPoison, isPoison, true, 1, 1); // Draw flame OR poison

        });
        animationTimer.start();
    }


    // Refactored Zoomer - takes a callback for when ZOOM-IN finishes
    public void performInfoZoomInAnimation(Runnable onZoomComplete) { // Renamed for clarity
        System.out.println("Performing Info Zoom-In Animation...");
        stopAnimationTimer(); // Stop any other animations
        view.setButtonsEnabled(false); // Disable buttons during animation

        final int targetX = model.getSize() / 2;

        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            Position currentEnemyPos = model.getEnemyPosition(); // Get current pos

            if (currentEnemyPos.x() <= targetX) {
                // --- Zoom-in Complete ---
                stopAnimationTimer();
                // Ensure exact position at center
                model.setEnemyPosition(new Position(targetX, currentEnemyPos.y()));
                // Redraw centered enemy (adjust redraw parameters as needed for zoomed view)

                this.getModel().setEnemyPosition(new Position(targetX, currentEnemyPos.y()));
                
                this.makeBigger(5, onZoomComplete);

                // *** CRUCIAL: Execute the completion callback ***
                // DO NOT automatically start infoNextDrawAnimation here anymore
            } else {
                // --- Still Zooming In ---
                model.setEnemyPosition(new Position(currentEnemyPos.x() - 1, currentEnemyPos.y()));
                // Redraw with enemy moving (adjust redraw params if needed)
                redrawView(false, false, false, 1, 1);
            }
        });
        // zoomerStep is not needed for this part anymore
        animationTimer.start();
    }

    private void makeBigger(int size, Runnable onZoomComplete){
        final int conto[] = {1};
        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            if (conto[0] > size){
                stopAnimationTimer();
                conto[0] = 0;
                if (onZoomComplete != null) {
                    System.out.println("Zoom-in complete, executing callback.");
                    onZoomComplete.run(); // This will trigger setState(InfoDisplayState)
                }
            }
            else{
                redrawView(false, false, false, 1, conto[0]);
                conto[0]++;
            }
        });
        animationTimer.start();
    }


    // This method is CALLED BY PlayerTurnState
    public void performInfoAnimation() {
        System.out.println("Controller: performInfoAnimation called.");
        // Start the zoom-in animation, providing the specific callback
        // to transition to InfoDisplayState when the zoom completes.
        performInfoZoomInAnimation(() -> {
            // This lambda is the onZoomComplete callback
            System.out.println("Callback: Setting state to InfoDisplayState.");
            this.setStates(new InfoDisplayState()); // Transition state AFTER zoom finishes
        });
        // Display text info immediately while animation runs
        view.showInfo("Enemy Info:\nName: " + model.getEnemyName() + "\nPower: " + model.getEnemyPower());
    }

    public boolean checkGameOver() {
        if (model.isGameOver()) {
            stopAnimationTimer(); // Stop any ongoing animations
            view.setButtonsEnabled(false); // Disable all buttons
            String winner = model.getPlayerHealth() <= 0 ? "Enemy" : "Player";
            view.showInfo("Game Over! " + winner + " wins!");
            return true;
        }
        return false;
    }

    public void performDeathAnimation(Position death, Runnable onComplete){
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), death, true, true, false, false, 1, 2, true, death);
        if (onComplete != null){
            onComplete.run();
        }
    }

    // GETTER METHODS

    public CombatModel getModel(){
        return this.model;
    }

    public CombatView getView(){
        return this.view;
    }

    // SETTER METHODS

    public void setStates(CombatState newState){
        this.currentState.exitState(this);
        this.currentState = newState;
        this.currentState.enterState(this);
    }

}