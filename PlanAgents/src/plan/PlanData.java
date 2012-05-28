/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

/**
 *
 * @author rufus
 */
public class PlanData {
    
    String symbol;
    String time;
    String name;
    String type;
    String teacher;
    int room;
    int day;
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getRoom() {
        return room;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return time + " : " + name + " : " + type + " : " + teacher + " : " + room;
    }
}
