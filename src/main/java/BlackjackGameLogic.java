import java.util.ArrayList;

public class BlackjackGameLogic{


    public String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand){

        if (playerHand1 == null || dealerHand == null) {
            throw new IllegalArgumentException("Hands cannot be null");
        }

        // given two hands this should return either player or dealer or push depending on who wins

        int playerTotal = handTotal(playerHand1);
        int dealerTotal = handTotal(dealerHand);

        if (playerTotal > dealerTotal){
            return "player";
        }
        else if (dealerTotal > 21){
            return "player";
        }
        else if (playerTotal < dealerTotal){
            return "dealer";
        }
        else{
            return "push";
        }
    }

    public int handTotal(ArrayList<Card> hand){
        /* this should return the total value of all cards in the hand
         *  handle blackjack
         *  add 10 for facecards ie (values over 10)
         *  handle aces to change if needed
         */

        int totalValue = 0;
        int aces = 0;

        for (Card card : hand){               // loops through each card in the ArrayList and adds it to the total
            if (card.value > 10){
                totalValue += 10;             // add 10 if we get face cards
            }
            else if (card.value == 1){ // counter for aces
                    totalValue += 1;  // will add 1 first and change accordingly
                    aces ++;
            }
            else {
                totalValue += card.value;
            }
        }

        while (aces > 0 && totalValue <= 11){ // while we have aces and the value wont bust
            totalValue += 10;                // add 10 to the ace to make it 11
            aces--;
        }

        return totalValue;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand){
        /*
         * this method should return true if the dealer should draw another card
         * ie if the value is 16 or less
         */

        return handTotal(hand) <= 16;
    }

}
