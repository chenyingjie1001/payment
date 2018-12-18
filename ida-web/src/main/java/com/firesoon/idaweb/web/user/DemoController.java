package com.firesoon.idaweb.web.user;

import com.firesoon.idaweb.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author create by yingjie.chen on 2018/11/19.
 * @version 2018/11/19 12:02
 */
@Api
@Slf4j
@RestController
@RequestMapping("/api/demo/")
public class DemoController extends BaseController {

    @ApiOperation(value = "这是方法解释", notes = "这是描述")
    @GetMapping(value = "a")
    public Object a(){
        return succ();
    }
}
