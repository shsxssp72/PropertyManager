package com.Property.Controller;

import com.Property.Service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    private ReceptionistService receptionistService;

    @RequestMapping("/updateChargingSituation")
    public ModelAndView updateChargingSituation(HttpServletRequest request, HttpServletResponse response){
        //面对面缴费
        return new ModelAndView();
    }

    @RequestMapping("/createAdvice")
    public ModelAndView createAdvice(HttpServletRequest request, HttpServletResponse response){
        //面对面意见
        return new ModelAndView();
    }

    @RequestMapping("/selfInfo")
    public ModelAndView selfInfo(HttpServletRequest request, HttpServletResponse response){
        //查看个人信息
        return new ModelAndView();
    }
}
