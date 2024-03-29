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
package ai.instance.tiamatStrongHold;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import java.util.concurrent.Future;


/**
 * @author Luzien
 *
 */
@AIName("electrocute")
public class ElectrocuteAI2 extends NpcAI2 {

	private Future<?> task;
  
  @Override
  protected void handleSpawned() {
  	super.handleSpawned();
		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run()	{
					AI2Actions.useSkill(ElectrocuteAI2.this, 20757);
					AI2Actions.useSkill(ElectrocuteAI2.this, 20738);
				}
			},0, 2000);
  	despawn();
  }
  
  private void despawn() {
  	ThreadPoolManager.getInstance().schedule(new Runnable() {

  		@Override
  		public void run() {
  			getOwner().getController().onDelete();
  		}
  	}, 10500);
  }
	
	@Override
	public void handleDespawned() {
		task.cancel(true);
		super.handleDespawned();
	}
 
  
  @Override
  protected AIAnswer pollInstance(AIQuestion question) {
      switch (question) {
          case SHOULD_DECAY:
              return AIAnswers.NEGATIVE;
          case SHOULD_RESPAWN:
              return AIAnswers.NEGATIVE;
          case SHOULD_REWARD:
              return AIAnswers.NEGATIVE;
          default:
              return null;
      }
   }
}