import java.util.Scanner;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BarrenLandAnalysis {
    public static void main( String[] args ) {
        BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(400, 600);

        // get and parse input from STDIN
        Scanner scan = new Scanner(System.in);
        String inputString = scan.nextLine().trim();
        List<Rectangle> barrenRectangles = analyzer.parseBarrenLandInput(inputString);

        // fill land grid appropriately
        analyzer.markBarrenLand(barrenRectangles);
        analyzer.markFertileLand();

        // calculate area in square meters for all fertile regions
        Map<Integer, Integer> regionAreas = analyzer.calculateAreasOfFertileRegions();

        // sort region areas
        List<Integer> sortedAreas = new ArrayList<Integer>(regionAreas.values());
        Collections.sort(sortedAreas);

        // output text according to specifications
        String outputString = "";
        for (Integer area : sortedAreas) {
            outputString += Integer.toString(area);
            outputString += " ";
        }
        System.out.println(outputString.trim());

        // Visualize for debug purposes
        if (args.length > 0 && args[0].equals("-visualize")) {
            String visualizationFileName = "barren_land_visualization";
            if (args[1].equals("")) {

            }
            analyzer.visualizeLand("barren_land_visualization");
        }

        System.exit( 0 ); //success
    }
}