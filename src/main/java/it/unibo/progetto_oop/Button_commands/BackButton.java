package it.unibo.progetto_oop.Button_commands;

import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;

public class BackButton implements GameButton {
    @Override
    public List<Position> execute() {
        return List.of(new Position(0, 0));
    }
}
