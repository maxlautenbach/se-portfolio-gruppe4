import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Controller;
import model.Credit;

import static model.Credit.creditTypes.FAELLIGKEITSKREDIT;

public class RunApp{
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        controller.createObject(1500,"",5,21, "monatlich", FAELLIGKEITSKREDIT);
        controller.getCredit().calculateEndAmount();
        controller.loadObjectById(1);
        controller.addWindow();
        controller.saveObject();
    }
}