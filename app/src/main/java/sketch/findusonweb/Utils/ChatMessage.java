package sketch.findusonweb.Utils;

/**
 * Created by Developer on 10/26/16.
 */

public class ChatMessage {

    public String body, sender, receiver, senderName;
    public String Date, img;
    public String msgid;
    public String seenstatus;
    public boolean isMine;// Did I send the message.

    public ChatMessage(String Sender, String name, String messageString, boolean isMINE, String seen) {
        body = messageString;
        isMine = isMINE;
       // sender = Sender;
        //msgid = ID;
        receiver = name;
        senderName = sender;
        seenstatus = seen;

    }



//    public void setMsgID() {
//
//        msgid += "-" + String.format("%02d", new Random().nextInt(100));
//    }
}

