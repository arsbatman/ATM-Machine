package Calculator;
public class Mortgage extends Calculator{
    private double pers=0.1;
    public Mortgage()
    {
        super();
    }
    //this method calculate credit for house
    public double mortgage()
    {
        return (this.getX()*pers)/(1-(Math.pow(1+pers,-this.getY())));
    }

}
