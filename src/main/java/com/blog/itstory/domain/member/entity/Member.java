package com.blog.itstory.domain.member.entity;

import com.blog.itstory.api.login.dto.TokenDto;
import com.blog.itstory.domain.common.BaseEntity;
import com.blog.itstory.domain.member.constant.Role;
import com.blog.itstory.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime; // refreshToken 만료 시간.

    @Builder
    public Member(Long memberId, String email, String password, Role role, String refreshToken,
                  LocalDateTime tokenExpirationTime) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    public void updateRefreshTokenAndExpirationTime(TokenDto.Response tokenDto) {
        this.refreshToken = tokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(tokenDto.getRefreshTokenExpirationTime());
    }
}
