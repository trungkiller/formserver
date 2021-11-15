package com.example.FormServer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_frm_fields")

public class FieldsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "field_key")
	private String field_key;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "default_value")
	private String default_value;
	
	@Column(name = "options")
	private String options;
	
	@Column(name = "field_order")
	private int field_order;
	
	@Column(name = "required")
	private int required;
	
	@Column(name = "field_options")
	private String field_options;
	
	@Column(name = "form_id")
	private int form_id;
	
	@Column(name = "created_at")
	private String created_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getField_key() {
		return field_key;
	}

	public void setField_key(String field_key) {
		this.field_key = field_key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public int getField_order() {
		return field_order;
	}

	public void setField_order(int field_order) {
		this.field_order = field_order;
	}

	public int getRequired() {
		return required;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public String getField_options() {
		return field_options;
	}

	public void setField_options(String field_options) {
		this.field_options = field_options;
	}

	public int getForm_id() {
		return form_id;
	}

	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
