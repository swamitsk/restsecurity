package com.test.restsecurity.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/*
 * This is the implementation class for the FileService. The class implements the methods for searching a given file with 
 * top n words based on word count, Also returns the occurrence of words. If the request comes as an url encoded format,
 * the request is obtained from the httprequest and the same is parsed using the method getCountForStringContent
 */


@Service
public class FileServiceHandler implements FileService {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(FileServiceHandler.class);


	@Override
	public String getCountForWords(List<String> inputList) {
		LOG.debug("input List:"+inputList);
		if(inputList == null || inputList.isEmpty())
			return null;
		Map<String, Integer> countByWords = new HashMap<String, Integer>();
		
		inputList.forEach(str -> {
			countByWords.put(str.toLowerCase(), 0);
		});
		
	    Scanner s = null;
		try {
			s = new Scanner(new File(FileLocation));
		} catch (FileNotFoundException e1) {
			LOG.error(e1.getMessage());
				e1.printStackTrace();
		}
	    while (s.hasNext()) {
	        String next = s.next().toLowerCase().replaceAll("[^a-zA-Z]","");
	       
	        Integer count = countByWords.get(next);
	        if(count == null)
	        	continue;
	        else if (count != 0) {
	            countByWords.put(next, count + 1);
	        } else {
	            countByWords.put(next, 1);
	        }
	    }
	    s.close();
	    
	    LOG.debug("output List:"+countByWords);
	    return new Gson().toJson(countByWords);

	}

	@Override
	public String getTopNWords(int num) {
	
		Map<String, Integer> countByWords = new HashMap<String, Integer>();
	    LOG.debug("input count:"+num);

	    Scanner s = null;
		try {
			s = new Scanner(new File(FileLocation));
		} catch (FileNotFoundException e1) {
			LOG.error(e1.getMessage());
			e1.printStackTrace();
		}
	    while (s.hasNext()) {
	        String next = s.next().toLowerCase().replaceAll("[^a-zA-Z]","");
	      
	        Integer count = countByWords.get(next);
	        if (count != null) {
	            countByWords.put(next, count + 1);
	        } else {
	            countByWords.put(next, 1);
	        }
	    }
	    StringBuffer resultMap = new StringBuffer();
	    countByWords.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(num).forEachOrdered(x -> resultMap.append(x.getKey()+"|"+ x.getValue()+"\n"));
	    
	    s.close();
	    LOG.debug("output list:"+resultMap);
	    return resultMap.toString();
		}

	/*
	 * This method is for the default curl command handling with a urlencoded request string.
	 * Ideally it should be a json request. As it will be right way to map.
	 * 
	 */
	
	@Override
	public String getCountForStringContent(Set<String> stringInput) {
		for(String val : stringInput){
			String s = val.replaceAll("[^a-zA-Z0-9,:]","");
			return getCountForWords(Arrays.asList(s.split(":")[1].split("\\s*,\\s*")));
		}
	    LOG.debug("Sending empty response for "+stringInput);
		return null;
	}
	@Override
	public void setFileLocation(String s) {
		
	}
	
	

}
