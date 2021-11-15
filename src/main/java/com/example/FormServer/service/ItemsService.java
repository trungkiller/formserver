package com.example.FormServer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FormServer.entity.ItemsEntity;
import com.example.FormServer.repository.ItemsRepository;

@Service
public class ItemsService implements ItemsRepository {

	@Autowired
	public ItemsRepository itemsRepository;

	public List<ItemsEntity> findAll() {
		return (List<ItemsEntity>) itemsRepository.findAll();
	}

	public ItemsEntity saveItem(ItemsEntity Item) {
		System.out.println("Ffsadfasf" + Item.toString());
		return itemsRepository.save(Item);
	}

	public ItemsEntity getItemById(int id) {
		return itemsRepository.findById(id).get();
	}

	public void deleteItem(int id) {
		itemsRepository.deleteById(id);
	}

	@Override
	public <S extends ItemsEntity> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ItemsEntity> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ItemsEntity> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ItemsEntity> findAllById(Iterable<Integer> ids) {
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
	public void delete(ItemsEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends ItemsEntity> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

//	@Override
//	public ItemsEntity findNameItems(String name) {
//		return itemsRepository.findNameItems(name);
//	}
}
