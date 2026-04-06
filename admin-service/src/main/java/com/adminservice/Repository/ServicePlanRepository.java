package com.adminservice.Repository;

import com.adminservice.Entity.ServicePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePlanRepository extends JpaRepository<ServicePlan, Long> {
}