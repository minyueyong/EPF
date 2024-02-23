package com.kwsp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import com.kwsp.model.WDGPAR;

@Repository
public interface WDGPARRepository   extends MongoRepository<WDGPAR, String> {

	@Query(value = "{'WDPRNM': ?0, 'WDPITM': ?1}", fields = "{'WDTVAL': 1, '_id': 0}")
	 String findWDTVALByWDPRNMAndWDPITM(String WDPRNM, String WDPITM);
}
