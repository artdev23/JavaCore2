import static java.lang.System.*;


public class PhonebookTest
{

  public static void main(String... args)
  {
	Phonebook book = new Phonebook();

	book.add("Сталин", "79051234567");
	book.add("Сталин", "79057654321");
	book.add("Ленин", "7-999-00-00-666");
	book.add("Брежнев", "79057654321");
	book.add("Горбачев", "6-666-666-666-6");
	book.add("Ельцин", "0-000-000-00-00");
	book.add("Путин", "11111111111");
	book.add("Путин", "22222222222");
	book.add("Путин", "33333333333");
	book.add("Путин", "44444444444");
	book.add("Путин", "55555555555");
	book.add("Путин", "66666666666");
	book.add("Путин", "77777777777");
	book.add("Путин", "88888888888");
	book.add("Путин", "99999999999");

	out.println(book.get("Путин"));
	out.println(book.get("Путов"));
  }

}