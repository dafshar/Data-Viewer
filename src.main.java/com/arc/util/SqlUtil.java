package com.arc.util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="queries")
@XmlAccessorType(XmlAccessType.FIELD)
public class SqlUtil {
	private static final String INPUT_FILE = "xml/queries.xml";
	
	private static Map<String, String> staticQueryMap = null;
	
	@XmlElement (name="queryMap")
	private Map<String, String> queryMap = null;
	
	static {
		SqlUtil.staticQueryMap = unMarshall()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
	}
	
	@SuppressWarnings("unused")
	private static Map<String, String> unMarshall() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(SqlUtil.class);
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    File queryFile = new File(INPUT_FILE);
		    SqlUtil util = (SqlUtil) jaxbUnmarshaller.unmarshal(queryFile);
		    return util.queryMap;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a prepared statement for the given connection and queryId.<br>
	 * @param connection
	 * @param queryId
	 * @return
	 */
	public static PreparedStatement getPreparedStatement(Connection connection, String queryId) { 
		try {
			return connection.prepareStatement(staticQueryMap.get(queryId));
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
