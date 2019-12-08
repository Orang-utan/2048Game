/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

public class Tile extends GameObj {
    public static final int SIZE = 100;
    public int init_pos_x = 0;
    public int init_pos_y = 0;

    private Color color;
    
    private int number = 2;
    
    
    public Tile(int courtWidth, int courtHeight, Color color, int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y, SIZE, SIZE, courtWidth, courtHeight);
        
        this.init_pos_x = init_pos_y;
    	this.init_pos_x = init_pos_y;

        this.color = color;
    }
    
    public Tile(Tile that) {
        super(that.init_pos_x, that.init_pos_y, SIZE, SIZE, that.getCourtWidth(), that.getCourtHeight());
        
        this.init_pos_x = init_pos_y;
    	this.init_pos_x = init_pos_y;

        this.color = that.color;
    }
    
    public int getNumber() {
    	return this.number;
    }
    
    public void setNumber(int number) {
    	this.number = number;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20)); 
        int width = g.getFontMetrics().stringWidth(this.number+"");
        g.drawString(this.number + "", this.getPx() + 50 - width, this.getPy() + 55);
    }  
    
    @Override
    public boolean equals(Object o) { 

        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Tile)) { 
            return false; 
        } 
          
        Tile c = (Tile) o; 
          
        return (this.getNumber() == c.getNumber());
    } 
}