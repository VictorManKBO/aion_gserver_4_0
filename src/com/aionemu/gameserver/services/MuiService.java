/* Copyright (C) 2013 Dr2co
 *
 * Created with IntelliJ IDEA.
 * User: Dr2co
 * Date: 03.07.13
 *
 *  pt-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  pt-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with pt-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.mui.MuiEngine;
import com.aionemu.gameserver.utils.mui.handlers.MuiHandler;

/**
 * @author Dr2co
 */


public class MuiService {

    private MuiHandler handler;

    public void load() {
        handler = MuiEngine.getInstance().getNewMuiHandler(GSConfig.SERVER_LANGUAGE);
    }

    public String getNonUTFMessage(String name, Object... params) {
        return handler.getMessage(name, params);
    }

    public String getMessage(String name, Object... params) {
        return convertFromUTF8(handler.getMessage(name, params));
    }


    public void sendNonUTFMessage(Player player, String message) {
        PacketSendUtility.sendMessage(player, message);
    }

    public void sendMessage(Player player, String name, Object... params) {
        PacketSendUtility.sendMessage(player, convertFromUTF8(handler.getMessage(name, params)));
    }

    public String convertFromUTF8(String s) {
        String out;
        try {
            byte[] bytes = s.getBytes();
            for (int i = 0; i < bytes.length - 1; i++) {
                if (bytes[i] == -48 && bytes[i + 1] == 63) {
                    bytes[i] = (byte) 208;
                    bytes[i + 1] = (byte) 152;
                }
            }
            out = new String(bytes, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public static MuiService getInstance() {
        return SingletonHolder.instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final MuiService instance = new MuiService();
    }
}
