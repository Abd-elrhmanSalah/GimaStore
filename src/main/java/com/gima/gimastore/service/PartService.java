package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartDTO;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.PartRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.NO_PART_ID;

@Service
public class PartService implements CommonRepo<PartDTO> {

    private PartRepository partRepo;

    public PartService(PartRepository partRepo) {
        this.partRepo = partRepo;
    }

    @Override
    public void add(PartDTO dto) {
        partRepo.save(ObjectMapperUtils.map(dto, Part.class));
    }

    @Override
    public void update(PartDTO dto) {
        partRepo.save(ObjectMapperUtils.map(dto, Part.class));

    }

    @Override
    public void delete(Long id) {
        Optional<Part> part = partRepo.findById(id);
        if (Objects.isNull(part) || part.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PART_ID.getCode(), NO_PART_ID.getKey(), NO_PART_ID.getMessage()));

        partRepo.deleteById(id);
    }

    @Override
    public PartDTO findById(Long id) {
        Optional<Part> part = partRepo.findById(id);
        if (Objects.isNull(part) || part.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PART_ID.getCode(), NO_PART_ID.getKey(), NO_PART_ID.getMessage()));

        return ObjectMapperUtils.map(part.get(), PartDTO.class);
    }

    @Override
    public List<PartDTO> findAll() {
        return ObjectMapperUtils.mapAll(partRepo.findAll(), PartDTO.class);
    }
}
