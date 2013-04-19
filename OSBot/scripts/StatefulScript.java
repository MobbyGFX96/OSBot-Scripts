

import java.awt.Graphics;

import org.osbot.script.Script;

@SuppressWarnings("rawtypes")
public abstract class StatefulScript<T extends Enum> extends Script {
	
	/**
	 * Used to determine the current state of this script
	 * @return The enumeration representing the script state
	 */
	public abstract T determine();
	
	/**
	 * Used to handle the current state of this script
	 * @param state The enumeration representing the script state
	 */
	public abstract int handle(T state);

	@Override
	public abstract void onPaint(Graphics graphics);

	@Override
	public int onLoop() {
		T state = determine();
		return handle(state);
	}
	
}
