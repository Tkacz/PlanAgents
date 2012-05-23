/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import behaviours.TeacherCheckMsgBehaviour;
import jade.core.Agent;

/**
 *
 * @author rufus
 */
public class TeacherAgent extends Agent {
    
    private int id;
    private String plan[][];//5x5 zawiera nazwe grupy
    private int days[];
    
    @Override
    protected void setup() {
        plan = new String[5][5];//5 dni po 5 jednostek czasowych (2 godziny każda), zawiera nazwę grupy
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                plan[i][j] = null;
            }
        }
        
        id = Integer.parseInt(getAID().getLocalName().substring(7));
        RMySQL sql = new RMySQL();
        days = sql.getTeacherDays(id);
        
        if(days != null) {
            System.out.println("Hello, I'm agent " + getAID().getLocalName() + " : " + days[0] + " : " + days[1] + "\n");
            addBehaviour(new TeacherCheckMsgBehaviour(this));
        } else {
            System.out.println(getAID().getLocalName() + ": days[] == null\n");
            takeDown();
        }
    }
    
    @Override
    protected void takeDown() {
        super.takeDown();
    }

    public int getId() {
        return id;
    }

    public String[][] getPlan() {
        return plan;
    }

    public int[] getDays() {
        return days;
    }
}
