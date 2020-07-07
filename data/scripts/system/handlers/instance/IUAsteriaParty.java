package instance;

import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MMORPG Online Games
 */

@InstanceID(301160000)
public class IUAsteriaParty extends GeneralInstanceHandler {
	
    private Map<Integer, StaticDoor> doors;
    private List<Integer> movies = new ArrayList<Integer>();
	  private boolean isInstanceDestroyed;
	  private int lukibukiKilled;
	  
	  @Override
		public void onEnterInstance(Player player) {
	  	
	  	player.getEffectController().removeEffect(218611);
  		player.getEffectController().removeEffect(218610);
		SkillEngine.getInstance().applyEffectDirectly(21332, player, player, 320000 * 3);
  		
		}
	
	  @Override
	  public void onDie(Npc npc) {
		
	  	 Player player = npc.getAggroList().getMostPlayerDamage();
	  	 switch (npc.getObjectTemplate().getTemplateId()) {
		   
	  	 case 233147: 
	  	 case 233153: 
	  	 case 233161: 
	  	 lukibukiKilled ++;
			 if (lukibukiKilled == 1) {
				}
			 else if (lukibukiKilled == 2) {
				}
			 else if (lukibukiKilled == 3) {
			 PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 0, 500, 0));
	     despawnNpc(getNpc(831572)); 
	     despawnNpc(getNpc(831598));
	     despawnNpc(getNpc(831573));
	     spawn(831598, 522.3982f, 564.6901f, 199.0337f, (byte) 60, 14); 
	     spawn(831574, 516.80f, 565.53f, 198.90f, (byte) 60);
			 spawn(831575, 510.55f, 565.47f, 198.75f, (byte) 60); 
			 
			 return;
			}
		 }
	  }
    	
    protected void despawnNpc(Npc npc) {
    if (npc != null) {
    npc.getController().onDelete();
      }
    }

    protected void despawnNpcs(List<Npc> npcs) {
    for (Npc npc: npcs) {
    npc.getController().onDelete();
      }
    }

    protected Npc getNpc(int npcId) {
    if (!isInstanceDestroyed) {
    return instance.getNpc(npcId);
      }
    return null;
    }

    protected List<Npc> getNpcs(int npcId) {
    if (!isInstanceDestroyed) {
    return instance.getNpcs(npcId);
      }
    return null;
    }

    protected void sendMsg(int msg, int Obj, int color) {
    sendMsg(msg, Obj, false, color);
    }

    private void sendMovie(Player player, int movie) {
    if (!movies.contains(movie)) {
    movies.add(movie);
    PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, movie));
      }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
    PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
    PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
    return true;
    }

    @Override
    public boolean onReviveEvent(Player player) {
    PlayerReviveService.revive(player, 25, 25, false, 0);
    player.getGameStats().updateStatsAndSpeedVisually();
    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
    TeleportService2.teleportTo(player, mapId, instanceId, 510.2436f, 512.10333f, 417.40436f, (byte) 49);
    return true;
    }

    private boolean isDead(Npc npc) {
    return (npc == null || npc.getLifeStats().isAlreadyDead());
    }

  	private void sendPacket(final AionServerPacket packet) {
  		instance.doOnAllPlayers(new Visitor<Player>() {

  			@Override
  			public void visit(Player player) {
  				PacketSendUtility.sendPacket(player, packet);
  			}

  		});
  	}
  	
  	@Override
  	public void onLeaveInstance(Player player) {
  		removeEffects(player);
  	}

  	@Override
  	public void onPlayerLogOut(Player player) {
  		removeEffects(player);
  	}

  	private void removeEffects(Player player) {
  		PlayerEffectController effectController = player.getEffectController();
  		effectController.removeEffect(19520);
  		effectController.removeEffect(21332);
  		effectController.removeEffect(21333);
  	}
  	
  	@Override
  	public void handleUseItemFinish(Player player, Npc npc) {
  		switch (npc.getNpcId()) {
  		case 730396:
  			SkillEngine.getInstance().getSkill(player, 19502, 1, player).useNoAnimationSkill();
  			NpcActions.scheduleRespawn(npc);
  			sendMsg(1400926);
  			break;
  		}
  	}

    @Override
    public void onInstanceDestroy() {
    movies.clear();
    isInstanceDestroyed = true;
      }
    }