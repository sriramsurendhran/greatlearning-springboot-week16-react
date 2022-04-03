package com.greatlearning.surabi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author sriram
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Entity
public class AuditLog {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "date default sysdate")
	private Date createDate;

	private String description;

	public AuditLog(Integer id, Date createDate, String description) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.description = description;
	}
}
