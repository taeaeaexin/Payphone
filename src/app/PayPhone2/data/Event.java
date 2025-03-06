package app.PayPhone2.data;

public class Event {
    private int eventId;
    private String title;
    private String text;
    private String option1;
    private String option2;
    private int result1_h;
    private int result1_m;
    private int result1_s;
    private int result2_h;
    private int result2_m;
    private int result2_s;
    private String result1_text;
    private String result2_text;

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }

    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }

    public int getResult1_h() { return result1_h; }
    public void setResult1_h(int result1_h) { this.result1_h = result1_h; }

    public int getResult1_m() { return result1_m; }
    public void setResult1_m(int result1_m) { this.result1_m = result1_m; }

    public int getResult1_s() { return result1_s; }
    public void setResult1_s(int result1_s) { this.result1_s = result1_s; }

    public int getResult2_h() { return result2_h; }
    public void setResult2_h(int result2_h) { this.result2_h = result2_h; }

    public int getResult2_m() { return result2_m; }
    public void setResult2_m(int result2_m) { this.result2_m = result2_m; }

    public int getResult2_s() { return result2_s; }
    public void setResult2_s(int result2_s) { this.result2_s = result2_s; }

    public String getResult1_text() { return result1_text; }
    public void setResult1_text(String result1_text) { this.result1_text = result1_text; }

    public String getResult2_text() { return result2_text; }
    public void setResult2_text(String result2_text) { this.result2_text = result2_text; }
}