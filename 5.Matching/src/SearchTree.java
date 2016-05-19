import java.util.*;

public class SearchTree
{
  Tree root;

  public SearchTree()
  {
    root = null;
  }

  public void insert(String input)
  {
    System.out.println('[' + input + "] inserted.");
  }

  public void print()
  {
    System.out.println("EMPTY");
  }

  public boolean isEmpty()
  {
    return root == null;
  }
}

class Tree
{
  String item;
}
