import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoadIntersection extends JPanel {
    private List<Car> cars = new CopyOnWriteArrayList<>();
    private Map<String, Integer> capacity = new HashMap<>();
    private Light[] lights;
    private final int width = 800, height = 600;
    private final int centerX = width / 2, centerY = height / 2;
    private long lastUpdate = 0;

    public RoadIntersection() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new Color(34, 139, 34));

        capacity.put("North", 0);
        capacity.put("South", 0);
        capacity.put("East", 0);
        capacity.put("West", 0);

        lights = new Light[] {
                new Light(centerX - 100, centerY - 100, Color.RED, 1),
                new Light(centerX - 100, centerY + 50, Color.RED, 2),
                new Light(centerX + 50, centerY - 100, Color.RED, 3),
                new Light(centerX + 50, centerY + 50, Color.RED, 4)
        };

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int nextId = cars.size() + 1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        if (canSpawn(0, centerY)) {
                            cars.add(new Car(nextId, 0, centerY, Direction.EAST));
                            capacity.put("East", capacity.get("East") + 1);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (canSpawn(width - 50, centerY - 50)) {
                            cars.add(new Car(nextId, width - 50, centerY - 50, Direction.WEST));
                            capacity.put("West", capacity.get("West") + 1);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (canSpawn(centerX - 50, 0)) {
                            cars.add(new Car(nextId, centerX - 50, 0, Direction.NORTH));
                            capacity.put("North", capacity.get("North") + 1);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (canSpawn(centerX, height - 50)) {
                            cars.add(new Car(nextId, centerX, height - 50, Direction.SOUTH));
                            capacity.put("South", capacity.get("South") + 1);
                        }
                        break;
                    case KeyEvent.VK_R:
                        addRandomCar();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                }
            }
        });

        Timer timer = new Timer(10, (ActionEvent e) -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void addRandomCar() {
        Random r = new Random();
        int dir = r.nextInt(4);
        int nextId = cars.size() + 1;

        if (dir == 0 && canSpawn(0, centerY)) {
            cars.add(new Car(nextId, 0, centerY, Direction.EAST));
            capacity.put("East", capacity.get("East") + 1);
        } else if (dir == 1 && canSpawn(width - 50, centerY - 50)) {
            cars.add(new Car(nextId, width - 50, centerY - 50, Direction.WEST));
            capacity.put("West", capacity.get("West") + 1);
        } else if (dir == 2 && canSpawn(centerX - 50, 0)) {
            cars.add(new Car(nextId, centerX - 50, 0, Direction.NORTH));
            capacity.put("North", capacity.get("North") + 1);
        } else if (dir == 3 && canSpawn(centerX, height - 50)) {
            cars.add(new Car(nextId, centerX, height - 50, Direction.SOUTH));
            capacity.put("South", capacity.get("South") + 1);
        }
    }

    private boolean canSpawn(int x, int y) {
        int safeZone = 60;
        for (Car car : cars) {
            if (Math.abs(car.x - x) < safeZone && Math.abs(car.y - y) < safeZone) {
                return false;
            }
        }
        return true;
    }

    private void update() {
        long now = System.currentTimeMillis() / 1000;
        if (now > lastUpdate && Light.isEmptyCenter(cars, centerX, centerY)) {
            for (Light l : lights)
                l.updateTrafficLight(capacity);
            lastUpdate = now;
        }

        cars.removeIf(car -> car.x < -100 || car.x > width + 100 || car.y < -100 || car.y > height + 100);

        for (Car car : cars) {
            car.updatePosition(lights, cars, capacity, centerX, centerY);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // ---Smooth drawing
        // active Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // map draw
        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // road draw
        g2.setColor(new Color(60, 60, 60));
        g2.fillRect(centerX - 50, 0, 100, height);
        g2.fillRect(0, centerY - 50, width, 100);

        g2.setColor(Color.WHITE);
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
        g2.setStroke(dashed);
        g2.drawLine(centerX, 0, centerX, height);
        g2.drawLine(0, centerY, width, centerY);

        // light draw
        for (Light l : lights) {
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(l.x - 2, l.y - 2, 54, 54);
            g2.setColor(l.color);
            g2.fillRect(l.x, l.y, 50, 50);
        }

        // cars draw
        for (Car car : cars) {
            g2.setColor(car.color);
            Rectangle r = car.getRect();
            g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Road Intersection Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RoadIntersection());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}