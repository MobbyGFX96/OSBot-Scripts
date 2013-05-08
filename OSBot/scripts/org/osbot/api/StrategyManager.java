package org.osbot.api;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.osbot.script.Script;

public class StrategyManager extends Script {
	
	public List<Strategy> strategyList = new LinkedList<Strategy>();
	public Iterator<Strategy> strategyTask = null;
	public boolean canRun = false;

	public void submitStrategy(Strategy... strategys) {
		if (strategys == null)
			return;
		for (Strategy strategy : strategys) {
			if (strategy != null && !strategyList.contains(strategy))
				strategyList.add(strategy);
		}
	}
	
	public void revokeStrategy(Strategy... strategys) {
		if (strategys == null)
			return;
		for (Strategy strategy : strategys) {
			if (strategy != null && strategyList.contains(strategy))
				strategyList.remove(strategy);
		}
	}

	public int onLoop() throws InterruptedException {
		return 50;
	}

	public void onPaint(Graphics g) {
		
	}
	
	/**
	 * TODO: Finish framework and make donia happy.
	 */
	
}
