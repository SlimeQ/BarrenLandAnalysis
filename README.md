# BarrenLandAnalysis

## Dependencies
A working Java installation is required.

## Structure
This project contains 3 subprojects:
- cli
  - Reads from STDIN, writes to STDOUT
  - Optionally, generates an image file to visualize the resulting land grid.
  - Usage: `$ java -jar BarrenLandAnalysis-cli.jar -visualize visualization_file.bmp`
- server
  - Provides a web interface for using the Barren Land Analyzer.
- shared
  - Contains `BarrenLandAnalyzer` class that can be used to process input.
  - Used in both the CLI and the server.
