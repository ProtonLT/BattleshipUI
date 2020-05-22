package battleship;
public class Space {
	
	public boolean isHit;
	public boolean isShip;
	private Ship parent;
	
	public Space(boolean isShip, Ship parent)
	{
		this.isShip = isShip;
		this.parent = parent;
		isHit = false;
	}
	
	public void takeDamage()
	{
		parent.setHealth(parent.getHealth() - 1);
	}

	public boolean getIsHit() {
		return isHit;
	}

	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
	}
	
	public boolean getIsShip() {
		return isShip;
	}

	public void setIsShip(boolean isShip) {
		this.isShip = isShip;
	}
	
	public Ship getParent()
	{
		return parent;
	}
}
