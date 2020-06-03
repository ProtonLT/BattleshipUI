package battleship;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
public class BattleshipFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	private Driver driver;
	private JTextArea textArea;
	private JTextArea playerMessage;
	private int counter;
	private int player = 1;
	private String[] shipNames = {"destroyer", "submarine", "cruiser", "battleship", "carrier"};
	public BattleshipFrame(String title, Driver driver)
	{
		//initialize and construct
		super(title);
		this.driver = driver;
		textArea = new JTextArea();
		playerMessage = new JTextArea();
		
		//create player input panel
		JPanel panel = new JPanel();
		JButton testButton = new JButton("Save");
		JLabel xLabel = new JLabel("X:");
		JLabel yLabel = new JLabel("Y:");
		JLabel orientationLabel = new JLabel("Orientation (V or H):");
		JTextField xField = new JTextField(10);
		JTextField yField = new JTextField(10);
		JTextField orientationField = new JTextField(10);
		Dimension size = panel.getPreferredSize();
		size.width = 300;
		panel.setPreferredSize(size);
		panel.setBorder(BorderFactory.createTitledBorder("Boards"));
		panel.add(playerMessage);
		panel.add(xLabel);
		panel.add(xField);
		panel.add(yLabel);
		panel.add(yField);
		panel.add(orientationLabel);
		panel.add(orientationField);
		panel.add(testButton);
		playerMessage.append("Player " + player + ", place your " + shipNames[counter]);
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
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
				if(counter > 4)
				{
					counter = 0;
					player = 2;
				}
				playerMessage.setText(message);
				playerMessage.append(" Player " + player + ", place your " + shipNames[counter]);
				textArea.setText("");
				if(player == 1)
					printBoard(driver.defend1);
				else
					printBoard(driver.defend2);
			}
		});
		
		//add components to frame container
		Container c = getContentPane();
		
		c.add(panel, BorderLayout.EAST);
		c.add(textArea, BorderLayout.WEST);
		//c.add(attackLabel, BorderLayout.WEST);
		//c.add(defendLabel, otherField);
	}
	
	/**public void initGame()
	{
		do
		{
			
		}
		while();
	}*/
	
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
