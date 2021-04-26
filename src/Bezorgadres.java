public class Bezorgadres {
    private String naam;
    private double latitude;
    private double longitude;
    private String postcode;


    public Bezorgadres(String naam, double latitude, double longitude, String postcode){
        this.naam=naam;
        this.latitude=latitude;
        this.longitude=longitude;
        this.postcode=postcode;
    }



    private static double afstandBerekenen(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344;//omzetten naar kilometers
            return (dist);
        }
    }
}
