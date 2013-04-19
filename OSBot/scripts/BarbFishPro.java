
import java.awt.Graphics;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
import org.osbot.script.rs2.model.GroundItem;
import org.osbot.script.rs2.model.NPC;
import org.osbot.script.rs2.model.Player;
import org.osbot.script.rs2.ui.Tab;

/**
 * @author 'MobbyGFX TODO: Add Antiban, add FishCaught exp / h;
 */
@ScriptManifest(name = "Barbarian Fisher Pro", author = "MobbyGFX", version = 1.0, info = "Fishes trout and salmon in barb village")
public class BarbFishPro extends Script {

	Filter<NPC> npcFilter = new Filter<NPC>() {
		public boolean accept(NPC npc) {
			return npc.getId() == 328 || npc.getName().equals("Fishing spot");
		}
	};
	public NPC fishingSpot;
	public int[] TOOLS = { 309, 314 };
	private long startTime;

	public void onStart() {
		startTime = System.currentTimeMillis();
		log("Welcome to BarbFishPro, by MobbyGFX");
	}

	public int onLoop() throws InterruptedException {
		if (client.getInventory().isFull())
			dropFish();
		catchFish();
		checkForRod();
		dropFish();
		return 50;
	}

	public void dropFish() throws InterruptedException {
		if (client.getInventory().isFull()) {
			if (currentTab() != Tab.INVENTORY)
				openTab(Tab.INVENTORY);
			client.getInventory().dropAllExcept(TOOLS);
			sleep(500);
		}
	}

	public void catchFish() throws InterruptedException {
		Player player = client.getMyPlayer();
		fishingSpot = closestNPC(npcFilter);
		if (fishingSpot == null) {
			log("Null exception.");
			return;
		}
		if (!client.getInventory().contains(TOOLS[0])) {
			checkForRod();
			return;
		}
		if (!player.isAnimating() && !player.isUnderAttack()) {
			selectEntityOption(fishingSpot, "Lure");
			client.moveCameraToEntity(fishingSpot);
			sleep(500 + random(1000, 2000));
		}
		moveMouseOutsideScreen();
	}

	public void checkForRod() throws InterruptedException {
		if (!client.getInventory().contains(TOOLS[0])) {
			GroundItem item = closestGroundItem(TOOLS[0]);
			if (item.getName().equals("Fly fishing rod")) {
				item.interact("Take");
			}
		}
	}

	public void onPaint(Graphics g) {
		int runTime = (int) (System.currentTimeMillis() - startTime);
		g.drawString("Run time: " + timeToString(runTime), 200, 50);
	}

	public void onMessage(String message) throws InterruptedException {
		if (message.contains("You gain a fishing level")) {

		}
	}

	public interface Filter<T> {
		public boolean accept(T element);
	}

	private NPC closestNPC(Filter<NPC> f) {
		NPC npc = null;
		for (NPC n : client.getLocalNPCs())
			if (n != null)
				if ((npc == null || distance(n) < distance(npc) && f.accept(n)))
					npc = n;
		return npc;
	}

	public static String timeToString(int time) {
		int seconds = (int) (time / 1000) % 60;
		int minutes = (int) ((time / (1000 * 60)) % 60);
		int hours = (int) ((time / (1000 * 60 * 60)) % 24);
		return (hours < 10 ? "0" + hours : hours) + ":"
				+ (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds);
	}

}
