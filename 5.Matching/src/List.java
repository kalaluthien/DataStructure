import java.util.*;

public class List<T extends Comparable<T>>
{
  Node<T> head;
  int numItems;

  public List()
  {
    head = new Node<T>(null);
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
    Node<T> curr = head;
    StringBuilder sb = new StringBuilder();

    while (curr.hasNext())
    {
      curr = curr.getNext();
      sb.append(' ' + curr.getItem().toString());
    }

    System.out.println(sb.toString().substring(1));
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
