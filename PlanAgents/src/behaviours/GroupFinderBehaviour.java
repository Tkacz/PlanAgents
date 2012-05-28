/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import plan.GroupAgent;
import plan.Room;

/**
 *
 * @author Rafał Tkaczyk
 */
public class GroupFinderBehaviour extends CyclicBehaviour {

    private int DAY;
    private int TIME;
    
    private GroupAgent groupAgent;
    private Room plan[][][];//plan [dni][jednostki czasowe][sale]
    private int roomsNo;//ilość sal
    private int x, y, z;//współrzędne na planie
    private Properties properties;
    
    public GroupFinderBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
        
        x = 0;
        y = 0;
        z = -1;
        
        try {
            FileInputStream in = new FileInputStream("src/plan/plan.properties");
            properties = new Properties();
            properties.load(in);
            in.close();
            DAY = Integer.parseInt(properties.getProperty("days"));
            TIME = Integer.parseInt(properties.getProperty("times"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        createPlan(a.getRooms());
    }

    @Override
    public void action() {
        //szukanie sali
        if (!groupAgent.getIsFoundPlace()) {//jeśli jeszcze nic nie znalazł
            z++;
            if (z == roomsNo) {
                z = 0;
                y++;
                if (y == TIME) {
                    y = 0;
                    x++;
                    if (x == DAY) {
                        x = 0;
                    }
                }
            }
            
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);//przygotowanie zapytania do wykładowcy
            msg.addReceiver(new AID("Teacher" + Integer.toString(groupAgent.getGroup().getTeacher()), AID.ISLOCALNAME));
            String content = Integer.toString(x) + "-" + Integer.toString(y) //określenie dnia i czasu
                    + "-" + Integer.toString(plan[x][y][z].getId())//id sali
                    + "-" + myAgent.getAID().getLocalName()
                    + "-" + Integer.toString(groupAgent.getRating(x, y, plan[x][y][z].getId()));
            msg.setContent(content);//wypełnienie wiadomości treścią
            myAgent.send(msg);//wysałnie wiadomości
            groupAgent.setIsFoundPlace(true);
        }
    }

    private void createPlan(ArrayList<Room> rooms) {
        roomsNo = rooms.size();
        plan = new Room[DAY][TIME][roomsNo];
        for (int i = 0; i < DAY; i++) {
            for (int j = 0; j < TIME; j++) {
                for (int k = 0; k < roomsNo; k++) {
                    plan[i][j][k] = rooms.get(k);
                }
            }
        }
    }
}
