import java.util.Iterator;
import java.util.NoSuchElementException;

public class MovieDB
{
  private MyLinkedList<Genre> l;

  public MovieDB()
  {
    l = new MyLinkedList<Genre>();
  }

  public void insert(MovieDBItem item)
  {
    Genre targetGenre = new Genre(item.getGenre());
    boolean isNewGenre = true;

    for (Genre genre : l)
    {
      if (targetGenre.equals(genre))
      {
        genre.insert(item.getTitle());
        isNewGenre = false;
        break;
      }
    }

    if (isNewGenre)
    {
      targetGenre.insert(item.getTitle());
      l.insert(targetGenre);
    }
  }

  public void delete(MovieDBItem item)
  {
    Genre targetGenre = new Genre(item.getGenre());

    for (Genre genre : l)
    {
      if (targetGenre.equals(genre))
      {
        genre.delete(item.getTitle());

        if (!genre.isVaild())
          l.delete(genre);

        break;
      }
    }
  }

  public MyLinkedList<MovieDBItem> search(String term)
  {
    MyLinkedList<MovieDBItem> resultList = new MyLinkedList<MovieDBItem>();

    for (Genre genre : l)
    {
      MyLinkedList<MovieDBItem> subList = genre.search(term);
      for (MovieDBItem item : subList)
      {
        resultList.insert(item);
      }
      subList.removeAll();
    }

    return resultList;
  }

  public MyLinkedList<MovieDBItem> items()
  {
    MyLinkedList<MovieDBItem> resultList = new MyLinkedList<MovieDBItem>();

    for (Genre genre : l)
    {
      MyLinkedList<MovieDBItem> subList = genre.index();
      for (MovieDBItem item : subList)
      {
        resultList.insert(item);
      }
      subList.removeAll();
    }

    return resultList;
  }
}

class Genre
  implements Comparable<Genre>
{
  private MyLinkedList<MovieDBItem> l;
  private String name;

  public Genre(String name)
  {
    l = new MyLinkedList<MovieDBItem>();
    this.name = name;
  }

  public MyLinkedList<MovieDBItem> index()
  {
    return l;
  }

  public void insert(String title)
  {
    l.insert(new MovieDBItem(name, title));
  }

  public void delete(String title)
  {
    l.delete(new MovieDBItem(name, title));
  }

  public MyLinkedList<MovieDBItem> search(String term)
  {
    MyLinkedList<MovieDBItem> resultList = new MyLinkedList<MovieDBItem>();

    for (MovieDBItem item : l)
    {
      if (item.getTitle().contains(term))
        resultList.insert(item);
    }

    return resultList;
  }

  public boolean isVaild()
  {
    return !l.isEmpty();
  }

  @Override
  public String toString()
  {
    return name;
  }

  @Override
  public int compareTo(Genre o)
  {
    return this.name.compareTo(name);
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (this.getClass() != obj.getClass())
      return false;

    Genre other = (Genre) obj;

    if (this.name == null)
    {
      if (other.name != null)
        return false;
    }
    else if (!this.name.equals(other.name))
      return false;

    return true;
  }
}
