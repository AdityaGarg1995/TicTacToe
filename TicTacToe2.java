/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Aditya Garg
 */
public class TicTacToe2 extends JFrame implements ActionListener {

    JButton playButtons[][] = new JButton[3][3],
            exit            = new JButton("Exit"),
            newGame         = new JButton("New Game");

    JPanel[] row = new JPanel[5];

    JTextArea display = new JTextArea("Player O's Turn", 2, 15);

    Font font  = new Font("Times new Roman", Font.BOLD, 24),
         font2 = new Font("Times new Roman", Font.PLAIN, 14);

    int[] dimW = {300, 70, 150},
          dimH = {30, 40, 50};

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

    public void initialise() {

        display.setText("Player O's Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playButtons[i][j].setText("  ");
                playButtons[i][j].setEnabled(true);
            }
        }

    }

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

    @Override
    public void actionPerformed(ActionEvent ae) {

        JButton clickedButton = (JButton) ae.getSource();

        if (clickedButton == newGame) {
            if(JOptionPane.showConfirmDialog(this,"NEW GAME?","",JOptionPane.YES_NO_OPTION) == 0){
                initialise();
                setVisible(true);
            }
        }

        if (clickedButton == exit){
            if(JOptionPane.showConfirmDialog(this,"EXIT CURRENT GAME?","",JOptionPane.YES_NO_OPTION) == 0)
                System.exit(0);
        }
        

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if (clickedButton == playButtons[i][j]) {

                    playButtons[i][j].setEnabled(false);

                    if (isPlayerOTurn) {
                        playButtons[i][j].setText("o");
                        display.setText("Player X's Turn");
                        isPlayerOTurn = false;
                    }
                    
                    else {
                        playButtons[i][j].setText("x");
                        display.setText("Player O's Turn");
                        isPlayerOTurn = true;
                    }

                }

            }

        }

        String symbol1 = playButtons[0][0].getText();

        //123 and 147
        if ((playButtons[0][1].getText().equals(symbol1) && playButtons[0][2].getText().equals(symbol1))
            || (playButtons[1][0].getText().equals(symbol1) && playButtons[2][0].getText().equals(symbol1))) {

            if ("o".equals(symbol1)) {
                display.setText("");
                JOptionPane.showMessageDialog(this, "  Player O Wins");
            }
            
            else if ("x".equals(symbol1)) {
                display.setText("");
                JOptionPane.showMessageDialog(this, "  Player X Wins");
            }

        }   
            
        symbol1 = playButtons[1][1].getText();

        //159 and 258 and 456 and 359
        if ((playButtons[0][0].getText().equals(symbol1) && playButtons[2][2].getText().equals(symbol1))
            || (playButtons[1][0].getText().equals(symbol1) && playButtons[1][2].getText().equals(symbol1))
            || (playButtons[0][1].getText().equals(symbol1) && playButtons[2][1].getText().equals(symbol1))
            || (playButtons[0][2].getText().equals(symbol1) && playButtons[2][0].getText().equals(symbol1))) {

            if ("o".equals(symbol1)) {
                display.setText("");
                JOptionPane.showMessageDialog(this, "  Player O Wins");
                int n = JOptionPane.showConfirmDialog(this, "Play Again ? \n Yes for new game, No to exit",
                                                      "", JOptionPane.YES_NO_OPTION);
                if (n == 0){
                    TicTacToe2 t = new TicTacToe2();
                }
                else if (n == 1)
                    System.exit(0);
            }
            
            else if ("x".equals(symbol1)) {
                display.setText("");
                JOptionPane.showMessageDialog(this, "  Player X Wins");
                int n = JOptionPane.showConfirmDialog(this, "Play Again ? \n Yes for new game, No to exit",
                                                      "", JOptionPane.YES_NO_OPTION);
                if (n == 0){
                    TicTacToe2 t = new TicTacToe2();
                }
                else if (n == 1)
                    System.exit(0);                
            }

        }

        symbol1 = playButtons[2][2].getText();

        //369 and 789
        if ((playButtons[0][2].getText().equals(symbol1) && playButtons[1][2].getText().equals(symbol1))
            || (playButtons[2][0].getText().equals(symbol1) && playButtons[2][1].getText().equals(symbol1))) {

            if ("o".equals(symbol1)) {
                display.setText("");
                JOptionPane.showMessageDialog(this, "  Player O Wins");
            }
            
            else if ("x".equals(symbol1)) {
                display.setText("");
                JOptionPane.showMessageDialog(this, "  Player X Wins");
            }

        }

    }

    public static void main(String[] args) {
        TicTacToe2 t = new TicTacToe2();
    }

}
