import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    private String orderID;
    private int routeID;
    private int klantID;
    private String status;

    public Order(int orderID){
        DatabaseConnectie.verbindingMaken();
        this.orderID= Integer.toString(orderID);

        //create statement/query
//        String query = "SELECT OrderID, RouteID, Status, KlantID FROM orders o INNER JOIN people p ON o.KlantID=p.PersonID WHERE p.PersonID =?";
        String query = "SELECT OrderID, Status, KlantID, RouteID FROM orders WHERE OrderID =?";


        //maal er een prepared statment + connectie
        try ( PreparedStatement stmt = DatabaseConnectie.getConnection().prepareStatement(query))
        {
            stmt.setInt(1, orderID);//parameter toevoegen in query

            //ontvangen data
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    System.out.println("OrderID: " + rs.getInt("OrderID"));
                    this.klantID=rs.getInt("KlantID");
                    this.status=rs.getString("Status");
                    this.routeID=rs.getInt("RouteID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();
    }

    public String getOrderID() {
        return orderID;
    }
}