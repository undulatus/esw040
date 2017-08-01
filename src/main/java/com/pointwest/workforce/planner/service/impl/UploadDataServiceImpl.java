package com.pointwest.workforce.planner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.data.OpportunityTnlRepository;
import com.pointwest.workforce.planner.domain.OpportunityTnl;
import com.pointwest.workforce.planner.service.UploadDataService;

@Service
public class UploadDataServiceImpl implements UploadDataService {

	@Autowired
	OpportunityTnlRepository opportunityTnlRepository;

	private static final Logger log = LoggerFactory.getLogger(UploadDataServiceImpl.class);

	@Override
	public List<OpportunityTnl> fetchAllOpporutnityTnl() {
		List<OpportunityTnl> opportunitTnls = new ArrayList<OpportunityTnl>();
		opportunityTnlRepository.findAll().forEach(opportunitTnls::add);
		return opportunitTnls;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<OpportunityTnl> saveOpportunityTnl(List<OpportunityTnl> opportunityTnls) {
		// TODO Auto-generated method stub
		List<OpportunityTnl> saved = null;
		try {
			saved = (List<OpportunityTnl>) opportunityTnlRepository.save(opportunityTnls);
		} catch(Exception e) {
			log.error("Exception in saving opp tnl " + e.getMessage());
			saved = null;
		}
		return saved;
	}

}
