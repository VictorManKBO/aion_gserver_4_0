/*
 * This file is part of Pirate Team Emulator.
 *
 * Pirate Team Emulator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pirate Team Emulator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pirate Team Emulator.  If not, see <http://www.gnu.org/licenses/>.
 */
package instance.steelrose;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;


@InstanceID(301010000)
public class SteelRoseCargoSoloInstance extends GeneralInstanceHandler {

    private boolean isInstanceDestroyed;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        int rnd = Rnd.get(1, 3);
        switch (rnd) {
            case 1:
                spawn(230658, 462.325f, 512.27454f, 877.6181f, (byte) 90); //Badu The Lunatic.
                break;
            case 2:
                spawn(230659, 462.325f, 512.27454f, 877.6181f, (byte) 90); //Captain Mumu Kang.
                break;
            case 3:
                spawn(230660, 462.325f, 512.27454f, 877.6181f, (byte) 90); //Lampsprung Raon.
                break;
        }
    }

    @Override
    public void onDie(Npc npc) {
        Player player = npc.getAggroList().getMostPlayerDamage();
        switch (npc.getObjectTemplate().getTemplateId()) {
            case 730208: //Prison Door.
                despawnNpc(npc);
                break;
            case 230662: //Maintenance Chief Notakiki.
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 120));
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        instance.doOnAllPlayers(new Visitor<Player>() {
                            @Override
                            public void visit(Player player) {
                                onExitInstance(player);
                            }
                        });
                        onInstanceDestroy();
                    }
                }, 120000);
                break;
        }
    }

    @Override
    public void onExitInstance(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    @Override
    public void onInstanceDestroy() {
        isInstanceDestroyed = true;
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }
}