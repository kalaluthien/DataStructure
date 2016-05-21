import java.util.*;

public class List<T extends Comparable<T>>
  implements Iterable<T>
{
  Node<T> head;
  int numItems;

  public List()
  {
    head = new Node<T>(null);
  }

  public final Iterator<T> iterator()
  {
    return new ListIterator<T>(this);
  }

  public boolean isEmpty()
  {
    return head.getNext() == null;
  }

  public int size()
  {
    return numItems;
  }

  public void insert(T item)
  {
    Node<T> curr = head;

    while (curr.hasNext())
    {
      T nextItem = curr.getNext().getItem();

      if (item.compareTo(nextItem) < 0)
        break;
      else if (item.equals(nextItem))
        return;

      curr = curr.getNext();
    }

    curr.insertNext(item);
    numItems++;
  }

  public void print()
  {
    if (isEmpty())
    {
      System.out.println("(0, 0)");
      return;
    }

    Node<T> curr = head;
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;

    while (curr.hasNext())
    {
      curr = curr.getNext();

      if (isFirst)
        isFirst = false;
      else
        sb.append(" ");

      sb.append(curr.getItem());
    }

    System.out.println(sb);
  }
}

class ListIterator<T extends Comparable<T>>
  implements Iterator<T>
{
  private Node<T> curr;

  public ListIterator(List<T> list)
  {
    this.curr = list.head;
  }

  @Override
  public boolean hasNext()
  {
    return curr.hasNext();
  }

  @Override
  public T next()
  {
    if (!hasNext())
      return null;

    curr = curr.getNext();

    return curr.getItem();
  }

  @Override
  public void remove()
  {
  }
}

class Node<T extends Comparable<T>>
{
  final T item;
  Node<T> next;

  public Node(T item)
  {
    this.item = item;
    this.next = null;
  }

  public Node(T item, Node<T> next)
  {
    this.item = item;
    this.next = next;
  }

  public T getItem()
  {
    return item;
  }

  public boolean hasItem()
  {
    return item != null;
  }

  public Node<T> getNext()
  {
    return next;
  }

  public void insertNext(T item)
  {
    Node<T> next = new Node<T>(item, this.next);
    this.next = next;
  }

  public boolean hasNext()
  {
    return next != null;
  }
}
