package edu.towson.cis.cosc442.project1.monopoly;

public class PropertyCell extends Cell {
	private String colorGroup;
	private int housePrice;
	private int numHouses;
	private int rent;
	private int sellPrice;

	/**
	 * Returns the color group of this property cell.
	 * @return the color group as a String
	 */
	public String getColorGroup() {
		return colorGroup;
	}

	/**
	 * Returns the price of a house on this property.
	 * @return the cost to buy a house on this property
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * Returns the number of houses currently built on this property.
	 * @return the number of houses
	 */
	public int getNumHouses() {
		return numHouses;
	}
    
    /**
     * Returns the selling price of this property.
     * @return the sell price as an integer
     */
    public int getPrice() {
		return sellPrice;
	}

	/**
	 * Calculates and returns the rent owed for this property based on ownership and houses.
	 * @return the rent amount to be paid
	 */
	public int getRent() {
		int rentToCharge = rent;
		String [] monopolies = theOwner.getMonopolies();
		rentToCharge = calculateMonopoliesRent(rentToCharge, monopolies);
		if(numHouses > 0) {
			rentToCharge = rent * (numHouses + 1);
		}
		return rentToCharge;
	}

	/**
	 * Calculates the rent to charge if the owner holds a monopoly on this property's color group.
	 * @param rentToCharge the base rent before monopoly adjustment
	 * @param monopolies array of color groups where the owner has monopolies
	 * @return the adjusted rent after considering monopolies
	 */
	private int calculateMonopoliesRent(int rentToCharge, String[] monopolies) {
		for(int i = 0; i < monopolies.length; i++) {
			if(monopolies[i].equals(colorGroup)) {
				rentToCharge = rent * 2;
			}
		}
		return rentToCharge;
	}

	/**
	 * Executes the action that occurs when a player lands on this property cell.
	 */
	public void playAction() {
		Player currentPlayer = null;
		if(!isAvailable()) {
			currentPlayer = GameMaster.instance().getCurrentPlayer();
			if(theOwner != currentPlayer) {
				currentPlayer.payRentTo(theOwner, getRent());
			}
		}
	}

	/**
	 * Sets the color group of this property cell.
	 * @param colorGroup the color group to assign
	 */
	public void setColorGroup(String colorGroup) {
		this.colorGroup = colorGroup;
	}

	/**
	 * Sets the price for building a house on this property.
	 * @param housePrice the cost to set for houses
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Sets the number of houses currently built on this property.
	 * @param numHouses the number of houses to set
	 */
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}

	/**
	 * Sets the selling price of this property.
	 * @param sellPrice the price to set for selling this property
	 */
	public void setPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * Sets the base rent amount for this property.
	 * @param rent the rent value to assign
	 */
	public void setRent(int rent) {
		this.rent = rent;
	}
}
