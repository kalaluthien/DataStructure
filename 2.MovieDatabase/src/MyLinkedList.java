import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T extends Comparable<T>>
  implements ListInterface<T>
{
  Node<T> head;
  int numItems;

  public MyLinkedList()
  {
    head = new Node<T>(null);
  }

  public final Iterator<T> iterator()
  {
    return new MyLinkedListIterator<T>(this);
  }

  @Override
  public boolean isEmpty()
  {
    return head.getNext() == null;
  }

  @Override
  public int size()
  {
    return numItems;
  }

  @Override
  public T first()
  {
    return head.getNext().getItem();
  }

  @Override
  public void insert(T item)
  {
    Node<T> last = head;

    while (last.hasNext())
    {
      T nextItem = last.getNext().getItem();

      if (item.compareTo(nextItem) < 0)
        break;
      else if (item.equals(nextItem))
        return;

      last = last.getNext();
    }

    last.insertNext(item);
    numItems++;
  }

  @Override
  public void delete(T item)
  {
    Node<T> last = head;
    while (last.hasNext())
    {
      T nextItem = last.getNext().getItem();

      if (item.compareTo(nextItem) < 0)
        return;
      else if (item.equals(nextItem))
      {
        last.removeNext();
        numItems--;
        return;
      }

      last = last.getNext();
    }
  }

  @Override
  public void removeAll()
  {
    head.setNext(null);
  }

  public void print()
  {
    System.out.println();
    System.out.println("[List size] " + numItems);
    for (T item : this)
    {
      System.out.println("[" + item.toString() + "]");
    }
    System.out.println();
  }
}

class MyLinkedListIterator<T extends Comparable<T>>
  implements Iterator<T>
{
  private MyLinkedList<T> list;
  private Node<T> curr;
  private Node<T> prev;

  public MyLinkedListIterator(MyLinkedList<T> list)
  {
    this.list = list;
    this.curr = list.head;
    this.prev = null;
  }

  @Override
  public boolean hasNext()
  {
    return curr.getNext() != null;
  }

  @Override
  public T next()
  {
    if (!hasNext())
      throw new NoSuchElementException();

    prev = curr;
    curr = curr.getNext();

    return curr.getItem();
  }

  @Override
  public void remove()
  {
    if (prev == null)
      throw new IllegalStateException("next() should be called first");
    if (curr == null)
      throw new NoSuchElementException();

    prev.removeNext();
    list.numItems--;
    curr = prev;
    prev = null;
  }
}
