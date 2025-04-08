package it.unibo.progetto_oop.Button_commands;


import java.util.List;

import progetto_oop.combattimento.Position;


public class LongRangeButton implements GameButton {
    
    private Position flame;

    public void setAttributes(Position fiamma){
        this.flame = fiamma;
    }

    @Override
    public List<Position> execute(){
        this.flame = new Position(this.flame.x() + 1, this.flame.y());
        return List.of(this.flame);
    }   
}