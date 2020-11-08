import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Observable;

/**
 * sets up the menu with new game and quit
 * @author Mmedara Josiah
 * @version 1.0.0
 */
public class RiskView extends JFrame{
    private JPanel mainPanel;
    private JButton newGameButton, quitButton;
    private String newGameButtonCommand = "newGameButton";
    private String quitButtonCommand = "quitButton";

    /**
     * constructs the new game menu
     */
    public RiskView (){
        //initialize mainPanel with grid layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        //create buttons for main panel
        newGameButton = new JButton("New Game");
        quitButton = new JButton("Quit");
        setActionCommand();

        //add buttons to main panel
        mainPanel.add(newGameButton);
        mainPanel.add(quitButton);

        add(mainPanel);
        setTitle("Risk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * sets action commands on objects
     */
    public void setActionCommand(){
        newGameButton.setActionCommand(newGameButtonCommand);
        quitButton.setActionCommand(quitButtonCommand);
    }

    /**
     * adds action listeners to objects
     * @param e event
     */
    public void addActionListeners(ActionListener e){
        newGameButton.addActionListener(e);
        quitButton.addActionListener(e);
    }
}
