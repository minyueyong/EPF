package com.kwsp.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateFromString;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwsp.field.CustomField;
import com.kwsp.model.WDDBCRL2;
import com.kwsp.model.WDGPAR;
import com.kwsp.repository.WDGPARRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.kwsp.repository.WDDBCRL2Repository;

import org.bson.Document;

import com.mongodb.ExplainVerbosity;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Aggregates.addFields;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.aggregation.Fields;

@Service
public class EPFBankService {

	
	//still waiting for MBAPAR table
	Date nextProcessingDate = new Date();

	@Autowired
	private WDGPARRepository WDGPARrepository;

	@Autowired
	private WDDBCRL2Repository WDDBCRL2Repository;

	private final MongoTemplate mongoTemplate;

	@Autowired
	public EPFBankService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	// Get Validity Period in months
	public Integer getWDTVALByWDPRNMAndWDPITM() {

		Query query = new Query();
		query.addCriteria(Criteria.where("WDPRNM").is("BANK_DATA").and("WDPITM").is("AGE_CASA"));

		WDGPAR wdgpar = mongoTemplate.findOne(query, WDGPAR.class);
		String input = wdgpar != null ? wdgpar.getWDTVAL() : null;

		System.out.println("Input " + input);

		Integer validity = Integer.parseInt(input); // remove leading zero

		System.out.println("Validity " + validity);
		return validity;

	}

	// Get CASA account greater than 
	public List<String> getBankAccountDetails() {
		System.out.println("Next Processing Date " + nextProcessingDate);

		
       ChronoLocalDate subtractedDate = subtractMonthsFromDate(nextProcessingDate, getWDTVALByWDPRNMAndWDPITM());
    		   
     System.out.println("Date after subtract " + subtractedDate);
		List<Document> result = executeNativeQuery();
		
		
		// Filter the list and check if WDSTS8 is after subtracted Date
		List<String> filteredConvertedDates = result.stream().map(document -> document.getString("converted_date")) 
				.filter(convertedDateStr -> {
					// Convert the date string to LocalDate for comparison
					LocalDate convertedDate = LocalDate.parse(convertedDateStr,
							DateTimeFormatter.ofPattern("dd-MM-yyyy"));
					// Filter based on the condition (converted date is greater than (next processing date - validity in months)
					return convertedDate
							.isAfter(subtractedDate);
				}).collect(Collectors.toList());

		// Print or process the filtered converted dates
		filteredConvertedDates.forEach(System.out::println);

		return filteredConvertedDates;

	}

	// Get Processing Date

	public Date getProcessingDate() {

		return new Date();
	}

	
	//Subtract validity in month from the processing date
	public static ChronoLocalDate subtractMonthsFromDate(Date date, int monthsToSubtract) {
	
		
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		
		LocalDate subtractedDate = localDate.minusMonths(monthsToSubtract);

		// Define the desired date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		
		String formattedDateStr = subtractedDate.format(formatter);


		return LocalDate.parse(formattedDateStr, formatter).atStartOfDay(ZoneId.systemDefault()).toLocalDate();
	}

	// Convert date format for comparison
	public List<Document> executeNativeQuery() {
		
		//add a new field called "converted_date"  that have the changed format
		String pipelineJson = " { \"$addFields\": { \"converted_date\": { \"$dateToString\": { \"format\": \"%d-%m-%Y\", \"date\": { \"$dateFromString\": { \"dateString\": \"$WDSTS8\", \"format\": \"%d.%m.%Y\" } } } } } } ";

		// Parse the JSON string into a list of Bson objects
		Document pipelineDocument = Document.parse(pipelineJson);
		List<? extends Bson> pipeline = Collections.singletonList(pipelineDocument);

		// Execute the aggregation pipeline
		AggregateIterable<Document> result = mongoTemplate.getCollection("WDDBCRL2").aggregate(pipeline);
		List<Document> resultList = new ArrayList<>();
		result.into(resultList);

		System.out.println("Native Query");
		for (Document document : resultList) {
			System.out.println(document.toJson());
		}

		return resultList;
	}

}
