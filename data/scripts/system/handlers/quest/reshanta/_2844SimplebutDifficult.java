package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author DeathMagnestic and Ada
 */

public class _2844SimplebutDifficult extends QuestHandler {

	private final static int questId = 2844;

	public _2844SimplebutDifficult() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(269266).addOnQuestStart(questId);
		qe.registerQuestNpc(269266).addOnTalkEvent(questId);
		qe.registerQuestNpc(215346).addOnKillEvent(questId);
		qe.registerQuestNpc(215347).addOnKillEvent(questId);
		qe.registerQuestNpc(215344).addOnKillEvent(questId);
		qe.registerQuestNpc(215335).addOnKillEvent(questId);
		qe.registerQuestNpc(215333).addOnKillEvent(questId);
		qe.registerQuestNpc(215332).addOnKillEvent(questId);
		qe.registerQuestNpc(215331).addOnKillEvent(questId);
		qe.registerQuestNpc(215330).addOnKillEvent(questId);
		qe.registerQuestNpc(215329).addOnKillEvent(questId);
		qe.registerQuestNpc(215343).addOnKillEvent(questId);
		qe.registerQuestNpc(215337).addOnKillEvent(questId);
		qe.registerQuestNpc(215336).addOnKillEvent(questId);
		qe.registerQuestNpc(215318).addOnKillEvent(questId);
		qe.registerQuestNpc(215319).addOnKillEvent(questId);
		qe.registerQuestNpc(215325).addOnKillEvent(questId);
		qe.registerQuestNpc(215326).addOnKillEvent(questId);
		qe.registerQuestNpc(215327).addOnKillEvent(questId);
		qe.registerQuestNpc(215320).addOnKillEvent(questId);
		qe.registerQuestNpc(215321).addOnKillEvent(questId);
		qe.registerQuestNpc(215322).addOnKillEvent(questId);
		qe.registerQuestNpc(215137).addOnKillEvent(questId);
		qe.registerQuestNpc(215139).addOnKillEvent(questId);
		qe.registerQuestNpc(215138).addOnKillEvent(questId);
		qe.registerQuestNpc(215141).addOnKillEvent(questId);
		qe.registerQuestNpc(215140).addOnKillEvent(questId);
		qe.registerQuestNpc(215143).addOnKillEvent(questId);
		qe.registerQuestNpc(215142).addOnKillEvent(questId);
		qe.registerQuestNpc(215145).addOnKillEvent(questId);
		qe.registerQuestNpc(215144).addOnKillEvent(questId);
		qe.registerQuestNpc(215147).addOnKillEvent(questId);
		qe.registerQuestNpc(215149).addOnKillEvent(questId);
		qe.registerQuestNpc(215151).addOnKillEvent(questId);
		qe.registerQuestNpc(215150).addOnKillEvent(questId);
		qe.registerQuestNpc(215152).addOnKillEvent(questId);
		qe.registerQuestNpc(215153).addOnKillEvent(questId);
		qe.registerQuestNpc(215154).addOnKillEvent(questId);
		qe.registerQuestNpc(215155).addOnKillEvent(questId);
		qe.registerQuestNpc(215156).addOnKillEvent(questId);
		qe.registerQuestNpc(215158).addOnKillEvent(questId);
		qe.registerQuestNpc(215159).addOnKillEvent(questId);
		qe.registerQuestNpc(215161).addOnKillEvent(questId);
		qe.registerQuestNpc(215162).addOnKillEvent(questId);
		qe.registerQuestNpc(215163).addOnKillEvent(questId);
		qe.registerQuestNpc(215164).addOnKillEvent(questId);
		qe.registerQuestNpc(215165).addOnKillEvent(questId);
		qe.registerQuestNpc(215166).addOnKillEvent(questId);
		qe.registerQuestNpc(215167).addOnKillEvent(questId);
		qe.registerQuestNpc(215179).addOnKillEvent(questId);
		qe.registerQuestNpc(215175).addOnKillEvent(questId);
		qe.registerQuestNpc(215172).addOnKillEvent(questId);
		qe.registerQuestNpc(215170).addOnKillEvent(questId);
		qe.registerQuestNpc(215171).addOnKillEvent(questId);
		qe.registerQuestNpc(215168).addOnKillEvent(questId);
		qe.registerQuestNpc(215169).addOnKillEvent(questId);
		qe.registerQuestNpc(215177).addOnKillEvent(questId);
		qe.registerOnEnterWorld(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		int targetId = 0;
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE) {
			if (targetId == 269266) {
				if (env.getDialog() == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 1011);
				else
					return sendQuestStartDialog(env);
			}
		}
		else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 269266)
				return true;
		}
		else if (qs.getStatus() == QuestStatus.REWARD && targetId == 269266) {
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
			if (player.getCommonData().getPosition().getMapId() == 300120000) {
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
