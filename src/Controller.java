import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Controller {

    public TextField xMinField;
    public TextField xMaxField;
    public TextField yMinField;
    public TextField hField;
    public Euler euler;
    public ImprovedEuler improvedEuler;
    public RungeKutta rungeKutta;

    public Controller() {
    }

    public void modifyAll(ActionEvent actionEvent) {
        if (euler == null) {
            double xMin = Double.valueOf(xMinField.getText());
            double xMax = Double.valueOf(xMaxField.getText());
            double yMin = Double.valueOf(yMinField.getText());
            double h = Double.valueOf(hField.getText());
            euler = new Euler(xMin, yMin, xMax, h);
            improvedEuler = new ImprovedEuler(xMin, yMin, xMax, h);
            rungeKutta = new RungeKutta(xMin, yMin, xMax, h);
        }
        xMinModify(actionEvent);
        xMaxModify(actionEvent);
        yMinModify(actionEvent);
        hModify(actionEvent);
    }


    public void eulerBuild(ActionEvent actionEvent) {
        modifyAll(actionEvent);
        euler.answer();
        Stage graph = new Stage();

        final NumberAxis xAxis = new NumberAxis(euler.xMin, euler.xMax, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setTitle("Euler");

        areaChart.setLegendSide(Side.LEFT);

        XYChart.Series<Number, Number> eulerPoints = new XYChart.Series<Number, Number>();

        eulerPoints.setName("Euler method");

        for (Point point : euler.points) {
            eulerPoints.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));
        }

        graph.setTitle("Euler Method");
        Scene scene = new Scene(areaChart, 400, 300);
        areaChart.getData().addAll(eulerPoints);
        graph.setScene(scene);
        graph.show();
    }

    public void improvedEulerBuild(ActionEvent actionEvent) {
        modifyAll(actionEvent);
        improvedEuler.answer();
        Stage graph = new Stage();

        final NumberAxis xAxis = new NumberAxis(improvedEuler.xMin, improvedEuler.xMax, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setTitle("Improved Euler");

        areaChart.setLegendSide(Side.LEFT);

        XYChart.Series<Number, Number> improvedEulerPoints = new XYChart.Series<Number, Number>();

        improvedEulerPoints.setName("Improved Euler method");

        for (Point point : improvedEuler.points) {
            improvedEulerPoints.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));
        }

        graph.setTitle("Improved Euler Method");
        Scene scene = new Scene(areaChart, 400, 300);
        areaChart.getData().addAll(improvedEulerPoints);
        graph.setScene(scene);
        graph.show();
    }

    public void rungeKuttaBuild(ActionEvent actionEvent) {
        modifyAll(actionEvent);
        rungeKutta.answer();
        Stage graph = new Stage();

        final NumberAxis xAxis = new NumberAxis(rungeKutta.xMin, rungeKutta.xMax, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setTitle("Runge-Kutta Euler");

        areaChart.setLegendSide(Side.LEFT);

        XYChart.Series<Number, Number> rungeKuttaPoints = new XYChart.Series<Number, Number>();

        rungeKuttaPoints.setName("Runge-Kutta method");

        for (Point point : rungeKutta.points) {
            rungeKuttaPoints.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));
        }

        graph.setTitle("Runge-Kutta Euler Method");
        Scene scene = new Scene(areaChart, 400, 300);
        areaChart.getData().addAll(rungeKuttaPoints);
        graph.setScene(scene);
        graph.show();
    }

    public void xMinModify(ActionEvent actionEvent) {
        double xMin = Double.valueOf(xMinField.getText());
        euler.setXMin(xMin);
        improvedEuler.setXMin(xMin);
        rungeKutta.setXMin(xMin);
        xMinField.setText(Double.toString(euler.xMin));
    }

    public void xMaxModify(ActionEvent actionEvent) {
        double xMax = Double.valueOf(xMaxField.getText());
        euler.setXMax(xMax);
        improvedEuler.setXMax(xMax);
        rungeKutta.setXMax(xMax);
        xMaxField.setText(Double.toString(euler.xMax));
    }

    public void yMinModify(ActionEvent actionEvent) {
        double yMin = Double.valueOf(yMinField.getText());
        euler.setYMin(yMin);
        improvedEuler.setYMin(yMin);
        rungeKutta.setYMin(yMin);
        yMinField.setText(Double.toString(euler.yMin));
    }

    public void hModify(ActionEvent actionEvent) {
        double h = Double.valueOf(hField.getText());
        euler.setH(h);
        improvedEuler.setH(h);
        rungeKutta.setH(h);
        hField.setText(Double.toString(euler.h));
    }
}
