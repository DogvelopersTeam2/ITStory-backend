package com.blog.itstory.api.login.dto;

import com.blog.itstory.domain.member.constant.Role;
import com.blog.itstory.domain.member.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ApiModel(value = "로그인 정보 객체")
public class RegisterDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식을 지켜줘")
    @ApiModelProperty(value = "이메일", name = "이메일", required = true, example = "email@gmail.com")
    private String email;

    //  비밀번호
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 30, message = "비밀번호는 4~30자 사이로 해줘요")
    @ApiModelProperty(value = "비밀번호",  required = true, example = "password1234")
    private String password1;

    //  비밀번호 확인
    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    @Size(min = 4, max = 30, message = "비밀번호는 4~30자 사이로 해줘요")
    @ApiModelProperty(value = "비밀번호 확인",  required = true, example = "password1234")
    private String password2;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(password1))
                .build();
    }
}
