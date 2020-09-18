package com.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.demo.utils.DateUtils;
import com.demo.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override    //在执行mybatisPlus的insert()时，为我们自动给某些字段填充值，这样的话，我们就不需要手动给insert()里的实体类赋值了
    public void insertFill(MetaObject metaObject) {
        //其中方法参数中第一个是前面自动填充所对应的字段，第二个是要自动填充的值。第三个是指定实体类的对象
        //this.setFieldValByName("id", UUID.randomUUID().toString(), metaObject);
        this.setFieldValByName("createId", UserContext.getCurrentUser().getId(), metaObject);
        this.setFieldValByName("createName", UserContext.getCurrentUser().getUserName(), metaObject);
        this.setFieldValByName("createTime", DateUtils.getCurrentDatetime(), metaObject);
        this.setFieldValByName("isDelete", false, metaObject);
        this.setFieldValByName("isEnabled", "1", metaObject);
    }

    @Override//在执行mybatisPlus的update()时，为我们自动给某些字段填充值，这样的话，我们就不需要手动给update()里的实体类赋值了
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateId", UserContext.getCurrentUser().getId(), metaObject);
        this.setFieldValByName("updateName", UserContext.getCurrentUser().getUserName(), metaObject);
        this.setFieldValByName("updateTime", DateUtils.getCurrentDatetime(), metaObject);
    }
}
