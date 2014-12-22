package damropa.code;

/**
 * Created by rudihartono on 23/12/2014.
 */
public enum Heading {
    NORTH,
    NORTEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString(){
        if(this.equals(EAST)){
            return "east";
        }else if(this.equals(SOUTHEAST)){
            return "southeast";
        }else if(this.equals(SOUTH)){
            return "south";
        }else if(this.equals(SOUTHWEST)){
            return "southwest";
        }else if(this.equals(WEST)){
            return "west";
        }else if(this.equals(NORTHWEST)){
            return "northwest";
        }else if(this.equals(NORTH)){
            return "north";
        }
        else{
            return "northeast";
        }
    }
}
