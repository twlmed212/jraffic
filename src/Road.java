import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;

public class Road {
    private Rectangle lane1;
    private Rectangle lane2;
    private Line divider;

    /**
     * @param x starting x
     * @param y starting y
     * @param laneWidth width of each lane
     * @param roadLength length of the road
     * @param vertical true if road is vertical, false if horizontal
     */
    public Road(double x, double y, double laneWidth, double roadLength, boolean vertical) {
        if (vertical) {
            // two vertical lanes
            lane1 = new Rectangle(x, y, laneWidth, roadLength);
            lane1.setFill(Color.DARKGRAY);

            lane2 = new Rectangle(x + laneWidth, y, laneWidth, roadLength);
            lane2.setFill(Color.DARKGRAY);

            // divider line between lanes
            divider = new Line(x + laneWidth, y, x + laneWidth, y + roadLength);
            divider.setStroke(Color.WHITE);
            divider.setStrokeWidth(2);
        } else {
            // two horizontal lanes
            lane1 = new Rectangle(x, y, roadLength, laneWidth);
            lane1.setFill(Color.DARKGRAY);

            lane2 = new Rectangle(x, y + laneWidth, roadLength, laneWidth);
            lane2.setFill(Color.DARKGRAY);

            // divider line between lanes
            divider = new Line(x, y + laneWidth, x + roadLength, y + laneWidth);
            divider.setStroke(Color.WHITE);
            divider.setStrokeWidth(2);
        }
    }

    public Rectangle[] getLanes() {
        return new Rectangle[]{lane1, lane2};
    }

    public Line getDivider() {
        return divider;
    }
}