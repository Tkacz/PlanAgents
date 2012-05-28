/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import behaviours.GroupDataWaiterBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

/**
 *
 * @author Rafał Tkaczyk
 */
public class GroupAgent extends Agent {
    
    private Group group;
    private ArrayList<Room> rooms;
    private boolean isFoundPlace;
        
    @Override
    protected void setup() {
        group = null;
        rooms = null;
        isFoundPlace = true;
        
        addBehaviour(new GroupDataWaiterBehaviour(this));
        
        ACLMessage msg = new ACLMessage(ACLMessage.QUERY_REF);
        msg.addReceiver(new AID("Master", AID.ISLOCALNAME));
        msg.setContent("GroupData");
        send(msg);
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
        msg.setContent("end");
        send(msg);
    }
    
    public void startAgain() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Master", AID.ISLOCALNAME));
        msg.setContent("start");
        send(msg);
    }
    
    public int getRating(int day, int time, int roomId) {
        int rating = 0;
        Room room = null;
        for(int i = 0, size = rooms.size(); i < size; i++) {
            if(rooms.get(i).getId() == roomId) {
                room = rooms.get(i);
            }
        }
        
        rating += group.getPriority();
        rating += group.getDayPriority(day);
        if(group.isBoard() == room.isBoard()) {
            rating += 1;
        } else {
            rating += 2;
        }
        if(group.isComps() == room.isComps()) {
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
               result.add(rooms.get(i)); 
            }
        }
        rooms = new ArrayList<Room>(result);
    }
    
    private boolean isRoomProper(Room room) {
        if(group.getNumber() > room.getCapacity()) {
            return false;
        } else if(group.isComps() == true && room.isComps() == false) {
            return false;
        } else if(group.isBoard() == true && room.isBoard() == false) {
            return false;
        } else if(group.isProjr() == true && room.isProjr() == false) {
            return false;
        }
        return true;
    }

    public void setGroup(Group group) {
        this.group = new Group(group);
    }

    public void setIsFoundPlace(boolean isFoundPlace) {
        this.isFoundPlace = isFoundPlace;
    }
    
    public boolean getIsFoundPlace() {
        return this.isFoundPlace;
    }

    public void setRooms(ArrayList<Room> rooms) {        
        this.rooms = new ArrayList<Room>(rooms);
        roomsFilter();//odrzucanie niepasujących sal
    }
}
