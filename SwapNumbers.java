package BasicsOfJava;

import java.util.Scanner;

public class SwapNumbers {
    public static void main(String[] args){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter two numbers:");
        int num1= sc.nextInt();
        int num2=sc.nextInt();
        int temp=num1;
        num1=num2;
        num2=temp;
        System.out.println("Swapped numbers: "+num1+num2);



    }
}
