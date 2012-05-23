/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import behaviours.GroupCheckRoomAnsBehaviour;
import behaviours.GroupCheckTeacherAnsBehaviour;
import behaviours.GroupFinderBehaviour;
import behaviours.GroupWaitingForCancelBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

/**
 *
 * @author rufus
 */
public class GroupAgent extends Agent {
    
    private int id;
    private Group group = null;
    private ArrayList<Room> rooms;
    private RMySQL sql;
    private boolean isTeacherAgreement;
    private String foundPlace;
    private String foundRoom;
    private boolean isFoundPlace;
        
    @Override
    protected void setup() {
        
        id = Integer.parseInt(getAID().getLocalName().substring(5));
        
        isTeacherAgreement = false;
        isFoundPlace = false;
        sql = new RMySQL();        
        group = sql.getGroupData(id);
        rooms = sql.getAllRoomsData();
        roomsFilter();
        
        if(group != null) {
            System.out.println("Hello, I'm agent " + getAID().getLocalName() + " : " + group.toString());
            if(rooms == null) {
                System.out.println("agent: " + getAID().getLocalName() + ": rooms == null");
                finish();
            } else {
                addBehaviour(new GroupFinderBehaviour(this));
                addBehaviour(new GroupCheckTeacherAnsBehaviour(this));
                addBehaviour(new GroupCheckRoomAnsBehaviour(this));
                addBehaviour(new GroupWaitingForCancelBehaviour(this));
            }
        } else {
            System.out.println("agent: " + getAID().getLocalName() + ": group == null");
            finish();
        }
    }
    
    @Override
    protected void takeDown() {
        super.takeDown();
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public void finish() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Master", AID.ISLOCALNAME));
        send(msg);
    }
    
    public void startAgain() {
        ACLMessage msg = new ACLMessage(ACLMessage.CANCEL);
        msg.addReceiver(new AID("Master", AID.ISLOCALNAME));
        send(msg);
    }
    
    public int getRating(int time, int day, int roomId) {
        int rating = 0;
        Room room = sql.getRoomData(roomId);
        
        rating += group.getPriority();
        rating += group.getDayPriority(day);
        if(group.isBoard() == room.isBoard()) {
            rating += 1;
        } else {
            rating += 2;
        }
        if(group.isKomps() == room.isKomps()) {
            rating += 1;
        } else {
            rating += 4;
        }
        if(group.isProjr() == room.isProjr()) {
            rating += 1;
        } else {
            rating += 3;
        }
        
        return rating;
    }
    
    private void roomsFilter() {//usuwanie z listy sal, które nie spełniają wymagań grupy
        ArrayList<Room> result = new ArrayList<Room>();
        for(int i = 0, size = rooms.size(); i < size; i++) {
            if(isRoomProper(rooms.get(i))) {
               System.out.println("!!!");
               result.add(rooms.get(i)); 
            }
        }
        rooms = new ArrayList<Room>(result);
    }
    
    private boolean isRoomProper(Room room) {
        if(group.getNumber() > 
                room.getCapacity()) {
            return false;
        } else if(group.isKomps() == true && room.isKomps() == false) {
            return false;
        } else if(group.isBoard() == true && room.isBoard() == false) {
            return false;
        } else if(group.isProjr() == true && room.isProjr() == false) {
            return false;
        }
        return true;
    }

    public void setIsTeacherAgreement(boolean isTeacherAgreement) {
        this.isTeacherAgreement = isTeacherAgreement;
    }
    
    public boolean getIsTeacherAgreement() {
        return this.isTeacherAgreement;
        
    }

    public String getFoundPlace() {
        return foundPlace;
    }

    public String getFoundRoom() {
        return foundRoom;
    }

    public void setFoundPlace(String foundPlace) {
        this.foundPlace = foundPlace;
    }

    public void setFoundRoom(String foundRoom) {
        this.foundRoom = foundRoom;
    }

    public void setIsFoundPlace(boolean isFoundPlace) {
        this.isFoundPlace = isFoundPlace;
    }
    
    public boolean getIsFoundPlace() {
        return this.isFoundPlace;
    }
}
