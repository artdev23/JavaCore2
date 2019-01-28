import java.text.MessageFormat;

import static java.lang.System.out;


public class Application
{
  private static final byte RowCount = 4;
  private static final byte ColCount = 4;
  private static final String[][] matrix = {
		  {"1", "2", "3", "4", ""},
		  {"01", "02", "03", "04"},
		  {"+1", "+2", "+3", "+4"},
		  {"-1", "-2", "-3", "-4"},
		  };



  public static void main(String... args)
  {
	try
	{
	  long sum = getSum(matrix);

	  out.println("результат расчета: "+sum);
	}
	catch(MyArraySizeException | MyArrayDataException e)
	{
	  e.printStackTrace();
	}
  }


  private static long getSum(String[][] matrix)
  throws MyArraySizeException, MyArrayDataException
  {
	checkArraySize(matrix);

	long sum = 0L;
	int i = 0;
	int j = 0;
	try
	{
	  for(i=0; i<RowCount; i++)
		for(j=0; j<ColCount; j++)
		{
		  int num = Integer.parseInt(matrix[i][j]);
		  sum += num;
		}
	}
	catch(NumberFormatException e)
	{
	  throw new MyArrayDataException(i,j);
	}

	return sum;
  }


  private static void checkArraySize(String[][] matrix)
  throws MyArraySizeException
  {
	if (matrix.length != RowCount)
	  throw new MyArraySizeException();

	for(String[] row : matrix)
	{
	  if (row.length != ColCount)
		throw new MyArraySizeException();
	}
  }


  private static class MyArraySizeException
		  extends Exception
  {
	private static final String message = MessageFormat.format(
			"размер массива должен быть {0}х{1}",
			RowCount, ColCount);

	MyArraySizeException()
	{
	  super(message);
	}
  }

  private static class MyArrayDataException
		  extends Exception
  {
	private static final String message =
			"в ячейке [{0}][{1}] должна быть строка, содержащая целое число";

	MyArrayDataException(int rowIndex, int colIndex)
	{
	  super(MessageFormat.format(message, rowIndex, colIndex));
	}
  }

}