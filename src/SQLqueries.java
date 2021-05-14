import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;

public class SQLqueries {
    private static Connection connection;

    public SQLqueries(){
        this.connection=DatabaseConnectie.getConnection();
    }

    public void getOrders(String provincie){
        DatabaseConnectie.verbindingMaken();

        boolean isgeautoriseerd = true;
        PreparedStatement stmOrders = null;



        if(isgeautoriseerd)//CHeck autorisatie?
        {
            //create statement/query
            String query = "SELECT OrderID FROM orders o INNER JOIN people p ON o.CustomerID=p.PersonID WHERE p.postcode IN (" +
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

    public void getOrderlines(int orderID){
        DatabaseConnectie.verbindingMaken();
        //create statement/query
        String query = "SELECT OrderLineID FROM orderlines WHERE OrderID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, orderID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.println("OrderLineID: " + rs.getInt("OrderLineID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnectie.verbindingSluiten();

    }

    public void getRoutes(String status){
//        connection=DatabaseConnectie.getConnection();
        //create statement/query
        String query = "SELECT RouteID, Provincie, Status From route WHERE Status=?";
        String[] routes= new String[5];//max aantal routes per pagina?anders arraylist gebruiken
        int iterationNum=0;

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            stmt.setString(1, status);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    String routeZin = "RouteID: " + rs.getInt("RouteID") + ", Provincie: " + rs.getString("Provincie") + ", Status: " + rs.getString("Status");
                    routes[iterationNum]= routeZin;
                    iterationNum++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnectie.verbindingSluiten();

        //laat opgehaalde gegevens zien
        for (int i = 0; i < routes.length; i++) {
            System.out.println(routes[i]);
        }
    }

    public void getpeople(int personID){//kan nu dus nog maar 1 persoon ophalen
//+postcode, dus met postcode tabel
        String query = "SELECT PersonID, FullName, isStockManager, isStockSorter, isDeliverer, Emailaddress, PhoneNumber, pe.postcode, po.provincie FROM people pe INNER JOIN postcode po ON pe.postcode=po.PostCodePK WHERE PersonID=?";
        ArrayList<String> persoon = new ArrayList<String>();

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            stmt.setInt(1, personID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
//                    System.out.print("PersonID: " + rs.getInt("PersonID"));
//                    System.out.print(", Fullname: " + rs.getString("FullName"));
//                    System.out.print(", Emailadress: " + rs.getString("Emailaddress"));
//                    System.out.print(", provincie: " + rs.getString("Provincie"));
//                    System.out.println(", postcode: " + rs.getString("postcode"));
                    String persoonsGegevens= "PersonID: " + rs.getInt("PersonID") + ", Fullname: " + rs.getString("FullName") + ", Emailadress: " + rs.getString("Emailaddress")+ ", provincie: " + rs.getString("Provincie")+ ", postcode: " + rs.getString("postcode");
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
}
