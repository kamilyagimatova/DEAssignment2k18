public class RungeKutta extends Euler {
    public RungeKutta(double xMin, double yMin, double xMax, double h) {
        super(xMin, yMin, xMax, h);
    }

    @Override
    public double formula(double x, double y) {
        double k1 = function(x, y);
        double k2 = function(x + h / 2, y + h * k1 / 2);
        double k3 = function(x + h / 2, y + h * k2 / 2);
        double k4 = function(x + h, y + h * k3);
        double deltaY = (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
        return y + deltaY;
    }
}
