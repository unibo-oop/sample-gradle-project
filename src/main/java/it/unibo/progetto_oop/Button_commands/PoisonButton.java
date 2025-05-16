package it.unibo.progetto_oop.Button_commands;

import java.util.List;

import progetto_oop.combattimento.Position;

public class PoisonButton implements GameButton {
    @Override
    public List<Position> execute() {
        return List.of(new Position(0, 0));
    }
}