package com.bagevent.activity_manager.manager_fragment.data;

import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.new_home.data.ExhibitionListData;
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
 * time 2019/03/12
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorDetail {
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

    public ExhibitorDetail(JsonObject jsonObject){
        if (jsonObject.get("respType") != null) {
            respType = jsonObject.get("respType").getAsInt();
        }
        if (jsonObject.get("retStatus") != null) {
            retStatus = jsonObject.get("retStatus").getAsInt();
        }
        if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonObject()) {
            respObject = new RespObjectBean(jsonObject.getAsJsonObject("respObject"));
        }
    }

    public static class RespObjectBean{
        private String contact;
        private int rank;
        private ArrayList<ExExhibitorReceiptOrgs> exExhibitorReceiptOrgs;
        private ExExhibitorInfo exExhibitorInfo;

        public String getContact() {
            return contact;
        }

        public int getRank() {
            return rank;
        }

        public ArrayList<ExExhibitorReceiptOrgs> getExExhibitorReceiptOrgs() {
            return exExhibitorReceiptOrgs;
        }

        public ExExhibitorInfo getExExhibitorInfo() {
            return exExhibitorInfo;
        }

        public RespObjectBean(JsonObject jsonObject){
               contact=jsonObject.get("contact").getAsString();
               rank=jsonObject.get("rank").getAsInt();
               if (jsonObject.get("exExhibitorReceiptOrgs")!=null||jsonObject.get("exExhibitorReceiptOrgs").isJsonArray()){
                   JsonArray jsonArray = jsonObject.get("exExhibitorReceiptOrgs").getAsJsonArray();
                   exExhibitorReceiptOrgs = new ArrayList<>(jsonArray.size());
                   for (int i = 0; i < jsonArray.size(); i++) {
                       exExhibitorReceiptOrgs.add(new ExExhibitorReceiptOrgs(jsonArray.get(i).getAsJsonObject()));
                   }
               }
               if (jsonObject.get("exExhibitorInfo").isJsonObject()){
                   exExhibitorInfo=new ExExhibitorInfo(jsonObject.getAsJsonObject("exExhibitorInfo"));
               }
        }
    }

    public static class ExExhibitorReceiptOrgs{

        private int exhibitorReceiptOrgId;
        private String receiptTime;
        private String amount;
        private String paymentVoucher;
        private int confirm;
        private String confirmTime;
        private String transferTime;
        private int type;
        private String sort;
        private String state;
        private String remark;
        private String createTime;
        private String updateTime;

        public int getExhibitorReceiptOrgId() {
            return exhibitorReceiptOrgId;
        }

        public String getReceiptTime() {
            return receiptTime;
        }

        public String getAmount() {
            return amount;
        }

        public String getPaymentVoucher() {
            return paymentVoucher;
        }

        public int getConfirm() {
            return confirm;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public String getTransferTime() {
            return transferTime;
        }

        public int getType() {
            return type;
        }

        public String getSort() {
            return sort;
        }

        public String getState() {
            return state;
        }

        public String getRemark() {
            return remark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public ExExhibitorReceiptOrgs(JsonObject jsonObject){
              exhibitorReceiptOrgId=jsonObject.get("exhibitorReceiptOrgId").getAsInt();
              amount=jsonObject.get("amount").getAsString();
              receiptTime=jsonObject.get("receiptTime").getAsString();
              paymentVoucher=jsonObject.get("paymentVoucher").getAsString();
              confirm=jsonObject.get("confirm").getAsInt();
              confirmTime=jsonObject.get("confirmTime").getAsString();
              transferTime=jsonObject.get("transferTime").getAsString();
              type=jsonObject.get("type").getAsInt();
              sort=jsonObject.get("sort").getAsString();
              state=jsonObject.get("state").getAsString();
              remark=jsonObject.get("remark").getAsString();
              createTime=jsonObject.get("createTime").getAsString();
              updateTime=jsonObject.get("updateTime").getAsString();

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
