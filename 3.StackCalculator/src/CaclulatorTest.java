import java.io.*;
import java.util.*;

/**
 * 정수
 * (0개 이상의 공백)E(0개 이상의 공백)
 * -E
 *  E+E
 *  E-E
 *  E*E
 *  E/E
 *  E%E
 *  E^E
 *  (E)
 */

enum Type
{
  NUMBER(-1), PAREN(0), SUM(1), PRODUCT(2), UNARY(3), EXP(4);

  private int priority;

  private Type(int priority)
  {
    this.priority = priority;
  }

  public int valueOf()
  {
    return priority;
  }
}

class Token implements Comparable<Token>
{
  private Type type;
  private String value;

  public Token(Type type, String value)
  {
    this.type = type;
    this.value = value;
  }

  public int compareTo(Token other)
  {
    if (this.valueOf() < other.valueOf())
      return -1;
    else if (this.valueOf() > other.valueOf())
      return 1;
    else
      return 0;
  }

  @Override
  public String toString
  {
    return value;
  }
}

public class CalculatorTest
{
  public static void main(String args[])
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    while (true)
    {
      try
      {
        String input = br.readLine();
        if (input.compareTo("q") == 0)
          break;

        processInput(input);
      }
      catch (Exception e)
      {
        System.out.println("ERROR: " + e.toString());
      }
    }
  }

  private static void processInput(String input)
  {

  }

  private static void infixToPostfix()
  {

  }

  private static boolean isWhite(char c)
  {
    return (c == ' ' || c == '\t');
  }
}
























