package org.osbot.api;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public abstract class PaintManager {

	public String title;
	public int x;
	public int y;
	public int width;
	public String[] strings;
	public boolean reloadStrings;

	public PaintManager() {
		this(0, 0);
	}

	public PaintManager(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public PaintManager(String title, int x, int y, int width,
			boolean reloadStrings, String... strings) {
		this.title = title;
		this.x = x;
		this.y = y;
		this.width = width;
		this.reloadStrings = reloadStrings;
		this.strings = strings;
	}

	public abstract String[] getStrings();

	public void drawString(Graphics2D g, String s, Rectangle container, int y) {
		g.drawString(s,
				(int) container.getMinX() + ((int) container.getWidth() / 2)
						- getXPositioningForString(g, s), y);
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public int getXPositioningForString(Graphics2D g, String string) {
		return (int) g.getFontMetrics().getStringBounds(string, g).getCenterX();
	}

	public void onPaintManager(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Verdana", Font.BOLD, 8);
		g.setFont(font);
		if (reloadStrings)
			strings = getStrings();
		Rectangle box = new Rectangle(x, y, width,
				((strings.length + 1) * 12) + 2);
		g.setColor(Color.black);
		g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		g.fillRect(x, y, width, ((strings.length + 1) * 12) + 2);
		g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
		g.drawRect(x, y, width, ((strings.length + 1) * 12) + 2);
		g.setColor(Color.white);
		g.drawRect(x, y - 1, width, ((strings.length + 1) * 12) + 2);
		drawString(g, title, box, y + 11);
		g.drawLine(x, y + 12, x + width, y + 12);
		for (int i = 0; i < strings.length; i++) {
			g.drawString(strings[i], x + 4, y + 24 + (i * 12));
		}
	}

}
