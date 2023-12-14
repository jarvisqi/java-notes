package com.softmax.design.intermediary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jarvis
 */
public class ChatRoom {
    /**
     * 聊天室命名
     */
    private String name;

    /**
     * 初始化必须命名聊天室
     *
     * @param name
     */
    public ChatRoom(String name) {
        this.name = name;
    }

    /**
     * 聊天室里的用户们
     */
    List<User> users = new ArrayList<>();

    public void connect(User user) {
        //用户进入聊天室加入列表。
        this.users.add(user);
        System.out.print("欢迎【");
        System.out.print(user.getName());
        System.out.println("】加入聊天室【" + this.name + "】");
    }

    public void sendMsg(User fromWhom, String msg) {
        // 循环所有用户，只发消息给非发送方fromWhom。
        users.stream()
                //过滤掉发送方fromWhom
                .filter(user -> !user.equals(fromWhom))
                //发送消息给剩下的所有人
                .forEach(toWhom -> toWhom.listen(fromWhom, msg));
    }
}