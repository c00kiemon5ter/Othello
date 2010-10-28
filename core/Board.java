/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */

package core;

import java.util.ArrayList;

/**
 *
 * @author Periklis Ntanasis
 */
public class Board {

    private Disk[][] disks;
    private boolean[][] board;

    public Board() {
        this(8);
    }

    public Board(int max) {
        board = new boolean[max][max];
        disks = new Disk[max][max];
        for(int i=0;i<max;i++)
            for(int j=0;j<max;j++)
            {
                board[i][j]=false;
                disks[i][j] = new Disk(true);
            }
    }

    /* return available positions for the picked disk */
    public ArrayList<int[]> getAvailableMoves(/* Disk Position */int[] p) {
        ArrayList retval = null;
        int[] availabledisk = new int[2];
        /* have to check 8 boxes around the disk */
        /* case top */
        if(this.hasTop(p))
        {
            if(board[p[0]-1][p[1]])
            {
                availabledisk[0]=p[0]-2;
                availabledisk[1]=p[1];
                /* return the position next to opponent disk */
                retval.add(availabledisk);
            }
            /* case top left */
            if(this.hasLeft(p))
            {
                if(board[p[0]-1][p[1]-1])
                {
                    availabledisk[0]=p[0]-2;
                    availabledisk[1]=p[1]-2;
                    retval.add(availabledisk);
                }
            }
            /* case top rigth */
            if(this.hasRight(p))
            {
                if(board[p[0]-1][p[1]+1])
                {
                    availabledisk[0]=p[0]+2;
                    availabledisk[1]=p[1]+2;
                    retval.add(availabledisk);
                }
            }
        }
        /* case left */
        if(this.hasLeft(p))
        {
            if(board[p[0]][p[1]-1])
            {
                availabledisk[0]=p[0];
                availabledisk[1]=p[1]-2;
                retval.add(availabledisk);
            }
        }
        /* case right */
        if(this.hasRight(p))
        {
            if(board[p[0]][p[1]+1])
            {
                availabledisk[0]=p[0];
                availabledisk[1]=p[1]+2;
                retval.add(availabledisk);
            }
        }
        /* case bottom */
        if(this.hasBottom(p))
        {
            if(board[p[0]+1][p[1]])
            {
                availabledisk[0]=p[0]+2;
                availabledisk[1]=p[1];
                retval.add(availabledisk);
            }
            /* case bottom left */
            if(this.hasLeft(p))
            {
                if(board[p[0]+1][p[1]-1])
                {
                    availabledisk[0]=p[0]+2;
                    availabledisk[1]=p[1]-2;
                    retval.add(availabledisk);
                }
            }
            /* case bottom rigth */
            if(this.hasRight(p))
            {
                if(board[p[0]+1][p[1]+1])
                {
                    availabledisk[0]=p[0]+2;
                    availabledisk[1]=p[1]+2;
                    retval.add(availabledisk);
                }
            }
        }
        return retval;
    }
    
    /* check that enough board exists :P */
    private boolean hasLeft(/* Disk position */int[] p) {
        return ((p[1]-1)>0)?true:false;
    }
    
    private boolean hasRight(int[] p) {
        return ((p[1]+1)<disks.length-1)?true:false;
    }
    
    private boolean hasTop(int[] p) {
        return ((p[0]-1)>0)?true:false;
    }
    
    private boolean hasBottom(int[] p) {
        return ((p[0]+1)<disks.length-1)?true:false;
    }
    /* End of has methods */

}
