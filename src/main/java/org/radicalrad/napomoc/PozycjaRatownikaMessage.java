package org.radicalrad.napomoc;

public class PozycjaRatownikaMessage {
	private String uzytkownikRatujacy;
	private String lokX;
	private String lokY;
	
	@Override
	public String toString() {
		return "PozycjaRatownikaMessage [uzytkownikRatujacy="
				+ uzytkownikRatujacy + ", lokX=" + lokX + ", lokY=" + lokY
				+ "]";
	}
	public String getUzytkownikRatujacy() {
		return uzytkownikRatujacy;
	}
	public void setUzytkownikRatujacy(String uzytkownikRatujacy) {
		this.uzytkownikRatujacy = uzytkownikRatujacy;
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

}
