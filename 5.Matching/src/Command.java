import java.util.*;
import java.io.*;

public interface Command
{
  void apply(Database db) throws Exception;
}

class InitCmd
  implements Command
{
  private final String arg;

  public InitCmd(String arg)
  {
    this.arg = arg.trim();
  }

  @Override
  public void apply(Database db)
    throws Exception
  {
    FileReader fr = new FileReader(arg);
    BufferedReader br = new BufferedReader(fr);

    db.initTable();

    String line = br.readLine();
    for (int i = 1; line != null; i++)
    {
      addLine(db, line, i);
      line = br.readLine();
    }
  }

  private void addLine(Database db, String line, int lineNum)
    throws Exception
  {
    for (int i = 0; i <= line.length() - Matching.SAMPLING_LENGTH; i++)
    {
      String subs = line.substring(i, i + Matching.SAMPLING_LENGTH);
      db.insert(subs, new Position(lineNum, i + 1));
    }
  }
}

class PrintCmd
  implements Command
{
  private final int arg;

  public PrintCmd(String arg)
  {
    this.arg = Integer.parseInt(arg.trim());
  }

  @Override
  public void apply(Database db)
    throws Exception
  {
    db.print(arg);
  }
}

class SearchCmd
  implements Command
{
  private String[] args;

  public SearchCmd(String arg)
  {
    int length = arg.length() / Matching.SAMPLING_LENGTH;
    int offset = arg.length() % Matching.SAMPLING_LENGTH;

    args = new String[length + (offset > 0 ? 1 : 0)];
    splitArg(arg, length, offset);
  }

  private void splitArg(String arg, int length, int offset)
  {
    int sl = Matching.SAMPLING_LENGTH;

    for (int i = 0; i < length; i++)
    {
      String subs = arg.substring(i * sl, i * sl + sl);
      args[i] = subs;
    }
    if (offset > 0)
    {
      int start = length * sl - sl + offset;
      args[length] = arg.substring(start, start + sl);
    }
  }

  @Override
  public void apply(Database db)
    throws Exception
  {
    List<List<Position>> l = new List<List<Position>>();

    for (int i = 0; i < args.length; i++)
      l.insert(db.search(args[0]));
  }
}















