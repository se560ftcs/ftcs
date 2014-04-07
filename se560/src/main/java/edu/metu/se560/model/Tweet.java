package edu.metu.se560.model;

import java.util.HashSet;
import java.util.Set;
//
/**
 * {"created_at":"Mon Mar 10 16:59:09 +0000 2014",
 * "id":443068550862077952,
 * "id_str":"443068550862077952",
 * "text":"RT @Trabzonliii61: Ne g\u00fczel demi\u015fti \u015eenol G\u00fcne\u015f 
 * \"Trabzonspor taraftar\u0131yla oynamak ate\u015fle oynamakt\u0131r\"diye",
 * "source":"web","truncated":false,
 * "in_reply_to_status_id":null,
 * "in_reply_to_status_id_str":null,
 * "in_reply_to_user_id":null,
 * "in_reply_to_user_id_str":null,
 * "in_reply_to_screen_name":null,
 * "user":{"id":391217867,"id_str":"391217867","name":"\u015eeymaa\u270c","screen_name":"Symayaz",
 * "location":"Trabzon",
 * "url":"http:\/\/instagram.com\/seymayazz","description":"Se\u00e7kin \u00d6zdemir","protected":false,
 * "followers_count":1051,
 * "friends_count":859,
 * "listed_count":0,
 * "created_at":"Sat Oct 15 07:10:35 +0000 2011",
 * "favourites_count":4638,
 * "utc_offset":-10800,
 * "time_zone":"Greenland",
 * "geo_enabled":true,
 * "verified":false,
 * "statuses_count":3367,
 * "lang":"tr",
 * "contributors_enabled":false,
 * "is_translator":false,
 * "is_translation_enabled":false,
 * "profile_background_color":"1A1B1F",
 * "profile_background_image_url":"http:\/\/abs.twimg.com\/images\/themes\/theme9\/bg.gif",
 * "profile_background_image_url_https":"https:\/\/abs.twimg.com\/images\/themes\/theme9\/bg.gif",
 * "profile_background_tile":false,"profile_image_url":"http:\/\/pbs.twimg.com\/profile_images\/441600578452733952\/kpJpQ9-b_normal.jpeg",
 * "profile_image_url_https":"https:\/\/pbs.twimg.com\/profile_images\/441600578452733952\/kpJpQ9-b_normal.jpeg",
 * "profile_banner_url":"https:\/\/pbs.twimg.com\/profile_banners\/391217867\/1393960074",
 * "profile_link_color":"2FC2EF","profile_sidebar_border_color":"181A1E","profile_sidebar_fill_color":"252429","profile_text_color":"666666","profile_use_background_image":true,"default_profile":false,"default_profile_image":false,"following":null,"follow_request_sent":null,"notifications":null},
 * "geo":null,"coordinates":null,"place":null,"contributors":null,
 * "retweeted_status":{"created_at":"Mon Mar 10 16:58:06 +0000 2014","id":443068286654488576,"id_str":"443068286654488576","text":"Ne g\u00fczel demi\u015fti \u015eenol G\u00fcne\u015f \"Trabzonspor taraftar\u0131yla oynamak ate\u015fle oynamakt\u0131r\"diye",
 * "source":"web","truncated":false,"in_reply_to_status_id":null,"in_reply_to_status_id_str":null,"in_reply_to_user_id":null,
 * "in_reply_to_user_id_str":null,"in_reply_to_screen_name":null,
 * "user":{"id":472913762,"id_str":"472913762","name":"Ayem From OFL\u0130\u0130\u0130 \ufea6","screen_name":"Trabzonliii61","location":"\u0130stanbul\/OF-Trabzon","url":null,"description":"2010-2011 \u015eampiyonu TRABZONSPOR Taraftar\u0131                                #OfluyuzElhamd\u00fclillah..! #Arsenal #BVB #Onurizm",
 * "protected":false,"followers_count":600,"friends_count":455,"listed_count":2,"created_at":"Tue Jan 24 12:50:08 +0000 2012","favourites_count":5018,"utc_offset":7200,"time_zone":"Athens","geo_enabled":false,"verified":false,"statuses_count":6494,"lang":"tr","contributors_enabled":false,
 * "is_translator":false,"is_translation_enabled":false,"profile_background_color":"022330","profile_background_image_url":"http:\/\/pbs.twimg.com\/profile_background_images\/378800000144596728\/qlbPyMQQ.jpeg","profile_background_image_url_https":
 * "https:\/\/pbs.twimg.com\/profile_background_images\/378800000144596728\/qlbPyMQQ.jpeg","profile_background_tile":false,
 * "profile_image_url":"http:\/\/pbs.twimg.com\/profile_images\/426036291910631424\/2r_pZId__normal.jpeg","profile_image_url_https":"https:\/\/pbs.twimg.com\/profile_images\/426036291910631424\/2r_pZId__normal.jpeg",
 * "profile_banner_url":"https:\/\/pbs.twimg.com\/profile_banners\/472913762\/1382706249","profile_link_color":"0084B4","profile_sidebar_border_color":"FFFFFF",
 * "profile_sidebar_fill_color":"EFEFEF","profile_text_color":"333333","profile_use_background_image":true,"default_profile":false,"default_profile_image":false,"following":null,"follow_request_sent":null,"notifications":null},"geo":null,
 * "coordinates":null,"place":null,"contributors":null,"retweet_count":2,"favorite_count":3,"entities":{"hashtags":[],"symbols":[],"urls":[],"user_mentions":[]},
 * "favorited":false,"retweeted":false,"lang":"tr"},"retweet_count":0,"favorite_count":0,"entities":{"hashtags":[],"symbols":[],"urls":[],
 * "user_mentions":[{"screen_name":"Trabzonliii61","name":"Musa Top\u00e7uo\u011flu \ufea6","id":472913762,"id_str":"472913762","indices":[3,17]}]},
 * "favorited":false,"retweeted":false,"filter_level":"medium","lang":"tr"}

 * @author semimac
 *
 */
public class Tweet {
	private String id;
	private String idStr;
	private String createdAt;
	private String text;
	private Set<String> words = new HashSet<String>(); //Text alanindaki kelimeler stem bulunup bu set'e doldurulacak
	private String source;
	private String userId;
	private String userLocation;
	private Integer followersCount;
	private String friendsCount;
	//burak bi seyler ekledi
	public Tweet() {
	}
	
	String[] strs = {
			"ç","\\u00e7",
			"ğ","\\u011f",
			"ı","\\u0131",
			"ş","\\u015f",
			"ö","\\u00f6",
			"ü","\\u00fc",
			"Ç","\\u00c7",
			"Ğ","\\u011e",
			"İ","\\u0130",
			"Ş","\\u015e",
			"Ö","\\u00d6",
			"Ü","\\u00dc"
	};
	private String toTr(String str) {
		for (int i=0;i<strs.length/2;i++) {
			String from = strs[i*2+1];
			String to = strs[i*2];
			while (str.contains(from)) {
				str = str.replace(from, to);
			}
		}
		return str;
	}

	public Tweet(String line) {
		this.setId(Utils.parseBetween(line, "\"id\":", ","));
		this.setIdStr(Utils.parseBetween(line, "\"id_str\":\"", "\""));
		this.setCreatedAt(Utils.parseBetween(line, "\"created_at\":\"", "\""));
		text = Utils.parseBetween(line, "\"text\":\"", "\",");
		text = toTr(text.replace("\\\""," ").replace("  "," "));
		text = removeWords(text);
		String[] words = text.split(" ");
		for(String w: words) {
			this.getWords().add(w);
		}
		this.setUserId(Utils.parseBetween(line, "\"user\":{\"id\":", ","));
		this.setUserLocation(Utils.parseBetween(line, "\"location\":\"", "\","));
		//System.out.println(this.toString());
	}

	private String removeWords(String txt) {
		txt = txt
				.replaceAll("\\\\u\\S*", "")
				.replaceAll("RT ", "")
				.replaceAll("@\\S*", "")
				.replaceAll("#\\S*", "")
				.replaceAll(" http\\S*", "")
				.replaceAll("\\p{P}", "")
				.replaceAll("GOT\\S*", "***")
				.replaceAll("SIK\\S*", "***")
				.replaceAll("KOY\\S*", "***")
				
				;
		return txt;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Set<String> getWords() {
		return words;
	}

	public void setWords(Set<String> words) {
		this.words = words;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public Integer getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}
	public String getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(String friendsCount) {
		this.friendsCount = friendsCount;
	}
	
	public Set getProperties() {
		Set result = new HashSet<String>();
		result.addAll(this.getWords());
		result.add(Utils.parseTill(this.getCreatedAt(),":")); //Bulunulan saat ile bir �nceki ve bir sonraki saat ayn� kabul edilecek �ekilde d�zenle
		result.add(this.getUserId());
		result.add(this.getUserLocation());
		
		return result;
	}

	public double jaccardSimilarity(Tweet t2) {
		Set<String> union = new HashSet<String>();
	    union.addAll(this.getWords());
	    union.addAll(t2.getWords());
	    
	    Set<String> intersect = new HashSet<String>();
	    intersect.addAll(this.getWords());
	    intersect.retainAll(t2.getWords());
	    double result = (double) ((intersect.size()*1.0) / (union.size()*1.0));
	    return result;
	}
	
	
	public String toString() {
		//return "id:"+this.getId()+", text:"+this.text;
		return this.getId()+"\tX\t"+this.text;
	}
	
	public String getDescription() {
		return this.text;
	}
	
	public String getDescriptionUtf() {
		String str = this.getDescription();
		for (int i=0;i<strs.length/2;i++) {
			String from = strs[i*2];
			String to = strs[i*2+1];
			while (str.contains(from)) {
				str = str.replace(from, to);
			}
		}
		return str;
	}

}
