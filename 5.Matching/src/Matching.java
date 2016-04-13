import java.io.*;

public class Matching
{
  public static void main(String args[])
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    while (true)
    {
      try
      {
        String input = br.readLine();
        if (input.compareTo("QUIT") == 0)
          break;

        command(input);
      }
      catch (IOException e)
      {
        System.out.println("ERROR: " + e.toString());
      }
    }
  }

  private static void command(String input)
  {
  }
}
