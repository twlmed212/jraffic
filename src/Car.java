import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Car {
    public int id, x, y;
    public Direction direction;
    public Route route;
    public Color color;
    public boolean state = true; 
    public boolean outCalc = false;

    public Car(int id, int x, int y, Direction dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = dir;

        Random rand = new Random();
        int r = rand.nextInt(3);
        if (r == 0) {
            this.route = Route.LEFT;
            this.color = new Color(128, 0, 128); // Purple
        } else if (r == 1) {
            this.route = Route.RIGHT;
            this.color = new Color(255, 140, 0); // Orange
        } else {
            this.route = Route.STRAIGHT;
            this.color = new Color(0, 191, 255); // Sky Blue
        }
    }

    public void updatePosition(Light[] lights, List<Car> cars, Map<String, Integer> capacity, int centerX, int centerY) {
        int speed = 3;

        switch (this.direction) {
            case EAST:
                if (this.x > centerX - 100) handleEnterIntersection(capacity, "East");
                if (state && x >= centerX - 110 && x <= centerX - 100) {
                    if (!lights[1].status || !isExitClear(cars, centerX, centerY) || !canMove(cars)) return;
                } else if (!canMove(cars)) return;
                
                this.x += speed;
                checkTurn(centerX, centerY);
                break;

            case WEST:
                if (this.x < centerX + 50) handleEnterIntersection(capacity, "West");
                if (state && x <= centerX + 60 && x >= centerX + 50) {
                    if (!lights[2].status || !isExitClear(cars, centerX, centerY) || !canMove(cars)) return;
                } else if (!canMove(cars)) return;

                this.x -= speed;
                checkTurn(centerX, centerY);
                break;

            case NORTH:
                if (this.y > centerY - 100) handleEnterIntersection(capacity, "North");
                if (state && y >= centerY - 110 && y <= centerY - 100) {
                    if (!lights[0].status || !isExitClear(cars, centerX, centerY) || !canMove(cars)) return;
                } else if (!canMove(cars)) return;

                this.y += speed;
                checkTurn(centerX, centerY);
                break;

            case SOUTH:
                if (this.y < centerY + 50) handleEnterIntersection(capacity, "South");
                if (state && y <= centerY + 60 && y >= centerY + 50) {
                    if (!lights[3].status || !isExitClear(cars, centerX, centerY) || !canMove(cars)) return;
                } else if (!canMove(cars)) return;

                this.y -= speed;
                checkTurn(centerX, centerY);
                break;
        }
    }

    private void checkTurn(int centerX, int centerY) {
        if (direction == Direction.EAST) {
            if (route == Route.LEFT && x >= centerX) { direction = Direction.SOUTH; route = Route.STRAIGHT; }
            else if (route == Route.RIGHT && x >= centerX - 50) { direction = Direction.NORTH; route = Route.STRAIGHT; }
        } else if (direction == Direction.WEST) {
            if (route == Route.LEFT && x + 50 <= centerX) { direction = Direction.NORTH; route = Route.STRAIGHT; }
            else if (route == Route.RIGHT && x <= centerX) { direction = Direction.SOUTH; route = Route.STRAIGHT; }
        } else if (direction == Direction.NORTH) {
            if (route == Route.LEFT && y >= centerY) { direction = Direction.EAST; route = Route.STRAIGHT; }
            else if (route == Route.RIGHT && y >= centerY - 50) { direction = Direction.WEST; route = Route.STRAIGHT; }
        } else if (direction == Direction.SOUTH) {
            if (route == Route.LEFT && y + 50 <= centerY) { direction = Direction.WEST; route = Route.STRAIGHT; }
            else if (route == Route.RIGHT && y <= centerY) { direction = Direction.EAST; route = Route.STRAIGHT; }
        }
    }

    private void handleEnterIntersection(Map<String, Integer> capacity, String dirStr) {
        this.state = false;
        if (!outCalc) {
            capacity.put(dirStr, Math.max(0, capacity.get(dirStr) - 1));
            outCalc = true;
        }
    }

    private boolean isExitClear(List<Car> cars, int centerX, int centerY) {
        int checkGap = 70; 
        for (Car other : cars) {
            if (this.id == other.id) continue;
            switch (this.direction) {
                case EAST: if (other.x > centerX + 50 && other.x < centerX + 50 + checkGap && Math.abs(other.y - this.y) < 10) return false; break;
                case WEST: if (other.x < centerX - 100 && other.x > centerX - 100 - checkGap && Math.abs(other.y - this.y) < 10) return false; break;
                case NORTH: if (other.y > centerY + 50 && other.y < centerY + 50 + checkGap && Math.abs(other.x - this.x) < 10) return false; break;
                case SOUTH: if (other.y < centerY - 100 && other.y > centerY - 100 - checkGap && Math.abs(other.x - this.x) < 10) return false; break;
            }
        }
        return true;
    }

    private boolean canMove(List<Car> cars) {
        int gap = 55; 
        for (Car other : cars) {
            if (this.id == other.id) continue;
            switch (this.direction) {
                case EAST: if (other.x > this.x && other.x - this.x < gap && Math.abs(other.y - this.y) < 10) return false; break;
                case WEST: if (other.x < this.x && this.x - other.x < gap && Math.abs(other.y - this.y) < 10) return false; break;
                case NORTH: if (other.y > this.y && other.y - this.y < gap && Math.abs(other.x - this.x) < 10) return false; break;
                case SOUTH: if (other.y < this.y && this.y - other.y < gap && Math.abs(other.x - this.x) < 10) return false; break;
            }
        }
        return true;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, 45, 45);
    }
}