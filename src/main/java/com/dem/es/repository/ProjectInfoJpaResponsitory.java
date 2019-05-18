package com.dem.es.repository;

import com.dem.es.entity.po.ProjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectInfoJpaResponsitory extends JpaRepository<ProjectInfo,Long> {
}
