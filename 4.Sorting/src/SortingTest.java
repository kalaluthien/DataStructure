import java.io.*;
import java.util.*;

public class SortingTest
{
  public static void main(String args[])
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try
    {
      boolean isRandom = false;
      int[] value;
      String nums = br.readLine();
      if (nums.charAt(0) == 'r')
      {
        isRandom = true;

        String[] nums_arg = nums.split(" ");

        int numsize = Integer.parseInt(nums_arg[1]);
        int rminimum = Integer.parseInt(nums_arg[2]);
        int rmaximum = Integer.parseInt(nums_arg[3]);

        Random rand = new Random();

        value = new int[numsize];
        for (int i = 0; i < value.length; i++)
          value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
      }
      else
      {
        int numsize = Integer.parseInt(nums);

        value = new int[numsize];
        for (int i = 0; i < value.length; i++)
          value[i] = Integer.parseInt(br.readLine());
      }

      while (true)
      {
        int[] newvalue = (int[])value.clone();

        String command = br.readLine();

        long t = System.currentTimeMillis();
        switch (command.charAt(0))
        {
          case 'B':
            newvalue = DoBubbleSort(newvalue);
            break;
          case 'I':
            newvalue = DoInsertionSort(newvalue);
            break;
          case 'H':
            newvalue = DoHeapSort(newvalue);
            break;
          case 'M':
            newvalue = DoMergeSort(newvalue);
            break;
          case 'Q':
            newvalue = DoQuickSort(newvalue);
            break;
          case 'R':
            newvalue = DoRadixSort(newvalue);
            break;
          case 'X':
            return;
          default:
            throw new IOException("잘못된 정렬 방법을 입력했습니다.");
        }
        if (isRandom)
          System.out.println((System.currentTimeMillis() - t) + " ms");
        else
        {
          for (int i = 0; i < newvalue.length; i++)
            System.out.println(newvalue[i]);
        }
      }
    }
    catch (IOException e)
    {
      System.out.println("ERROR: " + e.toString());
    }
  }

  private static int[] DoBubbleSort(int[] value)
  {
    return (value);
  }

  private static int[] DoInsertionSort(int[] value)
  {
    return (value);
  }

  private static int[] DoHeapSort(int[] value)
  {
    return (value);
  }

  private static int[] DoMergeSort(int[] value)
  {
    return (value);
  }

  private static int[] DoQuickSort(int[] value)
  {
    return (value);
  }

  private static int[] DoRadixSort(int[] value)
  {
    return (value);
  }
}
