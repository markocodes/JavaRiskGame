/**
 * Main class to initate the game
 * @author Mmedara Josiah
 * @version 1.0
 */
public class Main
{
    public static void main(String[] args){
        Game model = new Game();
        RiskView view = new RiskView();
        RiskController controller = new RiskController(model, view);
    }
}

