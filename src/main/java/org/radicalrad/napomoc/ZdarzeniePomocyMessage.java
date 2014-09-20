package org.radicalrad.napomoc;

import java.util.Date;

public class ZdarzeniePomocyMessage {

	private String uzytkownikWzywajacy;
	private String status;
	private String uzytkownikRatujacy;
	private String region;
	private String lokX;
	private String lokY;
	private Date dataOdebrania;
	private Date dataZgloszenia;
	
	public String getUzytkownikWzywajacy() {
		return uzytkownikWzywajacy;
	}
	public void setUzytkownikWzywajacy(String uzytkownikWzywajacy) {
		this.uzytkownikWzywajacy = uzytkownikWzywajacy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUzytkownikRatujacy() {
		return uzytkownikRatujacy;
	}
	public void setUzytkownikRatujacy(String uzytkownikRatujacy) {
		this.uzytkownikRatujacy = uzytkownikRatujacy;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLokX() {
		return lokX;
	}
	public void setLokX(String lokX) {
		this.lokX = lokX;
	}
	public String getLokY() {
		return lokY;
	}
	public void setLokY(String lokY) {
		this.lokY = lokY;
	}
	public Date getDataOdebrania() {
		return dataOdebrania;
	}
	public void setDataOdebrania(Date dataOdebrania) {
		this.dataOdebrania = dataOdebrania;
	}
	public Date getDataZgloszenia() {
		return dataZgloszenia;
	}
	public void setDataZgloszenia(Date dataZgloszenia) {
		this.dataZgloszenia = dataZgloszenia;
	}
	
	@Override
	public String toString() {
		return "ZdarzeniePomocyMessage [uzytkownikWzywajacy="
				+ uzytkownikWzywajacy + ", status=" + status
				+ ", uzytkownikRatujacy=" + uzytkownikRatujacy + ", region="
				+ region + ", lokX=" + lokX + ", lokY=" + lokY
				+ ", dataOdebrania=" + dataOdebrania + ", dataZgloszenia="
				+ dataZgloszenia + "]";
	}
}
