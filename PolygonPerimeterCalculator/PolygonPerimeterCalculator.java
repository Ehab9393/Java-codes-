/**********************************************************************
 * File Name: PolygonPerimeterCalculator.java
 * Description:
 * This Java program reads polygon vertex data from an input file 
 * ("polygon.txt"), calculates the perimeter using Euclidean distance 
 * between consecutive vertices, and prints the result formatted 
 * to two decimal places.
 *
 * The program:
 * - Uses Scanner to read numeric input (supports commas and whitespace)
 * - Stores vertices as Point2D.Double objects
 * - Wraps the last vertex to the first to close the polygon
 * - Computes total perimeter by summing distances between points
 *
 * Requires: polygon.txt file in the same directory as the program.
 **********************************************************************/

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PerimeterCalculator {

    public static void main(String[] args) throws FileNotFoundException {
        //  Open the input file
        Scanner scanner = new Scanner(new File("polygon.txt"));
        //  Split tokens on commas or any whitespace
        scanner.useDelimiter("[,\\s]+");  // splits on comma or space/newline

        //  Read how many vertices there are
        int n = scanner.nextInt();         // reads next int token

        // Read each (x, y) pair straight into Point2D.Double objects
        Point2D.Double[] vertices = new Point2D.Double[n];
        for (int i = 0; i < n; i++) {
            double x = scanner.nextDouble();  // next numeric token
            double y = scanner.nextDouble();  // next numeric token
            vertices[i] = new Point2D.Double(x, y);
        }
        scanner.close();

        //   Calculate perimeter by summing distances between consecutive points,
        //    wrapping the last point back to the first
        double perimeter = 0.0;
        for (int i = 0; i < n; i++) {
            perimeter += vertices[i].distance(vertices[(i + 1) % n]);  // Euclidean distance
        }

        //   Print the result, formatted to two decimal places
        System.out.printf("Perimeter of the polygon is: %.2f%n", perimeter);
    }
}
