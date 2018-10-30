import java.util.LinkedList;
import java.util.List;

public class Euler {
    private double xMin, xMax;
    private double yMin;
    private double h;
    List <Point> points;
    List <Double> errors;

    public void countPoints() {
        if (points != null) {
            points = null;
        }
        points = new LinkedList<Point>();
        ((LinkedList<Point>) points).addFirst(new Point(xMin, yMin));
        double xCurrent = xMin;
        while (xCurrent + h < xMax) {
            xCurrent = xCurrent + h;

        }
    }
}
