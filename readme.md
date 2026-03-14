```Road Intersection Simulation (Java)```
An intelligent 4-way intersection simulation built with Java Swing. This system manages traffic flow based on real-time vehicle density in each direction while ensuring collision avoidance and gridlock prevention.

🚀 Key Features
Smart Traffic Lights: Signals change dynamically based on vehicle capacity (waiting counts) and intersection clearance.

Collision Avoidance: Advanced safety logic ensures a constant gap between vehicles using predictive distance checking.

Gridlock Prevention: Cars follow the "Clear Path" rule—they won't enter the intersection unless there is sufficient space to exit on the other side.

Dynamic Routing: Vehicles randomly choose to turn left, turn right, or go straight, with corresponding color coding.

Smooth Visuals: Implementation of Antialiasing, Double Buffering, and Toolkit Sync for flicker-free, high-refresh-rate movement.

🛠️ Project Structure
Plaintext
├── Car.java            # Vehicle movement logic and local AI
├── Light.java          # Traffic light management and timing logic
├── RoadIntersection.java # Main GUI class and rendering engine
├── Direction.java      # Enum for directions (NORTH, SOUTH, EAST, WEST)
├── Route.java          # Enum for turn paths (LEFT, RIGHT, STRAIGHT)
└── Makefile            # Automation tool for building and running
💻 Getting Started
Prerequisites
JDK 11 or higher installed.

Make utility (optional but recommended).

Building and Running (via Makefile)
Open your terminal in the project directory and run:

Bash
make
Manual Compilation
If you don't have make installed:

Bash
# Compile all files
javac *.java

# Run the main class
java RoadIntersection
🎮 Controls (Keyboard)
You can manually spawn cars to test the simulation using the arrow keys:

⬅️ Left Arrow: Spawn car from the Right (Heading West).

➡️ Right Arrow: Spawn car from the Left (Heading East).

⬆️ Up Arrow: Spawn car from the Bottom (Heading North).

⬇️ Down Arrow: Spawn car from the Top (Heading South).

⌨️ R Key: Spawn a car in a random direction.

⚙️ Technical Specifications
Update Rate: The simulation loop runs every 10ms (~100 FPS).

Memory Management: Vehicles are automatically garbage collected once they leave the screen boundaries.

Thread Safety: Utilizes CopyOnWriteArrayList to prevent ConcurrentModificationException during simultaneous rendering and updates.