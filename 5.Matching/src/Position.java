import java.util.*;

public class Position
  implements Comparable<Position>
{
  private final int line;
  private final int cpos;

  public Position(int line, int cpos)
  {
    this.line = line;
    this.cpos = cpos;
  }

  public boolean neighbor(Position other, int offset)
  {
    boolean lineCond = this.line == other.line;
    boolean cposCond = (this.cpos + offset) == other.cpos;

    return lineCond && cposCond;
  }

  @Override
  public String toString()
  {
    return "(" + line + ", " + cpos + ")";
  }

  @Override
  public int compareTo(Position other)
  {
    if (this.line < other.line)
      return -1;
    else if (this.line > other.line)
      return 1;
    else if (this.cpos < other.cpos)
      return -1;
    else if (this.cpos > other.cpos)
      return 1;

    return 0;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    else if (obj == null)
      return false;
    else if (this.getClass() != obj.getClass())
      return false;

    Position other = (Position) obj;

    if (this.line == other.line && this.cpos == other.cpos)
      return true;

    return false;
  }
}
