package trainingBot.model;


import redis.clients.jedis.Jedis;

public class UserStateRepository {
    Jedis jedis = new Jedis("localhost");

}
