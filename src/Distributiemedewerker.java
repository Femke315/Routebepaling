//package src;

public class Distributiemedewerker {
    private static String naam;
    private static String mail;
    private static String telefoonnummer;
    private static int personID;
    private static String functie;
    private static Route route;

    public static void Inloggen(String naam, String mail, String telefoonnummer, int personID, String functie) {
        Distributiemedewerker.naam = naam;
        Distributiemedewerker.mail = mail;
        Distributiemedewerker.telefoonnummer = telefoonnummer;
        Distributiemedewerker.personID = personID;
        Distributiemedewerker.functie = functie;
    }

    public static void setRoute(Route route) {
       Distributiemedewerker.route = route;
    }

    public static void Uitloggen(){
        Distributiemedewerker.naam = null;
        Distributiemedewerker.mail = null;
        Distributiemedewerker.telefoonnummer = null;
        Distributiemedewerker.personID = 0;
        Distributiemedewerker.functie = null;
        Distributiemedewerker.route = null;
    }

    public static void print(){
        System.out.println("Naam: " + naam);
        System.out.println("Mail: " + mail);
        System.out.println("Telefoonnummer: " + telefoonnummer);
        System.out.println("Werknemersnummer: " + personID);
        System.out.println("Functie: " + functie);
        if (route!=null) {
            System.out.println("Route: " + route.getRouteID());
        }
    }

    public static String getNaam() {
        return naam;
    }

    public static String getMail() {
        return mail;
    }

    public static String getTelefoonnummer() {
        return telefoonnummer;
    }

    public static int getPersonID() {
        return personID;
    }

    public static String getFunctie() {
        return functie;
    }

    public static Route getRoute() {
        return route;
    }
}
