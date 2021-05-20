
import java.sql.*;
import java.util.*;


public class SQLqueries {
    private static Connection connection;

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

    //voor het algoritme
    //ongeordende lijst met alle routes in een provincie
    public ArrayList<Order> getOrdersVanProvincieMetArray(String provincie){
        connection=DatabaseConnectie.getConnection();
        ArrayList<Order> ordersLijst= new ArrayList<>();

        boolean isgeautoriseerd = true;


        if(isgeautoriseerd)//Check autorisatie?
        {
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

        }


        DatabaseConnectie.verbindingSluiten();
        return ordersLijst;
    }

    //De geordende lijst van routes ophalen voor het routeoverzicht
    public ArrayList<String> getRoutesMetArray(String status){
        connection=DatabaseConnectie.getConnection();
        ArrayList<String> routes= new ArrayList<>();

        if(true)//Check autorisatie met distributiemedewerker
        {
            //create statement/query
            String query = "SELECT RouteID, Provincie, Status, AantalPakketten, ReisTijd, Afstand From route WHERE Status=?";

            status= "afgerond";
            //maak er een prepared statment + connectie
            try (PreparedStatement stmRoutes  = connection.prepareStatement(query)) {
                stmRoutes.setString(1, status);//parameter toevoegen in query

                //data ontvangen---------------------
                try (ResultSet rs = stmRoutes.executeQuery()) {
                    while (rs.next()) {
                        String routeZin = "";

                        //route object hier gebruiken!
                        if(true){
                            //route objecten
                            routeZin = "RouteID: " + rs.getInt("RouteID")  + ", Status: " + rs.getString("Status") + ", aantal pakketten: " + rs.getInt("AantalPakketten");
                        }else{
                            routeZin = "RouteID: " + rs.getInt("RouteID") + ", Provincie: " + rs.getString("Provincie") + ", Status: " + rs.getString("Status")+ ", reistijd: " + rs.getString("ReisTijd") + ", Afstand: " + rs.getString("Afstand");
                        }

                        routes.add(routeZin);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DatabaseConnectie.verbindingSluiten();
        return routes;
    }

    //functie om één route te laten zien
    public ArrayList<String> showRouteMetArray(int routeID){
        connection=DatabaseConnectie.getConnection();
        String beginEindPunt= "Centrale opslag NerdyGadgets";
        ArrayList<String> route= new ArrayList<String>();
        route.add(beginEindPunt);

        String query = "SELECT o.OrderID, volgordeID FROM orders o INNER JOIN routelines r ON o.OrderID=r.OrderID WHERE r.RouteID=?";

        //prepared statement maken
        try (PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query

            //opgevraagde data ontvangen
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    System.out.println("Opgehaald OrderID: " + rs.getInt("OrderID"));
                    //order toevoegen op een specifieke index
                    route.add(rs.getInt("volgordeID"), "OrderID: " + rs.getInt("OrderID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            route.add(beginEindPunt);
        }

        DatabaseConnectie.verbindingSluiten();

        return route;
    }


}
