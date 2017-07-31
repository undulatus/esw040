package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.Group;

public interface GroupRepository extends CrudRepository<Group, Integer> {
	
	public List<Group> findAllByOrderByGroupNameDesc();

}
