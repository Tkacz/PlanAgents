package plan;

import behaviours.PlanDataManagerBehaviour;
import behaviours.PlanGuardianBehaviour;
import behaviours.PlanResultsGetterBehaviour;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;

import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author rufus
 */
public class PlanAgent extends Agent {
    
    private ArrayList<Room> rooms;
    private ArrayList<Teacher> teachers;
    private ArrayList<Group> groups;
    private ArrayList<AgentController> roomsAgents;
    private ArrayList<AgentController> teachersAgents;
    private ArrayList<AgentController> groupsAgents;
    private ArrayList<String> groupsEnd;
    private RMySQL sql;
    private AgentContainer agCont;
    private PlanGuardianBehaviour guard;
    private PlanDataManagerBehaviour dataManager;
    private PlanResultsGetterBehaviour getter;
    private ArrayList<Result> results;
    
    @Override
    protected void setup() {
        
        results = new ArrayList<Result>();
        
        agCont = (AgentContainer) getContainerController();
        
        sql = new RMySQL();
                
        rooms = sql.getAllRoomsData();
        teachers = sql.getAllTeachersData();
        groups = sql.getAllGroupsData();
        
        dataManager = new PlanDataManagerBehaviour(this);
        addBehaviour(dataManager);
        
        startRoomsAgents();
        startTeachersAgents();
        startGroupsAgents();
        
        groupsEnd = new ArrayList<String>();
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
            for(int i = 0; i < rooms.size(); i++){
                name = "Room" + Integer.toString(rooms.get(i).getId());
                roomsAgents.add(agCont.createNewAgent(name, "plan.RoomAgent", null));
                roomsAgents.get(i).start();
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
            for(int i = 0; i < teachers.size(); i++){
                name = "Teacher" + Integer.toString(teachers.get(i).getId());
                teachersAgents.add(agCont.createNewAgent(name, "plan.TeacherAgent", null));
                teachersAgents.get(i).start();
            }
        } catch (Exception e) {
            System.out.println("startTeachersAgents exception: " + e.toString());
        }
    }
    
    private void startGroupsAgents() {
        groupsAgents = new ArrayList<AgentController>();
        try {
            String name;
            for(int i = 0; i < groups.size(); i++){
                name = "Group" + groups.get(i).getSymbol();
                groupsAgents.add(agCont.createNewAgent(name, "plan.GroupAgent", null));
                groupsAgents.get(i).start();
            }
        } catch (Exception e) {
            System.out.println("startGroupsAgents exceptions: " + e.toString());
        }
    }
    
    public void addToGroups(String name) {
        if(groupsEnd.indexOf(name) == -1) {
            this.groupsEnd.add(name);
        }
        if(groupsEnd.size() == groupsAgents.size()) {
            
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            for(int i = 0, size = groupsEnd.size(); i < size; i++) {
                msg.addReceiver(new AID(groupsEnd.get(i), AID.ISLOCALNAME));
            }
            send(msg);
            removeBehaviour(guard);
            removeBehaviour(dataManager);
            getter = new PlanResultsGetterBehaviour(this);
            addBehaviour(getter);
        }
    }
    
    public void removeFromGroups(String name) {
        groupsEnd.remove(name);
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
            //stopSystem();
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
    
    public Room getRoomData(int id) {
        for(int i = 0, size = rooms.size(); i < size; i++) {
            if(rooms.get(i).getId() == id) {
                return rooms.get(i);
            }
        }
        return null;
    }
    
    public Teacher getTeacherData(int id) {
        for(int i = 0, size = teachers.size(); i < size; i++) {
            if(teachers.get(i).getId() == id) {
                return teachers.get(i);
            }
        }
        return null;
    }
    
    public Group getGroupData(String symbol) {
        for(int i = 0, size = groups.size(); i < size; i++) {
            if(groups.get(i).getSymbol().equals(symbol)) {
                return groups.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public void addToResults(Result result) {
        this.results.add(result);
        if(results.size() == groupsEnd.size()) {
            removeBehaviour(getter);
            
            showResults();
            
            slaughter();
        }
    }
    
    private void showResults() {
        
        ArrayList<PlanData> data = sql.getPlanData();
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).toString());
            for(int j = 0, size2 = data.size(); j < size2; j++) {
                if(data.get(j).getSymbol().equals(results.get(i).getSymbol().substring(5))) {
                    data.get(j).setTime(results.get(i).getTimeAsString());
                    data.get(j).setRoom(results.get(i).getRoomId());
                    data.get(j).setDay(results.get(i).getDay());
                }
            }
        }
        
        /*for(int i = 0; i < data.size(); i ++) {
            System.out.println(data.get(i).toString());
        }*/
        
        JFrame planView = new PlanView(data);
        planView.setVisible(true);
    }
}
