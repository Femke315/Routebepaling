import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {



        Tour berlin = new Tour();
        berlin.parseLine("NAME: berlin52");
        berlin.parseLine("TYPE: TSP");
        berlin.parseLine("COMMENT: 52 locations in Berlin (Groetschel)");
        berlin.parseLine("DIMENSION: 20");
        berlin.parseLine("EDGE_WEIGHT_TYPE: EUC_2D");
        berlin.parseLine("NODE_COORD_SECTION");
        berlin.parseLine("1 565.0 575.0");
        berlin.parseLine("2 25.0 185.0");
        berlin.parseLine("3 345.0 750.0");
        berlin.parseLine("4 945.0 685.0");
        berlin.parseLine("5 845.0 655.0");
        berlin.parseLine("6 880.0 660.0");
        berlin.parseLine("7 25.0 230.0");
        berlin.parseLine("8 525.0 1000.0");
        berlin.parseLine("9 580.0 1175.0");
        berlin.parseLine("10 650.0 1130.0");
        berlin.parseLine("11 1605.0 620.0");
        berlin.parseLine("12 1220.0 580.0");
        berlin.parseLine("13 1465.0 200.0");
        berlin.parseLine("14 1530.0 5.0");
        berlin.parseLine("15 845.0 680.0");
        berlin.parseLine("16 725.0 370.0");
        berlin.parseLine("17 145.0 665.0");
        berlin.parseLine("18 415.0 635.0");
        berlin.parseLine("19 510.0 875.0");
        berlin.parseLine("20 560.0 365.0");
//        berlin.parseLine("21 300.0 465.0");
//        berlin.parseLine("22 520.0 585.0");
//        berlin.parseLine("23 480.0 415.0");
//        berlin.parseLine("24 835.0 625.0");
//        berlin.parseLine("25 975.0 580.0");
//        berlin.parseLine("26 1215.0 245.0");
//        berlin.parseLine("27 1320.0 315.0");
//        berlin.parseLine("28 1250.0 400.0");
//        berlin.parseLine("29 660.0 180.0");
//        berlin.parseLine("30 410.0 250.0");
//        berlin.parseLine("31 420.0 555.0");
//        berlin.parseLine("32 575.0 665.0");
//        berlin.parseLine("33 1150.0 1160.0");
//        berlin.parseLine("34 700.0 580.0");
//        berlin.parseLine("35 685.0 595.0");
//        berlin.parseLine("36 685.0 610.0");
//        berlin.parseLine("37 770.0 610.0");
//        berlin.parseLine("38 795.0 645.0");
//        berlin.parseLine("39 720.0 635.0");
//        berlin.parseLine("40 760.0 650.0");
//        berlin.parseLine("41 475.0 960.0");
//        berlin.parseLine("42 95.0 260.0");
//        berlin.parseLine("43 875.0 920.0");
//        berlin.parseLine("44 700.0 500.0");
//        berlin.parseLine("45 555.0 815.0");
//        berlin.parseLine("46 830.0 485.0");
//        berlin.parseLine("47 1170.0 65.0");
//        berlin.parseLine("48 830.0 610.0");
//        berlin.parseLine("49 605.0 625.0");
//        berlin.parseLine("50 595.0 360.0");
//        berlin.parseLine("51 1340.0 725.0");
//        berlin.parseLine("52 1740.0 245.0");
        berlin.parseLine("EOF");
        double startTime = System.currentTimeMillis();
        NearestNeighborSolver berlinSolver = new NearestNeighborSolver(berlin);// alle tour attributen worden ingevoerd
        berlinSolver.getShortestTourMetBegin(0);

        double time = System.currentTimeMillis() - startTime;

        System.out.println("Time taken for nearest neighbour IN MILLISECONDS: " + time);
        System.out.println("Time taken for nearest neighbour IN SECONDS: " + time/1000);
    }
}
