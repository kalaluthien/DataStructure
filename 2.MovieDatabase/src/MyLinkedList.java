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
      last = last.getNext();
    }

    last.insertNext(item);
    numItems++;
  }

  @Override
  public void delete(T item)
  {
    Node<T> last = head;
    while (last.hasNext() && last.getNext().hasNext())
    {
      last = last.getNext();
    }

    last.removeNext();
    numItems--;
  }

  @Override
  public void removeAll()
  {
    head.setNext(null);
  }
}

class MyLinkedListIterator<T>
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
