package com.Property.Controller;

import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Ticket;
import com.Property.Service.ProprietorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ProprietorController {

    @Autowired
    private ProprietorService proprietorService;

    @RequestMapping("/payment")
    public ModelAndView payment(){
        List<ChargingSituation> chargingSituationList = proprietorService.getPayment("P1000931623");
        ModelAndView modelAndView = new ModelAndView("dashboard_Test");
        modelAndView.addObject("chargingSituationList",chargingSituationList);
        return modelAndView;
    }

    @RequestMapping("/history")
    public ModelAndView history(){
        List<ChargingSituation> chargingSituationList = proprietorService.getPaymentHistory("P1000931623");
        ModelAndView modelAndView = new ModelAndView("dashboard_Test");
        modelAndView.addObject("chargingSituationList",chargingSituationList);
        return modelAndView;
    }

    @RequestMapping("/callRepair")
    public ModelAndView callRepair(){
        /*Ticket ticket = new Ticket();
        ticket.setTicket_id("T1923858786");
        ticket.setTicket_type("pipe");
        ticket.setInitiator_prprt_id("P1000931623");
        ticket.setSubarea_id("SA92664");
        ticket.setAprt_building("B54807");
        ticket.setAprt_floor(47);
        ticket.setAprt_room_num(0);
        ticket.setDescription("水管损坏");
        proprietorService.prprtCallRepair(ticket);*/
        proprietorService.updateRepairFB(8,"P1000931623", "T1923858785");
        List<Ticket> ticketList = proprietorService.getRepairHistory("P1000931623");
        ModelAndView modelAndView = new ModelAndView("dashboard_Test");
        modelAndView.addObject("ticketList",ticketList);
        return modelAndView;
    }
}
