import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Calculation;
import controller.Controller;
import model.Credit;

import static model.Credit.creditTypes.*;

public class RunApp{
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        controller.createObject(1500,"",5,21, "monatlich", FAELLIGKEITSKREDIT);
        controller.calculateEndAmount();
        controller.loadObjectById(1);
        controller.addWindow();
        controller.saveObject();

    }
}