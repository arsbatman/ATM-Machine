package Calculator;
public class Main {
    public void doOperation() {
        Mortgage calculator=new Mortgage();
        //checker house or money
        switch(calculator.op)
        {
            case "money":
                calculator.result = calculator.calsum();
                System.out.println(calculator.result);
                break;

            case "house":
                calculator.result = calculator.mortgage();
                System.out.println(calculator.result);
                break;
        }
    }
}


