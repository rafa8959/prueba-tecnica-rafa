package com.inditex.price.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PriceId implements Serializable {

	private static final long serialVersionUID = -2001599426509823017L;
	
	private Long brandId;
    private Long productId;
    private Integer priceList;
    private LocalDateTime startDate;
}
