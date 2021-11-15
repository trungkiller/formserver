package com.example.FormServer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FormServer.entity.FormEntity;
import com.example.FormServer.repository.FormRepository;

@Service
public class FormService implements FormRepository {
	
	@Autowired
	public FormRepository formRepository;
	
	public Iterable<FormEntity> findAll() {
		System.out.println("Đấ");
        return (Iterable<FormEntity>) formRepository.findAll();
    }
	
	public void deleteForm(int id) {
		formRepository.deleteById(id);
	}
	
	public FormEntity saveForm(FormEntity form) {
		return formRepository.save(form);
	}
	
	public Optional<FormEntity> findById(int id) {
		return formRepository.findById(id);
	}

	@Override
	public <S extends FormEntity> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends FormEntity> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FormEntity> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<FormEntity> findAllById(Iterable<Integer> ids) {
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
	public void delete(FormEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends FormEntity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

		
}