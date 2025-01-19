package rw.mangatek.ebm2.core.domain;

import java.math.BigDecimal;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "company_monitor")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyMonitor extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055611100688692199L;
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "company_id", unique = false, nullable = true, insertable = true, updatable = true)
	private Company company;
	@Column(name = "total_tax")
	private BigDecimal totalTax;
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	@Column(name = "source")
	private String source;
}
