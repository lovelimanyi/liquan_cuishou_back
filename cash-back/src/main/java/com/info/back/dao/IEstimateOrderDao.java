package com.info.back.dao;

import com.info.web.pojo.EstimateOrder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public interface IEstimateOrderDao {
    List<EstimateOrder> findAll(HashMap<String,Object> pram);

    EstimateOrder findByDate(HashMap<String,Object> pram);

    int insert(EstimateOrder estimateOrder);
    int updateById(EstimateOrder estimateOrder);
}
