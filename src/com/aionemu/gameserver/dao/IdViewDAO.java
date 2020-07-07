package com.aionemu.gameserver.dao;

/**
 * @author GoodT
 */
public abstract class IdViewDAO implements IDFactoryAwareDAO {

        @Override
        public abstract int[] getUsedIDs();

        @Override
        public final String getClassName() {
                return IdViewDAO.class.getName();
        }
}