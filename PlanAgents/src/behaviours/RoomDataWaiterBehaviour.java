/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import plan.Room;
import plan.RoomAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class RoomDataWaiterBehaviour extends CyclicBehaviour {
    
    private RoomAgent roomAgent;
    
    public RoomDataWaiterBehaviour(RoomAgent a) {
        super(a);
        this.roomAgent = a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF));
        if(msg != null) {
            try {
                Room data = (Room) msg.getContentObject();
                roomAgent.setRoom(data);
                myAgent.addBehaviour(new RoomCheckMsgBehavior(roomAgent));
                myAgent.removeBehaviour(this);
            } catch (UnreadableException ex) {
                System.out.println(ex.toString());
            }
        } else {
            block();
        }
    }
}
