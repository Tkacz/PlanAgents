/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import plan.RoomAgent;

/**
 *
 * @author rufus
 */
public class RoomNegotiationBehavoiur extends CyclicBehaviour{

    RoomAgent roomAgent;
    private final MessageTemplate mt;
    
    public RoomNegotiationBehavoiur(RoomAgent a) {
        super(a);
        this.roomAgent = a;
        mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);//zbiera odpowiedzi o ocenie
    }
    
    @Override
    public void action() {
        
        if(roomAgent.getIsCollision()) {//gdy kolizja
            ACLMessage msg = myAgent.receive(mt);
            if(msg != null) {//czekam na odpowiedź oponenta
                int group = Integer.parseInt(roomAgent.getActualMsg().getContent().substring(4));
                int oponent = Integer.parseInt(msg.getContent());
                ACLMessage replyToGroup = roomAgent.getActualMsg().createReply();
                if(group < oponent) {//wygrywa grupa
                    ACLMessage replyToOponent = msg.createReply();
                    replyToOponent.setPerformative(ACLMessage.CANCEL);
                    int day = roomAgent.getActualMsg().getContent().charAt(1) - 48;//odczyt dnia
                    int time = roomAgent.getActualMsg().getContent().charAt(3) - 48;//odczyt id jednostki czasowej
                    roomAgent.addGroupToPlan(roomAgent.getActualMsg().getSender().getLocalName(), time, day);
                    myAgent.send(replyToGroup);
                    myAgent.send(replyToOponent);
                    roomAgent.setIsCollision(false);
                } else {//wygrywa oponent                    
                    replyToGroup.setPerformative(ACLMessage.REFUSE);
                    myAgent.send(replyToGroup);
                    roomAgent.setIsCollision(false);
                }
            } else {
                block();
            }
        }
    }   
}
