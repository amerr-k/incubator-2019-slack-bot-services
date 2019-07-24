package com.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.Message;
import com.domain.Trigger;

public interface TriggerRepository extends PagingAndSortingRepository<Trigger, Integer>{
	
	public List<Trigger> findAllByMessage(Message message);
}