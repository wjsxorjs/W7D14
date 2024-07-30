package com.sist.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.app.vo.CommVO;

@Mapper
public interface CommMapper {
    
    List<CommVO> commList(String b_idx);

}
