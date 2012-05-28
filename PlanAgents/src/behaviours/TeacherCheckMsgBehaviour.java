/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import plan.TeacherAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class TeacherCheckMsgBehaviour extends CyclicBehaviour {
    
    private String plan[][];//5x5 zawiera nazwe grupy
    private int days[];
    
    public TeacherCheckMsgBehaviour(TeacherAgent a) {
        super(a);
        plan = a.getPlan();
        days = a.getDays();
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            ACLMessage reply;
            String content[] = msg.getContent().split("-");
            int day = Integer.parseInt(content[0]);
            int time = Integer.parseInt(content[1]);
            String group = content[3];
            
            if(msg.getPerformative() == ACLMessage.QUERY_IF) {//question
                reply = msg.createReply();//odpowiedź
                reply.setContent(msg.getContent());
                if (plan[day][time] == null) {//jeśli wolne miejsce
                    plan[day][time] = group;//zaklepujemy
                    reply.setPerformative(ACLMessage.AGREE);//potwierdzenie
                } else if (days[day] == 5) {//jeśli 5 to nie możemy
                    reply.setPerformative(ACLMessage.REFUSE);//odpowiedź negatywna
                } else {//jeśli zajęte
                    reply.setPerformative(ACLMessage.REFUSE);//odpowiedź negatywna
                }
                myAgent.send(reply);//opdowiedź
                
            } else if(msg.getPerformative() == ACLMessage.CANCEL) {//remove
                plan[day][time] = null;//zwalnianie miejscówki
            }
        } else {
            block();
        }
    }
}
