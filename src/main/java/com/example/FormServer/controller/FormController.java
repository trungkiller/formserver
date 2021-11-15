package com.example.FormServer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
				if (pageStart != null) {
					if (pageEnd != null) {
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
			String urlStore = (String) json.get("Website/URL");
			String phone = (String) json.get("Phone");
			String email = (String) json.get("Email");

			if (name != null) {
				ItemsEntity item = new ItemsEntity(name, idform, dtf.format(now), dtf.format(now),
						RandomKey.getSaltString());
//				item.setName(name);
//				item.setForm_id(idform);
//				item.setItem_key(RandomKey.getSaltString());
//				item.setCreated_at(dtf.format(now));
//				item.setUpdated_at(dtf.format(now));

				ItemsMetaEntity item_meta = new ItemsMetaEntity();

				try {
					itemsService.saveItem(item);
				} catch (Exception e) {
					System.out.println(e.toString());
				}

				int idItem = item.getId();

				int idName = fieldsService.findIdField("Business Name").getId();

				item_meta.setItem_id(idItem);
				item_meta.setField_id(idName);
				item_meta.setMeta_value(name);
				item_meta.setCreated_at(dtf.format(now));

				itemsMetaService.saveItemMeta(item_meta);

				if (address != null) {
					ItemsMetaEntity item_meta_add = new ItemsMetaEntity();
					int idAddress = fieldsService.findIdField("Full Address").getId();
					item_meta_add.setItem_id(idItem);
					item_meta_add.setField_id(idAddress);
					item_meta_add.setMeta_value(address);
					item_meta_add.setCreated_at(dtf.format(now));

					itemsMetaService.saveItemMeta(item_meta_add);
				}

				if (urlStore != null) {
					ItemsMetaEntity item_meta_url = new ItemsMetaEntity();
					int idUrlStore = fieldsService.findIdField("Website/URL").getId();
					item_meta_url.setItem_id(idItem);
					item_meta_url.setField_id(idUrlStore);
					item_meta_url.setMeta_value(urlStore);
					item_meta_url.setCreated_at(dtf.format(now));

					itemsMetaService.saveItemMeta(item_meta_url);
				}

				if (phone != null) {
					ItemsMetaEntity item_meta_phone = new ItemsMetaEntity();
					int idPhone = fieldsService.findIdField("Phone").getId();
					item_meta_phone.setItem_id(idItem);
					item_meta_phone.setField_id(idPhone);
					item_meta_phone.setMeta_value(phone);
					item_meta_phone.setCreated_at(dtf.format(now));

					itemsMetaService.saveItemMeta(item_meta_phone);
				}

				if (email != null) {
					ItemsMetaEntity item_meta_email = new ItemsMetaEntity();
					int idEmail = fieldsService.findIdField("Email").getId();
					item_meta_email.setItem_id(idItem);
					item_meta_email.setField_id(idEmail);
					item_meta_email.setMeta_value(email);
					item_meta_email.setCreated_at(dtf.format(now));

					itemsMetaService.saveItemMeta(item_meta_email);
				}
			}

//			ItemsEntity getItem = itemsService.findNameItems(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			return "failed!";
		}
	}
}
