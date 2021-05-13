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
