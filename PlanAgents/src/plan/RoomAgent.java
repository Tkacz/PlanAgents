/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;


import behaviours.RoomDataWaiterBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 *
 * @author Rafał Tkaczyk
 */
public class RoomAgent extends Agent {
    
    private Room room;
    private String plan[][];//zawiera nazwy (id) agentów, którzy zajmują plan
    private Properties properties;
    private int DAY;
    private int TIME;
    
    @Override
    protected void setup() {

        try {
            FileInputStream in = new FileInputStream("src/plan/plan.properties");
            properties = new Properties();
            properties.load(in);
            in.close();
            DAY = Integer.parseInt(properties.getProperty("days"));
            TIME = Integer.parseInt(properties.getProperty("times"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        plan = new String[DAY][TIME];//5 dni po 5 jednostek czasowych (2 godziny każda), zawiera nazwę grupy
        for(int i = 0; i < DAY; i++) {
            for(int j = 0; j < TIME; j++) {
                plan[i][j] = null;
            }
        }
        
        addBehaviour(new RoomDataWaiterBehaviour(this));
        
        ACLMessage msg = new ACLMessage(ACLMessage.QUERY_REF);
        msg.addReceiver(new AID("Master", AID.ISLOCALNAME));
        msg.setContent("Room");
        send(msg);
        
    }
    
    @Override
    protected void takeDown() {
        super.takeDown();
    }

    public int getId() {
        return room.getId();
    }

    public Room getRoom() {
        return room;
    }

    public String[][] getPlan() {
        return plan;
    }
    
    public void addGroupToPlan(String agentName, int day, int time) {
        this.plan[day][time] = agentName;
    }

    public void setRoom(Room room) {
        this.room = new Room(room);
    }
}
