package de.rwth.rtsib.grp3.tr.DAO.mongo;

import java.util.List;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

import de.rwth.rtsib.grp3.tr.entity.Tweet;

public class TweetDAO extends BasicDAO<Tweet,String>{

	public TweetDAO(MongoClient mongoClient, Morphia morphia) {
		super(mongoClient, morphia, "Twitter");
	}
	
	public List<Tweet> findAll() {
		return this.find().asList();
	}

}
