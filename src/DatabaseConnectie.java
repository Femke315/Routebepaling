package src;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class DatabaseConnectie {
    private static Connection connection;


    public static boolean verbindingMaken(){
        String url = "jdbc:mysql://localhost/nerdygadgets";
        String username = "root", password = "";
        boolean isVerbonden = true;
        SQLException foutmelding = null;

        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException throwables) {
            isVerbonden = false;
            foutmelding = throwables;
        }
        if (isVerbonden) {
            System.out.println("verbindingMaken() = Er is verbinding gemaakt met de database!");
        } else {
            System.out.println("verbindingMaken() = Er kon geen verbinding worden gemaakt met de database:");
            System.out.println(foutmelding.toString());
        }
        return isVerbonden;
    }

//    public static boolean inloggen(String gebruikersnaam, String wachtwoord){
//        boolean succes = true;
//        SQLException foutmelding = null;
//        Statement statement;
//
//        try {
//            statement = connection.createStatement();
//        } catch (SQLException throwables) {
//            succes = false;
//            System.out.println("Statement kon niet worden aangemaakt:");
//            System.out.println(throwables.toString());
//        }
//        if (statement != null) {
//            Resultset rs = statement.executeQuery("SELECT ");
//        }
//    }






    public static boolean verbindingSluiten(){
        boolean isGesloten = true;
        SQLException foutmelding = null;

        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                isGesloten = false;
                foutmelding = throwables;
            }
            if (isGesloten) {
                System.out.println("verbindingSluiten() = De verbinding met de database is verbroken!");
            } else {
                System.out.println("verbindingSluiten() = De verbinding met de database kon niet worden verbroken:");
                System.out.println(foutmelding.toString());
            }
            return isGesloten;
        } else {
            System.out.println("verbindingSluiten() = Er is geen verbinding om te verbreken");
            return false;
        }
    }







}
