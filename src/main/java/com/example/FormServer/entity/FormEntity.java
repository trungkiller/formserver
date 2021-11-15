package com.example.FormServer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_frm_forms")

public class FormEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "form_key")
	private String form_key;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "parent_form_id")
	private int parent_form_id;

	@Column(name = "logged_in")
	private int logged_in;

	@Column(name = "editable")
	private int editable;

	@Column(name = "is_template")
	private int is_template;

	@Column(name = "default_template")
	private int default_template;

	@Column(name = "status")
	private String status;

	@Column(name = "options")
	private String options;

	@Column(name = "created_at")
	private String created_at;

	public FormEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getForm_key() {
		return form_key;
	}

	public void setForm_key(String form_key) {
		this.form_key = form_key;
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

	public int getParent_form_id() {
		return parent_form_id;
	}

	public void setParent_form_id(int parent_form_id) {
		this.parent_form_id = parent_form_id;
	}

	public int getLogged_in() {
		return logged_in;
	}

	public void setLogged_in(int logged_in) {
		this.logged_in = logged_in;
	}

	public int getEditable() {
		return editable;
	}

	public void setEditable(int editable) {
		this.editable = editable;
	}

	public int getIs_template() {
		return is_template;
	}

	public void setIs_template(int is_template) {
		this.is_template = is_template;
	}

	public int getDefault_template() {
		return default_template;
	}

	public void setDefault_template(int default_template) {
		this.default_template = default_template;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
