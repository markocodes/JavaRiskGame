/**
 * This class creates commands using command words
 * @author Mmedara Josiah
 * @version 1.0
 */
public class Command
{
    private String firstWord;
    private String secondWord;
    private String thirdWord;
    private String fourthWord;
    private String fifthWord;
    private String sixthWord;

    /**
     * Constructor creates a new command
     * @param firstWord is the first word in the command
     * @param secondWord is the second word in the command
     * @param thirdWord is the third word in the command
     * @param fourthWord is the fourth word in the command
     * @param fifthWord is the fifth word in the command
     * @param sixthWord is the sixth word in the command
     */
    public Command(String firstWord, String secondWord,
                   String thirdWord, String fourthWord,
                   String fifthWord, String sixthWord){
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.fourthWord = fourthWord;
        this.fifthWord = fifthWord;
        this.sixthWord = sixthWord;
    }

    /**
     * returns the first word in the command
     * @return returns the first word
     */
    public String getFirstWord()
    {
        return firstWord;
    }

    /**
     * returns the second word in the command
     * @return returns the second word
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * returns the third word in the command
     * @return returns the third word
     */
    public String getThirdWord()
    {
        return thirdWord;
    }

    /**
     * returns the fourth word in the command
     * @return returns the fourth word
     */
    public String getFourthWord()
    {
        return fourthWord;
    }

    /**
     * returns the fifth word in the command
     * @return returns the fifth word
     */
    public String getFifthWord()
    {
        return fourthWord;
    }

    /**
     * returns the fifth word in the command
     * @return returns the fifth word
     */
    public String getSixthWord()
    {
        return sixthWord;
    }

    /**
     * Returns true if the command has a second word
     * @return true if the command has a second word
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }

    /**
     * Returns true if the command has a third word
     * @return true if the command has a third word
     */
    public boolean hasThirdWord()
    {
        return (secondWord != null);
    }

    /**
     * Returns true if the command has a fourth word
     * @return true if the command has a fourth word
     */
    public boolean hasFourthWord()
    {
        return (fourthWord != null);
    }

    /**
     * Returns true if the command has a fifth word
     * @return true if the command has a fifth word
     */
    public boolean hasFifthWord()
    {
        return (fifthWord != null);
    }

    /**
     * Returns true if the command has a fifth word
     * @return true if the command has a fifth word
     */
    public boolean hasSixthWord()
    {
        return (fifthWord != null);
    }

    /**
     * Returns true if the first word is null
     * @return true if the first word is null
     */
    public boolean isUnknown(){
        return firstWord==null;
    }

    public String getCommand(){
        return getFirstWord();
    }
}
