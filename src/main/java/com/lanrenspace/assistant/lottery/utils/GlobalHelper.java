package com.lanrenspace.assistant.lottery.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: GlobalHelper
 * @Author: helongjun
 * @Date: 2022/12/13 13:15
 * @Description: TODO
 **/
public class GlobalHelper {


    /**
     * 获取推送标题
     *
     * @return 推送主题
     */
    public static String getTitle() {
        return "AssistantLottery-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
