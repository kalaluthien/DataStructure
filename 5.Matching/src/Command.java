import java.util.*;
import java.io.*;

public interface Command
{
  void apply(Database db) throws Exception;
}

class InitCmd
  implements Command
{
  final String arg;

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
    while (line != null)
    {
      addLine(db, line);
      line = br.readLine();
    }
  }

  private void addLine(Database db, String line)
    throws Exception
  {
    for (int i = 0; i <= line.length() - Matching.SAMPLING_LENGTH; i++)
    {
      String subs = line.substring(i, i + Matching.SAMPLING_LENGTH);
      db.insert(subs);
    }
  }
}

class PrintCmd
  implements Command
{
  final int arg;

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
  final String arg;

  public SearchCmd(String arg)
  {
    this.arg = arg;
  }

  @Override
  public void apply(Database db)
    throws Exception
  {
    /* TODO */
  }
}
