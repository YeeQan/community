package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.user.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    @Pattern(regexp = "\\w{1,12}", message = "账号格式错误，必须由字母、数字、下划线组成，不能超过12位",
            groups = {UserRegister.class, UserLogin.class})
    private String account;

    @Pattern(regexp = "[a-zA-Z\\d]{1,16}", message = "密码格式错误，必须由字母、数字、下划线组成，不能超过16位",
            groups = {UserRegister.class, UserLogin.class})
    private String password;

    @Pattern(regexp = "[\\u4E00-\\u9FA5A-Za-z\\d_]{1,12}", message = "昵称格式错误，必须由字母、数字、下划线组成，不能超过12位",
            groups = {UserRegister.class, UserInfoSave.class})
    private String username;

    @Pattern(regexp = "((\\d{3}[1-9]|\\d{2}[1-9]\\d|\\d[1-9]\\d{2}|[1-9]\\d{3})-(((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|"+
            "((0[469]|11)-(0[1-9]|[12]\\d|30))|(02-(0[1-9]|1\\d|2[0-8]))))|(((\\d{2})(0[48]|[2468][048]|[13579][26])|"+
            "((0[48]|[2468][048]|[3579][26])00))-02-29)$", message = "日期格式错误，必须为：yyyy-MM-dd",
            groups = {UserInfoSave.class})
    private String birthday;

    @Pattern(regexp = "[0-2]$", message = "参数错误",
            groups = {UserInfoSave.class})
    private String sex;

    private String city;

    private String introduction;

    private String school;

    private String major;

    private String company;

    private String position;

    @NotBlank(message = "参数不能为空", groups = {UserHomepage.class, UserDynamicList.class})
    private String homepageId;


}
