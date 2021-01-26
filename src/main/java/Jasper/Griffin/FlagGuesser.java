package Jasper.Griffin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class FlagGuesser extends ListenerAdapter {

    Random rand = new Random();
    String[] canvas = new String[54];
    String[] newCanvas = new String[63];
    HashSet<Integer> set = new HashSet<Integer>();
    GameData gd = new GameData();
    final int COUNTRY_NUMBER = 56;
    final int COUNTRY_NUMBER_V2 = 57;
    final int CANVAS_SIZE = 6;
    int counter = 0;

    String[] countries = { "France", "Germany", "Bulgaria", "Ukraine", "Belgium", "Sweden", "Denmark", "Finland",
            "Estonia", "Italy", "Poland", "Indonesia", "Monaco", "Austria", "Netherlands", "Russia", "Ireland",
            "Bangladesh", "Greece", "Trinidad and Tobago", "Greenland", "Palau", "Armenia", "Bahrain", "Benin",
            "Bolivia", "Thailand", "Ivory Coast", "Gabon", "Guinea", "Hungary", "Kuwait", "Liechtenstein", "Lithuania",
            "Madagascar", "Peru", "Chile", "Bosnia", "Antigua and Barbuda", "Luxembourg", "Latvia", "Liberia",
            "Maldives", "Mali", "Nigeria", "Togo", "China", "Qatar", "Lebanon", "Lesotho", "Azerbaijan", "Egypt",
            "India", "Yemen", "Zambia", "Paraguay", "Tajikistan", /*56*/

            "Norway", "Iceland", "Japan", "Cameroon", "Uruguay", "Jamaica", "Dominica", "South Africa",
            "Sao Tome and Principe", "Argentina", "The Bahamas", "Botswana", "Cape Verde", "Central African Republic",
            "Colombia", "Democratic Republic of the Congo", "Namibia", "Equatorial Guinea", "Gambia", "Grenada",
            "Honduras", "USA", "Scotland", "Dominican Republic", "El Salvador", "Ghana", "Switzerland", "Djibouti",
            "Iran", "Czech Republic", "Mexico", "Syria", "North Korea", "Philippines", "Suriname", "Tanzania",
            "Burkina Faso", "Guatemala", "Senegal", "Cuba", "Haiti", "Congo", "Rwanda", "Zimbabwe", "Sudan",
            "Jordan"};

    String[] countryCode = { ":flag_fr:", ":flag_de:", ":flag_bg:", ":flag_ua:", ":flag_be:", ":flag_se:", ":flag_dk:",
            ":flag_fi:", ":flag_ee:", ":flag_it:", ":flag_pl:", ":flag_id:", ":flag_mc:", ":flag_at:", ":flag_nl:",
            ":flag_ru:", ":flag_ie:", ":flag_bd:", ":flag_gr:", ":flag_tt:", ":flag_gl:", ":flag_pw:", ":flag_am:",
            ":flag_bh:", ":flag_bj:", ":flag_bo:", ":flag_th:", ":flag_ci:", ":flag_ga:", ":flag_gn:", ":flag_hu:",
            ":flag_kw:", ":flag_li:", ":flag_lt:", ":flag_mg:", ":flag_pe:", ":flag_cl:", ":flag_ba:", ":flag_ag:",
            ":flag_lu:", ":flag_lv:", ":flag_lr:", ":flag_mv:", ":flag_ml:", ":flag_ng:", ":flag_tg:", ":flag_cn:",
            ":flag_qa:", ":flag_lb:", ":flag_ls:", ":flag_az:", ":flag_eg:", ":flag_in:", ":flag_ye:", ":flag_zm:",
            ":flag_py:", ":flag_tj:", /*56*/

            ":flag_no:", ":flag_is:", ":flag_jp:", ":flag_cm:", ":flag_uy:", ":flag_jm:", ":flag_dm:", ":flag_za:",
            ":flag_st:", ":flag_ar:", ":flag_bs:", ":flag_bw:", ":flag_cv:", ":flag_cf:", ":flag_co:", ":flag_cd:",
            ":flag_na:", ":flag_gq:", ":flag_gm:", ":flag_gd:", ":flag_hn:", ":flag_us:", ":scotwhy:", ":flag_do:",
            ":flag_sv:", ":flag_gh:", ":flag_ch:", ":flag_dj:", ":flag_ir:", ":flag_cz:", ":flag_mx:", ":flag_sy:",
            ":flag_kp:", ":flag_ph:", ":flag_sr:", ":flag_tz:", ":flag_bf:", ":flag_gt:", ":flag_sn:", ":flag_cu:",
            ":flag_ht:", ":flag_cg:", ":flag_rw:", ":flag_zw:", ":flag_sd:", ":flag_jo:"};

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String serverId = event.getGuild().getId();

        String[] userMsg = event.getMessage().getContentRaw().split("\\s+");
        String twoLetterCountry = event.getMessage().getContentRaw();

        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder builder = new StringBuilder();

        int serverCount = event.getJDA().getGuilds().size();
        List<Guild> servers = event.getJDA().getGuilds();

        if (userMsg[0].equalsIgnoreCase(Main.prefix + "flagguesser")) {

            // gets the id of me!
            User owner = event.getJDA().getUserById("211959602370183168");

            embed.setTitle(":flag_ch: Flag guessing game! :flag_de:");
            embed.setDescription(
                    "A minigame that generates a country for you where you need to guess by its flag! \r\n\n"
                            + "**!generate , !g** - randomly generates a country for you where you start off with a blank canvas\r\n"
                            + "**!next , !n** - reveals each emoji of the flag a few tiles at a time\r\n"
                            + "**![country]** - is used to guess the country\r\n"
                            + "**!reset , !r** - is used to reveal the answer\n\n"
                            + "If you're interested in inviting this bot to your server you can follow this link\r\nhttps://discordbots.org/bot/591660304992174080");
            embed.setColor(0x00B4FF);
            embed.setFooter("Created by Jappy", owner.getAvatarUrl());

            event.getChannel().sendMessage(embed.build()).queue();
            embed.clear();
            // have info about generate, next, guess flag and reset
        }

        if (userMsg[0].equalsIgnoreCase(Main.prefix + "generate") || userMsg[0].equalsIgnoreCase(Main.prefix + "g")) {

            serverId = event.getGuild().getId();
            gd.booleanData.put(serverId, true);
            set.clear();

            int number = rand.nextInt(countries.length);
            String country = countries[number];

            if (gd.counterData.get(serverId) == null) {
                counter = 0;
            }

            // return array of canvas and country
            onCountryReceived(event, country, number);

            embed.setTitle(" Guess the flag!");
            embed.setColor(0x00B4FF);

            for (String value : gd.canvasData.get(serverId)) {
                builder.append(value);
            }
            String text = builder.toString();
            embed.setDescription(text);
            event.getChannel().sendMessage(embed.build()).queue();
            embed.clear();

            gd.countryData.put(serverId, country);
            gd.counterData.put(serverId, counter);
            gd.numberData.put(serverId, number);
        }

        if (userMsg[0].equalsIgnoreCase(Main.prefix + "next") || userMsg[0].equalsIgnoreCase(Main.prefix + "n") && gd.numberData.get(serverId) != null) {

            serverId = event.getGuild().getId();

            if (gd.booleanData.get(serverId) == false || gd.counterData.get(serverId) > 8) {
                // do nothing
            } else {

                if (gd.numberData.get(serverId) > COUNTRY_NUMBER) {

                    onNewCanvasReceived(event, gd.countryData.get(serverId), gd.canvasData.get(serverId), gd.numberData.get(serverId)); // (event, country, newCanvas, number);

                    // printing canvas (not making it)
                    for (String value : gd.canvasData.get(serverId)) {
                        builder.append(value);
                    }
                    String text = builder.toString();

                    embed.setTitle(" Guess the flag!");
                    embed.setColor(0x00B4FF);
                    embed.setDescription(text);
                    event.getChannel().sendMessage(embed.build()).queue();
                    embed.clear();
                }

                else {

                    onCanvasReceived(event, gd.countryData.get(serverId), gd.canvasData.get(serverId),
                            gd.numberData.get(serverId));

                    for (String value : gd.canvasData.get(serverId)) {
                        builder.append(value);
                    }
                    String text = builder.toString();
                    embed.setTitle(" Guess the flag!");
                    embed.setColor(0x00B4FF);
                    embed.setDescription(text);
                    event.getChannel().sendMessage(embed.build()).queue();
                    embed.clear();
                }
            }
        }

        if (userMsg[0].equalsIgnoreCase(Main.prefix + gd.countryData.get(serverId)) || twoLetterCountry.equalsIgnoreCase(Main.prefix + gd.countryData.get(serverId))) {

            String s = "";
            embed.setTitle(gd.countryData.get(serverId) + " " + countryCode[gd.numberData.get(serverId)]);
            embed.setColor(0x77B254);

            if (gd.numberData.get(serverId) > COUNTRY_NUMBER) {
                for (int i = 0; i < newCanvas.length; i++) {
                    s += Data.getFlag2().get(gd.numberData.get(serverId) - COUNTRY_NUMBER_V2).get(i + 1);
                }
            } else {
                for (int i = 0; i < canvas.length; i++) {
                    s += Data.getFlag().get(gd.numberData.get(serverId)).get(i + 1);
                }
            }

            event.getChannel().sendTyping().queue();
            embed.setDescription(s);
            event.getChannel().sendMessage(event.getMember().getAsMention() + " is correct! ✅").queue();
            event.getChannel().sendMessage(embed.build()).queue();
            embed.clear();
            removeData(serverId);
            return;
        }

        if (userMsg[0].equalsIgnoreCase(Main.prefix + "reset") || userMsg[0].equalsIgnoreCase(Main.prefix + "r")) {

            serverId = event.getGuild().getId();

            if (gd.booleanData.get(serverId) == false) {
                // do nothing
            }

            else {

                String s = "";
                if (gd.numberData.get(serverId) > COUNTRY_NUMBER) {
                    for (int i = 0; i < newCanvas.length; i++) {
                        //prints 7x9 flag
                        s += Data.getFlag2().get(gd.numberData.get(serverId) - COUNTRY_NUMBER_V2).get(i + 1);
                    }
                } else {
                    for (int i = 0; i < canvas.length; i++) {
                        s += Data.getFlag().get(gd.numberData.get(serverId)).get(i + 1);
                    }
                }

                event.getChannel().sendMessage("The game has been reset. The flag was...").queue();

                embed.setTitle(gd.countryData.get(serverId) + " " + countryCode[gd.numberData.get(serverId)]);

                embed.setDescription(s);
                embed.setColor(0xBF1931);
                event.getChannel().sendMessage(embed.build()).queue();
                embed.clear();
                removeData(serverId);
                return;
            }
        }

        if (userMsg[0].equalsIgnoreCase(Main.prefix + "servercount")) {
            embed.setTitle("server count = " + serverCount);
            embed.setDescription("" + servers.get(220));
            event.getChannel().sendMessage(embed.build()).queue();
        }

        if (userMsg[0].equalsIgnoreCase(Main.prefix + "printforme")) {
            int num = Integer.parseInt(userMsg[1]);
            //printFlag(event, num);
        }

    }

    // !generate
    public void onCountryReceived(GuildMessageReceivedEvent event, String country, int number) {

        // generates a default canvas
        String serverId = event.getGuild().getId();
        gd.countryData.put(serverId, country);

        String[] canvas = new String[54];

        if (number < COUNTRY_NUMBER + 1) {

            for (int i = 0; i < canvas.length; i++) {
                canvas[i] = Data.getDefault().get(0).get(i + 1);
            }
            gd.canvasData.put(serverId, canvas);
        }
        else {

            for (int i = 0; i < newCanvas.length; i++) {
                newCanvas[i] = Data.getDefault().get(1).get(i + 1);
            }
            gd.canvasData.put(serverId, newCanvas);
        }
    }

    // !next
    public void onCanvasReceived(GuildMessageReceivedEvent event, String country, String[] canvas, int number) {

        String serverId = event.getGuild().getId();
        canvas = new String[54];
        canvas = gd.canvasData.get(serverId);

        if (gd.setData.get(serverId) == null) {
            set = new HashSet<Integer>();
        } else {
            set = new HashSet<Integer>(Arrays.asList(gd.setData.get(serverId)));
        }

        int i = 0;
        while (i < CANVAS_SIZE) {

            int val = rand.nextInt(canvas.length);

            if (set.contains(val)) {
                continue;
            } else {
                canvas[val] = Data.getFlag().get(gd.numberData.get(serverId)).get(val + 1);
                set.add(val);
                i++;
            }
        }

        Integer[] arr = set.toArray(new Integer[set.size()]);
        gd.setData.put(serverId, arr);
        gd.canvasData.put(serverId, canvas);
        gd.counterData.put(serverId, gd.counterData.get(serverId) + 1);
    }

    // !next
    public void onNewCanvasReceived(GuildMessageReceivedEvent event, String country, String[] newCanvas, int number) {

        String serverId = event.getGuild().getId();
        newCanvas = new String[63];
        newCanvas = gd.canvasData.get(serverId);

        if (gd.setData.get(serverId) == null) {
            set = new HashSet<Integer>();
        }
        else {
            set = new HashSet<Integer>(Arrays.asList(gd.setData.get(serverId)));
        }

        int i = 0;
        while (i < CANVAS_SIZE + 1) {

            int val = rand.nextInt(newCanvas.length);

            if (set.contains(val)) {
                continue;
            } else {
                newCanvas[val] = Data.getFlag2().get(gd.numberData.get(serverId) - COUNTRY_NUMBER_V2).get(val + 1);
                set.add(val);
                i++;
            }
        }

        Integer[] arr = set.toArray(new Integer[set.size()]);
        gd.setData.put(serverId, arr);
        gd.canvasData.put(serverId, newCanvas);
        gd.counterData.put(serverId, gd.counterData.get(serverId) + 1);
    }

    public void removeData(String serverId) {

        gd.canvasData.remove(serverId);
        gd.countryData.remove(serverId);
        gd.numberData.remove(serverId);
        gd.booleanData.remove(serverId);
        gd.setData.remove(serverId);

        set.clear();
        counter = 0;
        gd.booleanData.put(serverId, false);
    }

    public void printFlag(GuildMessageReceivedEvent event, int num) {

        String s = "";

        if (num > COUNTRY_NUMBER) {
            for (int i = 0; i < newCanvas.length; i++) {
                //prints 7x9 flag
                s += Data.getFlag2().get(num - COUNTRY_NUMBER_V2).get(i + 1);
            }
        } else {
            for (int i = 0; i < canvas.length; i++) {
                s += Data.getFlag().get(num).get(i + 1);
            }
        }

        event.getChannel().sendMessage(s).queue();
    }
}
