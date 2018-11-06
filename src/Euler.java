import java.util.LinkedList;
import java.util.List;

public class Euler {
    public double xMin, xMax;
    public double yMin;
    public double h;
    public double c;
    List<Point> points;
    List<Double> globalErrors;
    List<Double> localErrors;
    List<Point> yExact;

    public Euler(double xMin, double yMin, double xMax, double h) {
        if (xMin <= 0 && xMax >= 0) {
            throw new IllegalArgumentException("Method doesn't work with x = 0");
        }
        if (xMin >= xMax) {
            throw new IllegalArgumentException("xMin must be less than xMax");
        }
        if (h <= 0) {
            throw new IllegalArgumentException("h has to be non-positive");
        }
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.h = h;
    }


    public double formula(double x, double y) {
        return y + h * function(x, y);
    }

    public double function(double x, double y) {
        if (x != 0) {
            return 3 * x * Math.exp(x) - y * (1 - 1 / x);
        } else {
            return 0;
        }
    }

    private void countC() {
        c = yMin / (xMin * Math.exp(-xMin)) - 1.5 * Math.exp(2 * xMin);
    }

    private double solutionFunction(double x) {
        return 1.5 * Math.exp(x) * x + c * x * Math.exp(-x);
    }

    public void countPoints() {
        if (points != null) {
            points = null;
        }
        points = new LinkedList<Point>();
        ((LinkedList<Point>) points).addFirst(new Point(xMin, yMin));
        double xCurrent = xMin;
        double yCurrent = yMin;
        while (xCurrent + h <= xMax) {
            yCurrent = formula(xCurrent, yCurrent);
            xCurrent += h;
            points.add(new Point(xCurrent, yCurrent));
        }
    }

    public void countLocalErrors() {
        if (localErrors != null) {
            localErrors = null;
        }
        localErrors = new LinkedList<>();
        localErrors.add((double) 0);
        for (int i = 1; i < points.size(); i++) {
            double error = solutionFunction(points.get(i).x) - formula(points.get(i - 1).x, solutionFunction(points.get(i - 1).x));
            localErrors.add(error);
        }
    }

    public void countGlobalErrors() {
        if (globalErrors != null) {
            globalErrors = null;
        }
        globalErrors = new LinkedList<>();
        for (Point point : points) {
            double yExact = solutionFunction(point.x);
            globalErrors.add(yExact - point.y);
        }
    }

    public void countYExact() {
        if (yExact != null) {
            yExact = null;
        }
        yExact = new LinkedList<Point>();
        ((LinkedList<Point>) yExact).addFirst(new Point(xMin, yMin));
        double xCurrent = xMin;
        double yCurrent = yMin;
        while (xCurrent + h <= xMax) {
            yCurrent = solutionFunction(xCurrent);
            xCurrent += h;
            yExact.add(new Point(xCurrent, yCurrent));
        }
    }

    public void answer() {
        countC();
        countPoints();
        countGlobalErrors();
        countLocalErrors();
        countYExact();
//        int i = 0;
//        for (Point point : points) {
//            System.out.printf("x = %f; y = %f; e local = %f; e global = %f; e exact = %f\n", point.x, point.y, localErrors.get(i), globalErrors.get(i), yExact.get(i).y);
//            i++;
//        }
    }


    public void setXminAndXMax(double xMin, double xMax) {
        if (xMin <= 0 && xMax >= 0) {
            throw new IllegalArgumentException("Method doesn't work with x = 0");
        }
        if (xMin >= xMax) {
            throw new IllegalArgumentException("xMin must be less than xMax");
        }
        this.xMin = xMin;
        this.xMax = xMax;
    }

    public void setYMin(double yMin) {
        this.yMin = yMin;
    }

    public void setH(double h) {
        if (h <= 0) {
            throw new IllegalArgumentException("h has to be positive");
        }
        this.h = h;
    }
}
