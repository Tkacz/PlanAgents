/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import plan.PlanAgent;

/**
 *
 * @author rufus
 */
public class PlanGuardianBehaviour extends CyclicBehaviour {
    
    private PlanAgent planAgent;
    
    public PlanGuardianBehaviour(PlanAgent a) {
        super(a);
        this.planAgent = a;
    }
    
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if(msg != null) {
            if(msg.getPerformative() == ACLMessage.INFORM) {
                planAgent.addToGroups(msg.getSender().getLocalName());
            } else if(msg.getPerformative() == ACLMessage.CANCEL) {
                planAgent.removeFromGroups(msg.getSender().getLocalName());
            }
        } else {
            block();
        }
    }
    
}
