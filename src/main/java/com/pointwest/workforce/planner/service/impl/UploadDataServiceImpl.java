package com.pointwest.workforce.planner.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.Person;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;
import com.pointwest.workforce.planner.constants.ESWConstants;
import com.pointwest.workforce.planner.data.OpportunityTnlRawRepository;
import com.pointwest.workforce.planner.data.WorkbookDataSourceRepository;
import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.domain.WorkbookDataSource;
import com.pointwest.workforce.planner.nonentity.domain.OpportunityTnl;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.security.GoogleServiceAuthorization;
import com.pointwest.workforce.planner.service.UploadDataService;
import com.pointwest.workforce.planner.util.KeyUtil;


@Service
public class UploadDataServiceImpl implements UploadDataService {
	
	@Autowired
	public WorkbookDataSourceRepository workbookDataSourceRepository;
	
	@Autowired
	OpportunityTnlRawRepository opportunityTnlRawRepository;

	@Autowired
	GoogleServiceAuthorization googleServiceAuthorization;
	
	@Value("${tnl.def.rowNumStart}")
	private long rowNum;
	
	@Value("${tnl.def.worksheetTnl}")
	private String worksheetTnl;
	
	@Value("${tnl.col.workbookdatasource.id}")
	private String dataSourceId;
	
	private GoogleService googleService;

	private static final Logger log = LoggerFactory.getLogger(UploadDataServiceImpl.class);

	@Override
	public List<WorkbookDataSource> fetchAllWorkbookDataSources() {
		log.debug("MCI >> fetchAllWorkbookDataSources");
		List<WorkbookDataSource> workbookDataSources = (List<WorkbookDataSource>) workbookDataSourceRepository.findAllByOrderByStartDateTimeDesc();
		log.debug("MCO >> fetchAllWorkbookDataSources");
		return workbookDataSources;
	}
	
	@Override
	public WorkbookDataSource fetchGBookByUrl(String workbookUrl) {
		WorkbookDataSource workbookDataSource = workbookDataSourceRepository.findByUrl(workbookUrl);
		return workbookDataSource;
	}
	
	@Override
	public List<OpportunityTnlRaw> fetchAllOpportunityTnlRaw() {
		List<OpportunityTnlRaw> opportunitTnlRaws = new ArrayList<OpportunityTnlRaw>();
		opportunityTnlRawRepository.findAll().forEach(opportunitTnlRaws::add);
		return opportunitTnlRaws;
	}
	
	
//	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<OpportunityTnlRaw> saveOpportunityTnlRaw(List<OpportunityTnlRaw> opportunityTnlRaws) {
		List<OpportunityTnlRaw> saved = null;
		try {
			saved = (List<OpportunityTnlRaw>) opportunityTnlRawRepository.save(opportunityTnlRaws);
		} catch(Exception e) {
			log.error("Exception in saving opp tnl " + e.getMessage());
			saved = null;
		}
		return saved;
	}

	@Override
	public WorkbookDataSource saveGoogleWorkbook(String workbookUrl, String validationStatus) {
		Date date = new Date();
		WorkbookDataSource workbookDataSource = new WorkbookDataSource(workbookUrl, date, "Google Sheet", validationStatus, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		
		WorkbookDataSource saved = workbookDataSourceRepository.save(workbookDataSource);
		
		return saved;
	}
	
	@Override
	public void updateStartUploadValidationStatus(WorkbookDataSource workbookDataSource) {
		Date date = new Date();
		workbookDataSourceRepository.updateStartUploadValidationStatus(workbookDataSource.getWorkbookDataSourceId(), date, workbookDataSource.getValidationStatus());
	}

	@Override
	public Workbook extractGoogleWorkbook(WorkbookDataSource workbookDataSource, Long workbookDataSourceId) throws Exception {
		String key = KeyUtil.getKey(workbookDataSource);
		
		Workbook workbook = new Workbook();
		
			try {
				googleService = googleServiceAuthorization.init();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e.getMessage() + " Error with GoogleServiceAuthorization.init()");
			}
			
		      URL SPREADSHEET_FEED_URL = new URL(ESWConstants.SPREADSHEETS_FEED);

		      SpreadsheetFeed feed;
			
		      try {
				feed = googleService.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
			} catch (IOException | ServiceException e) {
				e.printStackTrace();
				throw new Exception(e.getMessage() + " Error with GoogleService.getFeed()");
			}
		      List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		      SpreadsheetEntry spreadsheetFile = null;

		      for (SpreadsheetEntry spreadsheet : spreadsheets) {
		        if (spreadsheet.getWorksheetFeedUrl().getPath().contains(key)) 
		        {
		            //get Owner Name
		        	if(workbookDataSource.getOwner() == null && workbookDataSource.getWorkbookDataSourceFileName() == null){
		        		for(Person authors : spreadsheet.getAuthors()){
		        			workbookDataSource.setOwner(authors.getName());
		        		}
		        		workbookDataSource.setWorkbookDataSourceFileName(spreadsheet.getTitle().getPlainText());
		        	}

		        	//get File Name
		          spreadsheetFile = spreadsheet;
		          break;
		        }
		      }

		      // Fetch the list feed of the worksheet
		    WorksheetEntry worksheetFile = null;
			List<WorksheetEntry> worksheets = spreadsheetFile.getWorksheets();

			  // Iterate through each worksheet in the spreadsheet.
			  for (WorksheetEntry worksheet : worksheets) {
			    // Get the worksheet's title, row count, and column count.
				  if (worksheet.getTitle().getPlainText().contains(worksheetTnl)) {
					  worksheetFile =  worksheet;
				  }
			  }
		      
			  	URL listFeedUrl = worksheetFile.getListFeedUrl();
				ListFeed listFeed = googleService.getFeed(listFeedUrl, ListFeed.class);
				List<ListEntry> listEntries = listFeed.getEntries();
				// Iterate through each row, printing its cell values


				Long rowNo = rowNum;
				List<OpportunityTnl> opportunityTnlList = new ArrayList<OpportunityTnl>();

				for (ListEntry row : listEntries) {
					
					OpportunityTnl opportunityTnl = new OpportunityTnl();
					
					Map<String, Object> dataMap = new HashMap<String, Object>();
					
					for (String tag : row.getCustomElements().getTags()) {
						String value = row.getCustomElements().getValue(tag);
						dataMap.put(tag, value);
						dataMap.put(dataSourceId, workbookDataSourceId);
					}
					
					opportunityTnl.setOpportunityTnlRow(rowNo);
					opportunityTnl.setDataMap(dataMap);

					opportunityTnlList.add(opportunityTnl);

					rowNo++;
				}
				workbookDataSource.setEntry(opportunityTnlList.size());
				workbook.setOpportunityTnl(opportunityTnlList);

//			save owner, file name, entries	
			workbookDataSourceRepository.updateRequiredFields(workbookDataSource.getWorkbookDataSourceId(), workbookDataSource.getOwner(), workbookDataSource.getWorkbookDataSourceFileName(), workbookDataSource.getEntry(), 2);
			return workbook;
			
		}
	
}
