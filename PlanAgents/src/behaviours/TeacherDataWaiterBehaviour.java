/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import plan.Teacher;
import plan.TeacherAgent;

/**
 *
 * @author Rafał Tkaczyk
 */
public class TeacherDataWaiterBehaviour extends CyclicBehaviour {

    private TeacherAgent teacherAgent;
    
    public TeacherDataWaiterBehaviour(TeacherAgent a) {
        super(a);
        this.teacherAgent = a;
    }
    
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF));
        if(msg != null) {
            try {
                Teacher data = (Teacher) msg.getContentObject();
                teacherAgent.setTeacher(data);
                myAgent.addBehaviour(new TeacherCheckMsgBehaviour(teacherAgent));
                myAgent.removeBehaviour(this);
            } catch (UnreadableException ex) {
                System.out.println(ex.toString());
            }
        } else {
            block();
        }
    }
}
