import java.util.Scanner;

/**
 * This class implements the parser for the game
 * @author Mmedara Josiah
 * @version 1.0
 */

public class Parser
{
    private CommandWords commands;
    private Scanner reader;

    /**
     * Constructor initializes objects needed to parse user input
     */
    public Parser(){
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Asks user for input, scans the input and extracts the first 4 words
     * @return returns the users command
     */
    public Command getCommand(){
        String input;
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;
        String word5 = null;
        String word6 = null;

        System.out.print("Input next command > ");
        input = reader.nextLine();

        Scanner tokenizer = new Scanner(input);
        if (tokenizer.hasNext())
        {
            word1 = tokenizer.next();      // get first word
            if (tokenizer.hasNext())
            {
                word2 = tokenizer.next(); //get Second word
                if (tokenizer.hasNext())
                {
                    word3 = tokenizer.next();      // get third word
                    if (tokenizer.hasNext())
                    {
                        word4 = tokenizer.next();  //get fourth word
                        if (tokenizer.hasNext())
                        {
                            word5 = tokenizer.next(); //get fifth word
                            if (tokenizer.hasNext())
                            {
                                word6 = tokenizer.next(); //get sixth word

                            }
                        }
                    }
                }
            }
        }

        if(commands.isCommand(word1))
        {
            return new Command(word1, word2, word3, word4, word5, word6);
        }
        else{
            return new Command(null, word2, word3, word4, word5, word6);
        }
    }
}

