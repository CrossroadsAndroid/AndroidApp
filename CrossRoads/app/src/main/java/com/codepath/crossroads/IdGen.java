package com.codepath.crossroads;

import java.util.Random;

/**
 * Created by ar on 10/18/14.
 */
public class IdGen {
    private static int nextId = new Random().nextInt();

    private static Object lock = new Object();

    public static String get() {
        synchronized (lock) {
            return String.valueOf(nextId++);
        }
    }
}
