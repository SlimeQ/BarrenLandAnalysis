import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

import barrenlandanalysis.*;

public class BarrenLandAnalyzerTests {

	@Test
	public void shouldParseSingleInput() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);
		List<Rectangle> barrenRectangles = analyzer.parseBarrenLandInput("{\"0 0 2 2\"}");

		assertEquals(1, barrenRectangles.size(), "Input should yield 1 rectangle");
	}

	@Test
	public void shouldParseMultipleInputs() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);
		List<Rectangle> barrenRectangles = analyzer.parseBarrenLandInput("{\"0 0 2 2\"}, {\"6 6 8 8\"}");

		assertEquals(2, barrenRectangles.size(), "Input should yield 2 rectangles");
	}

	@Test
	public void shouldParseCorrectRectangle() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);
		List<Rectangle> barrenRectangles = analyzer.parseBarrenLandInput("{\"0 0 2 2\"}");

		assertEquals(1, barrenRectangles.size(), "Input should yield 1 rectangle");

		Rectangle rect = barrenRectangles.get(0);

		assertEquals(0, rect.getX(), "Rectangle origin X should be 0");
		assertEquals(0, rect.getY(), "Rectangle origin Y should be 0");
		assertEquals(3, rect.getWidth(), "Rectangle width should be 3");
		assertEquals(3, rect.getHeight(), "Rectangle height should be 3");
	}

	@Test
	public void shouldMarkBarrenLand() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);

		List<Rectangle> barrenRectangles = new ArrayList<Rectangle>();
		barrenRectangles.add(new Rectangle(0, 0, 3, 3));

		analyzer.markBarrenLand(barrenRectangles);

		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				if (x < 3 && y < 3) {
					assertEquals(-1, analyzer.landGrid[x][y], "landGrid values from (0,0) to (3,3) should be -1 for barren");
				} else {
					assertEquals(0, analyzer.landGrid[x][y], "landGrid values not inside rectangle should be 0 for untouched");
				}
			}
		}
	}

	@Test
	public void shouldMarkAllLandFertile() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);

		analyzer.fillFertileRegion(5, 5, 1);

		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				assertEquals(1, analyzer.landGrid[x][y], "landGrid should be filled with region 1");
			}
		}
	}

	@Test
	public void shouldMarkMultipleFertileRegions() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);

		// draw a barren line down the middle
		for (int i=0; i<10; i++) {
			analyzer.landGrid[5][i] = -1;
		}

		analyzer.markFertileLand();

		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				if (x < 5) {
					assertEquals(1, analyzer.landGrid[x][y], "left region should be marked 1");	
				} else if (x == 5) {
					assertEquals(-1, analyzer.landGrid[x][y], "center line should be barren (-1)");
				} else if (x > 5) {
					assertEquals(2, analyzer.landGrid[x][y], "right region should be marked 2");
				}
			}
		}
	}

	@Test
	public void shouldReturnCorrectRegionAreas() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);
		analyzer.landGrid = getSplitGridTestData(10, 10);

		Map<Integer, Integer> regionAreas = analyzer.calculateAreasOfFertileRegions();
		assertEquals(50, regionAreas.get(1).intValue(), "There should be 50 square meters of region 1");
		assertEquals(40, regionAreas.get(2).intValue(), "There should be 40 square meters of region 2");
	}

	@Test
	public void shouldCreateVisualizationFile() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);
		analyzer.landGrid = getSplitGridTestData(10, 10);

		// delete old file if it exists
		File file = new File("test_visualization.bmp");
		file.delete();

		// create visualization file
		analyzer.visualizeLandToFile("test_visualization");
		assertEquals(true, file.exists(), "An image file should have been created.");

		// cleanup
		file.delete();
	}

	@Test
	public void shouldAnalyzeTextAndReturnText() {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(10, 10);
		String outString = analyzer.analyzeInputString("{\"1 0 8 9\"}, {\"0 0 9 0\"}");

		// verify text output
		assertEquals("9 9", outString, "Text output was incorrect.");
	}

	int[][] getSplitGridTestData(int width, int height) {
		int[][] landGrid = new int[width][height];

		// left is 1, center is barren, right is 2
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {	
				if (x < 5) {
					landGrid[x][y]	= 1;
				} else if (x == 5) {
					landGrid[x][y]	= -1;
				} else if (x > 5) {
					landGrid[x][y]	= 2;
				}
			}
		}

		return landGrid;
	}
}