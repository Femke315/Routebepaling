
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void toevoegenRoute(ArrayList<Order> route) {
        connection= DatabaseConnectie.getConnection();


        int routeID=0;
//
//        //prepared statement maken
//        try{
//            ResultSet rs =  connection.createStatement().executeQuery("SELECT MAX(RouteID) FROM route");
//            while ( rs.next() ) {
//                routeID = rs.getInt("RouteID");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /*
        *Een route in deze methode aanmaken (dus alle not-null kolommen invullen van de route tabel)
        *benodidge tabellen HELEMAAL opslot zetten
        *Laatst toegevoegde routeID ophalen.
        *routeID toevoegen aan orders + lastEditedWhen aanpassen + Status veranderen (klaar voor sorteren)
        *routelines toevoegen met behulp van de index van de arraylist, routeID en orderid.
        *Alle tabellen weer openen.
        * */


        String toevoegenRoute = "INSERT INTO route (AantalPakketten, Afstand, Status, Provincie) VALUES (?,?,?,?)";
        String lockRouteTabel= "LOCK TABLE route WRITE";//dieper in kijken of deze lock nodig is
        String lockOrdersTabel= "LOCK TABLE orders WRITE";//dieper in kijken of deze lock nodig is
        String lockRoutelinesTabel= "LOCK TABLE routelines WRITE";//dieper in kijken of deze lock nodig is

//        String ophalenRouteID= "SELECT MAX(RouteID) FROM route";
        String aanpassenOrder="UPDATE orders SET LastEditedWhen=?, routeID=?, Status= 'Klaar voor sorteren' WHERE OrderID=?";
        String toevoegenRouteline= "INSERT INTO Routelines (VolgordeID, RouteID, OrderID) VALUES (?,?,?)";
        String openTabellen= "UNLOCK TABLES";

        try {
            connection.setAutoCommit(false);
            PreparedStatement firstStatement = connection.prepareStatement(toevoegenRoute, Statement.RETURN_GENERATED_KEYS);
            firstStatement.setInt(1, route.size()-2);//-2 begin/eind punt
            firstStatement.setDouble(2, 200.00);//HIER MOET NOG DE AFSTAND VARIABLE KOMEN
            firstStatement.setString(3, "Klaar voor sorteren");
            firstStatement.setString(4,"onbekend");
            firstStatement.executeUpdate();
            ResultSet rs = firstStatement.getGeneratedKeys();//haal primary key op van de gemaakte route
            if(rs.next())
                routeID = rs.getInt(1);
            System.out.println("eerste query");

            PreparedStatement secondStatement = connection.prepareStatement(lockRouteTabel);
            secondStatement.executeUpdate();
            System.out.println("tweede query");

            PreparedStatement lockStatement = connection.prepareStatement(lockOrdersTabel);
            lockStatement.executeUpdate();

            PreparedStatement locksecondStatement = connection.prepareStatement(lockRoutelinesTabel);
            locksecondStatement.executeUpdate();
//
//            PreparedStatement thirdStatement = connection.prepareStatement(ophalenRouteID);
//            thirdStatement.execute();//ROUTEID OPHALEN


            for (int i = 0; i < route.size(); i++) {
                PreparedStatement fourthStatement = connection.prepareStatement(aanpassenOrder);
                Date date= (Date) Calendar.getInstance().getTime();
                fourthStatement.setDate(1, date);//GEtCURRENT DATE
                fourthStatement.setInt(2, routeID);
                fourthStatement.setInt(3,route.get(i).getOrderID());
                fourthStatement.executeUpdate();
                System.out.println("derde query: " + i);

                PreparedStatement fifthStatement = connection.prepareStatement(toevoegenRouteline);
                fifthStatement.setInt(1, i);//het begint bij nul, dat is dan de opslag
                fifthStatement.setInt(2,routeID);
                fifthStatement.setInt(3,route.get(i).getOrderID());
                fifthStatement.executeUpdate();
                System.out.println("vierde query: " + i);
            }

            PreparedStatement sixthStatement = connection.prepareStatement(openTabellen);
            sixthStatement.executeUpdate();
            System.out.println("vijfde query");

            connection.commit();
            System.out.println("commit");
        } catch(SQLException sqle){
            try {
                connection.rollback();//proberen om alles terug te draaien
                System.out.println("cause: " + sqle.getMessage());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            sqle.getCause();
        }finally {
            DatabaseConnectie.verbindingSluiten();
        }

    }

}
