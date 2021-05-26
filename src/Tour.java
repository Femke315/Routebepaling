package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Tour {

    int afstand;
    int aantalPakketten;
    ArrayList<Order> Tour;

    //setter om de totale afstand van een route te berekenen
    public void setTotaleAfstand(double kortsteAfstand) {
        int afstandErbij = (int) Math.round(kortsteAfstand);
        this.afstand = getAfstand() + afstandErbij;
    }

    public void setAantalPakketten(int aantal) {
        this.aantalPakketten = aantal;
    }

    public int getAantalPakketten() {
        return aantalPakketten;
    }

    public int getAfstand() {
        return afstand;
    }

    //constructor voor een Route. Route wordt aangemaakt doormiddel van nearest neighbour heuristic. De indexnummer is de volgordeID, de value de orderID.
    public void setTour(ArrayList<Order> orderlijst) {

        //lijst met steden waaruit nog gekozen kan worden
        //wordt aangemaakt door de meegegeven orderlijst. Deze orderlijsten zijn gesorteerd op provincie
        ArrayList<Order> citiesLeft;
        citiesLeft = orderlijst;

        //Route wordt aangemaakt
        Tour = new ArrayList<>();

        //aantal pakketten wordt opgeslagen
        setAantalPakketten(orderlijst.size());

        //startpunt en eindpunt zullen altijd het distributiecentrum zijn, maar is dit inprincipe aan te passen als het nodig is
        Order startpunt = new Order(52.179933, 5.429910);
        Order currentCity = startpunt;

        int i = 0;

        //Route wordt aangemaakt. Stopt wanneer er geen steden meer over zijn uit het lijstje
        while (citiesLeft.size() != 0) {
            //System.out.println("Current orderID = : " + currentCity.orderID);

            Tour.add(nextOrder(currentCity, citiesLeft));
            currentCity = Tour.get(i);
            //System.out.println("Next orderID: " + currentCity.orderID);
            citiesLeft.remove(currentCity);
            i++;

        }

        //afstand tussen laatste punt en startpunt berekenen
        setTotaleAfstand(afstandBerekenen(currentCity.getLatitude(), currentCity.getLongitude(), startpunt.getLatitude(), startpunt.getLongitude()));

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
        double kortsteAfstand = 0;

        //currentCity is het punt waarvan met alle andere (mogelijke) punten het verschil in verte wordt berekend
        Order volgendeOrder = currentCity;

        //array met alle afstanden
        afstanden = new double[citiesLeft.size()];

        //elke afstand tussen de currentCity en alle andere mogelijke punten worden vergeleken
        for (int i = 0; i < citiesLeft.size(); i++) {
            Order nextCity = citiesLeft.get(i);
            afstanden[i] = afstandBerekenen(currentCity.getLatitude(), currentCity.getLongitude(), nextCity.getLatitude(), nextCity.getLongitude());
            double minValue = getMin(afstanden);
            //System.out.println(afstanden[i] + " OrderID = " + nextCity.orderID);

            //elke afstand wordt vergeleken met de laagste value van alle afstanden die tot nu toe in de afstanden array staan.
            //laagste uiteindelijke afstand wordt doorgegeven als Order object zodat de orderID terug te vinden is.
            if (afstanden[i] <= minValue) {
                volgendeOrder = citiesLeft.get(i);
                kortsteAfstand = afstanden[i];
                //System.out.println("Korste orderID tot nu toe = " + volgendeOrder.orderID);
            }
        }

        //System.out.println("kortste orderID = " + volgendeOrder.orderID);

        //afstand van volgendeOrder wordt meegenomen in de totale afstand
        setTotaleAfstand(kortsteAfstand);

        return volgendeOrder;

    }

    //    simpele methode om een Route snel te weergeven
    public void printRoute() {
        for (int i = 0; i < Tour.size(); i++) {
            System.out.println("Volgordenummer: " + i + ". OrderID = " + Tour.get(i).getOrderID());
        }
    }

}
