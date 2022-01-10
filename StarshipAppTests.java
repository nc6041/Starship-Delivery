import java.lang.invoke.MethodHandles;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class StarshipAppTests {



    //DATA WRANGLER TESTS: Natalie Cheng
    //1.dataWrangler_testEdgeExists()
    
    /**
     * Test if graph contains correct edge loaded in from test csv
     *
     * @throws FileNotFoundException
     */
    @Test
public void dataWrangler_testEdgeExists() throws FileNotFoundException {
    StarshipGraph graph = new StarshipGraph();
    StarshipLoader loadGraph = new StarshipLoader();
   graph = loadGraph.loadFile("StarshipTestFile.csv");
    assertTrue(graph.containsEdge("Memorial union", "Humphrey"));
}
    //2.dataWrangler_testWeight()

   /**
     * Test if graph returns correct weight from edges loaded in from test csv
     *
     * @throws FileNotFoundException
     */
    @Test
    public void dataWrangler_testWeight() throws FileNotFoundException {
        StarshipGraph graph = new StarshipGraph();
        StarshipLoader loadGraph = new StarshipLoader();
        graph = loadGraph.loadFile("StarshipTestFile.csv");
        assertEquals(10, graph.getWeight("Memorial union", "Humphrey"));
    }
    //3.dataWrangler_testCount()

   /**
     * Test if graph contains correct number of edges and vertices loaded in from test csv
     *
     * @throws FileNotFoundException
     */
    @Test
    public void dataWrangler_testCount() throws FileNotFoundException {
        StarshipGraph graph = new StarshipGraph();
        StarshipLoader loadGraph = new StarshipLoader();
        graph = loadGraph.loadFile("StarshipTestFile.csv");
        assertEquals(5, graph.getVertexCount());
        assertEquals(11, graph.getEdgeCount());
    }
    //4.dataWrangler_testExceptionThrown()

   /**
     * Test if FileNotFoundException is thrown when file is incorrect
     *
     * @throws FileNotFoundException
     */
    @Test
    public void dataWrangler_testExceptionThrown() throws FileNotFoundException {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            StarshipGraph graph = new StarshipGraph();
            StarshipLoader loadGraph = new StarshipLoader();
            graph = loadGraph.loadFile("StarshipFile.csv");
        });
        Assertions.assertEquals("File does not exist", thrown.getMessage());
    }
    //BACKEND TESTS: Lucas Chow
    //1.

    /**
     * Tests inserting and removing vertices
     */
    @Test
    public void backendTest1() {
        StarshipBackend graph = new StarshipBackend();

        graph.insertVertex("Pikachu");
        graph.insertVertex("Bulbasaur");
        graph.insertVertex("Charmander");
        graph.insertVertex("Squirtle");

        try {
            graph.insertVertex(null);
            fail("Didn't throw null pointer exception in backendTest1()");
        } catch (NullPointerException ignored) {
        }

        assertFalse(graph.isEmpty());
        assertEquals(graph.getVertexCount(), 4);

        assertTrue(graph.containsVertex("Pikachu"));
        assertTrue(graph.containsVertex("Bulbasaur"));
        assertTrue(graph.containsVertex("Charmander"));
        assertTrue(graph.containsVertex("Squirtle"));

        try {
            graph.removeVertex(null);
            fail("Didn't throw null pointer exception in backendTest1()");
        } catch (NullPointerException ignored) {
        }

        assertFalse(graph.removeVertex("Piplup"));
        assertTrue(graph.removeVertex("Pikachu"));
        assertTrue(graph.removeVertex("Bulbasaur"));
        assertTrue(graph.removeVertex("Charmander"));
        assertTrue(graph.removeVertex("Squirtle"));

        assertTrue(graph.isEmpty());
        assertEquals(graph.getVertexCount(), 0);

    }


    //2.

    /**
     * Tests inserting and removing edges
     */
    @Test
    public void backendTest2() {

        StarshipBackend graph = new StarshipBackend();

        graph.insertVertex("Pikachu");
        graph.insertVertex("Bulbasaur");
        graph.insertVertex("Charmander");
        graph.insertVertex("Squirtle");

        try {
            graph.insertEdge("Pikachu", null, 10);
            fail("Didn't throw null pointer exception in backendTest2()");
        } catch (NullPointerException ignored) {
        }

        try {
            graph.insertEdge(null, "Pikachu", 10);
            fail("Didn't throw null pointer exception in backendTest2()");
        } catch (NullPointerException ignored) {
        }

        try {
            graph.insertEdge("Piplup", "Pikachu", 10);
            fail("Didn't throw null pointer exception in backendTest2()");
        } catch (NullPointerException ignored) {
        }

        try {
            graph.insertEdge("Bulbasaur", "Pikachu", -10);
            fail("Didn't throw Illegal argument exception in backendTest2()");
        } catch (IllegalArgumentException ignored) {
        }

        assertTrue(graph.insertEdge("Pikachu", "Bulbasaur", 1));
        assertTrue(graph.insertEdge("Pikachu", "Charmander", 2));
        assertTrue(graph.insertEdge("Pikachu", "Squirtle", 3));

        try {
            graph.insertEdge("Pikachu", "Bulbasaur", 4);
            fail("Didn't throw Illegal argument exception in backendTest2()");
        } catch (IllegalArgumentException ignore) {
        }

        assertTrue(graph.getEdgeCountVertex("Pikachu") == 3);

        assertTrue(graph.containsEdge("Pikachu", "Bulbasaur"));
        assertTrue(graph.containsEdge("Pikachu", "Charmander"));
        assertTrue(graph.containsEdge("Pikachu", "Squirtle"));

        try {
            graph.removeEdge("Pikachu", null);
            fail("Didn't throw null pointer exception in backendTest2()");
        } catch (NullPointerException ignored) {
        }

        try {
            graph.removeEdge("Pikachu", "Piplup");
            fail("Didn't throw Illegal argument exception in backendTest2()");
        } catch (IllegalArgumentException ignored) {
        }

        assertTrue(graph.removeEdge("Pikachu", "Bulbasaur"));
        assertTrue(graph.removeEdge("Pikachu", "Charmander"));
        assertTrue(graph.removeEdge("Pikachu", "Squirtle"));

        assertTrue(graph.getEdgeCount() == 0);

    }

    //3.

    /**
     * Tests shortest path and shortest path costs
     */
    @Test
    public void backendTest3() {


        StarshipBackend graph = new StarshipBackend();

        graph.insertVertex("Pikachu");
        graph.insertVertex("Bulbasaur");
        graph.insertVertex("Charmander");
        graph.insertVertex("Squirtle");

        graph.insertEdge("Pikachu", "Bulbasaur", 1);
        graph.insertEdge("Pikachu", "Charmander", 5);
        graph.insertEdge("Pikachu", "Squirtle", 3);

        graph.insertEdge("Bulbasaur", "Charmander", 4);
        graph.insertEdge("Bulbasaur", "Squirtle", 4);

        graph.insertEdge("Charmander", "Pikachu", 5);
        graph.insertEdge("Charmander", "Squirtle", 7);
        graph.insertEdge("Charmander", "Bulbasaur", 3);

        graph.insertEdge("Squirtle", "Charmander", 2);

        ArrayList correctPath = new ArrayList();
        correctPath.add("Pikachu");
        correctPath.add("Squirtle");
        assertEquals(graph.shortestPath("Pikachu", "Squirtle"), correctPath);
        assertEquals(graph.getPathCost("Pikachu", "Squirtle"),3);
        correctPath.clear();

        correctPath.add("Charmander");
        correctPath.add("Bulbasaur");
        assertEquals(graph.shortestPath("Charmander", "Bulbasaur"), correctPath);
        assertEquals(graph.getPathCost("Charmander", "Bulbasaur"), 3);
        correctPath.clear();

        correctPath.add("Squirtle");
        correctPath.add("Charmander");
        correctPath.add("Pikachu");
        assertEquals(graph.shortestPath("Squirtle", "Pikachu"), correctPath);
        assertEquals(graph.getPathCost("Squirtle", "Pikachu"), 7);
        correctPath.clear();

    }



    // FRONTEND TESTS: Sabrina Huang
    //1.
    //2.
    //3.

    // INTEGRATION MANAGER TESTS: Daisy Chen
    //1. integrationManager_dataWranglerTest()
    //2. integrationManager_backEndTest()
    //3. integrationManager_frontEndTest()

     //1. Tests to see if loadFile method works with a smaller test file
    @Test
    public void integrationManager_dataWranglerTest() throws FileNotFoundException{
        StarshipLoader testLoader = new StarshipLoader();
        testLoader.loadFile("StarshipTestFile.csv");
        StarshipBackend backend = new StarshipBackend();
        assertEquals(11,backend.getEdgeCount());
        assertEquals(5, backend.getVertexCount());
    }

    //2. Tests shortest path from same graph as StarshipTestFile.csv
    @Test
    public void integrationManager_backEndTest() {
        StarshipBackend graph = new StarshipBackend();

        graph.insertVertex("Humph");
        graph.insertVertex("Mem U");
        graph.insertVertex("Pres");
        graph.insertVertex("SAC");
        graph.insertVertex("Humes");

        graph.insertEdge("Humph", "Mem U", 10);
        graph.insertEdge("Mem U", "Humph", 10);
        graph.insertEdge("Mem U", "Pres", 7);
        graph.insertEdge("Mem U", "SAC", 6);
        graph.insertEdge("Pres", "Mem U", 7);
        graph.insertEdge("Pres", "SAC", 4);
        graph.insertEdge("SAC", "Mem U", 6);
        graph.insertEdge("SAC", "Humes", 8);
        graph.insertEdge("Humes", "Humph", 21);
        graph.insertEdge("Humes", "Pres", 3);
        graph.insertEdge("Humes", "SAC", 8);

        ArrayList correctPath = new ArrayList();
        correctPath.add("Humes");
        correctPath.add("Pres");
        correctPath.add("Mem U");
        correctPath.add("Humph");
        assertEquals(graph.shortestPath("Humes", "Humph"), correctPath);

        ArrayList correctPath1 = new ArrayList();
        correctPath1.add("Humes");
        correctPath1.add("Pres");
        assertEquals(graph.shortestPath("Humes", "Pres"), correctPath1);
    }

    //3. Tests front end methods
    @Test public void integrationManager_frontEndTest() throws FileNotFoundException{
        StarshipFrontEnd front = new StarshipFrontEnd();
        assertTrue(front.insertNewLocation("bascom"));
        assertTrue(front.insertNewLocation("pres"));
        assertTrue(front.insertNewLocation("mem"));
        assertTrue(front.lookUpLocation("bascom"));
        assertTrue(front.insertNewEdge("bascom", "pres", 5));
        assertTrue(front.insertNewEdge("pres", "mem", 5));
        assertTrue(front.insertNewEdge("bascom", "mem", 3));
        assertEquals(5, front.getDistance("bascom", "pres"));
        assertEquals(3, front.getPathDistance("bascom", "mem"));
        assertTrue(front.changePath("pres"));

    }


    public static void main(String[] args) {
        String className = MethodHandles.lookup().lookupClass().getName();
        String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
        String[] arguments = new String[] {
                "-cp",
                classPath,
                "--include-classname=.*",
                "--select-class=" + className };
        ConsoleLauncher.main(arguments);
    }


}
