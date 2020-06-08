package com.liaojie.dao.bill;

import com.liaojie.entity.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    public List<Bill> getBillList(@Param("productName") String productName,
                                  @Param("providerId") int providerId,
                                  @Param("currentPageNo") int currentPageNo,
                                  @Param("pageSize") int pageSize);

    /**
     * 查询所有的订单表数量
     *
     * @param queryProductName
     * @param queryProviderId
     * @return
     */
    public int getBIllCount(@Param("productName") String queryProductName, @Param("providerId") int queryProviderId);

    public Bill getBillById(@Param("id") Integer billid);

    public int update(Bill bill);

    public int del(Integer id);
}
