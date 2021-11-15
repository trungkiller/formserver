package com.example.FormServer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.FormServer.entity.FieldsEntity;

@Repository
public interface FieldsRepository extends CrudRepository<FieldsEntity, Integer>  {
	@Query(value = "SELECT * FROM wp_frm_fields WHERE name = ?1 limit 1;", nativeQuery = true)
	public FieldsEntity findIdField(String name);
}

