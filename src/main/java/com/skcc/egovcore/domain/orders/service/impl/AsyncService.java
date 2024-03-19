package com.skcc.egovcore.domain.orders.service.impl;

import com.skcc.egovcore.core.mvc.service.SkAbstractService;
import com.skcc.egovcore.core.paging.mapper.PagingMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AsyncService extends SkAbstractService  {


    private final PagingMapper pagingMapper;

    private  ExecutorService executorService;

    @PostConstruct
    void init() {
        executorService = new ThreadPoolExecutor(
                3, // 코어 쓰레드 수
                200, // 최대 쓰레드 수
                120, // 최대 유휴 시간
                TimeUnit.SECONDS, // 최대 유휴 시간 단위
                new SynchronousQueue<>() // 작업 큐
        );
    }
    public void findAll() {
        CompletableFuture.runAsync(()->{
            int ret = pagingMapper.pagingCall("OrdersMapperDao.selectOrdersCount", new HashMap());
            log.info("{} -> Paging ret = {}", Thread.currentThread().getName(), ret);
        },executorService);
    }
}
