package Views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import crawler.Spider;

public class ViewPanel extends JPanel{
		
	
	 @Override
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         JButton button_1 = new JButton();
         JLabel label = new JLabel("Url :");
         label.setBounds(20,0,70,20);
         JTextField url = new JTextField();
         JLabel label2 = new JLabel("Páginas:");
         JLabel label3 = new JLabel("Arquivos txt serão armazenados no diretorio D:");
         label2.setBounds(20,50,70,20);
         JTextField mPag = new JTextField();
         mPag.setBounds(80, 50, 50, 20);
         url.setBounds(50, 0, 200, 20);
         button_1.setBounds(160,50,80,30); 
         label3.setBounds(5, 100, 300, 60);
         add(label3);
         button_1.setText("Crawl");
         add(mPag);
         add(url);
         add(label);
         add(label2);
         add(button_1);
         
         button_1.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  Spider spider = new Spider(Integer.parseInt(mPag.getText()),url.getText());
        	  } 
        	} );
         
     }
}
