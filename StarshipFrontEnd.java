import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

interface StarshipFrontEndInterface {

  // Prints out each location vertex you go through when given source and target
  public String printPath(String source, String target);

  // Changes the paths when given a location to remove
  public boolean changePath(String removal);

  // Gives the time it takes to get from source and target
  public double printTime(String source, String target);

  // Runs the program
  public void runner(StarshipBackend engine);
  // Prompts:
  // 1. Where would you like to get your order from?
  // 2. Insert destination
  // Command Menu:
  // 1. Print Path
  // 2. Change Path
  // 3. Print time it takes

  // Quits the program
  public void quit();
}


public class StarshipFrontEnd implements StarshipFrontEndInterface {
  Scanner scnr = new Scanner(System.in);
  StarshipBackend backend = new StarshipBackend();

  /*
   * Public method that inserts new location into graph
   * @param user input new location
   * @return true if new location has been inserted, false if location is already in the graph
   */
  public boolean insertNewLocation(String location) {
    return backend.insertVertex(location);
  }

  /*
   * Public method that looks up the location
   * @param user input location
   * @return true if location is within the graph, false if it is not
   */
  public boolean lookUpLocation(String location) {
    return backend.containsVertex(location);
  }
  
  /*
   * Public method that inserts edge into the graph
   * @param user input start location
   * @param user input end location
   * @param distance of the new edge
   * @return true if new edge has been inserted, false if any problems occur
   */
  public boolean insertNewEdge(String start, String end, int distance) {
    return backend.insertEdge(start, end, distance);
  }

  /*
   * Returns distance between two nodes that have an edge between them
   * @param user input start location
   * @param user input end location
   * @return distance between two locations
   */
  public int getDistance(String start, String end) {
    return backend.getWeight(start, end);
  }

  /*
   * Returns shortest distance in units from start to end path
   * @param user input start location
   * @param user input end location
   * @return shortest distance between start and end of distance
   */
  public int getPathDistance(String start, String end) {
    return backend.getPathCost(start, end);
  }

  /*
   * Prints the path from source to target as a string
   * @param user input start location
   * @param user input end location
   * @return String of the shortest pathway from source to target
   */
  @Override
  public String printPath(String source, String target) {
    List shortestPath = backend.shortestPath(source, target);
    return shortestPath.toString();
  }

  /*
   * Changes the graph and removes a vertex
   * @param removal - user input of location that should be removed
   * @return true if the data removed is removed, false if there is any problems
   */
  @Override
  public boolean changePath(String removal) {
    return backend.removeVertex(removal);
  }

  /*
   * Prints the time it takes to reach from source to target, each unit is 1 min
   * @param source - user input start location
   * @param target - user input end location
   * @return time it takes to get from source to target
   */
  @Override
  public double  printTime(String source, String target) {
    double time = getPathDistance(source, target) * 0.1;
    return time;
  }

  /**
   * Quits program and closes the scanner
   */
  @Override
  public void quit() {
    System.out.println("Quitting...");
    scnr.close();
  }

  /*
   * Private helper method that prints the main menu.
   */
  private void printMainMenu() {
    System.out.println("Please input the number that corresponds to what you would like to do.\n"
        + "Starship Commands:\n" 
        + "     1. Insert a new location\n" 
        + "     2. Remove a location\n"
        + "     3. Insert a new edge\n" 
        + "     4. Look up location\n"
        + "     5. Find distance between two locations\n"
        + "     6. Print shortest path and distance\n" 
        + "     7. Print time\n" 
        + "     8. Quit");
    // print list of locations?
  }

  /**
   * Runs the loop where user will choose options and based on user input, will conduct the desired methods
   */
  @Override
  public void runner(StarshipBackend engine) {
    backend = engine;
    boolean run = true;
    printMainMenu();
    int userVal = 0;

    while (userVal == 0) {
      try {
        userVal = scnr.nextInt();
        scnr.nextLine();
      } catch (InputMismatchException ignore) {
        System.out.println(
            "Invalid input. Enter a number from 1-8 corresponding to the command you would like to execute.");
        printMainMenu();
        scnr.nextLine();
      }
    }

    while (run == true) {
      try {
        if (userVal == 1) {
          System.out.println("What is the name of the location you would like to add");
          String location = scnr.nextLine();
          if (insertNewLocation(location)) {
            System.out.println("Location added");
          }
          else {
            System.out.println("Location is already in the graph");
          }
          
        } else if (userVal == 2) {
          System.out.println("What is the name of the location you would like to remove");
          String location = scnr.nextLine();
          if (changePath(location)){
            System.out.println("Location removed");
          }
          else {
            System.out.println("Location to be removed cannot be found");
          }
        } else if (userVal == 3) {
          System.out.println("Where is your starting location?");
          String startingLocation = scnr.nextLine();
          System.out.println("Where is your target location?");
          String targetLocation = scnr.nextLine();
          System.out.println("How many units away are these two locations?");
          int distance = scnr.nextInt();
          if (insertNewEdge(startingLocation, targetLocation, distance)) {
            System.out.println("New Edge inserted");
          } else {
            System.out.println("Edge cannot be inserted");
          }
        } else if (userVal == 4) {
          System.out.println("What location are you looking for?");
          String location = scnr.nextLine();
          if (lookUpLocation(location) == true) {
            System.out.println("Location exists");
          }else {
            System.out.println("Location does not exist");
          }
        } else if (userVal == 5) {
          System.out.println("What is your start location?");
          String startLocation = scnr.nextLine();
          System.out.println("What is your end location?");
          String endLocation = scnr.nextLine();
          int distance = getDistance(startLocation, endLocation);
          System.out.println(startLocation + " from " + endLocation + " is " + distance
              + " units far from each other");
        } else if (userVal == 6) {
          System.out.println("What is your start location?");
          String startLocation = scnr.nextLine();
          System.out.println("What is your end location?");
          String endLocation = scnr.nextLine();
          System.out.println("The shortest path from " + startLocation + " to " + endLocation
              + " is " + printPath(startLocation, endLocation) + " and it is "
              + getPathDistance(startLocation, endLocation));
        } else if (userVal == 7) {
          System.out.println("What is your start location?");
          String startLocation = scnr.nextLine();
          System.out.println("What is your end location?");
          String endLocation = scnr.nextLine();
          System.out.println(printTime(startLocation, endLocation) + " min");
        } else if (userVal == 8){
          run = false;
          quit();
        } else {
          System.out.println(
              "Invalid input. Enter a number from 1-8 corresponding to the command you would like to execute.");
        }
        if (run) {
          printMainMenu();
          userVal = scnr.nextInt();
          scnr.nextLine();
      }
        
      } catch (InputMismatchException ignore) {
        System.out.println("Invalid input. Try again.");
        printMainMenu();
        userVal = scnr.nextInt();
        scnr.nextLine();
      } catch (IllegalArgumentException ignore) {
        System.out.println("Location does not exist");
        printMainMenu();
        userVal = scnr.nextInt();
        scnr.nextLine();
      } catch (NullPointerException ignore) {
        System.out.println("Cannot use null location");
        printMainMenu();
        userVal = scnr.nextInt();
        scnr.nextLine();
      }catch (NoSuchElementException ignore) {
        System.out.println("Element does not exist");
        printMainMenu();
        userVal = scnr.nextInt();
        scnr.nextLine();
      }
    }
  }



}


