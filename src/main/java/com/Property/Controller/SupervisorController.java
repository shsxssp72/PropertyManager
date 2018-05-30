package com.Property.Controller;

import com.Property.Service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @RequestMapping("/deptStaff")
    public ModelAndView deptStaff(HttpServletRequest request, HttpServletResponse response){
        //查看所属部门员工信息
        return new ModelAndView();
    }

    @RequestMapping("/tbdTask")
    public ModelAndView tbdTask(HttpServletRequest request, HttpServletResponse response){
        //查看指定员工的待完成任务
        return new ModelAndView();
    }

    @RequestMapping("/historyTask")
    public ModelAndView historyTask(HttpServletRequest request, HttpServletResponse response){
        //查看指定员工的历史完成任务
        return new ModelAndView();
    }

    @RequestMapping("/changeTask")
    public ModelAndView changeTask(HttpServletRequest request, HttpServletResponse response){
        //重新分配任务
        return new ModelAndView();
    }

    @RequestMapping("/tbdTicket")
    public ModelAndView tbdTicket(HttpServletRequest request, HttpServletResponse response){
        //查看指定员工的待完成工单
        return new ModelAndView();
    }

    @RequestMapping("/historyTicket")
    public ModelAndView historyTicket(HttpServletRequest request, HttpServletResponse response){
        //查看指定员工的历史工单
        return new ModelAndView();
    }

    @RequestMapping("/changeTicket")
    public ModelAndView changeTicket(HttpServletRequest request, HttpServletResponse response){
        //重新分配工单
        return new ModelAndView();
    }

    @RequestMapping("/tbdOverhaul")
    public ModelAndView tbdOverhaul(HttpServletRequest request, HttpServletResponse response){
        //查看指定员工待完成检修任务
        return new ModelAndView();
    }

    @RequestMapping("/overhaulHistory")
    public ModelAndView overhaulHistory(HttpServletRequest request, HttpServletResponse response){
        //查看指定员工历史检修任务
        return new ModelAndView();
    }

    @RequestMapping("/changeOverhaul")
    public ModelAndView changeOverhaul(HttpServletRequest request, HttpServletResponse response){
        //重新分配检修任务
        return new ModelAndView();
    }
}
