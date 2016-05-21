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

    root = root.insert(input, pos);
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
    System.out.println(output);
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

  TreeNode leftChild, rightChild;
  int leftHeight, rightHeight;

  public TreeNode(String subs)
  {
    this.subs = subs;
    l = new List<Position>();
    leftChild = rightChild = null;
    leftHeight = rightHeight = 0;
  }

  public TreeNode insert(String subs, Position pos)
  {
    int comp = subs.compareTo(this.subs);

    if (comp < 0)
    {
      if (leftChild == null)
        leftChild = new TreeNode(subs);

      leftChild = leftChild.insert(subs, pos);
      leftHeight = leftChild.height();
    }
    else if (comp > 0)
    {
      if (rightChild == null)
        rightChild = new TreeNode(subs);

      rightChild = rightChild.insert(subs, pos);
      rightHeight = rightChild.height();
    }
    else
      l.insert(pos);

    return balanceTree();
  }

  public List<Position> search(String subs)
  {
    int comp = subs.compareTo(this.subs);

    if (comp < 0)
    {
      if (leftChild == null)
        return SearchTree.empty;

      return leftChild.search(subs);
    }
    else if (comp > 0)
    {
      if (rightChild == null)
        return SearchTree.empty;

      return rightChild.search(subs);
    }

    return l;
  }

  private TreeNode balanceTree()
  {
    TreeNode child;

    if (diffHeight() < 2)
      return this;

    if (leftHeight < rightHeight)
    {
      child = rightChild;

      if (child.leftHeight > child.rightHeight)
        rightChild = child.rightRotate();

      return leftRotate();
    }
    else
    {
      child = leftChild;

      if (child.rightHeight > child.leftHeight)
        leftChild = child.leftRotate();

      return rightRotate();
    }
  }

  private TreeNode leftRotate()
  {
    TreeNode root = rightChild;
    rightChild = root.leftChild;
    root.leftChild = this;

    rightHeight = root.leftHeight;
    root.leftHeight = height();

    return root;
  }

  private TreeNode rightRotate()
  {
    TreeNode root = leftChild;
    leftChild = root.rightChild;
    root.rightChild = this;

    leftHeight = root.rightHeight;
    root.rightHeight = height();

    return root;
  }

  private int diffHeight()
  {
    int diff = leftHeight - rightHeight;
    return diff < 0 ? -diff : diff;
  }

  private int height()
  {
    if (leftHeight < rightHeight)
      return rightHeight + 1;

    return leftHeight + 1;
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder(" " + subs);

    if (leftChild != null)
      result.append(leftChild.toString());

    if (rightChild != null)
      result.append(rightChild.toString());

    return result.toString();
  }
}
