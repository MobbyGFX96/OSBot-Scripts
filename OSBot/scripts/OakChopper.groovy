import org.osbot.script.rs2.skill.Skill
import java.awt.*
import org.osbot.script.Script
import org.osbot.script.ScriptManifest
import org.osbot.script.mouse.*
import org.osbot.script.rs2.*
import org.osbot.script.rs2.map.Position
import org.osbot.script.rs2.model.GroundItem
import org.osbot.script.rs2.model.Item
import org.osbot.script.rs2.model.PrimaryObject
import org.osbot.script.rs2.model.RS2Object
import org.osbot.script.rs2.ui.*
import javax.imageio.ImageIO
import java.io.IOException
import java.net.URL

import java.rmi.server.LogStream

@ScriptManifest(name = "OakChopper", author = "Sense", version = 1.1D, info="OakChopper banks/drops.")
class OakChopper extends Script {

    private final Color color = new Color(0, 0, 0)
    private final Color rectColor = new Color(0.6f, 0.7f, 0.4f, 0.7f)
    private final Font font = new Font("Georgia", 0, 16)

    Position bank = new Position(3092, 3242, 0)
    def wc = new RectangleDestination(new Rectangle(677, 367, 50, 23))




    int bankId = 2213
    int[] axeIds = [1352, 1360, 1350, 1354, 1362, 1358, 1356]
    int treeId = 1281
    int[] nests = [5071, 5072, 5073, 5074, 5075, 5070, 7413, 5076]

    enum State {
        IDLE, CHOPPING, WALK_TO_BANK, PIN, BANKING, DROPPING, WALK_TO_OAKS, CLOSE_BANK
    }
    def nearestevilTree = null
    def evilTree = 777
    def oaks = 0
    def state = State.IDLE
    def currentTree = !null
	def currentBank = !null
    def starttime = null;

    void onStart() {

        starttime = System.currentTimeMillis()

        if (client.getInventory().isFull())
            state = State.WALK_TO_BANK
    }

    int onLoop() {

        switch (state) {
            case State.IDLE:
                return onIdle()
            case State.CHOPPING:
                return onChopping()
            case State.WALK_TO_BANK:
                return walkToBank()
            case State.BANKING:
				return bank()
            case State.CLOSE_BANK:
                return closeBank()
            case State.WALK_TO_OAKS:
                return walkToOaks()
            case State.DROPPING:
				return drop()
        }
        return 300 + random(500)
    }

    int onIdle() {
        if (client.getMyPlayer().isAnimating() == false)
		{
            state = State.WALK_TO_OAKS
        }
        else if (client.getBank().isOpen()) {
            client.getBank().close()
            state = State.WALK_TO_OAKS
        }
        else if (client.getInventory().isFull())
		{
            state = State.WALK_TO_BANK

		}
        return 500 + gRandom(100, 400)
    }

    int onChopping() {
        nearestevilTree = closestObject(evilTree)

        checkIfStuck()

        if (client.getInventory().isFull()) {
            state = State.WALK_TO_BANK
            client.moveCameraToEntity(closestObject(bankId))
            return 300 + gRandom(100, 400)
        }
        if(random(50) == 0 && currentTree != null && currentTree.exists())
		{
            antiban()
		}
		else if(random(40) == 0 && currentTree != null && currentTree.exists())
		{
			moveMouseOutsideScreen()
		}
        if (currentTree == null) {
            state = State.IDLE
            log("Chopped down Oak!")
            return 500 + gRandom(1000, 500)
        }
        if (!currentTree.exists()) {
            state = State.IDLE
            currentTree = null
            log("Chopped down Oak!")
        }
        if (nearestevilTree != null) {
            log("Found Evil tree. Banking logs.")
            state = State.WALK_TO_BANK
        }
        return 500 + gRandom(100, 500)
    }

    int walkToBank()
	{
		if(client.getBank().isOpen())
		{
			 state = State.BANKING
			 return 100
		 }
		 else if(!client.getBank().isOpen())
		 {
		 
			currentBank = closestObject(bankId)
			if (currentBank != null)
			{
				selectEntityOption(closestObject(bankId), "Bank", "Bank booth")
			}
			else
			{
				state = State.DROPPING
				currentBank = null
			}
			return 1000
		 }
    }
    int bank() {
        selectInventoryOption(client.inventory.getSlotForId(1521), "Store All")
        state = State.CLOSE_BANK
        return 500 + gRandom(200, 300)
    }
    int closeBank() {
        client.getBank().close()
        state = State.WALK_TO_OAKS
		currentBank = null
        return 500 + gRandom(200, 300)
    }

    int drop()
	{
			if (client.getInventory().isFull() && !client.getMyPlayer().isAnimating())
			{
					if (currentTab() != Tab.INVENTORY)
					{
						openTab(Tab.INVENTORY)
					}
					log("No bank near so dropping inventory.")
					client.getInventory().dropAllExcept(axeIds)
			}
        state = State.WALK_TO_OAKS
        return 500 + gRandom(200, 300)
    }
    int antiban()
	{
        if (currentTab() != Tab.SKILLS){
            openTab(Tab.SKILLS)
            sleep(500+ gRandom(300, 50))
            client.moveMouseTo(wc, false, false, false)
            sleep(6000 + gRandom(300, 50))
            openTab(Tab.INVENTORY)
        }
        return 500 + gRandom(200, 300)
    }

    int checkIfStuck()
	{
		if (client.getMyPlayer().isAnimating() == false)
		{
			//log("Detected stuck script. Fixing.")
			state = State.WALK_TO_OAKS
		}
        return 1000
    }

    int walkToOaks() {
        log("Walking to Oak.")
        currentTree = closestObject(treeId)
        if (currentTree != null) {
            log("Found Oak tree.")
            selectEntityOption(currentTree, "Chop down", "Oak")
            client.moveCameraToEntity(currentTree)
            sleep(3000 + gRandom(500, 200))
            checkIfStuck()

        }
        return 200 + gRandom(800, 300)
    }

    void onPaint(Graphics g)
	{
        g.setFont(font)
        g.setColor(color)
        g.drawString("Logs cut: ${oaks}", 30, 260)
       g.drawString("Time Running: " + format(System.currentTimeMillis() - starttime), 30, 245)
    }


    String format(final long time) {
        final StringBuilder t = new StringBuilder();
        final long total_secs = time / 1000;
        final long total_mins = total_secs / 60;
        final long total_hrs = total_mins / 60;
        final int secs = (int) total_secs % 60;
        final int mins = (int) total_mins % 60;
        final int hrs = (int) total_hrs % 24;
        if (hrs < 10) {
            t.append("0");
        }
        t.append(hrs);
        t.append(":");
        if (mins < 10) {
            t.append("0");
        }
        t.append(mins);
        t.append(":");
        if (secs < 10) {
            t.append("0");
        }
        else if (secs < 0){
            t.append("0")
        }
        else{
            t.append(secs);
        }
        return t.toString();
    }
    void onMessage(String message) {
        if (message == "You swing your axe at the tree.")
		{
            log("Chopping down tree!")
            state = State.CHOPPING
        }
		else if (message == "You get some oak logs.")
		{
            state = State.IDLE
            oaks++
        }
		else if (message == "Your inventory is too full to hold any more logs.")
		{
            log("Banking!")
            state = State.WALK_TO_BANK
        }
		else if (message == "A bird's nest falls out of the tree.")
		{
            //selectEntityOption(closestGroundItem(nests), "Pick up", "Bird's nest")
			closestGroundItem(nests).interact("Take")
            log("Found Bird's Nest!")
            //log("Unfortunately, they aren't supported yet.")
            sleep(1000 + gRandom(300, 50))
            state = State.IDLE

        }
		else if (message == "You do not have an axe which you have the woodcutting level to use.")
		{
            log("[PAUSED]: Get an axe before starting a wc script.")
            sleep(60000)
            log("[RESUMED]: Assuming you already gathered an axe.")
        }
    }
}
