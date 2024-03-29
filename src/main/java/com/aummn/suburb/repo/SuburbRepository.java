/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aummn.suburb.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aummn.suburb.entity.Suburb;

/**
 * Repository class for <code>Suburb</code> domain objects
 *
 * @author James Jin
 * 
 */
@Repository
public interface SuburbRepository extends JpaRepository<Suburb, Long> {

    List<Suburb> findByPostcode(String postcode);

    List<Suburb> findByNameIgnoreCase(String name);
    
    Optional<Suburb> findByNameAndPostcodeIgnoreCase(String name, String postcode);
    
    Optional<Suburb> findById(Long id);
}
