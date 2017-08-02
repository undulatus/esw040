package com.pointwest.workforce.planner.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.pointwest.workforce.planner.domain.WorkbookDataSource;

public class KeyUtil {
	
	public static String getKey(WorkbookDataSource workbookDataSource){
		URL url = null;
		try {
			url = new URL(workbookDataSource.getUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		String[] parts = url.getPath().split("/");
		
		return parts[3];
		
	}
}
