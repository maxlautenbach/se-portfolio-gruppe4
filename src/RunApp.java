import controller.Controller;
import model.Credit;

public class RunApp{
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        Credit credit = new Credit(1500,0.05f,"yearly",3, "yearly", 500, "yearly", "");
        System.out.println(credit.calculateEndAmount());
    }
}