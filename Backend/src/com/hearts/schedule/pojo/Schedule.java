package com.hearts.schedule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule implements Serializable
{
    private Integer sid;
    private Integer uid;
    private String title;
    private Integer completed;
}
