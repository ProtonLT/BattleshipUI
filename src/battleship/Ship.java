package battleship;

public class Ship{

	private String name;
	private int health;
	private Space[] size;
	private int[] location = new int[2];
	private String orientation = "";
	
	public static void main(String[] args)
	{
		Ship a = new Ship("Alph", 5);
		a.printSize();
		a.test();
	}
	
	public Ship(String name, int length)
	{
		this.name = name;
		this.health = length;
		size = new Space[length];
		for(int i = 0; i < size.length; i++)
		{
			Space a = new Space(true, this);
			//System.out.println("A: " + a.getIsShip());
			size[i] = a;
			//System.out.println("Ship: " + size[i].getIsShip());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) { 
		this.name = name;
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public void setLocation(int[] location)
	{
		this.location = location;
	}
	
	public int[] getLocation()
	{
		return location;
	}
	
	public String getOrientation()
	{
		return orientation;
	}
	
	public void setOrientation(String orientation)
	{
		this.orientation = orientation;
	}
	
	public Space[] getSize()
	{
		return size;
	}
	
	public void printSize()
	{
		for(int i = 0; i < size.length; i++)
		{
			System.out.println(size[i].getIsShip());
		}
	}
	
	public void test()
	{
		for(int i = 0; i < size.length; i++)
		{
			System.out.println(size[i].getParent());
		}
	}
}
