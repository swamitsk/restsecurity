package com.test.restsecurity.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.restsecurity.service.FileService;

@RestController
@RequestMapping("/counter-api")

public class FileController{

	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;


	@RequestMapping(value = "/top/{count}", method = RequestMethod.GET, headers="Accept=text/csv")
	public String  getTopWordCount(@PathVariable int count) {
		LOG.debug("Received a get request for top n with count:"+count);
		return fileService.getTopNWords(count);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody String getWordCountList(@RequestBody Map<String,ArrayList<String>> map) {
		LOG.debug("Received a post request for given string list:"+map);
		return fileService.getCountForWords(map.get(FileService.SEARCHTEXT));
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, headers="Accept=application/json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public @ResponseBody String getWordCountList(HttpServletRequest req) {
		LOG.debug("Received a post request for given string list:"+req.getParameterMap());
		return fileService.getCountForStringContent(req.getParameterMap().keySet());
	}
}