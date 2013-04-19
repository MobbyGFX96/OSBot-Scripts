import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.model.Item;
import org.osbot.script.rs2.model.NPC;
import org.osbot.script.rs2.model.Player;
import org.osbot.script.rs2.skill.Skill;

/**
 * @author Scape & MobbyGFX <OSBot>
 */
@ScriptManifest(name = "Rock Crabs", author = "Scape & MobbyGFX", version = 1.0, info = "Kills crabs on the right side.")
public class RockCrabs extends Script {

	// public NPC crab;
	public int foodId;
	final int[] allids = { 1265, 1773, 1266, 1267, 1268 };
	final int[] attackable = { 1267, 1265 };
	final int[] rock = { 1266, 1268 };
	public boolean walkaway;
	public boolean walkback;
	Position reAttack = new Position(2707, 3678, 0);
	Position sideloc = new Position(2706, 3718, 0);
	public boolean inCombat = false;
	public GUI crabGui;
	public boolean startScript = false;
	MouseTrail trail;

	private int getFoodId() {
		int ids = 0;
		for (Item item : client.getInventory().getItems()) {
			if (foodId == 0) {
				foodId = item.getId();
				// }
			}
		}
		return ids;
	}

	@Override
	public void onStart() {
		crabGui = new GUI();
		trail = new MouseTrail();
		startTime = System.currentTimeMillis();
		xpStart1 = client.getSkills().getExperience(Skill.ATTACK);
		xpStart2 = client.getSkills().getExperience(Skill.STRENGTH);
		xpStart3 = client.getSkills().getExperience(Skill.DEFENCE);
		xpStart4 = client.getSkills().getExperience(Skill.RANGED);
		xpStart5 = client.getSkills().getExperience(Skill.MAGIC);

		lvlStart = client.getSkills().getExperience(Skill.ATTACK);
		lvlStart1 = client.getSkills().getExperience(Skill.STRENGTH);
		lvlStart2 = client.getSkills().getExperience(Skill.DEFENCE);
		lvlStart3 = client.getSkills().getExperience(Skill.RANGED);
		lvlStart4 = client.getSkills().getExperience(Skill.MAGIC);
		// getFoodId();
		log("Food id: " + foodId + "");
		log("Author: Scape and 'MobbyGFX");

	}

	public void skills() {
		if (whatskill == 0) {
			if (client.getSkills().getExperience(Skill.ATTACK) - lvlStart > 0) {
				lvlCurrent = (client.getSkills().getLevel(Skill.ATTACK));
				lvlStart = (client.getSkills().getLevel(Skill.ATTACK));
				whatskill = 1;
			}
			if (client.getSkills().getExperience(Skill.STRENGTH) - lvlStart1 > 0) {
				lvlCurrent = (client.getSkills().getLevel(Skill.STRENGTH));
				lvlStart = (client.getSkills().getLevel(Skill.STRENGTH));
				whatskill = 2;
			}
			if (client.getSkills().getExperience(Skill.DEFENCE) - lvlStart2 > 0) {
				lvlCurrent = (client.getSkills().getLevel(Skill.DEFENCE));
				lvlStart = (client.getSkills().getLevel(Skill.DEFENCE));
				whatskill = 3;
			}
			if (client.getSkills().getExperience(Skill.RANGED) - lvlStart3 > 0) {
				lvlCurrent = (client.getSkills().getLevel(Skill.RANGED));
				lvlStart = (client.getSkills().getLevel(Skill.RANGED));
				whatskill = 4;
			}
			if (client.getSkills().getExperience(Skill.MAGIC) - lvlStart4 > 0) {
				lvlCurrent = (client.getSkills().getLevel(Skill.MAGIC));
				lvlStart = (client.getSkills().getLevel(Skill.MAGIC));
				whatskill = 5;
			}
		}
	}

	@Override
	public int onLoop() {
		if (crabGui.startScript) {
			Player player = client.getMyPlayer();
			
			// if (player.)

			if (inCombat) {
				Status = "Attacking...";
				// return;
			}
			if (player.isMoving()) {
				Status = "Walking...";
			}

			// log(""+client.getMyPlayer().instance.+"");

			/*
			 * client.getMyPlayer().instance.getHealth();
			 * 
			 * if (client.getInventory().contains(foodid)) { if
			 * (client.getMyPlayer().instance.getHealth() <= 75) {
			 * 
			 * try { selectInventoryOption(
			 * client.getInventory().getSlotForId(foodid), "Use"); } catch
			 * (InterruptedException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } return 1000; } }
			 */

			if (walkback) {
				if (sideloc.distance(player.getPosition()) <= random(10)) {
					walkaway = false;
					walkback = false;
					return 1000;
				}
				try {
					walk(sideloc);
					// Status = "Walking";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 500 + random(700);
			}

			if (walkaway) {
				if (reAttack.distance(player.getPosition()) <= random(10)) {
					walkaway = false;
					walkback = true;
					log("Walking back...");
					return 1000;
				}

				try {
					walk(reAttack);
					// Status = "Walking";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 500 + random(750);
			}

			skills();
			locheck();
			attack();
			notatt();
			// log("Test Loojjujp lol");
		}
		return 1000;
	}

	public void locheck() {
		NPC all = closestNPC(allids);
		Player player = client.getMyPlayer();

		if (all != null) {
			if (all.getPosition().getX() <= 2692) {
				log("Running to middle of side...");
				try {
					walk(sideloc);
					// Status = "Walking";
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				return;
			}
		}

		if (all != null) {
			if (all.getPosition().getY() >= 3728 && !player.isUnderAttack()
					&& all.isUnderAttack()) {
				log("Npc not in multi, running to side...");
				try {
					walk(sideloc);
					// Status = "Walking";
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				return;
			}
		}
	}

	public void notatt() {
		Player player = client.getMyPlayer();
		NPC rocks = closestNPC(rock);

		if (rocks != null) {
			if (rocks.getPosition().distance(player.getPosition()) <= 1.5) {
				try {
					sleep(2000 + random(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (rocks.getPosition().distance(player.getPosition()) <= 1) {
					if (!rocks.isUnderAttack() && !player.isUnderAttack()
							&& !player.isMoving() && !inCombat) {
						walkaway = true;
						log("Walking away...");
					}
				}
			}
		}
	}

	public void attack() {
		Player player = client.getMyPlayer();
		NPC attackables = closestNPC(attackable);
		NPC rocks = closestNPC(rock);

		if (attackables.getPosition().distance(player.getPosition()) >= 1.5) {
			inCombat = false;
		}

		if (inCombat) {
			return;
		}

		if (attackables == null || rocks == null) {
			return;
		}

		if (attackables.getPosition().distance(player.getPosition()) <= 1.5) {
			try {
				selectEntityOption(attackables, "Attack");
				// Status = "Attacking";
				inCombat = true;
				sleep(500 + random(1500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		if (!player.isMoving() && !inCombat) {
			if (rocks.getPosition().distance(player.getPosition()) >= 1) {
				try {
					// Status = "Walking";
					walkExact(rocks.getPosition());
					sleep(500 + random(1500));
				} catch (InterruptedException e) {
				}
				return;
			}
		}

	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	private final Color color1 = new Color(255, 255, 255);

	private final Font font1 = new Font("Arial", 0, 9);
	private final Font font2 = new Font("kootenay", 0, 11);

	private final Image img3 = getImage("http://i.imgur.com/qQNRUSi.png");

	long startTime = 0;
	public int startXp;
	public int totalXP = 0;
	String Status;
	String training;
	int lvlStart;
	int lvlStart1;
	int lvlStart2;
	int lvlStart3;
	int lvlStart4;
	int lvlStart5;
	// int xpStart = 0;
	int xpGained;

	int xpStart = 0;
	int xpStart1 = 0;
	int xpStart2 = 0;
	int xpStart3 = 0;
	int xpStart4 = 0;
	int xpStart5 = 0;
	// int xpStart = 0;

	int whatlvlXPSTART = 0;
	public int whatskill;
	int lvlCurrent;

	@Override
	public void onPaint(Graphics g1) {

		long runTime = System.currentTimeMillis() - startTime;

		int xpH = (int) ((double) xpGained / runTime * 3600000);

		// int logscutted2 = logscutted + Inventory.getCount(LogID, false);
		// int cutted = (int) ((double) logscutted2 / runTime * 3600000);
		Graphics2D g = (Graphics2D) g1;
		g.drawImage(img3, 10, 335, null);
		g.setFont(font2);
		g.setColor(color1);
		if (Status == null) {
			Status = "Waiting...";
		}
		if (whatskill == 1) {
			lvlCurrent = (client.getSkills().getLevel(Skill.ATTACK));
			// whatskill = 1;
			xpGained = (client.getSkills().getExperience(Skill.ATTACK) - whatlvlXPSTART);
			whatlvlXPSTART = xpStart1;
			training = "Attack";
		}
		if (whatskill == 2) {
			lvlCurrent = (client.getSkills().getLevel(Skill.STRENGTH));
			xpGained = (client.getSkills().getExperience(Skill.STRENGTH) - whatlvlXPSTART);
			// whatskill = 2;
			whatlvlXPSTART = xpStart2;
			training = "Strength";
		}
		if (whatskill == 3) {
			lvlCurrent = (client.getSkills().getLevel(Skill.DEFENCE));
			xpGained = (client.getSkills().getExperience(Skill.DEFENCE) - whatlvlXPSTART);
			// whatskill = 3;
			whatlvlXPSTART = xpStart3;
			training = "Defence";
		}
		if (whatskill == 4) {
			lvlCurrent = (client.getSkills().getLevel(Skill.RANGED));
			xpGained = (client.getSkills().getExperience(Skill.RANGED) - whatlvlXPSTART);
			// whatskill = 4;
			whatlvlXPSTART = xpStart4;
			training = "Ranged";
		}
		if (whatskill == 5) {
			lvlCurrent = (client.getSkills().getLevel(Skill.MAGIC));
			xpGained = (client.getSkills().getExperience(Skill.MAGIC) - whatlvlXPSTART);
			// whatskill = 5;
			whatlvlXPSTART = xpStart5;
			training = "Magic";
		}
		int lvlGain = (lvlCurrent - lvlStart);

		if (lvlGain < 0) {
			lvlGain = 0;
		}
		if (training == null) {
			training = "Waiting...";
		}
		if (whatlvlXPSTART < 0) {
			whatlvlXPSTART = 0;
		}
		if (xpH < 0) {
			xpH = 0;
		}
		g.drawString(" " + Status, 188, 406);
		g.drawString("" + timeToString((int) runTime), 209, 430);
		g.drawString(" " + lvlCurrent + "(+" + lvlGain + ")", 383, 430);
		g.drawString(" " + training, 353, 453);
		g.drawString(" " + xpGained, 363, 406);
		g.drawString(" " + xpH, 209, 453);
		drawProgressBar(g, 2, 320, 487, 17, Color.BLACK, Color.decode("0xCC9933"), 150, 50);
		trail.add(client.getMousePosition());
		trail.draw(g);
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

	/*
	 * private Path MrCrabWalker = new Path(new Tile(2731, 3485), new Tile(2736,
	 * 3491), new Tile(2742, 3504), new Tile(2742, 3518), new Tile(2741, 3526),
	 * new Tile(2741, 3534), new Tile(2731, 3541), new Tile(2727, 3542), new
	 * Tile(2718, 3544), new Tile(2711, 3544), new Tile(2698, 3543), new
	 * Tile(2690, 3546), new Tile(2679, 3548), new Tile(2670, 3559), new
	 * Tile(2660, 3571), new Tile(2654, 3584), new Tile(2654, 3590), new
	 * Tile(2654, 3600), new Tile(2654, 3611), new Tile(2663, 3613), new
	 * Tile(2663, 3620), new Tile(2665, 3630), new Tile(2674, 3639), new
	 * Tile(2688, 3640), new Tile(2699, 3647), new Tile(2698, 3655), new
	 * Tile(2698, 3660), new Tile(2700, 3671), new Tile(2700, 3677), new
	 * Tile(2706, 3691), new Tile(2705, 3700), new Tile(2705, 3705), new
	 * Tile(2705, 3713), new Tile(2705, 3713));
	 * 
	 * }
	 */
}