import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.model.NPC;
import org.osbot.script.rs2.model.Player;

@ScriptManifest(name = "Experiments Pro", author = "MobbyGFX", version = 1.0, info = "Kills experiments in the experiment cave.")
public class ExperimentsPro extends Script {

	private final Color color1 = new Color(102, 255, 255);
	private final Color color2 = new Color(0, 51, 255);
	private final BasicStroke stroke1 = new BasicStroke(2);
	private final Font font1 = new Font("Verdana", 2, 18);
	// 1677, 1678
	// Center tile, = 3559, 9947, 0
	// 311 350 rect...
	final int[] EXPERIMENTS = { 1678, 1677 };
	final int[] POTIONS = {};
	long startTime;
	public MouseTrail trail;
	public Position centerTile = new Position(3551, 9931, 0);
	private String status = "idle";

	public void onStart() {
		trail = new MouseTrail();
		log("Welcome to Experiments Pro: By MobbyGFX");
		startTime = System.currentTimeMillis();
	}

	public int onLoop() {
		try {
			attackExperiments();
			usePotions();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 50;
	}

	private void attackExperiments() throws InterruptedException {
		Player player = client.getMyPlayer();
		NPC experiments = closestNPC(EXPERIMENTS);
		if (experiments == null)
			return;
		if (!player.isMoving()
				&& experiments.getPosition().distance(player.getPosition()) > 10
				&& !experiments.isUnderAttack()) {
			walk(experiments.getPosition());
			status = "Walking to experiment";
			sleep(500);
		}
		if (experiments.getPosition().distance(player.getPosition()) <= 10) {
			if (!player.isUnderAttack() && !experiments.isUnderAttack()) {
				selectEntityOption(experiments, "Attack");
				client.moveCameraToEntity(experiments);
				status = "Attacking Experiment...";
				sleep(500 + random(1500));
			}
		}
	}

	public void usePotions() {
		for (int i : POTIONS) {
			if (client.getInventory().contains(i)) {
				// Do potions here?
			} else {
				return;
			}
		}
	}

	public void onPaint(Graphics g1) {
		int runTime = (int) (System.currentTimeMillis() - startTime);
		Graphics2D g = (Graphics2D) g1;
		g.setColor(color1);
		g.fillRoundRect(217, 346, 275, 110, 16, 16);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawRoundRect(217, 346, 275, 110, 16, 16);
		g.setFont(font1);
		g.drawString("Run time: " + timeToString(runTime), 227, 371);
		g.drawString("Status: " + status, 225, 403);
		g.drawString("Exp P/H: ", 226, 435);
		g.setColor(Color.decode("0xCC9933"));
		trail.add(client.getMousePosition());
		trail.draw(g);
		drawProgressBar(g, 50, 50, 100, 17, Color.black, Color.cyan, 150, 25);
	}

	public static String timeToString(int time) {
		int seconds = (int) (time / 1000) % 60;
		int minutes = (int) ((time / (1000 * 60)) % 60);
		int hours = (int) ((time / (1000 * 60 * 60)) % 24);
		return (hours < 10 ? "0" + hours : hours) + ":"
				+ (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds);
	}

	public static void drawProgressBar(Graphics2D g, int x, int y, int width,
			int height, Color main, Color progress, int alpha, int percentage) {
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		GradientPaint base = new GradientPaint(x, y, new Color(200, 200, 200,
				alpha), x, y + height, main);
		GradientPaint overlay = new GradientPaint(x, y, new Color(200, 200,
				200, alpha), x, y + height, progress);
		if (height > width) {
			g.setPaint(base);
			g.fillRect(x, y, width, height);
			g.setPaint(overlay);
			g.fillRect(x,
					y + (height - (int) (height * (percentage / 100.0D))),
					width, (int) (height * (percentage / 100.0D)));
		} else {
			g.setPaint(base);
			g.fillRect(x, y, width, height);
			g.setPaint(overlay);
			g.fillRect(x, y, (int) (width * (percentage / 100.0D)), height);
		}
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}

	public static class MouseTrail {
		public static Point point;

		public MouseTrail() {
			point = new Point();
		}

		public void add(final Point p) {
			point = p;
		}

		public void draw(Graphics g) {
			g.setColor(Color.decode("0xCC9933"));
			g.drawLine(0, (int) point.getY(), 800, (int) point.getY());
			g.drawLine((int) point.getX(), 0, (int) point.getX(), 600);
		}

	}

}
