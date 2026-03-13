# Path to JavaFX libraries
JAVA_FX_LIB=/usr/share/openjfx/lib

# Folder to store compiled classes
BUILD_DIR=build

# All Java files
SRC=$(wildcard *.java)

# Default target: compile
all:
	mkdir -p $(BUILD_DIR)
	javac --module-path $(JAVA_FX_LIB) --add-modules javafx.controls,javafx.graphics -d $(BUILD_DIR) $(SRC)

# Run the program
run:
	java --module-path $(JAVA_FX_LIB) --add-modules javafx.controls,javafx.graphics -cp $(BUILD_DIR) Main

# Clean build folder
clean:
	rm -rf $(BUILD_DIR)

	# ---> run it with make run and clean it with make clean