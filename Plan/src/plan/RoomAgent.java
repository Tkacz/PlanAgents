/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author rufus
 */
public class RoomAgent extends Agent {
    
    private int id;
    private Room room;
    private String plan[][];//zawiera nazwy (id) agentów, którzy zajmują plan
    private ACLMessage actualMsg;
    private boolean isCollision;
    
    @Override
    protected void setup() {
        plan = new String[5][5];//5 dni po 5 jednostek czasowych (2 godziny każda), zawiera nazwę grupy
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                plan[i][j] = null;
            }
        }
        isCollision = false;
        
        id = Integer.parseInt(getAID().getLocalName().substring(4));
        RMySQL sql = new RMySQL();
        room = sql.getRoomData(id);
        
        if(room != null) {
            System.out.println("Hello, I'm agent " + getAID().getLocalName() + " : " + room.toString());// + " : " + room.toString());
        } else {
            System.out.println("room == null");
        }
        
        takeDown();
    }
    
    @Override
    protected void takeDown() {
        super.takeDown();
    }

    public int getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public String[][] getPlan() {
        return plan;
    }

    public void setActualMsg(ACLMessage actualMsg) {
        this.actualMsg = actualMsg;
    }

    public ACLMessage getActualMsg() {
        return actualMsg;
    }

    public void setIsCollision(boolean isCollision) {
        this.isCollision = isCollision;
    }
    
    public boolean getIsCollision() {
        return this.isCollision;
    }
    
    public void addGroupToPlan(String agentName, int time, int day) {
        this.plan[time][day] = agentName;
    }
}
