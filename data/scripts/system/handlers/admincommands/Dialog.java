package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.util.concurrent.ScheduledFuture;
import javolution.util.FastMap;

/**
 * @author Made in Russia.
 *
 */
public class Dialog extends AdminCommand {

    public Dialog() {
        super("dialog");
    }
    private int dialog;
    private FastMap<Integer, ScheduledFuture<?>> tasks = new FastMap<Integer, ScheduledFuture<?>>();

    @Override
    public void execute(Player admin, String[] params) {
        if (admin.getTarget() == null) {
            PacketSendUtility.sendMessage(admin, "You need selected target");
            return;
        }
        if (params.length == 0) {
            PacketSendUtility.sendMessage(admin, "Syntax : //test <DialogId> <start>");
            return;
        }
        int target = admin.getTarget().getObjectId();       
        if (params.length == 1) {
            if (params[0].equals("stop")) {
                tasks.get(admin.getObjectId()).cancel(true);
                tasks.remove(admin.getObjectId());
                return;
            }
            this.dialog = Integer.parseInt(params[0]);
            sendDialog(target, dialog, admin);
        } else {
            this.dialog = Integer.parseInt(params[0]);
            startAutoSend(target, admin);
        }
        return;
    }

    private void startAutoSend(final int target, final Player admin) {
        ScheduledFuture<?> task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                PacketSendUtility.sendPacket(admin, new SM_DIALOG_WINDOW(target, 0));
                sendDialog(target, dialog, admin);
                dialog++;
            }
        }, 500, 500);
        tasks.put(admin.getObjectId(), task);
        return;
    }

    private void sendDialog(int target, int dialog, Player admin) {
        try {
            PacketSendUtility.sendMessage(admin, "Target objId:" + target + " DialogId: " + dialog);
            PacketSendUtility.sendPacket(admin, new SM_DIALOG_WINDOW(target, dialog));
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "Syntax : //test <DialogId>");
        }
        return;
    }
}
