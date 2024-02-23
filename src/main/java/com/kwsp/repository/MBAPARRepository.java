package com.kwsp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.kwsp.model.MBAPAR;

@Repository
public interface MBAPARRepository extends MongoRepository<MBAPAR, String>   {

}
