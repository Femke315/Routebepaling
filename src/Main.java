
import java.io.File;
import java.util.ArrayList;

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
//        queries.getProducts(37);//test getProduct()
        queries.getRoutes("Klaar voor sorteren");
    }


}
