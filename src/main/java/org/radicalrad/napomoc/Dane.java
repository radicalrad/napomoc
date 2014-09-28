package org.radicalrad.napomoc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.vividsolutions.jts.geom.Coordinate;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;



public class Dane {
	private static final String nazwaCache = "ratownicy";
	private final Logger log = Logger.getLogger(getClass().getName());
	private static Dane instance = null;
	private Cache cache = null;

	private Dane() {

		try {
			CacheManager singletonManager = CacheManager.create();
			Cache c = new Cache(
					  new CacheConfiguration(nazwaCache, 100000)
					 //   .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
					    .eternal(true)
					    .timeToLiveSeconds(0)
					    .timeToIdleSeconds(0)
					    .diskExpiryThreadIntervalSeconds(0)
					    .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)));
					
			singletonManager.addCache(c);
			//singletonManager.addCache(nazwaCache);
			//Cache c = singletonManager.getCache(nazwaCache);
			
			
			setCache(c);
		} catch (Exception e) {

			log.warning("Problem initializing cache for region name ["
					+ nazwaCache + "].");
		}
	}
	
	public static Dane getInstance()
    {
        synchronized (Dane.class)
        {
            if (instance == null)
            {
                instance = new Dane();
            }
        }

        return instance;
    }

	public void aktualizujPozycjeRatownika(PozycjaRatownikaMessage prm) {

		try {
			log.info("Aktualizacja ratownika w cache");
			if (prm != null) {
				if (cache.get(prm.getUzytkownikRatujacy()) == null) {
					cache.put(new Element(prm.getUzytkownikRatujacy(), prm));
					
				} else {

					cache.remove(prm.getUzytkownikRatujacy());
					cache.put(new Element(prm.getUzytkownikRatujacy(), prm));

				}
			}
		} catch (Exception e) {
			log.warning("Wyjatek cache dla ratownik id:"+prm.getUzytkownikRatujacy());
		}
	}

	public List<PozycjaRatownikaMessage> pobierzRatownikowWodlegosciKm(double x, double y, int km){
		List keys = cache.getKeys();
		List<PozycjaRatownikaMessage> prml = new ArrayList();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Element el = cache.get(key);
			if(el!=null){
				log.info("iterowanie przez liste ratownikow: "+el.toString());
			PozycjaRatownikaMessage prm = (PozycjaRatownikaMessage)el.getObjectValue();
			
			Coordinate c1 = new Coordinate(x,y);
			Coordinate c2 = new Coordinate(new Double(prm.getLokX()),new Double(prm.getLokY()));
			Odleglosc o = new Odleglosc(c1, c2);
			if(o.getOdlegloscKilometry()<=km){
			prml.add(prm);
			log.info("Ratownik w poblizu: "+prm.toString());
			}
			}
		}
		return prml;
	}
		
		public List<PozycjaRatownikaMessage> pobierzRatownikow(){
			List keys = cache.getKeys();
			List<PozycjaRatownikaMessage> prml = new ArrayList();
			Iterator it = keys.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Element el = cache.get(key);
				if(el!=null){
					log.info("iterowanie przez liste ratownikow: "+el.toString());
				PozycjaRatownikaMessage prm = (PozycjaRatownikaMessage)el.getObjectValue();
				
				
				prml.add(prm);
				
				
				}
			}
		
		
	return prml;
	}
	
	
	private void setCache(Cache cache) {
		this.cache = cache;
	}

	public static void main(String[] args){
		Dane d = Dane.getInstance();
		d.pobierzRatownikow();
		
		
		PozycjaRatownikaMessage msg = new PozycjaRatownikaMessage();
		msg.setLokX("52.236421");
		msg.setLokY("21.017479");		
		msg.setUzytkownikRatujacy("rad@test.pl");
		
		d.aktualizujPozycjeRatownika(msg);
		PozycjaRatownikaMessage msg2 = new PozycjaRatownikaMessage();
		msg2.setLokX("52.236421");
		msg2.setLokY("21.017479");		
		msg2.setUzytkownikRatujacy("test@test.pl");
		d.aktualizujPozycjeRatownika(msg2);
		
		msg.setLokX("52.236424");
		msg.setLokY("21.017471");		
		msg.setUzytkownikRatujacy("rad2@test.pl");
		d.aktualizujPozycjeRatownika(msg);
		
		double x2 = new Double("52.239364");
		double y2 = new Double("21.045974");
		d.pobierzRatownikowWodlegosciKm(x2, y2, 10);
		
	}
}
