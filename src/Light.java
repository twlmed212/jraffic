import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Light {
    public int id;
    public int x, y;
    public Color color;
    public boolean status;

    public Light(int x, int y, Color color, int id) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
        this.status = false;
    }

    public void updateTrafficLight(Map<String, Integer> capacity) {
        int greenLightId = 0;
        int maxCars = -1;

        for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
            if (entry.getValue() > maxCars) {
                maxCars = entry.getValue();
                switch (entry.getKey()) {
                    case "North": greenLightId = 1; break;
                    case "East":  greenLightId = 2; break;
                    case "West":  greenLightId = 3; break;
                    case "South": greenLightId = 4; break;
                }
            }
        }

        if (this.id == greenLightId) {
            this.color = Color.GREEN;
            this.status = true;
        } else {
            this.color = Color.RED;
            this.status = false;
        }
    }

    public static boolean isEmptyCenter(List<Car> cars, int centerX, int centerY) {
        int top = centerY - 50;
        int bottom = centerY + 50;
        int left = centerX - 50;
        int right = centerX + 50;

        for (Car car : cars) {
            if (car.x + 50 > left && car.x < right && car.y + 50 > top && car.y < bottom) {
                return false;
            }
        }
        return true;
    }
}