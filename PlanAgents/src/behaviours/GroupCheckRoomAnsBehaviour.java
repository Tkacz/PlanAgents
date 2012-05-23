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
public class GroupCheckRoomAnsBehaviour extends CyclicBehaviour {

    private GroupAgent groupAgent;
    private final MessageTemplate mt;
    
    public GroupCheckRoomAnsBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
        mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.AGREE), 
                MessageTemplate.MatchPerformative(ACLMessage.REFUSE));//odbiera tylko zgodę lub odmowę
    }
    
    @Override
    public void action() {
        
        if (groupAgent.getIsTeacherAgreement()) {//czeka na wiadomość tylko jeśli wykładowca się zgodził
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                if (msg.getPerformative() == ACLMessage.AGREE) {
                    System.out.println("Odpowidź od sali pozytywna");/////////////////////////////////////////////
                    groupAgent.setIsFoundPlace(true);
                    groupAgent.finish();//powiadomienie Mastera o zakończeniu poszukiwań
                    System.out.println("zakończono poszukiwania");/////////////////////////////////////////////
                } else {//zwalnianie miejsce, które zaklepał wykładowca
                    System.out.println("wiadomość od sali negatywna");/////////////////////////////////////////////
                    ACLMessage msgToTeacher = new ACLMessage(ACLMessage.CANCEL);
                    msgToTeacher.addReceiver(new AID("Teacher" + Integer.toString(groupAgent.getGroup().getTeacher()), AID.ISLOCALNAME));
                    msg.setContent(groupAgent.getFoundPlace());//wypełnienie wiadomości treścią
                    myAgent.send(msgToTeacher);//wysałnie wiadomości
                    System.out.println("wysłanie rozkazu zwolnienia do wykładowcy i kontynuacja poszukiwań");/////////////////////////////////////////////
                }
                groupAgent.setIsTeacherAgreement(false);
            } else {
                block();
            }
        }
    }
    
}
