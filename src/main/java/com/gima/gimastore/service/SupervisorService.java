package com.gima.gimastore.service;

import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.SupervisorDTO;
import com.gima.gimastore.repository.SupervisorRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.NO_SUPERVISOR_ID;
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_SUPERVISOR_NAME;

@Service
@RequiredArgsConstructor
public class SupervisorService {
    private final SupervisorRepository supervisorRepo;

    public void add(SupervisorDTO dto) {
        validateSupervisorName(dto.getSupervisorName());
        supervisorRepo.save(ObjectMapperUtils.map(dto, Supervisor.class));
    }

    public void update(SupervisorDTO dto) {

        validateExistSupervisor(dto.getId());
        validateSupervisorAndID(dto.getSupervisorName(), dto.getId());

        supervisorRepo.save(ObjectMapperUtils.map(dto, Supervisor.class));

    }

    public void delete(Long id) {
        Optional<Supervisor> supervisorById = validateExistSupervisor(id);
        supervisorById.get().setIsLocked(true);
        supervisorRepo.save(supervisorById.get());
    }

    public SupervisorDTO findById(Long id) {
        Optional<Supervisor> supervisorById = supervisorRepo.findById(id);
        if (supervisorById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPERVISOR_ID.getCode(), NO_SUPERVISOR_ID.getKey(), NO_SUPERVISOR_ID.getMessage()));

        return ObjectMapperUtils.map(supervisorById.get(), SupervisorDTO.class);
    }


    public Page<Supervisor> findAll(Pageable pageable) {
        return supervisorRepo.findAll(pageable);
    }

    private void validateSupervisorName(String supervisorName) {
        if (supervisorRepo.existsBySupervisorName(supervisorName))
            throw new ApplicationException(new StatusResponse(REPEATED_SUPERVISOR_NAME.getCode(),
                    REPEATED_SUPERVISOR_NAME.getKey(), REPEATED_SUPERVISOR_NAME.getMessage()));

    }

    private void validateSupervisorAndID(String supervisorName, Long partId) {
        if (!supervisorRepo.findById(partId).get().getSupervisorName().equals(supervisorName))
            validateSupervisorName(supervisorName);
    }

    private Optional<Supervisor> validateExistSupervisor(Long id) {
        Optional<Supervisor> supervisorById = supervisorRepo.findById(id);
        if (supervisorById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPERVISOR_ID.getCode(), NO_SUPERVISOR_ID.getKey(), NO_SUPERVISOR_ID.getMessage()));
        return supervisorById;
    }
}
