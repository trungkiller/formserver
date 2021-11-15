package com.example.FormServer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FormServer.entity.FieldsEntity;
import com.example.FormServer.entity.FormEntity;
import com.example.FormServer.repository.FieldsRepository;
import com.example.FormServer.repository.FormRepository;

@Service
public class FieldsService implements FieldsRepository {
	
	@Autowired
	public FieldsRepository fieldsRepository;
	
	public Iterable<FieldsEntity> findAll() {
        return (Iterable<FieldsEntity>) fieldsRepository.findAll();
    }
	
	public void deleteField(int id) {
		fieldsRepository.deleteById(id);
	}
	
	public FieldsEntity saveField(FieldsEntity form) {
		return fieldsRepository.save(form);
	}

	@Override
	public <S extends FieldsEntity> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends FieldsEntity> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FieldsEntity> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<FieldsEntity> findAllById(Iterable<Integer> ids) {
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
	public void delete(FieldsEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends FieldsEntity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FieldsEntity findIdField(String name) {
		return fieldsRepository.findIdField(name);
	}

	
}
	
