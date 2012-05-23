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
public class GroupCheckTeacherAnsBehaviour extends CyclicBehaviour {

    private final MessageTemplate mt;
    private GroupAgent groupAgent;
    
    public GroupCheckTeacherAnsBehaviour(GroupAgent a) {
        super(a);
        groupAgent = a;
        mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.AGREE), 
                MessageTemplate.MatchPerformative(ACLMessage.REFUSE));//odbiera tylko zgodę lub odmowę
    }
    
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if(msg != null) {
            if(msg.getPerformative() == ACLMessage.AGREE) {//zgoda
                System.out.println("Odpowiedź od wykładowcy pozytywna");/////////////////////////////////////////////
                groupAgent.setIsTeacherAgreement(true);//ustawienie flagi, która wpływa na działanie natępnego zachowania
                String roomAgentName = groupAgent.getFoundRoom();
                String groupRating = Integer.toString(groupAgent.getRating(groupAgent.getFoundPlace().charAt(1)-48,
                        groupAgent.getFoundPlace().charAt(3)-48, Integer.parseInt(roomAgentName.substring(4))));
                msg = new ACLMessage(ACLMessage.QUERY_IF);//tworzenie zapytania do sali
                msg.addReceiver(new AID(roomAgentName, AID.ISLOCALNAME));//przygotowanie zapytania do agenta sali
                msg.setContent(groupAgent.getFoundPlace() + groupRating);//zapisanie miejscówki i oceny
                myAgent.send(msg);
                System.out.println("Wysłano zapytanie do sali");/////////////////////////////////////////////
            }
            System.out.println("Odp. od wykładowcy negatywna");/////////////////////////////////////////////
        } else {
            block();
        }
    }
    
}
