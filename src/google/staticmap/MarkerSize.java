package google.staticmap;

/**
 * Created by rudihartono on 03/12/2014.
 */
public enum MarkerSize {
    TINY,
    SMALL,
    MID;

    public String toString(){
        if(this.equals(TINY)){
            return "tiny";
        }else if(this.equals(SMALL)){
            return "small";
        }else{
            return "mid";
        }
    }
}
