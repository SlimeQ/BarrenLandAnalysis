# BarrenLandAnalysis

## Dependencies
A working Java installation is required.

## Structure
This project contains 3 subprojects:
- cli
  - Reads from STDIN, writes to STDOUT.
  - Optionally, generates an image file to visualize the resulting land grid.
  - Usage: `$ java -jar BarrenLandAnalysis-cli.jar -visualize visualization_file.bmp`
- server
  - Provides a web interface for using the Barren Land Analyzer.
- shared
  - Contains `BarrenLandAnalyzer` class that can be used to process input.
  - Used in both the CLI and the server.

## Building
This project uses the gradle wrapper. To build, enter `$ ./gradlew build`

## Testing
Tests will be run automatically on build, but to force tests to run you can enter `$ ./gradlew test`

## Command Line Interface Usage
The cli package reads from STDIN and writes to STDOUT. Optionally, generates an image file to visualize the resulting land grid.

```$ java -jar cli/build/libs/BarrenLandAnalysis-cli.jar -visualize visualization_file.bmp```

## Server Usage
The server package exposes BarrenLandAnalyzer functionality via a web page. To start the server, enter `java -jar server/build/libs/BarrenLandAnalysis-server.jar`

The server listens on port 8080 by default. This is a Spring-Boot server with two endpoints:

- `/` Serves a homepage with an AJAX form to interact with the server.
- `/analyze`
  - Accepts a POST request containing only a raw BarrenLandAnalysis input string.
  - Returns a JSON object containing the computed output string and the visualization image as a Base64 string.
    Example:
    ```
    {
      "output":"22816 192608",
      "visualization":"data:image/png;base64, Qk22/AoAAAAAADYAAAAoAAAAkAEAAFgCAAABA..."
    }
    ```
