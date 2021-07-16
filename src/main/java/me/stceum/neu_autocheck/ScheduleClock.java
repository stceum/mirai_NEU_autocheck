package me.stceum.neu_autocheck;

import net.mamoe.mirai.Bot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleClock {
    ScheduleClock() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay  = getTimeMillis(AutoCheckConfig.INSTANCE.getCheckTime()) - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;

        executor.scheduleAtFixedRate(
                new CheckTask(),
                initDelay,
                oneDay,
                TimeUnit.MILLISECONDS);
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    class CheckTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Long id : AutoCheckConfig.INSTANCE.getTargetGroups()) {
                Bot.getInstance(AutoCheckConfig.INSTANCE.getBotQq()).getGroup(id).sendMessage("[NEU Auto Check]: Started");
            }
            StringBuilder report = new StringBuilder("Schedule Check Result:");
            Map<String, String> lists = AutoCheckConfig.INSTANCE.getIdPasswdLists();
            for (String key : lists.keySet()) {
                report.append("\n").append(new NEUAutoCheck().check(key, lists.get(key).trim()));
            }
            for (Long id : AutoCheckConfig.INSTANCE.getTargetGroups()) {
                Bot.getInstance(AutoCheckConfig.INSTANCE.getBotQq()).getGroup(id).sendMessage(report.toString());
            }
        }
    }
}
