package com.tenpo.mockserver.service;

import com.tenpo.mockserver.dto.PercentageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PercentageService {

    public Page<PercentageDTO> getPercentage(Pageable pageable);
}
