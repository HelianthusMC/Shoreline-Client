package com.caspian.client.impl.event.chat;

import com.caspian.client.api.event.Cancelable;
import com.caspian.client.api.event.Event;

/**
 *
 * @author linus
 * @since 1.0
 */
@Cancelable
public class ChatKeyInputEvent extends Event
{
    //
    private final int keycode;
    private String chatText;

    /**
     *
     * @param keycode
     * @param chatText
     */
    public ChatKeyInputEvent(int keycode, String chatText)
    {
        this.keycode = keycode;
        this.chatText = chatText;
    }

    public int getKeycode()
    {
        return keycode;
    }

    public void setChatText(String chatText)
    {
        this.chatText = chatText;
    }

    public String getChatText()
    {
        return chatText;
    }
}
