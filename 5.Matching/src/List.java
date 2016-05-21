import java.util.*;

public class List<T extends Comparable<T>>
  implements Comparable<List<T>>
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

  public Object[] toArray()
  {
    Object[] array = new Object[numItems];

    Node<T> curr = head;
    for (int i = 0; curr.hasNext(); i++)
    {
      curr = curr.getNext();
      array[i] = curr.getItem();
    }

    return array;
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

  @Override
  public int compareTo(List<T> other)
  {
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

    List<T> other = (List<T>) obj;

    if (this.head == other.head)
      return true;

    return false;
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
