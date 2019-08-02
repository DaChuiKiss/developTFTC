package oysd.com.trade_app.http.bean;

import java.util.List;

/**
 * 货币类型
 */
public class CurrencyTypeBean {

    /**
     * status : {"code":0,"msg":"成功"}
     * data : [{"id":78,"name":"港元","abbreviation":"HKD","exchangeCoinName":null,"exchangeCoinId":null,"exchangeValue":null,"remark":null,"updateDate":null}]
     * pagingData : null
     */

    private StatusBean status;
    private Object pagingData;
    private List<DataBean> data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public Object getPagingData() {
        return pagingData;
    }

    public void setPagingData(Object pagingData) {
        this.pagingData = pagingData;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class StatusBean {
        /**
         * code : 0
         * msg : 成功
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean {
        /**
         * id : 78
         * name : 港元
         * abbreviation : HKD
         * exchangeCoinName : null
         * exchangeCoinId : null
         * exchangeValue : null
         * remark : null
         * updateDate : null
         */

        private int id;
        private String name;
        private String abbreviation;
        private Object exchangeCoinName;
        private Object exchangeCoinId;
        private Object exchangeValue;
        private Object remark;
        private Object updateDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public Object getExchangeCoinName() {
            return exchangeCoinName;
        }

        public void setExchangeCoinName(Object exchangeCoinName) {
            this.exchangeCoinName = exchangeCoinName;
        }

        public Object getExchangeCoinId() {
            return exchangeCoinId;
        }

        public void setExchangeCoinId(Object exchangeCoinId) {
            this.exchangeCoinId = exchangeCoinId;
        }

        public Object getExchangeValue() {
            return exchangeValue;
        }

        public void setExchangeValue(Object exchangeValue) {
            this.exchangeValue = exchangeValue;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }
    }
}
