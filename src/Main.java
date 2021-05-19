
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

        //methodes testen van SQLqueries file
        ArrayList<String> geordendeRoutes =  queries.getRoutesMetArray();
        for (String route:geordendeRoutes) {
            System.out.println(route);
        }
        System.out.println("\n\n");


        ArrayList<String> lijst = queries.getOrdersVanProvincieMetArray("Groningen");
        for (String order:lijst) {
            System.out.println(order);
        }
        System.out.println("\n\n");

        ArrayList<String> route = queries.showRouteMetArray(1);
        for (String bestelling: route) {
            System.out.println(bestelling);
        }
    }


}
