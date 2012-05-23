/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import plan.GroupAgent;
import plan.Room;

/**
 *
 * @author rufus
 */
public class GroupFinderBehaviour extends CyclicBehaviour {

    private GroupAgent groupAgent;
    private Room plan[][][];//plan [dni][jednostki czasowe][sale]
    private int roomsNo;//ilość sal
    private int x, y, z;//współrzędne na planie

    public GroupFinderBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
        createPlan(a.getRooms());
        x = 0;
        y = 0;
        z = 0;
    }

    @Override
    public void action() {
        //szukanie sali
        if (!groupAgent.getIsFoundPlace()) {//jeśli jeszcze nic nie znalazł
            if (x == 5) {
                x = 0;
            }
            for (; x < 5; x++) {
                if (y == 5) {
                    y = 0;
                }
                for (; y < 5; y++) {
                    if (z == roomsNo) {
                        z = 0;
                    }
                    for (; z < roomsNo; z++) {
                        ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);//przygotowanie zapytania do wykładowcy
                        msg.addReceiver(new AID("Teacher" + Integer.toString(groupAgent.getGroup().getTeacher()), AID.ISLOCALNAME));
                        String content = "d" + Integer.toString(x) + "t" + Integer.toString(y);//określenie dnia i czasu
                        msg.setContent(content);//wypełnienie wiadomości treścią
                        myAgent.send(msg);//wysałnie wiadomości
                        groupAgent.setFoundPlace(content);
                        groupAgent.setFoundRoom("Room" + Integer.toString(plan[x][y][z].getId()));
                        System.out.println("Found place: " + content);///////////////////////////////////
                        return;//przerwanie akcji zachowania
                    }
                }
            }
        }
    }

    private void createPlan(ArrayList<Room> rooms) {
        roomsNo = rooms.size();
        System.out.println("roomsNo: " + roomsNo);
        plan = new Room[5][5][roomsNo];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < roomsNo; k++) {
                    plan[i][j][k] = rooms.get(k);
                }
            }
        }
    }
}
