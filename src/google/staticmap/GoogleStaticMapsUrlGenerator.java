package google.staticmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rudihartono on 03/12/2014.
 */
public class GoogleStaticMapsUrlGenerator {

    static  final Logger LOG = LoggerFactory.getLogger(GoogleStaticMapsUrlGenerator.class);

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/staticmap?";

    private String center;

    private short zoomLevel;

    private short scale;

    private int height;

    private int width;

    private ImageFormat format;

    private MapType mapType;

    private ArrayList<Marker> markers;

    private boolean sensor;

    public GoogleStaticMapsUrlGenerator(){
        this.markers = new ArrayList<Marker>();
    }

    public URL generateURL() throws MalformedURLException, URLOverLengthException, InvalidParameterException{
        StringBuilder builder = new StringBuilder(2500);
        builder.append(BASE_URL);

        if(this.width <= 0 || this.height <= 0){
            throw new InvalidParameterException("This dimensions must be bigger than zero");
        }

        builder.append("&");
        builder.append("size=");
        builder.append(this.width);
        builder.append("x");
        builder.append(this.height);

        if(this.markers.isEmpty() && this.center == null){
            throw  new InvalidParameterException("The center must be specified, if no marker's are available.");
        }else if(this.center != null){
            builder.append("&");
            builder.append("center=");
            builder.append(this.center);
        }

        builder.append("&");
        builder.append("zoom=");
        builder.append(this.zoomLevel);

        if(this.scale != 0 && this.scale != 1){
            builder.append("&");
            builder.append("scale=");
            builder.append(this.scale);
        }

        if(this.format != null && this.format != ImageFormat.PNG8){
            builder.append("&");
            builder.append("format=");
            builder.append(this.format.toString());
        }

        if(this.mapType != null && this.mapType != MapType.ROADMAP){
            builder.append("&");
            builder.append("maptype=");
            builder.append(this.mapType.toString());
        }

        for(Marker curMarker : this.markers){
            builder.append(curMarker.getURLRepresentation());
        }
        builder.append("&");
        builder.append("sensor=");
        builder.append(this.sensor);

        String url = builder.toString();
        if(url.length() > 2500){
            throw  new URLOverLengthException();
        }
        return new URL(url);
    }

    public String getCenter(){
        return this.center;
    }
    public void setCenter(Coordinate center){
        this.center = center.toString();
    }
    public void setCenter(String address){
        this.center = address.replace(" ","+");
    }

    public short getZoomLevel(){
        return this.zoomLevel;
    }
    public void setZoomLevel(short zoomLevel)throws  InvalidParameterException{
        if(zoomLevel >=0 && zoomLevel <=21){
            this.zoomLevel = zoomLevel;
        }else{
            throw new InvalidParameterException("The zoom level must be between 0 and 21");
        }
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public void setDimentations(int width, int height) throws InvalidParameterException{
        if(width > 0 && height > 0){
            this.width = width;
            this.height = height;
        }else{
            throw new InvalidParameterException("The dimensions must be bigger than zero.");
        }
    }

    public short getScale(){
        return this.scale;
    }

    public void setScale(){
        switch (scale){
            case 1: this.scale = 1;
                    break;
            case 2: this.scale = 2;
                    break;
            case 3: this.scale = 4;
                break;
            default:
                      //throw new InvalidParameterException("Only scales of 1,2, or 4 are supported.");
        }
    }

    public ImageFormat getFormat(){
        return this.format;
    }
    public void setFormat(ImageFormat format){
        this.format = format;
    }
    public MapType getMapType(){
        return this.mapType;
    }
    public void setMapType(MapType mapType){
        this.mapType = mapType;
    }
    public ArrayList<Marker> getMarkers(){
        return this.markers;
    }
    public void addMarker(Marker marker){
        this.markers.add(marker);
    }
    public boolean isSensorUse(){
        return  this.sensor;
    }
    public void setSensor(boolean sensor){
        this.sensor = sensor;
    }
}
