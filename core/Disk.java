/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */

package core;

/**
 *
 * @author Periklis Ntanasis
 */
public class Disk {

    private boolean color;

    // Let's say false for black and true for white
    public Disk(boolean color) {
        this.color=color;
    }

    public void pawned() {
        this.color=!this.color;
    }

}
