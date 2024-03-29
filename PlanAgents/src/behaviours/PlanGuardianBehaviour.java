/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import plan.PlanAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class PlanGuardianBehaviour extends CyclicBehaviour {
    
    private PlanAgent planAgent;
    
    public PlanGuardianBehaviour(PlanAgent a) {
        super(a);
        this.planAgent = a;
    }
    
    @Override
    public void action() {
        
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if(msg != null) {
            if(msg.getContent().equals("end")) {
                planAgent.addToGroups(msg.getSender().getLocalName());
            } else {
                planAgent.removeFromGroups(msg.getSender().getLocalName());
            }
        } else {
            block();
        }
    }
    
}
