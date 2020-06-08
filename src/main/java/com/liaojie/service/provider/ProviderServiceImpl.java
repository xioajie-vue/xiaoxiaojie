package com.liaojie.service.provider;

import com.liaojie.dao.provider.ProviderMapper;
import com.liaojie.entity.Provider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
    @Resource
    private ProviderMapper providerMapper;

    public ProviderMapper getProviderMapper() {
        return providerMapper;
    }

    public void setProviderMapper(ProviderMapper providerMapper) {
        this.providerMapper = providerMapper;
    }


    @Override
    public List<Provider> findProviderWithConditions() {
        try {
            return providerMapper.Prolist();//调用DAO方法实现查询
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Provider> findProFuzzyQuery(Provider provider) {
        try {
            return providerMapper.ProFuzzyQuery(provider);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public boolean addNewPro(Provider provider) {
        boolean result = false;
        try {
            if (providerMapper.ProAdd(provider) == 1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

}
