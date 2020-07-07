package com.aionemu.gameserver.model.instance.instancereward;


@SuppressWarnings("rawtypes")
public class EternalBastionReward extends InstanceReward {

	private int points;
	private int npcKills;
	private int rank = 7;
	private int collections;
	private int scoreAP;
	private int sillus;
	private int ceramium;
	private int favorable;
	private boolean isRewarded = false;

	public EternalBastionReward(Integer mapId, int instanceId) {
		super(mapId, instanceId);
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public int getPoints() {
		return points;
	}

	public void addNpcKill() {
		npcKills++;
	}

	public int getNpcKills() {
		return npcKills;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public void addGather() {
		collections++;
	}

	public int getGatherCollections() {
		return collections;
	}
	
	public boolean isRewarded() {
		return isRewarded;
	}

	public void setRewarded() {
		isRewarded = true;
	}
	
	public void setScoreAP(int ap) {
		this.scoreAP = ap;
	}
	
	public void setSillus(int sillus) {
		this.sillus = sillus;
	}
	
	public void setCeramium(int ceramium) {
		this.ceramium = ceramium;
	}
	
	public void setFavorable(int favorable) {
		this.favorable = favorable;
	}
}
