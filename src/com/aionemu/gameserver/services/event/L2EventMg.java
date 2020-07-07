package com.aionemu.gameserver.services.event;



import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.controllers.observer.ObserverType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.MuiService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Romanz
 */
public class L2EventMg {
        private static final Logger log = LoggerFactory.getLogger(L2EventMg.class);
        private static List<float[]> floatArray = new ArrayList<float[]>();
        private static int WORLD_ID = 600020000;
        private static int NPC_ID = 219158;
        private static int[] rewards = {
164002096, 164002097, 164002093, 164002094, 164002099, 164002100, 164002093, 164002094, 164002099, 164002100,
186000030, 186000031, 164000076, 164000134, 164000073, 186000143, 162000080, 161000003, 162000077, 162000078,
186000030, 186000031, 164000076, 164000134, 164000073, 186000143, 162000080, 160009014, 162000077, 162000078, 
186000030, 186000031, 164000076, 186000096, 186000147, 186000143, 162000080, 166000090, 166000100, 166000110,
186000030, 186000031, 164000076, 164000134, 164000073, 186000143, 162000080, 161000003, 162000077, 162000078,
164002096, 164002097, 164002093, 164002094, 164002099, 164002100, 164002093, 164002094, 164002099, 164002100, 
186000030, 186000031, 164000076, 164000134, 164000073, 186000143, 162000080, 160009016, 162000077, 162000078, 
186000030, 186000031, 164000076, 186000096, 186000147, 186000143, 162000080, 166000090, 166000100, 166000110,
164002096, 164002097, 164002093, 164002094, 164002099, 164002100, 164002093, 164002094, 164002099, 164002100, 
188051508, 188051509, 188051510, 188051396, 188051411, 188051412, 188051416, 188051389, 188051430, 188051395, 188051398,
164002096, 164002097, 164002093, 164002094, 164002099, 164002100, 164002093, 164002094, 164002099, 164002100}; //add item ids here separated by commas.
        private static Npc mainN;
		
        public static void ScheduleCron(){
             CronService.getInstance().schedule(new Runnable(){

                  @Override
                  public void run() {
                       startEvent(); //To change body of generated methods, choose Tools | Templates.
                  }
                  
             }, "0 0 20 ? * SAT");
             log.info("Pig Event start: 0 0 20 ? * SAT Duration: 30 min.");
        }
		
        public static void startEvent(){
                initCoordinates();
                
                World.getInstance().doOnAllPlayers(new Visitor<Player>(){

                        @Override
                        public void visit(Player object) {
                                PacketSendUtility.sendYellowMessageOnCenter(object, MuiService.getInstance().getMessage("PIG_EVENT_START"));
                        }
                });
                
                initPig();
                
                ThreadPoolManager.getInstance().schedule(new Runnable(){

                     @Override
                     public void run() {
                          endEvent(); //To change body of generated methods, choose Tools | Templates.
                     }
                }, 30 * 60 * 1000);
                
        }
        
        private static void initPig() {
                float[] coords = floatArray.get(Rnd.get(floatArray.size()));
                SpawnTemplate spawn = SpawnEngine.addNewSingleTimeSpawn(WORLD_ID, NPC_ID, coords[0], coords[1], coords[2], (byte) coords[3]);
                VisibleObject mainObject = SpawnEngine.spawnObject(spawn, 1);
                if(mainObject instanceof Npc) {
                      mainN = (Npc) mainObject;
                }
                ActionObserver observer = new ActionObserver(ObserverType.ATTACKED){

                        @Override
                        public void attacked(Creature creature) {
                                if(creature instanceof Player) {
                                        final Player player = (Player) creature;
                                        final int id = rewards[Rnd.get(rewards.length)];
                                        ItemService.addItem(player, id, 1);
                                        World.getInstance().doOnAllPlayers(new Visitor<Player>(){

                                                @Override
                                                public void visit(Player object) {
                                                        PacketSendUtility.sendYellowMessageOnCenter(object, player.getName() + MuiService.getInstance().getMessage("PIG_EVENT_REWARD",id));
                                                }
                                        });
                                }
                                mainN.getObserveController().removeObserver(this);
                                //mainN.setSpawn(null);
                                mainN.getController().onDelete();
                                initPig();
                        }
                };
                if(mainN != null) {
                        mainN.getObserveController().attach(observer);
                }
        }
        
        public static void endEvent(){
                World.getInstance().doOnAllPlayers(new Visitor<Player>(){

                        @Override
                        public void visit(Player object) {
                                PacketSendUtility.sendYellowMessageOnCenter(object, MuiService.getInstance().getMessage("PIG_EVENT_STOP"));
                        }
                });
                
                mainN.getController().onDelete(); //finally, delete it.
        }
        
        private static void initCoordinates(){
                //add coordinates like in this example.
				floatArray.add(new float[] { 1392.6293f, 1443.9843f, 599.3814f, 0f } );
				floatArray.add(new float[] { 1380.1246f, 1439.3772f, 599.3814f, 0f } );
				floatArray.add(new float[] { 1375.0859f, 1451.715f, 599.3814f, 0f } );
				floatArray.add(new float[] { 1314.7891f, 1409.6606f, 596.875f, 0f } );
				floatArray.add(new float[] { 1328.1356f, 1439.2648f, 596.875f, 0f } );
				floatArray.add(new float[] { 1281.9504f, 1443.41f, 595.3478f, 0f } );
				floatArray.add(new float[] { 1259.2769f, 1507.1676f, 596.375f, 0f } );
				floatArray.add(new float[] { 1279.449f, 1516.3469f, 596.39667f, 0f } );
				floatArray.add(new float[] { 1321.1035f, 1464.9863f, 597.45654f, 0f } );
				floatArray.add(new float[] { 1291.4058f, 1532.2805f, 595.5f, 0f } );
				floatArray.add(new float[] { 1328.5823f, 1556.6425f, 595.4114f, 0f } );
				floatArray.add(new float[] { 1363.1011f, 1575.1185f, 596.375f, 0f } );
				floatArray.add(new float[] { 1380.4497f, 1570.4167f, 595.375f, 0f } );
				floatArray.add(new float[] { 1374.59f, 1544.9429f, 595.375f, 0f } );
				floatArray.add(new float[] { 1382.2604f, 1504.3105f, 597.45654f, 0f } );
				floatArray.add(new float[] { 1367.952f, 1477.3693f, 593.92474f, 0f } );
				floatArray.add(new float[] { 1342.2858f, 1486.9225f, 594.15155f, 0f } );
				floatArray.add(new float[] { 1316.567f, 1484.2151f, 593.9247f, 0f } );
				floatArray.add(new float[] { 1325.7007f, 1519.8116f, 594.4299f, 0f } );
				floatArray.add(new float[] { 1391.5535f, 1495.8503f, 595.5f, 0f } );
				floatArray.add(new float[] { 1313.5392f, 1459.7028f, 597.5314f, 0f } );
				floatArray.add(new float[] { 1335.4752f, 1390.05f, 598.875f, 0f } );
				floatArray.add(new float[] { 1366.4854f, 1385.4115f, 599.75f, 0f } );
				floatArray.add(new float[] { 1386.0065f, 1400.7584f, 599.75f, 0f } );
				floatArray.add(new float[] { 1406.5845f, 1423.3438f, 600.3041f, 0f } );
				floatArray.add(new float[] { 1441.0446f, 1442.0468f, 599.75f, 0f } );
				floatArray.add(new float[] { 1416.2412f, 1522.4453f, 596.875f, 0f } );
				floatArray.add(new float[] { 1353.6458f, 1554.5099f, 595.375f, 0f } );
				floatArray.add(new float[] { 1350.1179f, 1457.6365f, 598.31476f, 0f } );
				floatArray.add(new float[] { 1353.2439f, 1494.5071f, 594.15155f, 0f } );
				
        }
}
