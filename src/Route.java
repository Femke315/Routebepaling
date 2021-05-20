package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Route {
    private int routeID;
    private int personID;
    private int aantalPakketten;
    private int reistijd;
    private int afstand;
    private String provincie;
    private String status;
    private String opmerkingen;
    
    Order[] Route;


    // Constructor met alle gegevens
    public Route(int routeID, int personID, int aantalPakketten, int reistijd, int afstand, String provincie, String status, String opmerkingen){
        this.routeID = routeID;
        this.personID = personID;
        this.aantalPakketten = aantalPakketten;
        this.reistijd = reistijd;
        this.afstand = afstand;
        this.provincie = provincie;
        this.status = status;
        this.opmerkingen = opmerkingen;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getPersonID() {
        return personID;
    }

    public int getAantalPakketten() {
        return aantalPakketten;
    }

    public int getReistijd() {
        return reistijd;
    }

    public int getAfstand() {
        return afstand;
    }

    public String getProvincie() {
        return provincie;
    }

    public String getStatus() {
        return status;
    }

    public String getOpmerkingen() {
        return opmerkingen;
    }
    
    
  

    //constructor voor een Route. Route wordt aangemaakt doormiddel van nearest neighbour heuristic. De indexnummer is de volgordeID, de value de orderID.
    public Route(ArrayList<Order> orderlijst) {

        //lijst met steden waaruit nog gekozen kan worden
        //wordt aangemaakt door de meegegeven orderlijst. Deze orderlijsten zijn gesorteerd op provincie
        ArrayList<Order> citiesLeft;
        citiesLeft = orderlijst;

        //Route wordt aangemaakt
        Order[] route;

        route = new Order[(orderlijst.size())];

        //startpunt en eindpunt zullen altijd het distributiecentrum zijn, maar is dit inprincipe aan te passen als het nodig is
        Order startpunt = new Order(52.179933, 5.429910);
        Order currentCity = startpunt;


        int i = 0;

        //Route wordt aangemaakt. Stopt wanneer er geen steden meer over zijn uit het lijstje
        while (citiesLeft.size() != 0) {
            //System.out.println("Current orderID = : " + currentCity.orderID);

            route[i] = nextOrder(currentCity, citiesLeft);
            currentCity = route[i];
            //System.out.println("Next orderID: " + currentCity.orderID);
            citiesLeft.remove(currentCity);
            i++;
        }


        this.Route = route;
    }



    //Methode om afstand te berekenen tussen de x en y coordinaten van twee punten
    public double afstandBerekenen(double userLat, double userLng, double venueLat, double venueLng) {
        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);
        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (double) 6371 * c;
    }

    // Methode om de laagste value van een array te krijgen, met een check op dat er geen 0 waardes meegerekend wordt.
    public static double getMin(double[] inputArray) {
        double minValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] != 0 && inputArray[i] < minValue) {
                minValue = inputArray[i];
            }
        }
        return minValue;
    }

    //Bereken welke bestelling als volgende komt in de route
    //Wordt berekend met de nearest neighbour heuristic. Oftewel, de bestelling die het dichtsbij zit wordt als volgende punt genomen
    //en wordt gereturned
    public Order nextOrder(Order currentCity, ArrayList<Order> citiesLeft) {
        double[] afstanden;

        //currentCity is het punt waarvan met alle andere (mogelijke) punten het verschil in verte wordt berekend
        Order volgendeOrder = currentCity;

        //array met alle afstanden
        afstanden = new double[citiesLeft.size()];

        //elke afstand tussen de currentCity en alle andere mogelijke punten worden vergeleken
        for (int i = 0; i < citiesLeft.size(); i++) {
            Order nextCity = citiesLeft.get(i);
            afstanden[i] = afstandBerekenen(currentCity.x, currentCity.y, nextCity.x, nextCity.y);
            double minValue = getMin(afstanden);
            //System.out.println(afstanden[i] + " OrderID = " + nextCity.orderID);

            //elke afstand wordt vergeleken met de laagste value van alle afstanden die tot nu toe in de afstanden array staan.
            //laagste uiteindelijke afstand wordt doorgegeven als Order object zodat de orderID terug te vinden is.
            if (afstanden[i] <= minValue) {
                volgendeOrder = citiesLeft.get(i);
                //System.out.println("Korste orderID tot nu toe = " + volgendeOrder.orderID);
            }
        }

        //System.out.println("kortste orderID = " + volgendeOrder.orderID);
        return volgendeOrder;

    }

    //simpele methode om een Route snel te weergeven
    public void printRoute() {
        for (int i = 0; i < Route.length; i++) {
            System.out.println("Volgordenummer: " + i + ". OrderID = " + Route[i].orderID);
        }
    }

}




