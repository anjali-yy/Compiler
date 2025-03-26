
/**
 * Card is the class for Card objects which simulate playing cards in Solitaire.
 * They provide a variety of methods that help with the game
 *
 * @author Anjali Yella
 * @version 11/1/2022
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;
    
    /**
     * Constructor for objects of Card class
     * @param r the rank of the card
     * @param s the suit of the card
     */
    public Card (int r, String s)
    {
        rank = r;
        suit = s;
        isFaceUp = false;
    }
    
    /**
     * gets the rank of the card
     * @return the rank of the card
     */
    public int getRank()
    {
        return rank;
    }
    
    /**
     * gets the suit of the card
     * @return the suit of the card
     */
    public String getSuit()
    {
        return suit;
    }
    
    /**
     * determines whether the card is red or not
     * @return true if the card is red;
     *         otherwise, false
     */
    public boolean isRed()
    {
        if ((suit == "d") || (suit == "h"))
        {
            return true;
        }
        return false;
    }
    
    /**
     * determines whether the card is face up or not
     * @return true if the card is face up;
     *         otherwise, false
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }
    
    /**
     * turns the card to face up
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    /**
     * turns the card to face down
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    
    /**
     * gets the file name for the given card c using rank and suit
     * @param c the given card
     */
    public String getFileName()
    {
        String fileName = "cards/";
        if (!isFaceUp())
        {
            return "cards/back.gif";
        }
        else if (getRank() == 1)
        {
            fileName += "a";
        }
        else if (getRank() == 10)
        {
            fileName += "t";
        }
        else if (getRank() == 11)
        {
            fileName += "j";
        }
        else if (getRank() == 12)
        {
            fileName += "q";
        }
        else if (getRank() == 13)
        {
            fileName += "k";
        }
        else
        {
            fileName += getRank();
        }
        fileName += getSuit();
        return fileName + ".gif";
    }


}
