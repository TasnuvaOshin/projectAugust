package apps.searchme.sm.Recyclerview;

public class viewCus {

    String mac_address;
    String title;

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public viewCus() {
    }

    public viewCus(String mac_address, String title) {
        this.mac_address = mac_address;
        this.title = title;
    }
}
