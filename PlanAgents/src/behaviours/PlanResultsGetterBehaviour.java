/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import plan.PlanAgent;
import plan.Result;

/**
 *
 * @author Rafał Tkaczyk
 */
public class PlanResultsGetterBehaviour extends CyclicBehaviour {
    
    private PlanAgent planAgent;
    
    public PlanResultsGetterBehaviour(PlanAgent a) {
        super(a);
        this.planAgent = a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF));
        if(msg != null) {
            String data[] = msg.getContent().split("-");
            planAgent.addToResults(new Result(data[3], Integer.parseInt(data[0]), 
                    Integer.parseInt(data[1]), Integer.parseInt(data[2])));
        } else {
            block();
        }
    }
}
