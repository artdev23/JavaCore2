import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.*;
import static java.util.Arrays.*;


public class App
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


  public static void main(String... args)
  {
	Map<String,Long> unique = findUnique(words);

	for (String word : unique.keySet())
	{
	  long count = unique.get(word);
	  out.println(MessageFormat.format("{0}\t{1}", word, count));
	}
  }

}