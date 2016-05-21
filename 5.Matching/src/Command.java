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
  private final int offset;
  private static final Position[] empty = new Position[0];

  public SearchCmd(String arg)
  {
    int sl = Matching.SAMPLING_LENGTH;
    int length = arg.length() / sl;
    offset = arg.length() % sl;
    args = new String[length + (offset > 0 ? 1 : 0)];

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
    Position[][] steps = new Position[args.length][];
    List<Position> result = new List<Position>();

    initSteps(steps, db);

    for (int i = 0; i < steps[0].length; i++)
    {
      Position curr = steps[0][i];
      boolean found = true;

      for (int level = 1; level < args.length; level++)
      {
        Position[] nexts = steps[level];
        found = false;

        int cposdiff;
        if (offset != 0 && level == args.length - 1)
          cposdiff = offset;
        else
          cposdiff = Matching.SAMPLING_LENGTH;

        for (int j = 0; j < nexts.length; j++)
          if (curr.neighbor(nexts[j], cposdiff))
          {
            found = true;
            curr = nexts[j];
            break;
          }

        if (!found)
          break;
      }

      if (found)
        result.insert(steps[0][i]);
    }

    result.print();
  }

  private void initSteps(Position[][] steps, Database db)
    throws Exception
  {
    for (int i = 0; i < args.length; i++)
    {
      List<Position> l = db.search(args[i]);

      if (l.isEmpty())
      {
        steps[i] = empty;
        continue;
      }

      steps[i] = new Position[l.size()];

      int j = 0;
      for (Position pos : l)
      {
        steps[i][j] = pos;
        j++;
      }
    }
  }
}
