package BasicsOfJava;

public class ArrayReverse {


    static void reverse(int a[],int n)
    {
        int i,k,t;
        for (i=0;i< n/2;i++)
        {
            t=a[i];
            a[i]=a[n-i-1];
            a[n-i-1]=t;
        }
        System.out.println("Reversed array is: \n");
        for (k=0;k<n;k++)
        {
            System.out.println(a[k]);
        }
    }

    public static void main(String[] args)
    {
        int [] arr ={10,20,50,30,40,60};
        reverse(arr, arr.length);
    }
}
