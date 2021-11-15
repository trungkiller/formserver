package com.example.FormServer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.FormServer.entity.ItemsMetaEntity;

@Repository
public interface ItemsMetaRepository extends CrudRepository<ItemsMetaEntity, Integer>  {
	
}
