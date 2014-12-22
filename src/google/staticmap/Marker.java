package google.staticmap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rudihartono on 03/12/2014.
 */
public class Marker {

    private static final Pattern LABEL_PATTERN = Pattern.compile("[A-Z0-0]");

    private static final Pattern COLOR_PATTERN = Pattern.compile("^0x?(([a-fA-F0-9]){3}){1,2}$");

    private String position;

    private MarkerSize size;

    private String color;

    private char label;

    public Marker(Coordinate position){
        setPosition(position);
    }

    public Marker(String address){
        setPosition(address);
    }
    public String getPosition(){
        return this.position;
    }
    public void setPosition(Coordinate position){
        this.position = position.toString();
    }
    public void setPosition(String address){
        this.position = address.replace(" ","+");
    }
    public MarkerSize getSize(){
        return this.size;
    }
    public void setSize(MarkerSize size){
        this.size = size;
    }
    public String getColor(){
        return this.color;
    }
    public void setColor(Color color){
        this.color = color.toString();
    }
    public void setColor(String color) throws InvalidParameterException{
        Matcher matcher = COLOR_PATTERN.matcher(color);
        if(matcher.matches()){
            this.color = color.toString();
        }else{
            throw new InvalidParameterException("The color representation is invalid. Must be in the format of \"0xF0F0F0\"");
        }
    }
    public char getLabel(){
        return this.label;
    }
    public void setLabel(char label)throws InvalidParameterException{
        Matcher matcher = LABEL_PATTERN.matcher(Character.toString(label));
        if(matcher.matches()){
            this.label = label;
        }else
        {
            throw new InvalidParameterException( "Only alphanumeric upper case characters ({A-Z} or {0-9}) are allowed." );
        }
    }

    public String getURLRepresentation(){
        StringBuilder builder = new StringBuilder(64); //64 is capisty of string length
        builder.append("&");
        builder.append("markers=");

        if(this.size != null && this.size != MarkerSize.MID){
            builder.append("size:");
            builder.append(this.size.toString());
            builder.append("|");
        }

        if(this.color != null){
            builder.append("color:");
            builder.append(this.color.toString());
            builder.append("|");

        }
        if(this.label != '\u0000' && (this.size == null || this.size == MarkerSize.MID)){
            builder.append("label:");
            builder.append(this.label);
            builder.append("|");
        }
        builder.append(this.position);

        return builder.toString();
    }
}
