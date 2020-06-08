package com.liaojie.service.provider;


import com.liaojie.entity.Provider;
import com.liaojie.entity.User;

import java.util.List;

public interface ProviderService {
    public List<Provider> findProviderWithConditions(); //查询所有

    public List<Provider> findProFuzzyQuery(Provider provider);//按照供应商名称查询信息

    public boolean addNewPro(Provider provider);
}
