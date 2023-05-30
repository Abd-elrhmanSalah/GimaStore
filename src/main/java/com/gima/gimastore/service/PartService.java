package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartDTO;
import com.gima.gimastore.repository.PartRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static com.gima.gimastore.constant.ResponseCodes.NO_PART_ID;
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_PARTNAME;

@Service
public class PartService {

    private PartRepository partRepo;

    public PartService(PartRepository partRepo) {
        this.partRepo = partRepo;
    }


    public void add(PartDTO dto, MultipartFile file) throws IOException {

        validatePartName(dto.getPartName());

        Part savedPart = partRepo.save(ObjectMapperUtils.map(dto, Part.class));
        if (!file.isEmpty())
            savedPart.setPicture(ImageUtil.compressImage(file.getBytes()));

        partRepo.save(savedPart);

    }


    public void update(PartDTO dto, MultipartFile file) throws IOException {
        Optional<Part> part = partRepo.findById(dto.getId());
        if (Objects.isNull(part) || part.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PART_ID.getCode(), NO_PART_ID.getKey(), NO_PART_ID.getMessage()));

        validatePartAndID(dto.getPartName(), dto.getId());
        if (part.get().getPicture() != null)
            dto.setPicture(part.get().getPicture());

        Part savedPart = partRepo.save(ObjectMapperUtils.map(dto, Part.class));
        if (!file.isEmpty())
            savedPart.setPicture(ImageUtil.compressImage(file.getBytes()));

        partRepo.save(savedPart);
    }


    public void delete(Long id) {
        Optional<Part> part = partRepo.findById(id);
        if (Objects.isNull(part) || part.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PART_ID.getCode(), NO_PART_ID.getKey(), NO_PART_ID.getMessage()));

        partRepo.deleteById(id);
    }


    public PartDTO findById(Long id) throws DataFormatException, IOException {
        Optional<Part> part = partRepo.findById(id);
        if (Objects.isNull(part) || part.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PART_ID.getCode(), NO_PART_ID.getKey(), NO_PART_ID.getMessage()));

        PartDTO partDto = ObjectMapperUtils.map(part.get(), PartDTO.class);
        if (!Objects.isNull(part.get().getPicture()))
            partDto.setPicture(ImageUtil.decompressImage(part.get().getPicture()));

        return partDto;
    }


    public List<PartDTO> findAll() {

        return partRepo.findAll().stream().map(part -> {
            PartDTO partDto = ObjectMapperUtils.map(part, PartDTO.class);
            if (!Objects.isNull(part.getPicture())) {
                try {
                    partDto.setPicture(ImageUtil.decompressImage(part.getPicture()));
                } catch (DataFormatException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return partDto;
        }).collect(Collectors.toList());

    }

    private void validatePartName(String partName) {
        if (partRepo.existsByPartName(partName))
            throw new ApplicationException(new StatusResponse(REPEATED_PARTNAME.getCode(), REPEATED_PARTNAME.getKey(), REPEATED_PARTNAME.getMessage()));

    }

    private void validatePartAndID(String partName, Long partId) {
        if (!partRepo.findById(partId).get().getPartName().equals(partName))
            if (partRepo.existsByPartName(partName))
                throw new ApplicationException(new StatusResponse(REPEATED_PARTNAME.getCode(), REPEATED_PARTNAME.getKey(), REPEATED_PARTNAME.getMessage()));

    }
}
