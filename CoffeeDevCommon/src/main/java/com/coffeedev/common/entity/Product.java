package com.coffeedev.common.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 128, nullable = false, unique = true)
	private String name;
	
	@Column(unique = true, length = 255, nullable = false)
	private String alias;

	@Column(length = 512, nullable = false)
	private String description;


	@Column(updatable = false)
	private Date createTime;

	@Column(nullable = false)
	private Double price;

	@Column(length = 64)
	public String image;

	public boolean enabled;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	public Product() {
		
	}
	
	public Product(Integer id) {
		this.id = id;
	}


	public Product(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	 @PrePersist
	    protected void onCreate() {
	        createTime = new Date();
	    }
	

	 @Transient
		public String getImagePath() {
			if (this.id==null) return "/images/product-images.png";
			return "/product-images/"+this.id+"/"+this.image;
		}

}
