package google.staticmap;

/**
 * Created by rudihartono on 03/12/2014.
 */
public enum MapType {
    ROADMAP,
    SATELLITE,
    HYBRID,
    TERRAIN;

    public String toString(){
        if(this.equals(ROADMAP)){
            return "roadmap";
        }else if(this.equals(SATELLITE)){
            return "satellite";
        }else if(this.equals(HYBRID)){
            return "hybrid";
        }else{
            return "terrain";
        }
    }
}
