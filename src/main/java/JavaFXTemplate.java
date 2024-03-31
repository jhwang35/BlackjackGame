import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	 BlackjackDealer dealer = new BlackjackDealer();
	 BlackjackGameLogic logic = new BlackjackGameLogic();
	 BlackjackGame game = new BlackjackGame(dealer,logic);
	 ArrayList<Card> playerHand = new ArrayList<Card>();
	 ArrayList<Card> bankerHand = new ArrayList<Card>();
	 BorderPane borderPane, betPane;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Justin Hwang Homework 4");

		Scene mainMenu;
		dealer.generateDeck();
		dealer.shuffleDeck();

        playerHand = dealer.dealHand();
		bankerHand = dealer.dealHand();



		// Main scene
		HBox hbox = new HBox();
		hbox.setId("hbox");

		VBox welcomeBox = new VBox();
		welcomeBox.setId("welcomeBox");

		Button startBtn = new Button("Start Playing");
		startBtn.setId("btn");

		TextField money = new TextField();
		money.setAlignment(Pos.CENTER);
		money.setId("money");

		hbox.getChildren().addAll(money,startBtn);
		hbox.setAlignment(Pos.CENTER_RIGHT);

		// text for welcome
		Text textWelcome = new Text("Welcome to Blackjack!");
		textWelcome.setId("textWelcome"); // setting ID for css style sheet

		//text for rules
		Text rules = new Text("Place a bet in the betting areas marked on the table.\n" +
				"You are dealt two cards whilst the dealer is dealt one face up. \n" +
				"If your first 2 cards add up to 21 (an Ace and a card valued 10), that’s Blackjack!\n" +
				"If you have any other total, decide whether you wish to ‘draw’ or ‘stay’. \n" +
				"You can continue to draw cards until you are happy with your hand.\n" +
				"Once you have have taken your turn the dealer draws another card for their hand. They must draw until they reach 17 or more.\n" +
				"Once all cards are drawn, whoever has a total closer to 21 than the dealer wins. If player’s hand and dealer’s hand have an equal value, it’s a tie.\n" +
				"After each round you will be brought back to the bet screen to replace your bets \n" +
				"Once you run out of money you will return to this page \n" +
				"\n Please enter the amount you would like to start with below"
		);
		rules.setId("rules"); // setting ID for css style sheet
		welcomeBox.getChildren().addAll(textWelcome,rules);
		welcomeBox.setAlignment(Pos.TOP_CENTER);

		//creating the borderpane for all scenes
		borderPane = new BorderPane();
		borderPane.setId("borderPane"); // setting ID for css style sheet
		borderPane.setBottom(hbox); //putting things inside the borderpane
		borderPane.setTop(welcomeBox);

		mainMenu = new Scene(borderPane, 1000,1000);
		String css = this.getClass().getResource("styles.css").toExternalForm();
		mainMenu.getStylesheets().add(css);


		//Bet Pane
		betPane = new BorderPane();
		betPane.setId("borderPane");

		//making boxes for button
		HBox confirmBox = new HBox();
		confirmBox.setId("confirmBox");
		confirmBox.setAlignment(Pos.BOTTOM_RIGHT);
		Button confirmBtn = new Button("Confirm Bet");
		confirmBtn.setId("btn");

		TextField remainBetPage = new TextField();
		remainBetPage.setId("moneyRemaining");
		remainBetPage.setEditable(false);
		remainBetPage.setAlignment(Pos.CENTER);
		confirmBox.getChildren().addAll(remainBetPage, confirmBtn);


		//box and buttons for textfields

		VBox enter = new VBox();
		enter.setId("enterBox");
		enter.setAlignment(Pos.TOP_CENTER);
		Text instruction = new Text("Enter your bet amount in the box below");
		instruction.setId("instruction");
		TextField bet = new TextField();
		bet.setId("bet");
		bet.setAlignment(Pos.CENTER);
		enter.getChildren().addAll(instruction,bet);

		//setting location in bet pane
		betPane.setBottom(confirmBox);
		betPane.setTop(enter);




		// Game Pane
		BorderPane gamePane = new BorderPane();
		gamePane.setId("borderPane");

		HBox gameBox = new HBox();
		TextField remainGamePage = new TextField();
		remainGamePage.setId("remainGamePage");
		remainGamePage.setEditable(false);
		remainGamePage.setAlignment(Pos.CENTER);


		//put box into pane
		gameBox.getChildren().addAll(remainGamePage);
		gameBox.setAlignment(Pos.TOP_RIGHT);

		// creating hit or stay buttons
		HBox playBox = new HBox();
		Button hitBtn = new Button("Hit");
		Button stayBtn = new Button("Stay");
		hitBtn.setId("playBtn");
		stayBtn.setId("playBtn");
		Text outcome = new Text();
		outcome.setId("lose");
		playBox.getChildren().addAll(hitBtn,stayBtn,outcome);
		playBox.setAlignment(Pos.BOTTOM_LEFT);

		//displaying cards
		HBox bankerCards = new HBox();
		Text dealCard = new Text("Dealer's Cards: ");
		dealCard.setId("rules");
		bankerCards.getChildren().addAll(dealCard);
		bankerCards.setAlignment(Pos.TOP_CENTER);

		HBox playerCards = new HBox();
		Text playerText = new Text("Player's Cards: ");
		playerText.setId("rules");
		playerCards.getChildren().add(playerText);
		playerCards.setAlignment(Pos.BOTTOM_CENTER);

		gamePane.setTop(bankerCards);
		gamePane.setBottom(playBox);
		gamePane.setCenter(playerCards);
		gamePane.setRight(gameBox);


		//Buttons
		//transitions from main menu to bet pane
		//once the confirm button is hit we start to display two cards for us
		startBtn.setOnAction(e-> { mainMenu.setRoot(betPane);
			game.totalWinnings = Double.parseDouble(money.getText());
			remainBetPage.setText("Money Remaining: $" + game.totalWinnings);});

		confirmBtn.setOnAction(e-> {mainMenu.setRoot(gamePane);
			if (game.totalWinnings < Double.parseDouble(bet.getText())){
				PauseTransition pause = new PauseTransition(Duration.seconds(3));
				outcome.setText("NOT ENOUGH MONEY, RETURNING TO MAIN MENU");
				resetScene(mainMenu, borderPane, remainBetPage, outcome, bankerCards, dealCard, playerCards, playerText, pause);
			}
			else {
				game.totalWinnings -= Double.parseDouble(bet.getText());
				remainGamePage.setText("Money Remaining: $" + game.totalWinnings);
			}

			if (!bankerHand.isEmpty()) {
				Card card = bankerHand.get(bankerHand.size() - 1);
				ImageView playerCardImg = new ImageView(new Image(getPath(card)));
				playerCardImg.setPreserveRatio(true);
				playerCardImg.setFitHeight(200);
				bankerCards.getChildren().add(playerCardImg);

			}


			for(Card card : playerHand) {
				ImageView playerCardImg = new ImageView(new Image(getPath(card)));
				playerCardImg.setPreserveRatio(true);
				playerCardImg.setFitHeight(200);
				playerCards.getChildren().add(playerCardImg);

			}});

			//display another card
			hitBtn.setOnAction(e->{
				drawDisplay(playerCards, playerHand);
				if (logic.handTotal(playerHand) > 21){
					outcome.setText("YOU LOST" + "\n" + "RETURN TO BET MENU IN 2 SECONDS" );
					PauseTransition pause = new PauseTransition(Duration.seconds(2));
					resetScene(mainMenu, betPane, remainBetPage, outcome, bankerCards, dealCard, playerCards, playerText, pause);

				}
			});

			//when we stay the dealer plays
			stayBtn.setOnAction(e->{
				while (logic.evaluateBankerDraw(bankerHand)){
					drawDisplay(bankerCards,bankerHand);
					}
				int playerTotal = logic.handTotal(playerHand);
				int bankerTotal = logic.handTotal(bankerHand);

				if (playerTotal > bankerTotal || bankerTotal > 21) {
					// Player wins
					// Update player's balance and display result
					game.totalWinnings += game.evaluateWinnings();
					outcome.setId("win");
					if (bankerTotal > 21) {
						outcome.setText("YOU WIN (Dealer Busts)" + "\n" + "RETURN TO BET MENU IN 2 SECONDS");
					} else {
						outcome.setText("YOU WIN" + "\n" + "RETURN TO BET MENU IN 2 SECONDS");
					}
				} else if (playerTotal < bankerTotal) {

					outcome.setId("lose");
					outcome.setText("YOU LOSE" + "\n" + "RETURN TO BET MENU IN 2 SECONDS");
				} else {
					game.totalWinnings += game.evaluateWinnings();
					outcome.setId("push");
					outcome.setText("PUSH" + "\n" + "RETURN TO BET MENU IN 2 SECONDS");
				}
				PauseTransition pause = new PauseTransition(Duration.seconds(6));
				if (game.totalWinnings <= 0){
					resetScene(mainMenu, borderPane, remainBetPage, outcome, bankerCards, dealCard, playerCards, playerText, pause);
				}
				else{
					resetScene(mainMenu, betPane, remainBetPage, outcome, bankerCards, dealCard, playerCards, playerText, pause);
				}

			});




		primaryStage.setScene(mainMenu);
		primaryStage.setMaximized(true);
		primaryStage.show();




	}



	private void resetScene(Scene mainMenu, BorderPane pane, TextField remainBetPage, Text outcome, HBox bankerCards, Text dealCard, HBox playerCards, Text playerText, PauseTransition pause) {
		pause.setOnFinished(f ->{
			mainMenu.setRoot(pane);
			remainBetPage.setText("Money Remaining: $" + game.totalWinnings);
			playerCards.getChildren().clear();
			bankerCards.getChildren().clear();
			outcome.setText("");
			playerCards.getChildren().add(playerText);
			bankerCards.getChildren().add(dealCard);
			playerHand = dealer.dealHand();
			bankerHand = dealer.dealHand();

		});
		pause.play();
	}

	private void drawDisplay(HBox cards, ArrayList<Card> hand) {
		hand.add(dealer.drawOne());
		Card card = hand.get(hand.size() - 1);
		ImageView playerCardImg = new ImageView(new Image(getPath(card)));
		playerCardImg.setPreserveRatio(true);
		playerCardImg.setFitHeight(200);
		cards.getChildren().add(playerCardImg);
	}

	private String getPath(Card card){
		if (card.value == 1){
			return "ace_of_" + card.suit.toLowerCase() + ".png";
		}
		else if (card.value == 11){
			return "jack_of_" + card.suit.toLowerCase() + ".png";
		}
		else if (card.value == 12 ){
			return "queen_of_" + card.suit.toLowerCase() + ".png";
		}
		else if(card.value == 13){
			return "king_of_" + card.suit.toLowerCase() + ".png";
		}
		else{
			return  card.value + "_of_" + card.suit.toLowerCase() + ".png";
		}
	}

}

