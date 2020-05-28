package battleship;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.EventListenerList;

public class TestFrame extends JFrame{
	
	public TestFrame(String title, Driver driver)
	{
		super(title);
		
		//set layout manager
		setLayout(new BorderLayout());
		
		//create swing component
		final JTextArea textArea = new JTextArea();
		JButton button = new JButton("Start");
		
		/**DetailPanel detailPanel = new DetailPanel();
		
		detailPanel.addDetailListener(new DetailListener() {
			public void detailEventOccurred(DetailEvent e)
			{
				String text = e.getText();
				
				textArea.append(text);
			}
		}); */
		JPanel detailPanel = new JPanel();
		Dimension size = detailPanel.getPreferredSize();
		size.width = 250;
		detailPanel.setPreferredSize(size);
		detailPanel.setBorder(BorderFactory.createTitledBorder("Boards"));
		
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
	
	private EventListenerList listenerList = new EventListenerList();
	
	public DetailPanel()
	{
		Dimension size = getPreferredSize();
		size.width = 250;
		setPreferredSize(size);
		
		setBorder(BorderFactory.createTitledBorder("Boards"));
		
		JLabel attackLabel = new JLabel("Attack:");
		JLabel defendLabel = new JLabel("Defend:");
		
		final JTextField nameField = new JTextField(10);
		JTextField otherField = new JTextField(10);
		
		JButton addBtn = new JButton("Add");
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String name = nameField.getText();
				String occupation = otherField.getText();
				
				String text = name + ": " + occupation + "\n";
				
				System.out.println(text);
				
				fireDetailEvent(new DetailEvent(this, text));
			}
		});
		// First Column ///////////////////
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		add(attackLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		add(defendLabel, gc);
		
		// Seconf Cloumn ////////////////////
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		add(nameField, gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		add(otherField, gc);
		
		//final row
		gc.weighty = 10;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 1;
		gc.gridy = 2;
		add(addBtn, gc);
	}
	
	public void fireDetailEvent(DetailEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		
		for(int i = 0; i < listeners.length; i += 2)
		{
			if(listeners[i] == DetailListener.class)
			{
				((DetailListener)listeners[i+1]).detailEventOccurred(e);
			}
		}
	}
	
	public void addDetailListener(DetailListener listener)
	{
		listenerList.add(DetailListener.class, listener);
	}
	public void removeDetailListener(DetailListener listener)
	{
		listenerList.remove(DetailListener.class, listener);
	}
}

class DetailEvent extends EventObject
{
	private String text;
	public DetailEvent(Object source, String text)
	{
		super(source);
		
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
}

interface DetailListener extends EventListener
{
	public void detailEventOccurred(DetailEvent e);
}
