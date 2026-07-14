class Logger{
    private static Logger instance= null;
    private Logger()
    {
        System.out.println("Instance created");
    }

    public static Logger getInstance()
    {
        if(instance==null) instance= new Logger();
        return instance;
    }

    public void log(String message)
    {
        System.out.println(message);
    }
}


public class App {
    public static void main(String[] args) throws Exception {
        Logger first= Logger.getInstance();
        first.log("Is this all you have?");
        Logger second= Logger.getInstance();
        System.out.println(first==second);
    }
}
