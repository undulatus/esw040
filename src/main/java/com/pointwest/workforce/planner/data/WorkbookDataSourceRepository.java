package com.pointwest.workforce.planner.data;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.domain.WorkbookDataSource;

public interface WorkbookDataSourceRepository extends CrudRepository<WorkbookDataSource, Integer> {
	
	public WorkbookDataSource findByUrl(String url);
	
	@Transactional
	@Modifying
	@Query(value= 
			" UPDATE workbook_datasource" +
					" SET" +
					" workbook_datasource_entry = ?2," + 
					" workbook_datasource_start_upload = ?3" +
					" WHERE workbook_datasource_id = ?1"
			, nativeQuery=true)
	public void updateEntryAndStartUpload(Long id, Integer entry, Date startDateTime);
	
//	@Transactional
//	@Modifying
//	@Query(value= 
//			" UPDATE workbook_datasource" +
//			" SET" +
//			" workbook_datasource_owner = ?2," + 
//			" workbook_datasource_file_name = ?3," +
//			" business_unit_id = ?4" +
//			" WHERE workbook_datasource_id = ?1"
//			, nativeQuery=true)
//	public void saveOwnerFileNameAndBU(Long id, String owner, String Filename, String businessUnit);
	
	@Transactional
	@Modifying
	@Query(value= 
			" UPDATE workbook_datasource" +
			" SET" +
			" workbook_datasource_owner = ?2," + 
			" workbook_datasource_file_name = ?3" +
			" WHERE workbook_datasource_id = ?1"
			, nativeQuery=true)
	public void saveOwnerFileNameAndBU(Long id, String owner, String Filename);
}
