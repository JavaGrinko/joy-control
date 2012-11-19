package grinch.air.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CursorDisplay extends JPanel {
	private static final long serialVersionUID = 1L;
	private int x = 128;
	private int y = 128;
	public void setCoors(int x,int y){
		this.x = x;
		this.y = y;
		this.repaint();
	}
	@Override
	public void paint(Graphics g){
		Graphics2D gfx = (Graphics2D) g;
		gfx.clearRect(0, 0, 256, 256);
		gfx.drawOval(x, y, 5, 5);
		gfx.drawLine(0, 0, 255, 0);
		gfx.drawLine(0, 0, 0, 255);
		gfx.drawLine(0, 255, 255, 255);
		gfx.drawLine(255, 0, 255, 255);
	}
}
