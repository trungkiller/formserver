package com.example.FormServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.FormServer.entity.ItemsEntity;
import com.example.FormServer.entity.ItemsMetaEntity;

@Repository
public interface ItemsMetaRepository extends CrudRepository<ItemsMetaEntity, Integer>  {
	@Query(value = "SELECT * FROM wp_frm_item_metas WHERE field_id = 69 AND item_id = ?1", nativeQuery = true)
	public ItemsMetaEntity findNameItems(int id);
	
	@Query(value = "SELECT * FROM wp_frm_item_metas WHERE field_id = ?1 AND item_id = ?2", nativeQuery = true)
	public ItemsMetaEntity findItemsMeta(int field_id, int item_id);
}
