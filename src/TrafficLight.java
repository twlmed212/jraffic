import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLight {
    private Circle lightCircle;
    private boolean green;

    public TrafficLight(double x, double y) {
        lightCircle = new Circle(x, y, 10); // position on the corner
        setRed(); // default state
    }

    public Circle getView() {
        return lightCircle;
    }

    public void setRed() {
        green = false;
        lightCircle.setFill(Color.RED);
    }

    public void setGreen() {
        green = true;
        lightCircle.setFill(Color.GREEN);
    }

    public boolean isGreen() {
        return green;
    }
}