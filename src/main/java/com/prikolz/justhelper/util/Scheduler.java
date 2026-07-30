package com.prikolz.justhelper.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler {
    private static volatile int id = 0;
    private static Map<Integer, Task> tasks = new ConcurrentHashMap<>();

    public static void tick() {
        tasks.forEach((k, v) -> {
            if (v.isCanceled) {
                tasks.remove(k);
                return;
            }
            if (v.expire-- <= 0) {
                tasks.remove(k);
                v.run.run();
                v.cancel();
            };
        });
    }

    public static Task runLater(int ticks, Runnable run) {
        int value = id + 1;
        id = value;
        var task = new Task(id, ticks, run);
        tasks.put(value, task);
        return task;
    }

    public static Task sync(Runnable run) { return runLater(0, run); }

    public static class Task {
        public final int id;
        public final Runnable run;
        public int expire;
        public volatile boolean isCanceled;

        public Task(int id, int expire, Runnable run) {
            this.id = id;
            this.run = run;
            this.expire = expire;
        }

        public void cancel() { isCanceled = true; }
    }
}
