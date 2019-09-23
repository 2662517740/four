package com.h5.service.impl;

import com.h5.entity.Snake;
import com.h5.mapper.SnakeMapper;
import com.h5.service.ISnakeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-23
 */
@Service
public class SnakeServiceImpl extends ServiceImpl<SnakeMapper, Snake> implements ISnakeService {

}
