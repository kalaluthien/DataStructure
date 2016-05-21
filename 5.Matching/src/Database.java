import java.util.*;

public class Database
{
  private static final int TABLE_SIZE = 100;

  private SearchTree[] table;

  public Database()
  {
    table = new SearchTree[TABLE_SIZE];
  }

  public void initTable()
  {
    for (int i = 0; i < TABLE_SIZE; i++)
      table[i] = new SearchTree();
  }

  public void insert(String input, Position pos)
    throws Exception
  {
    if (input.length() != Matching.SAMPLING_LENGTH)
      throw new Exception(input + ": sampling length mismatch.");

    table[hash(input)].insert(input, pos);
  }

  public void print(int index)
  {
    if (table[index].isEmpty())
      System.out.println("EMPTY");

    table[index].print();
  }

  public List<Position> search(String input)
    throws Exception
  {
    if (input.length() != Matching.SAMPLING_LENGTH)
      throw new Exception(input + ": sampling length mismatch.");

    return table[hash(input)].search(input);
  }

  private static int hash(String input)
  {
    int sum = 0;

    for (int i = 0; i < input.length(); i++)
      sum += (int) input.charAt(i);

    return sum % TABLE_SIZE;
  }
}
