package com.liaojie.controller;

import com.alibaba.fastjson.JSONArray;
import com.liaojie.entity.Bill;
import com.liaojie.entity.Provider;
import com.liaojie.entity.Role;
import com.liaojie.entity.User;
import com.liaojie.service.bill.BillService;
import com.liaojie.service.provider.ProviderService;
import com.liaojie.tools.Constants;
import com.liaojie.tools.PageSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    //定义这两个表的service好调用方法
    @Resource
    private BillService billService;

    @Resource
    private ProviderService providerService;

    /**
     * 显示所有订单表
     *
     * @param model
     * @param queryProductName
     * @param queryProviderId
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String getUserList(Model model,
                              @RequestParam(value = "queryProductName", required = false) String queryProductName,
                              @RequestParam(value = "queryProviderId", required = false) String queryProviderId,
                              @RequestParam(value = "pageIndex", required = false) String pageIndex) {

        int _queryProviderId = 0;
        List<Bill> billList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        //判断传入进入来的值是否为空
        if (queryProductName == null) {
            queryProductName = "";
        }
        //如果传进来的两个参数都为空则赋值并为queryProviderId转入int类型
        if (queryProviderId != null && !queryProviderId.equals("")) {
            _queryProviderId = Integer.parseInt(queryProviderId);
        }

        if (pageIndex != null) {
            currentPageNo = Integer.valueOf(pageIndex);
        }

        //总数量（表）
        int totalCount = billService.getBIllCount(queryProductName, _queryProviderId);

        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        billList = billService.getBillList(queryProductName, _queryProviderId, currentPageNo, pageSize);
        model.addAttribute("billList", billList);
        List<Provider> providerList = null;
        providerList = providerService.findProviderWithConditions();
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "billlist";
    }


    @RequestMapping(value = "/cha.html")
    public String getcha(Integer billid, Model model) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill", bill);
        return "billview";
    }

    @RequestMapping(value = "/xiu.html", method = RequestMethod.GET)
    public String getxiu(Integer billid, Model model) {
        List<Provider> providerList = null;
        providerList = providerService.findProviderWithConditions();
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill", bill);
        model.addAttribute("providerList", providerList);
        return "billmodify";
    }

    @RequestMapping(value = "/xiugai.html", method = RequestMethod.POST)
    public String getGai(Bill bill, HttpSession session) {
        User loginUser = (User) session.getAttribute(Constants.USER_SESSION);
        bill.setModifyBy(loginUser.getId());
        bill.setModifyDate(new Date());
        boolean result = billService.update(bill);
        if (result) {
            return "redirect:/bill/list.html";
        }
        return "billmodify";
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public Object del(@RequestParam Integer billid) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (billService.del(billid)) {
            resultMap.put("result", "true");
        } else {
            resultMap.put("result", "false");
        }
        return JSONArray.toJSONString(resultMap);
    }
}
