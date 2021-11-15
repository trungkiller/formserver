package com.example.FormServer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FormServer.entity.ItemsMetaEntity;
import com.example.FormServer.repository.ItemsMetaRepository;

@Service
public class ItemsMetaService implements ItemsMetaRepository {
	
	@Autowired
	public ItemsMetaRepository itemsMetaRepository;
	
	public List<ItemsMetaEntity> findAll() {
        return (List<ItemsMetaEntity>) itemsMetaRepository.findAll();
    }
	
	public ItemsMetaEntity saveItemMeta(ItemsMetaEntity ItemMeta) {
		return itemsMetaRepository.save(ItemMeta);
	}
	
	public ItemsMetaEntity getItemMetaById(int id) {
		return itemsMetaRepository.findById(id).get();
	}
	
	public void deleteItemMeta(int id) {
		itemsMetaRepository.deleteById(id);
	}

	@Override
	public <S extends ItemsMetaEntity> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ItemsMetaEntity> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ItemsMetaEntity> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ItemsMetaEntity> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ItemsMetaEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends ItemsMetaEntity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemsMetaEntity findNameItems(int id) {
		return itemsMetaRepository.findNameItems(id);
	}
}