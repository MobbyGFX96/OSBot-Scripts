package org.osbot.api;

public class PaintCounter extends PaintManager {

	public PaintCounter() {
		this(0, 0);
	}

	public PaintCounter(int x, int y) {
		this("CounterPaint", x, y);
	}

	public PaintCounter(String title, int x, int y) {
		this("CounterPaint", x, y, 100);
	}

	public PaintCounter(String title, int x, int y, int width) {
		super(title, x, y, width, true);
	}

	public String[] getStrings() {
		return new String[] { "1", "2", "3", "4", "5" };
	}

}
