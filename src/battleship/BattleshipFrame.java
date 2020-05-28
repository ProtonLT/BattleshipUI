package battleship;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
public class BattleshipFrame extends JFrame{
	
	private Driver driver;
	public BattleshipFrame(String title, Driver driver)
	{
		super(title);
		JPanel panel = new JPanel();
		JButton testButton = new JButton("Print");
		JTextArea textArea = new JTextArea();
				
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String[] letters = {"     ", "A  ", "B  ", "C  ", "D  ", "E  ", "F  ", "G  ", "H  ", "I  ", "J"};
				//textArea.append("UMMM IS IT WORKING UMMM?");
				for(int i = 0; i < driver.defend1.length + 1; i++)
				{
					textArea.append(letters[i]); //print
				}
				textArea.append("\n");
				//System.out.println();
				for(int i = 0; i < driver.defend1.length; i++)
				{
					textArea.append(" " + i + "  "); //print
					for(int j = 0; j < driver.defend1[i].length; j++)
					{
						if(driver.defend1[i][j].getIsShip())
						{
							if(driver.defend1[i][j].getIsHit())
								textArea.append("0  "); //print
							else
								textArea.append("O  "); //print
						}
						else
						{
							if(driver.defend1[i][j].getIsHit()) //print
								textArea.append("/  ");
							else
								textArea.append("~  "); //print
						}
					}
					textArea.append("\n"); //println
				}
			}
		});
		Dimension size = panel.getPreferredSize();
		size.width = 250;
		panel.setPreferredSize(size);
		panel.setBorder(BorderFactory.createTitledBorder("Boards"));
		panel.add(testButton);
		
		Container c = getContentPane();

		c.add(panel, BorderLayout.EAST);
		c.add(textArea, BorderLayout.WEST);
	}

}
