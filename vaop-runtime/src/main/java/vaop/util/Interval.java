package vaop.util;

public class Interval {

    private long mStartTime;
    private long mEndTime;
    private long mElapsedTime;

    private void reset() {
        mStartTime = 0;
        mEndTime = 0;
        mElapsedTime = 0;
    }

    public void start() {
        reset();
        mStartTime = System.currentTimeMillis();
    }

    public void stop() {
        if (mStartTime != 0) {
            mEndTime = System.currentTimeMillis();
            mElapsedTime = mEndTime - mStartTime;
        } else {
            reset();
        }
    }

    /**
     * 返回花费的时间
     */
    public long getElapsedTime() {
        return mElapsedTime;
    }
}