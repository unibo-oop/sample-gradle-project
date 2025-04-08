package it.unibo.progetto_oop.Button_commands;

import java.util.LinkedList;
import java.util.List;

import progetto_oop.combattimento.Position;

public class MeleeButton implements GameButton{
    private List<Position> giocatori = new LinkedList<>();
    Position player;
    Position enemy;
    int where;
    int distance;

    public void setAttributes(Position player, Position enemy, int where, int distance){
        this.player = player;
        this.enemy = enemy;
        this.where = where;
        this.distance = distance;

    }

    @Override
    public List<Position> execute(){

        System.out.println( "PER NEIGHBOURS => " + (this.player.x() + this.where) + "\n" + this.distance);

        if (this.neighbours(new Position(this.player.x() + this.where, this.player.y()), this.enemy , 1)){

            this.giocatori = this.moveEnemy();
            return this.giocatori;
        }
        
        else{
            this.player = new Position(this.player.x() + this.where, this.player.y());
        }

        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        return this.giocatori;
    }

    public List<Position> moveEnemy(){
        
        this.enemy = new Position(this.enemy.x() + this.where, this.enemy.y());
        this.player = new Position(this.player.x() + this.where, this.player.y());
        
        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        
        return this.giocatori;
    }

    public boolean neighbours(Position player, Position other, int distance) {
        return Math.abs(player.x() - other.x()) <= distance && Math.abs(player.y() - other.y()) <= distance;
    }

    public boolean deathNeighbours(Position player, Position other, int distance){
        return 
        (Math.abs(player.x() - other.x()) == distance && Math.abs(player.y() - other.y()) == distance) || 
        (Math.abs(player.x() - other.x()) == distance && player.y() == other.y()) ||
        (player.x() == other.x() && Math.abs(player.y() - other.y()) == distance) ||
        (player.x() == other.x() && other.y() == player.y());
    }
}
