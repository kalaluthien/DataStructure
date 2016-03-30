import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MovieDatabaseConsole
{
  public static void main(String args[])
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    MovieDB db = new MovieDB();

    String input = null;
    while (true)
    {
      try
      {
        input = br.readLine().trim();

        if (input.isEmpty())
          continue;

        if (input.toUpperCase().equals("QUIT"))
          break;

        ConsoleCommand command = parse(input);

        command.apply(db);

      }
      catch (CommandParseException e)
      {
        System.err.printf("command parse failure: %s [cmd=%s, input=%s]\n",
            e.getMessage(), e.getCommand(), e.getInput());

        e.printStackTrace(System.err);
      }
      catch (CommandNotFoundException e)
      {
        System.err.printf("command not found: %s\n", e.getCommand());
        e.printStackTrace(System.err);
      }
      catch (Exception e)
      {
        System.err.printf("unexpected exception with input: [%s]\n", input);
        e.printStackTrace(System.err);
      }
    }
  }

  private static ConsoleCommand parse(String input)
    throws Exception
  {
    ConsoleCommand command = null;

    if (input.startsWith("INSERT"))
      command = new InsertCmd();
    else if (input.startsWith("DELETE"))
      command = new DeleteCmd();
    else if (input.startsWith("SEARCH"))
      command = new SearchCmd();
    else if (input.startsWith("PRINT")) 
      command = new PrintCmd();
    else
      throw new CommandNotFoundException(input);

    command.parse(input);
    return command;
  }
}
