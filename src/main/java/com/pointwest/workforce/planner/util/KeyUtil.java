package com.pointwest.workforce.planner.util;

public class KeyUtil {
	
	public static String getKey(String urlDatasource){
		
		String[] parts = urlDatasource.split("/");
		
		return parts[5];
		
	}
	
	
	public static String getContextPath(String workbookDataSourceUrl){
		String[] parts = null;
		
		if(workbookDataSourceUrl.contains("edit")){
			System.out.println("split edit");
			parts = workbookDataSourceUrl.split("/edit");
		}else if(workbookDataSourceUrl.contains("view")){
			System.out.println("split view");
			parts = workbookDataSourceUrl.split("/view");
		}
		System.out.println(parts[0]);
		return parts[0];
		
	}
}
