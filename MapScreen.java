/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Map Selection Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class MapScreen extends JPanel implements ActionListener, ComponentListener
{
	JButton[] defaultMapsArr = new JButton[8];
	JButton[] userMapsArr = new JButton[8];
	String purpose = "Start";
	EditorScreen ES;
	
	MapScreen(EditorScreen editor)
	{
		addComponentListener(this);
		
		ES = editor;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel prompt = new JLabel("Please select a map:", JLabel.CENTER);
		prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel defaultLabel = new JLabel("Default Maps:", JLabel.CENTER);
		defaultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel userLabel = new JLabel("User Maps:", JLabel.CENTER);
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel defaultMaps = new JPanel();
		JPanel userMaps = new JPanel();
		
		defaultMaps.setLayout(new GridLayout(2, 4, 5, 5));
		for(int i = 0; i < 8; i++)
		{
			defaultMapsArr[i] = new JButton("Default Map "  + i);
			defaultMapsArr[i].addActionListener(this);
			defaultMaps.add(defaultMapsArr[i]);
		}
		
		userMaps.setLayout(new GridLayout(2, 4, 5, 5));
		for(int i = 0; i < 8; i++)
		{
			userMapsArr[i] = new JButton("User Map "  + i);
			userMapsArr[i].addActionListener(this);
			userMaps.add(userMapsArr[i]);
		}
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(prompt);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(defaultLabel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(defaultMaps);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(userLabel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(userMaps);
	}	// end constructor(EditorScreen)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(purpose.equals("Start")) //Find which map to start playing
		{
			String mapToPlay = "";
			for(int i = 0; i < defaultMapsArr.length; i++)
			{
				if(ae.getSource() == defaultMapsArr[i])
					mapToPlay = "D" + i + ".txt";
			}
			for(int i = 0; i < userMapsArr.length; i++)
			{
				if(ae.getSource() == userMapsArr[i])
					mapToPlay = "U" + i + ".txt";
			}
			CoolPlatformer.changeScreen("GameScreen", mapToPlay);
		}
		else if(purpose.equals("Save")) // Find which location to save to
		{
			String mapToSave = ""; 
			for(int i = 0; i < userMapsArr.length; i++)
			{
				if(ae.getSource() == userMapsArr[i])
					mapToSave = "U" + i + ".txt";
			}
			ES.saveToManager(System.getProperty("user.dir") + "/include/levels", mapToSave);
			CoolPlatformer.changeScreen("TitleScreen");
		}
		else if(purpose.equals("Load")) // Find which location to load from
		{
			String mapToLoad = "";
			for(int i = 0; i < defaultMapsArr.length; i++)
			{
				if(ae.getSource() == defaultMapsArr[i])
					mapToLoad = "D" + i + ".txt";
			}
			for(int i = 0; i < userMapsArr.length; i++)
			{
				if(ae.getSource() == userMapsArr[i])
					mapToLoad = "U" + i + ".txt";
			}
			ES.loadFromManager(System.getProperty("user.dir") + "/include/levels", mapToLoad);
			CoolPlatformer.changeScreen("EditorScreen");
		}
	}	// end method actionPerformed
	
	public void setPurpose(String purp)
	{
		purpose = purp;
	}
	
	public void componentShown(ComponentEvent e)
	{
		if(purpose.equals("Save"))
		{
			for(int i = 0; i < defaultMapsArr.length; i++)
			{
				defaultMapsArr[i].setEnabled(false); // Cannot save to a default map
			}
		}
	}
	public void componentHidden(ComponentEvent e)
	{
		for(int i = 0; i < defaultMapsArr.length; i++)
		{
			defaultMapsArr[i].setEnabled(true);
		}
	}
	public void componentResized(ComponentEvent e){   }
	public void componentMoved(ComponentEvent e){   }
}	// end class MapScreen
