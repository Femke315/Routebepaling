package src;

public class Distributiemedewerker {
    private static String naam;
    private static String mail;
    private static String telefoonnummer;
    private static int personID;
    private static String functie;

    public static void Inloggen(String naam, String mail, String telefoonnummer, int personID, String functie) {
        Distributiemedewerker.naam = naam;
        Distributiemedewerker.mail = mail;
        Distributiemedewerker.telefoonnummer = telefoonnummer;
        Distributiemedewerker.personID = personID;
        Distributiemedewerker.functie = functie;
    }

    public static void Uitloggen(){
        Distributiemedewerker.naam = null;
        Distributiemedewerker.mail = null;
        Distributiemedewerker.telefoonnummer = null;
        Distributiemedewerker.personID = 0;
        Distributiemedewerker.functie = null;
    }

    public static void print(){
        System.out.println("Naam: " + naam);
        System.out.println("Mail: " + mail);
        System.out.println("Telefoonnummer: " + telefoonnummer);
        System.out.println("Werknemersnummer: " + personID);
        System.out.println("Functie: " + functie);
    }

    public static String getFunctie() {
        return functie;
    }
}
