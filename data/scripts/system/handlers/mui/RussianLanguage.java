package mui;

import com.aionemu.gameserver.utils.mui.handlers.GeneralMuiHandler;
import com.aionemu.gameserver.utils.mui.handlers.MuiName;

@MuiName("ru")
public class RussianLanguage extends GeneralMuiHandler {

    @Override
    public String getMessage(String name, Object... params) {
        return String.format(messages.valueOf(name).getMessage(), params);
    }
    
    private static enum messages {

        HELLO_WORLD("Добро пожаловать в мир %s!"),
        HELLO_REMEMBER("Пожалуйста помните:"),
        HELLO_STAFF("Сотрудники %s никогда не спросят у вас пароль!"),
        HELLO_RULLES("Реклама сторонних серверов запрещена!"),
        HELLO_ANNOUNCE("Анонс:"),
        HELLO_ANNOUNCE_INFO1("Анонс 1"),
        HELLO_ANNOUNCE_INFO2("Анонс 2"),
        HELLO_ANNOUNCE_INFO3("Анонс 3"),
        HELLO_ENJOY("Приятного прибывания вам на %s."),
        MEMBERSHIP_LOGIN("Статус вашего аккаунта:\n"),
		MEMBERSHIP_STATUS(" %s"),
        MEMBERSHIP_PREMIUM("Premium"),
        MEMBERSHIP_VIP("VIP"),
        MEMBERSHIP_CRAFT("КритКрафт"),
        MEMBERSHIP_AP("Очки бездны"),
        MEMBERSHIP_COLLECTION("Ремесло"),
        MEMBERSHIP_EXPIRE("до: %s\n"),
        ANNOUNCE_RATES("[color:Рейты;0 1 0] [color:серве;0 1 0][color:ра:;0 1 0] "),
        ANNOUNCE_RATES_XP("[color:Опыт:;0 1 0] x %s"),
        ANNOUNCE_RATES_QS("[color:Квес;0 1 0][color:ты:;0 1 0] x %s"),
        ANNOUNCE_RATES_DR("[color:Дроп:;0 1 0] x %s"),
        ANNOUNCE_RATES_AP("[color:Ап в ;0 1 0][color:Пвп:;0 1 0] x %s"),
        MEMBERSHIP_WORLD_ANNOUNCE("%s вошел в игру."),
        ONLINE_TOLL("На ваш счет было зачислено %d Gpoint."),
        ONLINE_BONUS_TOLL("На ваш бонусный счет было зачислено %d Gpoint."),
		CHEST_KEY("Чтобы открыть ящик, нужен ключ."),
		PLAYER("Игроку "),
		BAN(" выдан бан"),
		ON(" на "),
		MINUTES(" минут!"),
		REASON(" Причина: "),
		GAVE(" Выдал: "),
		CHAR("Персонаж "),
		BAN1(" забанен на "),
		DAYS(" дней! Причина: "),
		BANCHAT(" выдан бан чата"),
		PLAYER1("Игрок "),
		PRISON(" отправлен в тюрьму на "),
		PRISON1("Вы в тюрьме!"),
		PRISON2("Вы не можете атаковать в тюрьме!"),
		PRISON3("Вы не можете использовать умения в тюрьме!"),
		PRISON4("Вы не можете использовать чат в тюрьме!"),
		PRISON5("Вы не можете приглашать в группу игроков в тюрьме!"),
		PRISON6("Вы не можете приглашать в альянс игроков в тюрьме!"),
		PRISON7("Вы не можете использовать снаряжение в тюрьме!"),
		PRISON8("Вы не можете использовать предметы в тюрьме!"),
		PRISON9("Вы отправлены в тюрьму на "),
		PRISON10(" минут.\n Если вы вышли из игры счетчик времени останавливается, и запускается при следующем входе в игру."),
		PRISON11("Вы вышли из тюрьмы."),
		PRISON12("Чат будет доступен после релога!"),
		PRISON13("Вам осталось отсидеть "),
		PRISON14(" минут"),
		PRISON15("ы"),
		PRISON16("Вы будете телепортированы в тюрьму через одну минуту!"),
		MINUTES1(" минут! Причина: "),
		PIG_EVENT_START("В Сарфане начался ивент <Свинки> , поспешите принять участие !"),
		PIG_EVENT_REWARD(" нашел кабанчика и получил [item:%d]."),
		PIG_EVENT_STOP("Ивент <Свинки> завершен, до следующих встреч ! Хрю- хрю! :)"),
		INVULNERABLE_GM_CONNECTION(">> Режим неуязвимости : ВКЛ <<"),
		INVISIBLE_GM_CONNECTION(">> Режим невидимости : ВКЛ <<"),
		NEUTRAL_GM_CONNECTION(">> Нейтральный режим : ВКЛ <<"),
		ENEMYTY_GM_CONNECTION(">> Режим <Все враги> : ВКЛ <<"),
		VISION_GM_CONNECTION(">> Вы видите невидимые цели <<"),
		WHISPER_GM_CONNECTION(">> Личные сообщения : ВЫКЛ <<");
        private String message;

        private messages(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
