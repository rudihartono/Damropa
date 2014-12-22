package google.staticmap;

/**
 * Created by rudihartono on 02/12/2014.
 */
public class URLOverLengthException extends Exception {

    private static final long serialVersionUID = 1L;

    public URLOverLengthException(){
        super( "The URL exceeds the allowed length of 2500 characters." );
    }
}
