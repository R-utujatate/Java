public class OverrideExistingVal{
    int num=10;
    public static void main(String[] args) {
        OverrideExistingVal Obj=new OverrideExistingVal();
        Obj.num=90; //Num is now 90
        System.out.println(Obj.num);
    }

}