package Views;

import javax.swing.JFrame;

public class ViewFrame extends JFrame{

	
	public ViewFrame()
	{
		ViewPanel panel = new ViewPanel();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setTitle("WebCrawler");
		add(panel);
		setSize(300,200);
		setVisible(true);
	}
}
