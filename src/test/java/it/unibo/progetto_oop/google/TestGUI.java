package it.unibo.progetto_oop.google;


import javax.swing.SwingUtilities;

public class TestGUI {

    public static void main(String[] args) {
        // Configuration
        int gridSize = 13; // Example size
        int playerPower = 10;
        int playerPoisonPower = 2;
        int enemyPower = 8; // Example enemy stats
        int enemySpeed = 5;
        String enemyName = "Goblin";

        // Ensure UI creation happens on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // 1. Create Model
            CombatModel model = new CombatModel(gridSize, playerPower, playerPoisonPower, enemyPower, enemySpeed, enemyName);

            // 2. Create View
            CombatView view = new CombatView(model.getSize());

            // 3. Create Controller and link Model & View
            CombatController controller = new CombatController(model, view);

            // 4. Start the combat UI
            controller.startCombat();
        });
    }
}