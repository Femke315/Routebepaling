
import java.io.File;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;

public class Main {

    public static void main(String[] args) {

//        Boolean verbindingMaken = DatabaseConnectie.verbindingMaken();
//
//        String inloggen = DatabaseConnectie.inloggen("job@hotmail.nl", "TestWachtwoord");
//        System.out.println(inloggen);
//        System.out.println();
//
////        Boolean registreren = DatabaseConnectie.registreren("Job van Ardenne", "TestWachtwoord", "job@hotmail.nl");
//
//        Boolean verbindingSluiten = DatabaseConnectie.verbindingSluiten();

//        HoofdschermGUI Scherm = new HoofdschermGUI();


        SQLqueries queries= new SQLqueries();
        ArrayList<Order> orders= new ArrayList<>();
        orders.add(new Order(1));
        orders.add(new Order(2));
        queries.toevoegenRoute(orders);
    }


}
