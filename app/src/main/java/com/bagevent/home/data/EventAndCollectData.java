package com.bagevent.home.data;

import com.bagevent.new_home.data.IncomeData;
import com.bagevent.new_home.data.NewAttendeeData;
import com.bagevent.new_home.data.SaleMoneyData;

/**
 * Created by zwj on 2016/7/12.
 */
public class EventAndCollectData {

    private IncomeData incomeDatas;
    private SaleMoneyData saleMoney;
    private NewAttendeeData newAttendeeDatas;

    private String mark;
    private String address;
    private int attendeeCount;
    private int auditCount;
    private int brand;
    private int checkinCount;
    private int collectInvoice;
    private String endTime;
    private int eventId;
    private String eventName;
    private int eventType;
    private double income;
    private String logo;
    private String officialWebsite;
    private int participantsCount;
    private String startTime;
    private int status;
    private int ticketCount;
    private int websiteId;
    private int sType;

    private String collectionName;
    private String eventTypes;
    private int collectPointId;
    private int export;
    private int isRepeat;
    private String ticketIds;
    private String ticketIdStr;

    public String getTicketIdStr() {
        return ticketIdStr;
    }

    public void setTicketIdStr(String ticketIdStr) {
        this.ticketIdStr = ticketIdStr;
    }

    public int getsType() {
        return sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public SaleMoneyData getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(SaleMoneyData saleMoney) {
        this.saleMoney = saleMoney;
    }

    public IncomeData getIncomeDatas() {
        return incomeDatas;
    }

    public void setIncomeDatas(IncomeData incomeDatas) {
        this.incomeDatas = incomeDatas;
    }

    public NewAttendeeData getNewAttendeeDatas() {
        return newAttendeeDatas;
    }

    public void setNewAttendeeDatas(NewAttendeeData newAttendeeDatas) {
        this.newAttendeeDatas = newAttendeeDatas;
    }

    public String getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(String ticketIds) {
        this.ticketIds = ticketIds;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(int auditCount) {
        this.auditCount = auditCount;
    }

    public int getCheckinCount() {
        return checkinCount;
    }

    public void setCheckinCount(int checkinCount) {
        this.checkinCount = checkinCount;
    }

    public int getCollectInvoice() {
        return collectInvoice;
    }

    public void setCollectInvoice(int collectInvoice) {
        this.collectInvoice = collectInvoice;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
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

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(int websiteId) {
        this.websiteId = websiteId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(String eventTypes) {
        this.eventTypes = eventTypes;
    }

    public int getCollectPointId() {
        return collectPointId;
    }

    public void setCollectPointId(int collectPointId) {
        this.collectPointId = collectPointId;
    }

    public int getExport() {
        return export;
    }

    public void setExport(int export) {
        this.export = export;
    }

    public int getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(int isRepeat) {
        this.isRepeat = isRepeat;
    }
}
