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








