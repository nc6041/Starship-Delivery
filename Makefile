run: compile
	java StarshipApp

compile:
	javac StarshipLoader.java
	javac GraphADT.java
	javac StarshipGraph.java
	javac StarshipBackend.java
	javac StarshipFrontEnd.java
	javac StarshipApp.java

test: compileTest
	java -jar junit5.jar -cp . -c StarshipAppTests
	java TextUITester.java

compileTest: compile
	javac -cp .:junit5.jar StarshipAppTests.java
	javac TextUITester.java

clean:
	rm *.class
