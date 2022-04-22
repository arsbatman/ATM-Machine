package Calculator;
import java.util.Scanner;
//this basic credit calculator
public class Calculator {
    private double val1;
    private double val2;
    public String op;
    public double result;
    private double per=4;
    //input how much money they want and how many mounth he/she pay
    //and type of this credit "common money" or "house"
    public Calculator(){
        Scanner sca= new Scanner(System.in);
        System.out.println("How much money do you want to borrow");
        val1 = Integer.parseInt(sca.nextLine());
        System.out.println("For how many months");
        val2 = Integer.parseInt(sca.nextLine());
        System.out.println("choose credits for money or house");
        op = sca.nextLine();
    }
    //this method calculate credit for money
    public double calsum()
    {
        if(this.val2<=12){
            return this.val1/this.val2;
        }
        else{
         return this.val1*(per+(this.val2/(Math.pow(1+per,val2)-1)));
        }
    }

    public double getX()
    {
        return this.val1;
    }
    public double getY()
    {
        return this.val2;
    }

}