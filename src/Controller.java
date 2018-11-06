import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
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

    public boolean modifyAll(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Constraint");
        alert.setHeaderText("Look, an Information Dialog");

        try {
            if (euler == null) {
                double xMin = Double.valueOf(xMinField.getText());
                double xMax = Double.valueOf(xMaxField.getText());
                double yMin = Double.valueOf(yMinField.getText());
                double h = Double.valueOf(hField.getText());
                euler = new Euler(xMin, yMin, xMax, h);
                improvedEuler = new ImprovedEuler(xMin, yMin, xMax, h);
                rungeKutta = new RungeKutta(xMin, yMin, xMax, h);
            }
            xMinAndXMaxModify(actionEvent);
            yMinModify(actionEvent);
            hModify(actionEvent);
            return true;
        } catch (NumberFormatException e) {
            alert.setContentText("Your input is not double type");
            alert.showAndWait();
            return false;
        } catch (IllegalArgumentException e) {
            alert.setContentText(String.format("%s", e.getLocalizedMessage()));
            alert.showAndWait();
            return false;
        }
    }

    public XYChart.Series<Number, Number> exactBuild() {
        XYChart.Series<Number, Number> exactPoints = new XYChart.Series<Number, Number>();

        exactPoints.setName("Exact solution");

        for (Point point : euler.yExact) {
            exactPoints.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));
        }
        return exactPoints;
    }

    public void errorsBuild(ActionEvent actionEvent, Euler method) {
        Stage errors = new Stage();

        final NumberAxis xAxis = new NumberAxis(method.xMin, method.xMax, 1);
        //final NumberAxis yAxis = new NumberAxis(-1,1,1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("Errors");

        areaChart.setLegendSide(Side.LEFT);

        XYChart.Series<Number, Number> localErrors = new XYChart.Series<Number, Number>();

        localErrors.setName("Local errors");

        int i = 0;
        for (Point point : method.points) {
            localErrors.getData().add(new XYChart.Data<Number, Number>(point.x, method.localErrors.get(i)));
            i++;
        }

        XYChart.Series<Number, Number> globalErrors = new XYChart.Series<Number, Number>();

        globalErrors.setName("Global errors");

        i = 0;
        for (Point point : method.points) {
            globalErrors.getData().add(new XYChart.Data<Number, Number>(point.x, method.globalErrors.get(i)));
            i++;
        }

        errors.setTitle("Errors");
        areaChart.getData().addAll(localErrors, globalErrors);
        Scene scene = new Scene(areaChart, 400, 300);
        errors.setScene(scene);
        //errors.setFullScreen(true);
        errors.show();
    }

    public void eulerBuild(ActionEvent actionEvent) {
        if (!modifyAll(actionEvent)) {
            return;
        }
        euler.answer();
        Stage graph = new Stage();

        final NumberAxis xAxis = new NumberAxis(euler.xMin, euler.xMax, 1);
        //final NumberAxis yAxis = new NumberAxis(-1,1,1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("Euler");

        areaChart.setLegendSide(Side.LEFT);

        XYChart.Series<Number, Number> eulerPoints = new XYChart.Series<Number, Number>();

        eulerPoints.setName("Euler method");

        for (Point point : euler.points) {
            eulerPoints.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));
        }

        graph.setTitle("Euler Method");
        areaChart.getData().addAll(eulerPoints);
        areaChart.getData().addAll(exactBuild());
        Scene scene = new Scene(areaChart, 400, 300);
        graph.setScene(scene);
        //graph.setFullScreen(true);
        graph.show();
        errorsBuild(actionEvent, euler);
    }

    public void improvedEulerBuild(ActionEvent actionEvent) {
        if (!modifyAll(actionEvent)) {
            return;
        }
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
        areaChart.getData().addAll(improvedEulerPoints, exactBuild());
        graph.setScene(scene);
        //graph.setFullScreen(true);
        graph.show();
        errorsBuild(actionEvent, improvedEuler);
    }

    public void rungeKuttaBuild(ActionEvent actionEvent) {
        if (!modifyAll(actionEvent)) {
            return;
        }
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
        areaChart.getData().addAll(rungeKuttaPoints, exactBuild());
        graph.setScene(scene);
        //graph.setFullScreen(true);
        graph.show();
        errorsBuild(actionEvent, rungeKutta);
    }

//    public void xMinModify(ActionEvent actionEvent) {
//        double xMin = Double.valueOf(xMinField.getText());
//        euler.setXMin(xMin);
//        improvedEuler.setXMin(xMin);
//        rungeKutta.setXMin(xMin);
//        xMinField.setText(Double.toString(euler.xMin));
//    }
//
//    public void xMaxModify(ActionEvent actionEvent) {
//        double xMax = Double.valueOf(xMaxField.getText());
//        euler.setXMax(xMax);
//        improvedEuler.setXMax(xMax);
//        rungeKutta.setXMax(xMax);
//        xMaxField.setText(Double.toString(euler.xMax));
//    }

    public void xMinAndXMaxModify(ActionEvent actionEvent) {
        double xMin = Double.valueOf(xMinField.getText());
        double xMax = Double.valueOf(xMaxField.getText());
        euler.setXminAndXMax(xMin, xMax);
        improvedEuler.setXminAndXMax(xMin, xMax);
        rungeKutta.setXminAndXMax(xMin, xMax);
        xMinField.setText(Double.toString(euler.xMin));
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
