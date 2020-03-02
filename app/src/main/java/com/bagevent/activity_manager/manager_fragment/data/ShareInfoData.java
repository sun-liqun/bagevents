package com.bagevent.activity_manager.manager_fragment.data;

/**
 * Created by zwj on 2016/12/5.
 */
public class ShareInfoData {

    /**
     * img : /resource/20160805/20160805105749734146.png
     * eventUrl : http://123.57.41.96:9001/event/15677
     * title : 85test
     * content : 大学生暑期乐
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"img":"/resource/20160805/20160805105749734146.png","eventUrl":"http://123.57.41.96:9001/event/15677","title":"85test","content":"大学生暑期乐"}
     * respType : 0
     * retStatus : 200
     */

    private int respType;
    private int retStatus;

    public RespObjectBean getRespObject() {
        return respObject;
    }

    public void setRespObject(RespObjectBean respObject) {
        this.respObject = respObject;
    }

    public int getRespType() {
        return respType;
    }

    public void setRespType(int respType) {
        this.respType = respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    public static class RespObjectBean {
        private String img;
        private String eventUrl;
        private String title;
        private String content;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getEventUrl() {
            return eventUrl;
        }

        public void setEventUrl(String eventUrl) {
            this.eventUrl = eventUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
