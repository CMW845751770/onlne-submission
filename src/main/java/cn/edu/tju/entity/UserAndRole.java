package cn.edu.tju.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAndRole extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    //配置一对多关系
    private List<Role> userRoles;
}
