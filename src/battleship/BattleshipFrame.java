package battleship;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
public class BattleshipFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	private Driver driver;
	private JTextArea textArea;
	private JTextArea playerMessage;
	private JPanel panel;
	private int counter;
	private int player = 1;
	private String[] shipNames = {"destroyer", "submarine", "cruiser", "battleship", "carrier"};
	public BattleshipFrame(String title, Driver driver)
	{
		//initialize and construct
		super(title);
		this.driver = driver;
		textArea = new JTextArea();
		//Font original = ;
		//Font bigger = original.deriveFont(newSize);
		//textArea.setFont(new Font("Calibri", Font.PLAIN, 16));
		//textArea.setFont(bigger);
		playerMessage = new JTextArea(50, 55);
		playerMessage.setFont(new Font("Serif", Font.BOLD, 14));

		//create player input panel
		panel = new JPanel();
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				initGame();
			}
		});

		Container c = getContentPane();
		c.add(startButton, BorderLayout.CENTER);
		//add components to frame container

		//c.add(attackLabel, BorderLayout.WEST);
		//c.add(defendLabel, otherField);
	}

	public void initGame()
	{
		Container c = getContentPane();
		c.removeAll();
		c.repaint();
		panel.removeAll();
		c.add(panel, BorderLayout.WEST);
		c.add(textArea, BorderLayout.EAST);
		panel.repaint();
		panel.setLayout(new GridLayout(0, 1));
		JButton testButton = new JButton("Save");
		JLabel xLabel = new JLabel("X:");
		JLabel yLabel = new JLabel("Y:");
		JLabel orientationLabel = new JLabel("Orientation (V or H):");
		JTextField xField = new JTextField(2);
		JTextField yField = new JTextField(2);
		JTextField orientationField = new JTextField(10);
		Dimension size = panel.getPreferredSize();
		size.width = 300;
		panel.setPreferredSize(size);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.add(playerMessage, BorderLayout.PAGE_START);
		panel.add(xLabel);
		panel.add(xField);
		panel.add(yLabel);
		panel.add(yField);
		panel.add(orientationLabel, BorderLayout.WEST);
		panel.add(orientationField, BorderLayout.LINE_END);
		panel.add(testButton, BorderLayout.CENTER);
		playerMessage.append("Player " + player + ", place your " + shipNames[counter]);
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(counter + shipNames[counter]);
				String xCoordStr = xField.getText();
				String yCoordStr = yField.getText();
				String orientation = orientationField.getText();
				//System.out.println("x: " + xCoordStr + " y: " + yCoordStr + " x: " + xCoord + " y: " + yCoord);
				String message = driver.checkValidPlace(yCoordStr, xCoordStr, counter, player, orientation);
				if(message.equals("Ship placed."))
				{
					driver.placeShip(yCoordStr, xCoordStr, counter, player, orientation);
					counter++;
				}
				//System.out.println(counter);
				if(counter > 4)
				{
					if(player == 1)
					{
						counter= 0;
						player = 2;
					}
					else
					{
						//System.out.println("Here");
						run();
						return;
					}
				}
				//System.out.println("There");
				playerMessage.setText("Player " + player + ", place your " + shipNames[counter] + ".\n");
				playerMessage.append(message + "\n");
				textArea.setText("");
				if(player == 1)
					printBoard(driver.defend1);
				else
					printBoard(driver.defend2);
			}
		});
	}

	public void run()
	{
		textArea.setText("");
		System.out.println("running");
		//remove previous setup buttons
		panel.removeAll();
		panel.repaint();
		//create new targeting buttons
		counter = 0;
		player = 1;
		JLabel xLabel = new JLabel("X:");
		JLabel yLabel = new JLabel("Y:");
		JTextField xField = new JTextField(10);
		JTextField yField = new JTextField(10);
		panel.add(playerMessage, BorderLayout.PAGE_START);
		panel.add(xLabel, BorderLayout.WEST);
		panel.add(xField, BorderLayout.LINE_END);
		panel.add(yLabel, BorderLayout.WEST);
		panel.add(yField, BorderLayout.LINE_END);
		playerMessage.setText("Player 1 choose your target.");
		textArea.append("Attack:\n");
		printBoard(driver.attack1);
		textArea.append("Defend:\n");
		printBoard(driver.defend1);
		//set up fire! button
		JButton fireButton = new JButton("Fire!");
		panel.add(fireButton, BorderLayout.CENTER);
		fireButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String xCoordStr = xField.getText();
				String yCoordStr = yField.getText();
				String message = driver.checkValidTarget(yCoordStr, xCoordStr, player);
				//System.out.println(message + " " + message.substring(0, 4));
				if(message.substring(0, 4).equals("Hit!") || message.substring(0, 5).equals("Miss!"))
				{
					System.out.println(message);
					if(player == 1)
						player = 2;
					else
						player = 1;
				}
				playerMessage.setText(message + "\n");
				playerMessage.append("Player " + player + " choose your target.");
				textArea.setText("");
				if(player == 1)
				{
					textArea.append("Attack:\n");
					printBoard(driver.attack1);
					textArea.append("Defend:\n");
					printBoard(driver.defend1);
				}
				else
				{
					textArea.append("Attack:\n");
					printBoard(driver.attack2);
					textArea.append("Defend:\n");
					printBoard(driver.defend2);
				}
				int winner = driver.checkWon();
				if(winner != -1)
				{
					player = winner;
					gameEnd();
				}
			}
		});
	}

	public void gameEnd()
	{
		Container c = getContentPane();
		c.removeAll();
		JLabel winnerLabel = new JLabel("Player " + player + " wins!");
		c.add(winnerLabel, BorderLayout.CENTER);
	}
	
	public void printBoard(Space[][] board)
	{
		String[] letters = {"     ", "A  ", "B  ", "C  ", "D  ", "E  ", "F  ", "G  ", "H  ", "I  ", "J"};
		for(int i = 0; i < board.length + 1; i++)
		{
			textArea.append(letters[i]);
		}
		textArea.append("\n");
		for(int i = 0; i < board.length; i++)
		{
			textArea.append(" " + i + "  ");
			for(int j = 0; j < board[i].length; j++)
			{
				//System.out.println(board[i][j]);
				if(board[i][j].getIsShip())
				{
					if(board[i][j].getIsHit())
						textArea.append("0 ");
					else
						textArea.append("O ");
				}
				else
				{
					if(board[i][j].getIsHit())
						textArea.append("/  ");
					else
						textArea.append("~  ");
				}
			}
			textArea.append("\n");
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
