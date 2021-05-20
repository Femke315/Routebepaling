
import java.sql.*;
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

    //voor het algoritme
    //ongeordende lijst met alle routes in een provincie
    public ArrayList<Order> getOrdersVanProvincie(String provincie){
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

    //De al geordende lijst van routes ophalen voor het routeoverzicht
    public ArrayList<Route> getRoutes(String status){
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

    //functie om één route te laten zien
    public ArrayList<String> showRoute(int routeID){
        connection=DatabaseConnectie.getConnection();
        ArrayList<String> route= new ArrayList<String>();

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
        }

        DatabaseConnectie.verbindingSluiten();

        return route;
    }

    //berekende route in database opslaan in een transactie
    public void toevoegenRoute(ArrayList<Order> route) throws SQLException {
        connection= DatabaseConnectie.getConnection();

        /*
        * Een route aanmaken
        *
        *
        * */


        String stmt= "";

        try {
            connection.setAutoCommit(false);
            PreparedStatement firstStatement = connection .prepareStatement("firstQuery");
            firstStatement.executeUpdate();
            PreparedStatement secondStatement = connection .prepareStatement("secondQuery");
            secondStatement.executeUpdate();
            connection.commit();
        } catch(SQLException sqle){
            connection.rollback();
            sqle.getCause();
        }

        DatabaseConnectie.verbindingSluiten();
    }

}
