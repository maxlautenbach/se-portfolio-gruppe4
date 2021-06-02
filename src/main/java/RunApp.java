import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Controller;
import model.Credit;

public class RunApp{
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        Credit credit = new Credit(1500,0.05f,"yearly",3, "years", 500, "yearly", "");
        Credit credit2 = new Credit(3600,0.05f,"yearly",3, "years", 100, "monthly", "");
        System.out.println(credit.calculateEndAmount());
        credit.getJSON();
        System.out.println(credit2.calculateEndAmount());
    }
}