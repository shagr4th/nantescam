package com.ab.nantescam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cams {
	
	public static final String KEY_PREFS = "CamPrefs";
	public static final String KEY_PREF_FAVS = "CamPrefFavs";
	public static final String KEY_PREF_STARTFAVS = "CamPrefStartFavs";
	public static final String KEY_IMAGEURL = "ImageURL";
	public static final String KEY_MAP_ZOOM = "mapZoom";
	public static final String KEY_MAP_LAT = "mapLat";
	public static final String KEY_MAP_LONG = "mapLong";
	private static List<WebCam> allWebCams = new ArrayList<WebCam>();
	
	static {
		
		// http://rurunuela.free.fr/download/trafic.app/camera.txt
		
		allWebCams.add(new WebCam(701, "Pont du Cens", "http://infotrafic.nantesmetropole.fr/data/webcams/rt701.jpg", 47.24741, -1.57745));
		allWebCams.add(new WebCam(702, "Ront point de Rennes", "http://infotrafic.nantesmetropole.fr/data/webcams/rt702.jpg", 47.23337, -1.56582));
		allWebCams.add(new WebCam(703, "Michelet", "http://infotrafic.nantesmetropole.fr/data/webcams/rt703.jpg", 47.23301, -1.55656));
		allWebCams.add(new WebCam(704, "Pont Morand", "http://infotrafic.nantesmetropole.fr/data/webcams/rt704.jpg", 47.22119, -1.5556));
		allWebCams.add(new WebCam(705, "Tortière", "http://infotrafic.nantesmetropole.fr/data/webcams/rt705.jpg", 47.23533, -1.5487));
		allWebCams.add(new WebCam(706, "Hôtel Dieu", "http://infotrafic.nantesmetropole.fr/data/webcams/rt706.jpg", 47.21255, -1.55412));
		allWebCams.add(new WebCam(707, "Liberté-Chevreul", "http://infotrafic.nantesmetropole.fr/data/webcams/rt707.jpg", 47.19827, -1.5885));
		allWebCams.add(new WebCam(708, "Beauséjour", "http://infotrafic.nantesmetropole.fr/data/webcams/rt708.jpg", 47.23762, -1.58620));
		allWebCams.add(new WebCam(709, "Prairie au Duc", "http://infotrafic.nantesmetropole.fr/data/webcams/rt709.jpg", 47.20455, -1.56245));
		allWebCams.add(new WebCam(710, "Pirmil 1", "http://infotrafic.nantesmetropole.fr/data/webcams/rt710.jpg", 47.19773, -1.5422));
		
		allWebCams.add(new WebCam(711, "Pont Aristide Briand", "http://infotrafic.nantesmetropole.fr/data/webcams/rt711.jpg", 47.21082, -1.5427));
		allWebCams.add(new WebCam(712, "Martyrs Nantais", "http://infotrafic.nantesmetropole.fr/data/webcams/rt712.jpg", 47.20586, -1.54742));
		allWebCams.add(new WebCam(713, "Anne de Bretagne", "http://infotrafic.nantesmetropole.fr/data/webcams/rt713.jpg", 47.20922, -1.5667));
		allWebCams.add(new WebCam(714, "Rond point de Paris 1", "http://infotrafic.nantesmetropole.fr/data/webcams/rt714.jpg", 47.23397, -1.53561));
		// not found 29/01/14 allWebCams.add(new WebCam(715, "Duchesse Anne", "http://infotrafic.nantesmetropole.fr/data/webcams/rt715.jpg", 47.21643, -1.54693));
		allWebCams.add(new WebCam(716, "Marguyonnes", "http://infotrafic.nantesmetropole.fr/data/webcams/rt716.jpg", 47.19265, -1.5629));
		allWebCams.add(new WebCam(717, "Route de Vannes", "http://infotrafic.nantesmetropole.fr/data/webcams/rt717.jpg", 47.24624, -1.60423));
		allWebCams.add(new WebCam(718, "Vincent Gache", "http://infotrafic.nantesmetropole.fr/data/webcams/rt718.jpg", 47.20686, -1.53945));
		// not found 29/01/14 allWebCams.add(new WebCam(719, "Bourdonnières vers Sud", "http://infotrafic.nantesmetropole.fr/data/webcams/rt719.jpg"));
		allWebCams.add(new WebCam(720, "Route de Pornic", "http://infotrafic.nantesmetropole.fr/data/webcams/rt720.jpg", 47.18769, -1.5870));
		
		allWebCams.add(new WebCam(721, "Pont Willy Brandt", "http://infotrafic.nantesmetropole.fr/data/webcams/rt721.jpg", 47.20966, -1.5368));
		allWebCams.add(new WebCam(722, "Pirmil 2", "http://infotrafic.nantesmetropole.fr/data/webcams/rt722.jpg", 47.19770, -1.5428));
		allWebCams.add(new WebCam(723, "Rond point du Cardo", "http://infotrafic.nantesmetropole.fr/data/webcams/rt723.jpg", 47.26234, -1.5835));
		allWebCams.add(new WebCam(724, "Gustave Roch", "http://infotrafic.nantesmetropole.fr/data/webcams/rt724.jpg", 47.20384, -1.55163));
		allWebCams.add(new WebCam(725, "Greneraie", "http://infotrafic.nantesmetropole.fr/data/webcams/rt725.jpg", 47.19805, -1.529938));
		allWebCams.add(new WebCam(726, "Porte de la Beaujoire", "http://infotrafic.nantesmetropole.fr/data/webcams/rt726.jpg", 47.25508, -1.52989));
		allWebCams.add(new WebCam(727, "Rond point de Paris 2", "http://infotrafic.nantesmetropole.fr/data/webcams/rt727.jpg", 47.23393, -1.53616));
		allWebCams.add(new WebCam(728, "Duchesse Anne", "http://infotrafic.nantesmetropole.fr/data/webcams/rt728.jpg", 47.21627,-1.54598 )); // ajout 29/01/14
		allWebCams.add(new WebCam(729, "Victor Hugo/Gustave Roch", "http://infotrafic.nantesmetropole.fr/data/webcams/rt729.jpg", 47.20371, -1.55178));
		allWebCams.add(new WebCam(730, "Bourdonnières Nord", "http://infotrafic.nantesmetropole.fr/data/webcams/rt730.jpg", 47.18432, -1.52020));
		
		allWebCams.add(new WebCam(731, "Pont des 3 continents", "http://infotrafic.nantesmetropole.fr/data/webcams/rt731.jpg", 47.19803, -1.5619));
		allWebCams.add(new WebCam(732, "Victor Schoelcher", "http://infotrafic.nantesmetropole.fr/data/webcams/rt732.jpg", 47.20992, -1.56143));
		
		allWebCams.add(new WebCam(734, "Jacksonville", "http://infotrafic.nantesmetropole.fr/data/webcams/rt734.jpg", 47.20725, -1.57224));
		allWebCams.add(new WebCam(735, "Petit Port", "http://infotrafic.nantesmetropole.fr/data/webcams/rt735.jpg", 47.24349, -1.5566));
		allWebCams.add(new WebCam(736, "Prairie de Mauves", "http://infotrafic.nantesmetropole.fr/data/webcams/rt736.jpg", 47.22037, -1.516726));
		allWebCams.add(new WebCam(737, "Croix des Herses", "http://infotrafic.nantesmetropole.fr/data/webcams/rt737.jpg", 47.19296, -1.52775));
		allWebCams.add(new WebCam(738, "Rezé Saint Paul", "http://infotrafic.nantesmetropole.fr/data/webcams/rt738.jpg", 47.18479, -1.54617));
		allWebCams.add(new WebCam(739, "Raymond Poincaré", "http://infotrafic.nantesmetropole.fr/data/webcams/rt739.jpg", 47.22235, -1.58540));
		allWebCams.add(new WebCam(740, "Rue de Strasbourg", "http://infotrafic.nantesmetropole.fr/data/webcams/rt740.jpg", 47.21500, -1.55040));
		
		allWebCams.add(new WebCam(741, "Baco Madeleine", "http://infotrafic.nantesmetropole.fr/data/webcams/rt741.jpg", 47.21258, -1.55244));
		allWebCams.add(new WebCam(742, "Place du Cirque", "http://infotrafic.nantesmetropole.fr/data/webcams/rt742.jpg", 47.21638, -1.55656));
		// too old 29/01/14 allWebCams.add(new WebCam(743, "Haluchère 1", "http://infotrafic.nantesmetropole.fr/data/webcams/rt743.jpg"));
		allWebCams.add(new WebCam(744, "Sophie Trebuchet", "http://infotrafic.nantesmetropole.fr/data/webcams/rt744.jpg", 47.22052, -1.54547));
		allWebCams.add(new WebCam(745, "Buat Desaix", "http://infotrafic.nantesmetropole.fr/data/webcams/rt745.jpg", 47.22716, -1.54257));
		allWebCams.add(new WebCam(746, "Croix Bonneau", "http://infotrafic.nantesmetropole.fr/data/webcams/rt746.jpg", 47.20955, -1.59636));
		// not found 29/01/14 allWebCams.add(new WebCam(747, "Bonde", "http://infotrafic.nantesmetropole.fr/data/webcams/rt747.jpg"));
		allWebCams.add(new WebCam(748, "Place Auriol", "http://infotrafic.nantesmetropole.fr/data/webcams/rt748.jpg", 47.22178, -1.59223)); // ajout 29/01/14
		allWebCams.add(new WebCam(749, "Bout des pavés", "http://infotrafic.nantesmetropole.fr/data/webcams/rt749.jpg", 47.25403, -1.57643));
		allWebCams.add(new WebCam(750, "Boulevard Einstein", "http://infotrafic.nantesmetropole.fr/data/webcams/rt750.jpg", 47.26017, -1.55993));
		
		allWebCams.add(new WebCam(751, "Boulevard des Poilus", "http://infotrafic.nantesmetropole.fr/data/webcams/rt751.jpg", 47.22847, -1.52631));
		allWebCams.add(new WebCam(752, "Ranzay", "http://infotrafic.nantesmetropole.fr/data/webcams/rt752.jpg", 47.25257, -1.52999));
		allWebCams.add(new WebCam(753, "Trois Moulins", "http://infotrafic.nantesmetropole.fr/data/webcams/rt753.jpg", 47.17826, -1.54432));
		allWebCams.add(new WebCam(754, "Indochine Sud", "http://infotrafic.nantesmetropole.fr/data/webcams/rt754.jpg", 47.24241, -1.53439)); // ajout 29/01/14
		allWebCams.add(new WebCam(755, "Indochine Nord", "http://infotrafic.nantesmetropole.fr/data/webcams/rt755.jpg", 47.24287, -1.53415)); // ajout 29/01/14
		allWebCams.add(new WebCam(756, "Haluchère", "http://infotrafic.nantesmetropole.fr/data/webcams/rt756.jpg", 47.24847, -1.52139));
		allWebCams.add(new WebCam(757, "Saint Joseph Beaujoire", "http://infotrafic.nantesmetropole.fr/data/webcams/rt757.jpg", 47.25896, -1.52772));
		allWebCams.add(new WebCam(758, "Pont Senghor Blancho", "http://infotrafic.nantesmetropole.fr/data/webcams/rt758.jpg", 47.20682,-1.526823));
		allWebCams.add(new WebCam(759, "Porte de la Chapelle", "http://infotrafic.nantesmetropole.fr/data/webcams/rt759.jpg", 47.259562,-1.557301));
		
		allWebCams.add(new WebCam(766, "Perray", "http://infotrafic.nantesmetropole.fr/data/webcams/rt766.jpg", 47.241311,-1.508726)); // ajout 29/01/14
		allWebCams.add(new WebCam(767, "Sainte Luce int.", "http://infotrafic.nantesmetropole.fr/data/webcams/rt767.jpg", 47.24510, -1.5035)); // ajout 29/01/14
		allWebCams.add(new WebCam(768, "Sainte Luce ext.", "http://infotrafic.nantesmetropole.fr/data/webcams/rt768.jpg", 47.24675, -1.50009)); // ajout 29/01/14
		allWebCams.add(new WebCam(769, "Tbilissi", "http://infotrafic.nantesmetropole.fr/data/webcams/rt769.jpg", 47.212678,-1.541712)); // ajout 29/01/14
		
		allWebCams.add(new WebCam(770, "Picasso Berlin", "http://infotrafic.nantesmetropole.fr/data/webcams/rt770.jpg", 47.216349,-1.533985));
		allWebCams.add(new WebCam(771, "Senghor Enchantes", "http://infotrafic.nantesmetropole.fr/data/webcams/rt771.jpg", 47.204846,-1.523637));
		allWebCams.add(new WebCam(772, "Moutonnerie", "http://infotrafic.nantesmetropole.fr/data/webcams/rt772.jpg", 47.219716,-1.533172));
		
		allWebCams.add(new WebCam(774, "Millerand", "http://infotrafic.nantesmetropole.fr/data/webcams/rt774.jpg", 47.208023, -1.53029)); // ajout 29/01/14
		allWebCams.add(new WebCam(775, "Freinet", "http://infotrafic.nantesmetropole.fr/data/webcams/rt775.jpg", 47.20673, -1.53374)); // ajout 29/01/14
		allWebCams.add(new WebCam(776, "République", "http://infotrafic.nantesmetropole.fr/data/webcams/rt776.jpg", 47.205406,-1.554667)); // ajout 29/01/14
		allWebCams.add(new WebCam(777, "Quai des Antilles", "http://infotrafic.nantesmetropole.fr/data/webcams/rt777.jpg", 47.19982,-1.5732));
		allWebCams.add(new WebCam(778, "Pont Haudaudine", "http://infotrafic.nantesmetropole.fr/data/webcams/rt778.jpg", 47.208286,-1.555354));
		allWebCams.add(new WebCam(779, "Mangin", "http://infotrafic.nantesmetropole.fr/data/webcams/rt779.jpg", 47.20051,-1.5444));
		allWebCams.add(new WebCam(780, "Saint Similien", "http://infotrafic.nantesmetropole.fr/data/webcams/rt780.jpg", 47.220095,-1.558806));
		
		allWebCams.add(new WebCam(781, "Porte de Carquefou", "http://infotrafic.nantesmetropole.fr/data/webcams/rt781.jpg", 47.25309, -1.5155));
		allWebCams.add(new WebCam(782, "Pont Tabarly Sarrebruck", "http://infotrafic.nantesmetropole.fr/data/webcams/rt782.jpg", 47.21367, -1.5295));
		allWebCams.add(new WebCam(786, "Pont Tabarly Bollardière", "http://infotrafic.nantesmetropole.fr/data/webcams/rt786.jpg", 47.21134, -1.52822));
		allWebCams.add(new WebCam(787, "Schuman Close", "http://infotrafic.nantesmetropole.fr/data/webcams/rt787.jpg", 47.2415, -1.5733));
		allWebCams.add(new WebCam(788, "Pont Motte Rouge", "http://infotrafic.nantesmetropole.fr/data/webcams/rt788.jpg", 47.22876, -1.55297));
		allWebCams.add(new WebCam(789, "Place René Bouhier", "http://infotrafic.nantesmetropole.fr/data/webcams/rt789.jpg", 47.20967, -1.57047));
		allWebCams.add(new WebCam(790, "Edit de Nantes", "http://infotrafic.nantesmetropole.fr/data/webcams/rt790.jpg", 47.214571, -1.56747));
		
		allWebCams.add(new WebCam(791, "Guist'hau Geslin", "http://infotrafic.nantesmetropole.fr/data/webcams/rt791.jpg", 47.21605, -1.56580));
		allWebCams.add(new WebCam(792, "Place Eugène Livet", "http://infotrafic.nantesmetropole.fr/data/webcams/rt792.jpg", 47.21142,-1.56728));
		
		allWebCams.add(new WebCam(794, "Souillarderie", "http://infotrafic.nantesmetropole.fr/data/webcams/rt794.jpg", 47.23727, -1.51684)); // ajout 29/01/14
		allWebCams.add(new WebCam(795, "Européeens", "http://infotrafic.nantesmetropole.fr/data/webcams/rt795.jpg", 47.28059, -1.51413)); // ajout 29/01/14
		
	}
	
	public static List<WebCam> getNames (List<Integer> favorites) {
		ArrayList<WebCam> values = new ArrayList<WebCam>();
		for(WebCam s:allWebCams) {
			if (favorites == null || favorites.contains(s.getCode()))
				values.add(s);
		}
		Collections.sort(values);
		return values;
	}

	public static WebCam getWebCam(int parseInt) {
		for(WebCam s:allWebCams) {
			if (s.getCode() == parseInt)
				return s;
		}
		return null;
	}

}
