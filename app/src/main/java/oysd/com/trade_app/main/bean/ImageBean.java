package oysd.com.trade_app.main.bean;

public class ImageBean {


    /**
     * bigImagePath : string
     * imageId : 0
     * smallImagePath : string
     */

    private String bigImagePath;
    private int imageId;
    private String smallImagePath;

    public String getBigImagePath() {
        return bigImagePath;
    }

    public void setBigImagePath(String bigImagePath) {
        this.bigImagePath = bigImagePath;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getSmallImagePath() {
        return smallImagePath;
    }

    public void setSmallImagePath(String smallImagePath) {
        this.smallImagePath = smallImagePath;
    }
}
