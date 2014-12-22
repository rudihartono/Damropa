package google.staticmap;

/**
 * Created by rudihartono on 03/12/2014.
 */
public enum ImageFormat {
    PNG8,
    PNG32,
    GIF,
    JPG,
    JPG_BASELINE;

    public String toString(){
        if(this.equals(PNG8)){
            return "png8";
        }else if(this.equals(PNG32)){
            return "png32";
        }else if(this.equals(GIF)){
            return "gif";
        }else if(this.equals(JPG)){
            return "jpg";
        }else{
            return "jpg_baseline";
        }
    }
}
