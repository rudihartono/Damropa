package google.staticmap;

/**
 * Created by rudihartono on 02/12/2014.
 */
public class InvalidParameterException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidParameterException(String message){
        super("Invalid parameter! " + message);
    }
}
