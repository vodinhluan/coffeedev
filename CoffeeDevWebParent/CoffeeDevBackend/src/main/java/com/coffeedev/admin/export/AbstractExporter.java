package com.coffeedev.admin.export;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {
	public void setPesponeHeader(String contentType,String extention, HttpServletResponse response,String prefix) throws IOException {
		SimpleDateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp=dateFormatter.format(new Date());
		String fileName=prefix+timestamp+extention;
		response.setContentType(contentType);
		String headerKey="Content-Disposition";
		String headerValue="attachment; filename="+fileName;
		response.setHeader(headerKey, headerValue);
}
}

