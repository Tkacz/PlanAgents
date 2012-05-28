/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import plan.Group;
import plan.GroupAgent;
import plan.Room;

/**
 *
 * @author Rafał Tkaczyk
 */
public class GroupDataWaiterBehaviour extends CyclicBehaviour {

    private GroupAgent groupAgent;
    private MessageTemplate mt;

    public GroupDataWaiterBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
        mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF), 
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
    }

    @Override
    public void action() {
        //ACLMessage msg = myAgent.receive(mt);
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            try {
                if(msg.getPerformative() == ACLMessage.INFORM_REF) {
                    Group data = (Group) msg.getContentObject();
                    groupAgent.setGroup(data);
                    
                    ACLMessage askForRooms = new ACLMessage(ACLMessage.QUERY_REF);
                    askForRooms.addReceiver(new AID("Master", AID.ISLOCALNAME));
                    askForRooms.setContent("GroupRoomsData");
                    myAgent.send(askForRooms);
                } else if(msg.getPerformative() == ACLMessage.INFORM) {
                    ArrayList<Room> data = (ArrayList<Room>) msg.getContentObject();
                    groupAgent.setRooms(data);
                    groupAgent.setIsFoundPlace(false);
                    
                    myAgent.addBehaviour(new GroupCheckMsgBehaviour(groupAgent));
                    myAgent.addBehaviour(new GroupFinderBehaviour(groupAgent));
                    myAgent.removeBehaviour(this);
                }
            } catch (UnreadableException ex) {
                System.out.println(ex.toString());
            }
        } else {
            block();
        }
    }
}
