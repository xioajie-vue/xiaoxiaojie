package com.liaojie.dao.provider;

import com.liaojie.entity.Provider;

import java.util.List;

public interface ProviderMapper {
    public List<Provider> Prolist();//显示所有的供应商数据

    public List<Provider> ProFuzzyQuery(Provider provider);//按照供应商名称查询信息

    public int ProAdd(Provider provider);
}