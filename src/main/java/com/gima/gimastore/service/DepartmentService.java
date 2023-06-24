package com.gima.gimastore.service;

import com.gima.gimastore.entity.Department;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.DepartmentDTO;
import com.gima.gimastore.repository.DepartmentRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.NO_DEPARTMENT_ID;
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_DEPARTMENT_NAME;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepo;

    public DepartmentService(DepartmentRepository departmentRepo) {
        this.departmentRepo = departmentRepo;
    }


    public void add(DepartmentDTO dto) {
        validateDepartmentName(dto.getDeptName());
        departmentRepo.save(ObjectMapperUtils.map(dto, Department.class));
    }

    public void update(DepartmentDTO dto) {

        validateExistDept(dto.getId());
        validateDeptAndID(dto.getDeptName(), dto.getId());

        departmentRepo.save(ObjectMapperUtils.map(dto, Department.class));

    }

    public void delete(Long id) {
        Optional<Department> deptById = validateExistDept(id);
        deptById.get().setLocked(true);
        departmentRepo.save(deptById.get());
    }

    public DepartmentDTO findById(Long id) {
        Optional<Department> deptById = departmentRepo.findById(id);
        if (deptById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_DEPARTMENT_ID.getCode(), NO_DEPARTMENT_ID.getKey(), NO_DEPARTMENT_ID.getMessage()));

        return ObjectMapperUtils.map(deptById.get(), DepartmentDTO.class);
    }


    public Page<Department> findAll(Pageable pageable) {
        return departmentRepo.findAll(pageable);
    }

    private void validateDepartmentName(String departName) {
        if (departmentRepo.existsByDeptName(departName))
            throw new ApplicationException(new StatusResponse(REPEATED_DEPARTMENT_NAME.getCode(),
                    REPEATED_DEPARTMENT_NAME.getKey(), REPEATED_DEPARTMENT_NAME.getMessage()));

    }

    private void validateDeptAndID(String deptName, Long partId) {
        if (!departmentRepo.findById(partId).get().getDeptName().equals(deptName))
            validateDepartmentName(deptName);
    }

    private Optional<Department> validateExistDept(Long id) {
        Optional<Department> deptById = departmentRepo.findById(id);
        if (deptById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_DEPARTMENT_ID.getCode(), NO_DEPARTMENT_ID.getKey(), NO_DEPARTMENT_ID.getMessage()));
        return deptById;
    }
}
