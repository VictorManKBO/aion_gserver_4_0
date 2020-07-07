package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.event.L2EventMg;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 *
 * @author Romanz
 */
public class PigEvent extends AdminCommand {

        private boolean isStarted;
        public PigEvent(){
                super("pigevent");
        }
        
        @Override
    public void execute(Player admin, String[] params) {
                if(params == null || params.length < 1){
                        PacketSendUtility.sendMessage(admin, "syntax //pigevent <start | end>");
                        return;
                }
                
                if(params[0].toLowerCase().equals("start")){
                        if(!isStarted) {
                                L2EventMg.startEvent();
                                isStarted = true;
                        }
                        else {
                                PacketSendUtility.sendMessage(admin, "Event is already started.");
                        }
                }
                else if(params[0].toLowerCase().equals("stop")){
                        if(isStarted) {
                                isStarted = false;
                                L2EventMg.endEvent();
                        } else {
                                PacketSendUtility.sendMessage(admin, "Event is not started.");
                        }
                }
        }
        
}
