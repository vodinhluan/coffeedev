package com.coffeedev.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_rates")
public class ShippingRate extends IdBasedEntity {

	private float rate;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;
	
	@Column(nullable = false, length = 45)
	private String ward;

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public District getdistrict() {
		return district;
	}

	public void setdistrict(District district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	@Override
	public String toString() {
		return "ShippingRate [id=" + id + ", rate=" + rate + ", district=" + district.getName() + ", ward=" + ward + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippingRate other = (ShippingRate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

	
}
