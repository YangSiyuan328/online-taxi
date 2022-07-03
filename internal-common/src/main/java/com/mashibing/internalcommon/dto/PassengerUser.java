package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/29 0:28
 * @description
 */
@Data
public class PassengerUser {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte state;

    private String profilePhoto;

}
