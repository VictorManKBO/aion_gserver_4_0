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
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;


@InstanceID(301040000)
public class SteelRoseQuartersGroupInstance extends GeneralInstanceHandler {

    private boolean isInstanceDestroyed;

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        switch (npc.getNpcId()) {
            case 730770: //Shulack Battle Ration.
                player.getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.HP, 10000);
                player.getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.MP, 10000);
                despawnNpc(npc);
                break;
        }
    }

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        int rnd = Rnd.get(1, 4);
        switch (rnd) { //Nerukiki The Timid.
            case 1:
                spawn(230724, 517.2308f, 506.71335f, 951.7029f, (byte) 114);
                break;
            case 2:
                spawn(230724, 524.70435f, 519.89136f, 952.4762f, (byte) 55);
                break;
            case 3:
                spawn(230724, 473.1518f, 575.4152f, 958.0783f, (byte) 53);
                break;
            case 4:
                spawn(230724, 461.15442f, 512.30054f, 952.54895f, (byte) 5);
                break;
        }
    }

    @Override
    public void onDie(Npc npc) {
        Player player = npc.getAggroList().getMostPlayerDamage();
        switch (npc.getObjectTemplate().getTemplateId()) {
            case 230740: //Accountant Kanerunerk.
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