package edu.towson.cis.cosc442.project1.monopoly;

public class MockGUI implements MonopolyGUI {
    private boolean btnDrawCardState, btnEndTurnState, btnGetOutOfJailState;
    private boolean[] btnTradeState = new boolean[2];

    /**
     * Enables or prepares the end turn button for the specified player.
     * @param playerIndex the index of the player whose end turn button to enable
     */
    public void enableEndTurnBtn(int playerIndex) {
    }

    /**
     * Enables or sets the interface for the specified player's turn.
     * @param playerIndex the index of the player whose turn is being enabled
     */
    public void enablePlayerTurn(int playerIndex) {
    }

    /**
     * Enables the purchase button for the specified player.
     * @param playerIndex the index of the player to enable purchase button for
     */
    public void enablePurchaseBtn(int playerIndex) {
    }
	/**
	 * Returns a fixed dice roll result as an array of two integers.
	 * @return an array with two dice values representing the dice roll
	 */
	public int[] getDiceRoll() {
		int roll[] = new int[2];
		roll[0] = 2;
		roll[1] = 3;
		return roll;
	}

    /**
     * Checks if the draw card button is currently enabled.
     * @return true if the draw card button is enabled; false otherwise
     */
    public boolean isDrawCardButtonEnabled() {
        return btnDrawCardState;
    }

    /**
     * Checks if the end turn button is currently enabled.
     * @return true if the end turn button is enabled; false otherwise
     */
    public boolean isEndTurnButtonEnabled() {
        return btnEndTurnState;
    }
	
	/**
	 * Checks if the get out of jail button is currently enabled.
	 * @return true if the get out of jail button is enabled; false otherwise
	 */
	public boolean isGetOutOfJailButtonEnabled() {
		return btnGetOutOfJailState;
	}

    /**
     * Checks if the trade button for the specified player index is enabled.
     * @param i the player index for the trade button to check
     * @return true if the trade button is enabled for the specified index; false otherwise
     */
    public boolean isTradeButtonEnabled(int i) {
        return btnTradeState[i];
    }

    /**
     * Moves the player from one position to another on the game board.
     * @param index the index of the player to move
     * @param from the starting position index
     * @param to the destination position index
     */
    public void movePlayer(int index, int from, int to) {
    }

    /**
     * Opens a dialog to respond to a trade deal.
     * @param deal the trade deal to respond to
     * @return a respond dialog initialized with the trade deal
     */
    public RespondDialog openRespondDialog(TradeDeal deal) {
        RespondDialog dialog = new MockRespondDialog(deal);
        return dialog;
    }

    /**
     * Opens a dialog to initiate a trade between players.
     * @return a new trade dialog interface
     */
    public TradeDialog openTradeDialog() {
        TradeDialog dialog = new MockTradeDialog();
        return dialog;
    }

    /**
     * Sets whether the buy house button is enabled or disabled.
     * @param b true to enable the buy house button; false to disable it
     */
    public void setBuyHouseEnabled(boolean b) {
    }

    /**
     * Sets whether the draw card button is enabled or disabled.
     * @param b true to enable the draw card button; false to disable it
     */
    public void setDrawCardEnabled(boolean b) {
        btnDrawCardState = b;
    }

    /**
     * Sets whether the end turn button is enabled or disabled.
     * @param enabled true to enable the end turn button; false to disable it
     */
    public void setEndTurnEnabled(boolean enabled) {
        btnEndTurnState = enabled;
    }

    /**
     * Sets whether the get out of jail button is enabled or disabled.
     * @param b true to enable the get out of jail button; false to disable it
     */
    public void setGetOutOfJailEnabled(boolean b) {
    	this.btnGetOutOfJailState = b;
    }

    /**
     * Sets whether the purchase property button is enabled or disabled.
     * @param enabled true to enable the purchase property button; false to disable it
     */
    public void setPurchasePropertyEnabled(boolean enabled) {
    }

    /**
     * Sets whether the roll dice button is enabled or disabled.
     * @param b true to enable the roll dice button; false to disable it
     */
    public void setRollDiceEnabled(boolean b) {
    }

    /**
     * Sets whether the trade button is enabled or disabled for a specified player index.
     * @param index the player index for the trade button
     * @param b true to enable; false to disable the trade button
     */
    public void setTradeEnabled(int index, boolean b) {
        this.btnTradeState[index] = b;
    }

    /**
     * Shows the dialog for buying a house for the specified player.
     * @param currentPlayer the player currently taking action to buy a house
     */
    public void showBuyHouseDialog(Player currentPlayer) {
    }

    /**
     * Displays a message string to the user interface.
     * @param string the message to display
     */
    public void showMessage(String string) {
    }

	/**
	 * Returns a fixed utility dice roll value, typically for testing.
	 * @return an integer representing the utility dice roll result
	 */
	public int showUtilDiceRoll() {
//		int[] diceValues = GameMaster.instance().rollDice();
//		return diceValues[0] + diceValues[1];
		return 10;
	}

    /**
     * Starts or initializes the game interface and state.
     */
    public void startGame() {
    }

	/**
	 * Updates or refreshes the GUI state and display.
	 */
	public void update() {
	}
}
