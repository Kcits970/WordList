package util;

public class MyTimer {
    private long markedTime;

    public void mark() {
        markedTime = System.currentTimeMillis();
    }

    public boolean isMarked() {
        if (markedTime == 0)
            return false;
        else
            return true;
    }

    public long getElapsedMillis() {
        return System.currentTimeMillis() - markedTime;
    }

    public double getElapsedSecond() {
        return (double) getElapsedMillis() / 1000;
    }
}
