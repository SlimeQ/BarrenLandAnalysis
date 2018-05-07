package barrenlandanalysis;

import java.util.Scanner;
import barrenlandanalysis.BarrenLandAnalyzer;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;

public class BarrenLandAnalysisCLI {
    public static void main( String[] args ) {
        while (true) {
            BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(400, 600);

            String inputString = getInputString();

            // // get input string from STDIN
            // Scanner scan = new Scanner(System.in);
            // String inputString = scan.nextLine().trim();

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
        }
        // System.exit( 0 ); //success
    }

    public static String getInputString() {
        try {
            Terminal terminal = TerminalBuilder.builder()
                .jna(true)
                .system(true)
                .build();

            // raw mode means we get keypresses rather than line buffered input
            terminal.enterRawMode();
            NonBlockingReader reader = terminal.reader();

            String inputString = "";
            while (!isValidInputString(inputString)) {
                char newChar = (char)reader.read();
                if (newChar == '{') {
                    // reset input string
                    inputString = "{";
                } else {
                    inputString += newChar;
                }
                // System.out.print(newChar);
            }

            reader.close();
            terminal.close();

            return inputString;
        } catch (IOException e) {
            System.err.println("Failed to read from STDIN!");
        }
        return "";
    }

    public static boolean isValidInputString(String inputString) {
        if (inputString.length() >= 2) {
            if (inputString.substring(0, 1).equals("{")) {
                if (inputString.substring(inputString.length() - 1).equals("}")) {
                    return true;
                }
            }
        }
        return false;
    }
}