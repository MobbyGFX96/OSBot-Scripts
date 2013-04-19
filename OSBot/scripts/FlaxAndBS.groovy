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
     import org.osbot.script.rs2.model.*
     import javax.swing.*;
     
     import java.awt.*;
     
     import java.awt.event.*;
     
     
     @ScriptManifest(name = "FlaxAndBS", author = "Wood1master7", version = 2.0D, info="From nothing to flax. From flax to string From string to MILLS.")
     class FlaxAndBS extends Script {
     
     
     
     
         int bankId = 25808;
         int flax = 1780;
         int bs = 1777;
         int flaxObj = 3634;
         boolean deur = true;
         def starttime = null;
         def state = State.PICKING
         int picked = 0;
         int coordx = 2742;
         int coordy = 3444;
         int path = 0;
         int path2x = 2724;
         int path2y = 3459;
         int step = 0;
         int mstep = 0;
         int pstep = 0;
         boolean manim = false;
         int bankstep = 0;
         int coord1x = 241;
         int coord1y = 90;
         int randomNuber = 33;
         int randomNumber = 33;
         int craftingxp;
         int whentoworry = 0;
         int whentoworry2 = 0;
         int sentmessag = 0;
     int flaxX = 0;
     int flaxY = 0;
      String stepmess = "Not Available";
         boolean flaxing = false;
         boolean stringing = false;
         boolean both = false;
         def Newantiban = null;
         boolean Antibans = true
         Position ladderp = new Position(2714, 3470, 0)
         Position ladderp2 = new Position(2717, 3472, 0)
         Position doorp = new Position(2716, 3472, 0)
         Position oldpos
     
     
         boolean opened = false;
         private final Color color1 = new Color(0.1f, 0.1f, 0.1f, 0.7f);
         private final Color color2 = new Color(255, 0, 0);
     
     
         private final Font font1 = new Font("Calisto MT", 0, 16);
         private final Font font2 = new Font("Calisto MT", 0, 11);
     
         void onStart() {
             randomNumber = random(500)
             Newantiban = System.currentTimeMillis() + random(10000, 20000);
           
             def th = Thread.start {
                 antiban()
             }
             coordx = 2742 + random(2)
             coordy = 3444 + random(2)
             starttime = System.currentTimeMillis();
         if(client.getInventory().contains(1779)){
                 state = State.TOSTRIN

             } else if(client.getInventory().isFull()){
                 state = State.TOBANK
             }
         }
       
         void onExit() {
             log("Thank you! -for using Flax To Bowstring! ")
                     Antibans = false
             return
         }
       
       
         enum State {
             PICKING, TOBANK, BANKING, TOSTRIN,
         }
       
       
         int onLoop() {
             switch (state){
                 case State.PICKING:
                     return onPick()
                 case State.TOSTRIN:
                     return toStrin()
                 case State.TOBANK:
                     return toBank()
                 case State.BANKING:
                     return bank()
               
             }
         }
         int onPick(){
     randomNumber = random(500)
             if (client.getInventory().isFull()) {
             pstep = 0
      state = State.TOSTRIN
     return 250
             }
     if (client.getMyPlayer().getY() < 3435) {
             walkExact(new Position(2731 + random(5), 3441 + random(4), 0))
      sleep(699)
       selectEntityOption(closestObject(flaxObj), "Pick")
     return 225
     }
                 selectEntityOption(closestObject(flaxObj), "Pick")
      if (client.getMyPlayer().getY() < 3455) {
      return gRandom(1050, 1658)
      } else {
      return gRandom(333, 768)
      }
         }
       
       
         int toBank() {
     
    stepmess = "Not Available"

         step = 0;
         mstep = 0;
         pstep = 0;
         bankstep = 0;
         if(client.getMyPlayer().getZ() == 1){
                 selectEntityOption(closestObject(25939), "Climb-down", "Ladder")
                 return 7500 + random(500)
             } else if(closestObject(25819).getPosition() == doorp){
                     log("Door is Closed")
                     selectEntityOption(closestObject(25819), "Open", "Door")
                     return 3500 + gRandom(250, 500)
             }
    if (client.getMyPlayer().getX() > 2712 && client.getMyPlayer().getX() < 2716 && client.getMyPlayer().getY() > 3469 && client.getMyPlayer().getY() < 3473) {
     walkExact(new Position(2723 + random(4), 3475 + random(5), 0))
     return gRandom(1750, 2250)
    }
    if (client.getMyPlayer().getX() > 2722 && client.getMyPlayer().getX() < 2728 && client.getMyPlayer().getY() > 3474 && client.getMyPlayer().getY() < 3481) {
     walkExact(new Position(2723 + random(6), 3490 + random(3), 0))
     return gRandom(2854, 3207)
    }
    if (client.getBank().isOpen()) {
                     state = State.BANKING
    return 250
    }
    selectEntityOption(closestObject(bankId), "Bank", "Bank booth")
    return gRandom(1006, 1876)
         }
       
         int bank() {
      if(!client.getInventory().isEmpty()) {
                 client.getBank().depositAll()
      return 1000 + gRandom(0, 250)
      } else {
                 state = State.PICKING
      return random(300)
      }
         }
      
     
     int toStrin() {
     if (client.getMyPlayer().getZ() == 0 && step > 3 && client.getInventory().contains(1779)) {
     step = 0
     stepmess = "Climbing Ladder"
     log("retrying the climb...")
     return 250 + random(100)
         } else if(!client.getMyPlayer().isAnimating()){
     sleep(500)
     if(!client.getMyPlayer().isAnimating()){
     sleep(250)
     if(!client.getMyPlayer().isAnimating()){
     sleep(100)
     if(!client.getMyPlayer().isAnimating()){
     manim = false
     }
     }
     }
         }
         if(client.getMyPlayer().isAnimating()){
     manim = true
         }
     if(client.getMyPlayer().getX() == 2716 && client.getMyPlayer().getY() == 3471 && client.getMyPlayer().getZ() == 0) {
     walkExact(new Position(client.getMyPlayer().getX(), client.getMyPlayer().getY() + 1, 0))
         step = 0
                 return 433 + gRandom(0, 100)
     } else if(client.getMyPlayer().getX() <= 2711 && client.getMyPlayer().getZ() == 0) {
     walkExact(new Position(2719, 3469, 0))
     
     stepmess = "Walking to location"
         step = 0
                 return 1000 + gRandom(250, 500)
     
     } else if (client.getMyPlayer().getX() > 2711 && client.getMyPlayer().getX() < 2716 && client.getMyPlayer().getY() > 3469 && client.getMyPlayer().getY() < 3473 && client.getMyPlayer().getZ() == 0 && step < 3) {
     step = 3
     log("Skipped to step 3!")
     stepmess = "Climbing Ladder"
     } else if (step == 0) {
     stepmess = "Walking to location"
     if(client.getMyPlayer().getPosition() == ladderp){
                 step = 1
                 return 1000 + random(500)
             }else if(closestObject(25819).getPosition() == doorp){
                     log("Door is Closed")
                   
                 if (client.getMyPlayer().getPosition() != ladderp2) {
                 walkExact(ladderp2)
                 return 1000 + random(500)
                 } else {
                     selectEntityOption(closestObject(25819), "Open", "Door")
                     return(100)
                 }
             }else{
               
                 if (client.getMyPlayer().getZ() != 1) {
               
                 log("Door is open... how lucky master?")
     mstep = 0
                 if (oldpos == client.getMyPlayer().getPosition()) {
                 step = 1
                 return 500
                 }
                 walkExact(ladderp)
                 if (oldpos == client.getMyPlayer().getPosition()) {
                 walkExact(new Position(client.getMyPlayer().getX(), client.getMyPlayer().getY() + 1, 0))
                 return 250 + random(100)
             }
                 return 1000 + random(500)
             } else {
             step = 4
             }
             }
     
     } else if(step == 1){
     stepmess = "Open Door"
      if (oldpos == client.getMyPlayer().getPosition()) {
     walkExact(ladderp2)
     step = 0
     return 1000 + gRandom(500)
     }
     if(client.getMyPlayer().getX() > 2715 && client.getMyPlayer().getX() < 2721 && client.getMyPlayer().getY() > 3468 && client.getMyPlayer().getY() < 3473 && closestObject(25819).getPosition() == doorp){
     selectEntityOption(closestObject(25819), "Open", "Door")
     return 650 + gRandom(250, 500)
     } else if(!(client.getMyPlayer().getX() > 2710 && client.getMyPlayer().getX() < 2716 && client.getMyPlayer().getY() > 3469 && client.getMyPlayer().getY() < 3475)){
        step = 0
     return 600 + gRandom(0, 500)
     }   
         if(client.getMyPlayer().getZ() == 1){
                 step = 4
                 return(1000)
             }
           
             else if(client.getMyPlayer().getZ() != 1){
                 if (!(client.getMyPlayer().getX() > 2710 && client.getMyPlayer().getX() < 2716 && client.getMyPlayer().getY() > 3469 && client.getMyPlayer().getY() < 3473)) {
                 step = 0
                 return 4500 + random(500)
                 }
                 selectEntityOption(closestObject(25938), "Climb-up", "Ladder")
                 log("climbing ladder")
                 return 1500 + random(500)
             }
     step = 0
     return 500
             } else if(step == 2){
          step = 3
             } else if(step == 3){
     
    stepmess = "Climbing Ladder"
                 selectEntityOption(closestObject(25938), "Climb-up", "Ladder")
             step = 4
                 return 3423 + gRandom(50, 150)
             } else if(step == 4){
     
    stepmess = "Spinning Flax"
         if(client.getInventory().contains(1779)){
                 if(!client.getMyPlayer().isAnimating()){
                 if (client.getMyPlayer().getX() == 0) {
                 step = 0
                 mstep = 4
                 return 250
                 }
                 if (mstep == 3) {
                 if (!client.getMyPlayer().isAnimating()) {
                 mstep = 0
                 return 250 + gRandom(0, 250)
                 }
                 } else if (mstep == 0 && manim == false) {
                     selectEntityOption(closestObject(25824), "Spin")
                     log("Going to open the spin interface")
                     mstep = 1
                     return 3250 + gRandom(50, 300)
                 } else if (mstep == 1) {
                     selectInterfaceOption(459, 95, "Make X")
                     log("Making X amount...")
                     mstep = 2
                     return(1850 + gRandom(300, 500))
                 } else if (mstep == 2) {
                     if (randomNumber < 100) {
                     client.typeString("66")
                     log("input 66")
                     } else if (randomNumber < 200) {
                     client.typeString("55")
                     log("input 55")
                     } else if (randomNumber < 300) {
                     client.typeString("44")
                     log("input 44")
                     } else if (randomNumber < 400) {
                     client.typeString("33")
                     log("input 33")
                     } else {
                     client.typeString("99")
                     log("input 99")
                     }
                     sleep(500)
                     if (!client.getMyPlayer().isAnimating()) {
                     mstep = 0
                     return 250
                     }
                     mstep = 3
                     return(5000 + gRandom(100, 400))
                 } else if (mstep == 4) {
                 step = 0
                 return 760
                 }
                 if (client.getMyPlayer().isAnimating()) {
                     return(5000 + gRandom(250, 800))
                     }
                 }      
                 else if(client.getMyPlayer().isAnimating()){
                     randomNumber = random(500)
                     log("still animating, spinning")
                     manim = true
                     return 5600 + gRandom(33, 222)
                 }
         } else {
     
    stepmess = "Banking..."
     log("done... going to bank")
     mstep = 0
     
                             if (currentTab() != Tab.INVENTORY){
                                 openTab(Tab.INVENTORY);
                             }
         if(client.getMyPlayer().getZ() == 1){
                 selectEntityOption(closestObject(25939), "Climb-down", "Ladder")
                 return 7500 + random(500)
             } else if(closestObject(25819).getPosition() == doorp){
                     log("Door is Closed")
                     selectEntityOption(closestObject(25819), "Open", "Door")
                     return 3500 + gRandom(250, 500)
             } else if(client.getMyPlayer().getZ() != 1){
                 state = State.TOBANK
                 return 1000
             }
         }
     
         } else if (step == 5) {
     log("done... going to bank")
     
                             if (currentTab() != Tab.INVENTORY){
                                 openTab(Tab.INVENTORY);
                             }
         if(client.getMyPlayer().getZ() == 1){
                 selectEntityOption(closestObject(25939), "Climb-down", "Ladder")
                 return 7500 + random(500)
             } else if(closestObject(25819).getPosition() == doorp){
                     log("Door is Closed")
                     selectEntityOption(closestObject(25819), "Open", "Door")
                     return 3500 + gRandom(250, 500)
             } else if(client.getMyPlayer().getZ() != 1){
                 state = State.TOBANK
                 return 1000
             }
         }
     }
       
         void onPaint(Graphics g) {
             g.setColor(color1);
             g.fillRect(1, 51, 169, 113);
             g.setFont(font1);
             g.setColor(color2);
             g.drawString("Flax and BS V2.0", 7, 76);
             g.setFont(font2);
             g.drawString("Time: " + format(System.currentTimeMillis() - starttime), 7, 130);
             g.drawString("Flax Picked: (${picked})", 7, 100);
             g.drawString("Idel: ${state}", 7, 115);
             g.drawString("Crafting Xp Earned: ${craftingxp}", 7, 145);
             g.drawString("TOSTRIN Step: ${stepmess}", 7, 160);
         }
           
     
     
     
       
         String format(final long time) {
             final StringBuilder t = new StringBuilder();
             final long total_secs = time / 1000;
             final long total_mins = total_secs / 60;
             final long total_hrs = total_mins / 60;
             final int secs = (int) total_secs % 60;
             final int mins = (int) total_mins % 60;
             final int hrs = (int) total_hrs % 24;
         if (secs == 15 || secs == 30 || secs == 45 || secs == 60) {
         oldpos = client.getMyPlayer().getPosition()
         }
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
     
     
             if (message == "You pick some flax.") {
                 log("Picked a flax!")
             whentoworry = 0
                 picked ++
     } else
             if (message == "You have run out of flax.") {
         craftingxp = 15 * picked;
                             if (currentTab() != Tab.INVENTORY){
                                 openTab(Tab.INVENTORY);
                             }
     log("Out of flax. Updated craft xp and inv opened!")
     state = State.TOBANK
     manim = false
     } else if (message == "You'll need flax to do that." || message == "You'll need flax to make that.") {
     stage = 5
     } else if (message == "I can't reach that!" && (stage == 3 || stage == 4)) {
     stage = 0
     } else if (message == "You can't carry anymore flax") {
     state = State.TOSTRIN
     }
     }
     
     void antiban(){
             while(Antibans){
                 if (state == State.TOSTRIN && step >= 4 && Newantiban < System.currentTimeMillis()){
               
                     int Anti = random(0 , 3)
                     switch(Anti){
                         case 0:
                             log("Antiban: Move mouse")
                             moveMouseOutsideScreen()
                             Newantiban = System.currentTimeMillis() + random(30000, 70000)
                         break;
                         case 1:
                             log("Antiban: Move camera")
                             client.moveCameraToEntity(currentRock)
                             Newantiban = System.currentTimeMillis() + random(20000, 75000)
                         break;
                         case 2:
                             log("Antiban: Open Skills tab")
                             if (currentTab() != Tab.SKILLS){
                                 openTab(Tab.SKILLS);
                             }
        sleep(250 + gRandom(250, 500))
                             if (currentTab() == Tab.SKILLS){
                                 client.moveMouse(new PreciseDestination(new Point(random(613, 666), random(335, 360)), false), false);
         sleep(100)
        client.moveMouse(hover.getMouseDestination(), false);
                             }
                           
                             Newantiban = System.currentTimeMillis() + random(35000, 90000)
                         break;
                         case 3:
                             log("Antiban: Open Friends tab")
                             if (currentTab() != Tab.FRIENDS){
                                 openTab(Tab.FRIENDS);
                             }
                             Newantiban = System.currentTimeMillis() + random(40000, 80000)
                         break;
                     }
                 } else if (state != State.TOBANK && state != State.BANKING && Newantiban < System.currentTimeMillis()) {
                     int Anti = random(0 , 1)
                     switch(Anti){
                         case 0:
                             log("Antiban: Move mouse")
                             moveMouseOutsideScreen()
                             Newantiban = System.currentTimeMillis() + random(80000, 190000)
                         break;
                         case 1:
                             log("Antiban: Move camera")
                             client.moveCameraToEntity(currentRock)
                             Newantiban = System.currentTimeMillis() + random(70000, 200000)
                         break;
      }
      }
             Thread.sleep(1000)
             }
         }
     
     
     
     }