package ai.events;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.services.NpcShoutsService;

/**
 *
 * @author Romanz
 */
@AIName("pig")
public class PigAI2 extends AggressiveNpcAI2 {
    
        @Override
        protected void handleSpawned() {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 390005, getObjectId(), 0, 0);
                super.handleSpawned();
        }

}