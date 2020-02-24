package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/19.
 */
public class CreateEventData {

    private RespObjectBean respObject;

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
        private boolean isTicket;

        private EventBean event;
        private boolean isForm;

        private List<OrganizerListBean> organizerList;

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setIsTicket(boolean isTicket) {
            this.isTicket = isTicket;
        }

        public EventBean getEvent() {
            return event;
        }

        public void setEvent(EventBean event) {
            this.event = event;
        }

        public boolean isIsForm() {
            return isForm;
        }

        public void setIsForm(boolean isForm) {
            this.isForm = isForm;
        }

        public List<OrganizerListBean> getOrganizerList() {
            return organizerList;
        }

        public void setOrganizerList(List<OrganizerListBean> organizerList) {
            this.organizerList = organizerList;
        }

        public static class EventBean {
            private int addrType;
            private String address;
            private String banner;
            private String breif;
            private String defaultBanner;
            private String endTime;
            private String eventContent;
            private int eventId;
            private String eventName;
            private String jumpUrl;
            private String logo;
            private int nameType;
            private String officialWebsite;
            private String startTime;
            private int status;

            public int getAddrType() {
                return addrType;
            }

            public void setAddrType(int addrType) {
                this.addrType = addrType;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getBreif() {
                return breif;
            }

            public void setBreif(String breif) {
                this.breif = breif;
            }

            public String getDefaultBanner() {
                return defaultBanner;
            }

            public void setDefaultBanner(String defaultBanner) {
                this.defaultBanner = defaultBanner;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getEventContent() {
                return eventContent;
            }

            public void setEventContent(String eventContent) {
                this.eventContent = eventContent;
            }

            public int getEventId() {
                return eventId;
            }

            public void setEventId(int eventId) {
                this.eventId = eventId;
            }

            public String getEventName() {
                return eventName;
            }

            public void setEventName(String eventName) {
                this.eventName = eventName;
            }

            public String getJumpUrl() {
                return jumpUrl;
            }

            public void setJumpUrl(String jumpUrl) {
                this.jumpUrl = jumpUrl;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getNameType() {
                return nameType;
            }

            public void setNameType(int nameType) {
                this.nameType = nameType;
            }

            public String getOfficialWebsite() {
                return officialWebsite;
            }

            public void setOfficialWebsite(String officialWebsite) {
                this.officialWebsite = officialWebsite;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class OrganizerListBean {
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
}
