
import java.io.File;
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

        SQLqueries queries= new SQLqueries();//connectie wordt hierin gemaakt
        queries.getProducts(37);//test getProduct()
        System.out.println("\n\n");

        queries.getRoutes();
        System.out.println("\n\n");
        queries.getPerson(3146);//Connectie wordt hierin gesloten
        System.out.println("\n\n");

//        queries.getOrderlines(73596);
//        queries.getOrders(1);
        queries.getOrdersVanProvincie("Groningen");
        queries.getOrdersVanProvincie("Overijssel");
        System.out.println("\n\n");


        queries.showRoute(1);
    }


}
