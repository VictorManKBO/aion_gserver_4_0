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
package quest.pernon;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * 
 * @author Ritsu
 */
public class _28805SomethingOldSomethingNew extends QuestHandler 
{

	private static final int questId = 28805;

	public _28805SomethingOldSomethingNew()
	{
		super(questId);
	}

	@Override
	public void register() 
	{
		qe.registerQuestNpc(830154).addOnQuestStart(questId);
		qe.registerQuestNpc(830154).addOnTalkEvent(questId);
		qe.registerQuestNpc(830521).addOnTalkEvent(questId);
		qe.registerQuestNpc(830662).addOnTalkEvent(questId);
		qe.registerQuestNpc(830663).addOnTalkEvent(questId);
		qe.registerQuestNpc(730525).addOnTalkEvent(questId);
		qe.registerQuestNpc(730522).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if(qs == null || qs.getStatus() == QuestStatus.NONE)
		{
			if (targetId == 830154) 
			{
				if (dialog == DialogAction.QUEST_SELECT) 
					return sendQuestDialog(env, 1011); 
				else 
					return sendQuestStartDialog(env);
			}
		}
		else if (qs.getStatus() == QuestStatus.START)
		{
			int var = qs.getQuestVarById(0);
			switch (targetId)
			{
				case 830521: 
				case 830662: 
				case 830663: 
				{
					switch (dialog)
					{
						case QUEST_SELECT: 
						{
							if (var == 0) 
								return sendQuestDialog(env, 1352);
							else if (var == 2) 
								return sendQuestDialog(env, 2375);
						}
						case SETPRO1:
						{
							return defaultCloseDialog(env, 0, 1);
						}
						case SELECT_QUEST_REWARD:
						{
							changeQuestStep(env, 2, 2, true);
							return sendQuestDialog(env, 5);
						}
					}
				}
				case 730525: 
				case 730522: 
				{
					switch (dialog)
					{
						case USE_OBJECT: 
						{
							if (var == 1) 
								return sendQuestDialog(env, 1693);
						}
						case SETPRO2:
						{
							return defaultCloseDialog(env, 1, 2);
						}
					}
				}

			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD) 
		{
			switch (targetId)
			{
				case 830521: 
				case 830662: 
				case 830663: 
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
