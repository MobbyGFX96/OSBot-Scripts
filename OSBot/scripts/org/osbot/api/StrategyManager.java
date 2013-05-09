package org.osbot.api;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.osbot.script.Script;

public abstract class StrategyManager extends Script {

	public List<Strategy> strategyList = new LinkedList<Strategy>();
	public Iterator<Strategy> strategyTask = null;
	public boolean canRun = false;

	public abstract boolean initiate();

	public abstract void close();

	public abstract void onRepaint(Graphics2D g);

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

	public void onStart() {
		if (initiate()) {
			canRun = true;
		} else {
			try {
				stop();
				log("Conditions not met closing script.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int onLoop() throws InterruptedException {
		if (canRun) {
			if (strategyTask == null && !strategyTask.hasNext())
				strategyTask = strategyList.iterator();
			else {
				final Strategy currentStrat = strategyTask.next();
				if (currentStrat != null && currentStrat.canActivate())
					currentStrat.execute();
			}
		}
		return 50;
	}

	public void onPaint(Graphics g) {
		Graphics2D g1 = (Graphics2D) g;
		onRepaint(g1);
	}

	public void onExit() {
		canRun = false;
		close();
	}

	/**
	 * TODO: Finish framework and make donia happy.
	 */

}
