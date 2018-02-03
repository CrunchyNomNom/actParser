package parser;

public class main {
    public static void main(String[] args){
        try {
            Input input = new Input(args);
            Action action = new Action();
            action.run(input);
        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }
}
