package org.radicalrad.napomoc;

import java.util.logging.Logger;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

public class Odleglosc {
	
		private Coordinate poczatek;
		private Coordinate koniec;
		
		private double odleglosc;
		private int odlegloscMetry;
		private int odlegloscKilometry;
		
		private final Logger log = Logger.getLogger(getClass().getName());

	    public Odleglosc(Coordinate poczatek, Coordinate koniec) {
			super();
			this.poczatek = poczatek;
			this.koniec = koniec;
			//WGS84
			CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84; 
			double distance;
			try {
				log.info("Wyznaczanie odległości. Początek:"+poczatek.toString()+" koniec: "+koniec.toString() );
				distance = JTS.orthodromicDistance(poczatek, koniec, crs);
				setOdleglosc(distance);
				setOdlegloscMetry((int)distance);
				setOdlegloscKilometry(getOdlegloscMetry()/1000);
			} catch (TransformException e) {
				log.warning(e.getMessage());
				e.printStackTrace();
			}
			
	    }
	    
		   
	    public double getOdleglosc() {
			return odleglosc;
		}
		private void setOdleglosc(double odleglosc) {
			this.odleglosc = odleglosc;
		}
		public int getOdlegloscMetry() {
			return odlegloscMetry;
		}
		private void setOdlegloscMetry(int odlegloscMetry) {
			this.odlegloscMetry = odlegloscMetry;
		}
		public int getOdlegloscKilometry() {
			return odlegloscKilometry;
		}
		private void setOdlegloscKilometry(int odlegloscKilometry) {
			this.odlegloscKilometry = odlegloscKilometry;
		}

		public static void main(String[] args) {
			//52.236421,21.017479
			double x = new Double("52.236421");
			double y = new Double("21.017479");
			Coordinate poczatek = new Coordinate(x,y);
			//52.239364,21.045974
			double x2 = new Double("52.239364");
			double y2 = new Double("21.045974");
			Coordinate koniec = new Coordinate(x2,y2);
			 Odleglosc o = new Odleglosc(poczatek,koniec);
			 String m =o.getOdlegloscMetry()+"";
			 String km =o.getOdlegloscKilometry()+"";
			 System.out.println("Odleglosc:" +m+" ;km: "+km);
			
		}
}
