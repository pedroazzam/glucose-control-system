package com.managedata.glucontrolapi.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_EVENT")
public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="id")
	long id;
	
	@Column(name="date")
	LocalDateTime date;
	//Date date;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	User user;
	
	@Column(name="type")
	String type;
	
	@Column(name="value")
	BigDecimal value;
	
	@Column(name="varPerMin")
	BigDecimal varPerMin;
	
	@Column(name="description")
	String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		//System.out.println("TIMEZONE!!!");
		//System.out.println(date.getTimezoneOffset());
		//System.out.println(new Date().getTimezoneOffset());
		//System.out.println(new Date());
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getVarPerMin() {
		return varPerMin;
	}

	public void setVarPerMin(BigDecimal varPerMin) {
		this.varPerMin = varPerMin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
