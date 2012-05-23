/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rufus
 */
public class GroupAgent extends Agent {
    
    private int id;
    private Group group = null;
    private ArrayList<Room> rooms;
        
    @Override
    protected void setup() {
        
        id = Integer.parseInt(getAID().getLocalName().substring(5));
        
        RMySQL sql = new RMySQL();        
        group = sql.getGroupData(id);
        rooms = sql.getAllRoomsData();
        
        if(group != null) {
            System.out.println("Hello, I'm agent " + getAID().getLocalName() + " : " + group.toString());
            if(rooms != null) {
                
            } else {
                System.out.println("agent: " + getAID().getLocalName() + ": rooms == null!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            finish();
        } else {
            System.out.println("agent: " + getAID().getLocalName() + ": group == null!!!!!!!!!!!!!!!!");
        }
    }
    
    @Override
    protected void takeDown() {
        super.takeDown();
    }
    
    private void finish() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Master", AID.ISLOCALNAME));
        send(msg);
    }
}
