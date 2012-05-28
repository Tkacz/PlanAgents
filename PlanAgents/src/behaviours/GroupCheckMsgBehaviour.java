/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import plan.GroupAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class GroupCheckMsgBehaviour extends CyclicBehaviour {
    
    private GroupAgent groupAgent;
    private String foundPlace;
    
    public GroupCheckMsgBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if(msg != null) {
            if(msg.getPerformative() == ACLMessage.AGREE) {
                if(msg.getSender().getLocalName().contains("Teacher")) {//zgoda agenta wykładowcy
                    teacherAgree(msg);
                    
                } else if(msg.getSender().getLocalName().contains("Room")) {//zgoda agenta sali
                    roomAgree(msg);
                }
            } else if(msg.getPerformative() == ACLMessage.REFUSE) {
                if(msg.getSender().getLocalName().contains("Teacher")) {//odmowa agenta wykładowcy
                    teacherRefuse(msg);
                } else if(msg.getSender().getLocalName().contains("Room")) {//odmowa agenta sali
                    roomRefuse(msg);
                }
            } else if(msg.getPerformative() == ACLMessage.CANCEL) {
                wakeUpFinder(msg);
            } else if(msg.getPerformative() == ACLMessage.QUERY_REF) {
                sendRating(msg);
            } else if(msg.getPerformative() == ACLMessage.REQUEST) {
                sendResult(msg);
            }
        } else {
            block();
        }
    }
    
    private void teacherAgree(ACLMessage msg) {
        String content[] = msg.getContent().split("-");
        String roomAgentName = "Room" + content[2];
        ACLMessage query = new ACLMessage(ACLMessage.QUERY_IF);
        query.addReceiver(new AID(roomAgentName, AID.ISLOCALNAME));//przygotowanie zapytania do agenta sali
        query.setContent(msg.getContent());
        myAgent.send(query);
    }
    
    private void teacherRefuse(ACLMessage msg) {//zwalnianie miejsce, które zaklepał wykładowca
        groupAgent.setIsFoundPlace(false);
    }
    
    private void roomAgree(ACLMessage msg) {
        this.foundPlace = msg.getContent();
        groupAgent.finish();//powiadomienie Mastera o zakończeniu poszukiwań
    }
    
    private void roomRefuse(ACLMessage msg) {
        ACLMessage msgToTeacher = new ACLMessage(ACLMessage.CANCEL);
        msgToTeacher.addReceiver(new AID("Teacher" + Integer.toString(groupAgent.getGroup().getTeacher()), AID.ISLOCALNAME));
        msgToTeacher.setContent(msg.getContent());//wypełnienie wiadomości treścią
        myAgent.send(msgToTeacher);//wysałnie wiadomości
        groupAgent.setIsFoundPlace(false);
    }
    
    private void sendRating(ACLMessage msg) {
        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.INFORM_REF);
        String content[] = msg.getContent().split("-");
        int day = Integer.parseInt(content[0]);
        int time = Integer.parseInt(content[1]);
        int roomId = Integer.parseInt(content[2]);
        String rating = Integer.toString(groupAgent.getRating(day, time, roomId));
        reply.setContent(msg.getContent() + "-" + rating);
        myAgent.send(reply);
    }
    
    private void wakeUpFinder(ACLMessage msg) {
        groupAgent.startAgain();//powiadomienie mastera o wznowieniu poszukiwań
        ACLMessage msgToTeacher = new ACLMessage(ACLMessage.CANCEL);
        msgToTeacher.addReceiver(new AID("Teacher" + Integer.toString(groupAgent.getGroup().getTeacher()), AID.ISLOCALNAME));
        msgToTeacher.setContent(msg.getContent());//wypełnienie wiadomości treścią
        myAgent.send(msgToTeacher);//wysałnie wiadomości
        groupAgent.setIsFoundPlace(false);
    }
    
    private void sendResult(ACLMessage msg) {
        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.INFORM_REF);
        reply.setContent(foundPlace);
        myAgent.send(reply);
    }
}
