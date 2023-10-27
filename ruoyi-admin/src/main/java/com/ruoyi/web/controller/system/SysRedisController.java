package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/redis")
public class SysRedisController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/getTypeList")
    public TableDataInfo getAllTypeInfo(){
        return getDataTable(redisCache.getCacheObject("type_info"));
    }

}