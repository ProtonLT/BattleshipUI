package battleship;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BattleshipFrame extends JFrame{
	
	public BattleshipFrame(String title)
	{
		super(title);
		
		//set layout manager
		setLayout(new BorderLayout());
		
		//create swing component
		final JTextArea textArea = new JTextArea();
		JButton button = new JButton("Start");
		
		DetailPanel detailPanel = new DetailPanel();
		
		//add swing components to content panel
		Container c = getContentPane();

		c.add(textArea, BorderLayout.CENTER);
		c.add(button, BorderLayout.SOUTH);
		c.add(detailPanel, BorderLayout.EAST);

		//add behavior
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textArea.append("Hello\n");
			}
		});
	}
}

class DetailPanel extends JPanel{
	public DetailPanel()
	{
		Dimension size = getPreferredSize();
		size.width = 250;
		setPreferredSize(size);
		
		setBorder(BorderFactory.createTitledBorder("Grid"));
		
		JLabel attackLabel = new JLabel("Attack:");
		JLabel defendLabel = new JLabel("Defend:");
		
		JTextField nameField = new JTextField(10);
		JTextField otherField = new JTextField(10);
		
		JButton addBtn = new JButton("Add");
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
	}
}
