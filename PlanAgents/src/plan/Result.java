/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

/**
 *
 * @author Rafał Tkaczyk
 */
public class Result {
    
    private String symbol;
    int day;
    int time;
    int roomId;
    
    public Result(String symbol, int day, int time, int roomId) {
        this.symbol = symbol;
        this.day = day;
        this.time = time;
        this.roomId = roomId;
    }

    public int getDay() {
        return day;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        String d;
        if(day == 0) d = "pn";
        else if(day == 1) d = "wt";
        else if(day == 2) d = "sr";
        else if(day == 3) d = "cw";
        else d = "pt";
        
        String t;
        if(time == 0) t = "08:00 - 10:00";
        else if(time == 1) t = "10:00 - 12:00";
        else if(time == 2) t = "12:00 - 14:00";
        else if(time == 3) t = "14:00 - 16:00";
        else t = "16:00 - 18:00";
        
        return symbol.substring(5) + ": " + d + "\t" + t + " sala: " + roomId;
    }
    
    public String getTimeAsString() {
        if(time == 0) return "08:00 - 10:00";
        else if(time == 1) return "10:00 - 12:00";
        else if(time == 2) return "12:00 - 14:00";
        else if(time == 3) return "14:00 - 16:00";
        else if(time == 4) return "16:00 - 18:00";
        else return "18:00 - 20:00";
    }
}
