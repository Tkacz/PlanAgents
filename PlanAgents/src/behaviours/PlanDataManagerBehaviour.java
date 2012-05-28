/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.IOException;
import plan.PlanAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class PlanDataManagerBehaviour extends CyclicBehaviour {
    
    private PlanAgent planAgent;
    
    public PlanDataManagerBehaviour(PlanAgent a) {
        super(a);
        this.planAgent = a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.QUERY_REF));
        if(msg != null) {
            try {
                String content = msg.getContent();
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM_REF);                
                if (content.equals("Room")) {//zapytanie o dane sali
                    int id = Integer.parseInt(msg.getSender().getLocalName().substring(4));
                    reply.setContentObject(planAgent.getRoomData(id));
                } else if(content.equals("Teacher")) {//zapytanie o dane wykładowcy
                    int id = Integer.parseInt(msg.getSender().getLocalName().substring(7));
                    reply.setContentObject(planAgent.getTeacherData(id));
                } else if(content.equals("GroupData")) {//zapytanie o dane grupy
                    reply.setContentObject(planAgent.getGroupData(msg.getSender().getLocalName().substring(5)));
                } else if(content.equals("GroupRoomsData")) {//zapytanie o dane grupy
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContentObject(planAgent.getRooms());
                }
                myAgent.send(reply);
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } else {
            block();
        }
    }
}
