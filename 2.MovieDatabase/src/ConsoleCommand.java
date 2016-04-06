import java.util.Arrays;

public interface ConsoleCommand
{
  void parse(String input) throws CommandParseException;

  void apply(MovieDB db) throws Exception;
}

abstract class AbstractConsoleCommand
  implements ConsoleCommand
{
  @Override
  public void parse(String input)
    throws CommandParseException
  {
    String[] args = input.split(" *% *%? *");
    if (input.isEmpty())
      args = new String[0];

    parseArguments(args);
  }

  protected abstract void parseArguments(String[] args)
    throws CommandParseException;
}

class DeleteCmd
  extends AbstractConsoleCommand
{
  private String genre;
  private String movie;

  public void parseArguments(String[] args)
    throws CommandParseException
  {
    if (args.length != 3)
      throw new CommandParseException(
          "DELETE", Arrays.toString(args), "insufficient argument");
    this.genre = args[1];
    this.movie = args[2];
  }

  @Override
  public void apply(MovieDB db)
    throws Exception
  {
    db.delete(new MovieDBItem(genre, movie));
  }
}

class InsertCmd
  extends AbstractConsoleCommand
{
  private String genre;
  private String movie;

  @Override
  protected void parseArguments(String[] args)
    throws CommandParseException
  {
    if (args.length != 3)
      throw new CommandParseException(
          "INSERT", Arrays.toString(args), "insufficient argument");
    this.genre = args[1];
    this.movie = args[2];
  }

  @Override
  public void apply(MovieDB db)
    throws Exception
  {
    db.insert(new MovieDBItem(genre, movie));
  }
}

class PrintCmd
  extends AbstractConsoleCommand
{
  @Override
  protected void parseArguments(String[] args)
    throws CommandParseException
  {
    if (args.length != 1)
      throw new CommandParseException(
          "PRINT", Arrays.toString(args), "unnecessary argument(s)");
  }

  @Override
  public void apply(MovieDB db)
    throws Exception
  {
    MyLinkedList<MovieDBItem> result = db.items();

    if (result.size() == 0)
      System.out.println("EMPTY");
    else
    {
      for (MovieDBItem item : result)
        System.out.printf("(%s, %s)\n", item.getGenre(), item.getTitle());
    }
  }
}

class SearchCmd
  extends AbstractConsoleCommand
{
  private String term;

  @Override
  protected void parseArguments(String[] args)
    throws CommandParseException
  {
    if (args.length != 2)
      throw new CommandParseException(
          "SEARCH", Arrays.toString(args), "insufficient argument");
    this.term = args[1];
  }

  @Override
  public void apply(MovieDB db)
    throws Exception
  {
    MyLinkedList<MovieDBItem> result = db.search(term);

    if (result.size() == 0)
      System.out.println("EMPTY");
    else
    {
      for (MovieDBItem item : result)
        System.out.printf("(%s, %s)\n", item.getGenre(), item.getTitle());
    }
  }
}

@SuppressWarnings("serial")
class ConsoleCommandException
  extends Exception
{
  public ConsoleCommandException(String msg)
  {
    super(msg);
  }

  public ConsoleCommandException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
}

@SuppressWarnings("serial")
class CommandParseException
  extends ConsoleCommandException
{
  private String command;
  private String input;

  public CommandParseException(String cmd, String input, String cause)
  {
    super(cause, null);
    this.command = cmd;
    this.input = input;
  }

  public String getCommand()
  {
    return command;
  }

  public String getInput()
  {
    return input;
  }

}

class CommandNotFoundException
  extends ConsoleCommandException
{
  private String command;

  public CommandNotFoundException(String command)
  {
    super(String.format("input command: %s", command));
    this.command = command;
  }

  private static final long serialVersionUID = 1L;

  public String getCommand()
  {
    return command;
  }
}
