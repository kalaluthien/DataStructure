
public interface ListInterface<T>
  extends Iterable<T>
{
  public boolean isEmpty();

  public int size();

  public void insert(T item);

  public void delete(T item);

  public T first();

  public void removeAll();
}
