package com.example.FormServer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.FormServer.entity.ItemsEntity;

@Repository
public interface ItemsRepository extends CrudRepository<ItemsEntity, Integer>  {
	
//	@Query(value = "SELECT * FROM wp_frm_items WHERE name = ?1;", nativeQuery = true)
//	public ItemsEntity findNameItems(String name);
}