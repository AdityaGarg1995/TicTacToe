package tictactoe;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Aditya Garg
 */

/*
This TicTacToe game works through java swing and awt components
*/
public class TicTacToe2 extends JFrame implements ActionListener {

    final static String oTurn = "Player O's Turn",
                        xTurn = "Player X's Turn",
                        oWins = "Player O Wins!",
                        xWins = "Player X Wins!"; 

    JButton playButtons[][] = new JButton[3][3],       /* The nine buttons on whick "o" or "x" can be placed */
            exit            = new JButton("Exit"),     /* Exit Button*/
            newGame         = new JButton("New Game"); /* New Game Button*/

    JPanel[] row = new JPanel[5];

    JTextArea display = new JTextArea(oTurn, 2, 15);

    Font font  = new Font("Times new Roman", Font.BOLD, 24),
         font2 = new Font("Times new Roman", Font.PLAIN, 14);

    int   dimW[] = {300, 70, 150},
          dimH[] = {30, 40, 50},
          countOfMoves = 0;  

    Dimension displayDimension = new Dimension(dimW[0], dimH[0]),
              ButtonDimension  = new Dimension(dimW[1], dimH[2]),
              newGameDimension = new Dimension(dimW[2], dimH[0]);

    FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
    
    public static boolean isPlayerOTurn = true;

    
    public TicTacToe2() {

        super("TicTacToe");
        setDesign();
        setSize(480, 350);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(5, 1);
        setLayout(grid);

        initialiseGame();
    
        for (int i = 0; i < 5; i++) 
            row[i] = new JPanel(f1);
        
        row[0].add(display);
        add(row[0]);

        for (int i = 0; i < 3; i++) {
            row[1].add(playButtons[0][i]);
            row[2].add(playButtons[1][i]);
            row[3].add(playButtons[2][i]);
            add(row[i + 1]);
        }

        row[4] = new JPanel(f1);
        row[4].add(exit);
        row[4].add(newGame);
        add(row[4]);

        //display.setPreferredSize(displayDimension);
        display.setFont(font);
        display.setEditable(false);
        display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        buttonSetting(exit);
        buttonSetting(newGame);

        setVisible(true);
    }

    
//    Function to initialise the game
    public void initialiseGame() {
        display.setText("Player O's Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playButtons[i][j] = new JButton("  ");
                playButtons[i][j].setPreferredSize(ButtonDimension);
                playButtons[i][j].setEnabled(true);
                playButtons[i][j].setBackground(Color.gray);
                playButtons[i][j].setForeground(Color.red);
                playButtons[i][j].addActionListener(this);
            }
        }
    }

//    Function for settings of new game and exit buttons
    public final void buttonSetting(JButton button) {
        button.setPreferredSize(newGameDimension);
        button.setBackground(Color.gray);
        button.setForeground(Color.red);
        button.addActionListener(this);
        button.setFont(font2);
    }

    public final void setDesign() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (ClassNotFoundException | 
               InstantiationException |
               IllegalAccessException | 
               UnsupportedLookAndFeelException e) { }
    }

    
//    Function to launch a new game
    public void newGame(){
        
        countOfMoves = 0;
        
        if(isPlayerOTurn)
            display.setText(oTurn);
        else  display.setText(xTurn);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playButtons[i][j].setText("  ");
                playButtons[i][j].setEnabled(true);
            }
        }
    }


//    Helper function to create a confirm dialog box
    public int createConfirm(String message){
        return JOptionPane.showConfirmDialog(this, message, "", JOptionPane.YES_NO_OPTION);
    }

    
//    what to do when game finishes
    public void taskAfterConfirm(){
        switch(createConfirm("Play Again ? \n Yes for new game, No to exit")){
            case 0:
                try{
                   newGame();
                } catch (Exception e) { System.err.println(e.getCause()); }
                break;
            
            case 1:
                System.exit(0);
                break;
            
            // Can't reach here    
            default: System.out.println("Invalid Choice");
        }
    }
    
//    Function to display winner
    public void winner(String win){
        display.setText("");        
        JOptionPane.showMessageDialog(this, "  " + win);        
        taskAfterConfirm();
    }
    
    
//    Function to change turn:
//    If Player O has played, set turn to Player X's Turn and vice versa
    public void changeTurn(JButton button, String symbol, String turn){
        button.setText(symbol);
        display.setText(turn);
        isPlayerOTurn = !isPlayerOTurn;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {

        JButton clickedButton = (JButton) ae.getSource();

//        what to do if newGame clicked
        if (clickedButton == newGame) {
            if(createConfirm("NEW GAME?") == 0){
                try {
                    newGame();
                } catch (Exception e) { System.err.println(e.getCause()); }
            }
        }

//        what to do if exit clicked
        if (clickedButton == exit){
            if(createConfirm("EXIT CURRENT GAME?") == 0)
                System.exit(0);
        }
        
//        what to do if any of playButttons clicked
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (clickedButton == playButtons[i][j]) {
                    playButtons[i][j].setEnabled(false);
                    countOfMoves++;
                    
                    if (isPlayerOTurn) 
                        changeTurn(playButtons[i][j], "o", xTurn);
                    else  changeTurn(playButtons[i][j], "x", oTurn);
                }
            }
        }

        
//         Determine the winner if any or if the game is a draw
        String symbol1 = playButtons[0][0].getText();  
        boolean aPlayerWon = false;                  /* Boolean to signify if a player has won*/

        //123 and 147
        if ((playButtons[0][1].getText().equals(symbol1) && playButtons[0][2].getText().equals(symbol1))
            || (playButtons[1][0].getText().equals(symbol1) && playButtons[2][0].getText().equals(symbol1))) {

            if ("o".equals(symbol1)) 
                winner(oWins);
            
            else if ("x".equals(symbol1)) 
                winner(xWins);
            
            aPlayerWon = true;
        }   
            
        symbol1 = playButtons[1][1].getText();

        //159 and 258 and 456 and 359
        if ((playButtons[0][0].getText().equals(symbol1) && playButtons[2][2].getText().equals(symbol1))
            || (playButtons[1][0].getText().equals(symbol1) && playButtons[1][2].getText().equals(symbol1))
            || (playButtons[0][1].getText().equals(symbol1) && playButtons[2][1].getText().equals(symbol1))
            || (playButtons[0][2].getText().equals(symbol1) && playButtons[2][0].getText().equals(symbol1))) {

            if ("o".equals(symbol1)) 
                winner(oWins);
            
            else if ("x".equals(symbol1)) 
                winner(xWins);
            
            aPlayerWon = true;
        }

        symbol1 = playButtons[2][2].getText();

        //369 and 789
        if ((playButtons[0][2].getText().equals(symbol1) && playButtons[1][2].getText().equals(symbol1))
            || (playButtons[2][0].getText().equals(symbol1) && playButtons[2][1].getText().equals(symbol1))) {

            if ("o".equals(symbol1)) 
                winner(oWins);
            
            else if ("x".equals(symbol1)) 
                winner(xWins);
            
            aPlayerWon = true;
        }
                
//        Checking if the game is a draw
        if(!aPlayerWon /* i.e. none of the player has won*/ 
           && countOfMoves == 9 /* The count of moves has reached 9 i.e. all playButtons have been clicked*/ ) {
            JOptionPane.showMessageDialog(this, "  This game is a draw");
            taskAfterConfirm();
        }
    }

    
    public static void main(String[] args) {
        try{
            new TicTacToe2();
        } catch(Exception e){ System.err.println(e.getCause()); }
    }
}
