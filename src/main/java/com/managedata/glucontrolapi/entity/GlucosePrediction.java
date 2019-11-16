package com.managedata.glucontrolapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GlucosePrediction {

	private LocalDateTime sourceDateFrom;
	private LocalDateTime sourceDateTo;
	private LocalDateTime date;
	private BigDecimal value;
	private BigDecimal varPerMin;
	public LocalDateTime getSourceDateFrom() {
		return sourceDateFrom;
	}
	public void setSourceDateFrom(LocalDateTime sourceDateFrom) {
		this.sourceDateFrom = sourceDateFrom;
	}
	public LocalDateTime getSourceDateTo() {
		return sourceDateTo;
	}
	public void setSourceDateTo(LocalDateTime sourceDateTo) {
		this.sourceDateTo = sourceDateTo;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
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
	
	
}
