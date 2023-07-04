package WeatherForecast.Domain.Common.Dto;

public class NcstDto {

    private String PTY;
    private String RN1;
    private String REH;
    private String T1H;

    public NcstDto() {}

    public NcstDto(String PTY, String RN1, String REH, String t1H) {

        this.PTY = PTY;
        this.RN1 = RN1;
        this.REH = REH;
        this.T1H = t1H;
    }

    @Override
    public String toString() {
        return "NcstDto{" +
                "PTY='" + PTY + '\'' +
                ", RN1='" + RN1 + '\'' +
                ", REH='" + REH + '\'' +
                ", T1H='" + T1H + '\'' +
                '}';
    }

    public String getPTY() {
        return PTY;
    }

    public void setPTY(String PTY) {
        this.PTY = PTY;
    }

    public String getRN1() {
        return RN1;
    }

    public void setRN1(String RN1) {
        this.RN1 = RN1;
    }

    public String getREH() {
        return REH;
    }

    public void setREH(String REH) {
        this.REH = REH;
    }

    public String getT1H() {
        return T1H;
    }

    public void setT1H(String t1H) {
        T1H = t1H;
    }
}
/*
         * 항목값	항목명	단위
         * POP	강수확률	 %
         * RN1  1시간 강수량
         * PTY	강수형태	코드값
         * 강수형태(PTY) 코드 : (초단기) 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)
                      (단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
         * R06	6시간 강수량	범주 (1 mm)
         * T1H  기온
         * REH	습도	 %
         * S06	6시간 신적설	범주(1 cm)
         * SKY	하늘상태	코드값
         * 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)
         * T3H	3시간 기온	 ℃
         * TMN	아침 최저기온	 ℃
         * TMX	낮 최고기온	 ℃
         * UUU	풍속(동서성분)	 m/s
         * VVV	풍속(남북성분)	 m/s
         * WAV	파고	 M
         * VEC	풍향	 m/s
         * WSD	풍속	1
         */