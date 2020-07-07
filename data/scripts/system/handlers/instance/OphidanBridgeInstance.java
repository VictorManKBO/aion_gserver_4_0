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
package instance;

import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import java.util.Map;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;


@InstanceID(300590000)
public class OphidanBridgeInstance extends GeneralInstanceHandler {

    private int Powerdevice;
    private Map<Integer, StaticDoor> doors;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
    }

    @Override
    public void onDie(Npc npc) {
        switch (npc.getNpcId()) {
            case 230413: //Ophidan Bridge Power Device 1.
            case 230414: //Ophidan Bridge Power Device 2.
            case 230415: //Ophidan Bridge Power Device 3.
            case 230416: //Ophidan Bridge Power Device 3.
                Powerdevice++;
                if (Powerdevice == 1) {
                    sendMsg(1401884);
                } else if (Powerdevice == 2) {
                    sendMsg(1401886);
                } else if (Powerdevice == 3) {
                    sendMsg(1401888);
                } else if (Powerdevice == 4) {
                    sendMsg(1401890);
                    doors.get(47).setOpen(true);
                }
                despawnNpc(npc);
                break;
            case 231050: //Ophidan Bridge Ultimate Cannon.
                despawnNpc(npc);
                spawn(730868, 350.18478f, 490.73065f, 606.34015f, (byte) 1); //Ophidan Bridge Exit.
                break;
        }
    }

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        switch (npc.getNpcId()) {
            case 701646: //Ophidan Bridge Tank Elyos.
                SkillEngine.getInstance().getSkill(npc, 21434, 1, player).useNoAnimationSkill();
                despawnNpc(npc);
                break;
            case 701647: //Ophidan Bridge Tank Asmodians.
                SkillEngine.getInstance().getSkill(npc, 21435, 1, player).useNoAnimationSkill();
                despawnNpc(npc);
                break;
        }
    }

    private void removeEffects(Player player) {
        PlayerEffectController effectController = player.getEffectController();
        effectController.removeEffect(21434);
        effectController.removeEffect(21435);
    }

    @Override
    public void onLeaveInstance(Player player) {
        removeEffects(player);
    }

    @Override
    public void onPlayerLogOut(Player player) {
        removeEffects(player);
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}