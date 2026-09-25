package edu.towson.cis.cosc442.project1.monopoly;

public class TradeDeal {
    private int amount;
    private int playerIndex;
    private String propertyName;

    /**
     * Returns the amount of money involved in the trade deal.
     * @return the monetary amount of the trade
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * Retrieves the index of the player offering the property in the trade.
     * @return the player index representing the seller
     */
    public int getPlayerIndex() {
        return playerIndex;
    }
    
    /**
     * Returns the name of the property involved in the trade deal.
     * @return the name of the property to be traded
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * Constructs a message describing the trade proposal to the current player.
     * @return a formatted string message of the trade offer
     */
    public String makeMessage() {
        String message = GameMaster.instance().getCurrentPlayer() + 
        	" wishes to purchase " +
        	propertyName + " from " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	" for " + amount + ".  " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	", do you wish to trade your property?";
        return message;
    }
    
    /**
     * Sets the amount of money proposed for the trade deal.
     * @param amount the monetary amount to set for the trade
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    /**
     * Assigns the property name to be traded in this deal.
     * @param propertyName the name of the property to set for the trade
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * Sets the index of the player who is selling the property in the trade.
     * @param playerIndex the index identifying the seller player
     */
    public void setSellerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
