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
package quest.katalam;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * 
 * @author Antixrist, Smaster
 */
public class _22500NapTimeIsOver extends QuestHandler 
{

	private static final int questId = 22500;

	public _22500NapTimeIsOver() 
	{
		super(questId);
	}

	@Override
	public void register() 
	{
		qe.registerQuestNpc(800529).addOnQuestStart(questId);
		qe.registerQuestNpc(800529).addOnTalkEvent(questId);
		qe.registerQuestNpc(801001).addOnTalkEvent(questId);
		qe.registerQuestNpc(801007).addOnTalkEvent(questId);
		qe.registerQuestNpc(801255).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if(qs == null || qs.getStatus() == QuestStatus.NONE)
		{
			if (targetId == 800529) 
			{
				if (dialog == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 1011);
				else 
					return sendQuestStartDialog(env);
			}
		}

		if (qs == null)
			return false;
		else if (qs.getStatus() == QuestStatus.START) 
		{
			int var = qs.getQuestVarById(0);
			switch (targetId) 
			{
				case 801001: 
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 0) 
							{
								return sendQuestDialog(env, 1352);
							}
						}
						case SETPRO1: 
						{
							return defaultCloseDialog(env, 0, 1);
						}
					}
				}
				break;
				case 801007:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 1) 
							{
								return sendQuestDialog(env, 1693);
							}
						}
						case SETPRO2: 
						{
							return defaultCloseDialog(env, 1, 2);
						}
					}
				}
				break;
				case 801255:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 2) 
							{
								return sendQuestDialog(env, 2034);
							}
						}
						case SETPRO3: 
						{
							return defaultCloseDialog(env, 2, 3);
						}
					}
				}
				break;
				case 800529:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 3) 
							{
								return sendQuestDialog(env, 2375);
							}
						}
						case SELECT_QUEST_REWARD: 
						{
							return defaultCloseDialog(env, 3, 3, true, true, 5);
						}
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 800529)
			{
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
