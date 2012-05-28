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
public class Teacher implements Serializable {
    
    private final int id;
    private final int days[];
    
    public Teacher(Teacher t) {
        this.id = t.getId();
        this.days = t.getDays();
    }
    
    public Teacher(int id, int pn, int wt, int sr, int cw, int pt) {
        this.id = id;
        this.days = new int[5];
        days[0] = pn;
        days[1] = wt;
        days[2] = sr;
        days[3] = cw;
        days[4] = pt;
    }

    public int getId() {
        return id;
    }

    public int[] getDays() {
        return days;
    }
    
    public int getDay(int index) {
        return days[index];
    }
}
