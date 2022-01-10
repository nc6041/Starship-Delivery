import java.util.List;

public class StarshipApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Starship Order App");
        StarshipBackend engine = new StarshipBackend();
	engine.load("StarshipInfo.csv");
        StarshipFrontEndInterface ui = new StarshipFrontEnd();
        ui.runner(engine);
    }

}
