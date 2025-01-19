/*
 * Copyright (c) 2023. Mangatek Ltd. All right reserved.
 */

package rw.mangatek.ebm2.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Pierre Fourier Niyigena
 * @version 1.0.0
 * @since October 26, 2023
 */
@MappedSuperclass
@Data
@EqualsAndHashCode
@SuperBuilder
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private String id;
	@JsonIgnore
	@Column(name = "created_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	@CreatedDate
	protected LocalDateTime createdDate;
	@JsonIgnore
	@Column(name = "modified_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@LastModifiedDate
	protected LocalDateTime modifiedDate;
	@CreatedBy
	@Basic(optional = true)
	@Column(name = "created_by")
	private String createdBy = "";
	@LastModifiedBy
	@Basic(optional = true)
	@Column(name = "updated_by")
	private String updatedBy = "";
	@JsonIgnore
	@Version
	public int version;


	protected AbstractEntity() {
		createdDate = LocalDateTime.now();
		modifiedDate = LocalDateTime.now();
	}
	public AbstractEntity(String id, int version){
		this.id=id;
		this.version=version;
	}


}
