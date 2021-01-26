package Jasper.Griffin;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;
    public static String prefix = "!";

    public static void main(String[] args) throws LoginException {

        JDA jda = JDABuilder.createDefault("/*INSERT TOKEN HERE*/").build();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.playing("!flagguesser"));

        // event listeners
        //jda.addEventListener(new FlagGuesser());
    }


}
