/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package ai.instance.unstableSplinterpath;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.utils.MathUtil;


/**
 * @author Cheatkiller
 * @Modifier DeathMagnestic 4.0
 */
@AIName("unstablepieceofsplendor")
public class UnstablePieceOfSplendorAI2 extends AggressiveNpcAI2 {

  @Override
  protected void handleCreatureSee(Creature creature) {
      checkDistance(this, creature);
  }
  
  @Override
  protected void handleCreatureMoved(Creature creature) {
      checkDistance(this, creature);
  }

  private void checkDistance(NpcAI2 ai, Creature creature) {
  	Npc unstablerukril = getPosition().getWorldMapInstance().getNpc(219551);
  	Npc unstableebonsoul = getPosition().getWorldMapInstance().getNpc(219552);
     if (creature instanceof Npc) {
    	if (MathUtil.isIn3dRange(getOwner(), unstableebonsoul, 5) && unstableebonsoul.getEffectController().hasAbnormalEffect(19159)) {
    		unstableebonsoul.getEffectController().removeEffect(19159);
    		if(unstablerukril != null && unstablerukril.getEffectController().hasAbnormalEffect(19266))
    			unstablerukril.getEffectController().removeEffect(19266);
    	}
    }
  }
}
