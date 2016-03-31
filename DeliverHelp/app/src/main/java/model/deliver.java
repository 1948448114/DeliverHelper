package model;

/**
 * Created by LJ on 2015/9/28.
 */
public class deliver {
    private String name;
    private String phone;
    private String content;
    private  String id;
    private Integer state;



    public deliver(String id,String name,String content,String phone,int state){
        this.id = id;
        this.name=name;
        this.phone=phone;
        this.content =content;
        this.state=state;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
