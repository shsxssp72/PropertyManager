package com.Property.Controller;

import com.Property.Service.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/accountant")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;

    @RequestMapping("/chargingSituation")
    public ModelAndView chargingSituation(HttpServletRequest request, HttpServletResponse response){
        //查看物业缴费记录
        return new ModelAndView();
    }

    @RequestMapping("/externalCar")
    public ModelAndView externalCar(HttpServletRequest request, HttpServletResponse response){
        //查看外来车辆出入缴费记录
        return new ModelAndView();
    }

    @RequestMapping("/selfInfo")
    public ModelAndView selfInfo(HttpServletRequest request, HttpServletResponse response){
        //查看个人信息
        return new ModelAndView();
    }
}
