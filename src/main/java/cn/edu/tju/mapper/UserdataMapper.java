package cn.edu.tju.mapper;

import cn.edu.tju.entity.Userdata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-16
 */
public interface UserdataMapper extends BaseMapper<Userdata> {

    public void updateSelective(Userdata userdata) ;
}
