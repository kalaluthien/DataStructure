import java.util.Iterator;
import java.util.NoSuchElementException;

public class MovieDB
{
  MyLinkedList l;

  public MovieDB()
  {
    l = new MyLinkedList<MovieDBItem>();
  }

  public void insert(MovieDBItem item)
  {
  }

  public void delete(MovieDBItem item)
  {
  }

  public MyLinkedList<MovieDBItem> search(String term)
  {
    return null;
  }

  public MyLinkedList<MovieDBItem> items()
  {
    return null;
  }
}

class Genre
  extends Node<String> implements Comparable<Genre>
{
  public Genre(String name)
  {
    super(name);
  }

  @Override
  public int compareTo(Genre o)
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public int hashCode()
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public boolean equals(Object obj)
  {
    throw new UnsupportedOperationException("not implemented yet");
  }
}

class MovieList
  implements ListInterface<String>
{
  public MovieList()
  {

  }

  @Override
  public Iterator<String> iterator()
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public boolean isEmpty()
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public int size() 
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public void add(String item)
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public String first()
  {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override
  public void removeAll()
  {
    throw new UnsupportedOperationException("not implemented yet");
  }
}
