package com.lanrenspace.assistant.lottery.model;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @ClassName: GroupThreeModel
 * @Author: helongjun
 * @Date: 2022/12/13 13:29
 * @Description: TODO
 **/
@Data
public class GroupThreeModel implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 元素
     */
    private LinkedList<Integer> elements;
}
