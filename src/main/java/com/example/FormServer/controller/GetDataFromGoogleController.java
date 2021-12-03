package com.example.FormServer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FormServer.entity.ItemsEntity;
import com.example.FormServer.entity.ItemsMetaEntity;
import com.example.FormServer.finalclass.RandomKey;
import com.example.FormServer.selenium.ConvertPostCode;
import com.example.FormServer.selenium.GetDataFromGoogle;
import com.example.FormServer.service.FieldsService;
import com.example.FormServer.service.FormService;
import com.example.FormServer.service.ItemsMetaService;
import com.example.FormServer.service.ItemsService;

@RestController
@RequestMapping("/data")
public class GetDataFromGoogleController {

	@Autowired
	public FormService formService;

	@Autowired
	public FieldsService fieldsService;

	@Autowired
	public ItemsMetaService itemsMetaService;

	@Autowired
	public ItemsService itemsService;

	@Autowired
	public GetDataFromGoogle getData2;

	@GetMapping("/google")
	public String getData(@RequestParam("name") String nameStore) {
		if (nameStore != "") {
			try {
				getData2.StartSpec(nameStore);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return "0";
			}
		}
		return "1";
	}

	@GetMapping("/stop")
	public String stopSelenium() {
		try {
//			getData.StopSelenium();
		} catch (Exception e) {
			return "0";
		}
		return "1";
	}

	public void addDataToDB(JSONObject json) {
		String phone = (String) json.get("phone");
		String name = (String) json.get("NameStore");
		String address = (String) json.get("Address");
		String source_link = (String) json.get("StoreLinkonMap");
		String web_url = (String) json.get("linkCopy");
		String postCode = ConvertPostCode.checking(address);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		if (name != null) {
			List<ItemsEntity> itemCheck = itemsService.findNameItems(name);
			if (checkDuplicate(itemCheck, address) == null) {
				ItemsEntity item = new ItemsEntity(name, 6, dtf.format(now), dtf.format(now),
						RandomKey.getSaltString());
				itemsService.saveItem(item);
				addToItemMeta(name, item, address, source_link, phone, postCode, web_url);
			} else {
				ItemsEntity itemDuplicate = checkDuplicate(itemCheck, address);

				int idItem = itemDuplicate.getId();
				ItemsMetaEntity scraperSourceMeta = itemsMetaService.findItemsMeta(70, idItem);

				try {
					ItemsMetaEntity addressMeta = itemsMetaService.findItemsMeta(64, idItem);
//					ItemsMetaEntity postCodeMeta = itemsMetaService.findItemsMeta(65, idItem);
					ItemsMetaEntity phoneMeta = itemsMetaService.findItemsMeta(66, idItem);
					ItemsMetaEntity sourceLinkMeta = itemsMetaService.findItemsMeta(69, idItem);
					ItemsMetaEntity webUrlMeta = itemsMetaService.findItemsMeta(68, idItem);

					bonusData(addressMeta, itemsMetaService, "Full Address", address, idItem);
//					bonusData(postCodeMeta, itemsMetaService, "Postcode", postCode, idItem);
					bonusData(phoneMeta, itemsMetaService, "Phone", phone, idItem);
					bonusData(sourceLinkMeta, itemsMetaService, "Source Link", source_link, idItem);
					bonusData(webUrlMeta, itemsMetaService, "Website/URL", web_url, idItem);
				} catch (Exception e) {
					System.out.println(e.toString());
				}

				ItemsMetaEntity item_entity = new ItemsMetaEntity();
				int idColumn = fieldsService.findIdField("Scraper Source").getId();
				item_entity.setId(scraperSourceMeta.getId());
				item_entity.setItem_id(idItem);
				item_entity.setField_id(idColumn);
				item_entity.setMeta_value("a:2:{i:0;s:9:\"Treatwell\";i:1;s:6:\"Google\";}");
				item_entity.setCreated_at(dtf.format(now));

				itemsMetaService.saveItemMeta(item_entity);
			}

		} else {
			ItemsEntity item = new ItemsEntity(name, 6, dtf.format(now), dtf.format(now), RandomKey.getSaltString());
			itemsService.saveItem(item);
			addToItemMeta(name, item, address, source_link, phone, postCode, web_url);
		}

	}

	public void addToItemMeta(String name, ItemsEntity item, String address, String urlStore, String phone,
			String postCode, String webUrl) {

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
		String scraper = "a:2:{i:0;s:9:\"Treatwell\";i:1;s:6:\"Google\";}";

		saveItemMeta("Business Name", name, itemsMetaService, idItem);
		saveItemMeta("Creation Date", dtf.format(now), itemsMetaService, idItem);
		saveItemMeta("Scraper Source", scraper, itemsMetaService, idItem);
		saveItemMeta("Full Address", address, itemsMetaService, idItem);
		saveItemMeta("Source Link", urlStore, itemsMetaService, idItem);
		saveItemMeta("Phone", phone, itemsMetaService, idItem);
//		saveItemMeta("Email", email, itemsMetaService, idItem);
		saveItemMeta("Postcode", postCode, itemsMetaService, idItem);
		saveItemMeta("Website/URL", webUrl, itemsMetaService, idItem);
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

	public void bonusData(ItemsMetaEntity item, ItemsMetaService itemsMetaService, String nameColumn, String value,
			int idItem) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		if (item == null) {
			saveItemMeta(nameColumn, value, itemsMetaService, idItem);
		} else {
			ItemsMetaEntity item_entity = new ItemsMetaEntity();
			int idColumn = fieldsService.findIdField(nameColumn).getId();
			item_entity.setId(item.getId());
			item_entity.setItem_id(idItem);
			item_entity.setField_id(idColumn);
			item_entity.setMeta_value(value);
			item_entity.setCreated_at(dtf.format(now));

			itemsMetaService.saveItemMeta(item_entity);
		}
	}

	public ItemsEntity checkDuplicate(List<ItemsEntity> itemCheck, String address) {
		ItemsEntity item = null;
		if (itemCheck.size() != 0) {
			for (int i = 0; i < itemCheck.size(); i++) {
				int idItem = itemCheck.get(i).getId();
				ItemsMetaEntity postCodeMeta = itemsMetaService.findItemsMeta(65, idItem);

				if (postCodeMeta != null) {
					if (address.contains(postCodeMeta.getMeta_value())) {
						item = itemCheck.get(i);
					}
				} else {
					item = itemCheck.get(i);
				}
			}
		}
		return item;
	}
}
