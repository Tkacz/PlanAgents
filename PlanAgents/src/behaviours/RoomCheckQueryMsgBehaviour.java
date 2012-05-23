/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import plan.RoomAgent;

/**
 *
 * @author rufus
 */
public class RoomCheckQueryMsgBehaviour extends CyclicBehaviour {//czeka na zapytanie od Grupy

    private RoomAgent roomAgent;
    private final MessageTemplate mt;
    
    public RoomCheckQueryMsgBehaviour(RoomAgent a) {
        super(a);
        this.roomAgent = a;
        mt = MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF);//odbiera tylko zapytania
    }
    
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if(msg != null) {
            roomAgent.setActualMsg(msg);//zapamiętanie wiadomości
            String content = msg.getContent();//odebranie treści wiadomości
            int day = content.charAt(1) - 48;//odczyt dnia
            int time = content.charAt(3) - 48;//odczyt id jednostki czasowej
            String plan[][] = roomAgent.getPlan();
            if(plan[time][day] == null) {//nie ma kolizji
                roomAgent.setIsCollision(false);//ustawienie flagi, oznaczające, że brak kolizji
                roomAgent.addGroupToPlan(msg.getSender().getLocalName(), time, day);
                ACLMessage reply = msg.createReply();//szykowanie odpowiedzi
                reply.setPerformative(ACLMessage.AGREE);//zgoda na zajęcie terminu
                myAgent.send(reply);//wysłanie odpowiedzi
            } else {//kolizja - negocjacja
                ACLMessage ask = new ACLMessage(ACLMessage.QUERY_REF);//przygotowanie zapytania o ocenę do oponenta 
                ask.addReceiver(new AID(plan[time][day], AID.ISLOCALNAME));//adresowanie wiadomości
                ask.setContent("d" + Integer.toString(day) + "t" + Integer.toString(time));//treść wiadomości
                roomAgent.setIsCollision(true);//oznaczenie flagi kolizji
                myAgent.send(ask);//wysłanie zapytania
            }
        } else {
            block();
        }
    }
}
