import java.util.*;


public class Phonebook
{

  private Map<String, Set<String>> numbers;


  public Phonebook()
  {
	numbers = new HashMap<>();
  }


  public void add(String surname, String number)
  {
    if(surname == null || number == null)
      throw new IllegalArgumentException("Arguments has null value");

	surname = surname.trim();
	number = number.trim();

	if(surname.isEmpty() || number.isEmpty())
	  throw new IllegalArgumentException("Arguments has empty value");

	if (numbers.containsKey(surname))
	{
	  numbers.get(surname).add(number);
	  return;
	}

	Set<String> set = new HashSet<>();
	set.add(number);

	numbers.put(surname, set);
  }


  public Set<String> get(String surname)
  {
	if(surname == null)
	  throw new IllegalArgumentException("Argument has null value");

	return numbers.get(surname);
  }

}