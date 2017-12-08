package com.dem.es.service;

import com.dem.es.domain.ProjectInfo;

import java.io.IOException;
import java.util.List;

public interface ProjectInfoService {

    void batchAdd();

    String  addOne(Long id) throws IOException;
    Object query(String kw, int page, int pageSize);

    List<ProjectInfo> getAll();

    int deleteByProjectName(String name);

    Object queryMutiType(String kw,int page,int pageSize);
}
