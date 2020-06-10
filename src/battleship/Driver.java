package battleship;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.*;

public class Driver{

	Space[][] attack1 = new Space[10][10];
	Space[][] defend1 = new Space[10][10];
	Space[][] attack2 = new Space[10][10];
	Space[][] defend2 = new Space[10][10];
	Ship[] ships1 = new Ship[5];
	Ship[] ships2 = new Ship[5];
	private BattleshipFrame frame = new BattleshipFrame("Battleship", this);

	public Driver()
	{
		//JFrame frame = new TestFrame("Battleship", this);
		frame = new BattleshipFrame("Battleship", this);
		//create swing components
		//JPanel panel = new JPanel();
		//JButton start = new JButton("Start");
		//start.addActionListener(this);
		//JLabel startLabel = new JLabel("Start");
		//panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		//panel.setLayout(new GridLayout(0, 1));
		//panel.add(start);
		//panel.add(startLabel);
		//add swing components
		//frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setTitle("Battleship");
		frame.setPreferredSize(new Dimension(650, 530));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{

		Scanner input = new Scanner(System.in);
		Driver a = new Driver();
		a.initBoards();
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

	public void placeShip(String ycoordStr, String xcoordStr, int counter, int player, String orientation)
	{
		if(!checkValidPlace(ycoordStr, xcoordStr, counter, player, orientation).equals("Ship placed."))
		{
			//System.out.println("Here");
			return;
		}
		int xcoord = letToNum(xcoordStr);
		int ycoord = Integer.parseInt(ycoordStr);
		if(orientation.toUpperCase().equals("H"))
		{
			if(player == 1)
			{
				//System.out.println("another testing");
				int[] location = {ycoord, xcoord};
				ships1[counter].setLocation(location);
				ships1[counter].setOrientation("H");
				for(int j = 0; j < ships1[counter].getSize().length; j++)
				{

					defend1[ycoord][xcoord + j] = ships1[counter].getSize()[j];
				}
				//printBoard(defend1);
			}
			if(player == 2)
			{
				//System.out.println("umm testing");
				int[] location = {ycoord, xcoord};
				ships2[counter].setLocation(location);
				ships2[counter].setOrientation("H");
				for(int j = 0; j < ships2[counter].getSize().length; j++)
				{
					defend2[ycoord][xcoord + j] = ships2[counter].getSize()[j];
				}
				//printBoard(defend2);
			}
		}
		else if(orientation.toUpperCase().equals("V"))
		{
			if(player == 1)
			{
				int[] location = {ycoord, xcoord};
				ships1[counter].setLocation(location);
				ships1[counter].setOrientation("V");
				for(int j = 0; j < ships1[counter].getSize().length; j++)
				{
					defend1[ycoord + j][xcoord] = ships1[counter].getSize()[j];
				}
				//printBoard(defend1);
			}
			else if(player == 2)
			{
				int[] location = {ycoord, xcoord};
				ships2[counter].setLocation(location);
				ships2[counter].setOrientation("V");
				for(int j = 0; j < ships2[counter].getSize().length; j++)
				{
					defend2[ycoord + j][xcoord] = ships2[counter].getSize()[j];
				}
				//printBoard(defend2);
			}
		}
	}

	public String checkValidPlace(String ycoordStr, String xcoordStr, int counter, int player, String orientation)
	{
		//check that x coord is A -J and y coord is 0 - 9
		boolean isDigit = false;
		if(ycoordStr.length() == 1)
			if(Character.isDigit(ycoordStr.charAt(0)))
				isDigit = true;
		if(letToNum(xcoordStr) == -1 || !isDigit)
		{
			return ("X  must be A - J, Y must be 0 - 9.");
		}

		//check that orientation is v or h
		if(!orientation.toUpperCase().equals("H") && !orientation.toUpperCase().equals("V"))
		{
			return ("Must be 'V' or 'H'.");
		}
		int ycoord = Integer.parseInt(ycoordStr); 
		int xcoord = letToNum(xcoordStr); 
		//check that coordinates are within bounds
		//System.out.println("y: " + (ycoord + ships1[counter].getSize().length) + " x: " + (xcoord + ships1[counter].getSize().length));
		if(orientation.toUpperCase().equals("H"))
		{
			if(ycoord > 9 || xcoord + ships1[counter].getSize().length - 1 > 9)// check if coordinates are out of bounds
			{
				return ("Coordinate out of bounds.");
			}
		}
		else if(orientation.toUpperCase().equals("V"))
		{
			if(ycoord + ships1[counter].getSize().length - 1 > 9|| xcoord > 9)// check if coordinates are out of bounds
			{
				return ("Coordinate out of bounds.");
			}
		}

		//check if space is already taken
		if(orientation.toUpperCase().equals("H"))
		{
			//System.out.println("H");
			if(player == 1)
			{
				for(int j = 0; j < counter; j++)
				{
					if(defend1[ycoord][xcoord + j].getIsShip())		
					{	
						return ("Space is already taken.");
					}
				}
			}
			else if(player == 2)
			{
				for(int j = 0; j < counter; j++)
				{
					if(defend2[ycoord][xcoord + j].getIsShip())		
					{	
						return ("Space is already taken.");
					}
				}
			}
		}
		if(orientation.toUpperCase().equals("V"))
		{
			//System.out.println("V");
			if(player == 1)
			{
				for(int j = 0; j < counter; j++)
				{
					if(defend1[ycoord + j][xcoord].getIsShip())		
					{	
						return ("Space is already taken.");
					}
				}
			}
			else if(player == 2)
			{
				for(int j = 0; j < counter; j++)
				{
					if(defend2[ycoord + j][xcoord].getIsShip())		
					{	
						return ("Space is already taken.");
					}
				}
			}
		}
		return "Ship placed.";
	}

	public String checkValidTarget(String ycoordStr, String xcoordStr, int player)
	{
		int xcoord;
		int ycoord;
		//check that x is a - j, and y 0s 0 - 9
		boolean isDigit = false;
		if(ycoordStr.length() == 1)
			if(Character.isDigit(ycoordStr.charAt(0)))
				isDigit = true;
		if(letToNum(xcoordStr) == -1 || !isDigit)
		{
			return ("X must be A - J, Y must be 0 - 9.");
		}
		else
		{
			ycoord = Integer.parseInt(ycoordStr); 
			xcoord = letToNum(xcoordStr); 
		}
		//check to see if space has already been fired upon
		if(player == 1)
		{
			if(attack1[ycoord][xcoord].getIsHit())
			{
				return ("Cannot fire on space already targeted.");
			}
		}
		else
		{
			if(attack2[ycoord][xcoord].getIsHit())
			{
				return ("Cannot fire on space already targeted.");
			}
		}
		String message = "";
		if(player == 1)
		{
			defend2[ycoord][xcoord].setIsHit(true);
			if(defend2[ycoord][xcoord].getIsShip())
			{
				attack1[ycoord][xcoord].setIsHit(true);
				attack1[ycoord][xcoord].setIsShip(true);
				defend2[ycoord][xcoord].takeDamage();
				//System.out.println(defend2[xcoord][ycoord].getParent().getHealth());
				message += "Hit!  ";
				if(defend2[ycoord][xcoord].getParent().getHealth() == 0) //error line
				{
					message += (" " + defend2[ycoord][xcoord].getParent().getName() + " sunk!");
				}
			}
			else
			{
				attack1[ycoord][xcoord].setIsHit(true);
				message += "Miss! ";
			}
			return message;
		}
		else
		{
			defend1[ycoord][xcoord].setIsHit(true);
			if(defend1[ycoord][xcoord].getIsShip())
			{
				attack2[ycoord][xcoord].setIsHit(true);
				attack2[ycoord][xcoord].setIsShip(true);
				defend1[ycoord][xcoord].takeDamage();
				//System.out.println(defend2[xcoord][ycoord].getParent().getHealth());
				message += "Hit! ";
				if(defend1[ycoord][xcoord].getParent().getHealth() == 0) //error line
				{
					message += (" " + defend1[ycoord][xcoord].getParent().getName() + " sunk!");
				}
			}
			else
			{
				attack2[ycoord][xcoord].setIsHit(true);
				message += "Miss! ";
			}
			return message;
		}
	}

	public int checkWon()
	{
		boolean winnerFound = true;
		//System.out.println("Player 1");
		for(int i = 0; i < ships1.length; i++)
		{
			//System.out.println(ships1[i].getName() + ": " + ships1[i].getHealth());
			if(ships1[i].getHealth() != 0)
			{
				winnerFound = false;
			}
		}
		if(winnerFound)
			return 1;
		winnerFound = true;
		//System.out.println("Player 2");
		for(int i = 0; i < ships2.length; i++)
		{
			//System.out.println(ships2[i].getName() + ": " +ships2[i].getHealth());
			if(ships2[i].getHealth() != 0)
			{
				winnerFound = false;
			}
		}
		if(winnerFound)
			return 2;
		return -1;
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
