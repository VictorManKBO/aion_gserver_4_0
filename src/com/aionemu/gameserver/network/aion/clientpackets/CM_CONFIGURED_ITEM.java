/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

import com.aionemu.gameserver.services.item.ItemService;
/**
 *
 * @author 123
 */
public class CM_CONFIGURED_ITEM extends AionClientPacket {
    
        private int itemObjectId;
                
    
        /**
	 * Constructs new instance of <tt>CM_CONFIGURED_ITEM </tt> packet
	 * 
	 * @param opcode
	 */
	public CM_CONFIGURED_ITEM(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}
        
        @Override
	protected void readImpl() {
            itemObjectId = readD();
            readD();
        }
        
        @Override
	protected void runImpl() {
            ItemService.configuredItemByObjectId(getConnection().getActivePlayer(), itemObjectId);
        }
}
