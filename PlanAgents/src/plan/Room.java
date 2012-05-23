/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

/**
 *
 * @author rufus
 */
public class Room extends Object {
    private final int id;
    private final int nr;
    private final int capacity;
    private final boolean komps;
    private final boolean projr;
    private final boolean board;
    
    public Room(int id, int nr, int capacity, boolean komps, boolean projr, boolean board) {
        this.id = id;
        this.nr = nr;
        this.capacity = capacity;
        this.komps = komps;
        this.projr = projr;
        this.board = board;
    }

    Room(Room room) {
        this.id = room.getId();
        this.nr= room.getNr();
        this.capacity = room.getCapacity();
        this.komps = room.isKomps();
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

    public boolean isKomps() {
        return komps;
    }

    public boolean isProjr() {
        return projr;
    }
    
    @Override
    public String toString() {
        return Integer.toString(id) + " : " + Integer.toString(nr) + " : " + Integer.toString(capacity);
    }
}
