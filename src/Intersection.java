import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Intersection {
    private Road verticalRoad;
    private Road horizontalRoad;

    private TrafficLight northLight;
    private TrafficLight southLight;
    private TrafficLight westLight;
    private TrafficLight eastLight;

    public Intersection(Pane root) {
        int laneWidth = 40;
        int roadLength = 800;

        // 1️⃣ Create roads
        verticalRoad = new Road(380, 0, laneWidth, roadLength, true);
        horizontalRoad = new Road(0, 380, laneWidth, roadLength, false);

        // 2️⃣ Add lanes to root
        for (Rectangle r : verticalRoad.getLanes()) root.getChildren().add(r);
        for (Rectangle r : horizontalRoad.getLanes()) root.getChildren().add(r);

        // 3️⃣ Add dividers
        root.getChildren().add(verticalRoad.getDivider());
        root.getChildren().add(horizontalRoad.getDivider());

        // 4️⃣ Draw white corners for traffic lights
        Rectangle nwCorner = new Rectangle(360, 360, 20, 20);
        nwCorner.setFill(Color.WHITE);
        Rectangle neCorner = new Rectangle(420, 360, 20, 20);
        neCorner.setFill(Color.WHITE);
        Rectangle swCorner = new Rectangle(360, 420, 20, 20);
        swCorner.setFill(Color.WHITE);
        Rectangle seCorner = new Rectangle(420, 420, 20, 20);
        seCorner.setFill(Color.WHITE);
        root.getChildren().addAll(nwCorner, neCorner, swCorner, seCorner);

        // 5️⃣ Place traffic lights **on the white corners**
        northLight = new TrafficLight(370, 370); // NW corner
        southLight = new TrafficLight(430, 430); // SE corner
        westLight  = new TrafficLight(370, 430); // SW corner
        eastLight  = new TrafficLight(430, 370); // NE corner

        root.getChildren().addAll(
            northLight.getView(),
            southLight.getView(),
            westLight.getView(),
            eastLight.getView()
        );
    }
}