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
        int[] newvalue = (int[]) value.clone();

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
            throw new IOException("ERROR");
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
      System.out.println("ERROR");
    }
  }

  private static int[] DoBubbleSort(int[] value)
  {
    for (int last = value.length - 1; last > 0; last--)
      for (int start = 0; start < last; start++)
        if (value[start] > value[start + 1])
          arraySwap(value, start, start + 1);

    return value;
  }

  private static int[] DoInsertionSort(int[] value)
  {
    for (int last = 1; last < value.length; last++)
    {
      int start = 0;
      while (start < last && value[start] <= value[last])
        start++;

      if (start < last)
        arrayShift(value, start, last);
    }

    return value;
  }

  private static int[] DoHeapSort(int[] value)
  {
    for (int start = value.length / 2; start >= 0; start--)
      percolateDown(value, start, value.length - 1);

    for (int last = value.length - 1; last > 0; last--)
    {
      arraySwap(value, 0, last);
      percolateDown(value, 0, last - 1);
    }

    return value;
  }

  private static void percolateDown(int[] value, int start, int last)
  {
    int parent = start;
    int left = 2 * parent + 1;
    int right = left + 1;

    if (left <= last && value[parent] < value[left])
      parent = left;
    if (right <= last && value[parent] < value[right])
      parent = right;

    if (parent != start)
    {
      arraySwap(value, parent, start);
      percolateDown(value, parent, last);
    }
  }

  private static int[] DoMergeSort(int[] value)
  {
    mergeSort(value, 0, value.length - 1);
    return value;
  }

  private static void mergeSort(int[] value, int start, int last)
  {
    if (start >= last)
      return;

    int mid = (start + last) / 2;
    mergeSort(value, start, mid);
    mergeSort(value, mid + 1, last);
    merge(value, start, last);
  }

  private static void merge(int[] value, int start, int last)
  {
    int mid = (start + last) / 2;

    while (start <= mid && mid < last)
    {
      if (value[start] <= value[mid + 1])
        start++;
      else
      {
        arrayShift(value, start, mid + 1);
        start++;
        mid++;
      }
    }
  }

  private static int[] DoQuickSort(int[] value)
  {
    Random rand = new Random();
    quickSort(value, 0, value.length - 1, rand);
    return value;
  }

  private static void quickSort(int[] value, int start, int last, Random rand)
  {
    if (start >= last)
      return;

    int mid = partition(value, start, last, rand);
    quickSort(value, start, mid, rand);
    quickSort(value, mid + 1, last, rand);
  }

  private static int partition(int[] value, int start, int last, Random rand)
  {
    int pivot = value[rand.nextInt(last - start) + start];

    while (true)
    {
      while (value[start] < pivot)
        start++;
      while (pivot < value[last])
        last--;

      if (start < last)
        arraySwap(value, start++, last--);
      else
        return last;
    }
  }

  private static int[] DoRadixSort(int[] value)
  {
    int maxValue = 0, maxDigit = 1;

    for (int i = 0; i < value.length; i++)
    {
      int curr = value[i] < 0 ? value[i] : -value[i];
      if (maxValue > curr)
        maxValue = curr;
    }

    for (maxValue /= 10; maxValue < 0; maxValue /= 10)
      maxDigit *= 10;

    value = radixSort(value, maxDigit);

    return value;
  }

  private static int[] radixSort(int[] value, int maxDigit)
  {
    int[] count = new int[19];
    int[] bucket = new int[value.length];

    for (int digit = 1; digit <= maxDigit; digit *= 10)
    {
      Arrays.fill(count, 0);

      for (int i = 0; i < value.length; i++)
        count[(value[i] / digit) % 10 + 9]++;

      for (int i = 1; i < count.length; i++)
        count[i] += count[i - 1];

      for (int i = value.length - 1; i >= 0; i--)
        bucket[--count[(value[i] / digit) % 10 + 9]] = value[i];

      int[] temp = value;
      value = bucket;
      bucket = temp;
    }
    
    return value;
  }

  private static void arraySwap(int[] value, int i, int j)
  {
    int temp = value[i];
    value[i] = value[j];
    value[j] = temp;
  }

  private static void arrayShift(int[] value, int start, int last)
  {
    int temp = value[last];
    System.arraycopy(value, start, value, start + 1, last - start);
    value[start] = temp;
  }
}
