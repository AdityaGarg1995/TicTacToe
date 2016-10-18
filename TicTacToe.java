package tictactoe;

import java.util.Scanner;

public class TicTacToe {

    public static Player getPlayer(int playerNumber) {
        
        System.out.println("Enter player " + playerNumber + " name");
        
        Scanner s = new Scanner(System.in);
        
        String name = s.nextLine();

        char symbol = ' ';

        System.out.println("Enter player " + playerNumber + " symbol");

        symbol = s.nextLine().charAt(0);
    
        return new Player(name, symbol);
    
    }

    public static void start() {
        
        Player p1 = getPlayer(1);
        
        Scanner s = new Scanner(System.in);
        
        boolean done = false;
        
        Player p2 = null;
        
        while (!done) {
           
            p2 = getPlayer(2);
            
            if (p2.symbol != p1.symbol) 
                done = true;
            
            else   System.out.println("Both players cant have same symbol");
            
        }

        Board b = new Board(p1.symbol, p2.symbol);

        boolean isPlayer1Turn = true;

        b.print();

        while (b.getGameStatus() == Board.NOT_FINISHED) {
        
            Player currentPlayer = null;
            
            if (isPlayer1Turn)  
                currentPlayer = p1;
            
            else   currentPlayer = p2;
           
            System.out.println(currentPlayer.name + "'s turn");
            
            System.out.println("Enter x coordinate");
            int x = s.nextInt();
        
            System.out.println("Enter y coordinate");
            int y = s.nextInt();
            
            try {
                b.makeAMove(x,y,currentPlayer.symbol);
                isPlayer1Turn = !isPlayer1Turn;
            }
            catch (InvalidMoveException e) {
                System.out.println("Invalid move try again!");
            }
            
            b.print();
        
        }

        int result = b.getGameStatus();
        
        switch(result){
        
            case Board.PLAYER1_WON:  System.out.println(p1.name + "won!");
                break;
            
            case Board.PLAYER2_WON:  System.out.println(p2.name + "won!");
                break;
            
            default: System.out.println("Draw!");
                
        }

    }

    public static void main(String[] args) {
        start();
    }

}
