package battleship;
import java.util.Scanner;
import javax.swing.JFrame;

public class Driver extends JFrame{

	Space[][] attack1 = new Space[10][10];
	Space[][] defend1 = new Space[10][10];
	Space[][] attack2 = new Space[10][10];
	Space[][] defend2 = new Space[10][10];
	Ship[] ships1 = new Ship[5];
	Ship[] ships2 = new Ship[5];

	public static void main(String[] args)
	{
		System.out.println("BATTLESHIP");
		Scanner input = new Scanner(System.in);
		Driver a = new Driver();
		a.initBoards();
		//a.test();
		a.initGame(input);
		a.run(input);
		//a.printBoard(a.attack1);
		//a.printBoard(a.attack2);
		input.close();
	}
	
	public void test()
	{
		for(int i = 0; i < 5; i++)
		{
			System.out.println("Ships1 " + ships1[i].getName() + ": " + ships1[i]);
			System.out.println("Ships2 " + ships2[i].getName() + ": " + ships2[i]);
		}
		defend2[0][0] = ships1[0].getSize()[0];
		System.out.println(defend2[0][0].getParent());
		System.out.println(defend2[0][0].getParent().getName());
	}

	public void initBoards()
	{
		for(int i = 0; i < attack1.length; i++)
		{
			for(int j = 0; j < attack1[i].length; j++)
			{
				attack1[i][j] = new Space(false, null);
			}
		}
		for(int i = 0; i < defend1.length; i++)
		{
			for(int j = 0; j < defend1[i].length; j++)
			{
				defend1[i][j] = new Space(false, null);
			}
		}
		for(int i = 0; i < attack2.length; i++)
		{
			for(int j = 0; j < attack2[i].length; j++)
			{
				attack2[i][j] = new Space(false, null);
			}
		}
		for(int i = 0; i < defend2.length; i++)
		{
			for(int j = 0; j < defend2[i].length; j++)
			{
				defend2[i][j] = new Space(false, null);
			}
		}
		String[] names = {"Destroyer", "Submarine", "Cruiser", "Battleship", "Carrier"};
		int[] sizes = {2, 3, 3, 4, 5};
		for(int i = 0; i < 5; i++)
		{
			ships1[i] = new Ship(names[i], sizes[i]);
			ships2[i] = new Ship(names[i], sizes[i]);
		}
	}

	public void initGame(Scanner input)
	{
		//PLAYER 1 SETUP
		printBoard(defend1);
		for(int i = 0; i < 5; i++) // loops through each ship
		{
			boolean check1 = true;
			while(check1) // loops until placement of ship contains no errors
			{
				check1 = false;
				Ship ship = ships1[i];
				int ycoord = 0;
				int xcoord = 0;
				System.out.println("Player 1, place your ships.");
				boolean check = true;
				while(check)  //while loop to check that coordinates inputed are viable
				{
					check = false;
					//ship.printSize();
					System.out.println(ships1[i].getName() + " x: ");
					String xStr = input.nextLine();
					System.out.println("y: ");	
					String yStr = input.nextLine();
					boolean isDigit = false;
					if(yStr.length() == 1)
						if(Character.isDigit(yStr.charAt(0)))
							isDigit = true;
					if(letToNum(xStr) == -1 || !isDigit)
					{
						System.out.println("X coordinate must be A - J, Y coordinate must be 0 - 9.");
						check = true;
					}
					else
					{
						xcoord = Integer.parseInt(yStr); //I accidentally put x and y backwards so
						ycoord = letToNum(xStr); //I switched x and y coords here
					}
				}

				check = true;
				String alignment = "";
				while(check) // check to make sure player inputs h or v
				{
					check = false;
					System.out.println("Vertical (V) or Horizontal (H)");
					alignment = input.nextLine();
					if(!alignment.toUpperCase().equals("H") && !alignment.toUpperCase().equals("V"))
					{
						System.out.println("Must be 'V' or 'H'.");
						check = true;
					}
				}

				boolean isViable = true;
				if(alignment.toUpperCase().equals("H")) //HORIZONTAL
				{
					if(xcoord > 9 || ycoord + ship.getSize().length > 9)// check if coordinates are out of bounds
					{
						System.out.println("Coordinate out of bounds.");
						check1 = true;
					}
					else
					{
						for(int j = 0; j < ship.getSize().length; j++)
						{
							if(defend1[xcoord][ycoord + j].getIsShip())		
							{	
								System.out.println("Space is already taken.");
								check1 = true;
								isViable = false;
								break;
							}
						}
						if(isViable)
						{
							check1 = false;
							for(int j = 0; j < ship.getSize().length; j++)
							{
								defend1[xcoord][ycoord + j] = ship.getSize()[j];
							}
						}
					}
				}
				else if(alignment.toUpperCase().equals("V")) //VERTICAL
				{
					if(xcoord + ship.getSize().length - 1 > 9 || ycoord > 9)
					{
						System.out.println("Coordinate out of bounds.");
						check1 = true;
					}
					else
					{
						for(int j = 0; j < ship.getSize().length; j++)
						{
							//System.out.println("x: " + (xcoord + j) + " y: " + ycoord + " stuff: " + defend1[xcoord + j][ycoord].getIsShip());
							if(defend1[xcoord + j][ycoord].getIsShip())		
							{	
								System.out.println("Space is already taken.");
								isViable = false;
								check1 = true;
								break;
							}
						}
						if(isViable)
						{
							check1 = false;
							for(int j = 0; j < ship.getSize().length; j++)
							{
								defend1[xcoord + j][ycoord] = ship.getSize()[j];
								//System.out.println("End: " + " x: " + (xcoord + j) + " y: " + ycoord + defend1[xcoord + j][ycoord].getIsShip());
							}
						}
					}
				}
				printBoard(defend1);
			}
		}
		
		//PLAYER 2 SETUP
		printBoard(defend2);
		for(int i = 0; i < 5; i++) // loops through each ship
		{
			boolean check1 = true;
			while(check1) // loops until placement of ship contains no errors
			{
				check1 = false;
				Ship ship = ships2[i];
				int ycoord = 0;
				int xcoord = 0;
				System.out.println("Player 2, place your ships.");
				boolean check = true;
				while(check)  //while loop to check that coordinates inputed are viable
				{
					check = false;
					//ship.printSize();
					System.out.println(ships2[i].getName() + " x: ");
					String xStr = input.nextLine();
					System.out.println("y: ");	
					String yStr = input.nextLine();
					boolean isDigit = false;
					if(yStr.length() == 1)
						if(Character.isDigit(yStr.charAt(0)))
							isDigit = true;
					if(letToNum(xStr) == -1 || !isDigit)
					{
						System.out.println("X coordinate must be A - J, Y coordinate must be 0 - 9.");
						check = true;
					}
					else
					{
						xcoord = Integer.parseInt(yStr); //I accidentally put x and y backwards so
						ycoord = letToNum(xStr); //I switched x and y coords here
					}
				}

				check = true;
				String alignment = "";
				while(check) // check to make sure player inputs h or v
				{
					check = false;
					System.out.println("Vertical (V) or Horizontal (H)");
					alignment = input.nextLine();
					if(!alignment.toUpperCase().equals("H") && !alignment.toUpperCase().equals("V"))
					{
						System.out.println("Must be 'V' or 'H'.");
						check = true;
					}
				}

				boolean isViable = true;
				if(alignment.toUpperCase().equals("H")) //HORIZONTAL
				{
					if(xcoord > 9 || ycoord + ship.getSize().length > 9)// check if coordinates are out of bounds
					{
						System.out.println("Coordinate out of bounds.");
						check1 = true;
					}
					else
					{
						for(int j = 0; j < ship.getSize().length; j++)
						{
							if(defend2[xcoord][ycoord + j].getIsShip())		
							{	
								System.out.println("Space is already taken.");
								check1 = true;
								isViable = false;
								break;
							}
						}
						if(isViable)
						{
							check1 = false;
							for(int j = 0; j < ship.getSize().length; j++)
							{
								defend2[xcoord][ycoord + j] = ship.getSize()[j];
							}
						}
					}
				}
				else if(alignment.toUpperCase().equals("V")) //VERTICAL
				{
					if(xcoord + ship.getSize().length - 1 > 9 || ycoord > 9)
					{
						System.out.println("Coordinate out of bounds.");
						check1 = true;
					}
					else
					{
						for(int j = 0; j < ship.getSize().length; j++)
						{
							//System.out.println("x: " + (xcoord + j) + " y: " + ycoord + " stuff: " + defend1[xcoord + j][ycoord].getIsShip());
							if(defend2[xcoord + j][ycoord].getIsShip())		
							{	
								System.out.println("Space is already taken.");
								isViable = false;
								check1 = true;
								break;
							}
						}
						if(isViable)
						{
							check1 = false;
							for(int j = 0; j < ship.getSize().length; j++)
							{
								defend2[xcoord + j][ycoord] = ship.getSize()[j];
								//System.out.println("End: " + " x: " + (xcoord + j) + " y: " + ycoord + defend1[xcoord + j][ycoord].getIsShip());
							}
						}
					}
				}
				printBoard(defend2);
			}
		}
		
	}

	public void run(Scanner input)
	{
		int player1Health = 5;
		int player2Health = 5;
		while(player1Health != 0 && player2Health != 0)
		{
			printBoard(attack1);
			int xcoord = 0;
			int ycoord = 0;
			System.out.println("Player 1, choose target.");
			boolean check = true;
			while(check)  //while loop to check that coordinates inputed are viable
			{
				check = false;
				//ship.printSize();
				System.out.println("x: ");
				String xStr = input.nextLine();
				System.out.println("y: ");	
				String yStr = input.nextLine();
				boolean isDigit = false;
				if(yStr.length() == 1)
					if(Character.isDigit(yStr.charAt(0)))
						isDigit = true;
				if(letToNum(xStr) == -1 || !isDigit)
				{
					System.out.println("X coordinate must be A - J, Y coordinate must be 0 - 9.");
					check = true;
				}
				else
				{
					xcoord = Integer.parseInt(yStr); //I accidentally put x and y backwards so
					ycoord = letToNum(xStr); //I switched x and y coords here
				}
				if(attack1[xcoord][ycoord].getIsHit())
				{
					System.out.println("Cannot fire on space already targeted.");
					check = true;
					continue;
				}
			}
			defend2[xcoord][ycoord].setIsHit(true);
			if(defend2[xcoord][ycoord].getIsShip())
			{
				attack1[xcoord][ycoord].setIsHit(true);
				attack1[xcoord][ycoord].setIsShip(true);
				defend2[xcoord][ycoord].takeDamage();
				//System.out.println(defend2[xcoord][ycoord].getParent().getHealth());
				System.out.println("Hit!");
				if(defend2[xcoord][ycoord].getParent().getHealth() == 0) //error line
				{
					System.out.println(defend2[xcoord][ycoord].getParent().getName() + " sunk!");
					player2Health--;
					if(player2Health == 0)
						continue;
				}
			}
			else
			{
				attack1[xcoord][ycoord].setIsHit(true);
				System.out.println("Miss!");
			}
			//printBoard(defend2);
			
			//PLAYER 2 ATTACK
			printBoard(attack2);
			xcoord = 0;
			ycoord = 0;
			System.out.println("Player 2, choose target.");
			check = true;
			while(check)  //while loop to check that coordinates inputed are viable
			{
				check = false;
				//ship.printSize();
				System.out.println("x: ");
				String xStr = input.nextLine();
				System.out.println("y: ");	
				String yStr = input.nextLine();
				boolean isDigit = false;
				if(yStr.length() == 1)
					if(Character.isDigit(yStr.charAt(0)))
						isDigit = true;
				if(letToNum(xStr) == -1 || !isDigit)
				{
					System.out.println("X coordinate must be A - J, Y coordinate must be 0 - 9.");
					check = true;
				}
				else
				{
					xcoord = Integer.parseInt(yStr); //I accidentally put x and y backwards so
					ycoord = letToNum(xStr); //I switched x and y coords here
				}
				if(attack2[xcoord][ycoord].getIsHit())
				{
					System.out.println("Cannot fire on space already targeted.");
					check = true;
					continue;
				}
			}
			defend1[xcoord][ycoord].setIsHit(true);
			if(defend1[xcoord][ycoord].getIsShip())
			{
				attack2[xcoord][ycoord].setIsHit(true);
				attack2[xcoord][ycoord].setIsShip(true);
				defend1[xcoord][ycoord].takeDamage();
				//System.out.println(defend1[xcoord][ycoord].getParent().getHealth());
				System.out.println("Hit!");
				if(defend1[xcoord][ycoord].getParent().getHealth() == 0) //error line
				{
					System.out.println(defend1[xcoord][ycoord].getParent().getName() + " sunk!");
					player1Health--;
				}
			}
			else
			{
				attack2[xcoord][ycoord].setIsHit(true);
				System.out.println("Miss!");
			}
			//printBoard(defend2);
		}
		if(player1Health == 0)
			System.out.println("Player 2 Wins!!");
		else
			System.out.println("Player 1 Wins!!");
		System.out.println("Player 1's Board: ");
		printBoard(defend1);
		System.out.println("Player 2's Board");
		printBoard(defend2);
	}

	public void printBoard(Space[][] board)
	{
		String[] letters = {"  ", "A ", "B ", "C ", "D ", "E ", "F ", "G ", "H ", "I ", "J"};
		for(int i = 0; i < board.length + 1; i++)
		{
			System.out.print(letters[i]);
		}
		System.out.println();
		for(int i = 0; i < board.length; i++)
		{
			System.out.print(i + " ");
			for(int j = 0; j < board[i].length; j++)
			{
				if(board[i][j].getIsShip())
				{
					if(board[i][j].getIsHit())
						System.out.print("0 ");
					else
						System.out.print("O ");
				}
				else
				{
					if(board[i][j].getIsHit())
						System.out.print("/ ");
					else
						System.out.print("~ ");
				}
			}
			System.out.println();
		}
	}

	public int letToNum(String letter)
	{
		switch(letter.toUpperCase())
		{
		case "A":
			return 0;
		case "B":
			return 1;
		case "C":
			return 2;
		case "D":
			return 3;
		case "E":
			return 4;
		case "F":
			return 5;
		case "G":
			return 6;
		case "H":
			return 7;
		case "I":
			return 8;
		case "J":
			return 9;
		default:
			return -1;
		}

	}
}
