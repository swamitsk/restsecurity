package com.test.restsecurity.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface FileService {

	 
	    String FileLocation = "FileContent.txt";
	    final String SEARCHTEXT = "searchText";
		
		public String getCountForWords(List<String> inputList);

		public String getTopNWords(int num);
		
		public String getCountForStringContent(Set<String> stringInput);
		
		public void setFileLocation(String s);


}
