package apps.searchme.sm.Recyclerview;

public class ChatClass {
    String room_name;
    String user_one;
    String user_two;
    String name_one;
    String name_two;

    public ChatClass() {
    }

    public ChatClass(String room_name, String user_one, String user_two, String name_one, String name_two) {
        this.room_name = room_name;
        this.user_one = user_one;
        this.user_two = user_two;
        this.name_one = name_one;
        this.name_two = name_two;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getUser_one() {
        return user_one;
    }

    public void setUser_one(String user_one) {
        this.user_one = user_one;
    }

    public String getUser_two() {
        return user_two;
    }

    public void setUser_two(String user_two) {
        this.user_two = user_two;
    }

    public String getName_one() {
        return name_one;
    }

    public void setName_one(String name_one) {
        this.name_one = name_one;
    }

    public String getName_two() {
        return name_two;
    }

    public void setName_two(String name_two) {
        this.name_two = name_two;
    }
}
