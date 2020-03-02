package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/18.
 */
public class SponsorData {

    /**
     * respObject : [{"bannerUrl":"/resource/20160301/2016030118134823287.png","brief":"会务管理","contactMail":"allen@eventslack.com","contactPhone":"13037506596","contactQq":"","contacts":"","logoUrl":"/resources/img/organizers_logo.png","officialHomepage":"http://www.bagevent.com","organizerId":3,"organizerName":"bagEvent","usedState":0},{"bannerUrl":"/resources/img/organizer/banner01.jpg","brief":"润肤成分为非 v 饿","contactMail":"allen@eventslack.com","contactPhone":"13037506596","contactQq":"","contacts":"","logoUrl":"/resources/img/organizers_logo.png","officialHomepage":"http://www.bagevent.com","organizerId":11,"organizerName":"程勋","usedState":0},{"bannerUrl":"/resources/img/organizer/banner01.jpg","brief":"","contactMail":"","contactPhone":"13037506596","contactQq":"","contacts":"","logoUrl":"/resource/20160628/201606281704497540.png","officialHomepage":"http://www.bagevent.com/org/90156","organizerId":90156,"organizerName":"百格活动","usedState":0},{"bannerUrl":"/resources/img/organizer/banner01.jpg","brief":"","contactMail":"","contactPhone":"","contactQq":"","contacts":"","logoUrl":"/resources/img/organizers_logo.png","officialHomepage":"http://www.bagevent.com/org/101787","organizerId":101787,"organizerName":"滔滔汩汩","usedState":0}]
     * respType : 1
     * retStatus : 200
     */

    private int respType;
    private int retStatus;
    /**
     * bannerUrl : /resource/20160301/2016030118134823287.png
     * brief : 会务管理
     * contactMail : allen@eventslack.com
     * contactPhone : 13037506596
     * contactQq :
     * contacts :
     * logoUrl : /resources/img/organizers_logo.png
     * officialHomepage : http://www.bagevent.com
     * organizerId : 3
     * organizerName : bagEvent
     * usedState : 0
     */

    private List<RespObjectBean> respObject;

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

    public List<RespObjectBean> getRespObject() {
        return respObject;
    }

    public void setRespObject(List<RespObjectBean> respObject) {
        this.respObject = respObject;
    }

    public static class RespObjectBean {
        private String bannerUrl;
        private String brief;
        private String contactMail;
        private String contactPhone;
        private String contactQq;
        private String contacts;
        private String logoUrl;
        private String officialHomepage;
        private int organizerId;
        private String organizerName;
        private int usedState;

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getContactMail() {
            return contactMail;
        }

        public void setContactMail(String contactMail) {
            this.contactMail = contactMail;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getContactQq() {
            return contactQq;
        }

        public void setContactQq(String contactQq) {
            this.contactQq = contactQq;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getOfficialHomepage() {
            return officialHomepage;
        }

        public void setOfficialHomepage(String officialHomepage) {
            this.officialHomepage = officialHomepage;
        }

        public int getOrganizerId() {
            return organizerId;
        }

        public void setOrganizerId(int organizerId) {
            this.organizerId = organizerId;
        }

        public String getOrganizerName() {
            return organizerName;
        }

        public void setOrganizerName(String organizerName) {
            this.organizerName = organizerName;
        }

        public int getUsedState() {
            return usedState;
        }

        public void setUsedState(int usedState) {
            this.usedState = usedState;
        }
    }
}
