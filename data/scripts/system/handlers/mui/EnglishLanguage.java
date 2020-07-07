package mui;

import com.aionemu.gameserver.utils.mui.handlers.GeneralMuiHandler;
import com.aionemu.gameserver.utils.mui.handlers.MuiName;


@MuiName("en")
public class EnglishLanguage extends GeneralMuiHandler {

    @Override
    public String getMessage(String name, Object... params) {
        return String.format(messages.valueOf(name).getMessage(), params);
    }

    private static enum messages {

        HELLO_WORLD("Welcome to %s!"),
        HELLO_REMEMBER("Please remember:"),
        HELLO_STAFF("Announcement : %s Staff will never ask for your password!\n"),
        HELLO_ADVERTISING("Announcement : Advertising for another server is prohibited."),
        HELLO_ENJOY("Please enjoy your stay on our server."),
        MEMBERSHIP_LOGIN("\nYour Status: %s."),
        MEMBERSHIP_PREMIUM("Premium"),
        MEMBERSHIP_VIP("VIP"),
        MEMBERSHIP_CRAFT("CritCraft"),
        MEMBERSHIP_AP("Abyss Point"),
        MEMBERSHIP_COLLECTION("Craft"),
        MEMBERSHIP_EXPIRE("\nExpiration date: %s\n"),
        MEMBERSHIP_WORLD_ANNOUNCE("%s login in game."),
        ONLINE_TOLL("Your account has been credited %d Gpoint."),
        ONLINE_BONUS_TOLL("To your bonus account was credited %d Gpoint."),
        JSON_TITLE("News in VK %s"),
        VK_COMMENTS_LIKE("<br>Date: %s<br><font color='829BAF'>Comments: %d &nbsp;Likes: %d</font>");
        private String message;

        private messages(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
