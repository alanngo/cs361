//package assignement2;

/**
 A class to measure time elapsed.
*/

public class StopWatch
{
    private long startTime;
    private long stopTime;

    public static final double MILLA_PER_SEC = 1000.0;

	/**
	 start the stop watch.
	*/
	public void start(){
		startTime = System.currentTimeMillis();
	}

	/**
	 stop the stop watch.
	*/
	public void stop()
	{	//stopTime = System.nanoTime();
	stopTime = System.currentTimeMillis();
	}

	/**
	elapsed time in seconds.
	@return the time recorded on the stopwatch in seconds
	*/
	public double time()
	{	return (stopTime - startTime); 	}

	public String toString(){
	    return "elapsed time: " + time() + " milliseconds.";
	}

	/**
	elapsed time in nanoseconds.
	@return the time recorded on the stopwatch in nanoseconds
	*/
	public long timeInNanoseconds()
	{	return (stopTime - startTime);	}
}
