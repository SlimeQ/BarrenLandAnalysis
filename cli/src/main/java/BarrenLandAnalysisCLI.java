package barrenlandanalysis;

import java.util.Scanner;
import barrenlandanalysis.BarrenLandAnalyzer;

public class BarrenLandAnalysisCLI {
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
            if (!args[1].equals("")) {
                visualizationFileName = args[1];
            }
            analyzer.visualizeLandToFile(visualizationFileName);
        }

        System.exit( 0 ); //success
    }
}