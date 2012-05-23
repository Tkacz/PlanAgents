/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import plan.TeacherAgent;

/**
 *
 * @author rufus
 */
public class TeacherCheckMsgBehaviour extends CyclicBehaviour {
    
    private int agentId;
    private String plan[][];//5x5 zawiera nazwe grupy
    private int days[];
    
    public TeacherCheckMsgBehaviour(TeacherAgent a) {
        super(a);
        agentId = a.getId();
        plan = a.getPlan();
        days = a.getDays();
    }

    @Override
    public void action() {
        ACLMessage msg = this.myAgent.receive();
        if (msg != null) {
            ACLMessage reply;
            
            AID sender = msg.getSender();
            String cont = msg.getContent();
            int day = cont.charAt(1)-48;
            int time = cont.charAt(3)-48;
            //System.out.println("cont: " + cont + " : " + day + " : " + time);
            
            if(msg.getPerformative() == ACLMessage.QUERY_IF) {//question
                reply = msg.createReply();//odpowiedź
                if (plan[time][day] == null) {//jeśli wolne miejsce
                    plan[time][day] = sender.getLocalName();//zaklepujemy
                    reply.setPerformative(ACLMessage.AGREE);//potwierdzenie
                } else if (days[day] == 5) {//jeśli 5 to nie możemy
                    reply.setPerformative(ACLMessage.REFUSE);//odpowiedź negatywna
                } else {//jeśli zajęte
                    reply.setPerformative(ACLMessage.REFUSE);//odpowiedź negatywna
                }
                myAgent.send(reply);//opdowiedź
            } else if(msg.getPerformative() == ACLMessage.CANCEL) {//remove
                plan[time][day] = null;//zwalnianie miejscówki
            }
        } else {
            block();
        }
    }
}
