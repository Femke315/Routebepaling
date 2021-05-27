import com.mysql.cj.protocol.Resultset;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

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
            System.out.println();
        } else {
            System.out.println("verbindingMaken() = Er kon geen verbinding worden gemaakt met de database:");
            System.out.println(foutmelding.toString());
            System.out.println();
        }
        return isVerbonden;
    }


    public static String inloggen(String gebruikersnaam, String wachtwoord){
        PreparedStatement statementGebruikersnaam = null;
        ResultSet rs = null;
        String inlogStatus = null;
        boolean gebruikersnaamKlopt = false;
        boolean wachtwoordKlopt = false;
        boolean permissieKlopt = true;

        if (DatabaseConnectie.verbindingMaken()) {

            // Gebruikersnaam checken
            try {
                statementGebruikersnaam = connection.prepareStatement("SELECT * FROM people WHERE EmailAddress = ?");
                statementGebruikersnaam.setString(1, gebruikersnaam);
                rs = statementGebruikersnaam.executeQuery();
                rs.next();
                gebruikersnaamKlopt = (rs.getString(15).equals(gebruikersnaam));
            } catch (SQLException throwables) {
                System.out.println("inloggen() = Statement kon niet worden aangemaakt:");
                inlogStatus = "Mail is niet bekend in ons systeem";
                System.out.println(throwables.toString());
                System.out.println();
            }
            if (!gebruikersnaamKlopt) {
                inlogStatus = "Mail is niet bekend in ons systeem";
            }


            // Wachtwoord checken
            if (rs != null && gebruikersnaamKlopt) {
                MessageDigest digest = null;
                byte[] hash = null;

                try {
                    digest = MessageDigest.getInstance("SHA-256");
                    hash = digest.digest(wachtwoord.getBytes(StandardCharsets.UTF_8));
                } catch (NoSuchAlgorithmException throwables) {
                    System.out.println("inloggen() = Wachtwoord kon niet worden gehashed:");
                    inlogStatus = "Wachtwoord is niet geldig";
                    System.out.println(throwables.toString());
                    System.out.println();
                }

                try {
                    wachtwoordKlopt = (Arrays.equals(rs.getBytes(8), hash));

                    System.out.println();
                } catch (SQLException throwables) {
                    System.out.println("inloggen() = Wachtwoorden konden niet worden vergeleken:");
                    inlogStatus = "Wachtwoord kon niet worden vergeleken";
                    System.out.println(throwables.toString());
                    System.out.println();
                }

                if (!wachtwoordKlopt) {
                    inlogStatus = "Wachtwoord is onjuist";
                }
            }


            // Permissie checken
            if (rs != null && gebruikersnaamKlopt && wachtwoordKlopt) {
                try {
                    permissieKlopt = rs.getBoolean(9);
                    if (!permissieKlopt) {
                        permissieKlopt = rs.getBoolean(10);
                    } if (!permissieKlopt) {
                        permissieKlopt = rs.getBoolean(11);
                    }
                } catch (SQLException throwables) {
                    System.out.println("inloggen() = Permissie gegevens konden niet opgehaalt worden:");
                    inlogStatus = "Permissie gegevens konden niet opgehaalt worden";
                    System.out.println(throwables.toString());
                    System.out.println();
                }

                if (!permissieKlopt) {
                    inlogStatus = "Account heeft geen toegang om in te loggen";
                } else {
                    inlogStatus = "Account is ingelogd";
                }
            }

            // Distributiemedewerker klasse invullen
            if (permissieKlopt) {
                try {
                    String naam = rs.getString(2);
                    String mail = rs.getString(15);
                    String telefoonnummer = rs.getString(13);
                    int personID = rs.getInt(1);
                    String functie;

                    if (rs.getBoolean(9)){
                        functie = "Magazijn manager";
                    } else if (rs.getBoolean(10)) {
                        functie = "Magazijn sorteerder";
                    } else if (rs.getBoolean(11)) {
                        functie = "Bezorger";
                    } else {
                        functie = "Medewerker";
                    }

                    Distributiemedewerker.Inloggen(naam, mail, telefoonnummer, personID, functie);

                    if (Distributiemedewerker.getFunctie().equals("Bezorger")) {
                        Distributiemedewerker.setRoute(SQLqueries.gekozenRouteOphalen(Distributiemedewerker.getPersonID()));
                    }

                } catch (SQLException throwables) {
                    System.out.println("inloggen() = Permissie gegevens konden niet opgehaalt worden:");
                    System.out.println(throwables.toString());
                    System.out.println();
                }

            }


            // Query result afsluiten
            try {
                assert statementGebruikersnaam != null;
                statementGebruikersnaam.close();
                assert rs != null;
                rs.close();
            } catch (SQLException throwables) {
                gebruikersnaamKlopt = false;
                System.out.println("inloggen() = Afsluiten statement of resultset is niet gelukt:");
                System.out.println(throwables.toString());
                System.out.println();
            }

            DatabaseConnectie.verbindingSluiten();
        } else {
            inlogStatus = "Geen verbinding met database";
        }
            return inlogStatus;
    }


    public static boolean registreren(String fullName, String password, String emailaddress) {

        DatabaseConnectie.verbindingMaken();

        boolean isGeregistreerd = true;
        PreparedStatement statementRegistreren = null;
        int rs = 9999;
        int personID = 0;
        byte[] hash = null;

        try {
            Statement statementPersonID = connection.createStatement();
            ResultSet rsPersonID = statementPersonID.executeQuery("SELECT MAX(PersonID) FROM people");
            rsPersonID.next();
            personID = rsPersonID.getInt(1);
            System.out.println(personID);
        } catch (SQLException throwables) {
            isGeregistreerd = false;
            System.out.println("registreren() = Ophalen PersonID is niet gelukt:");
            System.out.println(throwables.toString());
            System.out.println();
        }


        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            isGeregistreerd = false;
            System.out.println("registreren() = Hashen van wachtwoord is niet gelukt:");
            System.out.println(e.toString());
            System.out.println();
        }

       try {
            statementRegistreren = connection.prepareStatement("INSERT INTO people (PersonID, FullName, PreferredName, SearchName, IsPermittedToLogon, IsExternalLogonProvider, HashedPassword, IsStockmanager, IsStockSorter, IsDeliverer, PhoneNumber, EmailAddress, LastEditedBy, ValidFrom, ValidTo,postcode) " +
                                                                   "VALUES (?, ?, ?, ?, 1, 0, ?, 0, 0, 1, '06 123 45 678', ?, 1, ?, ?, '4568RD')");
            statementRegistreren.setInt(1, personID+1);
            statementRegistreren.setString(2, fullName);
            statementRegistreren.setString(3, fullName);
            statementRegistreren.setString(4, fullName);
            statementRegistreren.setBytes(5, hash);
            statementRegistreren.setString(6, emailaddress);
            statementRegistreren.setString(7, String.valueOf(LocalDate.now()));
           statementRegistreren.setString(8, String.valueOf(LocalDate.now().plusYears(5)));
            rs = statementRegistreren.executeUpdate();
        } catch (SQLException throwables) {
            isGeregistreerd = false;
            System.out.println("registreren() = Statement kon niet worden aangemaakt:");
            System.out.println(throwables.toString());
            System.out.println();
        }

        System.out.println(rs);

        DatabaseConnectie.verbindingSluiten();

        return isGeregistreerd;
    }


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
                System.out.println();
            } else {
                System.out.println("verbindingSluiten() = De verbinding met de database kon niet worden verbroken:");
                System.out.println(foutmelding.toString());
                System.out.println();
            }
            return isGesloten;
        } else {
            System.out.println("verbindingSluiten() = Er is geen verbinding om te verbreken");
            System.out.println();
            return false;
        }
    }

    public static Connection getConnection() {
        try {
        if (connection==null || connection.isClosed()) {
            verbindingMaken();
        } } catch (SQLException ignored) {

        }
        return connection;
    }


}
