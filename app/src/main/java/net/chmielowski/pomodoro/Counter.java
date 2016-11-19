package net.chmielowski.pomodoro;

import android.content.SharedPreferences;

class Counter {

    private final SharedPreferences mPrefs;

    public Counter(SharedPreferences preferences) {
        mPrefs = preferences;
    }

    long number() {
        return mPrefs.getLong("counter", 0);
    }

    void increment() {
        mPrefs.edit().putLong(
                "counter", number() + 1).apply();
    }
}
