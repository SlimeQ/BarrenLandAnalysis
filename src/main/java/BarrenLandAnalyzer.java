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

class FertileRegionQuery {

    public int x, y, region;

    public FertileRegionQuery(int inX, int inY, int inRegion) {
        x = inX;
        y = inY;
        region = inRegion;
    }
}

public class BarrenLandAnalyzer {

    public int width = 400;
    public int height = 600;
    public int[][] landGrid;


    public BarrenLandAnalyzer(int inWidth, int inHeight) {
        width = inWidth;
        height = inHeight;
        landGrid = new int[width][height];
    }

    public List<Rectangle> parseBarrenLandInput(String inputString) {
        // Parse into distinct rectangle inputs
        String[] inputs = inputString.split("(\\{|\"|\\“|\\”|(,\\s*)|\\})+");

        // Create rectangles from inputs
        List<Rectangle> barrenRectangles = new ArrayList<Rectangle>();
        for (String barrenInput : inputs) {
            String[] coordinates = barrenInput.split(" ");
            if (coordinates.length == 4) {
                int bottomLeftX = Integer.parseInt(coordinates[0]);
                int bottomLeftY = Integer.parseInt(coordinates[1]);
                int topRightX = Integer.parseInt(coordinates[2]);
                int topRightY = Integer.parseInt(coordinates[3]);

                int width = (topRightX - bottomLeftX) + 1;
                int height = (topRightY - bottomLeftY) + 1;

                barrenRectangles.add(new Rectangle(bottomLeftX, bottomLeftY, width, height));
            }
        }

        return barrenRectangles;
    }

    public void markBarrenLand(List<Rectangle> barrenLand) {
        // mark all 
        for (Rectangle barrenPatch : barrenLand) {
            for (int y=0; y<barrenPatch.getHeight(); y++) {
                for (int x=0; x<barrenPatch.getWidth(); x++) {
                    int xCoord = (int)barrenPatch.getX() + x;
                    int yCoord = (int)barrenPatch.getY() + y;

                    landGrid[xCoord][yCoord] = -1;
                }
            }
        }
    }

    public void markFertileLand() {
        int currentRegion = 1;

        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                // only fill areas that haven't been touched yet
                if (landGrid[x][y] == 0) {
                    fillFertileRegion(x, y, currentRegion);
                    currentRegion++;
                }
            }
        }
    }

    public void fillFertileRegion(int x, int y, int region) {
        Stack<FertileRegionQuery> regionCheckStack = new Stack<FertileRegionQuery>();
        regionCheckStack.push(new FertileRegionQuery(x, y, region));

        while (!regionCheckStack.empty()) {
            FertileRegionQuery query = regionCheckStack.pop();

            // skip if out of bounds
            if (query.x < 0 || query.x >= width || query.y < 0 || query.y >= height)
                continue;

            // skip if already marked
            if (landGrid[query.x][query.y] != 0)
                continue;

            // mark region
            landGrid[query.x][query.y] = query.region;

            // propagate to adjacent units
            regionCheckStack.push(new FertileRegionQuery(query.x-1, query.y, query.region));
            regionCheckStack.push(new FertileRegionQuery(query.x+1, query.y, query.region));
            regionCheckStack.push(new FertileRegionQuery(query.x, query.y-1, query.region));
            regionCheckStack.push(new FertileRegionQuery(query.x, query.y+1, query.region));
        }
    }

    public Map<Integer, Integer> calculateAreasOfFertileRegions() {
        Map<Integer, Integer> regionAreas = new HashMap<Integer, Integer>();

        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                int region = landGrid[x][y];

                if (region >= 0) {
                    if (regionAreas.get(region) == null) {
                        regionAreas.put(region, 1);
                    }
                    else {
                        regionAreas.put(region, regionAreas.get(region) + 1);
                    }
                }
            }
        }

        return regionAreas;
    }

    public void visualizeLand(String fileName) {
        // initialize color list
        List<Color> debugColorList = new ArrayList<Color>();
        debugColorList.add(Color.white);
        debugColorList.add(Color.red);
        debugColorList.add(Color.blue);
        debugColorList.add(Color.green);
        debugColorList.add(Color.pink);
        debugColorList.add(Color.cyan);

        // initialize image
        int[] flattenedData = new int[width*height*3];

        // build image
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                // determine correct pixel color
                Color pixelColor = debugColorList.get(0);
                int region = landGrid[x][height - 1 - y];

                if (region == -1) {
                    pixelColor = Color.black;
                } else {
                    if (region < debugColorList.size()) {
                        pixelColor = debugColorList.get(landGrid[x][height - 1 - y]);
                    }
                }

                // write pixel to iage
                int index = width*3 * y + x*3;
                flattenedData[index] = pixelColor.getRed();
                flattenedData[index+1] = pixelColor.getGreen();
                flattenedData[index+2] = pixelColor.getBlue();
            }            
        }

        // write image to file
        try {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            img.getRaster().setPixels(0, 0, width, height, flattenedData);
            ImageIO.write(img, "bmp", new File(fileName + ".bmp"));
        } catch (IOException e) {
            System.err.println("IOException when writing visualization file!");
        }
    }
}