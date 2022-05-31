import javafx.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.*;

public class ISU {

    // Constructor
    public ISU () {
        frame = new JFrame ("Basic JFrame Example");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setLocation(200, 200);

        myPanel = new JPanel ();
        label = new JLabel ("Hello World!");
        button = new JButton ("Hide");

        myPanel.add (label);
        myPanel.add (button);

        frame.add (myPanel);
        frame.pack ();
        frame.setVisible (true);
    } 

    public void actionPerformed (ActionEvent event) {
        
    }

    public void newGame ()
	{

    public static void main(String[] args) throws Exception {
        
    }
}
