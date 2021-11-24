package com.example.FormServer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FormServer.entity.FieldsEntity;
import com.example.FormServer.entity.FormEntity;
import com.example.FormServer.entity.ItemsEntity;
import com.example.FormServer.entity.ItemsMetaEntity;
import com.example.FormServer.finalclass.ConstantVariable;
import com.example.FormServer.finalclass.RandomKey;
import com.example.FormServer.service.FieldsService;
import com.example.FormServer.service.FormService;
import com.example.FormServer.service.ItemsMetaService;
import com.example.FormServer.service.ItemsService;

import com.example.FormServer.selenium.*;

@RestController

@SuppressWarnings("unchecked")
public class FormController {

	@Autowired
	public FormService formService;

	@Autowired
	public FieldsService fieldsService;

	@Autowired
	public ItemsMetaService itemsMetaService;

	@Autowired
	public ItemsService itemsService;

	@Autowired
	public Selenium selenium;

	@GetMapping("/all")
	public Iterable<FormEntity> searchDevice() {
		System.out.println("Fdssdf");
		Iterable<FormEntity> list = formService.findAll();
		return list;
	}

	@GetMapping("/getdata")
	public String getData(@RequestParam("idform") int idform, @RequestParam("page_start") String pageStart,
			@RequestParam("page_end") String pageEnd) {

		try {
			Optional<FormEntity> formEntity = formService.findById(idform);
			if (formEntity.get().getName().equals(ConstantVariable.url)) {
//			Selenium selenium = new Selenium(idform);
				if (pageStart != "") {
					if (pageEnd != "") {
						selenium.StartLoopUntil(ConstantVariable.url + pageStart, ConstantVariable.url + pageEnd,
								idform);
					} else {
						selenium.StartSpec(ConstantVariable.url + pageStart, idform);
					}
				} else {
					selenium.StartLoop(ConstantVariable.url, idform);
				}
			}
		} catch (Exception e) {
			return "0";
		}
		return "1";
	}

	@GetMapping("/stop")
	public String stopSelenium() {
		try {
			selenium.StopSelenium();
		} catch (Exception e) {
			return "0";
		}
		return "1";
	}

	@PostMapping(path = "form/add", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public JSONObject createForm(@RequestBody FormEntity forms) {
		FormEntity form = new FormEntity();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		form.setForm_key(RandomKey.getSaltString());
		form.setName(forms.getName());
		form.setCreated_at(dtf.format(now));

		JSONObject response = new JSONObject();

		try {
			formService.saveForm(form);
			response.put("message", "Thêm url thành công !");
			response.put("code", "0");
		} catch (Exception e) {
			response.put("message", e.toString());
		}

		return response;
	}

	@PostMapping(path = "field/add", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public JSONObject saveToFieldTable(@RequestBody FieldsEntity fields) {

		FieldsEntity field = new FieldsEntity();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		field.setName(fields.getName());
		field.setForm_id(6);
		field.setField_key("dasdas");
		field.setDescription(fields.getDescription());
		field.setType(fields.getType());
		field.setCreated_at(dtf.format(now));

		JSONObject response = new JSONObject();

		try {
			fieldsService.saveField(field);
			response.put("message", "Thêm field thành công !");
			response.put("code", "0");
		} catch (Exception e) {
			response.put("message", e.toString());
		}

		return response;
	}

	public synchronized void writeDataToDB(JSONObject json, int idform) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();

			String name = (String) json.get("Business Name");
			String address = (String) json.get("Full Address");
			String urlStore = (String) json.get("Source Link");
			String postCode = (String) json.get("PostCode");
			String phone = (String) json.get("Phone");
			String email = (String) json.get("Email");

			if (name != null) {
				List<ItemsEntity> itemCheck = itemsService.findNameItems(name);

				if (checkDuplicate(itemCheck, urlStore)) {
					ItemsEntity item = new ItemsEntity(name, idform, dtf.format(now), dtf.format(now),
							RandomKey.getSaltString());
					itemsService.saveItem(item);
					addToItemMeta(name, item, address, urlStore, phone, email, postCode);
				}
			}

//			ItemsEntity getItem = itemsService.findNameItems(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			return "failed!";
		}
	}

	public void addToItemMeta(String name, ItemsEntity item, String address, String urlStore, String phone,
			String email, String postCode) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
//
//		ItemsMetaEntity item_meta = new ItemsMetaEntity();

//		try {
//			itemsService.saveItem(item);
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}

		int idItem = item.getId();
		String scraper = "a:1:{i:0;s:9:\"Treatwell\";}";

		saveItemMeta("Business Name", name, itemsMetaService, idItem);
		saveItemMeta("Creation Date", dtf.format(now), itemsMetaService, idItem);
		saveItemMeta("Scraper Source", scraper, itemsMetaService, idItem);
		saveItemMeta("Full Address", address, itemsMetaService, idItem);
		saveItemMeta("Source Link", urlStore, itemsMetaService, idItem);
		saveItemMeta("Phone", phone, itemsMetaService, idItem);
		saveItemMeta("Email", email, itemsMetaService, idItem);
		saveItemMeta("Postcode", postCode, itemsMetaService, idItem);
	}

	public void saveItemMeta(String nameColumn, String value, ItemsMetaService itemsMetaService, int idItem) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		if (value != null) {
			ItemsMetaEntity item_entity = new ItemsMetaEntity();
			int idColumn = fieldsService.findIdField(nameColumn).getId();
			item_entity.setItem_id(idItem);
			item_entity.setField_id(idColumn);
			item_entity.setMeta_value(value);
			item_entity.setCreated_at(dtf.format(now));

			itemsMetaService.saveItemMeta(item_entity);
		}
	}

	public boolean checkDuplicate(List<ItemsEntity> itemCheck, String urlStore) {
//		ItemsEntity item = null;
		if (itemCheck.size() != 0) {
			for (int i = 0; i < itemCheck.size(); i++) {
				int idItem = itemCheck.get(i).getId();
				ItemsMetaEntity itemMeta = itemsMetaService.findNameItems(idItem);

				if (itemMeta != null) {
					if (urlStore.equals(itemMeta.getMeta_value())) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
}
