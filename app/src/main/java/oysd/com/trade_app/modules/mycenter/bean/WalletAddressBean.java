package oysd.com.trade_app.modules.mycenter.bean;

public class WalletAddressBean {

    /**
     * imageData : string
     * takeInReminder : string
     * walletAddress : string
     */

    private String imageData;
    private String takeInReminder;
    private String walletAddress;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getTakeInReminder() {
        return takeInReminder;
    }

    public void setTakeInReminder(String takeInReminder) {
        this.takeInReminder = takeInReminder;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
