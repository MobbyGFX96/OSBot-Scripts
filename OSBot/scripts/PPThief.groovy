import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
 
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
 
@ScriptManifest(name = "PPThief", author="PengPhin", version = 1.0D, info = "Only Supports upto Master Farmer")
class PPThief extends Script {
 
	enum State {
		INPUT, IDLE, ROBBING,
	}
 
	Npc[] Npcs = [
			new Npc("People level 1", [ 1, 2, 3, 4, 5 ] as int[]), // Add new NPCs here
			new Npc("Farmer	level 10", [1757] as int[]),
			new Npc("Warrior Women	Level 25",  15  as int[]),
			new Npc("Rogues	Level 32",  187 as int[]),
			new Npc("Cave Goblins Level 36", [ 1822, 1823, 1824, 1825 ] as int[]),
			new Npc("Master Farmer Level 38", [ 2234, 2235] as int[]),
				] as Npc[];
 
	State state = State.INPUT;
	Npc Npc = null;
 
	void onStart() {
		if (state == State.INPUT) {
			showInput();
		}
	}
 
	int onLoop() {
		switch(state) {
			case State.ROBBING:
				return steal();
		}
		return 1000 + gRandom(400, 800);
	}
 
	int steal() {
		def npc = closestNPC(Npc.getIds());
		if (npc != null) {
			if (selectEntityOption(npc, "Pickpocket")) {
				return 2000 + random(200, 500);
			}
		}
		return 1000 + random(200, 1000);
	}
 
	void onPaint(Graphics g) {
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.7f));
		g.fillRect(3, 308, 513, 30);
 
		g.setColor(Color.GREEN);
		g.drawString("PengPhins Robber | Npc: ${Npc.getName()} | State: ${state}", 10, 327);
	}
 
	void showInput() {
		JPanel panel = new JPanel();
		String[] Npcs = new String[this.Npcs.length];
		for(int i = 0; i < Npcs.length; i++) {
			Npcs[i] = this.Npcs[i].getName();
		}
		JComboBox combo = new JComboBox(Npcs);
		panel.add(combo);
		int ret = JOptionPane.showConfirmDialog(null, panel, "Select Npc", JOptionPane.OK_CANCEL_OPTION);
		if (ret == JOptionPane.OK_OPTION) {
			Npc = this.Npcs[combo.getSelectedIndex()];
			state = State.ROBBING;
		} else {
			showInput();
			//TODO: Add Banking, Food Support
		}
	}
 
 
	class Npc {
 
		private final String name;
		private final int[] ids;
 
		public Npc(String name, int[] ids) {
			this.name = name;
			this.ids = ids;
		}
 
		public String getName() {
			return name;
		}
 
		public int[] getIds() {
			return ids;
		}
 
 
	}
 
}