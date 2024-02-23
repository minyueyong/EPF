package com.kwsp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kwsp.model.WDDBCRL2;


@Repository
public interface WDDBCRL2Repository   extends MongoRepository<WDDBCRL2, String> {
	
	 List<WDDBCRL2> findByWDSTS8GreaterThan(Date date);
	 
	 //List<WDDBCRL2> findByWDSTS8GreaterThanAndMemberNo(Date date, String memberNo);
}
