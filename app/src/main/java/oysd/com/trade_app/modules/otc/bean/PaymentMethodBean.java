package oysd.com.trade_app.modules.otc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 支付方式 bean .
 * Created by Liam on 2018/8/25.
 */
public class PaymentMethodBean implements Parcelable{

    /**
     * paymentMethod : 5
     * paymentMethodName : 银行卡
     */

    private int paymentMethod;
    private String paymentMethodName;

    protected PaymentMethodBean(Parcel in) {
        paymentMethod = in.readInt();
        paymentMethodName = in.readString();
    }

    public static final Creator<PaymentMethodBean> CREATOR = new Creator<PaymentMethodBean>() {
        @Override
        public PaymentMethodBean createFromParcel(Parcel in) {
            return new PaymentMethodBean(in);
        }

        @Override
        public PaymentMethodBean[] newArray(int size) {
            return new PaymentMethodBean[size];
        }
    };

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(paymentMethod);
        dest.writeString(paymentMethodName);
    }

}
