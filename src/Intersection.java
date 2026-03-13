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
        int laneWidth  = 40;
        int roadLength = 800;

        // Road geometry:
        //   verticalRoad   x: 380 → 460  (two 40-px lanes)
        //   horizontalRoad y: 380 → 460  (two 40-px lanes)
        // Intersection box (where roads overlap): x 380-460, y 380-460
        // Corner pavement squares sit just OUTSIDE the road edges.

        int roadLeft   = 380;   // left  edge of vertical road
        int roadRight  = 460;   // right edge of vertical road   (380 + 2*40)
        int roadTop    = 380;   // top   edge of horizontal road
        int roadBottom = 460;   // bottom edge of horizontal road (380 + 2*40)
        int cornerSize = 20;    // size of each pavement corner square

        // 1️⃣ Create roads
        verticalRoad   = new Road(roadLeft, 0, laneWidth, roadLength, true);
        horizontalRoad = new Road(0, roadTop,  laneWidth, roadLength, false);

        // 2️⃣ Add lanes to root
        for (Rectangle r : verticalRoad.getLanes())   root.getChildren().add(r);
        for (Rectangle r : horizontalRoad.getLanes()) root.getChildren().add(r);

        // 3️⃣ Add dividers
        root.getChildren().add(verticalRoad.getDivider());
        root.getChildren().add(horizontalRoad.getDivider());

        // 4️⃣ Draw pavement corner squares OUTSIDE the intersection box
        //    NW corner: left of and above the road box
        Rectangle nwCorner = new Rectangle(roadLeft - cornerSize, roadTop - cornerSize, cornerSize, cornerSize);
        nwCorner.setFill(Color.LIGHTGRAY);

        //    NE corner: right of and above the road box
        Rectangle neCorner = new Rectangle(roadRight, roadTop - cornerSize, cornerSize, cornerSize);
        neCorner.setFill(Color.LIGHTGRAY);

        //    SW corner: left of and below the road box
        Rectangle swCorner = new Rectangle(roadLeft - cornerSize, roadBottom, cornerSize, cornerSize);
        swCorner.setFill(Color.LIGHTGRAY);

        //    SE corner: right of and below the road box
        Rectangle seCorner = new Rectangle(roadRight, roadBottom, cornerSize, cornerSize);
        seCorner.setFill(Color.LIGHTGRAY);

        root.getChildren().addAll(nwCorner, neCorner, swCorner, seCorner);

        // 5️⃣ Place traffic lights centred on their pavement corner
        //    Convention: each light is named after the traffic it controls.
        //      northLight → controls vehicles entering from the NORTH (NW corner)
        //      eastLight  → controls vehicles entering from the EAST  (NE corner)
        //      southLight → controls vehicles entering from the SOUTH (SE corner)
        //      westLight  → controls vehicles entering from the WEST  (SW corner)
        int cx = cornerSize / 2; // half-corner offset → centres the light on the square

        northLight = new TrafficLight(roadLeft  - cornerSize + cx, roadTop    - cornerSize + cx); // NW
        eastLight  = new TrafficLight(roadRight              + cx, roadTop    - cornerSize + cx); // NE
        southLight = new TrafficLight(roadRight              + cx, roadBottom              + cx); // SE
        westLight  = new TrafficLight(roadLeft  - cornerSize + cx, roadBottom              + cx); // SW

        root.getChildren().addAll(
            northLight.getView(),
            eastLight.getView(),
            southLight.getView(),
            westLight.getView()
        );
    }
}