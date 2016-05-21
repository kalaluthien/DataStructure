import java.util.*;

public class SearchTree
{
  public static final List<Position> empty = new List<Position>();

  TreeNode root;

  public SearchTree()
  {
    root = null;
  }

  public void insert(String input, Position pos)
  {
    if (isEmpty())
      root = new TreeNode(input);

    root.insert(input, pos);
  }

  public List<Position> search(String input)
  {
    if (isEmpty())
      return empty;

    return root.search(input);
  }

  public void print()
  {
    if (isEmpty())
      return;

    String output = root.toString().substring(1);
    //System.out.println(output);
    root.printTree("");
  }

  public boolean isEmpty()
  {
    return root == null;
  }
}

class TreeNode
{
  final String subs;
  List<Position> l;
  TreeNode left, right;

  public TreeNode(String subs)
  {
    this.subs = subs;
    l = new List<Position>();
    left = right = null;
  }

  public void insert(String subs, Position pos)
  {
    int comp = subs.compareTo(this.subs);

    if (comp < 0)
    {
      if (left == null)
        left = new TreeNode(subs);

      left.insert(subs, pos);
    }
    else if (comp > 0)
    {
      if (right == null)
        right = new TreeNode(subs);

      right.insert(subs, pos);
    }
    else
      l.insert(pos);
  }

  public List<Position> search(String subs)
  {
    int comp = subs.compareTo(this.subs);

    if (comp < 0)
    {
      if (left == null)
        return SearchTree.empty;

      return left.search(subs);
    }
    else if (comp > 0)
    {
      if (right == null)
        return SearchTree.empty;

      return right.search(subs);
    }

    return l;
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder(" " + subs);

    if (left != null)
      result.append(left.toString());

    if (right != null)
      result.append(right.toString());

    return result.toString();
  }

  /* DEBUG */
  public void printTree(String indent)
  {
    System.out.println(indent + '[' + subs + ']');

    if (left != null)
      left.printTree(indent + "L:  ");

    if (right != null)
      right.printTree(indent + "R:  ");
  }
}

















































