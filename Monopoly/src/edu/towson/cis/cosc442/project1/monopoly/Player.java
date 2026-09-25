package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Player {
	//the key of colorGroups is the name of the color group.
	private Hashtable<String, Integer> colorGroups = new Hashtable<String, Integer>();
	private boolean inJail;
	private int money;
	private String name;

	private Cell position;
	private ArrayList<PropertyCell> properties = new ArrayList<PropertyCell>();
	private ArrayList<Cell> railroads = new ArrayList<Cell>();
	private ArrayList<Cell> utilities = new ArrayList<Cell>();
	
	/**
	 * Initializes a new Player positioned at 'Go' with no jail status.
	 */
	public Player() {
		GameBoard gb = GameMaster.instance().getGameBoard();
		inJail = false;
		if(gb != null) {
			position = gb.queryCell("Go");
		}
	}

    /**
     * Assigns ownership of a property to this player and deducts the purchase amount from their money.
     * @param property The Cell representing the property to buy.
     * @param amount The amount of money to pay for the property.
     */
    public void buyProperty(Cell property, int amount) {
        property.setTheOwner(this);
        if(property instanceof PropertyCell) {
            PropertyCell cell = (PropertyCell)property;
            properties.add(cell);
            colorGroups.put(
                    cell.getColorGroup(), 
                    getPropertyNumberForColor(cell.getColorGroup())+1);
        }
        if(property instanceof RailRoadCell) {
            railroads.add(property);
            colorGroups.put(
                    RailRoadCell.COLOR_GROUP, 
                    getPropertyNumberForColor(RailRoadCell.COLOR_GROUP)+1);
        }
        if(property instanceof UtilityCell) {
            utilities.add(property);
            colorGroups.put(
                    UtilityCell.COLOR_GROUP, 
                    getPropertyNumberForColor(UtilityCell.COLOR_GROUP)+1);
        }
        setMoney(getMoney() - amount);
    }
	
	/**
	 * Determines if the player is eligible to purchase houses by checking for ownership of any monopolies.
	 * @return True if the player owns at least one monopoly; otherwise false.
	 */
	public boolean canBuyHouse() {
		return (getMonopolies().length != 0);
	}

	/**
	 * Checks whether the player owns a property with the specified name.
	 * @param property The name of the property to check for ownership.
	 * @return True if the player owns the property; otherwise false.
	 */
	public boolean checkProperty(String property) {
		for(int i=0;i<properties.size();i++) {
			Cell cell = properties.get(i);
			if(cell.getName().equals(property)) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Transfers all properties owned by this player to another player or frees them if null.
	 * @param player The player to receive the properties or null to free them.
	 */
	public void exchangeProperty(Player player) {
		for(int i = 0; i < getPropertyNumber(); i++ ) {
			PropertyCell cell = getProperty(i);
			cell.setTheOwner(player);
			if(player == null) {
				cell.setAvailable(true);
				cell.setNumHouses(0);
			}
			else {
				player.properties.add(cell);
				colorGroups.put(
						cell.getColorGroup(), 
						getPropertyNumberForColor(cell.getColorGroup())+1);
			}
		}
		properties.clear();
	}
    
    /**
     * Retrieves all properties, railroads, and utilities owned by the player as an array.
     * @return An array of all Cell objects owned by the player.
     */
    public Cell[] getAllProperties() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        list.addAll(properties);
        list.addAll(utilities);
        list.addAll(railroads);
        return (Cell[])list.toArray(new Cell[list.size()]);
    }

	/**
	 * Returns the current amount of money the player has.
	 * @return The player's current money balance.
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Returns an array of color groups for which the player owns all properties, indicating monopolies.
	 * @return An array of monopoly color group names owned by the player.
	 */
	public String[] getMonopolies() {
		ArrayList<String> monopolies = new ArrayList<String>();
		Enumeration<String> colors = colorGroups.keys();
		while(colors.hasMoreElements()) {
			String color = colors.nextElement();
            if(!(color.equals(RailRoadCell.COLOR_GROUP)) && !(color.equals(UtilityCell.COLOR_GROUP))) {
    			Integer num = colorGroups.get(color);
    			GameBoard gameBoard = GameMaster.instance().getGameBoard();
    			if(num.intValue() == gameBoard.getPropertyNumberForColor(color)) {
    				monopolies.add(color);
    			}
            }
		}
		return (String[])monopolies.toArray(new String[monopolies.size()]);
	}

	/**
	 * Returns the name of the player.
	 * @return The player's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pays the jail bail and releases the player from jail, resetting properties if bankrupt.
	 */
	public void getOutOfJail() {
		money -= JailCell.BAIL;
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(null);
		}
		inJail = false;
		GameMaster.instance().updateGUI();
	}

	/**
	 * Returns the Cell representing the player's current position on the board.
	 * @return The current position Cell of the player.
	 */
	public Cell getPosition() {
		return this.position;
	}
	
	/**
	 * Retrieves the property at the specified index from the player's list of properties.
	 * @param index The index of the property to retrieve.
	 * @return The PropertyCell at the specified index.
	 */
	public PropertyCell getProperty(int index) {
		return properties.get(index);
	}
	
	/**
	 * Returns the total number of properties owned by the player.
	 * @return The count of properties owned.
	 */
	public int getPropertyNumber() {
		return properties.size();
	}

	/**
	 * Returns the number of properties owned by the player for a specified color group.
	 * @param name The color group name.
	 * @return The number of properties owned in the given color group.
	 */
	private int getPropertyNumberForColor(String name) {
		Integer number = colorGroups.get(name);
		if(number != null) {
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Determines if the player is bankrupt based on their money being zero or less.
	 * @return True if the player has no money or less; otherwise false.
	 */
	public boolean isBankrupt() {
		return money <= 0;
	}

	/**
	 * Indicates whether the player is currently in jail.
	 * @return True if the player is in jail; otherwise false.
	 */
	public boolean isInJail() {
		return inJail;
	}

	/**
	 * Returns the number of railroad properties owned by the player.
	 * @return The count of railroads owned.
	 */
	public int numberOfRR() {
		return getPropertyNumberForColor(RailRoadCell.COLOR_GROUP);
	}

	/**
	 * Returns the number of utility properties owned by the player.
	 * @return The count of utilities owned.
	 */
	public int numberOfUtil() {
		return getPropertyNumberForColor(UtilityCell.COLOR_GROUP);
	}
	
	/**
	 * Pays rent to another player, transferring the rent amount or all remaining money if insufficient, and updates ownership if bankrupt.
	 * @param owner The player to pay rent to.
	 * @param rentValue The rent amount to be paid.
	 */
	public void payRentTo(Player owner, int rentValue) {
		if(money < rentValue) {
			owner.money += money;
			money -= rentValue;
		}
		else {
			money -= rentValue;
			owner.money +=rentValue;
		}
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(owner);
		}
	}
	
	/**
	 * Attempts to purchase the current position property if available, applying the appropriate purchase method based on property type.
	 */
	public void purchase() {
		if(getPosition().isAvailable()) {
			Cell c = getPosition();
			c.setAvailable(false);
			if(c instanceof PropertyCell) {
				PropertyCell cell = (PropertyCell)c;
				purchaseProperty(cell);
			}
			if(c instanceof RailRoadCell) {
				RailRoadCell cell = (RailRoadCell)c;
				purchaseRailRoad(cell);
			}
			if(c instanceof UtilityCell) {
				UtilityCell cell = (UtilityCell)c;
				purchaseUtility(cell);
			}
		}
	}
	
	/**
	 * Purchases a specified number of houses for all properties in a selected monopoly if funds are sufficient and house limits are not exceeded.
	 * @param selectedMonopoly The monopoly color group to buy houses for.
	 * @param houses The number of houses to purchase per property.
	 */
	public void purchaseHouse(String selectedMonopoly, int houses) {
		GameBoard gb = GameMaster.instance().getGameBoard();
		PropertyCell[] cells = gb.getPropertiesInMonopoly(selectedMonopoly);
		if((money >= (cells.length * (cells[0].getHousePrice() * houses)))) {
			for(int i = 0; i < cells.length; i++) {
				int newNumber = cells[i].getNumHouses() + houses;
				if (newNumber <= 5) {
					cells[i].setNumHouses(newNumber);
					this.setMoney(money - (cells[i].getHousePrice() * houses));
					GameMaster.instance().updateGUI();
				}
			}
		}
	}
	
	/**
	 * Purchases a given PropertyCell by assigning ownership and deducting the price from the player's money.
	 * @param cell The PropertyCell to purchase.
	 */
	private void purchaseProperty(PropertyCell cell) {
        buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases a given RailRoadCell by assigning ownership and deducting the price from the player's money.
	 * @param cell The RailRoadCell to purchase.
	 */
	private void purchaseRailRoad(RailRoadCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases a given UtilityCell by assigning ownership and deducting the price from the player's money.
	 * @param cell The UtilityCell to purchase.
	 */
	private void purchaseUtility(UtilityCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

    /**
     * Sells a property owned by the player, removing ownership and adding the sale amount to the player's money.
     * @param property The Cell property to sell.
     * @param amount The amount received from selling the property.
     */
    public void sellProperty(Cell property, int amount) {
        property.setTheOwner(null);
        if(property instanceof PropertyCell) {
            properties.remove(property);
        }
        if(property instanceof RailRoadCell) {
            railroads.remove(property);
        }
        if(property instanceof UtilityCell) {
            utilities.remove(property);
        }
        setMoney(getMoney() + amount);
    }

	/**
	 * Sets the player's jail status to indicate if they are in jail or not.
	 * @param inJail True if the player should be marked as in jail; false otherwise.
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * Sets the player's money to a specified amount.
	 * @param money The new amount of money for the player.
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Sets the player's name.
	 * @param name The new name of the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Updates the player's current position on the board.
	 * @param newPosition The Cell representing the new position.
	 */
	public void setPosition(Cell newPosition) {
		this.position = newPosition;
	}

    /**
     * Returns the string representation of the player, which is their name.
     * @return The player's name as a string.
     */
    public String toString() {
        return name;
    }
    
    /**
     * Clears all properties, railroads, and utilities owned by the player, resetting their ownership lists.
     */
    public void resetProperty() {
    	properties = new ArrayList<PropertyCell>();
    	railroads = new ArrayList<Cell>();
    	utilities = new ArrayList<Cell>();
	}
}
