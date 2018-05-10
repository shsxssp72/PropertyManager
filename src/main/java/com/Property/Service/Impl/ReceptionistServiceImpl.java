package com.Property.Service.Impl;

import com.Property.Dao.ChargingSituationDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.SuggestionDao;
import com.Property.Domain.Staff;
import com.Property.Domain.Suggestion;
import com.Property.Service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

public class ReceptionistServiceImpl implements ReceptionistService {

    @Autowired
    private ChargingSituationDao chargingSituationDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private SuggestionDao suggestionDao;

    @Override
    public int updateChargingSituation(String collector_id, Date charge_date, String fee_id) {
        return chargingSituationDao.updateChargingSituation(collector_id, charge_date, fee_id);
    }

    @Override
    public Staff getSelfInfo(String id) {
        return staffDao.getSelfInfo(id);
    }

    @Override
    public int createAdvice(Suggestion suggestion) {
        return suggestionDao.createAdvice(suggestion);
    }
}
