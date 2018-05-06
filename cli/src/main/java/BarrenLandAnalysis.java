package barrenlandanalysis;

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

        // get input string from STDIN
        Scanner scan = new Scanner(System.in);
        String inputString = scan.nextLine().trim();

        // analyze and output
        String outputString = analyzer.analyzeInputString(inputString);
        System.out.println(outputString);

        // Visualize analyzer for debug purposes
        if (args.length > 0 && args[0].equals("-visualize")) {
            String visualizationFileName = "barren_land_visualization";
            if (args[1].equals("")) {

            }
            analyzer.visualizeLandToFile("barren_land_visualization");
        }

        System.exit( 0 ); //success
    }
}