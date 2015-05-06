package de.rwth.rtsib.grp3.tr;


import java.util.concurrent.ConcurrentLinkedQueue;

import de.rwth.rtsib.grp3.tr.entity.Tweet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class CustomStatusListener implements StatusListener {

	private ConcurrentLinkedQueue<Tweet> queue;
	
	public CustomStatusListener(ConcurrentLinkedQueue<Tweet> queue){
		this.queue = queue;
	}
	
	public void onStatus(Status status) {
		queue.add(new Tweet(status));
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

	public void onTrackLimitationNotice(int i) {
		System.out.println("Track limitation notice for " + i);
	}

	public void onScrubGeo(long l, long l2) {}

	public void onStallWarning(StallWarning stallWarning) {
		System.out.println("Stall warning");
	}

	public void onException(Exception e) {
		System.out.println("Exception occured:" + e.getMessage());
		e.printStackTrace();
	}

}
