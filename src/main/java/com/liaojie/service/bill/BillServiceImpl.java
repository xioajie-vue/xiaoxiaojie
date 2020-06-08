package com.liaojie.service.bill;

import com.liaojie.dao.bill.BillMapper;
import com.liaojie.entity.Bill;
import com.liaojie.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("billService")
public class BillServiceImpl implements BillService {
    @Resource
    private BillMapper billMapper;

    @Override
    public int getBIllCount(String queryProductName, int queryProviderId) {
        int count = 0;
        count = billMapper.getBIllCount(queryProductName, queryProviderId);
        return count;

    }

    @Override
    public List<Bill> getBillList(String queryProductName, int queryProviderId, int currentPageNo, int pageSize) {
        int beginIndex = (currentPageNo - 1) * pageSize;
        return billMapper.getBillList(queryProductName, queryProviderId, beginIndex, pageSize);
    }

    @Override
    public Bill getBillById(Integer billid) {
        return billMapper.getBillById(billid);
    }

    @Override
    public boolean update(Bill bill) {
        return billMapper.update(bill) > 0;
    }

    @Override
    public boolean del(Integer id) {
        return billMapper.del(id) > 0;
    }
}
