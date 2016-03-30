import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigInteger
{
  public static final int MAX_LEN = 256;
  public static final String QUIT_COMMAND = "quit";
  public static final String MSG_INVALID_INPUT = "ERROR";
  public static final String EXPRESSION_REGEX =
    "^\\s*([\\+|-]?)\\s*(\\d+)\\s*(\\+|-|\\*)\\s*([\\+|-]?)\\s*(\\d+)\\s*$";
  public static final Pattern EXPRESSION_PATTERN =
    Pattern.compile(EXPRESSION_REGEX);

  private int[] item;
  private int length;

  public BigInteger(int i)
  {
    item = new int[MAX_LEN];

    int sign = getSign(i);
    for (length = 0; i * sign > 0; length++)
    {
      item[length] = i % 10;
      i /= 10;
    }
  }

  public BigInteger(int[] num)
  {
    item = new int[MAX_LEN];

    for (length = num.length; length > 0; length--)
      if (num[length - 1] != 0)
        break;

    for (int i = 0; i < length; i++)
      item[i] = num[i];
  }

  public BigInteger(String s)
  {
    item = new int[MAX_LEN];

    int sign = 1;
    switch (s.charAt(0))
    {
      case '-':
        sign = -1;
      case '+':
        s = s.substring(1);
      default:
        break;
    }

    int startIndex;
    for (startIndex = 0; startIndex < s.length(); startIndex++)
      if (s.charAt(startIndex) != '0')
        break;
    s = s.substring(startIndex);

    char ch;
    for (length = 0; length < s.length(); length++)
    {
      ch = s.charAt(s.length() - length - 1);
      item[length] = sign * Character.getNumericValue(ch);
    }
  }

  public BigInteger add(BigInteger big)
  {
    int[] item = new int[MAX_LEN];

    for (int i = 0; i < this.length || i < big.length; i++)
      item[i] = this.item[i] + big.item[i];

    return new BigInteger(standardize(item));
  }

  public BigInteger subtract(BigInteger big)
  {
    return this.add(big.multiply(new BigInteger(-1)));
  }

  public BigInteger multiply(BigInteger big)
  {
    int[] item = new int[MAX_LEN];

    for (int i = 0; i < this.length; i++)
      for (int j = 0; j < big.length; j++)
        item[i+j] += this.item[i] * big.item[j];

    return new BigInteger(standardize(item));
  }

  private static int[] standardize(int[] num)
  {
    applyCarry(num);
    applyBorrow(num);
    return num;
  }

  private static void applyCarry(int[] num)
  {
    for (int i = 0; i < MAX_LEN - 1; i++)
    {
      int sign = getSign(num[i]);
      while (num[i] * sign > 9)
      {
        num[i] -= 10 * sign;
        num[i + 1] += sign;
      }
    }
  }

  private static void applyBorrow(int[] num)
  {
    int lastIndex = MAX_LEN - 1;
    while (lastIndex > 0 && num[lastIndex] == 0)
      lastIndex--; 

    int sign = getSign(num[lastIndex]);
    for (int i = 0; i < lastIndex; i++)
      while (num[i] * sign < 0)
      {
        num[i] += 10 * sign;
        num[i + 1] -= sign;
      }
  }

  private static int getSign(int n)
  {
    return n < 0 ? -1 : 1;
  }

  @Override
  public String toString()
  {
    if (length == 0)
      return "0";

    StringBuilder resultString = new StringBuilder();

    for (int i = length - 1; i >= 0; i--)
    {
      int digit = item[i] < 0 ? item[i] * -1 : item[i];
      resultString.append(digit);
    }

    String sign = item[length - 1] < 0 ? "-" : "";
    return sign + resultString.toString();
  }

  private static BigInteger evaluate(String input)
    throws IllegalArgumentException
  {
    String operator;
    BigInteger big1, big2;

    Matcher matcher = EXPRESSION_PATTERN.matcher(input);
    if (matcher.matches())
    {
      if (matcher.group(2).equals("") ||
          matcher.group(3).equals("") ||
          matcher.group(5).equals(""))
        throw new IllegalArgumentException();

      big1 = new BigInteger(matcher.group(1) + matcher.group(2));
      big2 = new BigInteger(matcher.group(4) + matcher.group(5));
      operator = matcher.group(3);
    }
    else
      throw new IllegalArgumentException();

    if (operator.equals("+"))
      return big1.add(big2);
    else if (operator.equals("-"))
      return big1.subtract(big2);
    else if (operator.equals("*"))
      return big1.multiply(big2);
    else
      throw new IllegalArgumentException();
  }

  public static void main(String[] args) throws Exception
  {
    try (InputStreamReader isr = new InputStreamReader(System.in))
    {
      try (BufferedReader reader = new BufferedReader(isr))
      {
        boolean done = false;
        while (!done)
        {
          String input = reader.readLine();

          try
          {
            done = processInput(input);
          }
          catch (IllegalArgumentException e)
          {
            System.err.println(MSG_INVALID_INPUT);
          }
        }
      }
    }
  }

  static boolean processInput(String input)
    throws IllegalArgumentException
  {
    boolean quit = isQuitCmd(input);

    if (quit)
      return true;
    else
    {
      BigInteger result = evaluate(input);
      System.out.println(result.toString());
      return false;
    }
  }

  static boolean isQuitCmd(String input)
  {
    return input.equalsIgnoreCase(QUIT_COMMAND);
  }
}
