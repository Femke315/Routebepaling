<<<<<<< HEAD
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    private String orderID;
    private int routeID;
    private int klantID;
    private String status;
    private double longitude;
    private double latitude;

    public Order(int orderID){
        DatabaseConnectie.verbindingMaken();
        this.orderID= Integer.toString(orderID);

        //create statement/query
//        String query = "SELECT OrderID, RouteID, Status, KlantID FROM orders o INNER JOIN people p ON o.KlantID=p.PersonID WHERE p.PersonID =?";
        String query = "SELECT OrderID, Status, KlantID, RouteID FROM orders WHERE OrderID =?";


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
=======
package src;
import java.util.ArrayList;

public class Order {

        public double x;
        public double y;
        public String postcode;
        public int orderID;
        public int routeID;

        public Order(String postcode, int orderID, double x, double y) {
            this.postcode = postcode;
            this.orderID = orderID;
            this.x = x;
            this.y = y;
        }

        public Order(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }








>>>>>>> origin/Distributiemedewerker_&_Route
