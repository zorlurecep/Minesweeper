package Model.Tile;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Tile extends JButton{
    public int r,c;   //position
    public int count; //how many surrounding bomb does it have?
    public boolean isMine; //false by default
    boolean flagged;
    public int nrOfFlaggedTiles = 0;

    public Tile(int r , int c) {
        this.r = r;
        this.c = c;
        flagged = false;
    }

    /**
     * TOGGLE FUNCTION FOR EXPLOSIVE TILE
     */

    public void isFlagged(){
        flagged = !flagged; //if flagged true
        if(flagged){
           this.setIcon(new ImageIcon("src/UsedAssets/flag.png"));


        } else {
            this.setIcon(null);

        }

    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }


}



