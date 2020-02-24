package com.bagevent.new_home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/02/25
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitionListData {
    private int respType;
    private int retStatus;
    private RespObjectBean respObject;

    public int getRespType() {
        return respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public RespObjectBean getRespObject() {
        return respObject;
    }

   public ExhibitionListData(JsonObject jsonObject){
       if (jsonObject.get("respType") != null) {
           respType = jsonObject.get("respType").getAsInt();
       }
       if (jsonObject.get("retStatus") != null) {
           retStatus = jsonObject.get("retStatus").getAsInt();
       }
       if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonArray()) {
           respObject = new RespObjectBean(jsonObject.get("respObject").getAsJsonArray());
       }
    }
    public static class RespObjectBean{
        private ArrayList<ExhibitionList> exhibitorList;


        public ArrayList<ExhibitionList> getExhibitorList() {
            return exhibitorList;
        }

        public RespObjectBean(JsonArray jsonArray){
            exhibitorList=new ArrayList<>(jsonArray.size());
            for (int i=0;i<jsonArray.size();i++){
                exhibitorList.add(new ExhibitionList(jsonArray.get(i).getAsJsonObject()));
            }
        }
    }

    public static class ExhibitionList{
        private int rank;
        private Event event;
        private ExExhibitorInfo exExhibitorInfo;

        public int getRank() {
            return rank;
        }

        public Event getEvent() {
            return event;
        }

        public ExExhibitorInfo getExExhibitorInfo() {
            return exExhibitorInfo;
        }

        public ExhibitionList(JsonObject jsonObject){
            rank=jsonObject.get("rank").getAsInt();
            if (jsonObject.get("event").isJsonObject()) {
                event = new Event(jsonObject.getAsJsonObject("event"));
            }
            if (jsonObject.get("exExhibitorInfo").isJsonObject()) {
                exExhibitorInfo = new ExExhibitorInfo(jsonObject.getAsJsonObject("exExhibitorInfo"));
            }
        }
    }

    public static class Event{
        private int eventId;
        private String address;
        private String eventName;
        private int status;
        private int attendeeCount;
        private int checkinCount;
        private int auditCount;
        private int ticketCount;
        private int income;
        private int totalIncome;
        private int eventType;
        private int collectInvoice;
        private String startTime;
        private String endTime;
        private String updateTime;
        private int brand;
        private String logo;

        public Event(JsonObject jsonObject){
            eventId=jsonObject.get("eventId").getAsInt();
            address=jsonObject.get("address").getAsString();
            status=jsonObject.get("status").getAsInt();
            eventName=jsonObject.get("eventName").getAsString();
            attendeeCount=jsonObject.get("attendeeCount").getAsInt();
            checkinCount=jsonObject.get("checkinCount").getAsInt();
            auditCount=jsonObject.get("auditCount").getAsInt();
            ticketCount=jsonObject.get("ticketCount").getAsInt();
            income=jsonObject.get("income").getAsInt();
            eventType=jsonObject.get("eventType").getAsInt();
            totalIncome=jsonObject.get("totalIncome").getAsInt();
            collectInvoice=jsonObject.get("collectInvoice").getAsInt();
            startTime=jsonObject.get("startTime").getAsString();
            endTime=jsonObject.get("endTime").getAsString();
            updateTime=jsonObject.get("updateTime").getAsString();
            logo=jsonObject.get("logo").getAsString();
            brand=jsonObject.get("brand").getAsInt();
        }
        public int getEventId() {
            return eventId;
        }

        public String getAddress() {
            return address;
        }

        public String getEventName() {
            return eventName;
        }

        public int getStatus(){
            return status;
        }

        public int getAttendeeCount() {
            return attendeeCount;
        }

        public int getCheckinCount() {
            return checkinCount;
        }

        public int getAuditCount() {
            return auditCount;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public int getIncome() {
            return income;
        }

        public int getTotalIncome() {
            return totalIncome;
        }

        public int getEventType() {
            return eventType;
        }

        public int getCollectInvoice() {
            return collectInvoice;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public int getBrand() {
            return brand;
        }

        public String getLogo() {
            return logo;
        }

    }

    public static class ExExhibitorInfo{
        private int exhibitorId;
        private ExExhibitorInfoDTO exExhibitorInfoDTO;
        private String remark;
        private int audit;
        private String boothNo;
        private String boothHall;
        private int favouriteNum;
        private int questionNum;
        private int viewCount;
        private int amount;



       public ExExhibitorInfo(JsonObject jsonObject){
           exhibitorId=jsonObject.get("exhibitorId").getAsInt();
           remark=jsonObject.get("remark").getAsString();
           audit=jsonObject.get("audit").getAsInt();
           boothNo=jsonObject.get("boothNo").getAsString();
           boothHall=jsonObject.get("boothHall").getAsString();
           favouriteNum=jsonObject.get("favouriteNum").getAsInt();
           questionNum=jsonObject.get("questionNum").getAsInt();
           viewCount=jsonObject.get("viewCount").getAsInt();
           amount=jsonObject.get("amount").getAsInt();
           if (jsonObject.get("exExhibitorInfoDTO").isJsonObject()) {
               exExhibitorInfoDTO = new ExExhibitorInfoDTO(jsonObject.getAsJsonObject("exExhibitorInfoDTO"));
           }
       }

        public int getExhibitorId() {
            return exhibitorId;
        }

        public ExExhibitorInfoDTO getExExhibitorInfoDTO() {
            return exExhibitorInfoDTO;
        }

        public String getRemark() {
            return remark;
        }

        public int getAudit() {
            return audit;
        }

        public String getBoothNo() {
            return boothNo;
        }

        public String getBoothHall() {
            return boothHall;
        }

        public int getFavouriteNum() {
            return favouriteNum;
        }

        public int getQuestionNum() {
            return questionNum;
        }

        public int getViewCount() {
            return viewCount;
        }

        public int getAmount() {
            return amount;
        }
    }

    public static class ExExhibitorInfoDTO{
        private int exhibitorInfoId;
        private String userId;
        private String company;
        private String logo;
        private String email;
        private String phone;
        private String website;
        private String brief;
        private String exhibitorType;

        public ExExhibitorInfoDTO(JsonObject jsonObject){
            exhibitorInfoId=jsonObject.get("exhibitorInfoId").getAsInt();
            userId=jsonObject.get("userId").getAsString();
            company=jsonObject.get("company").getAsString();
            logo=jsonObject.get("logo").getAsString();
            email=jsonObject.get("email").getAsString();
            phone=jsonObject.get("phone").getAsString();
            website=jsonObject.get("website").getAsString();
            brief=jsonObject.get("brief").getAsString();
            exhibitorType=jsonObject.get("exhibitorType").getAsString();
        }
        public int getExhibitorInfoId() {
            return exhibitorInfoId;
        }

        public String getUserId() {
            return userId;
        }

        public String getCompany() {
            return company;
        }

        public String getLogo() {
            return logo;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getWebsite() {
            return website;
        }

        public String getBrief() {
            return brief;
        }

        public String getExhibitorType() {
            return exhibitorType;
        }


    }
}
