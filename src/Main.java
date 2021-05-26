
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
//        Boolean registreren = DatabaseConnectie.registreren("Anna", "TestWachtwoord", "Nerdy@hotmail.nl");
//
//        Boolean verbindingSluiten = DatabaseConnectie.verbindingSluiten();

//        HoofdschermGUI Scherm = new HoofdschermGUI();

        SQLqueries queries= new SQLqueries();//connectie wordt hierin gemaakt
        ArrayList<Order> orders= queries.showRoute(1);
        for (Order o:orders) {
            System.out.println("Postcode: " + o.getPostcode());
            System.out.println("Plaats: " + o.getPlaats());
            System.out.println("adres: "+ o.getAdres());
            System.out.println("\n");
        }
    }


}
