package com.ticketsystem.ticketsystem.booking.services;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import  java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisSeatLockService implements  SeatLockService {

    private static final Duration LOCK_TTL = Duration.ofMinutes(10);

    private  StringRedisTemplate redisTemplate;

    public RedisSeatLockService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @Override
    public boolean lockSeats(Long showId, List<Long> seatIds, Long bookingId) {

        List<String> acquiredLocks= new ArrayList<>();

        for(Long seatId: seatIds){
            String key = lockKey(showId, seatId);
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, bookingId.toString(), LOCK_TTL);
            if(Boolean.FALSE.equals(success)){
                releaseKeys(acquiredLocks);
                return false;
            }
            acquiredLocks.add(key);
        }

        return true;
    }

    @Override
    public void releaseSeats(Long showId, List<Long> seatIds, Long bookingId) {
        for(Long seatId : seatIds){
            String key = lockKey(showId, seatId);
            String value = redisTemplate.opsForValue().get(key);
            if(bookingId.toString().equals(value)){
                redisTemplate.delete(key);
            }
        }
    }

    private String lockKey(Long showId, Long seatId){
        return "seat lock:"+showId+":"+seatId;
    }

    private void releaseKeys(List<String> keys){
        if(!keys.isEmpty()){
            redisTemplate.delete(keys);
        }
    }
}
