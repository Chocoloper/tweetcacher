package de.rwth.rtsib.grp3.tr.util;


import com.mongodb.MongoClient;


public class MongoDBUtil {
	
	private static MongoClient mongoClient;
    
    static{
    	connect();
    }
    
    private static void connect () {
    	mongoClient = new MongoClient( "localhost" , 27017 );
    }
    
    public void disconnect () {
    	mongoClient.close();
    }
    
    public static MongoClient getMongoClient(){
        return MongoDBUtil.mongoClient;
    }
    
}