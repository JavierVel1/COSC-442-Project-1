package edu.towson.cis.cosc442.project1.monopoly;

public abstract class Cell {
	private boolean available = true;
	private String name;
	protected Player theOwner;

	/**
	 * Returns the name of the cell.
	 * @return The name of the cell.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the player who owns the cell.
	 * @return The owner of the cell.
	 */
	public Player getTheOwner() {
		return theOwner;
	}
	
	/**
	 * Returns the price of the cell, defaulting to 0 in this base class.
	 * @return The price of the cell.
	 */
	public int getPrice() {
		return 0;
	}

	/**
	 * Indicates whether the cell is currently available.
	 * @return true if the cell is available; false otherwise.
	 */
	public boolean isAvailable() {
		return available;
	}
	
	/**
	 * Performs the action associated with landing on this cell.
	 */
	public abstract void playAction();

	/**
	 * Sets the availability status of the cell.
	 * @param available The new availability status to set.
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	/**
	 * Sets the name of the cell.
	 * @param name The name to assign to the cell.
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Assigns the specified player as the owner of the cell.
	 * @param owner The player to set as the owner.
	 */
	public void setTheOwner(Player owner) {
		this.theOwner = owner;
	}
    
    /**
     * Returns a string representation of the cell, which is its name.
     * @return The name of the cell as a string.
     */
    public String toString() {
        return name;
    }
}
