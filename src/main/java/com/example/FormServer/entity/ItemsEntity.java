package com.example.FormServer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_frm_items")

public class ItemsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "item_key")
	private String item_key ;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "form_id")
	private int form_id ;
	
	@Column(name = "post_id")
	private int post_id ;
	
	@Column(name = "user_id")
	private int user_id ;
	
	@Column(name = "parent_item_id")
	private int parent_item_id ;
	
	@Column(name = "is_draft")
	private int is_draft;
	
	@Column(name = "updated_by")
	private int updated_by;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	public ItemsEntity(String name, int form_id, String created_at, String updated_at, String key) {
		this.name = name;
		this.form_id = form_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.item_key = key;
	}
	
	public ItemsEntity() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem_key() {
		return item_key;
	}

	public void setItem_key(String item_key) {
		this.item_key = item_key;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getForm_id() {
		return form_id;
	}

	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getParent_item_id() {
		return parent_item_id;
	}

	public void setParent_item_id(int parent_item_id) {
		this.parent_item_id = parent_item_id;
	}

	public int getIs_draft() {
		return is_draft;
	}

	public void setIs_draft(int is_draft) {
		this.is_draft = is_draft;
	}

	public int getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
}
