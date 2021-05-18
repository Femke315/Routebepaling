import com.mysql.cj.protocol.Resultset;
import jdk.jshell.Snippet;


import java.sql.*;
import java.util.*;


public class SQLqueries {
    private static Connection connection;

    //ongeordende lijst met alle routes in een provincie
    public void getOrdersVanProvincie(String provincie){
        connection=DatabaseConnectie.getConnection();

        boolean isgeautoriseerd = true;


        if(isgeautoriseerd)//Check autorisatie?
        {
            //create statement/query
            String query = "SELECT OrderID FROM orders o INNER JOIN people p ON o.KlantID=p.PersonID WHERE p.postcode IN (" +
                    "SELECT PostCodePK FROM postcode WHERE provincie = ? ) limit 100";

            try (
                    //maal er een prepared statment + connectie
                    PreparedStatement stmt = connection.prepareStatement(query))
            {
                stmt.setString(1, provincie);//parameter toevoegen in query
                try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                    while(rs.next()) {
                        System.out.println("OrderID: " + rs.getInt("OrderID"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        DatabaseConnectie.verbindingSluiten();
    }

    //De geordende lijst van routes ophalen voor het routeoverzicht
    public void getRoutes(){
        connection=DatabaseConnectie.getConnection();
        boolean isgeautoriseerd = true;
        boolean isSorteerder= true;
        boolean isBezorger = false;
        boolean isManager = false;
        String[] routes = new String[5];//max aantal routes per pagina?anders arraylist gebruiken
        String status;

        if(isgeautoriseerd)//Check autorisatie?
        {
            //create statement/query
            String query = "SELECT RouteID, Provincie, Status, AantalPakketten, ReisTijd, Afstand From route WHERE Status=?";
            int iterationNum = 0;


            if(isSorteerder){
                status= "Klaar voor sorteren";
            }else if(isBezorger){
                status = "Klaar voor bezorging";
            }else{
                //als het dus de manager is
                status = "'Klaar voor sorteren', 'Klaar voor bezorging'";
                query = "SELECT RouteID, Provincie, Status, AantalPakketten From route WHERE Status IN (?)";
            }


            //maak er een prepared statment + connectie
            try (PreparedStatement stmRoutes  = connection.prepareStatement(query)) {
                stmRoutes.setString(1, status);//parameter toevoegen in query

                //data ontvangen---------------------
                try (ResultSet rs = stmRoutes.executeQuery()) {
                    while (rs.next()) {
                        String routeZin = "";

                        if(isSorteerder){
                            routeZin = "RouteID: " + rs.getInt("RouteID")  + ", Status: " + rs.getString("Status") + ", aantal pakketten: " + rs.getInt("AantalPakketten");
                        }else{
                            routeZin = "RouteID: " + rs.getInt("RouteID") + ", Provincie: " + rs.getString("Provincie") + ", Status: " + rs.getString("Status")+ ", reistijd: " + rs.getString("ReisTijd") + ", Afstand: " + rs.getString("Afstand");
                        }

                        routes[iterationNum] = routeZin;
                        iterationNum++;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DatabaseConnectie.verbindingSluiten();

        //laat opgehaalde gegevens zien
        for (int i = 0; i < routes.length; i++) {
            //laat alleen de routes zien
            if(routes[i] != null){
                System.out.println(routes[i]);
            }
        }
    }

    //functie om één route te laten zien
    public void showRoute(int routeID){
        connection=DatabaseConnectie.getConnection();
        String beginEindPunt= "Centrale opslag NerdyGadgets";
        ArrayList<String> route= new ArrayList<String>();
        route.add(beginEindPunt);

        String query = "SELECT o.OrderID, volgordeID FROM orders o INNER JOIN routelines r ON o.OrderID=r.OrderID WHERE r.RouteID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
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


        System.out.println(route);
        DatabaseConnectie.verbindingSluiten();
    }


    //Andere hulp methodes-------------------------------------------------------------------------------------------
    public void getProducts(int productID){
        connection=DatabaseConnectie.getConnection();
        //+voorraad, dus met stockitemholdings tabel
        String query = " SELECT si.StockitemID, si.StockItemName, QuantityOnHand From StockItems si LEFT JOIN stockitemholdings sh ON si.StockItemID=sh.StockItemID WHERE si.StockitemID=?";

        try (//try with resource close statements
             //maak er een prepared statment + connectie
             PreparedStatement stmt = connection.prepareStatement(query))
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

    //gegevens van één persoon ophalen voor accountpagina
    public void getPerson(int personID){
        connection=DatabaseConnectie.getConnection();

        String query = "SELECT PersonID, FullName, isStockManager, isStockSorter, isDeliverer, Emailaddress, PhoneNumber, pe.postcode, po.provincie, po.Longitude, po.Latitude FROM people pe INNER JOIN postcode po ON pe.postcode=po.PostCodePK WHERE PersonID=?";
        ArrayList<String> persoon = new ArrayList<String>();

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, personID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
//                    System.out.print("PersonID: " + rs.getInt("PersonID"));
//                    System.out.print(", Fullname: " + rs.getString("FullName"));
//                    System.out.print(", Emailadress: " + rs.getString("Emailaddress"));
//                    System.out.print(", provincie: " + rs.getString("Provincie"));
//                    System.out.println(", postcode: " + rs.getString("postcode"));
                    String persoonsGegevens= "PersonID: " + rs.getInt("PersonID") + ", Fullname: " + rs.getString("FullName") + ", Emailadress: " + rs.getString("Emailaddress")+ ", provincie: " + rs.getString("Provincie")+ ", postcode: " + rs.getString("postcode") + ", longitude:latitude: " + rs.getString("Longitude")+" : " + rs.getString("Latitude");
                    persoon.add(persoonsGegevens);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnectie.verbindingSluiten();

        //laat opgehaalde gegevens zien
        System.out.println(persoon);
    }



    //Voor bezorgroute scherm
    /*public ArrayList<ArrayList<String>> getOrders(int routeID){


//        String route= "route#"+routeID;
        // create our map
        Map<Order, List<Orderline>> route = new HashMap<>();

        // populate it, voor elke bestelling in een route!!
        //create statement/query
        String query = "SELECT OrderID FROM orders WHERE RouteID = ? ) limit 100";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.println("OrderID: " + rs.getInt("OrderID"));

                    //per key:value
                    List<Orderline> bestellingen = new ArrayList<>();
                    bestellingen.add(new Orderline(73596));
                    route.put(new Order(rs.getInt("OrderID")), bestellingen);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // read from it
        List<Orderline> bestellingenVanOrder = route[routeID];
        Orderline bob1 = bestellingenVanOrder[0];
        Orderline bob2 = bestellingenVanOrder[1];


        DatabaseConnectie.verbindingMaken();


        ArrayList<String> lijstOrders = new ArrayList<String>(); // maken ArrayList


        //haal alle orders op van in een route
        String query = "SELECT OrderID FROM orders WHERE RouteID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.println("OrderID: " + rs.getInt("OrderID"));
                    lijstOrders.add("OrderID: " + rs.getInt("OrderID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //haal alle producten op van een order
        for (String ordernummer:lijstOrders) {

        }


        DatabaseConnectie.verbindingSluiten();
    }*/

    public void getOrders(int personID){
        DatabaseConnectie.verbindingMaken();

        boolean isgeautoriseerd = true;
        PreparedStatement stmOrders = null;
        ArrayList<String> lijstOrders = new ArrayList<String>(); // maken ArrayList



        if(isgeautoriseerd)//CHeck autorisatie?
        {
            //create statement/query
            String query = "SELECT OrderID FROM orders o INNER JOIN people p ON o.KlantID=p.PersonID WHERE p.PersonID =?";

            try (
                    //maal er een prepared statment + connectie
                    PreparedStatement stmt = connection.prepareStatement(query))
            {
                stmt.setInt(1, personID);//parameter toevoegen in query
                try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                    while(rs.next()) {
                        System.out.println("OrderID: " + rs.getInt("OrderID"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        DatabaseConnectie.verbindingSluiten();
    }

    public void getOrderlines(int orderID){
        DatabaseConnectie.verbindingMaken();
        //create statement/query
        String query = "SELECT OrderLineID, StockItemID FROM orderlines WHERE OrderID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, orderID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.print("OrderLineID: " + rs.getInt("OrderLineID"));
                    System.out.println(" ,StockItemID: " + rs.getInt("StockItemID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnectie.verbindingSluiten();

    }


    public void getRoutelines(int routeID){
        DatabaseConnectie.verbindingMaken();
        String query = "SELECT * FROM routelines WHERE RouteID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, routeID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.print("PersonID: " + rs.getInt("PersonID"));
                    System.out.print("OrderID: " + rs.getInt("OrderID"));
                    System.out.println("VolgorderID: " + rs.getInt("VolgordeID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnectie.verbindingSluiten();

    }



}
