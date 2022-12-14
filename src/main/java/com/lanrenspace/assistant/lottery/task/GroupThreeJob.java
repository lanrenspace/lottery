package com.lanrenspace.assistant.lottery.task;

import com.alibaba.fastjson2.JSONObject;
import com.lanrenspace.assistant.lottery.entity.GroupThreeModeOne;
import com.lanrenspace.assistant.lottery.model.GroupThreeModel;
import com.lanrenspace.assistant.lottery.model.MailModel;
import com.lanrenspace.assistant.lottery.service.GroupThreeModeOneService;
import com.lanrenspace.assistant.lottery.service.MailService;
import com.lanrenspace.assistant.lottery.utils.GlobalHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: GroupThreeJob
 * @Author: helongjun
 * @Date: 2022/12/13 14:02
 * @Description: TODO
 **/
@Slf4j
@Component
public class GroupThreeJob {

    private MailService mailService;

    private GroupThreeModeOneService groupThreeModeOneService;

    @Autowired
    public void setGroupThreeModeOneService(GroupThreeModeOneService groupThreeModeOneService) {
        this.groupThreeModeOneService = groupThreeModeOneService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    //    @Scheduled(cron = "0 28 13 * * ? ")
    @Scheduled(fixedDelay = 60_000)
    public void pushGroupThree() {
        MailModel mailVo = new MailModel();
        mailVo.setId("1001");
        mailVo.setFrom("AssistantLottery");
        mailVo.setTo("lanrenspace@163.com");
        mailVo.setSubject(GlobalHelper.getTitle());
        //设置模板参数
        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int a = (int) (Math.random() * 10);
            int b = (int) (Math.random() * 10);
            int c = (int) (Math.random() * 10);
            dataList.add(a);
            dataList.add(b);
            dataList.add(c);
        }
        List<GroupThreeModel> threeModels = new ArrayList<>();
        Random rand = new Random();
        for (int i = 1; i <= 3; i++) {
            GroupThreeModel threeModel = new GroupThreeModel();
            LinkedList<Integer> elements = new LinkedList<>();
            for (int j = 1; j <= 6; j++) {
                boolean hasNext = true;
                while (hasNext) {
                    int randomElement = dataList.get(rand.nextInt(dataList.size()));
                    if (!elements.contains(randomElement)) {
                        elements.add(randomElement);
                        hasNext = false;
                    }
                }
            }
            threeModel.setTitle("第 " + i + " 位");
            threeModel.setElements(elements);
            threeModels.add(threeModel);
        }
        GroupThreeModeOne threeModeOne = new GroupThreeModeOne();
        threeModeOne.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer finalStringBuffer1 = stringBuffer;
        threeModels.get(0).getElements().forEach(item -> finalStringBuffer1.append(item.toString()));
        threeModeOne.setOneData(finalStringBuffer1.toString());

        stringBuffer = new StringBuffer();
        StringBuffer finalStringBuffer = stringBuffer;
        threeModels.get(1).getElements().forEach(item -> finalStringBuffer.append(item.toString()));
        threeModeOne.setTwoData(finalStringBuffer.toString());

        stringBuffer = new StringBuffer();
        StringBuffer finalStringBuffer2 = stringBuffer;
        threeModels.get(1).getElements().forEach(item -> finalStringBuffer2.append(item.toString()));
        threeModeOne.setThreeData(finalStringBuffer2.toString());

        groupThreeModeOneService.save(threeModeOne);

        map.put("groupThreeList", threeModels);
        mailVo.setAttachment(map);
        MailModel mailModel = mailService.sendTemplateMail(mailVo);
        log.info("push group three result: {}", JSONObject.toJSONString(mailModel));
    }
}
