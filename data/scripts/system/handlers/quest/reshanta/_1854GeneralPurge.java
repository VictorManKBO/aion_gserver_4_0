/*
 * This file is part of gtemu.net <gtemu.net>.
 *
 *  gtemu.net is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  gtemu.net is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with gtemu.net.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

/**
 * @author Luzien
 */
public class _1854GeneralPurge extends QuestHandler {

    private final static int questId = 1854;

    public _1854GeneralPurge() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(278501).addOnQuestStart(questId);
        qe.registerQuestNpc(278501).addOnTalkEvent(questId);
        qe.registerOnKillRanked(AbyssRankEnum.GENERAL, questId);
    }

    @Override
    public boolean onKillRankedEvent(QuestEnv env) {
        return defaultOnKillRankedEvent(env, 0, 3, true); // reward
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (env.getTargetId() == 278501) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                }
                else {
                    return sendQuestStartDialog(env);
                }
            }
            else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
                if (env.getTargetId() == 278501) {
                    if (env.getDialog() == DialogAction.USE_OBJECT) {
                        return sendQuestDialog(env, 1352);
                    }
                    else {
                        return sendQuestEndDialog(env);
                    }
                }
            }
        }
        return false;
    }
}