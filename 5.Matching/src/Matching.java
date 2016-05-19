import java.io.*;

public class Matching
{
  public static final int SAMPLING_LENGTH = 6;

  public static void main(String args[])
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Database db = new Database();

    while (true)
    {
      try
      {
        String input = br.readLine();
        if (input.compareTo("QUIT") == 0)
          break;

        excuteCommand(db, input);
      }
      catch (Exception e)
      {
        System.out.println("ERROR: " + e.toString());
      }
    }
  }

  private static void excuteCommand(Database db, String input)
    throws Exception
  {
    Command command = null;
    String arg = input.substring(2);

    if (input.startsWith("< "))
      command = new InitCmd(arg);
    else if (input.startsWith("@ "))
      command = new PrintCmd(arg);
    else if (input.startsWith("? "))
      command = new SearchCmd(arg);
    else
      throw new Exception(input);

    command.apply(db);
  }
}
