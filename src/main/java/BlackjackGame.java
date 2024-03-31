import java.util.ArrayList;

public class BlackjackGame {

    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BlackjackDealer theDealer;
    BlackjackGameLogic gameLogic;
    double currentBet; //amount currently bet from user
    double totalWinnings; //the total amount the user has
    public BlackjackGame(BlackjackDealer theDealer, BlackjackGameLogic gameLogic) {
        this.theDealer = theDealer;
        this.gameLogic = gameLogic;
        this.playerHand = new ArrayList<>();
        this.bankerHand = new ArrayList<>();
    }
    public double evaluateWinnings(){
        /*
         * this method will determine if the user won or lost their bet
         * and return the amount won or lost based on the currentBet
         * if the player wins normally, return double the amount they bet (bet 100 -> win 200)
         * if the player wins by blackjack return their bet plus 150% of their bet ( bet 100 -> win 250)
         */

        if ((gameLogic.whoWon(playerHand, bankerHand)).equalsIgnoreCase("player")){
            if (gameLogic.handTotal(playerHand) == 21 && playerHand.size() == 2) { // blackjack win
                return currentBet * 2.50;
            }
            else { // normal win
                return currentBet * 2.0;
            }
        }
        else if ((gameLogic.whoWon(playerHand, bankerHand)).equalsIgnoreCase("dealer")){
            return 0.0;
        }
        else{
            return currentBet;             // if they push return the amount they bet
        }
    }


}
