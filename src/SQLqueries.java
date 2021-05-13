import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class SQLqueries {
    private static Connection connection;

    public SQLqueries(){
        this.connection=DatabaseConnectie.getConnection();
    }

    public Resultset getOrders(String provincie){
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
//
//            try {
//                Statement statementPersonID = connection.createStatement();
//                ResultSet rsPersonID = statementPersonID.executeQuery("SELECT MAX(PersonID) FROM people");
//                rsPersonID.next();
//            } catch (SQLException throwables) {
//                System.out.println("getOrders() = Ophalen van orders is niet gelukt:");
//                System.out.println(throwables.toString());
//                System.out.println();
//            }
        }


        DatabaseConnectie.verbindingSluiten();
    }

    public Resultset getOrderlines(int orderID){
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
    }

    public Resultset getRoutes(String status){
        //create statement/query
        String query = "SELECT RouteID, Provincie, Status From route WHERE Status=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, status);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.print("RouteID: " + rs.getInt("RouteID"));
                    System.out.print(", Provincie: " + rs.getString("Provincie"));
                    System.out.println(", Status: " + rs.getString("Status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Resultset getpeople(int personID){
//+postcode, dus met postcode tabel
        String query = "SELECT PersonID, FullName, isStockManager, isStockSorter, isDeliverer, Emailaddress, PhoneNumber, pe.postcode, po.provincie FROM people pe INNER JOIN postcode po ON pe.postcode=po.PostCodePK WHERE PersonID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, personID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.println("PersonID: " + rs.getInt("PersonID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Resultset getRoutelines(int routeID){
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
    }

    public Resultset getProducts(int productID){
//+voorraad, dus met stockitemholdings tabel
        String query = " SELECT si.StockitemID, si.StockItemName, QuantityOnHand From StockItems si LEFT JOIN stockitemholdings sh ON si.StockItemID=sh.StockItemID WHERE si.StockitemID=?";

        try (
                //maal er een prepared statment + connectie
                PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, productID);//parameter toevoegen in query
            try (ResultSet rs = stmt.executeQuery()) {//ontvangen data
                while(rs.next()) {
                    System.out.print("StockitemID: " + rs.getInt("StockitemID"));
                    System.out.print("description: " + rs.getInt("StockItemName"));
                    System.out.println("QuantityOnHand: " + rs.getInt("Quantity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
