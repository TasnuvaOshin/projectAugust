package apps.searchme.sm.Recyclerview;

public class ChatViewClass {

   public String name;
   public String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ChatViewClass() {
    }

    public ChatViewClass(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }
}
