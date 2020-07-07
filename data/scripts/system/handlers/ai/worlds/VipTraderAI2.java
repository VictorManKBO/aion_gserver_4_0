package ai.worlds;

import ai.GeneralNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Romanz
 */
@AIName("vip_trader")
public class VipTraderAI2 extends GeneralNpcAI2 {

  	@Override
	protected void handleDialogStart(Player player) {
            int membership = player.getClientConnection().getAccount().getMembership();
			
        if(membership >= 2)
		{
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10));
		}
		else {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
			}
		}
	}
