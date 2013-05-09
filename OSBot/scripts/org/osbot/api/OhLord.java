package org.osbot.api;

import java.awt.Graphics2D;

import org.osbot.script.ScriptManifest;
import org.osbot.script.rs2.model.NPC;
import org.osbot.script.rs2.model.Player;

@ScriptManifest(author = "SolaceCoding", info = "Testing purposes", name = "OhLord", version = 1.0D)
public class OhLord extends StrategyManager {

	public MPaint paint;
	
	public boolean initiate() {
		log("HELLLO");
		submitStrategy(new Strategy[] { new Attack() });
		paint = new MPaint();
		return true;
	}

	public void close() {
		log("BYEEE");
	}

	public void onRepaint(Graphics2D g) {
		paint.draw(g);
	}

	public class Attack implements Strategy {

		public Filter<NPC> experiment = new Filter<NPC>() {
			public boolean accept(NPC npc) {
				return npc.getName().toLowerCase().equals("experiment")
						&& !npc.isUnderAttack()
						&& npc.getDefinition().getLevel() == 25;
			}
		};

		public NPC getBestExperiment() {
			return closestAttackableNPC(experiment);
		}

		private NPC closestAttackableNPC(Filter<NPC> f) {
			NPC npc = null;
			for (NPC n : client.getLocalNPCs())
				if (n != null)
					if (((npc == null || distance(n) < distance(npc)) && f
							.accept(n)))
						npc = n;
			return npc;
		}

		public boolean canActivate() {
			Player player = client.getMyPlayer();
			return getBestExperiment() != null && player.getAnimation() == -1
					&& !player.isUnderAttack();
		}

		public void execute() throws InterruptedException {
			final NPC mob = getBestExperiment();
			final Player player = client.getMyPlayer();
			if (mob != null) {
				if (mob.getPosition().distance(player.getPosition()) > 7
						&& !player.isMoving()) {
					if (!player.isUnderAttack()) {
						if (walk(mob.getPosition())) {
							sleep(random(2000, 3000));
						}
					}
				} else {
					if (!mob.isUnderAttack() && !player.isUnderAttack()
							&& !player.isMoving()) {
						client.moveCameraToEntity(mob);
						if (mob.interact("Attack")) {
							sleep(random(2000, 3000));
						}
					}
				}
			} else {
				sleep(random(3000, 5000));
			}
		}

	}

	public interface Filter<T> {
		public boolean accept(T element);
	}

}
