package com.example.pocketgpt.telegram.model;

public class TgResponse {

    private final String reply;

    private TgResponse(String reply) {
        this.reply = reply;
    }

    public static TgResponse newChatCreated() {
        return new TgResponse("Новый чат создан! Что хочешь обсудить?");
    }

    public static TgResponse messageWriteNameOfNewChat() {
        return new TgResponse("Придумай название диалога");
    }

    public static TgResponse messageMaxAmountOfChats() {
        return new TgResponse("У тебя уже максимальное количество чатов. Если хочешь создать новый - удали один из существующих");
    }

    public String getReply() {
        return reply;
    }

}
