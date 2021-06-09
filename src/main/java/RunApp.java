import controller.Controller;

public class RunApp{
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        controller.addWindow();
    }
}