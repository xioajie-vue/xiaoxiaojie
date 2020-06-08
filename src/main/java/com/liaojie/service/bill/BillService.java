package com.liaojie.service.bill;

import com.liaojie.entity.Bill;
import com.liaojie.entity.Role;

import java.util.List;

public interface BillService {

    /**
     * 查询所有的供应商表总数
     *
     * @param queryProductName
     * @param queryProviderId
     * @return
     */
    public int getBIllCount(String queryProductName, int queryProviderId);

    List<Bill> getBillList(String queryProductName, int queryProviderId, int currentPageNo, int pageSize);

    public Bill getBillById(Integer billid);

    public boolean update(Bill bill);

    public boolean del(Integer id);
}
