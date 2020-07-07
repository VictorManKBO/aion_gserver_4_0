package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author DeathMagnestic and Ada
 */

public class _2845TakeOutTheTrash extends QuestHandler {

	private final static int questId = 2845;

	public _2845TakeOutTheTrash() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(270166).addOnQuestStart(questId);
		qe.registerQuestNpc(270166).addOnTalkEvent(questId);
		qe.registerQuestNpc(215350).addOnKillEvent(questId);
		qe.registerQuestNpc(215351).addOnKillEvent(questId);
		qe.registerQuestNpc(215349).addOnKillEvent(questId);
		qe.registerQuestNpc(215358).addOnKillEvent(questId);
		qe.registerQuestNpc(215359).addOnKillEvent(questId);
		qe.registerQuestNpc(215356).addOnKillEvent(questId);
		qe.registerQuestNpc(215357).addOnKillEvent(questId);
		qe.registerQuestNpc(215354).addOnKillEvent(questId);
		qe.registerQuestNpc(215355).addOnKillEvent(questId);
		qe.registerQuestNpc(215352).addOnKillEvent(questId);
		qe.registerQuestNpc(215353).addOnKillEvent(questId);
		qe.registerQuestNpc(215377).addOnKillEvent(questId);
		qe.registerQuestNpc(215378).addOnKillEvent(questId);
		qe.registerQuestNpc(215379).addOnKillEvent(questId);
		qe.registerQuestNpc(215380).addOnKillEvent(questId);
		qe.registerQuestNpc(215361).addOnKillEvent(questId);
		qe.registerQuestNpc(215360).addOnKillEvent(questId);
		qe.registerQuestNpc(215363).addOnKillEvent(questId);
		qe.registerQuestNpc(215362).addOnKillEvent(questId);
		qe.registerQuestNpc(215365).addOnKillEvent(questId);
		qe.registerQuestNpc(215364).addOnKillEvent(questId);
		qe.registerQuestNpc(215367).addOnKillEvent(questId);
		qe.registerQuestNpc(215366).addOnKillEvent(questId);
		qe.registerQuestNpc(215369).addOnKillEvent(questId);
		qe.registerQuestNpc(215368).addOnKillEvent(questId);
		qe.registerQuestNpc(215371).addOnKillEvent(questId);
		qe.registerQuestNpc(215370).addOnKillEvent(questId);
		qe.registerQuestNpc(215373).addOnKillEvent(questId);
		qe.registerQuestNpc(215372).addOnKillEvent(questId);
		qe.registerQuestNpc(215375).addOnKillEvent(questId);
		qe.registerQuestNpc(215374).addOnKillEvent(questId);
		qe.registerQuestNpc(215212).addOnKillEvent(questId);
		qe.registerQuestNpc(215214).addOnKillEvent(questId);
		qe.registerQuestNpc(215208).addOnKillEvent(questId);
		qe.registerQuestNpc(215209).addOnKillEvent(questId);
		qe.registerQuestNpc(215210).addOnKillEvent(questId);
		qe.registerQuestNpc(215211).addOnKillEvent(questId);
		qe.registerQuestNpc(215204).addOnKillEvent(questId);
		qe.registerQuestNpc(215205).addOnKillEvent(questId);
		qe.registerQuestNpc(215206).addOnKillEvent(questId);
		qe.registerQuestNpc(215207).addOnKillEvent(questId);
		qe.registerQuestNpc(215200).addOnKillEvent(questId);
		qe.registerQuestNpc(215220).addOnKillEvent(questId);
		qe.registerQuestNpc(215222).addOnKillEvent(questId);
		qe.registerQuestNpc(215219).addOnKillEvent(questId);
		qe.registerQuestNpc(215182).addOnKillEvent(questId);
		qe.registerQuestNpc(215183).addOnKillEvent(questId);
		qe.registerQuestNpc(215180).addOnKillEvent(questId);
		qe.registerQuestNpc(215181).addOnKillEvent(questId);
		qe.registerQuestNpc(215199).addOnKillEvent(questId);
		qe.registerQuestNpc(215198).addOnKillEvent(questId);
		qe.registerQuestNpc(215197).addOnKillEvent(questId);
		qe.registerQuestNpc(215196).addOnKillEvent(questId);
		qe.registerQuestNpc(215195).addOnKillEvent(questId);
		qe.registerQuestNpc(215194).addOnKillEvent(questId);
		qe.registerQuestNpc(215193).addOnKillEvent(questId);
		qe.registerQuestNpc(215192).addOnKillEvent(questId);
		qe.registerQuestNpc(215189).addOnKillEvent(questId);
		qe.registerQuestNpc(215187).addOnKillEvent(questId);
		qe.registerQuestNpc(215186).addOnKillEvent(questId);
		qe.registerQuestNpc(215185).addOnKillEvent(questId);
		qe.registerQuestNpc(215184).addOnKillEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();

		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE) {
			if (env.getTargetId() == 270166) {
				if (env.getDialog() == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 1011);
				else
					return sendQuestStartDialog(env);
			}
		}
		else if (qs.getStatus() == QuestStatus.START) {
			if (env.getTargetId() == 270166)
				return true;
		}
		else if (qs.getStatus() == QuestStatus.REWARD && env.getTargetId() == 270166) {
			qs.setQuestVarById(0, 0);
			updateQuestStatus(env);
			return sendQuestEndDialog(env);
		}
		return false;
	}

	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START)
			return false;

		if (qs.getStatus() == QuestStatus.START) {
			if (player.getCommonData().getPosition().getMapId() == 300130000) {
				if (qs.getQuestVarById(0) < 79) {
					qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
					updateQuestStatus(env);
					return true;
				}
				else if (qs.getQuestVarById(0) == 79 || qs.getQuestVarById(0) > 79) {
					qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
					return true;
				}
			}
		}
		return false;
	}
}
