import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Controller;
import model.Credit;

public class RunApp{
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        controller.createObject(1500,0.05f,"yearly",3, "years", 500, "yearly", "");
        controller.saveObject();
    }
}