package com.softmax.design.intermediary;

/**
 * @author Jarvis
 */
public class User {
    /**
     * 名字
     */
    private String name;

    /**
     * //聊天室引用
     */
    private ChatRoom chatRoom;

    public User(String name) {
        //初始化必须起名字
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * //用户登陆
     *
     * @param chatRoom
     */
    public void login(ChatRoom chatRoom) {
        //调用聊天室连接方法
        chatRoom.connect(this);
        //注入聊天室引用
        this.chatRoom = chatRoom;
    }

    //用户发言
    public void talk(String msg) {
        //给聊天室发消息
        chatRoom.sendMsg(this, msg);
    }

    /**
     * //且听风吟
     *
     * @param fromWhom
     * @param msg
     */
    public void listen(User fromWhom, String msg) {
        System.out.print("【" + this.name + "的对话框】");
        System.out.println(fromWhom.getName() + " 说： " + msg);
    }
}