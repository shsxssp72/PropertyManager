package com.Property.Service.Impl;

import com.Property.Dao.ChargingSituationDao;
import com.Property.Dao.ProprietorDao;
import com.Property.Dao.SuggestionDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.ChargingSituation;
import com.Property.Domain.Proprietor;
import com.Property.Domain.Suggestion;
import com.Property.Domain.Ticket;
import com.Property.Service.ProprietorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProprietorServiceImpl implements ProprietorService {

    @Autowired
    private ChargingSituationDao chargingSituationDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private SuggestionDao suggestionDao;
    @Autowired
    private ProprietorDao proprietorDao;

    @Override
    public List<ChargingSituation> getPayment(String id) {
        return chargingSituationDao.getPayment(id);
    }

    @Override
    public List<ChargingSituation> getPaymentHistory(String id) {
        return chargingSituationDao.getPaymentHistory(id);
    }

    @Override
    public int prprtCallRepair(Ticket ticket) {
        return ticketDao.prprtCallRepair(ticket);
    }

    @Override
    public List<Ticket> getRepairHistory(String id) {
        return ticketDao.getRepairHistory(id);
    }

    @Override
    public int updateRepairFB(int feedback, String prprt_id, String ticket_id) {
        return ticketDao.updateRepairFB(feedback, prprt_id, ticket_id);
    }

    @Override
    public int giveAdvice(Suggestion suggestion) {
        return suggestionDao.giveAdvice(suggestion);
    }

    @Override
    public List<Suggestion> getAdviceHistory(String id) {
        return suggestionDao.getAdviceHistory(id);
    }

    @Override
    public Proprietor getSelfInfo(String id) {
        return proprietorDao.getSelfInfo(id);
    }

    @Override
    public int alterTel(String tel, String id) {
        return proprietorDao.alterTel(tel,id);
    }

    @Override
    public List<ChargingSituation> getPaymentbyParams(Map<String, Object> params) {
        return chargingSituationDao.getPaymentbyParams(params);
    }
}
