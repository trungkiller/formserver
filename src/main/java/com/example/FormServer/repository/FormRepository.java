package com.example.FormServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.FormServer.entity.FieldsEntity;
import com.example.FormServer.entity.FormEntity;

@Repository
public interface FormRepository extends CrudRepository<FormEntity, Integer>  {
	
}
