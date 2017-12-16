package cn.ishow.service.impl;

import cn.ishow.entity.Person;
import cn.ishow.mapper.PersonMapper;
import cn.ishow.service.IPersonService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 代艳格
 * @since 2017-12-11
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

}
