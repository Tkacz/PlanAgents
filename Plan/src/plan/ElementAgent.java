/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import jade.core.Agent;

/**
 *
 * @author rufus
 */
public class ElementAgent extends Agent {
    String teacher;
    String subject;
    int priority;//przedmiot obowiązkowy (o najwyższym priorytecie, nic nie może mieć z nim kolizji), seminarium, fakultet, monograf, etc;
    int groupID;//ID grupy;
    boolean projector;//Rzutnik;
    boolean computers;//Komputery;
    int studNumber;//Ilość studentów (potrzebna pojemność sali);
    int time;//Czas trwania;
    //Preferowane dni tygodnia;
    
}
