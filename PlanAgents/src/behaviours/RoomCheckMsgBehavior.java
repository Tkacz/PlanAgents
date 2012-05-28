/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import plan.RoomAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class RoomCheckMsgBehavior extends CyclicBehaviour {
    
    private RoomAgent roomAgent;
    
    public RoomCheckMsgBehavior(RoomAgent a) {
        super(a);
        this.roomAgent = a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if(msg != null) {
            if(msg.getPerformative() == ACLMessage.QUERY_IF) {
                checkPlace(msg);
            } else if(msg.getPerformative() == ACLMessage.INFORM_REF) {
                roomNegotiation(msg);
            }
        } else {
            block();
        }
    }
    
    private void checkPlace(ACLMessage msg) {
        String content[] = msg.getContent().split("-");//odebranie treści wiadomości
        int day = Integer.parseInt(content[0]);//odczyt dnia
        int time = Integer.parseInt(content[1]);//odczyt id jednostki czasowej
        String plan[][] = roomAgent.getPlan();
        if (plan[day][time] == null) {//nie ma kolizji
            roomAgent.addGroupToPlan(content[3], day, time);
            ACLMessage reply = msg.createReply();//szykowanie odpowiedzi
            reply.setPerformative(ACLMessage.AGREE);//zgoda na zajęcie terminu
            reply.setContent(msg.getContent());
            myAgent.send(reply);//wysłanie odpowiedzi
        } else {//kolizja - negocjacja
            ACLMessage ask = new ACLMessage(ACLMessage.QUERY_REF);//przygotowanie zapytania o ocenę do oponenta 
            ask.addReceiver(new AID(plan[day][time], AID.ISLOCALNAME));//adresowanie wiadomości
            ask.setContent(msg.getContent());//treść wiadomości
            myAgent.send(ask);//wysłanie zapytania
        }
    }
    
    private void roomNegotiation(ACLMessage msg) {
        String data[] = msg.getContent().split("-");
        int day = Integer.parseInt(data[0]);
        int time = Integer.parseInt(data[1]);
        int groupRating = Integer.parseInt(data[4]);
        int oponentRating = Integer.parseInt(data[5]);
        
        if (groupRating < oponentRating) {//wygrywa grupa
            ACLMessage replyToOponent = msg.createReply();
            replyToOponent.setPerformative(ACLMessage.CANCEL);
            replyToOponent.setContent(msg.getContent());
            myAgent.send(replyToOponent);
            roomAgent.addGroupToPlan(data[3], day, time);
            
            
            ACLMessage replyToGroup = new ACLMessage(ACLMessage.AGREE);
            replyToGroup.addReceiver(new AID(data[3], AID.ISLOCALNAME));
            replyToGroup.setContent(msg.getContent());
            myAgent.send(replyToGroup);
        } else {//wygrywa oponent                    
            ACLMessage replyToGroup = new ACLMessage(ACLMessage.REFUSE);
            replyToGroup.addReceiver(new AID(data[3], AID.ISLOCALNAME));
            replyToGroup.setContent(msg.getContent());
            myAgent.send(replyToGroup);
        }
    }
}
