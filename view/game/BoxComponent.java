package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoxComponent extends JComponent {
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;


    public BoxComponent(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        Border border ;
        if(isSelected){
            border = BorderFactory.createLineBorder(Color.red,3);
        }else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

   public int getBlockId() {
        if (getWidth() == 50 && getHeight() == 50) return 1; // 1×1
        if (getWidth() == 100 && getHeight() == 50) return 2; // 1×2
        if (getWidth() == 50 && getHeight() == 100) return 3; // 2×1
        if (getWidth() == 100 && getHeight() == 100) return 4; // 2×2
        return 0;
    }
}
