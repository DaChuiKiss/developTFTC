package oysd.com.trade_app.common;

import java.io.Serializable;
import java.util.Map;


/**
 * @author houmingkuan
 * @time 2018/10/17
 * @desc A wrapper for serializing a map object.
 */
public class SerializableMap implements Serializable {

    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
