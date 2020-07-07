package instance.danuar;

import java.util.Map;
import java.util.Set;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;


@InstanceID(301140000)
public class DanuarSanctuaryInstance extends GeneralInstanceHandler {

    private int danuarSanctuaryBossEasy;
    private int danuarSanctuaryBossMiddle;
    private int danuarSanctuaryBossStrong;
    private boolean isStartTimer1 = false;
    private boolean isStartTimer2 = false;
    private boolean isStartTimer3 = false;
    private Map<Integer, StaticDoor> doors;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
        int rnd = Rnd.get(1, 3);
        switch (rnd) {
            case 1:
                spawn(233086, 1056.5698f, 693.86584f, 282.0391f, (byte) 30); //Warmage Suyaroka.
                spawn(233089, 1045.4534f, 682.2679f, 282.0391f, (byte) 60); //Chief Medic Tagnu.
                spawn(233090, 1056.4889f, 670.9826f, 282.0391f, (byte) 91); //Virulent Ukahim.
                break;
            case 2:
                spawn(233380, 1045.4534f, 682.2679f, 282.0391f, (byte) 60); //Warmage Suyaroka.
                spawn(233381, 1056.4889f, 670.9826f, 282.0391f, (byte) 91); //Chief Medic Tagnu.
                spawn(233382, 1056.5698f, 693.86584f, 282.0391f, (byte) 30); //Virulent Ukahim.
                break;
            case 3:
                spawn(233383, 1045.4534f, 682.2679f, 282.0391f, (byte) 60); //Warmage Suyaroka.
                spawn(233384, 1056.4889f, 670.9826f, 282.0391f, (byte) 91); //Chief Medic Tagnu.
                spawn(233385, 1056.5698f, 693.86584f, 282.0391f, (byte) 30); //Virulent Ukahim.
                break;
        }
    }

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        switch (npc.getNpcId()) {
            case 701859: //Metallic Mystic KeyStone.
                ItemService.addItem(player, 182400001, 10);
                break;
            case 701860: //Golden Mystic KeyStone.
                ItemService.addItem(player, 182400001, 10);
                break;
            case 701861: //Bronze Mystic KeyStone.
                ItemService.addItem(player, 182400001, 10);
                break;
            case 701862: //Cubic Mystic KeyStone.
                openDoor(160);
                break;
            case 701863: //Spherical Mystic KeyStone.
                openDoor(10);
                break;
            case 701864: //Pyramidal Mystic KeyStone.
                openDoor(154);
                break;
        }
    }

    @Override
    public void onDie(Npc npc) {
        Player player = npc.getAggroList().getMostPlayerDamage();
        switch (npc.getObjectTemplate().getTemplateId()) {
            case 233103: //Shulack Mercenary Elite.
                openDoor(400);
                break;
            case 233187: //Attack the rocks to activate the updraft.
                despawnNpc(npc);
                TeleportService2.teleportTo(player, 301140000, 887.1296f, 845.2534f, 292.86874f, (byte) 112, TeleportAnimation.BEAM_ANIMATION);
                break;
            case 233086: //Warmage Suyaroka.
            case 233089: //Chief Medic Tagnu.
            case 233090: //Virulent Ukahim.
                danuarSanctuaryBossEasy++;
                if (danuarSanctuaryBossEasy == 3) {
                    spawn(701876, 1075.5334f, 682.4891f, 282.0391f, (byte) 60); //Danuar Sanctuary Exit.
                }
                break;
            case 233380: //Warmage Suyaroka.
            case 233381: //Chief Medic Tagnu.
            case 233382: //Virulent Ukahim.
                danuarSanctuaryBossMiddle++;
                if (danuarSanctuaryBossMiddle == 3) {
                    spawn(701876, 1075.5334f, 682.4891f, 282.0391f, (byte) 60); //Danuar Sanctuary Exit.
                }
                break;
            case 233383: //Warmage Suyaroka.
            case 233384: //Chief Medic Tagnu.
            case 233385: //Virulent Ukahim.
                danuarSanctuaryBossStrong++;
                if (danuarSanctuaryBossStrong == 3) {
                    spawn(701876, 1075.5334f, 682.4891f, 282.0391f, (byte) 60); //Danuar Sanctuary Exit.
                }
                break;
        }
    }

    //The players have 15 minutes to clear the way.
    //After 15 min boss "ELITE" will be removed.
    @Override
    public void onEnterZone(Player player, ZoneInstance zone) {
        if (zone.getAreaTemplate().getZoneName() == ZoneName.get("THE_CHARNELS_301140000")) {
            if (!isStartTimer1) {
                isStartTimer1 = true;
                System.currentTimeMillis();
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900));
                final Npc mobwaypath1A = (Npc) spawn(233391, 1050.2266f, 276.24228f, 309.3889f, (byte) 64);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        mobwaypath1A.getController().delete();
                    }
                }, 900000);
            }
        } else if (zone.getAreaTemplate().getZoneName() == ZoneName.get("THE_CRYPTS_301140000")) {
            if (!isStartTimer2) {
                isStartTimer2 = true;
                System.currentTimeMillis();
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900));
                final Npc mobwaypath1B = (Npc) spawn(233391, 717.85614f, 980.1326f, 318.71698f, (byte) 110);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        mobwaypath1B.getController().delete();
                    }
                }, 900000);
            }
        } else if (zone.getAreaTemplate().getZoneName() == ZoneName.get("THE_CATACOMBS_301140000")) {
            if (!isStartTimer3) {
                isStartTimer3 = true;
                System.currentTimeMillis();
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900));
                final Npc mobwaypath1C = (Npc) spawn(233391, 1005.93787f, 1366.8308f, 337.25f, (byte) 13);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        mobwaypath1C.getController().delete();
                    }
                }, 900000);
            }
        }
    }

    public void removeItems(Player player) {
        Storage storage = player.getInventory();
        storage.decreaseByItemId(185000181, storage.getItemCountByItemId(185000181)); //The Catacombs Key.
        storage.decreaseByItemId(185000182, storage.getItemCountByItemId(185000182)); //The Crypts Key.
        storage.decreaseByItemId(185000183, storage.getItemCountByItemId(185000183)); //The Charnels Key.
    }

    @Override
    public void onDropRegistered(Npc npc) {
        Set<DropItem> dropItems = DropRegistrationService.getInstance().geCurrentDropMap().get(npc.getObjectId());
        int npcId = npc.getNpcId();
        switch (npcId) {
            case 233391: //Sanctuary Keybox.
                dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000181, 1));
                dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000182, 1));
                dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000183, 1));
                break;
        }
    }

    @Override
    public void onLeaveInstance(Player player) {
        removeItems(player);
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }

    private void openDoor(int doorId) {
        StaticDoor door = doors.get(doorId);
        if (door != null) {
            door.setOpen(true);
        }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}