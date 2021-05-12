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

        if(isgeautoriseerd)//en geautoriseerd
        {
            String query = "SELECT * from orders WHERE CustomerID = limit 100";
            try(
                Statement stmt = connection.createStatement();
                stmt.setInt(1, provincie)
                ResultSet rs = stmt.executeQuery(query);
            ) {
                while(rs.next()){
                    //Display values
                    System.out.print("OrderID: " + rs.getInt("OrderID"));
                    System.out.print(", CustomerID: " + rs.getInt("CustomerID"));
                    System.out.print(", RouteID: " + rs.getInt("RouteID"));
                    System.out.print(", Status: " + rs.getString("Status"));
                    System.out.println(", Comments: " + rs.getString("Comments"));
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

    public Resultset getOrderlines(){

    }

    public Resultset getRoutes(){

    }
    public Resultset getpeople(){
//+postcode, dus met postcode tabel
    }
    public Resultset getRoutelines(){

    }

    public Resultset getProducts(){
//+voorraad, dus met stockitemholdings tabel
    }
}
