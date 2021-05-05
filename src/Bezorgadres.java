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

    public String getNaam() {
        return naam;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


//    public static double afstandBerekenen(double lat1, double lon1, double lat2, double lon2) {
//        if ((lat1 == lat2) && (lon1 == lon2)) {
//            return 0;
//        }
//        else {
//            double theta = lon1 - lon2;
//            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
//            dist = Math.acos(dist);
//            dist = Math.toDegrees(dist);
//            dist = dist * 60 * 1.1515;
//
//            dist = dist / 1.609344;//omzetten naar kilometers
//            return (dist);
//        }
//    }

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    public static double calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (6371 * c);
    }

    public double afstandBerekenen(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (double) 6371 * c;
    }

}
