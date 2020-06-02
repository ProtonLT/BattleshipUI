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
	private int counter;
	private int player = 1;
	public BattleshipFrame(String title, Driver driver)
	{
		super(title);
		this.driver = driver;
		JPanel panel = new JPanel();
		JButton testButton = new JButton("Save");
		textArea = new JTextArea();
		
		JLabel xLabel = new JLabel("X:");
		JLabel yLabel = new JLabel("Y:");
		JLabel orientationLabel = new JLabel("Orientation (V or H):");
		JTextField xField = new JTextField(10);
		JTextField yField = new JTextField(10);
		JTextField orientationField = new JTextField(10);
		Dimension size = panel.getPreferredSize();
		size.width = 250;
		panel.setPreferredSize(size);
		panel.setBorder(BorderFactory.createTitledBorder("Boards"));
		panel.add(xLabel);
		panel.add(xField);
		panel.add(yLabel);
		panel.add(yField);
		panel.add(orientationLabel);
		panel.add(orientationField);
		panel.add(testButton);
		
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(counter > 4)
				{
					counter = 0;
					player = 2;
				}
				String xCoordStr = xField.getText();
				String yCoordStr = yField.getText();
				String orientation = orientationField.getText();
				int xCoord = letToNum(xCoordStr);
				int yCoord = Integer.parseInt(yCoordStr);
				//System.out.println("x: " + xCoordStr + " y: " + yCoordStr + " x: " + xCoord + " y: " + yCoord);
				driver.placeShip(xCoord, yCoord, counter, player, orientation);
				counter++;
				textArea.setText("");
				if(player == 1)
					printBoard(driver.defend1);
				else
					printBoard(driver.defend2);
			}
		});
		
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
