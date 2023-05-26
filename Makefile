.PHONY: clean default run
%.class: %.java
	javac $<
clean:
	find . -name '*.class' | xargs rm
run: PolyFracDiffApplet.class
	appletviewer demo.html
