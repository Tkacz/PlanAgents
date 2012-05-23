/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import plan.GroupAgent;

/**
 *
 * @author rufus
 */
public class GroupWaitingForCancelBehaviour extends CyclicBehaviour {

    private GroupAgent groupAgent;
    private final MessageTemplate mt;

    public GroupWaitingForCancelBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
        mt = MessageTemplate.MatchPerformative(ACLMessage.CANCEL);
    }

    @Override
    public void action() {
        if (groupAgent.getIsFoundPlace()) {
            System.out.println("czekam");/////////////////////////////////////////////
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                groupAgent.startAgain();//powiadomienie mastera o wznowieniu poszukiwań
                ACLMessage msgToTeacher = new ACLMessage(ACLMessage.CANCEL);
                msgToTeacher.addReceiver(new AID("Teacher" + Integer.toString(groupAgent.getGroup().getTeacher()), AID.ISLOCALNAME));
                msg.setContent(groupAgent.getFoundPlace());//wypełnienie wiadomości treścią
                myAgent.send(msgToTeacher);//wysałnie wiadomości
                groupAgent.setIsFoundPlace(false);
            }
        }
    }
}
