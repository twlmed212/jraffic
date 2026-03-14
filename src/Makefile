# متغيرات المشروع
JAVAC = javac
JAVA = java
SRC_DIR = .
BIN_DIR = bin
MAIN_CLASS = RoadIntersection

SOURCES = $(wildcard $(SRC_DIR)/*.java)

all: compile run

compile:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)

run:
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BIN_DIR)

rebuild: clean all

.PHONY: all compile run clean rebuild