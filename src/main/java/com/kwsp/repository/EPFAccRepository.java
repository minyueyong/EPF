package com.kwsp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kwsp.model.EPFAccDetails;



@Repository
public interface EPFAccRepository extends MongoRepository<EPFAccDetails, String>   {

}
