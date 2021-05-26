import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;


public class SQLqueries {
    private static Connection connection;
    //product informatie ophalen
    public void getProducts(int productID){
        connection=DatabaseConnectie.getConnection();
        //+voorraad, dus met stockitemholdings tabel
        String query = " SELECT si.StockitemID, si.StockItemName, QuantityOnHand From StockItems si LEFT JOIN stockitemholdings sh ON si.StockItemID=sh.StockItemID WHERE si.StockitemID=?";


        try (PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, productID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.print("StockitemID: " + rs.getInt("StockitemID"));
                    System.out.print(", description: " + rs.getString("StockItemName"));
                    System.out.println(", QuantityOnHand: " + rs.getInt("QuantityOnHand"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();

    }

    //VÓÓR het algoritme: ongeordende lijst met alle orders in een provincie
    public ArrayList<Order> getOrdersVanProvincie(String provincie){
        connection=DatabaseConnectie.getConnection();
        ArrayList<Order> ordersLijst= new ArrayList<>();

        //create statement/query
        String query = "SELECT OrderID FROM orders o INNER JOIN people p ON o.KlantID=p.PersonID WHERE p.postcode IN (" +
                "SELECT PostCodePK FROM postcode WHERE provincie = ? ) limit 100";

        try (PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, provincie);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    //order toevoegen aan de lisjt
//                        ordersLijst.add("OrderID: " + rs.getInt("OrderID"));
                    ordersLijst.add(new Order(rs.getInt("OrderID")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        DatabaseConnectie.verbindingSluiten();
        return ordersLijst;
    }

    //NA het algoritme: De al geordende lijst van routes ophalen voor het routeoverzicht
    public static ArrayList<Route> getRoutes(String status){
        connection=DatabaseConnectie.getConnection();
        ArrayList<Route> routes= new ArrayList<>();


        //create statement/query
        String query = "SELECT RouteID, Provincie, Status, AantalPakketten, ReisTijd, Afstand, Opmerkingen From route WHERE Status=?";

        //maak er een prepared statment + connectie
        try (PreparedStatement stmRoutes  = connection.prepareStatement(query)) {
            stmRoutes.setString(1, status);//parameter toevoegen in query

            //data ontvangen---------------------
            try (ResultSet rs = stmRoutes.executeQuery()) {
                while (rs.next()) {
                    //voeg de route object toe aan arraylist
                    routes.add(new Route(rs.getInt("RouteID"), rs.getString("Provincie"), rs.getString("Status"), rs.getInt("AantalPakketten"), rs.getDouble("ReisTijd"), rs.getInt("Afstand"), rs.getString("Opmerkingen")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();
        return routes;
    }

    //NA het algoritme: één route ophalen
    public ArrayList<Order> showRoute(int routeID){
        connection=DatabaseConnectie.getConnection();
        ArrayList<Order> route= new ArrayList<>();

        String query = "SELECT o.OrderID, volgordeID FROM orders o INNER JOIN routelines r ON o.OrderID=r.OrderID WHERE r.RouteID=?";

        //prepared statement maken
        try (PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query

            //opgevraagde data ontvangen
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
//                    System.out.println("Opgehaald OrderID: " + rs.getInt("OrderID"));
                    //order toevoegen op een specifieke index

                    route.add(rs.getInt("volgordeID")-1, new Order(rs.getInt("OrderID")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();

        return route;
    }

    //Een berekende route in database opslaan in een transactie
    public void toevoegenRoute(Tour route) {
        connection= DatabaseConnectie.getConnection();
        int routeID=0;

        //queries om een route op te slaan
        String toevoegenRoute = "INSERT INTO route (AantalPakketten, Afstand, Status, Provincie) VALUES (?,?,?,?)";
        String lockTabellen= "LOCK TABLE route WRITE, orders WRITE, routelines Write";//tabellen opslot zetten
        String aanpassenOrder="UPDATE orders SET LastEditedWhen=?, routeID=?, Status= 'Klaar voor sorteren' WHERE OrderID=?";
        String toevoegenRouteline= "INSERT INTO Routelines (VolgordeID, RouteID, OrderID) VALUES (?,?,?)";
        String openTabellen= "UNLOCK TABLES";

        try {
            connection.setAutoCommit(false);

            //Route aanmaken
            PreparedStatement AanmakenRouteStmt = connection.prepareStatement(toevoegenRoute, Statement.RETURN_GENERATED_KEYS);
            AanmakenRouteStmt.setInt(1, route.getAantalPakketten());
            AanmakenRouteStmt.setInt(2, route.getAfstand());//HIER MOET NOG DE AFSTAND VARIABLE KOMEN
            AanmakenRouteStmt.setString(3, "Klaar voor sorteren");
            AanmakenRouteStmt.setString(4,"onbekend");
            AanmakenRouteStmt.executeUpdate();
            ResultSet rs = AanmakenRouteStmt.getGeneratedKeys();//Gemaakte routeID ophalen
            if(rs.next())
                routeID = rs.getInt(1);

            //tabellen op slot zetten
            PreparedStatement tabellenOpslotStmt = connection.prepareStatement(lockTabellen);
            tabellenOpslotStmt.executeUpdate();


            for (int i = 0; i < route.getAantalPakketten(); i++) {
                //per bestelling status veranderen en routeID meegeven
                PreparedStatement orderAanpassenStmt = connection.prepareStatement(aanpassenOrder);

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());//ophalen van huidige datum

                orderAanpassenStmt.setDate(1, Date.valueOf(formatter.format(date)));
                orderAanpassenStmt.setInt(2, routeID);
                orderAanpassenStmt.setInt(3,route.Tour.get(i).getOrderID());
                orderAanpassenStmt.executeUpdate();

                //per bestelling een routeline aanmaken
                PreparedStatement toevoegenRoutelineStmt = connection.prepareStatement(toevoegenRouteline);
                toevoegenRoutelineStmt.setInt(1, i);//het begint bij nul, dat is dan de opslag
                toevoegenRoutelineStmt.setInt(2,routeID);
                toevoegenRoutelineStmt.setInt(3,route.Tour.get(i).getOrderID());
                toevoegenRoutelineStmt.executeUpdate();
            }

            //alle tabellen openen
            PreparedStatement tabellenOpenenStmt = connection.prepareStatement(openTabellen);
            tabellenOpenenStmt.executeUpdate();

            connection.commit();
            System.out.println("commit");
        } catch(SQLException sqle){
            System.out.println("cause: " + sqle.getMessage());
            try {
                connection.rollback();//proberen om alles terug te draaien
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("rollback ging verkeerd: " + throwables.getMessage());
            }
        }finally {
            DatabaseConnectie.verbindingSluiten();
        }

    }

    //Voor de actor: de magazijn sorteerder
    public boolean statusSorterenNaarBezorging(int routeID){

        boolean isAangepast=false;

        connection=DatabaseConnectie.getConnection();

        String query = "UPDATE route SET Status='Klaar voor bezorging' WHERE RouteID=?";

        //prepared statement maken
        try (PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query
            int aantalRijenVeranderd =stmt.executeUpdate();

            if(aantalRijenVeranderd ==1){
                isAangepast=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();

        return isAangepast;
    }

    //Voor de actor: de bezorger
    public boolean statusBezorgingNaarOnderweg(int routeID, int personID){
        boolean zijnAangepast=false;
        int aantalRijenAangepast=0;
        connection=DatabaseConnectie.getConnection();

        //eerst toe-eigenen van een route
        String toeEigenenQuery = "UPDATE route SET PersonID=? WHERE RouteID=?";

        //prepared statement maken
        try (PreparedStatement stmt = connection.prepareStatement(toeEigenenQuery))
        {
            stmt.setInt(1, personID);//medewerkersnummer toevoegen
            stmt.setInt(2, routeID);//routeID toevoegen

            aantalRijenAangepast = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //query om een route status te veranderen
        String updateQuery = "UPDATE route SET Status='Onderweg' WHERE RouteID=?";

        //prepared statement maken
        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery))
        {
            updateStmt.setInt(1, routeID);//parameter toevoegen in query
             aantalRijenAangepast =+ updateStmt.executeUpdate();
            //checken of beide statements zijn uitgevoerd
            if(aantalRijenAangepast ==2){
                zijnAangepast=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        DatabaseConnectie.verbindingSluiten();

        return zijnAangepast;
    }

    //Voor de actor: de bezorger
    public boolean routeAfronden(Route route){
        boolean isAangepast=false;

        connection= DatabaseConnectie.getConnection();

        //orderlijst ophalen van route
        ArrayList<Order> bestellingen = showRoute(route.getRouteID());

        /*
            Status van route op 'afronden' zetten
            Statussen van alle bestellingen op 'afronden' zetten
         */

        //queries om een route op te slaan
        String updateRouteStatus= "UPDATE route SET Status='Afgerond' WHERE RouteID=?";
        String lockTabellen= "LOCK TABLE orders WRITE";//tabellen opslot zetten
        String aanpassenOrder="UPDATE orders SET LastEditedWhen=?, Status='Geleverd' WHERE OrderID=?";
        String openTabel= "UNLOCK TABLES";

        try {
            connection.setAutoCommit(false);

            //Route aanmaken
            PreparedStatement AanmakenRouteStmt = connection.prepareStatement(updateRouteStatus);
            AanmakenRouteStmt.setInt(1, route.getRouteID());
            AanmakenRouteStmt.executeUpdate();

            //orders tabel op slot zetten
            PreparedStatement tabellenOpslotStmt = connection.prepareStatement(lockTabellen);
            tabellenOpslotStmt.executeUpdate();


            //per bestelling de Status en LastEditedWhen kolommen aanpassen
            for (Order bestelling: bestellingen) {
                PreparedStatement orderAanpassenStmt = connection.prepareStatement(aanpassenOrder);

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());//ophalen van huidige datum

                orderAanpassenStmt.setDate(1, Date.valueOf(formatter.format(date)));
                orderAanpassenStmt.setInt(2, bestelling.getOrderID());//get orderID
                orderAanpassenStmt.executeUpdate();
            }


            //alle tabellen openen
            PreparedStatement tabellenOpenenStmt = connection.prepareStatement(openTabel);
            tabellenOpenenStmt.executeUpdate();

            connection.commit();
            System.out.println("commit");
            isAangepast=true;
        } catch(SQLException sqle){
            System.out.println("cause: " + sqle.getMessage());
            try {
                connection.rollback();//proberen om alles terug te draaien
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("rollback ging verkeerd: " + throwables.getMessage());
            }
        }finally {
            DatabaseConnectie.verbindingSluiten();
        }


        return isAangepast;
    }



}