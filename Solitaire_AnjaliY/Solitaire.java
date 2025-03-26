import java.util.*;

/**
 * Solitaire is a class used for a game with one person with 
 one or more regular 52-card packs, part or all of which are usually 
 dealt out according to a given pattern, the object being to arrange the
 cards in a predetermined manner (from google)
 * @author Anjali Yella and collaboration with Claire Miao
 * @version 11/1/2023
 */
public class Solitaire
{
	public static void main(String[] args)
	{
		new Solitaire();
	}

	private Stack<Card> stock; 
	private Stack<Card> waste;
	private Stack<Card>[] foundations;
	private Stack<Card>[] piles;
	private SolitaireDisplay display;
    private static final boolean DEBUG = false;
    private int score;

    /**
     * Constructor for Solitaire class 
     */
	public Solitaire()
	{
		
		foundations  = new Stack[4];
		piles = new Stack[7];
		stock = new Stack <Card>();
		waste = new Stack <Card>();
        score = 0;

		for (int i = 0; i<foundations.length;i++)
		{
			foundations[i] = new Stack<Card>();
		}
		for (int i = 0; i<piles.length;i++)
		{
			piles[i] = new Stack<Card>();
		}

		createStock();
    
        
        if(DEBUG)
           specialdeal();
        else
            deal();


		display = new SolitaireDisplay(this);
        
	}
    /** 
     * extra method that calculates the players score, depending 
     * on certain moves in the game
     * 
     * @return score of the player
     */
    public int getScore()
    {
        return score;
    }
	/**
     * returns the card on top of the stock or null if the stock is empty
     * 
     * @return the stock card
     */
	public Card getStockCard()
	{
		if (! stock.isEmpty())
		{
			 return stock.peek();
		}
		return null;
		
	}

	/**
    * returns the card on top of the waste else null if waste stack is emtpy
    * @return the waste card
    */
	public Card getWasteCard()
	{
		if (! waste.isEmpty())
		{
			 return waste.peek();
		}
		return null;
	}

	/**
     * precondition:  0 <= index < 4
     * 
     * @param index which the foundation pile gets cards from 
     * @return the card from the foundation
     * postcondition: returns the card on top of the foundation,
     *  or null if foundation is empty 
     */
	public Card getFoundationCard(int index)
	{
		if (! foundations[index].isEmpty ())
		{
			 return foundations[index].peek();
		}
		return null;
	}

	/**
     * 
     * gets the piles of cards
     * 
     * precondition:  0 <= index < 7
     * @param index the index of piles
     * @return the stack of cards
     * postcondition: returns a reference to the given pile
    */
	public Stack<Card> getPile(int index)
	{
			 return piles[index];
	}

	/**
     * method that is called when the stock is clicked
     * performs certain methods to check
     */
	public void stockClicked()
	{
		if (display.isWasteSelected())
        {
            display.unselect();
        }
        if ((!display.isWasteSelected()) && (!display.isPileSelected()))
        {
            if (stock.isEmpty()) 
            {
                resetStock();
            }
            else 
            {
                dealThreeCards();
            }
        }
        System.out.println("stock clicked");
	}

	/**
     * method called when the waste is clicked
     */
	public void wasteClicked()
	{
		if ((!waste.isEmpty()) && 
        ((!display.isWasteSelected())||(!display.isPileSelected())))
        {
            display.selectWaste();
        }
        else
        {
            display.unselect();
        }
	}

	/**
     * called when given foundation is clicked
     * 
     * precondition:  0 <= index < 4
     * @param index the index of the pile clicked
     * 
     */
	public void foundationClicked(int index)
	{
		{
            if(display.isWasteSelected() 
            && canAddToFoundation(waste.peek(), index))
            {
                foundations[index].push(waste.pop()); 
                score += 5; 
            }
            if(display.isPileSelected())
            {
                Stack<Card> pile = piles[display.selectedPile()];
                if(canAddToFoundation(pile.peek(), index))
                {
                    foundations[index].push(pile.pop());
                    score += 5; 
                }
            }
            display.unselect();
        }
    //creates a stock with cards of different ranks
	}
	public void createStock()
    {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int rank = 1; rank<=13; rank++)
        {
            Card c1 = new Card(rank, "c");
            deck.add(c1);
            Card c2 = new Card(rank, "h");
            deck.add(c2);
            Card c3 = new Card(rank, "s");
            deck.add(c3);
            Card c4 = new Card(rank, "d");
            deck.add(c4);
        }
        while (!deck.isEmpty())
        {
            int index = (int)(Math.random()*deck.size());
            stock.push(deck.remove(index));
        }
    }
	  /**
     * precondition:  0 <= index < 7
     * @param index the index of which pile was clicked
     * 
     * method is called when pile is clicked
     */
	public void pileClicked(int index)
	{
		Stack<Card> pile = piles[index];
        int selected = display.selectedPile();
        if((!waste.isEmpty()) && (display.isWasteSelected()) 
        && (canAddToPile(waste.peek(), index)))
        {
            piles[index].push(waste.pop());
            score+=1;
            display.unselect();
            return;
        }

        if(!display.isPileSelected()
        && !display.isWasteSelected() 
        && !pile.isEmpty())
        {
            Card card=pile.peek(); 
            if(card.isFaceUp())
            {
                display.selectPile(index); 
            }
            else
            {
                card.turnUp(); 
            }
        }
        else if(selected==index)
        {
            display.unselect(); 
        }
        else
        {
            if (selected != -1)
            {
                Stack<Card> removed = removeFaceUpCards(selected);
                if(canAddToPile(removed.peek(), index))
                {
                    addToPile(removed, index); 
                    score+=1;
                }
                else
                {
                    addToPile(removed, selected); 
                }
                display.unselect(); 
            }
        }
        System.out.println("pile #" + index + " clicked");
        System.out.println("Your score is " + score + " !!");
	}

	/**
     * deals the cards from the piles
     */
    public void deal()
    {
        for (int i = 0; i<piles.length; i++)
        {
            for (int p = 0; p<=i; p++)
            {
                Card next = stock.pop();
                if (p==i)
                {
                    next.turnUp();
                }
                piles[i].push(next);
            }
        }
		
    }
    /**
     * special method that checks the moves of the queen and king 
     */
    public void specialdeal()
    {
        for (int i = 3; i<piles.length; i++)
        {
            for (int p = 0; p<=i; p++)
            {
                Card next = stock.pop();
                if (p==i)
                {
                    next.turnUp();
                }
                piles[i].push(next);

            }
            Card c5 = new Card(13, "h");
            piles[3].push(c5);
            Card c6 = new Card(12, "d");
            piles[4].push(c6);
        }
		
    }

	 /**
     * deals 3 cards from the stock
     */
    public void dealThreeCards()
    {
        int count = 0;
        while ((!stock.isEmpty()) && (count <=3))
        {
            Card next = stock.pop();
            next.turnUp();
            waste.push(next);
            count++;
        }
    }

	/**
     * resets stock of cards
	 * helper method
     */
    public void resetStock()
    {
        while (!waste.isEmpty())
        {
            Card next = waste.pop();
            next.turnDown();
            stock.push(next);
        }
    }
	/**
     * precondition: 0 <= index < 7
     * @param card which is card to be used
     * @param index which is the index to be looked at when adding
     * postcondition: Will return true if card can be added to pile
     * @return true if the given card can be added to the pile
     *         otherwise, false
     */
    private boolean canAddToPile(Card card, int index)
    {
        Stack<Card> pile = piles[index];
        if (pile.isEmpty())
        {
            return card.getRank()==13; 
        }
        boolean isRed = pile.peek().isRed();
        boolean isCardRed = card.isRed();
        int iRank = piles[index].peek().getRank();
        int cardRank = card.getRank();

        if (!pile.isEmpty())
        {
            Card topcard = pile.peek();
            if (isRed)
            {
                if((pile.peek().isFaceUp()) && (!isCardRed)
                && (cardRank == iRank-1))
                {
                    return true; 
                }
                return false; 
            }
            else if ((pile.peek().isFaceUp()) && (isCardRed) 
            && (cardRank == iRank-1))
            {
                return true; 
            }
            return false;
        }
        return false;
    }
	/**
     * precondition:  0 <= index < 7
     * @param cards which is the stack of cards
     * @param index the index to pick cards from 
     * postcondition: removes elements from cards and adds to specified pile
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while (!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }

	/**
     * precondition:  0 <= index < 7
     * @param index the index of the stack of cards
     * @return the stack of removed face up cards.
     * postcondition: removes all face up cards on top of pile and returns
     * a stack with these cards
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> place = new Stack<Card>();
        Stack<Card> pile = piles[index];
        while (!pile.isEmpty() && pile.peek().isFaceUp())
        {
            place.push(pile.pop());
        }
        return place;
    }

	/**
     * precondition:  0 <= index < 4
     * @param card which is the card being assessed 
     * @param index where the card will be added
     * postcondition: Returns true if the card can be moved to top of foundation
     * @return true if a card can be added to foundation;
     *         otherwise, false
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        int cardRank = card.getRank();
        if ((foundations[index].isEmpty()) && (cardRank==1))
        {
            return true;
        }
        int currRank = foundations[index].peek().getRank();
        String currSuit = foundations[index].peek().getSuit();
        if ((cardRank == currRank + 1) && (currSuit.equals(card.getSuit())))
        {
            return true;
        }
        return false;
    }
}