package com.bagevent.db;

import com.bagevent.activity_manager.manager_fragment.data.InvoiceListData;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ZWJ on 2017/12/28 0028.
 */
@Table(database = AppDatabase.class)
public class Invoice extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public int userId;
    @Column
    public String eventId;
    @Column
    public String buyerCellphone;
    @Column
    public String buyerEmail;
    @Column
    public String buyerFirstName;
    @Column
    public String buyerLastName;
    @Column
    public String buyerName;
    @Column
    public int orderId;
    @Column
    public double payTotalPrice;
    @Column
    public double rawTotalPrice;
    @Column
    public String address;
    @Column
    public String bankAccount;
    @Column
    public String brief;
    @Column
    public String cellphone;
    @Column
    public String companyBank;
    @Column
    public String companyCode;
    @Column
    public String companyFinanceContact;
    @Column
    public String companyRegAddr;
    @Column
    public int confirm;
    @Column
    public String eInvoiceCellPhone;
    @Column
    public String expressCompany;
    @Column
    public String expressNum;
    @Column
    public String invoiceCode;
    @Column
    public String invoiceItem;
    @Column
    public String invoiceNumber;
    @Column
    public String invoiceTitle;
    @Column
    public int invoiceType;
    @Column
    public int obtaied;
    @Column
    public int obtainMethod;
    @Column
    public int obtainWay;
    @Column
    public int orderInvoiceId;
    @Column
    public String orderNumber;
    @Column
    public String receiver;
    @Column
    public int receiverType;
    @Column
    public String taxNum;
    @Column
    public String updateTime;
    @Column
    public int useCompanyCode;
}
