import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.*;
import static java.util.Arrays.*;


public class UniqueWords
{

  private static String[] words = {
  		"class", "new", "public", "private", "interface", "new", "class",
		"extends", "public", "class", "implements", "protected", "private",
		"new", "interface", "extends", "private", "interface", "class"
		};


  private static Map<String,Long> findUnique(String[] words)
  {
    List<String> list = asList(words);
	Set<String> set = new HashSet<>(list);
	Map<String,Long> result = new HashMap<>();

	for (String word : set)
	{
	  long cnt = list.stream()
					 .filter(x -> x.equalsIgnoreCase(word))
					 .count();

	  result.put(word, cnt);
	}

	return result;
  }


  private static void print(Map<String, Long> words)
  {
	for (String word : words.keySet())
	{
	  long count = words.get(word);
	  out.println(MessageFormat.format(
	  				"Слово: {0} \t\t Кол-во вхождений: {1}", word, count));
	}
  }


  public static void main(String... args)
  {
	print(findUnique(words));
  }

}