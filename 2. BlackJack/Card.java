import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Card {
    public static final String [] SUIT = {"Clubs", "Diamonds", "Hearts", "Spades"};
    public static final String [] CARDNAME = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    public String cardname, suit;
    public int value1, value2; // store two possible values for aces

    // default constructor is not used in this implementation of blackjack
    public Card () {
        cardname = "Empty Card";
        suit = "No Suit";
        value1 = value2 = 0;
    }

    public Card (String cardname, String suit) {
        this.suit = suit;
        this.cardname = cardname;
        if (cardname.equals("Ace")){
            value1 = 1;
            value2 = 11;
        }
        else if (cardname.equals("Jack") || cardname.equals("Queen") || cardname.equals("King")){
            value1 = value2 = 10;
        }
        else value1 = value2 = Integer.parseInt(cardname);
    }

    public static void buildDeck(ArrayList<Card> deck) {
        Random rand = new Random();
        int randomNum, randomSuit;
        boolean needNewCard;
        for (int cnum = 1; cnum <= 52; cnum++){
            randomNum = rand.nextInt(13);
            randomSuit = rand.nextInt(4);
            needNewCard = false;

            // check if the newly randomized card is already in the deck
            while (true){
                for (int i = 0; i < deck.size(); i++){
                    if (deck.get(i).cardname.equals(CARDNAME[randomNum]) && deck.get(i).suit.equals(SUIT[randomSuit])){
                        needNewCard = true;
                        randomNum = rand.nextInt(13);
                        randomSuit = rand.nextInt(4);
                        break;
                    }
                }
                if (needNewCard != true){
                    break;
                }
                needNewCard = false;
            }
            deck.add(new Card(CARDNAME[randomNum], SUIT[randomSuit]));
        }
    }

    public static void initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
        deck.clear();
        buildDeck(deck);
        playerHand.clear();
        dealerHand.clear();
        
        for (int i = 0; i < 2; i++) playerHand.add(deck.get(i));
        for (int i = 0; i < 2; i++) deck.remove(0);
        for (int i = 0; i < 2; i++) dealerHand.add(deck.get(i));
        for (int i = 0; i < 2; i++) deck.remove(0);
    }

    public static void dealOne(ArrayList<Card> deck, ArrayList<Card> hand){
        hand.add(deck.get(0));
        deck.remove(0);
    }

    public static boolean checkBust(ArrayList<Card> hand){
        int totalV1 = 0;
        for (int i = 0; i < hand.size(); i++) totalV1 += hand.get(i).value1;
        return (totalV1 > 21);
    }

    // If the total is 17 or more, dealer stands. If the total is 16 or under, dealer hits, until the total is 17 or more. 
    // The first ace is counted as 11, and subsequent aces are counted as 1's.
    public static boolean dealerTurn(ArrayList<Card> deck, ArrayList<Card> hand){
        int handValue = 0, numAces = 0;
        for (int i = 0; i < hand.size(); i++){
            handValue += hand.get(i).value1;
            if (hand.get(i).cardname.equals("Ace")) numAces++;
        }
        if (numAces > 0 && handValue + 10 <= 21) handValue += 10;
        while (handValue < 17){
            handValue += deck.get(0).value1;
            if (deck.get(0).cardname.equals("Ace") && numAces == 0 && handValue + 10 <= 21){
                numAces++;
                handValue += 10;
            }
            dealOne(deck, hand);
        }
        return checkBust(hand);
    }

    // In case of a tie, the dealer wins. 
    // Player win --> returns 1
    // Dealer win --> returns 2
    public static int whoWins(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
        // Get the two possible total values of a hand.
        int dealerValue = 0, playerValue = 0, dealerAces = 0, playerAces = 0;
        for (int i = 0; i < dealerHand.size(); i++){
            dealerValue += dealerHand.get(i).value1;
            if (dealerHand.get(i).cardname.equals("Ace")) dealerAces++;
        }
        for (int i = 0; i < playerHand.size(); i++){
            playerValue += playerHand.get(i).value1;
            if (playerHand.get(i).cardname.equals("Ace")) playerAces++;
        }
        for (int i = 0; i < dealerAces; i++){
            if (dealerValue + 10 <= 21) dealerValue += 10;
        }
        for (int i = 0; i < playerAces; i++){
            if (playerValue + 10 <= 21) playerValue += 10;
        }
        if (playerValue > dealerValue) return 1;
        else return 2;
    }

    public static String displayCard(ArrayList<Card> hand){
        return hand.get(1).cardname + "-" + hand.get(1).suit;
    }

    public static String displayHand(ArrayList<Card> hand){
        String str = "";
        for (int i = 0; i < hand.size(); i++){
            str += hand.get(i).cardname + "-" + hand.get(i).suit + " ";
        }
        return str;
    }

    // run the game of blackjack until the player decides to stop
    public static void main(String[] args) {
        int playerChoice, winner;
        ArrayList<Card> deck = new ArrayList<Card> ();

        buildDeck(deck);

        playerChoice = JOptionPane.showConfirmDialog(null, "Ready to Play Blackjack?", "Blackjack", JOptionPane.OK_CANCEL_OPTION);

        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
            System.exit(0);

        Object[] options = {"Hit","Stand"};
        boolean isBusted, dealerBusted;
        boolean isPlayerTurn;
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> dealerHand = new ArrayList<>();

        do{ // Game loop
            initialDeal(deck, playerHand, dealerHand);
            isPlayerTurn=true;
            isBusted=false;
            dealerBusted=false;

            while(isPlayerTurn){

                // Shows the hand and prompts player to hit or stand
                playerChoice = JOptionPane.showOptionDialog(null,"Dealer shows " + displayCard(dealerHand) + "\n Your hand is: " + displayHand(playerHand) + "\n What do you want to do?","Hit or Stand",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

                if(playerChoice == JOptionPane.CLOSED_OPTION)
                    System.exit(0);

                else if(playerChoice == JOptionPane.YES_OPTION){
                    dealOne(deck, playerHand);
                    isBusted = checkBust(playerHand);
                    if(isBusted){
                        playerChoice = JOptionPane.showConfirmDialog(null,"Player has busted!", "You lose", JOptionPane.OK_CANCEL_OPTION);

                        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                            System.exit(0);

                        isPlayerTurn=false;
                    }
                }

                else{
                    isPlayerTurn=false;
                }
            }
            if(!isBusted){ // Continues if player hasn't busted
                dealerBusted = dealerTurn(deck, dealerHand);
                if(dealerBusted){
                    playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " + displayHand(playerHand) + "\nThe dealer busted.\n Congrautions!", "You Win!!!", JOptionPane.OK_CANCEL_OPTION);            

                    if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                        System.exit(0);
                }

                else{ //The Dealer did not bust. The winner must be determined
                    winner = whoWins(playerHand, dealerHand);

                    if(winner == 1){ //Player Wins
                        playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " + displayHand(playerHand) + "\n Congrautions!", "You Win!!!", JOptionPane.OK_CANCEL_OPTION);

                        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                            System.exit(0);
                    }

                    else{ //Player Loses
                        playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " + displayHand(playerHand) + "\n Better luck next time!", "You lose!!!", JOptionPane.OK_CANCEL_OPTION); 

                        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                            System.exit(0);
                    }
                }
            }
        }while(true);
    }
}
