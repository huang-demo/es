package com.dem.es.service;

import com.dem.es.domain.ProjectInfo;

import java.util.List;

public interface ProjectInfoService {

    void inseart2Elastic();

    Object query(String kw, int page, int pageSize);

    List<ProjectInfo> getAll();

    int deleteByProjectName(String name);
}
