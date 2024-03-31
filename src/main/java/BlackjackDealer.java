import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class BlackjackDealer {

    //variables that will be used by the entire class
    ArrayList<Card> deck = new ArrayList<Card>();
    Random rand = new Random();


    public void generateDeck(){
        /*
         * this should generate 52 cards, one for each of the 13 faces and 4 suits
         */

//        ArrayList<Card> deck = new ArrayList<Card>();

        for (int i = 1; i <= 13; i++){            // loops through each card and each suit
            for (int j = 0; j < 4; j++){
                switch(j){
                    case 0: deck.add( new Card("Spades", i));
                            break;
                    case 1: deck.add( new Card("Clubs", i));
                            break;
                    case 2: deck.add( new Card("Diamonds", i));
                            break;
                    case 3: deck.add( new Card("Hearts", i));
                            break;

                }
            }
        }

    }
    public ArrayList<Card> dealHand(){
        /*
         * this will return an Arraylist of two cards
         * and leave the remainder of the deck able to be drawn later.
         */
        ArrayList<Card> myHand = new ArrayList<Card>();

        if(deck.size() >= 2){           // rand.nextInt returns a random number between position 0 and 51
            for (int i = 0; i < 2; i++){
                myHand.add(drawOne());
            }
        }

        return myHand;

    }

    public Card drawOne(){
        /*
         * this will return the next card on top of the deck
         */
        if (deck.isEmpty()){
            generateDeck();
        }
        return deck.remove(0);
    }

    public void shuffleDeck(){

        int curr = 0;
        generateDeck();

        for (int i = 0; i < deck.size(); i++){
            int randIndex = rand.nextInt(deck.size());
            Card temp = deck.get(i);          // creating a temp card to swap
            deck.set(i, deck.get(randIndex)); // swapping the card at the i-th position with a card at a random position
            deck.set(randIndex, temp);
        }
    }

    public int deckSize()
    {
        return deck.size();
    }

}
