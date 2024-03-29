package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer>{
	public Department findByDepartmentCode(String departmentCode);
	public Department findByTitle(String title);
}
