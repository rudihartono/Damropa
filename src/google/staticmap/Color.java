package google.staticmap;

/**
 * Created by rudihartono on 23/12/2014.
 */
public enum Color {
    BLACK,
    BROWN,
    GREEN,
    PURPLE,
    YELLOW,
    BLUE,
    GREY,
    ORANGE,
    RED,
    WHITE;
    public String toString(){
        if(this.equals(BLACK)){
            return "black";
        }else if(this.equals(BROWN)){
            return "brown";
        }else if(this.equals(GREEN)){
            return "brown";
        }else if(this.equals(BROWN)){
            return "brown";
        }else if(this.equals(PURPLE)){
            return "purple";
        }else if(this.equals(YELLOW)){
            return "yellow";
        }else if(this.equals(BLUE)){
            return "blue";
        }else if(this.equals(GREY)){
            return "grey";
        }else if(this.equals(RED)){
            return "red";
        }else{
            return "white";
        }
    }
}