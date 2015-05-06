package de.rwth.rtsib.grp3.tr;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.mongodb.morphia.Morphia;

import de.rwth.rtsib.grp3.tr.DAO.mongo.TweetDAO;
import de.rwth.rtsib.grp3.tr.entity.Tweet;
import de.rwth.rtsib.grp3.tr.util.MongoDBUtil;
import twitter4j.FilterQuery;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetReaderMain {

	public static void main(String args[]) throws Exception{
		print("Start Main");
		
		TwitterStream stream = new TwitterStreamFactory().getInstance();
		ConcurrentLinkedQueue<Tweet> queue = new ConcurrentLinkedQueue<Tweet>();
		Morphia morphia = new Morphia();
		TweetDAO dao = new TweetDAO(MongoDBUtil.getMongoClient(), morphia);
		FilterQuery filterQuery = new FilterQuery();
		
		stream.addListener(new CustomStatusListener(queue));

        String[] trends = getTrends();
        
        if ( trends.length == 0) {
        	print("Start Filtering");
        	stream.sample();
        }
        else {
        	print("Current trends in UK:");
        	for (String trend : trends) {
        		System.out.println("- " + trend);
        	}
        	filterQuery.language(new String[]{ "en" });
        	filterQuery.track(trends);
		
        	print("Start Filtering");
        	stream.filter(filterQuery);
        }
		
		print("Start Saving!");
		while(true){
			while(!queue.isEmpty()) {
				queue.peek().print();
				dao.save(queue.poll());
			}
		}
	}
	
	private static void print(String printMe) {
		System.out.println("#################");
		System.out.println(printMe);
		System.out.println("#################");
	}
	
    public static String[] getTrends() {
        List<String> trendNames = new ArrayList<String>();
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Trends trends = twitter.getPlaceTrends(23424975);
            for (Trend trend : trends.getTrends()) {
                trendNames.add(trend.getName());
            }
        } catch (TwitterException e) {
            System.out.println("ERROR: Cannot load current trends.");
        }
        return trendNames.toArray(new String[trendNames.size()]);
    }
}
