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
public class Group implements Serializable {
    private final String symbol;//symbol grupy
    private final int number;//ilość osób w grupie
    private final int priority;//priorytet 1-5 (1 najważniejszy)
    private final boolean comps;//czy potrzebne komputery
    private final boolean projr;//czy potrzebny projektor
    private final boolean board;//czy potrzebna tablica interaktywna
    private final int teacher;//id wykladowcy
    private final int[] daysPriority;//priorytet dni, od 1-5 (1 najważniejszy)
    
    public Group(String symbol, int number, int priority, boolean comps, boolean projr, boolean board, int teacher, int pn, int wt, int sr, int cz, int pt) {
        this.symbol = symbol;
        this.number = number;
        this.priority = priority;
        this.comps = comps;
        this.projr = projr;
        this.board = board;
        this.teacher = teacher;
        this.daysPriority = new int[5];
        daysPriority[0] = pn;
        daysPriority[1] = wt;
        daysPriority[2] = sr;
        daysPriority[3] = cz;
        daysPriority[4] = pt;
    }

    Group(Group group) {
        this.symbol = group.getSymbol();
        this.number = group.getNumber();
        this.priority = group.getPriority();
        this.comps = group.isComps();
        this.projr = group.isProjr();
        this.board = group.isBoard();
        this.teacher = group.getTeacher();
        this.daysPriority = new int[5];
        daysPriority[0] = group.getDayPriority(0);
        daysPriority[1] = group.getDayPriority(1);
        daysPriority[2] = group.getDayPriority(2);
        daysPriority[3] = group.getDayPriority(3);
        daysPriority[4] = group.getDayPriority(4);
    }

    public int getNumber() {
        return number;
    }

    public int getPriority() {
        return priority;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTeacher() {
        return teacher;
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
    
    public int getDayPriority(int pos) {
        if(pos >= 0 && pos < 5){
            return daysPriority[pos];
        } else {
            return -1;
        }
    }
    
    @Override
    public String toString() {
        return symbol + " : " + Integer.toString(number) + " : " + Integer.toString(priority);
    }
}
