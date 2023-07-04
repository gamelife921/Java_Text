package WeatherForecast.Domain.Common.Dto;

public class FcstDto {

    private String fcstDate;
    private String fcstTime;
    private String fcstPTY;
    private String fcstRN1;
    private String fcstREH;
    private String fcstT1H;

    public FcstDto() {
    }

    public FcstDto(String fcstDate, String fcstTime, String fcstPTY, String fcstRN1, String fcstREH, String fcstT1H) {
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.fcstPTY = fcstPTY;
        this.fcstRN1 = fcstRN1;
        this.fcstREH = fcstREH;
        this.fcstT1H = fcstT1H;
    }

    @Override
    public String toString() {
        return "FcstDto{" +
                "fcstDate='" + fcstDate + '\'' +
                ", fcstTime='" + fcstTime + '\'' +
                ", fcstPTY='" + fcstPTY + '\'' +
                ", fcstRN1='" + fcstRN1 + '\'' +
                ", fcstREH='" + fcstREH + '\'' +
                ", fcstT1H='" + fcstT1H + '\'' +
                '}';
    }

    public String getFcstDate() {
        return fcstDate;
    }

    public void setFcstDate(String fcstDate) {
        this.fcstDate = fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public void setFcstTime(String fcstTime) {
        this.fcstTime = fcstTime;
    }

    public String getFcstPTY() {
        return fcstPTY;
    }

    public void setFcstPTY(String fcstPTY) {
        this.fcstPTY = fcstPTY;
    }

    public String getFcstRN1() {
        return fcstRN1;
    }

    public void setFcstRN1(String fcstRN1) {
        this.fcstRN1 = fcstRN1;
    }

    public String getFcstREH() {
        return fcstREH;
    }

    public void setFcstREH(String fcstREH) {
        this.fcstREH = fcstREH;
    }

    public String getFcstT1H() {
        return fcstT1H;
    }

    public void setFcstT1H(String fcstT1H) {
        this.fcstT1H = fcstT1H;
    }
}
