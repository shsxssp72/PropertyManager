package com.Property.Controller;

import com.Property.Service.CleanService;
import com.Property.Service.OverhaulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/repairman")
public class OverhaulController {

    @Autowired
    private OverhaulService overhaulService;

    @RequestMapping("/tbdOverhaul")
    public ModelAndView tbdOverhaul(HttpServletRequest request, HttpServletResponse response){
        //查看待完成检修任务
        return new ModelAndView();
    }

    @RequestMapping("/finishOverhaul")
    public ModelAndView finishOverhaul(HttpServletRequest request, HttpServletResponse response){
        //完成检修任务
        return new ModelAndView();
    }

    @RequestMapping("/overhaulHistory")
    public ModelAndView overhaulHistory(HttpServletRequest request, HttpServletResponse response){
        //查看历史检修任务
        return new ModelAndView();
    }

    @RequestMapping("/tbdTicket")
    public ModelAndView tbdTicket(HttpServletRequest request, HttpServletResponse response){
        //查看待完成工单
        return new ModelAndView();
    }

    @RequestMapping("/finishTicket")
    public ModelAndView finishTicket(HttpServletRequest request, HttpServletResponse response){
        //完成工单
        return new ModelAndView();
    }

    @RequestMapping("/finishHistory")
    public ModelAndView finishHistory(HttpServletRequest request, HttpServletResponse response){
        //完成工单历史
        return new ModelAndView();
    }

    @RequestMapping("/createTicket")
    public ModelAndView createTicket(HttpServletRequest request, HttpServletResponse response){
        //发起工单
        return new ModelAndView();
    }

    @RequestMapping("/createHistory")
    public ModelAndView createHistory(HttpServletRequest request, HttpServletResponse response){
        //发起工单历史
        return new ModelAndView();
    }

    @RequestMapping("/selfInfo")
    public ModelAndView selfInfo(HttpServletRequest request, HttpServletResponse response){
        //查看个人信息
        return new ModelAndView();
    }
}
