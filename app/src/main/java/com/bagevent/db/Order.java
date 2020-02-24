package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2017/4/17.
 */
@Table(database = AppDatabase.class)
public class Order extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public int eventId;
    @Column
    public String accountType;
    @Column
    public String apiUserName;
    @Column
    public int audit;
    @Column
    public boolean auditOrder;
    @Column
    public String bankCode;
    @Column
    public int buySource;
    @Column
    public int buyWay;
    @Column
    public String buyerCellphone;
    @Column
    public String buyerEmail;
    @Column
    public String buyerFirstName;
    @Column
    public String buyerIp;
    @Column
    public String buyerLastName;
    @Column
    public String buyerName;
    @Column
    public String currencySign;
    @Column
    public String deviceName;
    @Column
    public String discountCode;
    @Column
    public float discountPrice;
    @Column
    public int discountType;
    @Column
    public String expireTime;
    @Column
    public int hasGroup;
    @Column
    public float invoiceTaxPrice;
    @Column
    public int needInvoice;
    @Column
    public String notes;
    @Column
    public int orderId;
    @Column
    public String orderNumber;
    @Column
    public int orderStatus;
    @Column
    public String orderTime;
    @Column
    public int payOrderId;
    @Column
    public int payStatus;
    @Column
    public int payWay;
    @Column
    public int quantity;
    @Column
    public float rawTotalPrice;
    @Column
    public String sessionId;
    @Column
    public String status;
    @Column
    public float totalFee;
    @Column
    public float ticketOrderTotalPrice;
    @Column
    public float totalPrice;
    @Column
    public String updateTime;
    @Column
    public String areaCode;
    @Column
    public String sort;
    @Column
    public int buyingGroupId;
    @Column
    public int buyingGroupState;
}
