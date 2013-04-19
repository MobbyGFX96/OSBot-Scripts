import java.awt.Graphics;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.utility.Area;

@ScriptManifest(name = "Ultimate Rock Crabs", author = "MobbyGFX", version = 1.0D, info = "Kills crabs on the right side.")
public class UltimateRockCrabs extends Script {

	/**
	 * WalkBack position = 2702, 3721 walkTo position = 2702 3693 Rock idle Id =
	 * 1266 && 1268 Rock active id = 1265 && 1267 Rectangular Area of rock crabs
	 * = Y = 2691, 3726, 0, X = 2720, 3712, 0
	 */

	public boolean RESET_CRABS = true;
	public boolean WALK_TO_CRABS;
	public int[] IDLE_CRABS = { 1266, 1268 };
	public ScriptState state = ScriptState.IDLE;
	public Area localCrabs = new Area(new Position[] {
			new Position(2692, 3726, 0), new Position(2692, 3712, 0),
			new Position(2718, 3712, 0), new Position(2718, 3726, 0) });

	public void onStart() {
		log("Welcome to Ultimate Rock crabs by MobbyGFX");
	}
	
	public int onLoop() throws InterruptedException {
		
		return 50;
	}

	public void onPaint(Graphics g) {
		g.drawString(localCrabs.contains(client.getMyPlayer()) ? "In area"
				: "Not in area", 50, 50);
	}

	public static enum ScriptState {
		IDLE, ATTACKING, RESETTING, WALKING_BACK, ERROR;
	}

}
