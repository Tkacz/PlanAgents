/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rafal Tkaczyk
 */
public class RMySQL {

    private static Connection con;
    private final String sql_connect;
    private final String sql_driver;
    private static Statement st;
    private final String sql_login;
    private final String sql_password;
    ////
    String tempSel = "SELECT g.symbol, g.ilosc, p.priorytet, p.komputery, p.rzutnik, p.tablicaInt, w.id, w.pon, w.wt, w.sr, w.czw, w.pt"
            + " FROM Grupy g, Przedmioty p, PrzedGru pg, Wykladowcy w, WykGru wg"
            + " WHERE pg.id_Przed=p.id"
            + " AND pg.id_Gru=g.id"
            + " AND wg.id_Wyk=w.id"
            + " AND wg.id_Gru=g.id;";
    ////

    public RMySQL() {
        con = null;
        sql_connect = "jdbc:mysql://127.0.0.1/plan?characterEncoding=utf8";
        sql_driver = "com.mysql.jdbc.Driver";
        sql_login = "root";
        sql_password = "pass";

        if (setDriver()) {
            System.out.println("Udane połączenie z bazą danych");
        } else {
            System.out.println("Nie udalo sie nawiazac polaczenia z baza danych");
        }
    }

    private boolean setDriver() {
        boolean ret = false;
        try {
            Class.forName(sql_driver);
            createStatement();
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Exception" + ex);
        } catch (Exception ex) {
            System.out.println("Driver Exception" + ex);
        }
        return ret;
    }

    private boolean open() {
        boolean ret = false;
        try {
            if ((con == null) || con.isClosed()) {
                System.out.println("Laczenie z baza danych");
                con = (Connection) DriverManager.getConnection(sql_connect, sql_login, sql_password);
                System.out.println("Polaczony z baza danych");
            }
            ret = true;
        } catch (SQLException ex) {
            System.out.println("Open Exception" + ex);
            while (ex != null) {
                ex = ex.getNextException();
            }
        }
        return ret;
    }

    private void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Close Con Exception" + ex);
        }
    }

    private void createStatement() {
        if (open()) {
            try {
                if (st == null || st.isClosed()) {
                    st = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                }
            } catch (SQLException ex) {
                System.out.println("createStatement Exception" + ex);
                while (ex != null) {
                    ex = ex.getNextException();
                }
            }
        }
    }

    private ResultSet select(String comm) {//zapytania
        ResultSet rs = null;
        if (comm != null) {
            try {
                rs = st.executeQuery(comm);
                System.out.println("Select complete");
            } catch (SQLException ex) {
                System.out.println("executeQuery Exception");
            }
        } else {
            System.out.println("query == null");
        }
        return rs;
    }

    private void execute(String comm) {//dodawanie do tabeli
        if (comm != null) {
            try {
                st.execute(comm);
            } catch (SQLException ex) {
                System.out.println("executeUpdate Exception: " + ex.toString());
            }
        }
    }

    //// PUBLIC METHODS ////
    public int[] getRoomsId() {
        int ids[], size;
        ResultSet rs = select("SELECT id FROM Sale;");
        try {
            rs.last();
            size = rs.getRow();
            ids = new int[size];
            rs.first();
            for (int i = 0; i < size; i++, rs.next()) {
                ids[i] = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ids = null;
            System.out.println(ex.toString());
        }

        return ids;
    }

    public int[] getTeachersId() {
        int ids[], size;
        ResultSet rs = select("SELECT id FROM Wykladowcy;");
        try {
            rs.last();
            size = rs.getRow();
            ids = new int[size];
            rs.first();
            for (int i = 0; i < size; i++, rs.next()) {
                ids[i] = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ids = null;
            System.out.println(ex.toString());
        }

        return ids;
    }

    public int[] getGroupsId() {
        int ids[], size;
        ResultSet rs = select("SELECT id FROM Grupy;");
        try {
            rs.last();
            size = rs.getRow();
            ids = new int[size];
            rs.first();
            for (int i = 0; i < size; i++, rs.next()) {
                ids[i] = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ids = null;
            System.out.println(ex.toString());
        }

        return ids;
    }

    public Group getGroupData(int id) {
        String sel = "SELECT g.symbol, g.ilosc, p.priorytet, p.komputery, p.rzutnik, p.tablicaInt, w.id, w.pon, w.wt, w.sr, w.czw, w.pt"
                + " FROM Grupy g, Przedmioty p, PrzedGru pg, Wykladowcy w, WykGru wg"
                + " WHERE g.id=" + Integer.toString(id)
                + " AND pg.id_Przed=p.id"
                + " AND pg.id_Gru=g.id"
                + " AND wg.id_Wyk=w.id"
                + " AND wg.id_Gru=g.id;";

        try {
            ResultSet rs = select(sel);
            rs.first();

            boolean komps;
            if (rs.getString(4).equals("T")) {
                komps = true;//czy potrzebne komputery
            } else {
                komps = false;
            }

            boolean projr;//czy potrzebny projektor
            if (rs.getString(5).equals("T")) {
                projr = true;//czy potrzebne komputery
            } else {
                projr = false;
            }

            boolean board;//czy potrzebna tablica interaktywna
            if (rs.getString(6).equals("T")) {
                board = true;//czy potrzebne komputery
            } else {
                board = false;
            }

            return new Group(rs.getString(1), rs.getInt(2), rs.getInt(3), komps, projr, board,
                    rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12));

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Room getRoomData(int id) {

        try {
            ResultSet rs = select("SELECT * FROM Sale WHERE id=" + Integer.toString(id) + ";");
            boolean komps;
            rs.first();
            if (rs.getString(4).equals("T")) {
                komps = true;
            } else {
                komps = false;
            }

            boolean projr;
            if (rs.getString(5).equals("T")) {
                projr = true;
            } else {
                projr = false;
            }

            boolean board;
            if (rs.getString(6).equals("T")) {
                board = true;
            } else {
                board = false;
            }

            return new Room(rs.getInt(1), rs.getInt(2), rs.getInt(3), komps, projr, board);

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Room> getAllRoomsData() {
        
        ArrayList<Room> rooms = new ArrayList<Room>();
        try {
            ResultSet rs = select("SELECT * FROM Sale;");
            boolean komps, projr, board;
            while(rs.next()) {
                if (rs.getString(4).equals("T")) {
                    komps = true;
                } else {
                    komps = false;
                }

                if (rs.getString(5).equals("T")) {
                    projr = true;
                } else {
                    projr = false;
                }

                if (rs.getString(6).equals("T")) {
                    board = true;
                } else {
                    board = false;
                }
                rooms.add(new Room(rs.getInt(1), rs.getInt(2), rs.getInt(3), komps, projr, board));
            }
            return rooms;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public int[] getTeacherDays(int id) {
        try {
            ResultSet rs = select("SELECT pon, wt, sr, czw, pt FROM Wykladowcy WHERE id=" + Integer.toString(id) + ";");
            rs.first();
            int days[] = {rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)};
            return days;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
