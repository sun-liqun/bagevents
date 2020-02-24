package com.bagevent.activity_manager.manager_fragment.data;

import com.bagevent.new_home.data.ExhibitionListData;
import com.bagevent.new_home.data.ExhibitorDynamicData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/11
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorData {

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

    public ExhibitorData(JsonObject jsonObject){
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

    public static class RespObjectBean {
        private ArrayList<ExhibitorList> exhibitorList;

        public ArrayList<ExhibitorList> getExhibitorList() {
            return exhibitorList;
        }

        public RespObjectBean(JsonArray jsonArray){
            exhibitorList=new ArrayList<>(jsonArray.size());
            for (int i=0;i<jsonArray.size();i++){
                exhibitorList.add(new ExhibitorList(jsonArray.get(i).getAsJsonObject()));
            }
        }

    }

    public static class ExhibitorList{

        private String booth_hall;
        private String company_type;
        private String exhibitor_info_brief;
        private String nation;
        private String work_phone;
        private int form_data_id;
        private String blog;
        private String exhibitor_info_company;
        private String company_address;
        private String exhibitor_info_logo;
        private String weibo_id;
        private String passport;
        private int price;
        private String avatar_collect;
        private String contact;
        private String exhibitor_info_email;
        private String receipt_amount;
        private String state;
        private String qq;
        private String home_phone;
        private String create_time;
        private String submit_time;
        private String sort;
        private int condition;
        private int exhibitor_info_id;
        private String user_id;
        private String company_name;
        private int favorite_num;
        private int status;
        private String received_amount;
        private String remark;
        private String title;
        private String weixin_id;
        private String exhibitor_info_phone;
        private String update_time;
        private int audit;
        private String mobile_phone;
        private String company;
        private String collectpointid;
        private String first_name;
        private int amount;
        private String website;
        private String address;
        private String sex;
        private int exhibitor_id;
        private int form_id;
        private String last_name;
        private String profile_data;
        private String booth_no;
        private int event_id;
        private String email_address;
        private String idcard;
        private String attendee_avatar;
        private String exhibitor_info_website;
        private int right_status;
        private String age;
        private String company_details;
        private String username;
        private ArrayList<ExhibitorSponsorLevels> exhibitorSponsorLevels;

        public String getBooth_hall() {
            return booth_hall;
        }

        public String getCompany_type() {
            return company_type;
        }

        public String getExhibitor_info_brief() {
            return exhibitor_info_brief;
        }

        public String getNation() {
            return nation;
        }

        public String getWork_phone() {
            return work_phone;
        }

        public int getForm_data_id() {
            return form_data_id;
        }

        public String getBlog() {
            return blog;
        }

        public String getExhibitor_info_company() {
            return exhibitor_info_company;
        }

        public String getCompany_address() {
            return company_address;
        }

        public String getExhibitor_info_logo() {
            return exhibitor_info_logo;
        }

        public String getWeibo_id() {
            return weibo_id;
        }

        public String getPassport() {
            return passport;
        }

        public int getPrice() {
            return price;
        }

        public String getAvatar_collect() {
            return avatar_collect;
        }

        public String getContact() {
            return contact;
        }

        public String getExhibitor_info_email() {
            return exhibitor_info_email;
        }

        public String getReceipt_amount() {
            return receipt_amount;
        }

        public String getState() {
            return state;
        }

        public String getQq() {
            return qq;
        }

        public String getHome_phone() {
            return home_phone;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getSubmit_time() {
            return submit_time;
        }

        public String getSort() {
            return sort;
        }

        public int getCondition() {
            return condition;
        }

        public int getExhibitor_info_id() {
            return exhibitor_info_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public int getFavorite_num() {
            return favorite_num;
        }

        public int getStatus() {
            return status;
        }

        public String getReceived_amount() {
            return received_amount;
        }

        public String getRemark() {
            return remark;
        }

        public String getTitle() {
            return title;
        }

        public String getWeixin_id() {
            return weixin_id;
        }

        public String getExhibitor_info_phone() {
            return exhibitor_info_phone;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public int getAudit() {
            return audit;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public String getCompany() {
            return company;
        }

        public String getCollectpointid() {
            return collectpointid;
        }

        public String getFirst_name() {
            return first_name;
        }

        public int getAmount() {
            return amount;
        }

        public String getWebsite() {
            return website;
        }

        public String getAddress() {
            return address;
        }

        public String getSex() {
            return sex;
        }

        public int getExhibitor_id() {
            return exhibitor_id;
        }

        public int getForm_id() {
            return form_id;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getProfile_data() {
            return profile_data;
        }

        public String getBooth_no() {
            return booth_no;
        }

        public int getEvent_id() {
            return event_id;
        }

        public String getEmail_address() {
            return email_address;
        }

        public String getIdcard() {
            return idcard;
        }

        public String getAttendee_avatar() {
            return attendee_avatar;
        }

        public String getExhibitor_info_website() {
            return exhibitor_info_website;
        }

        public int getRight_status() {
            return right_status;
        }

        public String getAge() {
            return age;
        }

        public String getCompany_details() {
            return company_details;
        }

        public String getUsername() {
            return username;
        }

        public ArrayList<ExhibitorSponsorLevels> getExhibitorSponsorLevels() {
            return exhibitorSponsorLevels;
        }

        public ExhibitorList(JsonObject jsonObject){
            booth_hall=jsonObject.get("booth_hall").getAsString();
            exhibitor_info_company=jsonObject.get("exhibitor_info_company").getAsString();
            company_address=jsonObject.get("company_address").getAsString();
            exhibitor_info_logo=jsonObject.get("exhibitor_info_logo").getAsString();
            exhibitor_info_email=jsonObject.get("exhibitor_info_email").getAsString();
            audit=jsonObject.get("audit").getAsInt();
            condition=jsonObject.get("condition").getAsInt();
            exhibitor_info_id=jsonObject.get("exhibitor_info_id").getAsInt();
            status=jsonObject.get("status").getAsInt();
            company=jsonObject.get("company").getAsString();
            exhibitor_id=jsonObject.get("exhibitor_id").getAsInt();
            form_id=jsonObject.get("form_id").getAsInt();
            booth_no=jsonObject.get("booth_no").getAsString();
            email_address=jsonObject.get("email_address").getAsString();
            event_id=jsonObject.get("event_id").getAsInt();
            if (jsonObject.get("exhibitorSponsorLevels")!=null||jsonObject.get("exhibitorSponsorLevels").isJsonArray()){
                JsonArray sponsorLevels=jsonObject.get("exhibitorSponsorLevels").getAsJsonArray();
                exhibitorSponsorLevels=new ArrayList<>(sponsorLevels.size());
                for (int i = 0; i < sponsorLevels.size(); i++) {
                   exhibitorSponsorLevels.add(new ExhibitorSponsorLevels(sponsorLevels.get(i).getAsJsonObject()));
                }
            }
        }
    }

    public static class ExhibitorSponsorLevels{
        private int exhibitorSponsorLevelId;
        private int exhibitorId;
        private int levelId;
        private String nameKey;
        private String description;
        private String price;
        private int superEventId;
        private String eventLocal;
        private String state;

        public int getExhibitorSponsorLevelId() {
            return exhibitorSponsorLevelId;
        }

        public int getExhibitorId() {
            return exhibitorId;
        }

        public int getLevelId() {
            return levelId;
        }

        public String getNameKey() {
            return nameKey;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }

        public int getSuperEventId() {
            return superEventId;
        }

        public String getEventLocal() {
            return eventLocal;
        }

        public String getState() {
            return state;
        }

        public ExhibitorSponsorLevels(JsonObject jsonObject){
            exhibitorSponsorLevelId=jsonObject.get("exhibitorSponsorLevelId").getAsInt();
            exhibitorId=jsonObject.get("exhibitorId").getAsInt();
            levelId=jsonObject.get("levelId").getAsInt();
            nameKey=jsonObject.get("nameKey").getAsString();
            description=jsonObject.get("description").getAsString();
            price=jsonObject.get("price").getAsString();
            eventLocal=jsonObject.get("eventLocal").getAsString();
            state=jsonObject.get("state").getAsString();
            superEventId=jsonObject.get("superEventId").getAsInt();
        }
    }
}
