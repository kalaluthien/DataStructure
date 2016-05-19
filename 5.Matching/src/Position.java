import java.util.*;

public class Position
  implements Comparable<Position>
{
  private final int line;
  private final int pos;

  public Position(int line, int pos)
  {
    this.line = line;
    this.pos = pos;
  }

  @Override
  public String toString()
  {
    return '(' + line + ", " + pos + ')';
  }

  @Override
  public int compareTo(Position other)
  {
    if (this.line < other.line)
      return -1;
    else if (this.line > other.line)
      return 1;
    else if (this.pos < other.pos)
      return -1;
    else if (this.pos > other.pos)
      return 1;
    else
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

    if (this.line == other.line && this.pos == other.pos)
      return true;

    return false;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;

    result = prime * result + line;
    result = prime * result + pos;

    return result;
  }
}
