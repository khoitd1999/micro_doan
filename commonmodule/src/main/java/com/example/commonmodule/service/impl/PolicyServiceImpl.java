package com.example.commonmodule.service.impl;

import com.example.commonmodule.entity.Policy;
import com.example.commonmodule.repository.PolicyRepository;
import com.example.commonmodule.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public List<Policy> loadAllData() {
        return policyRepository.findAll();
    }
}
