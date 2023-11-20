package com.tenpo.mockserver.service;

import com.tenpo.mockserver.dto.PercentageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
public class PercentageServiceImpl implements PercentageService {
    @Value("${mock_server.percentage}")
    private Double percentage;
    @Override
    public Page<PercentageDTO> getPercentage(Pageable pageable) {
        PercentageDTO percentageDTO = new PercentageDTO(percentage);
        ArrayList<PercentageDTO> percentageDTOList = new ArrayList<PercentageDTO>();
        percentageDTOList.add(percentageDTO);
        return new PageImpl<PercentageDTO>(percentageDTOList, pageable,percentageDTOList.size());
    }
}
