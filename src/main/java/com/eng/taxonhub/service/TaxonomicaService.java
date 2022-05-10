package com.eng.taxonhub.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class TaxonomicaService {
	
	public String VerifyVersion() throws Exception {
		String url = "http://www.worldfloraonline.org/downloadData";
		Document doc = Jsoup.connect(url).get();
		
		Element element = doc.getElementsByTag("tr").first();
		String result = element.childNodes().get(3).childNodes().get(0).toString();
		return result;
	}
	
	
}
