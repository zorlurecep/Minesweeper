package Model;

import Model.Tile.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements MouseListener {
    int nrOfRows=8;
    int nrOfCols=8;
    int nrOfMines=10;
    int nrOfUnopenedTiles = nrOfRows*nrOfCols;
    Tile[][] board = new Tile[nrOfRows][nrOfCols];

    public Board() {
        JFrame frame = new JFrame("Minesweeper BETA");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(nrOfRows, nrOfCols));

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Tile t = new Tile(row, col);        //Create a Model.Tile.Tile object with a given row,col value
                t.addMouseListener(this);
                frame.add(t);            //add it to GUI
                board[row][col] = t;    //for data structure
            }
        }

        generateMines();
        countMines();
        frame.setVisible(true);
    }

    public  void generateMines() {
        int count = 0;
        while (count < nrOfMines) {
            /**
             *  GENERATE AT A RANDOM LOCATION
             */
            int row = (int) (Math.random() * board.length);
            int col = (int) (Math.random() * board[0].length);

            /**
             * BECASUSE THE Math.random() WILL GIVE A VALUE BETWEEN 0.0 AND 1.0
             * THERE ARE CHANCES THAT 2 OR MORE MINES WILL END UP IN THE SAME TILE
             * SO WE NEED TO CHECK IF THERE ARE MINES ALREADY PRESENT IN THE TILES
             * IF YES, THEN IT RE-DISTRIBUTES THE MINES
             * NESTED WHILE --> FIRST THE OUTER WHILE IS EXECUTED AND THEN THE INNER ONE
             */
            while (board[row][col].isMine) {
                row = (int) (Math.random() * board.length);
                col = (int) (Math.random() * board[0].length);
            }
            board[row][col].isMine = true; //this tile with coordinates (row,col) will be a mine!
            count++;
        }
    }

    public void countMines() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                updateCount(row, col);
            }
        }
    }

    public void updateCount(int r, int c){
        //if no mine at a certain location--> do nothing
        if(!board[r][c].isMine) {return;}

        //otherwise.....
        for(int row= r-1; row<=r+1; row++){
            for(int col= c-1; col<=c+1; col++){
                try {
                    board[row][col].count++;
                }catch (Exception e){} //do nothing --> out of bounds
            }
        }
    }

    /**
     *REVEALS THE TILES THAT HAVE NO MINES (0)
     */
    public void reveal(int r, int c){
        //base case is when we have nothing to do
        if(r<0 || r>= board.length || c<0 || c>=board[0].length || board[r][c].getText().length()>0 || !board[r][c].isEnabled()){
            return;
        }else if (board[r][c].count!=0){
            board[r][c].setText(board[r][c].count + "");
            board[r][c].setEnabled(false);
            nrOfUnopenedTiles--;
           // System.out.println(nrOfUnopenedTiles);
        }else{
            board[r][c].setEnabled(false);
            reveal (r-1, c); //NORTH
            reveal (r+1,c); //SOUTH
            reveal(r,c-1); //EAST
            reveal(r,c+1); // WEST
            nrOfUnopenedTiles--;
          //  System.out.println(nrOfUnopenedTiles);
        }
    }

    /**
     * IF THE NUMBER OF UNOPENED TILES IS EQUAL TO THE NUMBER OF MINES THEN THE PLAYER HAS WON
     */
    public void checkWon(){
        if(nrOfMines==nrOfUnopenedTiles){
            revealMines();
            JOptionPane.showMessageDialog(null, "Congratulations you won!");
        }
    }

    public void gameOver() {
        for (Tile[] tiles : board) {
            for (int col = 0; col < board[0].length; col++) {
                if (tiles[col].isMine) {
                    tiles[col].setIcon(new ImageIcon("src/UsedAssets/mine.png"));

                }
                tiles[col].setText(tiles[col].getCount() + "");
                tiles[col].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(null,"Oh no! You lost :( .....Better luck next time!");
        repaint();
    }


    public void revealMines(){
        for (Tile[] tiles : board) {
            for (int col = 0; col < board[0].length; col++) {
                if (tiles[col].isMine) {
                    tiles[col].setIcon(new ImageIcon("src/UsedAssets/mine.png"));
                    tiles[col].setEnabled(false);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //LEFT CLICK
        if(e.getButton()==1){
            Tile t = (Tile)(e.getComponent());
            if (t.isMine){
                gameOver();
            }else{
                reveal(t.r,t.c);
                checkWon();

            }
        }
        else if(e.getButton()==3){
            Tile t = (Tile) e.getComponent();
            t.isFlagged();
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}