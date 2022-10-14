package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.user.Homepage;
import com.styeeqan.community.common.annotation.group.user.Login;
import com.styeeqan.community.common.annotation.group.user.Register;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    @Pattern(regexp = "\\w{1,12}", message = "账号格式错误，必须由字母、数字、下划线组成，不能超过12位",
            groups = {Register.class, Login.class})
    private String account;

    @Pattern(regexp = "[\\u4E00-\\u9FA5A-Za-z\\d_]{1,12}", message = "昵称格式错误，必须由字母、数字、下划线组成，不能超过12位",
            groups = {Register.class})
    private String username;

    @Pattern(regexp = "[a-zA-Z\\d]{1,16}", message = "密码格式错误，必须由字母、数字、下划线组成，不能超过16位",
            groups = {Register.class, Login.class})
    private String password;

    @NotBlank(message = "参数不能为空", groups = {Homepage.class})
    private String homepageId;
}
