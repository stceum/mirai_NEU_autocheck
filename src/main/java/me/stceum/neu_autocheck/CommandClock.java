package me.stceum.neu_autocheck;

import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.Map;
import java.util.function.Consumer;

public class CommandClock implements Consumer<GroupMessageEvent> {
    @Override
    public void accept(GroupMessageEvent event) {
        // test this
        if (event.getMessage().contentToString().startsWith("!?")) {
            event.getGroup().sendMessage("?! neu auto check");
        }
        if (event.getMessage().contentToString().startsWith("!dk")) {
            String help_info = "Commands: \n" +
                    "1. help - show this info\n" +
                    "2. list - display all accounts added\n" +
                    "3. add - use as \" add 20180000 abcdefg\", the password should be encoded by base64\n" +
                    "4. update - the usage is similar to the command \"add\"\n" +
                    "5. remove - use as \" remove 20180000\"\n" +
                    "6. run-all - check out all of the accounts\n" +
                    "7. run - use as \"run 20180000 20180001 ...\"";
            Map<String, String> lists = AutoCheckConfig.INSTANCE.getIdPasswdLists();
            String[] dkPhrases = event.getMessage().contentToString().split(" ");
            if (dkPhrases.length == 1) {
                event.getGroup().sendMessage(help_info);
            } else {
                switch (dkPhrases[1]) {
                    case "list":
                        StringBuilder idLists = new StringBuilder("Saved StuId:");
                        for (String key : AutoCheckConfig.INSTANCE.getIdPasswdLists().keySet()) {
                            idLists.append("\n").append(key);
                        }
                        event.getGroup().sendMessage(idLists.toString());
                        break;
                    case "add":
                        if (dkPhrases.length != 4) {
                            event.getGroup().sendMessage("Error: Two parameters needed, while " +
                                    Integer.toString(dkPhrases.length - 2) + " provided.");
                        } else if (lists.containsKey(dkPhrases[2])) {
                            event.getGroup().sendMessage(dkPhrases[2] + " exists.");
                        } else {
                            lists.put(dkPhrases[2].trim(), dkPhrases[3].trim());
                            AutoCheckConfig.INSTANCE.setIdPasswdLists(lists);
                            event.getGroup().sendMessage(dkPhrases[2].trim() + " added.");
                        }
                        break;
                    case "update":
                        if (dkPhrases.length != 4) {
                            event.getGroup().sendMessage("Error: Two parameters needed, while " +
                                    Integer.toString(dkPhrases.length - 2) + " provided.");
                        } else {
                            if (!lists.containsKey(dkPhrases[2])) {
                                event.getGroup().sendMessage("Error: " + dkPhrases[2] + " DO NOT exist.");
                            } else {
                                lists.replace(dkPhrases[2].trim(), dkPhrases[3].trim());
                                AutoCheckConfig.INSTANCE.setIdPasswdLists(lists);
                                event.getGroup().sendMessage(dkPhrases[2].trim() + " updated.");
                            }
                        }
                        break;
                    case "remove":
                        if (dkPhrases.length != 3) {
                            event.getGroup().sendMessage("Error: One parameters needed, while " +
                                    Integer.toString(dkPhrases.length - 2) + " provided.");
                        } else {
                            if (!lists.containsKey(dkPhrases[2])) {
                                event.getGroup().sendMessage("Error: " + dkPhrases[2] + " DO NOT exist.");
                            } else {
                                lists.remove(dkPhrases[2]);
                                AutoCheckConfig.INSTANCE.setIdPasswdLists(lists);
                                event.getGroup().sendMessage(dkPhrases[2].trim() + " removed.");
                            }
                        }
                        break;
                    case "run-all":
                        event.getGroup().sendMessage("Copy that. Starting......");
                        StringBuilder report = new StringBuilder("Result:");
                        if (lists.size() > 0) {
                            for (String key : lists.keySet()) {
                                report.append("\n").append(new NEUAutoCheck().check(key, lists.get(key).trim()));
                            }
                        }
                        event.getGroup().sendMessage(report.toString());
                        break;
                    case "run":
                        if (dkPhrases.length < 3) {
                            event.getGroup().sendMessage("More than one parameters needed, while 0 provided.");
                        } else {
                            event.getGroup().sendMessage("Copy that. Starting......");
                            StringBuilder report_single = new StringBuilder("Result: ");
                            for (int i = 2; i < dkPhrases.length; i++) {
                                if (lists.keySet().contains(dkPhrases[i].trim())) {
                                    report_single.append("\n").append(new NEUAutoCheck().check(dkPhrases[i].trim(),
                                            lists.get(dkPhrases[i]).trim()));
                                } else {
                                    report_single.append("\n").append(dkPhrases[i]).append(" not exist.");
                                }
                            }
                            event.getGroup().sendMessage(report_single.toString());
                        }
                        break;
                    case "help":
                    default:
                        event.getGroup().sendMessage(help_info);
                }
            }
        }

//        if (event.getMessage().contentToString().equals("/dk")) {
//            event.getGroup().sendMessage("Copy That. Starting......");
//            String report = "Result:";
//            Map<String, String> lists = AutoCheckConfig.INSTANCE.getIdPasswdLists();
//            for (String key : lists.keySet()) {
//                report += ("\n" + new NEUAutoCheck().check(key, lists.get(key).trim()));
//            }
//            event.getGroup().sendMessage(report);
//        } else if (event.getMessage().contentToString().equals("/dk test")) {
//            Map<String, String> lists = AutoCheckConfig.INSTANCE.getIdPasswdLists();
//            event.getGroup().sendMessage(lists.get("20180000"));
//            AutoCheckConfig.INSTANCE.getIdPasswdLists().replace("20180000", "testtesttest");
//
//            PluginMain.INSTANCE.reloadPluginConfig(AutoCheckConfig.INSTANCE);
//            event.getGroup().sendMessage(AutoCheckConfig.INSTANCE.getIdPasswdLists().get("20180000"));
//        }


    }
}
