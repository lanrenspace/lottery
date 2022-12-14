package com.lanrenspace.assistant.lottery.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @ClassName: GroupTreeModeOne
 * @Author: helongjun
 * @Date: 2022/12/14 18:10
 * @Description: TODO
 **/
@Data
@Entity(name = "group_three_mode_one")
public class GroupThreeModeOne implements Serializable {

    @Id
    private String date;

    private String oneData;

    private String twoData;

    private String threeData;
}
