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
	 * Initializes a Player object setting the initial position to the 'Go' cell and marking the player as not in jail.
	 */
	public Player() {
		GameBoard gb = GameMaster.instance().getGameBoard();
		inJail = false;
		if(gb != null) {
			position = gb.queryCell("Go");
		}
	}

    /**
     * Assigns ownership of the specified property to the player and deducts the purchase amount from the player's money.
     * @param property The property cell to be bought.
     * @param amount The purchase price to pay for the property.
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
	 * Determines if the player is eligible to buy houses by checking if the player has any monopolies.
	 * @return True if the player can buy houses; false otherwise.
	 */
	public boolean canBuyHouse() {
		return (getMonopolies().length != 0);
	}

	/**
	 * Checks whether the player owns the property with the specified name.
	 * @param property The name of the property to check ownership for.
	 * @return True if the player owns the specified property; false otherwise.
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
	 * Transfers all player-owned properties to another player or resets ownership if the target player is null.
	 * @param player The player to receive the properties, or null to release them.
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
     * Returns an array of all properties, including regular properties, railroads, and utilities, owned by the player.
     * @return An array of all owned property cells.
     */
    public Cell[] getAllProperties() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        list.addAll(properties);
        list.addAll(utilities);
        list.addAll(railroads);
        return (Cell[])list.toArray(new Cell[list.size()]);
    }

	/**
	 * Retrieves the current amount of money the player has.
	 * @return The player's current money balance.
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Returns an array of color group names for which the player owns all properties, indicating monopolies.
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
	 * @return The player's name as a string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Allows the player to pay bail and get out of jail, updating bankruptcy status and the game GUI.
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
	 * Retrieves the player's current position on the game board.
	 * @return The current Cell the player is positioned on.
	 */
	public Cell getPosition() {
		return this.position;
	}
	
	/**
	 * Returns the property at the specified index in the player's list of owned properties.
	 * @param index The index of the property to retrieve.
	 * @return The PropertyCell owned by the player at the given index.
	 */
	public PropertyCell getProperty(int index) {
		return properties.get(index);
	}
	
	/**
	 * Returns the number of property cells owned by the player.
	 * @return The count of owned properties (excluding utilities and railroads).
	 */
	public int getPropertyNumber() {
		return properties.size();
	}

	/**
	 * Returns the number of properties owned by the player for a given color group.
	 * @param name The color group name to count properties for.
	 * @return The count of properties owned in that color group.
	 */
	private int getPropertyNumberForColor(String name) {
		Integer number = colorGroups.get(name);
		if(number != null) {
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Determines if the player is bankrupt based on their money balance.
	 * @return True if the player has zero or negative money; false otherwise.
	 */
	public boolean isBankrupt() {
		return money <= 0;
	}

	/**
	 * Checks if the player is currently in jail.
	 * @return True if the player is in jail; false otherwise.
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
	 * Pays rent to another player, adjusting money balances accordingly and handling bankruptcy if necessary.
	 * @param owner The player receiving the rent payment.
	 * @param rentValue The amount of rent to pay.
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
	 * Purchases the property at the player's current position if it is available.
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
	 * Purchases a specified number of houses in a monopoly if the player has sufficient funds and the maximum house limit is not exceeded.
	 * @param selectedMonopoly The color group name representing the monopoly.
	 * @param houses The number of houses to purchase for each property in the monopoly.
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
	 * Buys the specified property cell for its listed price and assigns it to the player.
	 * @param cell The property cell to purchase.
	 */
	private void purchaseProperty(PropertyCell cell) {
        buyProperty(cell, cell.getPrice());
	}

	/**
	 * Buys the specified railroad cell for its listed price and assigns it to the player.
	 * @param cell The railroad cell to purchase.
	 */
	private void purchaseRailRoad(RailRoadCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

	/**
	 * Buys the specified utility cell for its listed price and assigns it to the player.
	 * @param cell The utility cell to purchase.
	 */
	private void purchaseUtility(UtilityCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

    /**
     * Sells the specified property and increases the player's money by the specified amount.
     * @param property The property cell to sell.
     * @param amount The sale price received for the property.
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
	 * Sets the player's jail status to either in jail or not.
	 * @param inJail True to mark the player as in jail; false otherwise.
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * Sets the player's current amount of money to the specified value.
	 * @param money The new money amount for the player.
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Sets the player's name to the specified string.
	 * @param name The new name for the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Updates the player's current position on the game board.
	 * @param newPosition The new cell representing the player's position.
	 */
	public void setPosition(Cell newPosition) {
		this.position = newPosition;
	}

    /**
     * Returns the player's name as its string representation.
     * @return The player's name string.
     */
    public String toString() {
        return name;
    }
    
    /**
     * Clears all current property ownership data for the player, including properties, railroads, and utilities.
     */
    public void resetProperty() {
    	properties = new ArrayList<PropertyCell>();
    	railroads = new ArrayList<Cell>();
    	utilities = new ArrayList<Cell>();
	}
}
