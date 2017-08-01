package com.pointwest.workforce.planner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.data.OpportunityTnlRawRepository;
import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.service.UploadDataService;

@Service
public class UploadDataServiceImpl implements UploadDataService {

	@Autowired
	OpportunityTnlRawRepository opportunityTnlRawRepository;

	private static final Logger log = LoggerFactory.getLogger(UploadDataServiceImpl.class);

	@Override
	public List<OpportunityTnlRaw> fetchAllOpportunityTnlRaw() {
		List<OpportunityTnlRaw> opportunitTnlRaws = new ArrayList<OpportunityTnlRaw>();
		opportunityTnlRawRepository.findAll().forEach(opportunitTnlRaws::add);
		return opportunitTnlRaws;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<OpportunityTnlRaw> saveOpportunityTnlRaw(List<OpportunityTnlRaw> opportunityTnlRaws) {
		// TODO Auto-generated method stub
		List<OpportunityTnlRaw> saved = null;
		try {
			saved = (List<OpportunityTnlRaw>) opportunityTnlRawRepository.save(opportunityTnlRaws);
		} catch(Exception e) {
			log.error("Exception in saving opp tnl " + e.getMessage());
			saved = null;
		}
		return saved;
	}

}
