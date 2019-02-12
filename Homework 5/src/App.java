import static java.lang.Math.*;
import static java.lang.System.*;
import static java.util.Arrays.*;


public class App
{

  private static final int size = 10000000;
  private static final int halfIndex = size / 2;
  private static float[] array = new float[size];
  private static long totalTime;
  private static Object lock = new Object();


  public static void main(String... args)
  {
	compArrValues();
	try
	{
	  compArrValuesWithThreads();
	}
	catch (InterruptedException e)
	{
	  e.printStackTrace();
	}
  }


  private static void compArrValues()
  {
	fill(array, 1);

	totalTime = 0;
	changeArray(array);

	out.println("compArrValues executing time: " + totalTime + "ms");
  }


  private static void changeArray(float[] array)
  {
	long startTime = currentTimeMillis();

	for (int i = 0; i < array.length; i++)
	{
	  array[i] = (float) (
			  array[i] * sin(0.2f + i / 5) * cos(0.2f + i / 5) * cos(0.4f + i / 2)
	  );
	}

	long finishTime = currentTimeMillis();

	synchronized (lock)
	{
	  totalTime += finishTime - startTime;
	}
  }


  private static void compArrValuesWithThreads()
  throws InterruptedException
  {
	fill(array, 1);

	totalTime = 0;

	long startTime = currentTimeMillis();
	float[] half1 = copyOfRange(array, 0, halfIndex);
	float[] half2 = copyOfRange(array, halfIndex, size);
	long finishTime = currentTimeMillis();

	totalTime += finishTime - startTime;

	Runnable changeHalf1 = () -> changeArray(half1);
	Runnable changeHalf2 = () -> changeArray(half2);

	Thread thr1 = new Thread(changeHalf1);
	Thread thr2 = new Thread(changeHalf2);
	thr1.start();
	thr2.start();
	thr1.join();
	thr2.join();

	startTime = currentTimeMillis();
	array = joinArrays(half1, half2);
	finishTime = currentTimeMillis();

	totalTime += finishTime - startTime;
	out.println("compArrValuesWithThreads executing time: " + totalTime + "ms");
  }


  private static float[] joinArrays(float[]... arrays)
  {
	int length = 0;
	for (float[] array : arrays)
	{
	  length += array.length;
	}

	final float[] result = new float[length];

	int offset = 0;
	for (float[] array : arrays)
	{
	  int len = array.length;
	  arraycopy(array, 0, result, offset, len);
	  offset += len;
	}

	return result;
  }

}