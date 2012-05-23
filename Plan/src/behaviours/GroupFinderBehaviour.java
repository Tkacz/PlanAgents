/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import plan.GroupAgent;

/**
 *
 * @author rufus
 */
public class GroupFinderBehaviour extends CyclicBehaviour {

    private GroupAgent groupAgent;
    
    public GroupFinderBehaviour(GroupAgent a) {
        super(a);
        this.groupAgent = a;
    }
    
    @Override
    public void action() {
        
    }
}
