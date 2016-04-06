import java.util.NoSuchElementException;

public class Node<T>
{
  private T item;
  private Node<T> next;

  public Node(T obj)
  {
    this.item = obj;
    this.next = null;
  }

  public Node(T obj, Node<T> next)
  {
    this.item = obj;
    this.next = next;
  }

  public T getItem()
  {
    return item;
  }

  public void setItem(T item)
  {
    this.item = item;
  }

  public Node<T> getNext()
  {
    return this.next;
  }

  public void setNext(Node<T> next)
  {
    this.next = next;
  }

  public boolean hasNext()
  {
    return next != null;
  }

  public void insertNext(T obj)
  {
    Node<T> newNode = new Node<T>(obj, next);
    this.next = newNode;
  }

  public void removeNext()
  {
    if (this.next == null)
      throw new NoSuchElementException();

    this.next = this.next.next;
  }
}
