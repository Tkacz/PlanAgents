/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import jade.util.leap.Serializable;

/**
 *
 * @author Rafał Tkaczyk
 */
public class Room implements Serializable {
    private final int id;
    private final int nr;
    private final int capacity;
    private final boolean comps;
    private final boolean projr;
    private final boolean board;
    
    public Room(int id, int nr, int capacity, boolean comps, boolean projr, boolean board) {
        this.id = id;
        this.nr = nr;
        this.capacity = capacity;
        this.comps = comps;
        this.projr = projr;
        this.board = board;
    }

    Room(Room room) {
        this.id = room.getId();
        this.nr= room.getNr();
        this.capacity = room.getCapacity();
        this.comps = room.isComps();
        this.projr = room.isProjr();
        this.board = room.isBoard();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }

    public int getNr() {
        return nr;
    }

    public boolean isBoard() {
        return board;
    }

    public boolean isComps() {
        return comps;
    }

    public boolean isProjr() {
        return projr;
    }
    
    @Override
    public String toString() {
        return Integer.toString(id) + " : " + Integer.toString(nr) + " : " + Integer.toString(capacity);
    }
}
