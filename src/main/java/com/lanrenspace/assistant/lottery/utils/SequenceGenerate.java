package com.lanrenspace.assistant.lottery.utils;

import com.alibaba.fastjson2.JSONObject;
import com.lanrenspace.assistant.lottery.entity.GroupThreeModeOne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: SequenceGenerate
 * @Author: helongjun
 * @Date: 2022/12/19 10:23
 * @Description: 数列生成
 **/
public class SequenceGenerate {

    /**
     * 数列模式一
     *
     * @return 数列结果
     */
    private static GroupThreeModeOne generateGroupThreeModeOne(int previous) {
        int current = (int) (Math.random() * 10);
        boolean hasNext = true;
        List<Integer> oneDataList = new ArrayList<>(Collections.singletonList(previous));
        while (hasNext) {
            if ((current < previous && previous >= 6) || (current > previous && previous < 6)) {
                oneDataList.add(current);
                hasNext = false;
            } else {
                current = (int) (Math.random() * 10);
            }
        }
        StringBuilder rowFiveOne = new StringBuilder();
        rowFiveOne.append(oneDataList.get(0));
        StringBuilder rowFiveTwo = new StringBuilder();
        rowFiveTwo.append(oneDataList.get(1));
        GroupThreeModeOne threeModeOne = new GroupThreeModeOne();
        for (int i = 1; i <= 2; i++) {
            List<Integer> twoDataList = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
                hasNext = true;
                while (hasNext) {
                    current = (int) (Math.random() * 10);
                    if (!twoDataList.contains(current)) {
                        twoDataList.add(current);
                        hasNext = false;
                    }
                }
            }
            StringBuilder sBuffer = new StringBuilder();
            for (Integer integer : twoDataList) {
                sBuffer.append(integer);
            }
            int rowFive = twoDataList.get(new Random().nextInt(twoDataList.size()));
            rowFiveOne.append(rowFive);
            rowFiveTwo.append(rowFive);
            if (i == 1) {
                threeModeOne.setTwoData(sBuffer.toString());
            } else {
                threeModeOne.setThreeData(sBuffer.toString());
            }
        }
        StringBuilder sBuffer = new StringBuilder();
        for (Integer integer : oneDataList) {
            sBuffer.append(integer);
        }
        threeModeOne.setOneData(sBuffer.toString());

        rowFiveOne.append((int) (Math.random() * 10));
        rowFiveTwo.append((int) (Math.random() * 10));
        rowFiveOne.append((int) (Math.random() * 10));
        rowFiveTwo.append((int) (Math.random() * 10));

        threeModeOne.setRowFiveOne(rowFiveOne.toString());
        threeModeOne.setRowFiveTwo(rowFiveTwo.toString());
        return threeModeOne;
    }

    public static void main(String[] args) {
        GroupThreeModeOne threeModeOne = SequenceGenerate.generateGroupThreeModeOne(6);
        System.out.println(JSONObject.toJSONString(threeModeOne));
    }
}
