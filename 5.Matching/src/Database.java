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

  public void insert(String input)
    throws Exception
  {
    if (input.length() != Matching.SAMPLING_LENGTH)
      throw new Exception(input);

    table[hash(input)].insert(input);
  }

  public void print(int index)
  {
    if (table[index].isEmpty())
      System.out.println("EMPTY");

    table[index].print();
  }

  public void search(String pattern)
    throws Exception
  {
    if (pattern.length() < Matching.SAMPLING_LENGTH)
      throw new Exception(pattern);

    /* TODO */
  }

  private static int hash(String input)
  {
    int sum = 0;

    for (int i = 0; i < input.length(); i++)
      sum += (int) input.charAt(i);

    return sum % TABLE_SIZE;
  }
}
