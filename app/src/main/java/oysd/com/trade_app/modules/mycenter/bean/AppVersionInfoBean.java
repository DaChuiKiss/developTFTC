package oysd.com.trade_app.modules.mycenter.bean;

import java.util.List;

public class AppVersionInfoBean {


    /**
     * appType : 0
     * createDate : 2018-08-28T10:17:08.499Z
     * downUrl : string
     * id : 0
     * isCompulsory : 0
     * status : 0
     * versionDesc : [{"content":"string","languageCode":"string","languageName":"string"}]
     * versionDescList : string
     * versionRecognition : 0
     * vsersionNo : string
     */

    private int appType;
    private String createDate;
    private String downUrl;
    private int id;
    private int isCompulsory;
    private int status;
    private String versionDescList;
    private int versionRecognition;
    private String vsersionNo;
    private List<VersionDescBean> versionDesc;

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsCompulsory() {
        return isCompulsory;
    }

    public void setIsCompulsory(int isCompulsory) {
        this.isCompulsory = isCompulsory;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVersionDescList() {
        return versionDescList;
    }

    public void setVersionDescList(String versionDescList) {
        this.versionDescList = versionDescList;
    }

    public int getVersionRecognition() {
        return versionRecognition;
    }

    public void setVersionRecognition(int versionRecognition) {
        this.versionRecognition = versionRecognition;
    }

    public String getVsersionNo() {
        return vsersionNo;
    }

    public void setVsersionNo(String vsersionNo) {
        this.vsersionNo = vsersionNo;
    }

    public List<VersionDescBean> getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(List<VersionDescBean> versionDesc) {
        this.versionDesc = versionDesc;
    }

    public static class VersionDescBean {
        /**
         * content : string
         * languageCode : string
         * languageName : string
         */

        private String content;
        private String languageCode;
        private String languageName;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
        }
    }
}
