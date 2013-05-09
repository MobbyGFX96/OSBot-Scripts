package org.osbot.api;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MPaint {

	public int x;
	public int y;
	public Rectangle rect;
	public boolean showRect;
	
	public MPaint() {
		x = 261;
		y = 343;
	}
	
	public MPaint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g) {
		rect = new Rectangle(x + 3, y + 3, 27, 25);
	}
	
}
