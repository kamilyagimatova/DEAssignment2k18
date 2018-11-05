public class ImprovedEuler extends Euler {
    public ImprovedEuler(double xMin, double yMin, double xMax, double h) {
        super(xMin, yMin, xMax, h);
    }

    public ImprovedEuler() {
        super();
    }

    @Override
    public double formula(double x, double y) {
        return y + h * function(x + h / 2, y + (h / 2) * function(x, y));
    }
}
