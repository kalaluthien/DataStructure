import java.io.*;
import java.util.*;
import java.util.regex.*;

enum Type
  implements Comparable<Type>
{
  NUMBER(0, false), PAREN(1, false), SUM(2, false),
  PRODUCT(3, false), UNARY(4, true), EXP(5, true);

  private int precedence;
  public final boolean isRightAssoc;

  private Type(int precedence, boolean isRightAssoc)
  {
    this.precedence = precedence;
    this.isRightAssoc = isRightAssoc;
  }
}

class Token
  implements Comparable<Token>
{
  public final Type type;
  public final Object value;

  public Token(Object value)
  {
    if (value instanceof Long)
      type = Type.NUMBER;
    else
    {
      switch (((String) value).charAt(0))
      {
        case '+': case '-':
          type = Type.SUM;
          break;
        case '*': case '/': case '%':
          type = Type.PRODUCT;
          break;
        case '~':
          type = Type.UNARY;
          break;
        case '^':
          type = Type.EXP;
          break;
        default:
          type = Type.PAREN;
          break;
      }
    }
    this.value = value;
  }

  @Override
  public int compareTo(Token other)
  {
    return this.type.compareTo(other.type);
  }

  @Override
  public String toString()
  {
    if (type == Type.NUMBER)
      return String.valueOf((Long) value);
    else
      return (String) value;
  }
}

public class CalculatorTest
{
  private static final String EXPRESSION_PATTERN = "[+\\-*/%^()]|\\d+";

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

        ArrayList<Token> tokens = tokenize(input);
        long result = evaluate(tokens);

        printTokens(tokens);
        System.out.println(result);
      }
      catch (Exception e)
      {
        System.out.println("ERROR");
      }
    }
  }

  private static ArrayList<Token> tokenize(String input)
    throws Exception
  {
    ArrayList<Token> tokens = new ArrayList<Token>();
    Stack<Token> opers = new Stack<Token>();

    Pattern p = Pattern.compile(EXPRESSION_PATTERN);
    Matcher m = p.matcher(input);

    boolean isBeforeNumber = true;

    while (m.find())
    {
      String tokenString = m.group();

      if (isBeforeNumber)
      {
        switch (tokenString.charAt(0))
        {
          case '+': case '*': case '/': case '%': case '^':
            throw new Exception();
          case '-':
            stackOper("~", tokens, opers);
            break;
          case '(':
            opers.push(new Token(tokenString));
            break;
          default:
            isBeforeNumber = false;
            tokens.add(new Token(Long.parseLong(tokenString)));
            break;
        }
      }
      else
      {
        switch (tokenString.charAt(0))
        {
          case '+': case '-': case '*': case '/': case '%': case '^':
            isBeforeNumber = true;
            stackOper(tokenString, tokens, opers);
            break;
          case ')':
            boolean isMatched = false;

            while (!opers.empty())
            {
              Token top = opers.peek();
              if (top.type == Type.PAREN)
              {
                opers.pop();
                isMatched = true;
                break;
              }
              tokens.add(opers.pop());
            }

            if (isMatched)
              break;
          default:
            throw new Exception();
        }
      }
    }

    while (!opers.empty())
      tokens.add(opers.pop());

    if (isBeforeNumber)
      throw new Exception();

    return tokens;
  }

  private static void stackOper
    (String tokenString, ArrayList<Token> tokens, Stack<Token> opers)
  {
    Token token = new Token(tokenString);

    while (!opers.empty())
    {
      Token top = opers.peek();

      if (top.compareTo(token) < 0)
        break;
      else if (top.compareTo(token) == 0)
        if (token.type.isRightAssoc)
          break;

      tokens.add(opers.pop());
    }
    opers.push(token);
  }

  private static long evaluate(ArrayList<Token> tokens)
    throws Exception
  {
    Stack<Long> numbers = new Stack<Long>();

    for (Token token : tokens)
    {
      if (token.type == Type.NUMBER)
        numbers.push((Long) token.value);
      else if (token.type == Type.UNARY)
        numbers.push(-1 * numbers.pop());
      else
      {
        long numRight = numbers.pop();
        long numLeft = numbers.pop();

        switch (token.toString().charAt(0))
        {
          case '+':
            numbers.push(numLeft + numRight);
            break;
          case '-':
            numbers.push(numLeft - numRight);
            break;
          case '*':
            numbers.push(numLeft * numRight);
            break;
          case '/':
            numbers.push(numLeft / numRight);
            break;
          case '%':
            numbers.push(numLeft % numRight);
            break;
          case '^':
            if (numLeft == 0 && numRight < 0)
              throw new Exception();

            numbers.push((long) Math.pow((double) numLeft, (double) numRight));
            break;
          default:
            throw new Exception();
        }
      }
    }

    long result = numbers.pop();

    if (numbers.empty())
      return result;
    else
      throw new Exception();
  }

  private static void printTokens(ArrayList<Token> tokens)
  {
    StringBuilder result = new StringBuilder();
    boolean isFirstToken = true;

    for (Token token : tokens)
    {
      if (isFirstToken)
        isFirstToken = false;
      else
        result.append(" ");

      result.append(token.toString());
    }

    System.out.println(result);
  }
}
