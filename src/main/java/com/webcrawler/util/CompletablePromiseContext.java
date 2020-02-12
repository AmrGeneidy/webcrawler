package com.webcrawler.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CompletablePromiseContext {
    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static void schedule(Runnable r) {
        SERVICE.schedule(r, 1, TimeUnit.MILLISECONDS);
    }
}