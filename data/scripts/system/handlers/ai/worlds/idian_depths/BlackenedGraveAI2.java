package ai.worlds.idian_depths;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.world.knownlist.Visitor;

@AIName("blackened_grave")
public class BlackenedGraveAI2 extends NpcAI2 {

	@Override
	protected void handleDied() {
		Npc npc = (Npc) spawn(284262, 394.15338f, 893.5626f, 559.375f, (byte) 0);
		super.handleDied();
		AI2Actions.deleteOwner(this);
	}
	
	@Override
	public int modifyDamage(int damage) {
		return super.modifyDamage(1);
	}

}