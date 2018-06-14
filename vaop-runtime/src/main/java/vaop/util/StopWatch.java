package vaop.util;

public class StopWatch {

    private long startTime;
    private long endTime;
    private long elapsedTime;

    public StopWatch() {
    }

    private void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }

    public void start() {
        reset();
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        if (startTime != 0) {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
        } else {
            reset();
        }
    }

    /**
     * 返回花费的时间，单位是纳秒
     *
     * @return
     */
    public long getElapsedTime() {

        return elapsedTime;
    }
}