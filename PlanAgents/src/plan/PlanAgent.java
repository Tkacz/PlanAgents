/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import behaviours.PlanGuardianBehaviour;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;

import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;

/**
 *
 * @author rufus
 */
public class PlanAgent extends Agent {
    
    private int roomsId[];//id sal
    private int teachersId[];//ids of teachers
    private int groupsId[];//id of groups
    private ArrayList<AgentController> roomsAgents;
    private ArrayList<AgentController> teachersAgents;
    private ArrayList<AgentController> groupsAgents;
    private ArrayList<String> groups;
    private RMySQL sql;
    private AgentContainer agCont;
    private PlanGuardianBehaviour guard;
    
    @Override
    protected void setup() {
        
        agCont = (AgentContainer) getContainerController();
        
        sql = new RMySQL();
        roomsId = sql.getRoomsId();
        startRoomsAgents();
        
        teachersId = sql.getTeachersId();
        startTeachersAgents();
        
        groupsId = sql.getGroupsId();
        startGroupsAgents();
        
        groups = new ArrayList<String>();
        guard = new PlanGuardianBehaviour(this);
        addBehaviour(guard);
    }
    
    @Override
    protected void takeDown() {
        super.takeDown();
    }
    
    private void startRoomsAgents() {
        roomsAgents = new ArrayList<AgentController>();
        try {
            String name;
            for(int i = 0; i < roomsId.length; i++){
                name = "Room" + Integer.toString(roomsId[i]);
                roomsAgents.add(agCont.createNewAgent(name, "plan.RoomAgent", null));
                roomsAgents.get(i).start();
                Thread.sleep(1000);
            }
        }
        catch (Exception e) {
            System.out.println("startRoomsAgents exceptions: " + e.toString());
        }
    }
    
    private void startTeachersAgents() {
        teachersAgents = new ArrayList<AgentController>();
        try {
            String name;
            for(int i = 0; i < teachersId.length; i++){
                name = "Teacher" + Integer.toString(teachersId[i]);
                teachersAgents.add(agCont.createNewAgent(name, "plan.TeacherAgent", null));
                teachersAgents.get(i).start();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("startTeachersAgents exception: " + e.toString());
        }
    }
    
    private void startGroupsAgents() {
        groupsAgents = new ArrayList<AgentController>();
        try {
            String name;
            //////////////
//            name = "Group" + Integer.toString(groupsId[0]);
//            groupsAgents.add(agCont.createNewAgent(name, "plan.GroupAgent", null));
//            groupsAgents.get(0).start();
//            Thread.sleep(50);
            ////////////////
            
            for(int i = 0; i < groupsId.length; i++){
                name = "Group" + Integer.toString(groupsId[i]);
                groupsAgents.add(agCont.createNewAgent(name, "plan.GroupAgent", null));
                groupsAgents.get(i).start();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("startGroupsAgents exceptions: " + e.toString());
        }
    }
    
    public void addToGroups(String name) {
        System.out.println("Add to groups: " + name);
        if(groups.indexOf(name) == -1) {//jeszcze nie ma
            this.groups.add(name);
        }
        if(groups.size() == groupsAgents.size()) {
            slaughter();
        }
    }
    
    public void removeFromGroups(String name) {
        System.out.println("Remove from groups: " + name);
        groups.remove(name);
    }
    
    private void slaughter() {
        try {
            for (int i = 0, size = teachersAgents.size(); i < size; i++) {
                teachersAgents.get(i).kill();
            }
            
            for(int i = 0, size = roomsAgents.size(); i < size; i++) {
                roomsAgents.get(i).kill();
            }
            
            for(int i = 0, size = groupsAgents.size(); i < size; i++) {
                groupsAgents.get(i).kill();
            }
            removeBehaviour(guard);
            stopSystem();
        } catch (StaleProxyException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void stopSystem() {
        Codec codec = new SLCodec();
        Ontology jmo = JADEManagementOntology.getInstance();
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(jmo);
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(getAMS());
        msg.setLanguage(codec.getName());
        msg.setOntology(jmo.getName());
        try {
            getContentManager().fillContent(msg, new Action(getAID(), new ShutdownPlatform()));
            send(msg);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
