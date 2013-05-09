package org.osbot.api;

public interface Strategy {

	public boolean canActivate();
	public void execute() throws InterruptedException;

}
