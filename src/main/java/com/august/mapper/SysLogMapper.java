package com.august.mapper;

import com.august.entity.SysLog;

public interface SysLogMapper {
    int insert(SysLog record);

    int insertSelective(SysLog record);
}